//
//  UiLabelPadding.swift
//  client-mpp-ios
//
//  Created by Simon Schubert on 11.05.19.
//  Copyright © 2019 ktor. All rights reserved.
//

import Foundation
import UIKit

class UILabelPadding: UILabel {
    
    let padding = UIEdgeInsets(top: 8, left: 8, bottom: 8, right: 8)
    override func drawText(in rect: CGRect) {
        super.drawText(in: rect.inset(by: padding))
    }
    
    override var intrinsicContentSize : CGSize {
        let superContentSize = super.intrinsicContentSize
        let width = superContentSize.width + padding.left + padding.right
        let heigth = superContentSize.height + padding.top + padding.bottom
        return CGSize(width: width, height: heigth)
    }

}
