#
# Makefile for JNI-InChI
#
# Author: Sam Adams <sea36 at users.sourceforge.net>
#         Unilever Centre for Molecular Science Informatics
#         University Of Cambrige, UK
#

# Native code version
VERSION = 1.7

ifndef JAVA_HOME
f := $(error JAVA_HOME is not defined)
endif

# Detect OS/Architecture
LIB_OS := $(shell mvn -q -f ../../../pom.xml exec:java -Dexec.mainClass=net.sf.jnati.SystemTool -Dexec.args=os)
LIB_ARCH := $(shell mvn -q -f ../../../pom.xml exec:java -Dexec.mainClass=net.sf.jnati.SystemTool -Dexec.args=arch)


# Include OS specific configuration
# Platform specific files must set:
#	LIB_PREFIX
#	LIB_SUFFIX
ifeq ($(LIB_OS),WINDOWS)
include Makefile.windows
else
ifeq ($(LIB_OS),LINUX)
include Makefile.linux
else
ifeq ($(LIB_OS),MAC)
include Makefile.mac
else
f := $(error Platform $(LIB_OS) not supported)
endif
endif
endif



LIB_OS_ARCH = $(LIB_OS)-$(LIB_ARCH)
LIB_VERSION = $(VERSION)-$(LIB_OS_ARCH)

TARGET_PATH = ../../../target/native
TARGET_FILE = $(LIB_PREFIX)JniInchi-$(LIB_VERSION)$(LIB_SUFFIX)
TARGET = $(TARGET_PATH)/$(TARGET_FILE)
JAR_WORKDIR = $(TARGET_PATH)/jar
JAR = $(TARGET_PATH)/jniinchi-$(LIB_VERSION).jar

OBJDIR = $(TARGET_PATH)/o/$(LIB_OS_ARCH)


#INCHI_SRCDIR = inchi-1.02-beta

#new inchi src dir
INCHI_SRCDIR = inchi-1-API

INCHI_OBJDIR = $(OBJDIR)/inchi

JAVA_SOURCE = ../java/net/sf/jniinchi/JniInchiWrapper.java
JAVA_CLASS = net.sf.jniinchi.JniInchiWrapper
JAVA_CLASSPATH = ../../../target/classes
JAVA_CLASS_FILE = net/sf/jniinchi/JniInchiWrapper.class


JAVAH = "$(JAVA_HOME)/bin/javah"
# f := $(error Cannot find javah. Ensure JAVA_HOME is correctly set.)

JAVAH_FLAGS += -classpath $(JAVA_CLASSPATH)
JAVAH_CMD = $(JAVAH) $(JAVAH_FLAGS) $(OUTPUT_OPTION)

RESOURCE_PATH = ../resources
ARTIFACT_PATH = META-INF/jniinchi/$(VERSION)/$(LIB_OS_ARCH)

LDFLAGS += -s
CFLAGS += -I"${JAVA_HOME}/include" -I$(JNI_SRCDIR) -I$(INCHI_SRCDIR)
CFLAGS += -Os

#_INCHI_OBJECTS =	ichican2.o ichicano.o ichicans.o ichiisot.o \
#					ichilnct.o ichimak2.o ichimake.o ichimap1.o \
#					ichimap2.o ichimap4.o ichinorm.o ichiparm.o \
#					ichiprt1.o ichiprt2.o ichiprt3.o ichiqueu.o \
#					ichiread.o ichiring.o ichirvr1.o ichirvr2.o \
#					ichirvr3.o ichirvr4.o ichirvr5.o ichirvr6.o \
#					ichirvr7.o ichisort.o ichister.o ichitaut.o \
#					ichi_bns.o ikey_base26.o ikey_dll.o inchi_dll.o \
#					inchi_dll_a.o inchi_dll_a2.o inchi_dll_main.o \
#					runichi.o sha2.o strutil.o util.o
#

_INCHI_OBJECTS =	ichi_bns.o \
			ichi_io.o \
			ichican2.o \
			ichicano.o \
			ichicans.o \
			ichiisot.o \
			ichilnct.o \
			ichimak2.o \
			ichimake.o \
			ichimap1.o \
			ichimap2.o \
			ichimap4.o \
			ichinorm.o \
			ichiparm.o \
			ichiprt1.o \
			ichiprt2.o \
			ichiprt3.o \
			ichiqueu.o \
			ichiread.o \
			ichiring.o \
			ichirvr1.o \
			ichirvr2.o \
			ichirvr3.o \
			ichirvr4.o \
			ichirvr5.o \
			ichirvr6.o \
			ichirvr7.o \
			ichisort.o \
			ichister.o \
			ichitaut.o \
			ikey_base26.o \
			ikey_dll.o \
			inchi_dll.o \
			inchi_dll_a.o \
			inchi_dll_a2.o \
			inchi_dll_main.o \
			runichi.o \
			sha2.o \
			strutil.o \
			util.o

INCHI_OBJECTS = $(addprefix $(INCHI_OBJDIR)/,$(_INCHI_OBJECTS))

JNI_SRCDIR = jni-inchi
JNI_OBJDIR = $(OBJDIR)/jni
JNI_OBJECTS = $(JNI_OBJDIR)/JniInchiWrapper.o
JNI_HEADERS = $(JNI_SRCDIR)/net_sf_jniinchi_JniInchiWrapper.h


all: copies
.PHONY: all

.PHONY: clean
clean:
	$(RM) $(TARGET) $(JNI_OBJECTS) $(INCHI_OBJECTS) $(JNI_HEADERS)
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
	$(LINK.c) $^ -o $@

$(INCHI_OBJDIR)/%.o: $(INCHI_SRCDIR)/%.c
	$(COMPILE.c) $< -o $@

# $(JNI_OBJDIR)/%.o: $(JNI_SRCDIR)/%.cc $(JNI_HEADERS)
#	$(COMPILE.cc) $< -o $@
$(JNI_OBJDIR)/%.o: $(JNI_SRCDIR)/%.c $(JNI_HEADERS)
	$(COMPILE.c) $< -o $@

$(JNI_HEADERS): $(JAVA_SOURCE) $(JAVA_CLASSPATH)/$(JAVA_CLASS_FILE)
	$(JAVAH_CMD) -o $@ $(JAVA_CLASS)

$(JAVA_CLASSPATH)/$(JAVA_CLASS_FILE):
	mvn -f ../../../pom.xml compile

.PHONY: debug
debug:
	-@ echo "OS: $(LIB_OS)"
	-@ echo "ARCH: $(LIB_ARCH)"

.PHONY: archives
VERSIONS = $(shell ls ../resources/META-INF/jniinchi/$(VERSION) )
archives: $(addprefix jniinchi-$(VERSION)-, $(addsuffix .jar, $(VERSIONS)))

jniinchi-$(VERSION)-%.jar:
	$(RM) -r $(TARGET_PATH)/jartemp
	mkdir -p $(TARGET_PATH)/jartemp/META-INF/jniinchi/$(VERSION)/$*
	cp ../resources/META-INF/jniinchi/$(VERSION)/$*/* $(TARGET_PATH)/jartemp/META-INF/jniinchi/$(VERSION)/$*
	jar -cMf ../../../jniinchi-$(VERSION)-$*.jar -C $(TARGET_PATH)/jartemp META-INF
