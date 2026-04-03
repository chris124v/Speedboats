#!/usr/bin/env sh
set -eu
ROOT="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)/.."

( cd "$ROOT/tours-service" && ./scripts/run.sh ) &
( cd "$ROOT/users-service" && ./scripts/run.sh ) &
( cd "$ROOT/bookings-service" && ./scripts/run.sh ) &
( cd "$ROOT/frontend" && ./scripts/start.sh ) &

wait
