$vcpkgFolder = Get-Item $PSScriptRoot/../../rbfx/vcpkg

If ($IsWindows) {
    Invoke-Expression "$vcpkgFolder/bootstrap-vcpkg.bat"
    Invoke-Expression "$vcpkgFolder/vcpkg.exe update"
}
else {
    Invoke-Expression "$vcpkgFolder/bootstrap-vcpkg.sh"
    Invoke-Expression "$vcpkgFolder/vcpkg update"
}

