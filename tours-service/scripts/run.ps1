$ErrorActionPreference = 'Stop'
$serviceRoot = Split-Path -Parent $PSScriptRoot
Set-Location $serviceRoot

New-Item -ItemType Directory -Force -Path (Join-Path $serviceRoot 'data') | Out-Null

# Runs the Micronaut service in dev mode
./scripts/gradle.ps1 run
