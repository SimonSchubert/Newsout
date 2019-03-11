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
class ViewController: UIViewController {
    let api = ApplicationApi()

    @IBOutlet weak var loginButton: UIButton!

    override func viewDidLoad() {
        super.viewDidLoad()
        
    self.loginButton.addTarget(self, action: #selector(didButtonClick), for: .touchUpInside)
    }
    
    @objc func didButtonClick(_ sender: UIButton) {
        api.version { (version) in
            print("Version: " + version.version!)
            
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "feeds") as! FeedsViewController
            self.present(vc, animated: true, completion: nil)
            
            return KotlinUnit()
        }
    }
}
