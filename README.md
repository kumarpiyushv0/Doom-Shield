# 🛡️ DoomShield - Combat Doomscrolling, Reclaim Your Time

<div align="center">

![Platform](https://img.shields.io/badge/Platform-Android-brightgreen)
![Language](https://img.shields.io/badge/Language-Kotlin-purple)
![UI](https://img.shields.io/badge/UI-Jetpack%20Compose-blue)
![Min SDK](https://img.shields.io/badge/Min%20SDK-26-orange)
![License](https://img.shields.io/badge/License-MIT-yellow)

**A comprehensive digital wellbeing app built with Kotlin and Jetpack Compose to combat doomscrolling and promote healthier phone usage patterns.**

</div>

---

## 📖 Table of Contents

- [Overview](#-overview)
- [Key Features](#-key-features)
- [Screenshots](#-screenshots)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Installation](#-installation)
- [Features in Detail](#-features-in-detail)
- [Permissions](#-permissions)
- [How It Works](#-how-it-works)
- [Building the Project](#-building-the-project)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🌟 Overview

**DoomShield** is a native Android application designed to help users break free from the endless cycle of doomscrolling. By leveraging advanced accessibility services, intelligent pattern detection, and a suite of digital wellbeing tools, DoomShield empowers users to take control of their screen time and cultivate healthier digital habits.

The app features a **stunning, modern UI** built entirely with **Jetpack Compose**, incorporating Material 3 design principles, smooth animations, gradient backgrounds, and glassy neo-morphic elements for a premium user experience.

---

## ✨ Key Features

### 🎯 Intelligent Doomscrolling Detection
- **Real-time monitoring** using Android's Accessibility Service
- **Hybrid detection algorithm** combining heuristics and optional ML model
- Tracks scroll frequency, session duration, and app usage patterns
- Identifies excessive scrolling behavior across social media and other apps

### 🚫 Interactive Anti-Doomscrolling Alerts
- **Beautiful animated pop-ups** when excessive scrolling is detected
- Multiple intervention options:
  - 🧘 Take a 20-second mindfulness break
  - ⏸️ Pause DoomShield temporarily (10 min)
  - 🔒 Block current app (30 min)
  - ✅ Dismiss (I'm okay)
- Soft gradient backgrounds with smooth entrance animations
- Haptic feedback for enhanced user engagement

### 💚 Digital Wellbeing Hub
A comprehensive suite of tools to support mental health and mindful phone usage:

- **🫁 Breathing Exercises**: Animated circle guides for inhale/exhale patterns
- **⏱️ Focus Timer**: Session-based productivity tool with customizable durations
- **😊 Mood Tracker**: Daily emotional check-ins with emoji-based logging
- **✨ Daily Affirmations**: Motivational quotes to start your day right
- **🌙 Sleep Reminders**: Configurable bedtime notifications
- **🔥 Streak System**: Gamified rewards for maintaining healthy habits
- **📊 Usage Heatmaps**: Visualize your phone usage patterns over time

### 📈 Advanced Usage Statistics
- **Daily & Weekly Reports**: Comprehensive screen time breakdowns
- **App-Specific Analytics**: See which apps consume most of your time
- **Beautiful Charts**: Interactive visualizations using modern charting libraries
- **Trend Analysis**: Track your progress over weeks and months

### 🔐 App Management & Time Limits
- **App Blocking Interface**: Temporarily restrict access to specific apps
- **Whitelist Management**: Protect important apps from restrictions
- **Custom Time Limits**: Set daily usage caps for individual apps
- **Smart Notifications**: Get alerted when approaching your limits

### 🎨 Premium UI/UX Design
- **Material 3 Design Language** with custom color schemes
- **Dark Mode Support** for comfortable nighttime usage
- **Smooth Animations**: Spring-based transitions and fade effects
- **Glassy Cards**: Neo-morphic, rounded card designs
- **Gradient Backgrounds**: Eye-catching color gradients throughout
- **Custom Typography**: Carefully selected fonts for optimal readability
- **Adaptive Icons**: Modern launcher icons that adapt to device themes

### 🔔 Foreground Service & Notifications
- **Persistent Background Monitoring**: Continues running even when app is closed
- **Quick Controls**: Pause/resume DoomShield directly from notification
- **Status Updates**: Real-time detection status in notification bar
- **Battery Optimized**: Minimal battery drain despite continuous operation

### 🗄️ Local Data Storage
Utilizes Room Database for efficient, privacy-first data storage:
- Scroll events and patterns
- App usage sessions
- Detected doomscrolling incidents
- Wellbeing logs (mood, focus sessions)
- Daily affirmations and reminders
- User preferences and settings

### 🎓 Beautiful Onboarding Experience
- **Multi-screen walkthrough** explaining app features
- **Permission education**: Clear explanations of why each permission is needed
- **Animation-rich**: Engaging illustrations and micro-interactions
- **Mental health focus**: Emphasizes the importance of digital wellbeing

---
## 📱 Screenshots

<img src="https://github.com/user-attachments/assets/89d1ac26-dd8a-432e-a0aa-1fec5d41329b" width="200"/> 
<img src="https://github.com/user-attachments/assets/6fb9f27b-6617-44e5-b076-1c1504f8aa6d" width="200"/> 
<img src="https://github.com/user-attachments/assets/c7c54de0-5c30-4856-afe7-65b9e6d6ad40" width="200"/>



---

## 🛠️ Tech Stack

### Core Technologies
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Design System**: Material 3
- **Minimum SDK**: Android 10 (API 26)
- **Target SDK**: Android 13+ (API 36)

### Architecture & Libraries
- **Architecture Pattern**: MVVM (Model-View-ViewModel)
- **Clean Architecture**: Separation of concerns with domain, data, and presentation layers
- **Dependency Injection**: Hilt (Dagger)
- **Asynchronous**: Kotlin Coroutines & Flow
- **Navigation**: Jetpack Navigation Compose
- **Database**: Room Persistence Library
- **Data Storage**: DataStore Preferences
- **State Management**: ViewModel + SavedStateHandle

### Advanced Features
- **Accessibility Service**: For scroll detection and monitoring
- **Overlay System**: Custom window overlays for intervention pop-ups
- **Foreground Service**: Continuous background operation
- **ML Integration**: TensorFlow Lite (optional, for enhanced detection)
- **Charts**: Custom Compose charts for usage visualization

---

## 🏗️ Architecture

DoomShield follows **Clean Architecture** principles with clear separation of concerns:

```
app/
├── presentation/          # UI Layer (Compose screens, ViewModels)
│   ├── screens/          
│   │   ├── onboarding/   # Welcome & permission screens
│   │   ├── home/         # Main dashboard
│   │   ├── wellbeing_hub/# Wellbeing tools
│   │   ├── stats/        # Usage statistics
│   │   ├── app_management/# App blocking & limits
│   │   ├── settings/     # App settings
│   │   └── permissions/  # Permission request screens
│   ├── components/       # Reusable UI components
│   └── navigation/       # Navigation graph
│
├── domain/               # Business Logic Layer
│   ├── model/           # Domain entities
│   ├── repository/      # Repository interfaces
│   └── usecase/         # Use cases (business rules)
│
├── data/                # Data Layer
│   ├── local/          
│   │   ├── room/       # Room database, DAOs, entities
│   │   └── datastore/  # DataStore for preferences
│   ├── repository/     # Repository implementations
│   └── mapper/         # Data mappers
│
├── doomshield/         # Core Detection Engine
│   ├── DoomshieldAccessibilityService.kt
│   └── DoomshieldMonitor.kt
│
├── overlay/            # Intervention Pop-up System
│   └── DoomShieldOverlayService.kt
│
├── service/            # Background Services
│   └── DoomShieldForegroundService.kt
│
├── di/                 # Dependency Injection Modules
│   └── AppModule.kt
│
└── ui/theme/           # App Theming
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

### Design Pattern: MVVM

```
┌─────────────┐      ┌──────────────┐      ┌─────────────┐
│   View      │─────▶│  ViewModel   │─────▶│ Repository  │
│  (Compose)  │      │  (StateFlow) │      │  (UseCase)  │
└─────────────┘      └──────────────┘      └─────────────┘
      │                      │                      │
      │                      │                      │
      └──── Observes UI ─────┘                      │
           State Changes                            │
                                                     ▼
                                            ┌───────────────┐
                                            │   Data Source │
                                            │ (Room/Service)│
                                            └───────────────┘
```

---

## 🚀 Installation

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 11 or higher
- Android SDK 26+
- Gradle 8.0+

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/doomshield.git
   cd doomshield
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory

3. **Sync Gradle**
   - Android Studio will automatically sync Gradle
   - Wait for dependencies to download

4. **Run the app**
   ```bash
   ./gradlew installDebug
   ```
   Or simply click the "Run" button in Android Studio

---

## 🎯 Features in Detail

### 1. Doomscrolling Detection Engine

The heart of DoomShield is its sophisticated detection system:

#### How It Works:
1. **Accessibility Service** monitors all scroll events system-wide
2. **Pattern Recognition** identifies rapid, continuous scrolling
3. **Context Awareness** considers app type, time of day, break intervals
4. **Threshold Algorithm** combines multiple factors:
   - Scrolls per second (velocity)
   - Session duration (time spent)
   - Scroll consistency (continuous vs. sporadic)
   - App category (social media flagged)

#### Detection Metrics:
- **Scroll Rate**: Tracks velocity of scrolling gestures
- **Session Time**: Monitors continuous app usage
- **Break Intervals**: Identifies lack of meaningful pauses
- **App Context**: Flags high-risk apps (Instagram, TikTok, Twitter, etc.)

#### Optional ML Enhancement:
- Lightweight **TensorFlow Lite** model for advanced pattern recognition
- Trained on anonymized scrolling behavior datasets
- Improves detection accuracy over time
- Privacy-first: All processing happens on-device

---

### 2. Anti-Doomscrolling Intervention System

When excessive scrolling is detected, DoomShield intervenes with a beautiful, non-intrusive overlay:

#### Visual Design:
- **Animated entrance**: Smooth slide-up or fade-in animation
- **Gradient background**: Soft, calming color transitions
- **Glassy effect**: Frosted glass morphism for modern aesthetics
- **Friendly messaging**: Empathetic tone, not judgmental

#### Intervention Options:

| Option | Duration | Effect |
|--------|----------|--------|
| 🧘 **Mindful Break** | 20 seconds | Guided breathing exercise |
| ⏸️ **Pause DoomShield** | 10 minutes | Temporarily disable monitoring |
| 🔒 **Block App** | 30 minutes | Prevent access to current app |
| ✅ **Dismiss** | Instant | Acknowledge and continue (logged) |

#### Smart Timing:
- Won't interrupt important tasks (calls, navigation)
- Learns from your dismissal patterns
- Adjusts intervention frequency based on effectiveness

---

### 3. Wellbeing Hub - Your Digital Wellness Toolkit

#### 🫁 Breathing Exercises
- **Animated Circle**: Expands/contracts to guide breathing rhythm
- **Customizable Patterns**: 4-7-8, Box Breathing, Deep Breathing
- **Session Timer**: Track duration of practice
- **Calming Audio**: Optional background sounds (nature, ambient)

#### ⏱️ Focus Timer (Pomodoro-style)
- **Configurable Sessions**: 15, 25, 45, or 60 minutes
- **Break Reminders**: Automatic short/long break scheduling
- **Task Labels**: Name your focus sessions
- **Statistics**: Track total focus time per day/week

#### 😊 Mood Tracker
- **Daily Check-ins**: Log your emotional state
- **Emoji-based**: Quick, visual mood selection
- **Trend Visualization**: See mood patterns over time
- **Correlation Insights**: Link mood to screen time (optional)

#### ✨ Daily Affirmations
- **Curated Library**: 200+ positive affirmations
- **Random Display**: Fresh motivation each day
- **Custom Affirmations**: Add your own personal mantras
- **Notification Option**: Morning affirmation push notification

#### 🌙 Sleep Reminders
- **Customizable Bedtime**: Set your target sleep time
- **Wind-down Alerts**: 30-minute pre-sleep notification
- **Do Not Disturb**: Optional auto-enable DND mode
- **Blue Light Awareness**: Reminds you to reduce screen time before bed

#### 🔥 Streak System (Gamification)
- **Daily Goals**: Stay under screen time limits
- **Streak Counter**: Track consecutive days of healthy usage
- **Badges & Achievements**: Unlock rewards for milestones
- **Progress Sharing**: Optional social sharing (privacy-conscious)

---

### 4. Usage Statistics & Analytics

#### Daily Dashboard:
- **Total Screen Time**: Today's cumulative usage
- **Top Apps**: Ranked by time spent
- **Doomscroll Incidents**: Number of interventions today
- **Focus Sessions**: Time spent in productive focus

#### Weekly Reports:
- **Trend Charts**: Line graphs showing screen time trends
- **Day-by-Day Breakdown**: Bar charts for each day
- **Average Comparison**: Compare this week to last week
- **Improvement Metrics**: % reduction in doomscrolling

#### App-Specific Stats:
- **Per-App Screen Time**: Detailed usage for each app
- **Category Breakdown**: Social media, productivity, entertainment, etc.
- **Usage Heatmap**: Visual representation of usage by hour/day
- **Unlock Frequency**: How often you check your phone

#### Visualization:
- **Beautiful Charts**: Custom Compose-based charting
- **Color-coded**: Different colors for different app categories
- **Interactive**: Tap to see detailed breakdowns
- **Exportable**: Share or save reports (coming soon)

---

### 5. App Management & Time Limits

#### App Blocking:
- **Temporary Blocks**: 15 min, 30 min, 1 hour, custom
- **Schedule-based**: Block apps during work hours, bedtime
- **Emergency Override**: Pause block with confirmation
- **Usage Warning**: Notification when blocked app is attempted

#### Whitelist Management:
- **Protected Apps**: Exclude essential apps from all restrictions
- **Smart Suggestions**: Auto-suggest work, communication apps
- **Import/Export**: Save whitelist configurations

#### Time Limits:
- **Per-App Daily Limits**: Set maximum usage per app
- **Category Limits**: Limit entire categories (e.g., "Social Media: 1 hour")
- **Warning Thresholds**: Alert at 80% of limit
- **Soft vs. Hard Limits**: Choose blocking vs. warning

---

### 6. Foreground Service Architecture

DoomShield uses a **Foreground Service** to ensure continuous monitoring:

#### Benefits:
- **Persistent Operation**: Runs even when app is closed
- **System Priority**: Less likely to be killed by Android
- **Quick Access**: Control from notification shade
- **Battery Efficient**: Optimized to minimize power consumption

#### Notification Controls:
- **Pause/Resume**: Toggle monitoring with one tap
- **Status Display**: "Monitoring Active" or "Paused"
- **Quick Settings**: Access settings directly from notification
- **Dismissible**: Can be swiped away (service continues)

#### Battery Optimization:
- **Efficient Polling**: Smart event batching
- **Wake Lock Management**: Minimal CPU wake-ups
- **Doze Mode Compatible**: Works with Android's battery saver
- **User Control**: Easy to disable when not needed

---

### 7. Privacy & Data Security

#### Privacy-First Design:
- ✅ **All data stored locally** (Room database on device)
- ✅ **No cloud sync** (no data transmitted to servers)
- ✅ **No analytics tracking** (no third-party SDKs)
- ✅ **No personal data collection** (only usage patterns)
- ✅ **Open Source** (transparent, auditable code)

#### Data Stored Locally:
- Scroll events (timestamp, app, duration)
- App usage sessions
- Wellbeing logs (mood, focus sessions)
- User preferences and settings
- **No sensitive content**, screenshots, or keyboard input

#### Permissions Transparency:
Every permission is clearly explained during onboarding:
- **Accessibility**: Required to detect scrolling patterns
- **Usage Access**: Needed to track app usage statistics
- **Overlay**: Allows intervention pop-ups to appear
- **Foreground Service**: Enables background monitoring

---

## 🔐 Permissions

DoomShield requires the following permissions to function:

| Permission | Purpose | Required? |
|------------|---------|-----------|
| **Accessibility Service** | Monitor scroll events and gestures | ✅ Essential |
| **Usage Access Stats** | Track app usage time and frequency | ✅ Essential |
| **Display Over Other Apps** | Show intervention pop-ups | ✅ Essential |
| **Foreground Service** | Continuous background monitoring | ✅ Essential |
| **Query All Packages** | List installed apps for blocking | ⚠️ Android 11+ |

### How to Grant Permissions:

1. **Installation**: DoomShield will guide you through onboarding
2. **Accessibility**: Settings → Accessibility → DoomShield → Enable
3. **Usage Access**: Settings → Apps → Special Access → Usage Access → DoomShield
4. **Overlay**: Settings → Apps → Special Access → Display Over Other Apps → DoomShield
5. **Notifications**: Ensure notifications are enabled for full functionality

---

## ⚙️ How It Works

### User Flow:

```
1. Install DoomShield
        ↓
2. Complete Onboarding
        ↓
3. Grant Required Permissions
        ↓
4. Accessibility Service Starts Monitoring
        ↓
5. Use Phone Normally
        ↓
6. Excessive Scrolling Detected?
        ├─── No → Continue Monitoring
        └─── Yes → Show Intervention Pop-up
                      ↓
                User Chooses Action
                      ↓
              Log Event & Resume
```

### Technical Flow:

```
DoomshieldAccessibilityService
        ↓ (detects scroll events)
DoomshieldMonitor
        ↓ (analyzes patterns)
Detection Algorithm (heuristics + optional ML)
        ↓ (threshold exceeded?)
DoomShieldOverlayService
        ↓ (displays intervention)
User Interaction
        ↓ (logs to database)
Room Database
```

---

## 🏗️ Building the Project

### Build Commands:

```bash
# Clean build artifacts
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Build release APK (requires signing config)
./gradlew assembleRelease

# Install debug build to connected device
./gradlew installDebug

# Run unit tests
./gradlew test

# Run instrumented tests (requires emulator/device)
./gradlew connectedAndroidTest
```

### Build Configuration:

- **Min SDK**: 26 (Android 8.0 Oreo)
- **Target SDK**: 36 (Android 13+)
- **Compile SDK**: 36
- **Java Version**: 11
- **Kotlin Version**: Latest stable

### Dependencies Management:

Dependencies are centralized in `gradle/libs.versions.toml`:
- Jetpack Compose BOM
- Hilt (Dagger)
- Room Database
- Navigation Compose
- Material 3
- Coroutines & Flow
- DataStore Preferences

---

## 🤝 Contributing

Contributions are welcome! Whether it's bug fixes, new features, or documentation improvements, your help makes DoomShield better for everyone.

### How to Contribute:

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/amazing-feature`
3. **Commit changes**: `git commit -m 'Add amazing feature'`
4. **Push to branch**: `git push origin feature/amazing-feature`
5. **Open a Pull Request**

### Contribution Guidelines:

- Follow existing code style (Kotlin conventions)
- Write meaningful commit messages
- Update documentation for new features
- Add unit tests for new functionality
- Ensure all tests pass before submitting PR

### Reporting Issues:

Found a bug? Have a feature request? 

1. Check if the issue already exists
2. If not, create a new issue with:
   - Clear description
   - Steps to reproduce (for bugs)
   - Expected vs. actual behavior
   - Device info (Android version, manufacturer)
   - Screenshots (if applicable)

---

## 📜 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2024 DoomShield

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

[Full license text...]
```

---

## 🙏 Acknowledgments

- **Jetpack Compose Team** for the amazing modern UI toolkit
- **Android Open Source Project** for accessibility APIs
- **Mental Health Community** for raising awareness about digital wellbeing
- **Contributors** who help improve DoomShield

---

## 📞 Contact & Support

- **GitHub Issues**: [Report bugs or request features](https://github.com/yourusername/doomshield/issues)
- **Discussions**: [Ask questions or share ideas](https://github.com/yourusername/doomshield/discussions)
- **Email**: support@doomshield.app *(coming soon)*

---

## 🗺️ Roadmap

### Planned Features:

- [ ] **Export Reports**: PDF/CSV export of usage statistics
- [ ] **Focus Mode**: Aggressive blocking for deep work sessions
- [ ] **App Timers**: Set countup/countdown timers per app
- [ ] **Family Sharing**: Sync settings across family devices (opt-in)
- [ ] **Widget Support**: Home screen widgets for quick stats
- [ ] **Wear OS Companion**: View stats on smartwatch
- [ ] **Improved ML Model**: Enhanced doomscroll detection
- [ ] **Themes**: Custom color themes and dark mode variants
- [ ] **Localization**: Multi-language support
- [ ] **Cloud Backup**: Optional encrypted cloud backup (privacy-first)

---

## ⭐ Star History

If you find DoomShield useful, please consider giving it a ⭐ on GitHub! Your support motivates continued development.

---

<div align="center">

**Made with ❤️ and Jetpack Compose**

*Reclaim your time. Reclaim your mind. Use DoomShield.*

[⬆ Back to Top](#️-doomshield---combat-doomscrolling-reclaim-your-time)

</div>
