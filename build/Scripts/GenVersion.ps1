param (
    [Parameter(Mandatory=$true)][string]$versionTemplateFile = "version.txt",
    [Parameter(Mandatory=$false)][string]$envVariableName = "VERSION"
)

$lastCommit = & git log -n1 --pretty=format:%H -- $versionTemplateFile
Write-Host "Last version template file commit: $lastCommit"

[int]$gitTreeHeight = & git rev-list --first-parent --count "$lastCommit..HEAD"
Write-Host "Git tree height since last version update: $gitTreeHeight"

$gitBranchName = & git rev-parse --abbrev-ref=strict HEAD
$gitCurrentCommit = & git rev-parse --short HEAD
Write-Host "Current branch $gitBranchName at commit $gitCurrentCommit"

$version = [Version]([IO.File]::ReadAllText("$versionTemplateFile"))
Write-Host "Version template: $version"

$version = New-Object System.Version($version.Major, $version.Minor, $version.Build, $gitTreeHeight)
Write-Host "Modified version: $version"

if ($gitBranchName -ne "master")
{
	$versionString = "$version-alpha$gitCurrentCommit"
}
else
{
	$versionString = "$version"
}

Write-Host "##vso[task.setvariable variable=$envVariableName]$versionString"
Write-Host "$envVariableName environment variable set to $versionString"
