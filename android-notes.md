# Android Emulator M1 Preview

This is a preview of some basic Android emulation functionality on the M1. There are still many issues, but apps work at a basic level. To be updated soon with more fixes. The release tag corresponds to this commit: <https://android.googlesource.com/platform/external/qemu/+/aca144a9e9264b11c2d729096af90d695d01455d>

## Linux x64 specifiek

- niet opstarten als root: in /root/android emulators, kan niet gevonden worden fout
- permission denied /dev/kvm: useradd <user> kvm groep. zie https://developer.android.com/studio/run/emulator-acceleration voor juiste packages te installeren. `ia32-libs-multiarch` bestaat blijkbaar niet meer en is niet nodig?
- "Expected minSdkVersion >= 21 but found 1": mismatch build.gradle android sdk en ingesteld in emulator. best gewoon hetzelfde houden, fout verdween na 30/30 te pakken?

> Emulator: process was killed

Oorzaak: honderd-en-een dingen: zie log. 

- bvb zelfs disk space. 
- als root ingelogd, .ini file niet gevonden door dat die ergens anders zit? 
- x86 emulator ipv x64 geïnstalleerd
- M1 shit. zie onder

## Kotlin Multiplatform Mobile (KMM)

https://kotlinlang.org/docs/mobile/getting-started.html

Te moeilijk omdat voor iOS ook een iOS device + Mac nodig is (Xcode)? Compileert naar Kotlin/Native via LLVM, maar Xcode tools nodig. 

Ook nog eens KMM plugin nodig in Android studio

## installatie Anrdoid Studio

https://developer.android.com/studio/ (4.2.1)
Dat is maar de installer: daarna default opties aan laten staan, next next next, duurt nog +15min voor install compleet is

### M1 issues:

Unable to install Intel® HAXM
Your CPU does not support VT-x.
Unfortunately, your computer does not support hardware accelerated virtualization.
Here are some of your options:
 1) Use a physical device for testing
 2) Develop on a Windows/OSX computer with an Intel processor that supports VT-x and NX
 3) Develop on a Linux computer that supports VT-x or SVM
 4) Use an Android Virtual Device based on an ARM system image
   (This is 10x slower than hardware accelerated virtualization)

## Eerste project

Hoeveel disk space int total?

Welke versie van Android ondersteunen? Gekozen voor Oreo. 

Initiele indexatie JDK/gradle download: duurt nog erg lang. Zie onderaan "Gradle sync start" -- background tasks. 

Staat vanboen rechts: "Indexing..." - best even wachten. 