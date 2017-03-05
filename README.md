# Plan-B Android Crash Recovery Lib

**THIS IS WORK IN PROGRESS.**

Planb is a crash recovery lib for Android. It helps defining proper behaviour on [uncaught exceptions](http://www.javamex.com/tutorials/exceptions/exceptions_uncaught_handler.shtml) like suppressing the OS dialog or showing an activity with additional debugging info for debug and production builds. The lib also persists crashes locally, so it is possible to view them later. This is specifically convenient when multiple people are testing the app. This is thought to extend crash reporting frameworks like [hockeyapp](https://hockeyapp.net/) or [crashlytics](https://try.crashlytics.com/).

The lib contains of a `core` library and a full version containing default implementations for crash Activity and explorer.

[![Download](https://api.bintray.com/packages/patrickfav/maven/planb/images/download.svg) ](https://bintray.com/patrickfav/maven/planb/_latestVersion)
[![Build Status](https://travis-ci.org/patrickfav/planb-android.svg?branch=master)](https://travis-ci.org/patrickfav/planb-android)
[![play store banner](doc/playstore_badge_new_sm.png)](https://play.google.com/store/apps/details?id=at.favre.app.planb.demo)

![Screenshot Gallery](doc/screenshot_gallery.png)

## Quick Start

Add the following to your dependencies ([add jcenter to your repositories](https://developer.android.com/studio/build/index.html#top-level) if you haven't)

```gradle
compile 'at.favre.lib:planb:x.y.z'
```

In your [`Application`](https://developer.android.com/reference/android/app/Application.html) initialize PlanB

```Java
@Override
public void onCreate() {
    super.onCreate();
    PlanB.get().init(true,
        PlanB.newConfig(this)
             .applicationVariant(BuildConfig.BUILD_TYPE, BuildConfig.FLAVOR).build());
}
```

Then either in your `Activity.onCreate()` or in the `Application` itself enable the handler

```Java
PlanB.get().enableCrashHandler(this,
    PlanB.behaviourFactory(.createRestartForegroundActivityCrashBehaviour());
```

# Integration with Crash Frameworks

Basically every crash framework sets itself as the default uncaught exception handler (see [`Thread.UncaughtExceptionHandler`](https://docs.oracle.com/javase/7/docs/api/java/lang/Thread.UncaughtExceptionHandler.html). However the `Thread` object only allows a single handler at a time and there is no support of proper chaining the handlers. Therefore there is no elegant way to use multiple crash frameworks, it usually boils down to the when which framework reads or sets the default handler.

## HockeyApp

## Crashlytics

## Acra

## Similar Projects:

* [Recovery](https://github.com/Sunzxyong/Recovery)

# License

Copyright 2017 Patrick Favre-Bulle

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.