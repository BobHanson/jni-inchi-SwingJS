#
# Makefile for JNI-InChI
#
# Author: Sam Adams <sea36@cam.ac.uk>
#         Unilever Centre for Molecular Science Informatics
#         University Of Cambrige, UK
#

# Detect platform, and include correct makefile
UNAME := $(shell uname)
MNAME := $(shell uname -m)

# Detect architecture
ifeq ($(MNAME), x86)
LIB_ARCH = X86
endif
ifeq ($(MNAME), i686)
LIB_ARCH = X86
endif
ifeq ($(MNAME), i586)
LIB_ARCH = X86
endif
ifeq ($(MNAME), i486)
LIB_ARCH = X86
endif
ifeq ($(MNAME), i386)
LIB_ARCH = X86
endif
ifeq ($(MNAME), amd64)
LIB_ARCH = AMD64
endif
ifeq ($(MNAME), a64)
LIB_ARCH = AMD64
endif
ifeq ($(MNAME), x86_64) # eg srcf
LIB_ARCH = AMD64
endif

ifndef LIB_ARCH
f := $(error Architecture $(MNAME) $(LIB_ARCH) not supported)
endif


# Detect OS
ifeq ($(UNAME),Linux)
include Makefile.linux
else
ifeq ($(findstring MINGW,$(UNAME)),MINGW)
include Makefile.windows
else
ifeq ($(findstring CYGWIN,$(UNAME)),CYGWIN)
include Makefile.windows
else
f := $(error Platform $(UNAME) not supported)
endif
endif
endif

VERSION = 1.5

LIB_OS_ARCH = $(LIB_OS)-$(LIB_ARCH)
LIB_VERSION = $(VERSION)-$(LIB_OS_ARCH)

TARGET_PATH = ../../../target/native
TARGET_FILE = $(LIB_PREFIX)JniInchi-$(LIB_VERSION)$(LIB_SUFFIX)
TARGET = $(TARGET_PATH)/$(TARGET_FILE)
JAR_WORKDIR = $(TARGET_PATH)/jar
JAR = $(TARGET_PATH)/JniInchi-$(LIB_VERSION).jar

OBJDIR = $(TARGET_PATH)/o

INCHI_SRCDIR = inchi-1.02-beta
INCHI_OBJDIR = $(OBJDIR)/inchi

JAVA_SOURCE = ../java/net/sf/jniinchi/JniInchiWrapper.java
JAVA_CLASS = net.sf.jniinchi.JniInchiWrapper
JAVA_CLASSPATH = ../../../target/classes
JAVA_CLASS_FILE = net/sf/jniinchi/JniInchiWrapper.class
JAVAH = "$(JAVA_HOME)/bin/javah"
JAVAH_FLAGS += -classpath $(JAVA_CLASSPATH)
JAVAH_CMD = $(JAVAH) $(JAVAH_FLAGS) $(OUTPUT_OPTION)

RESOURCE_PATH = ../resources
ARTIFACT_PATH = META-INF/jnati/jniinchi/$(VERSION)/$(LIB_OS_ARCH)

LDFLAGS += -s -shared
CXXFLAGS += -Os -I"${JAVA_HOME}/include" -I$(JNI_SRCDIR) -I$(INCHI_SRCDIR)
CFLAGS += -Os

_INCHI_OBJECTS =	ichican2.o ichicano.o ichicans.o ichiisot.o \
					ichilnct.o ichimak2.o ichimake.o ichimap1.o \
					ichimap2.o ichimap4.o ichinorm.o ichiparm.o \
					ichiprt1.o ichiprt2.o ichiprt3.o ichiqueu.o \
					ichiread.o ichiring.o ichirvr1.o ichirvr2.o \
					ichirvr3.o ichirvr4.o ichirvr5.o ichirvr6.o \
					ichirvr7.o ichisort.o ichister.o ichitaut.o \
					ichi_bns.o ikey_base26.o ikey_dll.o inchi_dll.o \
					inchi_dll_a.o inchi_dll_a2.o inchi_dll_main.o \
					runichi.o sha2.o strutil.o util.o

INCHI_OBJECTS = $(addprefix $(INCHI_OBJDIR)/,$(_INCHI_OBJECTS))

JNI_SRCDIR = jni-inchi
JNI_OBJDIR = $(OBJDIR)/jni
JNI_OBJECTS = $(JNI_OBJDIR)/JniInchiWrapper.o
JNI_HEADER = $(JNI_SRCDIR)/net_sf_jniinchi_JniInchiWrapper.h


all: copies
.PHONY: all

.PHONY: clean
clean:
	$(RM) $(TARGET) $(JNI_OBJECTS) $(INCHI_OBJECTS) $(JNI_HEADER)
	$(RM) -r $(JNI_OBJDIR) $(INCHI_OBJDIR) $(OBJDIR)

.PHONY: jar
jar: buildresource
	-@ jar -cMf $(JAR) -C $(JAR_WORKDIR) META-INF

.PHONY: buildresource
buildresource: build
	mkdir -p $(JAR_WORKDIR)/$(ARTIFACT_PATH)
	-@ echo "<manifest id=\"jniinchi\" version=\"$(VERSION)\" platform=\"$(LIB_OS)\" architecture=\"$(LIB_ARCH)\">" > $(JAR_WORKDIR)/$(ARTIFACT_PATH)/MANIFEST.xml
	-@ echo "  <file library=\"true\">$(TARGET_FILE)</file>" >> $(JAR_WORKDIR)/$(ARTIFACT_PATH)/MANIFEST.xml
	-@ echo "</manifest>" >> $(JAR_WORKDIR)/$(ARTIFACT_PATH)/MANIFEST.xml
	-@ cp $(TARGET) $(JAR_WORKDIR)/$(ARTIFACT_PATH)/$(TARGET_FILE)

.PHONY: copies
copies: resourcecopy buildcopy

.PHONY: resourcecopy 
resourcecopy: buildresource
	-@ echo Copying to $(RESOURCE_PATH)
	-@ cp -r $(JAR_WORKDIR)/META-INF $(RESOURCE_PATH)

.PHONY: buildcopy
buildcopy: buildresource
	-@ echo Copying to $(JAVA_CLASSPATH)
	-@ cp -r $(JAR_WORKDIR)/META-INF $(JAVA_CLASSPATH)

.PHONY: build
build: nativedirs $(TARGET)

.PHONY: nativedirs
nativedirs:
	mkdir -p $(TARGET_PATH)
	mkdir -p $(INCHI_OBJDIR)
	mkdir -p $(JNI_OBJDIR)

$(TARGET): $(INCHI_OBJECTS) $(JNI_OBJECTS)
	$(LINK.cc) $^ -o $@

$(INCHI_OBJDIR)/%.o: $(INCHI_SRCDIR)/%.c
	$(COMPILE.c) $< -o $@

$(JNI_OBJDIR)/%.o: $(JNI_SRCDIR)/%.cc $(JNI_HEADER)
	$(COMPILE.cc) $< -o $@

$(JNI_HEADER): $(JAVA_SOURCE) $(JAVA_CLASSPATH)/$(JAVA_CLASS_FILE)
	$(JAVAH_CMD) -o $@ $(JAVA_CLASS)

$(JAVA_CLASSPATH)/$(JAVA_CLASS_FILE):
	f := $(error Class file '$(JAVA_CLASSPATH)/$(JAVA_CLASS_FILE)' not found. Run 'mvn compile' first)
