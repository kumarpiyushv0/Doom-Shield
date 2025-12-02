# Gemini Project: Doom Shield

## Project Overview

"Doom Shield" is a comprehensive native Android application designed to combat doomscrolling and promote digital well-being. Built with Kotlin and Jetpack Compose, the app aims for a visually stunning, modern, minimalistic, animated, and highly polished user interface.

**Key Features and Technologies:**

*   **Jetpack Compose UI:** Utilizes Material 3, custom color schemes, smooth animations, rounded/glassy/neo-morphic cards, gradient backgrounds, and rich iconography for an impressive user experience across multiple screens (Onboarding, Home Dashboard, Real-time Doomshield Monitor, Well-being Hub, Usage Stats/Weekly Reports, App Blocking/Whitelist UI, Settings).
*   **Doomscrolling Detection:** Implements a highly effective detector using `AccessibilityService` to monitor scrolling patterns, session length, and app usage. It employs a hybrid detection logic combining heuristics (scrolls/sec, rapid swipes, long sessions) and an optional lightweight ML model (TFLite).
*   **Anti-Doomscrolling Pop-up:** Triggers a beautiful, animated, and interactive blocking pop-up when doomscrolling is detected, offering options to take a break, pause DoomShield, block the current app, or dismiss the alert.
*   **Digital Wellbeing Features:** Provides a rich Well-being Hub with tools like breathing exercises, focus timer, mood tracker, daily affirmations, sleep reminders, usage heatmaps, screen time stats, app-specific restriction timers, and a reward streak system.
*   **Foreground Service & Notification:** Ensures continuous operation and provides quick controls via a persistent notification.
*   **Local Database (Room):** Stores critical user data such as scroll events, app sessions, detected doomscroll events, well-being logs, and focus timer sessions.
*   **Permissions Flow:** Features a friendly and informative onboarding process to explain and request necessary permissions (Accessibility, Usage Access, Overlay).
*   **Architecture:** Adheres to MVVM, Clean Architecture, Repository pattern, and utilizes Dependency Injection with Hilt, along with Kotlin Coroutines and Flows for asynchronous operations.
*   **Additional:** Includes dark mode support, adaptive icons, custom typography, targets Android 10+, and prioritizes minimal battery usage and accessibility.

## Building and Running

The project is managed by Gradle. Here are the common commands to build and test the project from the command line:

*   **Build the project:**
    ```bash
    ./gradlew build
    ```

*   **Run unit tests:**
    ```bash
    ./gradlew test
    ```

*   **Install the application on a connected device or emulator (debug version):**
    ```bash
    ./gradlew installDebug
    ```
    After installation, the app can be launched from the device's app drawer.

*   **Clean the build cache:**
    ```bash
    ./gradlew clean
    ```

> **Note:** For a full development experience, it is recommended to open this project in Android Studio.

## Development Conventions

*   **Language:** The primary language is Kotlin.
*   **UI Framework:** Jetpack Compose is used for building the user interface, with a strong emphasis on visually stunning, modern, minimalistic designs, Material 3 components, custom color schemes, smooth animations, and polished interactions. UI components are defined as `@Composable` functions.
*   **Architecture:** The project follows an MVVM (Model-View-ViewModel) architectural pattern, combined with Clean Architecture principles and the Repository pattern for data management.
*   **Dependency Injection:** Hilt is utilized for dependency injection, promoting modularity and testability.
*   **Asynchronous Operations:** Kotlin Coroutines and Flows are used for efficient handling of asynchronous tasks and data streams.
*   **Dependencies:** Project dependencies are managed in the `build.gradle.kts` files and the `gradle/libs.versions.toml` file.
*   **Structure:** The code follows the standard Android project structure with further organization based on Clean Architecture principles:
    *   `app/src/main/java`: Main application source code, logically divided into packages for UI, data, domain, etc.
    *   `app/src/main/res`: Application resources (drawables, layouts, strings, styles, etc.).
    *   `app/src/test`: Unit tests.
    *   `app/src/androidTest`: Instrumented tests.
*   **Theming:** The app's theme, including custom typography and dark mode support, is defined in `app/src/main/java/com/example/doomshield/ui/theme/`.

## Key Files (Expected)

*   `app/build.gradle.kts`: App-level Gradle configuration, including dependencies.
*   `build.gradle.kts`: Top-level Gradle configuration.
*   `gradle/libs.versions.toml`: Centralized dependency version management.
*   `app/src/main/AndroidManifest.xml`: Android application manifest, declaring components, permissions, and services (e.g., `AccessibilityService`, `ForegroundService`).
*   `app/src/main/java/com/example/doomshield/MainActivity.kt`: The main entry point and screen of the application.
*   `app/src/main/java/com/example/doomshield/doomshield/DoomshieldAccessibilityService.kt`: (Expected) Implementation of the `AccessibilityService` for doomscrolling detection.
*   `app/src/main/java/com/example/doomshield/doomshield/DoomshieldMonitor.kt`: (Expected) Logic for real-time doomscrolling detection and pattern analysis.
*   `app/src/main/java/com/example/doomshield/ui/screens/*`: (Expected) Directory containing various UI screens (Onboarding, Dashboard, Wellbeing Hub, Settings, etc.).
*   `app/src/main/java/com/example/doomshield/data/local/room/AppDatabase.kt`: (Expected) Room database definition.
*   `app/src/main/java/com/example/doomshield/data/local/room/*Dao.kt`: (Expected) Data Access Objects for database interactions.
*   `app/src/main/java/com/example/doomshield/domain/repository/*Repository.kt`: (Expected) Repository interfaces for data abstraction.
*   `app/src/main/java/com/example/doomshield/domain/usecase/*UseCase.kt`: (Expected) Use cases defining business logic.
*   `app/src/main/java/com/example/doomshield/presentation/viewmodel/*ViewModel.kt`: (Expected) ViewModels for managing UI-related data and logic.
*   `app/src/main/java/com/example/doomshield/di/*Module.kt`: (Expected) Hilt dependency injection modules.
*   `app/src/main/java/com/example/doomshield/ui/theme/`: Directory containing theming definitions (Color, Theme, Type).

**Note:** Some of these files/directories are architectural components described in the `README.md` and may not yet exist in the initial scaffold. They represent expected parts of the complete project.
