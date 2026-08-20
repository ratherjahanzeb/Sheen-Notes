# Sheen Notes ❄️

A minimal, elegant note-taking application inspired by the serene winters of Kashmir ("Sheen" translates to snow). Built with a focus on simplicity, fluid animations, and a distraction-free writing experience.


## 🌟 Features

*   **Kashmir Winter Aesthetic:** Immersive Light and Dark themes transitioning elegantly from crisp Snow White to Deep Midnight Blue and Slate Charcoal.
*   **Rich Text Editing:** A robust WYSIWYG editor supporting bold, italic, underline, and bulleted lists natively inside the app.
*   **Instant Search:** Seamless, real-time filtering through all your notes' titles and contents.
*   **Hero Animations:** Fluid, spatial shared-element transitions that gracefully expand your note cards into the full editor.
*   **Offline First:** Lightning-fast local persistence powered by Android's Room Database.
*   **Edge-to-Edge UI:** Transparent status and navigation bars for a completely modern, immersive device experience.

## 🛠️ Tech Stack

*   **Language:** Kotlin
*   **UI Toolkit:** Jetpack Compose (Material 3)
*   **Local Storage:** Room Database (SQLite), DataStore (Preferences)
*   **Rich Text:** RichEditor-Compose
*   **Architecture:** MVVM (Model-View-ViewModel)

## 🚀 Getting Started

### Prerequisites
*   [Android Studio](https://developer.android.com/studio) (Latest version recommended)
*   Android SDK

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/sheen-notes.git
   ```
2. Open the project in Android Studio.
3. Sync the Gradle files.
4. Build and run the app on an emulator or physical device.

## 📦 Building the Release APK

To build an installable `.apk` file for your physical device, run the following commands from the root directory of the project:

```bash
# Clean the project
./gradlew clean

# Build the release APK
./gradlew assembleRelease
```
*The generated APK will be located at:* `app/build/outputs/apk/release/app-release.apk`
