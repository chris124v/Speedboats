#!/usr/bin/env sh
set -eu
ROOT="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)/.."
cd "$ROOT/tours-service"
./scripts/run.sh
