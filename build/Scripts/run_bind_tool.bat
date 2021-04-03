cd %~dp0..\..\rbfx\Source\Urho3D

python3 "%~dp0..\..\rbfx\Source\Tools\BindTool\BindTool.py" --clang "C:\Program Files\LLVM\bin\clang.exe" --output "%~dp0..\..\rbfx\Source\Urho3D\CSharp\Swig\generated\Urho3D" "%~dp0..\..\rbfxbuild\Source\Urho3D\GeneratorOptions_Urho3D_Debug.txt" "%~dp0..\..\rbfx\Source\Urho3D\BindAll.cpp"
