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
    @IBOutlet weak var activityIndicator: UIActivityIndicatorView!

    let modeNewsout = 0
    let modeNextcloud = 1
    var selectedMode = 0

    let api = Api()

    override func viewDidLoad() {
        super.viewDidLoad()

        self.loginButton.addTarget(self, action: #selector(didButtonClick), for: .touchUpInside)
        self.modeSegment.addTarget(self, action: #selector(self.modeApply), for: UIControl.Event.valueChanged)

        updateMode()
    }

    @objc func didButtonClick(_ sender: UIButton) {
        if (selectedMode == modeNewsout) {
            attemptSignup()
        } else {
            attemptLogin()
        }
    }

    private func attemptLogin() {
        var url = urlText.text ?? ""
        if(selectedMode == modeNewsout) {
            url = "https://nx3217.your-next.cloud"
        }
        let email = emailText.text ?? ""
        let password = passwordText.text ?? ""

        showLoading()

        api.login(url: url, email: email, password: password, callback: { (_) in
            self.saveLogin(url: url, email: email, password: password)
            let navigationVc = self.storyboard?.instantiateViewController(withIdentifier: "navigation")
            self.present(navigationVc!, animated: true, completion: nil)
            return KotlinUnit()
        }, unauthorized: { () in
            self.hideLoading()
            let alert = UIAlertController(title: "Password or email might be wrong", message: "", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
            self.present(alert, animated: true)
            return KotlinUnit()
        }, error: { () in
            self.hideLoading()
            let alert = UIAlertController(title: "Could not connect", message: "", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
            self.present(alert, animated: true)
            return KotlinUnit()
        })
    }

    private func attemptSignup() {
        let url = "https://nx3217.your-next.cloud"
        let email = emailText.text ?? ""
        let password = passwordText.text ?? ""

        if(!ExtensionFunctionsKt.isEmailValid(email)) {
            let alert = UIAlertController(title: "Please enter a correct email address", message: "", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
            self.present(alert, animated: true)
            return;
        }

        if (password.count < 6) {
            let alert = UIAlertController(title: "Password is too short", message: "", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
            self.present(alert, animated: true)
            return
        }

        showLoading()

        api.createAccount(url: url, email: email, password: password, success: { () -> KotlinUnit in
            self.saveLogin(url: url, email: email, password: password)
            let navigationVc = self.storyboard?.instantiateViewController(withIdentifier: "navigation")
            self.present(navigationVc!, animated: true, completion: nil)
            return KotlinUnit()
        }, userExists: { () -> KotlinUnit in
            self.attemptLogin()
            return KotlinUnit()
        }) { () -> KotlinUnit in
            let alert = UIAlertController(title: "Try again with a different password", message: "", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
            self.present(alert, animated: true)
            return KotlinUnit()
        }
    }

    private func hideLoading() {
        self.activityIndicator.isHidden = true
        self.loginButton.isHidden = false
    }

    private func showLoading() {
        activityIndicator.isHidden = false
        loginButton.isHidden = true
    }

    private func saveLogin(url: String, email: String, password: String) {
        KeychainWrapper.standard.set(url, forKey: "SERVER")
        KeychainWrapper.standard.set(email, forKey: "EMAIL")
        KeychainWrapper.standard.set(password, forKey: "PASSWORD")
    }

    @objc private func modeApply(segment: UISegmentedControl) {
        selectedMode = segment.selectedSegmentIndex
        updateMode()
    }

    private func updateMode() {
        switch selectedMode {
        case modeNewsout:
            urlTextHeight.constant = 0
            loginButton.setTitle("Login/Signup", for: .normal)
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
