import UIKit

import main

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
class FeedsViewController: UITableViewController {
    let api = ApplicationApi()
    var data = ([NextcloudNewsFeed])()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        api.feeds { (feeds) in
            self.data = feeds
            self.tableView?.reloadData()
            print("feeds: \(self.data.count)")
            return KotlinUnit()
        }
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "basic", for: indexPath)
        let item = data[indexPath.row]
        
        cell.textLabel?.text = item.title
        
        return cell
    }
}