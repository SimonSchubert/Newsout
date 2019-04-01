[![GitHub](https://img.shields.io/github/license/SimonSchubert/NewsOut.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Codacy grade](https://img.shields.io/codacy/grade/86f6598a08a1476585b3b7dda5598360.svg)](https://app.codacy.com/project/sschubert89/NewsOut/dashboard)
[![Liberapay receiving](https://img.shields.io/liberapay/receives/arnald.svg)](https://liberapay.com/arnald/donate)

# Newsout

Android and iOS mobile client for Nextcloud news App.
The Android client is already available to download in the Play Store. F-Droid and Apple App Store will hopefully come soon.

[![Download](https://raw.githubusercontent.com/SimonSchubert/NewsOut/master/art/android_store_badge.png)](https://play.google.com/store/apps/details?id=com.inspiredandroid.newsout)

## Project structure

This project shares the Database, Api, Models and Serialization code. Only the interaction with the UI is done separately for iOS and Android.

<img src="https://raw.githubusercontent.com/SimonSchubert/NewsOut/master/art/project_structure.png" width="400">

## Android

<img src="https://raw.githubusercontent.com/SimonSchubert/NewsOut/master/art/android_screen_phone_01.png" width="200"> <img src="https://raw.githubusercontent.com/SimonSchubert/NewsOut/master/art/android_screen_phone_02.png" width="200"> <img src="https://raw.githubusercontent.com/SimonSchubert/NewsOut/master/art/android_screen_phone_03.png" width="200"> <img src="https://raw.githubusercontent.com/SimonSchubert/NewsOut/master/art/android_screen_phone_04.png" width="200">

<img src="https://raw.githubusercontent.com/SimonSchubert/NewsOut/master/art/android_screen_tablet_01.png" width="300"> <img src="https://raw.githubusercontent.com/SimonSchubert/NewsOut/master/art/android_screen_tablet_02.png" width="300">

## iOS

<img src="https://raw.githubusercontent.com/SimonSchubert/NewsOut/master/art/ios_screen_phone_01.png" width="200"> <img src="https://raw.githubusercontent.com/SimonSchubert/NewsOut/master/art/ios_screen_phone_02.png" width="200"> <img src="https://raw.githubusercontent.com/SimonSchubert/NewsOut/master/art/ios_screen_phone_03.png" width="200">

## Features

  - Sync of feeds and unread marker
  - List all starred/unread items
  - Change sorting order
  - Add new feeds
  - Rename feeds/folders
  - Delete feeds/folders
  - Star/unstar items
  - Mark all feeds or a single feed as read
  - Explore popular feeds

Android strings are hardcoded until I find a nice multiplatform solution.

Compare Dropbox/Google Drive/Nextcloud: [https://nextcloud.com/compare](https://nextcloud.com/compare) 
