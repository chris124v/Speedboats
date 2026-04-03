$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $PSScriptRoot
Set-Location $root

if (-not (Get-Command npm -ErrorAction SilentlyContinue)) {
	$nodeDir = Join-Path $env:ProgramFiles 'nodejs'
	if ((Test-Path (Join-Path $nodeDir 'node.exe')) -and (Test-Path (Join-Path $nodeDir 'npm.cmd'))) {
		$env:Path = "$nodeDir;$env:Path"
	}
}

if (-not (Get-Command npm -ErrorAction SilentlyContinue)) {
	Write-Error "npm no está instalado o no está en el PATH. Si ya instalaste Node.js, reinicia VS Code/PowerShell o agrega 'C:\\Program Files\\nodejs' al PATH."
}

npm install
npm run dev
