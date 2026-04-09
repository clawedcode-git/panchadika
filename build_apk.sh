#!/bin/bash
set -e

WORKSPACE_DIR="$(cd "$(dirname "$0")" && pwd)"
ANDROID_SDK_DIR="$WORKSPACE_DIR/android-sdk"
JAVA_HOME=/nix/store/6m2zgvl7y9mrsdckqx4mgxghpmasszbi-openjdk-17.0.6+10

export JAVA_HOME
export ANDROID_HOME="$ANDROID_SDK_DIR"
export PATH=$JAVA_HOME/bin:$ANDROID_SDK_DIR/cmdline-tools/latest/bin:$ANDROID_SDK_DIR/platform-tools:$PATH

echo "=== Panchadika Android APK Build ==="
echo "Workspace: $WORKSPACE_DIR"
echo "Android SDK: $ANDROID_HOME"
echo "Java: $(java -version 2>&1 | head -1)"
echo ""

# Auto-setup Android SDK if missing
if [ ! -d "$ANDROID_SDK_DIR/platforms/android-35" ]; then
    echo "Android SDK not found — setting up..."

    mkdir -p "$ANDROID_SDK_DIR/cmdline-tools"

    if [ ! -f "$ANDROID_SDK_DIR/cmdline-tools/latest/bin/sdkmanager" ]; then
        echo "Downloading Android command-line tools..."
        curl -s -o /tmp/cmdline-tools.zip "https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip"
        unzip -q /tmp/cmdline-tools.zip -d /tmp/cmdline-extract
        mv /tmp/cmdline-extract/cmdline-tools "$ANDROID_SDK_DIR/cmdline-tools/latest"
        rm -f /tmp/cmdline-tools.zip
    fi

    echo "Accepting licenses..."
    yes | sdkmanager --licenses > /dev/null 2>&1 || true

    echo "Installing SDK components..."
    sdkmanager "platform-tools" "platforms;android-35" "build-tools;35.0.1"
    echo "SDK setup complete!"
    echo ""
fi

# Write local.properties
echo "sdk.dir=$ANDROID_HOME" > "$WORKSPACE_DIR/local.properties"
echo "SDK path written to local.properties"
echo ""

echo "Starting Gradle build..."
"$WORKSPACE_DIR/gradlew" assembleDebug 2>&1
BUILD_EXIT=${PIPESTATUS[0]}

APK_PATH="$WORKSPACE_DIR/app/build/outputs/apk/debug/app-debug.apk"
if [ -f "$APK_PATH" ]; then
    echo ""
    echo "=== Build Successful! ==="
    echo "APK: $APK_PATH"
    ls -lh "$APK_PATH"
else
    echo ""
    echo "=== Build Failed ==="
    exit 1
fi
