param(
  [Parameter(ValueFromRemainingArguments = $true)]
  [string[]]$GradleArgs
)

$ErrorActionPreference = 'Stop'
$serviceRoot = Split-Path -Parent $PSScriptRoot
Set-Location $serviceRoot

$gradleVersion = '8.8'
$distDir = Join-Path $serviceRoot '.gradle-dist'
$gradleHome = Join-Path $distDir "gradle-$gradleVersion"
$gradleBat = Join-Path $gradleHome 'bin/gradle.bat'

if (!(Test-Path $gradleBat)) {
  New-Item -ItemType Directory -Force -Path $distDir | Out-Null
  $zipPath = Join-Path $distDir "gradle-$gradleVersion-bin.zip"
  $extractDir = Join-Path $distDir '_extract'

  $ProgressPreference = 'SilentlyContinue'
  Invoke-WebRequest "https://services.gradle.org/distributions/gradle-$gradleVersion-bin.zip" -OutFile $zipPath

  if (Test-Path $extractDir) { Remove-Item $extractDir -Recurse -Force }
  Expand-Archive -Path $zipPath -DestinationPath $extractDir -Force

  $extractedHome = Join-Path $extractDir "gradle-$gradleVersion"
  if (!(Test-Path $extractedHome)) {
    throw "Unexpected Gradle archive layout at $extractDir"
  }

  if (Test-Path $gradleHome) { Remove-Item $gradleHome -Recurse -Force }
  Move-Item -Path $extractedHome -Destination $gradleHome
  Remove-Item $extractDir -Recurse -Force
}

& $gradleBat @GradleArgs
