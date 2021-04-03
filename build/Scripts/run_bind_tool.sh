#!/usr/bin/env bash

SOURCE="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

BUILDFOLDER="$SOURCE/../../rbfxbuild"
RBFXFOLDER="$SOURCE/../../rbfx"

python3 $RBFXFOLDER/Source/Tools/BindTool/BindTool.py --output $RBFXFOLDER/Source/Urho3D/CSharp/Swig/generated/Urho3D $BUILDFOLDER/Source/Urho3D/GeneratorOptions_Urho3D_.txt $RBFXFOLDER/Source/Urho3D/BindAll.cpp
