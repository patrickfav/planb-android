# Plan-B Android Crash Recovery Lib


**THIS IS WORK IN PROGRESS.**


A crash recovery lib

[![Download](https://api.bintray.com/packages/patrickfav/maven/planb/images/download.svg) ](https://bintray.com/patrickfav/maven/planb/_latestVersion)
[![Build Status](https://travis-ci.org/patrickfav/planb-android.svg?branch=master)](https://travis-ci.org/patrickfav/planb-android)
[![play store banner](doc/playstore_badge_new_sm.png)](https://play.google.com/store/apps/details?id=at.favre.app.planb.demo)

## Quick Start

Add the following to your dependencies ([add jcenter to your repositories](https://developer.android.com/studio/build/index.html#top-level) if you haven't)

```gradle
compile 'at.favre.lib:planb:x.y.z'
```

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



# Bugreport

## Testobject

### App Version

Timestamp: 17.07.2017

Version: v0.1.0 (34) / debug:psa

SCM: af762993 / develop

CI: 567 / Job

### Device

Model: Nexus 6P (angler)

Android: 7.1.1 (SDK 25)

Serial: EF00726464673

## Description

**Preconditions:**

**Observed Behaviour:**

**Expected:**

## Stacktrace

**java.lang.NullPointerException**

```
-07 20:10:18.440 2146-11731/com.google.android.googlequicksearchbox:search W/ErrorProcessor: onFatalError, processing error from engine(4)
                                                                                               com.google.android.apps.gsa.shared.speech.a.g: Error reading from input stream
                                                                                                   at com.google.android.apps.gsa.staticplugins.recognizer.i.a.a(SourceFile:342)
                                                                                                   at com.google.android.apps.gsa.staticplugins.recognizer.i.a$1.run(SourceFile:1367)
                                                                                                   at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:428)
                                                                                                   at java.util.concurrent.FutureTask.run(FutureTask.java:237)
                                                                                                   at com.google.android.apps.gsa.shared.util.concurrent.a.ak.run(SourceFile:66)
                                                                                                   at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1133)
                                                                                                   at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:607)
                                                                                                   at java.lang.Thread.run(Thread.java:761)
                                                                                                   at com.google.android.apps.gsa.shared.util.concurrent.a.ad$1.run(SourceFile:85)
                                                                                                Caused by: com.google.android.apps.gsa.shared.exception.GsaIOException: Error code: 393238 | Buffer overflow, no available space.
                                                                                                   at com.google.android.apps.gsa.speech.audio.Tee.g(SourceFile:2531)
                                                                                                   at com.google.android.apps.gsa.speech.audio.ap.read(SourceFile:555)
                                                                                                   at java.io.InputStream.read(InputStream.java:101)
                                                                                                   at com.google.android.apps.gsa.speech.audio.al.run(SourceFile:362)
                                                                                                   at com.google.android.apps.gsa.speech.audio.ak$1.run(SourceFile:471)
                                                                                                   at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:428)
                                                                                                   at java.util.concurrent.FutureTask.run(FutureTask.java:237)
                                                                                                   at com.google.android.apps.gsa.shared.util.concurrent.a.ak.run(SourceFile:66)
                                                                                                   at com.google.android.apps.gsa.shared.util.concurrent.a.ax.run(SourceFile:139)
                                                                                                   at com.google.android.apps.gsa.shared.util.concurrent.a.ax.run(SourceFile:139)
                                                                                                   at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1133) 
                                                                                                   at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:607) 
                                                                                                   at java.lang.Thread.run(Thread.java:761) 
                                                                                                   at com.google.android.apps.gsa.shared.util.concurrent.a.ad$1.run(SourceFile:85)
```