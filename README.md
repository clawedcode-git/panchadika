<!-- Logo/Header -->
<div align="center">
  <img src="https://raw.githubusercontent.com/clawedcode-git/panchadika/master/app/src/main/res/drawable/ic_launcher_foreground.xml" alt="Panchadika" width="120" height="120">
  <h1>Panchadika</h1>
  <p>A modern Android SMS application with elegant E-Chat design</p>
</div>

<!-- Badges -->
<div align="center">

[![Android](https://img.shields.io/badge/Android-34-success?style=flat&logo=android)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blue?style=flat&logo=kotlin)](https://kotlinlang.org)
[![License](https://img.shields.io/badge/License-MIT-green?style=flat)](LICENSE)
[![Platform](https://img.shields.io/badge/Platform-Android-brightgreen?style=flat&logo=android)](https://developer.android.com)

</div>

---

## About

**Panchadika** is a feature-rich Android SMS application built with modern Android development practices. It provides seamless SMS conversation management with a beautiful E-Chat inspired design featuring teal green (#44FC94) primary color with yellow (#F8DC89) accents.

The app can be set as the default SMS app on Android devices and supports carrier auto-detection, making it a complete messaging solution.

---

## Features

| Feature | Description |
|---------|-------------|
| 💬 **SMS Conversations** | View, manage, and participate in SMS threads |
| 📡 **Carrier Auto-Detection** | Automatically detects carrier name, SIM details, network type, and SMSC |
| ✉️ **New Message Composition** | Send SMS to any phone number |
| ⚙️ **Settings** | View carrier and network information |
| 🎨 **E-Chat Design** | Modern Material Design 3 UI with teal green theme |
| 📱 **Default SMS App** | Can be set as default SMS app on Android |
| 🔔 **System Integration** | Handles incoming SMS and responds to SMS intents |

---

## Tech Stack

<div align="center">

| Technology | Icon | Description |
|------------|------|-------------|
| <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/kotlin/kotlin-original.svg" width="24"/> **Kotlin** | 🟣 | Modern Android development language |
| <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/android/android-original.svg" width="24"/> **Jetpack Compose** | 🎨 | Modern declarative UI toolkit |
| <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/android/android-original.svg" width="24"/> **Material Design 3** | ✨ | Modern Material You design system |
| **Clean Architecture** | 🏗️ | Domain/Data/Presentation layers |
| **MVVM** | 🔄 | Model-View-ViewModel pattern |
| **Hilt** | 💉 | Dependency Injection framework |
| **Coroutines + Flow** | 🔥 | Asynchronous programming |
| **Room** | 🗄️ | Local database (if used) |

</div>

### Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                       │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐ │
│  │ Screens     │  │ ViewModels  │  │ UI Components       │ │
│  │ Compose UI  │  │ StateFlow   │  │ Material 3          │ │
│  └─────────────┘  └─────────────┘  └─────────────────────┘ │
├─────────────────────────────────────────────────────────────┤
│                      Domain Layer                           │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐ │
│  │ Models      │  │ Use Cases   │  │ Repository Interfaces│ │
│  │ Data classes│  │ Business    │  │ Contracts           │ │
│  └─────────────┘  └─────────────┘  └─────────────────────┘ │
├─────────────────────────────────────────────────────────────┤
│                       Data Layer                            │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐ │
│  │ Data Sources│  │ Repositories│  │ Android Telephony   │ │
│  │ SMS Provider│  │ Implementations│ Content Provider   │ │
│  └─────────────┘  └─────────────┘  └─────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

---

## Screenshots

| Conversation List | Thread View | New Message | Settings |
|------------------|-------------|-------------|----------|
| 💬 | 💬 | ✉️ | ⚙️ |

---

## Build

### Prerequisites

- Java 17+
- Android SDK (API 34)
- Gradle 8.9+

### Build Commands

```bash
# Clone the repository
git clone https://github.com/clawedcode-git/panchadika.git
cd panchadika

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease
```

---

## APK

Download the latest debug APK from the [Releases](https://github.com/clawedcode-git/panchadika/releases) page.

### Requirements

- **minSdk**: Android 8.0 (API 26)
- **targetSdk**: Android 14 (API 34)

---

## Permissions

The app requires the following permissions:

- `SEND_SMS` - Send SMS messages
- `READ_SMS` - Read SMS messages
- `RECEIVE_SMS` - Receive SMS messages
- `READ_PHONE_STATE` - Read carrier information
- `READ_CONTACTS` - Read contact information (optional)

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

<div align="center">

Made with ❤️ using Kotlin & Jetpack Compose

</div>
