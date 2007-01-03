#!/bin/bash
export VERSION=1.4
g++ -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -I"src/main/native" -Wl,-linchi,-L"inchi" -shared -o "src/main/resources/libJniInchi.so" "src/main/native/JniInchiWrapper.cpp"
strip "src/main/resources/libJniInchi.so"
mv src/main/resources/libJniInchi.so src/main/resources/libJniInchi.$VERSION.so
