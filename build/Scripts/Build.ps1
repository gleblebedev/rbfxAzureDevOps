New-Item -Force -ItemType "directory" -Path $PSScriptRoot/../../rbfxbuild

cd $PSScriptRoot/../../rbfxbuild

cmake -DCMAKE_TOOLCHAIN_FILE=$PSScriptRoot/../../rbfx/vcpkg/scripts/buildsystems/vcpkg.cmake -DBUILD_SHARED_LIBS=ON -DURHO3D_GLOW=OFF -DURHO3D_FEATURES="CSHARP;SYSTEMUI" ../rbfx

