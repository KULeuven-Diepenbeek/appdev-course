---
title: 5. Multiplatform Mobile dev.
---

{{% notice note %}}
This small Section is **completely optional**, and not part of the core contents of this course. It showcases other possibilities for mobile app development, broken free from the solitary confinements of Android native code. 
{{% /notice %}}

## 1. Other Native Solutions

Not everyone buys Android-based phones. Apple's iPhone is arguably (much) more popular, and is based on iOS. Developing apps for iOS is a totally different development experience: it requires other tools, another language, and other deployment chains. This is a _bad thing_: it means you will have to learn to live in both worlds if you intend to release your software on both operating systems!

Development of iOS apps is done through [the Xcode IDE](https://developer.apple.com/xcode/), a proprietary IDE that only runs on MacOS. That's right, you'll need a Mac machine to build and deploy native iOS apps. 

To program iOS apps in Xcode, you write code [in Swift](https://learnappmaking.com/learn-swift-programming/), a programming language created by Apple in 2014 built on top of the LLVM environment. Swift serves as a replacement for Apple's older Objective-C that originated in the eighties. One of its strengths is its flawless interoperability with Objective-C, like Kotlin has with Java. Swift is also statically typed and also uses smart inferred typing. They both are relatively modern languages. A few similarities and differences:

<div class="devselect">

```kt
val immutable = 55
var somevar: Double = 33.0

fun greet(name: String, day: String): String {
    return "Hello $name, today is $day."
}
```

```swift
let immutable = 55
var somevar: Double = 33

func greet(_ name: String,_ day: String) -> String {
    return "Hello \(name), today is \(day)."
}
```

</div>

And then there's [SwiftUI](https://learnappmaking.com/swiftui-getting-started-how-to-ios-swift/):

> SwiftUI is a framework to build User Interfaces (UI) for iOS apps. With SwiftUI, you create the UIs of your iOS apps entirely with Swift code, using a novel declarative approach.

We would have loved to introduce you to the world of iOS development for a single class, but that would mean everyone must have access to Apple hardware. 

Still, if you're serious about app development, the question isn't "which language will I learn" but "which will I learn first". You simply cannot ignore the iPhone market!

## 2. Multiplatform Mobile

### 2.1 The Kotlin Way: KMM

To ease the development pains that come from maintaining two different code bases in two different languages, using two different IDEs, Kotlin introduced the KMM, or the _Kotlin Multiplatform Mobile_ system. [Get familiar with KMM here](https://kotlinlang.org/docs/mobile/getting-started.html). 

In essence, KMM enables you to write and maintain a shared code base for your domain logic, while it splits the UI logic to both OSes' unique capabilities, written either in Android's Activity/Fragment/Intent system, or using SwiftUI. Your code base will look like this:

```
src
    | androidMain
        | MyActivity.kt
    | androidTest
    | commonMain
        | MyObject.kt
    | commonTest
    | iosMain
        | MySwiftUIWindow.swift
    | iosTest
```

![](/img/kmm.png "The KMM platform: shared Kotlin business logic takes the center.")

KMM will _scaffold_ certain Swift files for you: it will generate parts of the Swift code, making it easier for the developer to build a single interface for both OSes. See the [make your android application work on iOS tutorial](https://kotlinlang.org/docs/mobile/integrate-in-existing-app.html#enjoy-the-results-update-the-logic-only-once) for more in-depth information. KMM's [case studies](https://kotlinlang.org/lp/mobile/case-studies/), and especially the [Netflix migration blog post](https://netflixtechblog.com/netflix-android-and-ios-studio-apps-kotlin-multiplatform-d6d4d8d25d23) are interesting reads. 

You will still need access to both the Xcode and Android Studio toolchain. Therefore, we will again leave it up to the curious student with access to a Mac to discover KMM's capabilities. 

### 2.2 The Microsoft way: Xamarin

Microsoft developed the [freely available Xamarin framework](https://dotnet.microsoft.com/apps/xamarin), which is:

> Free. Cross-platform. Open source.<br/>
An app platform for building Android and iOS apps with .NET and C#

Using Xamarin, you can develop your apps in C# but still deploy on iOS and Android. It is fully cross-platform, and still builds natively. As the site states, _Anything you can do in Objective-C or Java, you can do in C#_. Looking a bit further, you will still notice [two separate docs pages](https://dotnet.microsoft.com/apps/xamarin/mobile-apps) for iOS and Android, as the namespaces and framework libs differ quite a bit. The language might be the same, but the underlying principles are not. This is in line with what KMM tries to do. 

## 3. Mobile Web Tools

Both leaning on Kotlin/Swift and KMM requires heavy investments. What if you could develop mobile apps in a language you're already familiar with? Many companies opt for a JavaScript-powered solution instead, as they employ web developers that not necessarily need to take the time to learn a new web-based framework. This is a _rapidly changing_ environment, meaning many frameworks came and went: 

- [React Native](https://reactnative.dev/) (hot and relatively new from Facebook)
- [Apache Cordova](http://cordova.apache.org/) (almost considered obsolete now)
- [Ionic Mobile](https://ionicframework.com/) (in case you're tired of React and want things in Vue.js instead)
- [jQuery Mobile](https://jquerymobile.com/) (_very_ ancient now)

Web development techniques will be explained in detail in the cloud computing course in the master year. 

