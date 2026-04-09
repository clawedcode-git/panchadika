# Panchadika

An Android SMS application with carrier auto-detection, conversation management, and modern Material Design 3 UI.

## Features

- **SMS Conversations**: View and manage SMS threads
- **Carrier Auto-Detection**: Automatically detects carrier name, SIM details, network type, and SMSC
- **New Message Composition**: Send SMS to any contact
- **Settings**: View carrier and network information
- **E-Chat Design**: Teal green (#44FC94) primary with yellow (#F8DC89) accent
- **Default SMS App**: Can be set as default SMS app on Android
- **System Integration**: Handles incoming SMS and responds to SMS intents

## Tech Stack

- Kotlin + Jetpack Compose
- Clean Architecture + MVVM
- Hilt for Dependency Injection
- Coroutines + Flow for Async Operations
- Direct SMS content provider access

## Build

```bash
./gradlew assembleDebug
```

## APK

Debug APK: `app/build/outputs/apk/debug/app-debug.apk`

## Requirements

- Android API 26+ (minSdk)
- Android API 34 (targetSdk)