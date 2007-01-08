set VERSION=1.4
g++ -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" -I"src\main\native" -Wl,--add-stdcall-alias,-llibinchi,-L"inchi" -O3 -shared -o "src\main\resources\JniInchi.%VERSION%.dll" src\main\native\JniInchiWrapper.cpp
strip src\main\resources\JniInchi.%VERSION%.dll

