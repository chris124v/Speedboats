#!/usr/bin/env sh
set -eu
ROOT="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)/.."
cd "$ROOT/bookings-service"
./scripts/run.sh
