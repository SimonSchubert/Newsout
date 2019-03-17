import UIKit

import main

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
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
