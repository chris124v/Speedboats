$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $PSScriptRoot
Set-Location $root

if (-not (Get-Command npm -ErrorAction SilentlyContinue)) {
	Write-Error "npm no está instalado o no está en el PATH. Instala Node.js (incluye npm) y vuelve a intentar."
}

npm install
npm run build
npm run preview
