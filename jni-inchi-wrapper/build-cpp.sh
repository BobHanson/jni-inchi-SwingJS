#!/bin/bash
export VERSION=1.3
g++ -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -I"inchi" -I"cpp-src" -Wl,-linchi,-L"inchi" -shared -o "cpp-bin/libJniInchi.so" "cpp-src/JniInchiWrapper.cpp"
strip "cpp-bin/libJniInchi.so"
mv cpp-bin/libJniInchi.so cpp-bin/libJniInchi.$VERSION.so
