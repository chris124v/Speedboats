#!/usr/bin/env sh
set -eu
ROOT="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)/.."
cd "$ROOT"

npm install
npm run build
npm run preview
