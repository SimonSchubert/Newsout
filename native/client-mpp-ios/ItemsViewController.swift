/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

import UIKit

import main

class ItemsViewController: UITableViewController {
    let api = Api()
    var data = ([Item])()
    var itemId: Int64 = 0
    var type: Int64 = 0

    override func viewDidLoad() {
        super.viewDidLoad()

        let refreshControl = UIRefreshControl()
        if #available(iOS 10.0, *) {
            tableView.refreshControl = refreshControl
        } else {
            tableView.addSubview(refreshControl)
        }
        refreshControl.addTarget(self, action: #selector(refreshItemData(_:)), for: .valueChanged)

        tableView.tableFooterView = UIView()

        let database = Database()
        self.data = database.getItems(feedId: itemId, type: type) as! [Item]
        self.tableView?.reloadData()

        self.tableView.refreshManually()
        fetchItemData()
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "item", for: indexPath) as! ItemTableViewCell

        let item = data[indexPath.row]

        cell.titleLabel?.text = item.title
        cell.coverImageView?.kf.setImage(with: URL(string: item.imageUrl))
        cell.accessoryType = .disclosureIndicator

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
        api.getItems(id: itemId
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
}

class ItemTableViewCell: UITableViewCell {
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var coverImageView: UIImageView!
}
