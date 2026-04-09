#!/bin/bash
export ANDROID_HOME=~/android-sdk
export JAVA_HOME=/nix/store/6m2zgvl7y9mrsdckqx4mgxghpmasszbi-openjdk-17.0.6+10
export PATH=$JAVA_HOME/bin:$PATH:~/android-sdk/cmdline-tools/latest/bin:~/android-sdk/platform-tools

echo "=== Panchadika Android APK Build ==="
echo "Android SDK: $ANDROID_HOME"
echo "Java: $(java -version 2>&1 | head -1)"
echo ""
echo "Starting Gradle build..."
./gradlew assembleDebug 2>&1

if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
    echo ""
    echo "=== Build Successful! ==="
    echo "APK location: app/build/outputs/apk/debug/app-debug.apk"
    ls -lh app/build/outputs/apk/debug/app-debug.apk
else
    echo ""
    echo "=== Build Failed or APK not found ==="
fi
