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
    @IBOutlet weak var modeSegment: UISegmentedControl!
    @IBOutlet weak var urlTextHeight: NSLayoutConstraint!

    let modeNewsout = 0
    let modeNextcloud = 1
    var selectedMode = 0

    override func viewDidLoad() {
        super.viewDidLoad()

        self.loginButton.addTarget(self, action: #selector(didButtonClick), for: .touchUpInside)
        self.modeSegment.addTarget(self, action: #selector(self.modeApply), for: UIControl.Event.valueChanged)

        updateMode()
    }

    @objc func didButtonClick(_ sender: UIButton) {
        var url = urlText.text ?? ""
        if(selectedMode == modeNewsout) {
            url = "https://nx3217.your-next.cloud"
        }
        let email = emailText.text ?? ""
        let password = passwordText.text ?? ""

        let api = Api()

        api.login(url: url, email: email, password: password, callback: { (_) in
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

    @objc private func modeApply(segment: UISegmentedControl) {
        selectedMode = segment.selectedSegmentIndex
        updateMode()
    }

    private func updateMode() {
        switch selectedMode {
        case modeNewsout:
            urlTextHeight.constant = 0
            loginButton.setTitle("Login/Create", for: .normal)
            urlText.layoutIfNeeded()
        case modeNextcloud:
            urlTextHeight.constant = 44
            loginButton.setTitle("Login", for: .normal)
            urlText.layoutIfNeeded()
        default:
            break
        }
    }
}
