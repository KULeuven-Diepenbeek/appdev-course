---
title: 2. Common Errors FAQ
---

### 1. minSdkVersion mismatch

Error:

> "Expected minSdkVersion >= 21 but found 1"

Cause: mismatch in `build.gradle` of android SDK and configured SDK in your emulator.

Solution: just keep these the same. The error disappeared here after selecting for example 30/30.

### 2. Emulator process was killed?

Error:

> Emulator: process was killed

Cause: a hundred different things. Consult the Android Studio error log for more details---these aren't displayed in the IDE. Either pick Help -- show Log (or show Log in Finder if you're on a Mac), or locate it on the HDD (Mac: `~/Logs/Google/AndroidStudioPreviewX`, Win: `C:\Users\<name>\.AndroidStudioX\system\log`)

Find the process was killed message, and you'll likely see another error that caused that one. For instance:

- Too little disk space.
- Logged in as root user in Linux. `.ini` file could not be found.
- `x86` emulator installed on an `x64`-architecture system
- Apple M1 silicon-related errors if you did not install the latest preview edition.

### 3. Virtualization HAXVM-related errors

Error:

> Unable to install Intel® HAXM

Or: 

> Your CPU does not support VT-x.

Or:

> Unfortunately, your computer does not support hardware accelerated virtualization.

Cause 1: Are you on a Mac M1 and did you install the latest release instead of the preview release? You need to download an Android Virtual Device based on an ARM systems image. This is done automatically in the latest preview edition of Android Studio. See [installation guide](/extra/install).

Cause 2: Are you on a 64-bit system but did you install 32-bit emulators or components? Pay careful attention to the processor architecture of the installation and your machine---these should match!

### 4. Invalid Entry CRC while deploying

Error:

> Failed to transform material-1.3.0.aar invalid entry CRC expected 0x9797... but got 0x48546....

Cause: I have no idea, and Google/Stack Overflow doesn't seem to know either. It happens sometimes after building and deploying. The emulator boots, but the app won't load in the emulator. 

Solution: Rebuild. Do not restart the emulator, just leave it open. Simply rebuilding seems to fix this problem. 

### 5. LifecycleOwners must call register before they are STARTED.

Error:

> ... is attempting to register while current state is STARTED. LifecycleOwners must call register before they are STARTED.

Cause:

Are you calling `registerForActivityResult` in `onCreate()` using a callback of sorts? If so, your fragment/activity is still in STARTED state and should be in CREATED state for this particular intent to receive the results. Usually if you want to receive a camera image. 

Solution: move your listener outside of create scope (e.g. `onViewCreated()`).
