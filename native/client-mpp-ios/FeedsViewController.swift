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
    let database = Database()
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

        let settingsImage = UIImage(named: "icons8-settings3")!
        let markAsReadImage = UIImage(named: "icons8-checkmark")!

        let markAsReadButton = UIBarButtonItem(image: markAsReadImage, style: .plain, target: self, action: #selector(didTapMaekAsReadButton))
        let settingsButton = UIBarButtonItem(image: settingsImage, style: .plain, target: self, action: #selector(didTapSettingsButton))
        
        navigationItem.rightBarButtonItems = [markAsReadButton, settingsButton]

        tableView.tableFooterView = UIView()

        self.data = database.getFeeds() as! [Feed]
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
                vc?.feedId = feed?.id ?? 0
                vc?.type = feed?.isFolder ?? 0
                vc?.title = feed?.title ?? ""
            }
        }
    }

    @objc private func refreshFeedData(_ sender: Any) {
        fetchFeedData()
    }

    @objc func didTapAddButton(sender: AnyObject) {
        didTapAddFeedButton(url: "")
    }
    
    @objc func didTapMaekAsReadButton(sender: AnyObject) {
        // didTapAddFeedButton(url: "")
    }

    private func logout() {
        KeychainWrapper.standard.set("", forKey: "SERVER")
        KeychainWrapper.standard.set("", forKey: "EMAIL")
        KeychainWrapper.standard.set("", forKey: "PASSWORD")

        database.clear()

        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        let storyboard = UIStoryboard.init(name: "Main", bundle: nil)
        let nav = storyboard.instantiateViewController(withIdentifier: "login")
        appDelegate.window?.rootViewController = nav
    }

    private func didTapAddFeedButton(url: String) {
        let alert = UIAlertController(title: "Add feed", message: "", preferredStyle: UIAlertController.Style.alert)
        alert.addAction(UIAlertAction(title: "Save", style: UIAlertAction.Style.default, handler: { (_) in
            let urlTextField = alert.textFields![0] as UITextField
            self.createFeed(url: urlTextField.text ?? "")
        }))
        alert.addAction(UIAlertAction(title: "Explore", style: UIAlertAction.Style.default, handler: { (_) in
            self.didTapExploreButton()
        }))
        alert.addAction(UIAlertAction(title: "Cancel", style: UIAlertAction.Style.cancel, handler: nil))
        alert.addTextField(configurationHandler: { (textField: UITextField!) in
            textField.placeholder = "Url"
            textField.text = url
            if(!url.isEmpty) {
                textField.layer.borderColor = UIColor.red.cgColor
                textField.rightView?.isHidden = false
            }
        })

        self.present(alert, animated: true, completion: nil)
    }

    private func didTapExploreButton() {
        let alert = UIAlertController(title: "Explore", message: "", preferredStyle: UIAlertController.Style.alert)

        ExploreFeed.Companion.init().exploreFeeds.forEach { (exploreFeed) in
            alert.addAction(UIAlertAction(title: exploreFeed.title, style: .default) { (_) in
                self.createFeed(url: exploreFeed.url)
            })
        }
        alert.addAction(UIAlertAction(title: "Cancel", style: UIAlertAction.Style.cancel, handler: nil))

        self.present(alert, animated: true, completion: nil)
    }

    private func createFeed(url: String) {
        self.tableView.refreshManually()
        api.createFeed(url: url, folderId: 0, callback: { () in
            self.fetchFeedData()
            return KotlinUnit()
        }, error: { () in
            self.didTapAddFeedButton(url: url)
            self.refreshControl?.endRefreshing()
            return KotlinUnit()
        })
    }

    private func fetchFeedData() {
        api.getFeeds(callback: { (feeds) in
            self.data = feeds
            self.tableView?.reloadData()
            self.refreshControl?.endRefreshing()
            return KotlinUnit()
        }, error: { () -> KotlinUnit in
            self.refreshControl?.endRefreshing()
            return KotlinUnit()
        }) { () -> KotlinUnit in
            self.logout()
            return KotlinUnit()
        }
    }

    @objc func didTapSettingsButton(sender: AnyObject) {
        let alert = UIAlertController(title: "Settings", message: "", preferredStyle: UIAlertController.Style.alert)

        alert.addAction(UIAlertAction(title: "Add feed", style: UIAlertAction.Style.default, handler: { (_) in
            self.didTapAddFeedButton(url: "")
        }))
        alert.addAction(UIAlertAction(title: "Logout", style: UIAlertAction.Style.destructive, handler: { (_) in
            self.logout()
        }))
        alert.addAction(UIAlertAction(title: "Cancel", style: UIAlertAction.Style.cancel, handler: {
            (alertAction: UIAlertAction!) in
            alert.dismiss(animated: true, completion: nil)
        }))


        let user = database.getUser()

        let folderSwitch = createFolderSwitch(user: user)
        alert.view.addSubview(folderSwitch)

        alert.view.layoutIfNeeded()

        folderSwitch.centerXAnchor.constraint(equalTo: alert.view.centerXAnchor).isActive = true
        folderSwitch.topAnchor.constraint(equalTo: alert.view.topAnchor, constant: alert.view.bounds.height + 10).isActive = true

        let sortingSegment = createSortingSegment(user: user)
        alert.view.addSubview(sortingSegment)

        alert.view.layoutIfNeeded()

        sortingSegment.centerXAnchor.constraint(equalTo: alert.view.centerXAnchor).isActive = true
        sortingSegment.topAnchor.constraint(equalTo: alert.view.topAnchor, constant: alert.view.bounds.height + folderSwitch.bounds.height + 20).isActive = true

        alert.view.layoutIfNeeded()

        let height: CGFloat = alert.view.bounds.height + CGFloat(52)*3 + folderSwitch.bounds.size.height +
            sortingSegment.bounds.size.height + 30
        alert.view.heightAnchor.constraint(equalToConstant: height).isActive = true

        self.present(alert, animated: true, completion: nil)
    }

    func createSortingSegment (user: User?) -> UIStackView {
        let label = UILabel()
        label.text = "Sorting"
        label.sizeToFit()

        let items = ["Unread count", "Alphabetically"]
        let sortingSegment = UISegmentedControl(items: items)
        sortingSegment.frame = CGRect.init(x: 0, y: 0, width: 200, height: 30)
        sortingSegment.selectedSegmentIndex = user?.sorting == database.SORT_UNREADCOUNT ? 0 : 1
        sortingSegment.tintColor = UIColor.black
        sortingSegment.addTarget(self, action: #selector(self.sortingApply), for: UIControl.Event.valueChanged)

        let stackView = UIStackView()
        stackView.axis = .vertical
        stackView.spacing = 8

        stackView.addArrangedSubview(label)
        stackView.addArrangedSubview(sortingSegment)

        stackView.translatesAutoresizingMaskIntoConstraints = false
        stackView.layoutIfNeeded()

        return stackView
    }

    func createFolderSwitch (user: User?) -> UIStackView {
        let label = UILabel()
        label.text = "Folders always at top"
        label.sizeToFit()

        let folderSwitch = UISwitch()
        folderSwitch.setOn(user?.isFolderTop == 1 ? true : false, animated: true)

        folderSwitch.addTarget(self, action: #selector(self.setFolderFirst(_:)), for: .valueChanged)

        let stackView = UIStackView()
        stackView.axis = .horizontal
        stackView.spacing = 8

        stackView.addArrangedSubview(label)
        stackView.addArrangedSubview(folderSwitch)

        stackView.translatesAutoresizingMaskIntoConstraints = false
        stackView.layoutIfNeeded()

        return stackView
    }

    @objc private func sortingApply(segment: UISegmentedControl) -> Void {
        switch segment.selectedSegmentIndex {
        case 0:
            database.getUserQueries()?.updateSorting(sorting: database.SORT_UNREADCOUNT)
        case 1:
            database.getUserQueries()?.updateSorting(sorting: database.SORT_TITLE)
        default:
            break
        }

        self.data = database.getFeeds() as! [Feed]
        self.tableView?.reloadData()
    }

    @objc func setFolderFirst(_ folderSwitch: UISwitch?) {
        database.getUserQueries()?.updateFolderTop(isFolderTop: folderSwitch?.isOn ?? true ? 1 : 0)
        self.data = database.getFeeds() as! [Feed]
        self.tableView?.reloadData()
    }

    @objc func dismissAlertController() {
        self.dismiss(animated: true, completion: nil)
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
        self.setContentOffset(CGPoint(x: 0, y: self.contentOffset.y - (self.refreshControl!.frame.size.height)), animated: true)
        self.refreshControl?.beginRefreshing()
    }
}
