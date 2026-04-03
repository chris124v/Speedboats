$ErrorActionPreference = 'Stop'
$serviceRoot = Split-Path -Parent $PSScriptRoot
Set-Location $serviceRoot

New-Item -ItemType Directory -Force -Path (Join-Path $serviceRoot 'data') | Out-Null

./scripts/gradle.ps1 clean shadowJar
$buildRoot = $env:SPEEDBOATS_BUILD_ROOT
if ([string]::IsNullOrWhiteSpace($buildRoot)) { $buildRoot = 'C:\speedboats-build' }

$jarPath = Join-Path $buildRoot 'bookings-service\libs\bookings-service-0.1-all.jar'
if (!(Test-Path $jarPath)) {
	$jarPath = Join-Path $serviceRoot 'build\libs\bookings-service-0.1-all.jar'
}

java -jar $jarPath
