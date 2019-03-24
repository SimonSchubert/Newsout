import UIKit
import Kingfisher

import main
import SwiftKeychainWrapper

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
class FeedsViewController: UITableViewController {
    let api = Api()
    var data = ([Feed])()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let refreshControl = UIRefreshControl()
        if #available(iOS 10.0, *) {
            tableView.refreshControl = refreshControl
        } else {
            tableView.addSubview(refreshControl)
        }
        refreshControl.addTarget(self, action: #selector(refreshFeedData(_:)), for: .valueChanged)
        
        let addImage    = UIImage(named: "icons8-plus_math")!
        let settingsImage  = UIImage(named: "icons8-settings3")!
        
        let addButton   = UIBarButtonItem(image: addImage,  style: .plain, target: self, action: #selector(didTapAddButton))
        let settingsButton = UIBarButtonItem(image: settingsImage,  style: .plain, target: self, action: #selector(didTapSettingsButton))
        
        navigationItem.rightBarButtonItems = [settingsButton, addButton]
        
        tableView.tableFooterView = UIView()
        
        let database = Database()
        self.data = database.getFeedQueries()?.selectAllByTitle().executeAsList() as! [Feed]
        self.tableView?.reloadData()
        
        self.tableView.refreshManually()
        fetchFeedData()
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "basic", for: indexPath) as! FeedTableViewCell
        
        let item = data[indexPath.row]
        
        cell.titleLabel?.text = item.title
        cell.unreadCountLabel?.text = String(item.unreadCount)
        cell.unreadCountLabel?.isHidden = item.unreadCount == 0
        cell.coverImageView?.kf.setImage(with: URL(string: item.faviconUrl), placeholder: UIImage(named: item.isFolder == 1 ? "icons8-folder_invoices_filled" : "icons8-rss_filled"))
        cell.accessoryType = .disclosureIndicator
        
        return cell
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let item = data[indexPath.row]
        performSegue(withIdentifier: "items", sender: item)
        tableView.deselectRow(at: indexPath, animated: true)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?)
    {
        if segue.destination is ItemsViewController
        {
            let vc = segue.destination as? ItemsViewController
            if sender is Feed {
                let feed = (sender as? Feed)
                vc?.id = feed?.id ?? 0
                vc?.type = feed?.isFolder ?? 0
                vc?.title = feed?.title ?? ""
            }
        }
    }
    
    @objc private func refreshFeedData(_ sender: Any) {
        fetchFeedData()
    }
    
    @objc func didTapAddButton(sender: AnyObject){
        didTapAddFeedButton(url: "")
    }
    
    @objc func didTapSettingsButton(sender: AnyObject){
        KeychainWrapper.standard.set("", forKey: "SERVER")
        KeychainWrapper.standard.set("", forKey: "EMAIL")
        KeychainWrapper.standard.set("", forKey: "PASSWORD")
        
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        let storyboard = UIStoryboard.init(name: "Main", bundle: nil)
        let nav = storyboard.instantiateViewController(withIdentifier: "login")
        appDelegate.window?.rootViewController = nav
    }
    
    private func didTapAddFeedButton(url: String) {
        let alert = UIAlertController(title: "Add feed", message: "", preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "Save", style: UIAlertActionStyle.default, handler: { (action) in
            let urlTextField = alert.textFields![0] as UITextField
            self.createFeed(url: urlTextField.text ?? "")
        }))
        alert.addAction(UIAlertAction(title: "Cancel", style: UIAlertActionStyle.default, handler: nil))
        alert.addAction(UIAlertAction(title: "Explore", style: UIAlertActionStyle.default, handler: { (action) in
            self.didTapExploreButton()
        }))
        alert.addTextField(configurationHandler: {(textField: UITextField!) in
            textField.placeholder = "Url"
            textField.text = url
            if(!url.isEmpty) {
                textField.layer.borderColor = UIColor.red.cgColor
                textField.rightView?.isHidden = false
            }
        })
        
        self.present(alert, animated: true, completion: nil)
    }
    
    private func didTapExploreButton(){
        let alert = UIAlertController(title: "Explore", message: "", preferredStyle: UIAlertControllerStyle.alert)
        
        ExploreFeed.Companion.init().exploreFeeds.forEach { (exploreFeed) in
            alert.addAction(UIAlertAction(title: exploreFeed.title, style: .default) { (_) in
                self.createFeed(url: exploreFeed.url)
            })
        }
        self.present(alert, animated: true, completion: nil)
    }
    
    private func createFeed(url: String) {
        api.createFeed(url: url, folderId: 0, callback: { () in
            self.tableView.refreshManually()
            return KotlinUnit()
        }, error: { () in
            self.didTapAddFeedButton(url: url)
            return KotlinUnit()
        })
    }
    
    private func fetchFeedData() {
        api.feeds(callback: { (feeds) in
            self.data = feeds
            self.tableView?.reloadData()
            self.refreshControl?.endRefreshing()
            return KotlinUnit()
        }) { () in
            return KotlinUnit()
        }
    }
}

class FeedTableViewCell: UITableViewCell {
    @IBOutlet weak var coverImageView: UIImageView!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var unreadCountLabel: UILabel!
}

extension UITableView
{
    func refreshManually() {
        self.setContentOffset(CGPoint(x:0, y:self.contentOffset.y - (self.refreshControl!.frame.size.height)), animated: true)
        self.refreshControl?.beginRefreshing()
    }
}
