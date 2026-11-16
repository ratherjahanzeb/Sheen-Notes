#!/usr/bin/env bash
#
# Sets up the local development environment for Sheen Notes.
# Run from the repository root:  ./scripts/setup-dev.sh
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR"

info() { printf '\033[1;34m[setup]\033[0m %s\n' "$*"; }
warn() { printf '\033[1;33m[setup]\033[0m %s\n' "$*" >&2; }
fail() { printf '\033[1;31m[setup]\033[0m %s\n' "$*" >&2; exit 1; }

# 1. Java toolchain (JDK 17 or newer is required for Android Gradle Plugin 9.x).
if ! command -v java >/dev/null 2>&1; then
  fail "Java not found. Install a JDK 17+ (e.g. Eclipse Temurin 17 or 21) and re-run."
fi
JAVA_MAJOR="$(java -version 2>&1 | awk -F'"' '/version/ {print $2}' | cut -d. -f1)"
# Handle legacy "1.8.0_x" style version strings.
if [ "$JAVA_MAJOR" = "1" ]; then
  JAVA_MAJOR="$(java -version 2>&1 | awk -F'"' '/version/ {print $2}' | cut -d. -f2)"
fi
if [ "${JAVA_MAJOR:-0}" -lt 17 ]; then
  fail "JDK 17+ required, found major version '$JAVA_MAJOR'."
fi
info "Found JDK $(java -version 2>&1 | head -n1)"

# 2. Android SDK. The build needs the SDK in local.properties (or ANDROID_HOME /
#    ANDROID_SDK_ROOT) and platform 36 (Android 16, minor API 36.1) installed.
if [ ! -f local.properties ]; then
  if [ -n "${ANDROID_HOME:-}" ]; then
    info "Writing local.properties with sdk.dir=$ANDROID_HOME"
    printf 'sdk.dir=%s\n' "$ANDROID_HOME" > local.properties
  elif [ -n "${ANDROID_SDK_ROOT:-}" ]; then
    info "Writing local.properties with sdk.dir=$ANDROID_SDK_ROOT"
    printf 'sdk.dir=%s\n' "$ANDROID_SDK_ROOT" > local.properties
  else
    warn "local.properties not found and ANDROID_HOME/ANDROID_SDK_ROOT is unset."
    warn "Install the Android SDK (e.g. via Android Studio) and create local.properties with:"
    warn "  sdk.dir=/path/to/Android/Sdk"
  fi
else
  info "local.properties already present"
fi

# 3. Debug keystore. app/build.gradle.kts signs the debug build with
#    ./debug.keystore (password: android, alias: androiddebugkey).
if [ ! -f debug.keystore ]; then
  if command -v keytool >/dev/null 2>&1; then
    info "Generating debug.keystore (alias: androiddebugkey)"
    keytool -genkeypair -v \
      -keystore debug.keystore \
      -storepass android \
      -keypass android \
      -alias androiddebugkey \
      -keyalg RSA -keysize 2048 -validity 10000 \
      -dname "CN=Android Debug,O=Android,C=US" >/dev/null
  else
    warn "keytool not found; install a JDK and re-run to generate debug.keystore."
  fi
else
  info "debug.keystore already present"
fi

# 4. Environment secrets. .env is gitignored; .env.example documents the keys.
if [ ! -f .env ]; then
  info "Creating .env from .env.example (fill in your values)"
  cp .env.example .env
else
  info ".env already present"
fi

# 5. Gradle wrapper sanity check.
[ -x gradlew ] || chmod +x gradlew
info "Gradle wrapper: $(grep -E '^distributionUrl' gradle/wrapper/gradle-wrapper.properties | cut -d= -f2-)"

info "Done. Next steps:"
info "  1. Open this directory in Android Studio (Giraffe+ / Panda 3+ recommended), or"
info "  2. Build from the CLI once the Android SDK is available:"
info "     ./gradlew :app:assembleDebug"
info "  3. Run unit tests:   ./gradlew :app:testDebugUnitTest"
info "  4. Run lint:         ./gradlew :app:lintDebug"
