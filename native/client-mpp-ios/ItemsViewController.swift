/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

import UIKit

import main

class ItemsViewController: UITableViewController {
    let api = Api()
    let database = Database()
    var data = ([Item])()
    var feedId: Int64 = 0
    var type: Int64 = 0
    var rowHeights: [Int: CGFloat] = [:]
    var defaultHeight: CGFloat = 43
    var unreadMap: [Int64: Bool] = [:]
    var isReloadingData: Bool = false
    var isUserDragging = false

    override func viewDidLoad() {
        super.viewDidLoad()

        let refreshControl = UIRefreshControl()
        if #available(iOS 10.0, *) {
            tableView.refreshControl = refreshControl
        } else {
            tableView.addSubview(refreshControl)
        }
        refreshControl.addTarget(self, action: #selector(refreshItemData(_:)), for: .valueChanged)

        let markAsReadImage = UIImage(named: "icons8-checkmark")!

        let markAsReadButton = UIBarButtonItem(image: markAsReadImage, style: .plain, target: self, action: #selector(didTapMarkAsReadButton))

        navigationItem.rightBarButtonItems = [markAsReadButton]

        tableView.tableFooterView = UIView()

        let items = database.getItems(feedId: feedId, type: type) as! [Item]
        items.forEach { (item) in
            unreadMap[item.id] = item.isUnread == 1 ? true : false
        }
        self.data = items
        self.tableView?.reloadData()

        self.tableView.refreshManually()
        fetchItemData()
    }

    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) ->
    CGFloat {
        if let height = self.rowHeights[indexPath.row] {
            return height
        } else {
            return defaultHeight
        }
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "item", for: indexPath) as! ItemTableViewCell

        let item = data[indexPath.row]

        if(unreadMap[item.id] ?? false) {
            let attachment: NSTextAttachment = NSTextAttachment()
            attachment.image = UIImage(named: "icons8-bell")!
            attachment.bounds = CGRect(x: 0, y: -4, width: 20, height: 20)

            let attachmentString: NSAttributedString = NSAttributedString(attachment: attachment)
            let myString: NSMutableAttributedString = NSMutableAttributedString(string: item.title)
            myString.insert(attachmentString, at: 0)
            cell.titleLabel?.attributedText = myString
        } else {
            cell.titleLabel?.text = item.title
        }

        cell.titleLabel?.sizeToFit()
        let labelHeight = (cell.titleLabel?.bounds.size.height ?? 0) + 16
        self.rowHeights[indexPath.row] = labelHeight
        
        cell.coverImageView?.kf.setImage(with: URL(string: item.imageUrl)) { result in
            switch result {
            case .success(let value):
                if(self.rowHeights[indexPath.row] == labelHeight) {
                    let aspectRatio = value.image.size.height / value.image.size.width
                    let imageHeight = self.view.frame.width * aspectRatio
                    self.rowHeights[indexPath.row] = imageHeight
                    self.tableView.reloadRows(at: [indexPath], with: UITableView.RowAnimation.none)
                }
            case .failure(_): break
            }
        }

        return cell
    }

    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let item = data[indexPath.row]

        guard let url = URL(string: item.url) else { return }
        UIApplication.shared.open(url)
    }

    override func tableView(_ tableView: UITableView, didEndDisplaying cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        if(isReloadingData || !isUserDragging) {
            return
        }
        
        let item = data[indexPath.row]
        let id = item.id

        if(unreadMap[id] ?? false) {
            unreadMap[id] = false
            database.getItemQueries()?.markItemAsRead(id: id)
            database.getFeedQueries()?.decreaseUnreadCount(id: feedId, isFolder: type)
            api.markItemAsRead(itemId: id)
        }
    }
    
    override func scrollViewWillEndDragging(_ scrollView: UIScrollView, withVelocity velocity: CGPoint, targetContentOffset: UnsafeMutablePointer<CGPoint>) {
        isUserDragging = false
    }
    
    override func scrollViewWillBeginDragging(_ scrollView: UIScrollView) {
        isUserDragging = true
    }

    @objc private func refreshItemData(_ sender: Any) {
        fetchItemData()
    }

    private func fetchItemData() {
        api.getItems(id: feedId
                     , type: type
                     , offset: false
                     , callback: { (items) in
                         items.forEach { (item) in
                             self.unreadMap[item.id] = item.isUnread == 1 ? true : false
                         }
                         self.isReloadingData = true
                         self.data = items
                         self.tableView?.reloadData()
                         self.refreshControl?.endRefreshing()
                         self.isReloadingData = false
                         return KotlinUnit()
                     }) { () in
            self.refreshControl?.endRefreshing()
            return KotlinUnit()
        }
    }

    @objc func didTapMarkAsReadButton(sender: AnyObject) {
        switch type {
        case database.TYPE_FEED:
            api.markFeedAsRead(feedId: feedId, callback: { (items) in
                self.data = items
                self.tableView?.reloadData()
                return KotlinUnit()
            })
        case database.TYPE_FOLDER:
            api.markFolderAsRead(folderId: feedId, callback: { (items) in
                self.data = items
                self.tableView?.reloadData()
                return KotlinUnit()
            })
        default: break
        }
    }
}

class ItemTableViewCell: UITableViewCell {
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var coverImageView: UIImageView!
}
