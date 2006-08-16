#!/bin/bash
g++ -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -I"inchi" -I"jni-inchi-wrapper/cpp" -Wl,-linchi,-L"inchi" -shared -o "cpp-bin/libJniInchi.so" "jni-inchi-wrapper/cpp/JniInchiWrapper.cpp"
strip "cpp-bin/libJniInchi.so"
