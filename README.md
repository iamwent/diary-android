# diary-android [![android](https://img.shields.io/badge/android-24%2B-brightgreen.svg)](https://android-arsenal.com/api?level=24)[![license](https://img.shields.io/badge/license-GPL3-lightgrey.svg)](https://github.com/iamwent/diary-android/blob/master/LICENSE)

Android implementation for [小记](https://apps.apple.com/cn/app/%E5%B0%8F%E8%AE%B0/id975031499).

## decomposer
I have added the decomposer gradle plugin to decompile bytecode into Java. It helps us dive into the deep of Jetpack Compose, Kotlin Coroutine, and more.
Just run the magic command and check the dir `app/build/decompiled`:
```shell
./gradlew clean :app:compileDebugKotlin
```

## License

```
GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007

Copyright (C) 2024 iamwent

This program comes with ABSOLUTELY NO WARRANTY.
This is free software, and you are welcome to redistribute it under certain conditions.
```

