g++ -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" -I"inchi" -I"jni-inchi-wrapper\cpp" -Wl,--add-stdcall-alias,-llibinchi,-L"inchi" -O3 -shared -o "cpp-bin\JniInchi.dll" jni-inchi-wrapper\cpp\JniInchiWrapper.cpp
strip cpp-bin\JniInchi.dll
