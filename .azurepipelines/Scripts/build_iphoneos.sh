#!/usr/bin/env bash

SOURCE="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

BUILDFOLDER="$SOURCE/../../cmake-build-ios"
RBFXFOLDER="$SOURCE/../../rbfx"

mkdir -p $BUILDFOLDER

cmake -G Xcode -DCMAKE_TOOLCHAIN_FILE=$RBFXFOLDER/CMake/Toolchains/IOS.cmake -DURHO3D_SDK=$RBFXFOLDER/sdk -DENABLE_BITCODE=OFF -DPLATFORM=OS64COMBINED -DDEPLOYMENT_TARGET=12 -DURHO3D_COMPUTE=OFF -DURHO3D_GRAPHICS_API=GLES3 -DBUILD_SHARED_LIBS=ON -DURHO3D_GLOW=ON -DURHO3D_FEATURES="CSHARP;SYSTEMUI" -DURHO3D_NETFX=netstandard2.0 -DURHO3D_PROFILING=OFF -DURHO3D_PLAYER=OFF -DURHO3D_EXTRAS=OFF -DURHO3D_TOOLS=OFF -DURHO3D_RMLUI=ON -DSWIG_MODULE_Urho3D_DLLIMPORT=__Internal -DSWIG_MODULE_ImGui_DLLIMPORT=__Internal -S $RBFXFOLDER -B $BUILDFOLDER

#dotnet tool run ezpipeline xcode-setbuildsystemtype -i $BUILDFOLDER --build-system-type Original

/usr/bin/xcodebuild -sdk iphoneos -arch arm64 -configuration Debug -project $BUILDFOLDER/rbfx.xcodeproj -list -verbose OTHER_CFLAGS="-v" CODE_SIGNING_ALLOWED=NO CODE_SIGN_IDENTITY="-"

/usr/bin/xcodebuild -sdk iphoneos -arch arm64 -configuration Debug -project $BUILDFOLDER/rbfx.xcodeproj -target Urho3D build -verbose OTHER_CFLAGS="-v" CODE_SIGNING_ALLOWED=NO CODE_SIGN_IDENTITY="-"
