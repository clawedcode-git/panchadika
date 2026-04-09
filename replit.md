# Panchadika - Android SMS App

## Overview
Panchadika is an Android SMS application with carrier auto-detection, conversation management, and modern Material Design 3 UI. It is written in Kotlin with Jetpack Compose.

## Tech Stack
- **Language:** Kotlin 1.9.25
- **UI:** Jetpack Compose (Material Design 3)
- **Architecture:** Clean Architecture + MVVM
- **DI:** Dagger Hilt 2.51.1
- **Navigation:** Jetpack Navigation Compose
- **Async:** Kotlin Coroutines + Flow
- **Image Loading:** Coil
- **Build System:** Gradle 8.9 with Kotlin DSL
- **Min SDK:** 26 (Android 8.0)
- **Target SDK:** 34 / Compile SDK: 35

## Project Structure
- `app/src/main/java/com/panchadika/`
  - `data/` - Repositories and data sources (SMS, Contacts, Carrier)
  - `domain/` - Business logic, use cases, models
  - `presentation/` - Jetpack Compose UI, ViewModels
  - `di/` - Hilt dependency injection modules

## Build Setup (Replit)
This is an Android app — it cannot run in the browser. It builds to an APK.

### Android SDK Location
- Android SDK: `~/android-sdk`
- Platform: android-35
- Build Tools: 35.0.1
- JDK: OpenJDK 17 at `/nix/store/6m2zgvl7y9mrsdckqx4mgxghpmasszbi-openjdk-17.0.6+10`

### Build Command
```bash
bash build_apk.sh
```

Or directly:
```bash
export ANDROID_HOME=~/android-sdk
export JAVA_HOME=/nix/store/6m2zgvl7y9mrsdckqx4mgxghpmasszbi-openjdk-17.0.6+10
export PATH=$JAVA_HOME/bin:$PATH:~/android-sdk/cmdline-tools/latest/bin:~/android-sdk/platform-tools
./gradlew assembleDebug
```

### APK Output
After a successful build:
- Debug APK: `app/build/outputs/apk/debug/app-debug.apk`

## Workflow
- **Build APK** - Runs `bash build_apk.sh` to compile the debug APK (console output)

## Notes
- `gradle.properties` sets `org.gradle.java.home` to OpenJDK 17 for Android compatibility
- GraalVM 19 (system default) is NOT compatible with Android build tools due to jlink issues
- Only minor deprecation warnings in code (no functional impact)
