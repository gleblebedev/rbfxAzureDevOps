cd %~dp0\..\..\rbfxbuild

cmake -G "Visual Studio 16 2019" -A x64 -DURHO3D_OPENXR=OFF -DURHO3D_SAMPLES=ON -DBUILD_SHARED_LIBS=ON -DURHO3D_GLOW=OFF -DURHO3D_FEATURES="CSHARP;SYSTEMUI" -DURHO3D_NETFX=netstandard2.0 -DURHO3D_PROFILING=OFF -DURHO3D_PLAYER=OFF -DURHO3D_EXTRAS=OFF -DURHO3D_TOOLS=OFF -DURHO3D_RMLUI=ON -DURHO3D_GRAPHICS_API=D3D11 -DSWIG_MODULE_Urho3D_DLLIMPORT=Urho3D -DSWIG_MODULE_ImGui_DLLIMPORT=Urho3D -S %~dp0\..\..\rbfx -B %~dp0\..\..\rbfxbuild

cmake --build . --config RelWithDebInfo --target Urho3D

dotnet restore rbfx.sln

cmake --build . --config RelWithDebInfo --target Urho3DNet
