#!/usr/bin/env bash

SOURCE="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

BUILDFOLDER="$SOURCE/../../rbfxbuild"
RBFXFOLDER="$SOURCE/../../rbfx"

mkdir $BUILDFOLDER
cd $BUILDFOLDER

#-T buildsystem=1
cmake -DBUILD_SHARED_LIBS=ON -DURHO3D_GLOW=OFF -DURHO3D_FEATURES="CSHARP;SYSTEMUI" -DURHO3D_NETFX=netstandard2.0 -DURHO3D_PROFILING=OFF -DURHO3D_PLAYER=OFF -DURHO3D_EXTRAS=OFF -DURHO3D_TOOLS=OFF -DURHO3D_RMLUI=OFF -DSWIG_MODULE_Urho3D_DLLIMPORT=Urho3D -DSWIG_MODULE_ImGui_DLLIMPORT=Urho3D -S $RBFXFOLDER -B $BUILDFOLDER
