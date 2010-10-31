
# Visual studio based makefile

$(TARGET): $(INCHI_OBJECTS) $(JNI_OBJECTS)
	link.exe /DLL /OUT:$@ $^

$(INCHI_OBJDIR)/%.o: $(INCHI_SRCDIR)/%.c
	cl.exe /c /Ox /I$(INCHI_SRCDIR) /Fo$@ $<

$(JNI_OBJDIR)/%.o: $(JNI_SRCDIR)/%.c $(JNI_HEADERS)
	cl.exe /c /Ox /I$(INCHI_SRCDIR) /I"${JAVA_HOME}/include" /I"${JAVA_HOME}/include/win32" /I$(JNI_HEADER_DIR)  /Fo$@ $<
