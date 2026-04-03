$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $PSScriptRoot

if (-not (Get-Command npm -ErrorAction SilentlyContinue)) {
	$nodeDir = Join-Path $env:ProgramFiles 'nodejs'
	if ((Test-Path (Join-Path $nodeDir 'node.exe')) -and (Test-Path (Join-Path $nodeDir 'npm.cmd'))) {
		$env:Path = "$nodeDir;$env:Path"
	}
}

if (-not (Get-Command npm -ErrorAction SilentlyContinue)) {
	Write-Host 'Skipping frontend: npm is not installed / not on PATH (install Node.js LTS).'
	return
}

Set-Location (Join-Path $root 'frontend')
.\scripts\start.ps1
