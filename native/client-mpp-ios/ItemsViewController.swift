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

        let markAsReadButton = UIBarButtonItem(image: markAsReadImage, style: .plain, target: self, action: #selector(didTapMaekAsReadButton))

        navigationItem.rightBarButtonItems = [markAsReadButton]

        tableView.tableFooterView = UIView()

        self.data = database.getItems(feedId: feedId, type: type) as! [Item]
        self.tableView?.reloadData()

        self.tableView.refreshManually()
        fetchItemData()
    }

    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
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

        if(item.isUnread == 1) {
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
        self.rowHeights[indexPath.row] = (cell.titleLabel?.bounds.size.height ?? 0) + 16

        cell.coverImageView?.kf.setImage(with: URL(string: item.imageUrl)) { result in
            switch result {
            case .success(let value):
                let aspectRatio = value.image.size.height / value.image.size.width
                let imageHeight = self.view.frame.width * aspectRatio
                tableView.beginUpdates()
                self.rowHeights[indexPath.row] = imageHeight
                tableView.endUpdates()
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

    @objc private func refreshItemData(_ sender: Any) {
        fetchItemData()
    }

    private func fetchItemData() {
        api.getItems(id: feedId
                     , type: type
                     , offset: false
                     , callback: { (items) in
                         self.data = items
                         self.tableView?.reloadData()
                         self.refreshControl?.endRefreshing()
                         return KotlinUnit()
                     }) { () in
            self.refreshControl?.endRefreshing()
            return KotlinUnit()
        }
    }

    @objc func didTapMaekAsReadButton(sender: AnyObject) {
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
