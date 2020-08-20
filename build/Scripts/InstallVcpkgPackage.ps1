param (
    [Parameter(Mandatory=$true)][string[]]$Packages,
    [Parameter(Mandatory=$false)][string[]]$Triplets
)

Set-Location -Path $PSScriptRoot/../../rbfx/vcpkg

If ($IsWindows) {
    $vcpkg = Get-Item vcpkg.exe
}
else {
    $vcpkg = Get-Item vcpkg
}

If (!$Triplets) {
    If ($IsWindows) {
        $Triplets = @('x64-windows', 'x64-uwp');
    }
    elseif ($IsLinux) {
        $Triplets = @('x64-linux');
    }
    else {
        $Triplets = @('x64-osx');
    }
}

foreach ($Package in $Packages)
{
    $allTriplets = $Triplets | Join-String -Property {"${Package}:$_"} -Separator ' '
    $cmdline = "${vcpkg} install ${allTriplets}"
    Write-Host $cmdline
    Invoke-Expression "${cmdline}"
}
