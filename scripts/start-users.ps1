$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $PSScriptRoot

function Test-PortListening {
	param([Parameter(Mandatory=$true)][int]$Port)
	if (Get-Command Get-NetTCPConnection -ErrorAction SilentlyContinue) {
		return (Get-NetTCPConnection -LocalPort $Port -State Listen -ErrorAction SilentlyContinue | Select-Object -First 1) -ne $null
	}
	return (netstat -ano | Select-String -Pattern ":${Port}\s+.*LISTENING\s+\d+" | Select-Object -First 1) -ne $null
}

if (Test-PortListening -Port 8002) {
	Write-Host 'users-service is already running (port 8002 is in use).'
	return
}

Set-Location (Join-Path $root 'users-service')
.\scripts\run.ps1
