#!/usr/bin/env bash

SOURCE="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

BUILDFOLDER="$SOURCE/../../cmake-build-sdk"
RBFXFOLDER="$SOURCE/../../rbfx"

mkdir -p $BUILDFOLDER

cmake -DURHO3D_ENABLE_ALL=OFF -DBUILD_SHARED_LIBS=ON -DURHO3D_TOOLS="PackageTool;swig" -S $RBFXFOLDER -B $BUILDFOLDER

cmake --build $BUILDFOLDER --config Release --target swig PackageTool

