package com.inspiredandroid.purenews.models

class User {
    var mode: Int = NONE

    companion object {
        const val NONE = 0
        const val OFFLINE = 1
        const val NEXTCLOUD = 2
    }
}
