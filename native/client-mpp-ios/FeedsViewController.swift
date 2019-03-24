import UIKit
import Kingfisher

import main

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
        
        let database = Database()
        self.data = database.getFeedQueries()?.selectAllByTitle().executeAsList() as! [Feed]
        self.tableView?.reloadData()
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
        cell.coverImageView?.kf.setImage(with: URL(string: item.faviconUrl))
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
            }
        }
    }
    
    @objc private func refreshFeedData(_ sender: Any) {
        fetchFeedData()
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
