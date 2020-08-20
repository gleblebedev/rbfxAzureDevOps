$rootFolder = (Get-Item $PSScriptRoot/../..)

Write-Host "Root folder: $rootFolder"

New-Item -Force -ItemType "directory" -Path $rootFolder/rbfxbuild

Set-Location -Path "$rootFolder/rbfxbuild"

cmake -DCMAKE_TOOLCHAIN_FILE="$rootFolder/rbfx/vcpkg/scripts/buildsystems/vcpkg.cmake" -DBUILD_SHARED_LIBS=ON -DURHO3D_GLOW=OFF -DURHO3D_FEATURES="CSHARP;SYSTEMUI" ../rbfx

