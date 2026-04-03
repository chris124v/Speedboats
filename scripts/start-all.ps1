$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $PSScriptRoot

function Get-ListeningProcessInfo {
	param([Parameter(Mandatory=$true)][int]$Port)

	$info = [ordered]@{ InUse = $false; Port = $Port; Pid = $null; Process = $null }

	if (Get-Command Get-NetTCPConnection -ErrorAction SilentlyContinue) {
		$conn = Get-NetTCPConnection -LocalPort $Port -State Listen -ErrorAction SilentlyContinue | Select-Object -First 1
		if ($conn) {
			$info.InUse = $true
			$info.Pid = $conn.OwningProcess
			$proc = Get-Process -Id $info.Pid -ErrorAction SilentlyContinue
			if ($proc) { $info.Process = $proc.ProcessName }
			return [pscustomobject]$info
		}
	}

	$match = netstat -ano | Select-String -Pattern ":${Port}\s+.*LISTENING\s+(\d+)" | Select-Object -First 1
	if ($match -and $match.Matches.Count -gt 0) {
		$info.InUse = $true
		$info.Pid = [int]$match.Matches[0].Groups[1].Value
		$proc = Get-Process -Id $info.Pid -ErrorAction SilentlyContinue
		if ($proc) { $info.Process = $proc.ProcessName }
	}

	return [pscustomobject]$info
}

$tours = Get-ListeningProcessInfo -Port 8001
if ($tours.InUse) {
	Write-Host ("Skipping tours-service: port 8001 already in use (PID {0}{1})." -f $tours.Pid, $(if ($tours.Process) { ", $($tours.Process)" } else { "" }))
} else {
	Start-Process powershell -ArgumentList @('-NoExit','-Command', "Set-Location '$root'; .\\scripts\\start-tours.ps1")
}

$users = Get-ListeningProcessInfo -Port 8002
if ($users.InUse) {
	Write-Host ("Skipping users-service: port 8002 already in use (PID {0}{1})." -f $users.Pid, $(if ($users.Process) { ", $($users.Process)" } else { "" }))
} else {
	Start-Process powershell -ArgumentList @('-NoExit','-Command', "Set-Location '$root'; .\\scripts\\start-users.ps1")
}

$bookings = Get-ListeningProcessInfo -Port 8003
if ($bookings.InUse) {
	Write-Host ("Skipping bookings-service: port 8003 already in use (PID {0}{1})." -f $bookings.Pid, $(if ($bookings.Process) { ", $($bookings.Process)" } else { "" }))
} else {
	Start-Process powershell -ArgumentList @('-NoExit','-Command', "Set-Location '$root'; .\\scripts\\start-bookings.ps1")
}


$npmAvailable = (Get-Command npm -ErrorAction SilentlyContinue)
if (-not $npmAvailable) {
	$nodeDir = Join-Path $env:ProgramFiles 'nodejs'
	if ((Test-Path (Join-Path $nodeDir 'node.exe')) -and (Test-Path (Join-Path $nodeDir 'npm.cmd'))) {
		$npmAvailable = $true
	}
}

if (-not $npmAvailable) {
	Write-Host "Skipping frontend: npm is not installed / not on PATH (install Node.js LTS)."
} else {
	$frontend = Get-ListeningProcessInfo -Port 3000
	if ($frontend.InUse) {
		Write-Host ("Skipping frontend: port 3000 already in use (PID {0}{1})." -f $frontend.Pid, $(if ($frontend.Process) { ", $($frontend.Process)" } else { "" }))
	} else {
		Start-Process powershell -ArgumentList @('-NoExit','-Command', "Set-Location '$root'; .\\scripts\\start-frontend.ps1")
	}
}

Write-Host 'Start-all complete (services are started in new PowerShell windows when needed).'
Write-Host 'Tours:     http://localhost:8001/api/tours'
Write-Host 'Users:     http://localhost:8002/api/users'
Write-Host 'Bookings:  http://localhost:8003/api/bookings'
Write-Host 'Frontend:  http://localhost:3000 (or the next free port if 3000 is busy)'
