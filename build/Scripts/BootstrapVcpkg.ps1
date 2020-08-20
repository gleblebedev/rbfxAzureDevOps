$vcpkgFolder = Get-Item $PSScriptRoot/../../rbfx/vcpkg

If ($IsWindows) {
    Invoke-Expression "$vcpkgFolder/bootstrap-vcpkg.bat"
}
else {
    Invoke-Expression "$vcpkgFolder/bootstrap-vcpkg.sh"
}
