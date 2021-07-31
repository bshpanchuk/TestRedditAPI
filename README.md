
# Test App Yalantis

Test App Android Junior to display [Reddit API](https://www.reddit.com/dev/api/) it has been built with clean architecture principles, Repository Pattern and MVVM
pattern as well as Architecture Components.

Build System : [Gradle](https://gradle.org/)

## Table of Contents

- [Prerequisite](#prerequisite)
- [Task requirements](#task-requirements)
- [Architecture](#architecture)
- [Libraries](#libraries)
- [Demo](#demo)

## Task requirements
- App show be able to shows th top 50 entries from RedditApi
- Pagination
- Detailed post preview (opening URL would be enough)
- Kotlin
-	RecyclerView
-	Chrome Tabs
-	MVP/MVVM architecture + Repository pattern
-	Retrofit 2 + RxJava / RxJava2
-	Some kind of error handling
-	Database storage

## Architecture

The purpose of this repo is to follow up Clean Architecture principles by bringing them to Android.

## Libraries

Libraries used in the whole application are:

- [Jetpack](https://developer.android.com/jetpack)🚀
  - [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI related data in a lifecycle conscious way 
  and act as a channel between use cases and ui
  - [View Binding](https://developer.android.com/topic/libraries/view-binding) -  feature that allows you to more easily write code that interacts with views
  - [Room](https://developer.android.com/jetpack/androidx/releases/room) -  provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite
  - [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) -  helps you load and display pages of data from a larger dataset from network
- [Retrofit](https://square.github.io/retrofit/) - type safe http client 
and supports coroutines out of the box
- [Gson](https://github.com/google/gson) - JSON Parser,used to parse 
requests on the data layer for Entities and understands Kotlin non-nullable 
and default parameters
- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - logs HTTP request and response data
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines
- [Material Design](https://material.io/develop/android/docs/getting-started/) - build awesome beautiful UIs
- [Koin](https://github.com/InsertKoinIO/koin) - A pragmatic lightweight dependency injection framework for Kotlin
- [ViewBindingPropertyDelegate](https://github.com/kirich1409/ViewBindingPropertyDelegate) - Make work with Android View Binding simpler
- [RxJava2](https://github.com/ReactiveX/RxJava) -  implementation of Reactive Extensions: a library for composing asynchronous and event-based programs by using observable sequences
- [Glide](https://github.com/bumptech/glide) -  fast and efficient open source media management and image loading framework for Android


## Demo

|<img src="art/screen1.jpg" width=200/>|<img src="art/screen2.jpg" width=200/>|
|:----:|:----:|


## License

 ```
   Copyright 2021 Bohdan Shpanchuk
   
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 ```
