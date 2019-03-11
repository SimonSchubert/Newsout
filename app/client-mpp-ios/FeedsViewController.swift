import UIKit

import main

/* Copyright 2019 Simon Schubert
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
