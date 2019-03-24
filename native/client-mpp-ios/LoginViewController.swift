import UIKit

import main
import SwiftKeychainWrapper

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */
class LoginViewController: UIViewController {

    @IBOutlet weak var loginButton: UIButton!
    @IBOutlet weak var urlText: UITextField!
    @IBOutlet weak var emailText: UITextField!
    @IBOutlet weak var passwordText: UITextField!

    override func viewDidLoad() {
        super.viewDidLoad()

        self.loginButton.addTarget(self, action: #selector(didButtonClick), for: .touchUpInside)
    }

    @objc func didButtonClick(_ sender: UIButton) {
        let url = urlText.text ?? ""
        let email = emailText.text ?? ""
        let password = passwordText.text ?? ""

        let api = Api()

        api.login(url: url, email: email, password: password, callback: { (version) in
            KeychainWrapper.standard.set(url, forKey: "SERVER")
            KeychainWrapper.standard.set(email, forKey: "EMAIL")
            KeychainWrapper.standard.set(password, forKey: "PASSWORD")
            let navigationVc = self.storyboard?.instantiateViewController(withIdentifier: "navigation")
            self.present(navigationVc!, animated: true, completion: nil)
            return KotlinUnit()
        }, unauthorized: { () in
            return KotlinUnit()
        }, error: { () in
            return KotlinUnit()
        })
    }
}
