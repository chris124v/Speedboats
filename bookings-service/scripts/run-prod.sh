#!/usr/bin/env sh
set -eu
SERVICE_ROOT="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)/.."
cd "$SERVICE_ROOT"

mkdir -p ./data
chmod +x ./scripts/gradle.sh
./scripts/gradle.sh clean shadowJar
java -jar ./build/libs/bookings-service-0.1-all.jar
