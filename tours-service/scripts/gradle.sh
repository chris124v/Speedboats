#!/usr/bin/env sh
set -eu
SERVICE_ROOT="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)/.."
cd "$SERVICE_ROOT"

GRADLE_VERSION="8.8"
DIST_DIR="$SERVICE_ROOT/.gradle-dist"
GRADLE_HOME="$DIST_DIR/gradle-$GRADLE_VERSION"
GRADLE_BIN="$GRADLE_HOME/bin/gradle"

if [ ! -x "$GRADLE_BIN" ]; then
  mkdir -p "$DIST_DIR"
  ZIP_PATH="$DIST_DIR/gradle-$GRADLE_VERSION-bin.zip"
  EXTRACT_DIR="$DIST_DIR/_extract"

  if command -v curl >/dev/null 2>&1; then
    curl -fsSL "https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip" -o "$ZIP_PATH"
  elif command -v wget >/dev/null 2>&1; then
    wget -q "https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip" -O "$ZIP_PATH"
  else
    echo "curl or wget is required to download Gradle" >&2
    exit 1
  fi

  rm -rf "$EXTRACT_DIR"
  mkdir -p "$EXTRACT_DIR"
  unzip -q "$ZIP_PATH" -d "$EXTRACT_DIR"

  rm -rf "$GRADLE_HOME"
  mv "$EXTRACT_DIR/gradle-$GRADLE_VERSION" "$GRADLE_HOME"
  rm -rf "$EXTRACT_DIR"
fi

exec "$GRADLE_BIN" "$@"
