# AumGhanti
This is a free to use, open sourced Android and iOS app aimed to mimick a Hindu Ghanti functionality for religious, spiritual use-cases.
Just shake your phone like shaking a ghanti and listen to the divine bell aum humming sound.

## Demo

https://github.com/user-attachments/assets/e57554e3-9306-4555-afb9-ec5de7fbeff5

## Project structure 
This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    `iosMain` would be the right folder for ios platform

* `/iosApp` contains iOS applications.

## Resources and Assets used
* `Shake functionality` : https://github.com/DevLucem/Shake-Detector/blob/master/shakedetector/src/main/java/com/lucem/anb/shakedetector/ShakeDetector.java
* `Bell app icon` : https://pngtree.com/element/down?id=NjYzNTY0OQ==&type=1&time=1725735954&token=YWRhMGIyNWEwNzkxNjY0YWE5YzVjZGZmZTgwYjQ1NjA=&t=0
* `Bell sound` : https://www.soundjay.com/bell-sound-effect.html
* `Bell image` : https://www.google.com/imgres?q=hindu%20ghanti%20image%20asset&imgurl=https%3A%2F%2Fm.media-amazon.com%2Fimages%2FI%2F51hgtIi6g9S._AC_UL400_.jpg&imgrefurl=https%3A%2F%2Fwww.amazon.com%2FSATVIK-Bell-Wedding-Decoration-Housewarming%2Fdp%2FB0C4PRTD41&docid=u2pjATa5akWFlM&tbnid=OCiJZlJPZcuHyM&vet=12ahUKEwiZ7drQoLGIAxXQD1kFHWzKN4IQM3oECHEQAA..i&w=162&h=400&hcb=2&ved=2ahUKEwiZ7drQoLGIAxXQD1kFHWzKN4IQM3oECHEQAA

## Disclaimer
This app was built with no intentions to make money, and is meant to be distributed freely. Apologies for any un-intentional copyright issue, please reach out by making an issue and I will try my best to help resolve it quickly.
 - Deep Parekh
