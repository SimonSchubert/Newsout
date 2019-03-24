import UIKit

import main

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
class ViewController: UIViewController {

    @IBOutlet weak var loginButton: UIButton!
    @IBOutlet weak var urlText: UITextField!
    @IBOutlet weak var emailText: UITextField!
    @IBOutlet weak var passwordText: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.loginButton.addTarget(self, action: #selector(didButtonClick), for: .touchUpInside)
        
        let database = Database()
        database.setup()
    }
    
    @objc func didButtonClick(_ sender: UIButton) {
        let url = urlText.text ?? ""
        let email = emailText.text ?? ""
        let password = passwordText.text ?? ""
        
        let api = Api()
        
        api.login(url: url, email: email, password: password, callback: { (version) in
            print("Version: " + version.version!)
            let feedsVc = self.storyboard?.instantiateViewController(withIdentifier: "feeds") as! FeedsViewController
            self.present(feedsVc, animated: true, completion: nil)
            return KotlinUnit()
        }, unauthorized: { () in
            return KotlinUnit()
        }, error: { () in
            return KotlinUnit()
        })
    }
}
