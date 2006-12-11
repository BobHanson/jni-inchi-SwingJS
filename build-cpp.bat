set VERSION=1.4
g++ -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" -I"inchi" -I"cpp-src" -Wl,--add-stdcall-alias,-llibinchi,-L"inchi" -O3 -shared -o "cpp-bin\JniInchi.%VERSION%.dll" cpp-src\JniInchiWrapper.cpp
strip cpp-bin\JniInchi.%VERSION%.dll

