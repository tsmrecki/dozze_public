# ⏱️ Dozze

**Dozze** is a minimalist, cross-platform goal-tracking timer app built with [Kotlin Multiplatform](https://kotlinlang.org/lp/multiplatform/) and [Jetpack Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/). 

Log custom goals with a single tap, and track how long it’s been since you last accomplished them. Whether it's watering a plant or taking a moment to breathe, Dozze helps you stay mindful — without the pressure of streaks.

---

## 🌟 What is Dozze?

When you complete a goal, just tap to log it. Dozze starts a timer and shows you how long it’s been since you last took that action. That’s it. Simple, effective, and gently motivating.

### Example Use Cases

- 🌱 Watering your plants  
- ☕ Taking a coffee break  
- 💧 Drinking water  
- 🧘 Doing a stretch  
- 🐕 Walking your dog  

---

## 🎯 Features

- Create and customize personal goals  
- One-tap logging for goal completions  
- Automatically tracks time since last action  
- Beautiful, minimalist UI  
- Dark/light mode support
- Android Dynamic Color theme style support
- Fully cross-platform: Android & iOS  

---

## 📁 Project Structure

This is a Kotlin Multiplatform project targeting Android and iOS.

### `/composeApp`

This is the shared module that contains most of your business logic and UI written in Compose Multiplatform.

- `commonMain` — shared Kotlin code for all platforms  
- `androidMain`, `iosMain` — platform-specific Kotlin code  
  - For example, use `iosMain` if you want to call iOS-specific APIs like Apple’s CoreCrypto

### `/iosApp`

Native iOS application module.

Even if you're sharing UI with Compose, this module serves as the **entry point** for your iOS app. This is where you can add Swift or SwiftUI code to interact with the shared Compose views.

📚 Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)

---

## 🛠️ Getting Started

### Prerequisites

- Android Studio Giraffe or newer  
- Kotlin 1.9+  
- Xcode 15+ (for iOS)  
- CocoaPods installed (if needed)  

### Run the app

```bash
git clone https://github.com/yourusername/dozze.git
cd dozze
./gradlew build
```

- **Android**: Run via Android Studio  
- **iOS**: Open `/iosApp` in Xcode and run on a simulator or device  

---

## 🧪 Onboarding Sample

Dozze comes with a playful onboarding sample to show users how the app works:

> **Goal**: *“Are you here?”*  
> **Button**: *“Yep, I’m here 👋”*

No pressure. Just a tap to try it out.

---

## 🚀 Tech Stack

- [Kotlin Multiplatform (KMP)](https://kotlinlang.org/lp/multiplatform/)  
- [Jetpack Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)  
- Swift Package Manager (SPM) for iOS dependencies  
- Firebase for analytics & crash reporting
- Room & DataStore for local storage
- Koin for DI

---

## 📸 Screenshots

https://github.com/user-attachments/assets/4e0d6bba-b7dc-4df9-b955-d4bc31fcdcb9




---

## 📄 License

MIT License. See [LICENSE](LICENSE) for more information.

