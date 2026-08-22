# AegisCall

AegisCall is an Android application built with Kotlin and Jetpack Compose.

> **Status:** 🚧 Early scaffolding. This project currently contains the default
> Android Studio "Empty Activity" starting point — no call-related features
> have been implemented yet. This README will be expanded as functionality is
> added.

## Tech Stack

- **Language:** Kotlin
- **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Build System:** Gradle (Kotlin DSL) with the [version catalog](gradle/libs.versions.toml)
- **Min SDK:** 24
- **Target / Compile SDK:** 37

| Dependency | Version |
|---|---|
| Android Gradle Plugin | 9.3.1 |
| Kotlin | 2.2.10 |
| Compose BOM | 2026.02.01 |
| Core KTX | 1.19.0 |
| Lifecycle Runtime KTX | 2.11.0 |
| Activity Compose | 1.13.0 |

## Project Structure

```
AegisCall/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/codefreaks/aegiscall/   # App source (MainActivity, theme)
│   │   │   ├── res/                             # Resources (strings, icons, themes)
│   │   │   └── AndroidManifest.xml
│   │   ├── test/                                # Unit tests
│   │   └── androidTest/                         # Instrumented tests
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/libs.versions.toml                    # Centralized dependency versions
```

## Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio) (latest stable release recommended)
- JDK 11 or newer
- An Android device or emulator running API level 24+

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/tg3454/AegisCall.git
   cd AegisCall
   ```
2. Open the project in Android Studio and let Gradle sync, **or** build from the command line:
   ```bash
   ./gradlew build
   ```

### Running the app

- **From Android Studio:** select the `app` run configuration and click **Run**.
- **From the command line:**
  ```bash
  ./gradlew installDebug
  ```

### Running tests

```bash
# Unit tests
./gradlew test

# Instrumented tests (requires a connected device/emulator)
./gradlew connectedAndroidTest
```

## Contributing

This project is in its early stages, so structure and conventions may change
frequently. Issues and pull requests are welcome — please open an issue to
discuss significant changes before submitting a PR.

## License

No license has been specified yet for this repository. Until one is added,
all rights are reserved by the author.
