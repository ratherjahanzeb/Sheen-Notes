<img width="1080" height="2144" alt="1000002227" src="https://github.com/user-attachments/assets/afec8c05-6c79-4427-80cb-5b0cc87cf043" />
<img width="1080" height="2144" alt="1000002226" src="https://github.com/user-attachments/assets/7bb9357d-6603-4d42-8356-03a4b60e44f1" />
<img width="1080" height="2144" alt="1000002225" src="https://github.com/user-attachments/assets/aab6a65a-5937-4112-929e-46e52953a290" />
<img width="1080" height="2144" alt="1000002224" src="https://github.com/user-attachments/assets/1f7862d5-23a3-4c0b-ac2a-b9bc0bba6e34" />

## Development Setup

**Prerequisites**

- JDK 17+ (e.g. Eclipse Temurin 17 or 21)
- Android SDK with platform **36** (Android 16, minor API 36.1) and build-tools installed — the easiest way is to install [Android Studio](https://developer.android.com/studio) and open this folder once.
- A `.env` file with the keys from `.env.example` (copy `.env.example` to `.env` and fill in `GEMINI_API_KEY` if you use the Gemini AI features).

**Getting started**

```bash
./scripts/setup-dev.sh   # checks JDK, writes local.properties, generates debug.keystore, creates .env
./gradlew :app:assembleDebug
```

**Common tasks**

| Task | Command |
| --- | --- |
| Build a debug APK | `./gradlew :app:assembleDebug` |
| Run unit tests (Robolectric) | `./gradlew :app:testDebugUnitTest` |
| Run lint | `./gradlew :app:lintDebug` |
| Record/verify screenshot tests | `./gradlew :app:roborazziDebugRecord` / `./gradlew :app:roborazziDebugVerify` |

The debug build signs with `./debug.keystore` (password `android`, alias `androiddebugkey`), which `scripts/setup-dev.sh` creates automatically; it is gitignored. Release builds read `KEYSTORE_PATH`, `STORE_PASSWORD`, and `KEY_PASSWORD` from the environment.

**Project layout**

- `app/src/main/java/com/example/model` — Room database (`Note`, `NoteDao`, `NoteRepository`, `AppDatabase`)
- `app/src/main/java/com/example/ui` — Compose UI (`NotesScreen`, `AddNoteScreen`, `NotesViewModel`, navigation, theme)
- `app/src/test` — JVM unit tests (Robolectric + Roborazzi screenshot tests)
- `app/src/androidTest` — instrumented tests

---

_📌Sheen Notes features a minimal winter aesthetic, offline storage, rich text editing, real-time search, smooth animations, and a dedicated dark mode._



❄️​Kashmir Winter Aesthetic: A borderless, minimalist UI using frosted glassmorphism, crisp whites, and ice-blue accents.



🌙​"Night in Kashmir" Dark Mode: A deep midnight blue and charcoal theme tailored for low-light writing.



💾​Local Device Storage: Complete offline functionality that saves all notes securely to the device.



🔍​Dynamic Search: Real-time filtering to instantly find specific notes by searching through titles and body content.



✨​Fluid Animations: Smooth transitions that seamlessly expand note cards from the grid into the full editor screen.

__**Join Discord Server; https://discord.gg/tQkJKeyT9M**__
