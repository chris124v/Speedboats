$ErrorActionPreference = 'Stop'
$serviceRoot = Split-Path -Parent $PSScriptRoot
Set-Location $serviceRoot

New-Item -ItemType Directory -Force -Path (Join-Path $serviceRoot 'data') | Out-Null

./scripts/gradle.ps1 run
