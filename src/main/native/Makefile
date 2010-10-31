#
# Makefile for JNI-InChI
#
# Author: Sam Adams <sea36 at users.sourceforge.net>
#         Unilever Centre for Molecular Science Informatics
#         University Of Cambrige, UK
#

# Current native code version
VERSION = 1.03_1

ifndef JAVA_HOME
f := $(error JAVA_HOME is not defined, Ensure that it is pointing to a JDK)
endif

JAVAH = "$(JAVA_HOME)/bin/javah"
# TODO $(error Cannot find javah. Ensure JAVA_HOME is correctly set and pointing to a JDK)


# Include OS specific configuration

# PLATFORM environment variable must be set
ifndef PLATFORM
f := $(error PLATFORM is not defined)
endif

LIB_VERSION = $(VERSION)-$(PLATFORM)

TARGET_PATH = ../../../target/native/$(LIB_VERSION)
TARGET_FILE = $(LIB_PREFIX)JniInchi-$(LIB_VERSION)$(LIB_SUFFIX)
TARGET = $(TARGET_PATH)/$(TARGET_FILE)
JAR_WORKDIR = $(TARGET_PATH)/jar
JAR = $(TARGET_PATH)/jniinchi-$(LIB_VERSION).jar

OBJDIR = $(TARGET_PATH)/o

INCHI_OBJDIR = $(OBJDIR)/inchi

JAVA_SOURCE = ../java/net/sf/jniinchi/JniInchiWrapper.java
JAVA_CLASS = net.sf.jniinchi.JniInchiWrapper
JAVA_CLASSPATH = ../../../target/classes
JAVA_CLASS_FILE = net/sf/jniinchi/JniInchiWrapper.class

JAVAH_FLAGS += -classpath $(JAVA_CLASSPATH)
JAVAH_CMD = $(JAVAH) $(JAVAH_FLAGS) $(OUTPUT_OPTION)

RESOURCE_PATH = ../resources
ARTIFACT_PATH = META-INF/jniinchi/$(VERSION)/$(PLATFORM)

_INCHI_OBJECTS =	ichi_bns.o  ichimake.o  ichiprt3.o  ichirvr5.o     inchi_dll.o      \
                    ichi_io.o   ichimap1.o  ichiqueu.o  ichirvr6.o     inchi_dll_a.o    \
                    ichican2.o  ichimap2.o  ichiread.o  ichirvr7.o     inchi_dll_a2.o   \
                    ichicano.o  ichimap4.o  ichiring.o  ichisort.o     inchi_dll_main.o \
                    ichicans.o  ichinorm.o  ichirvr1.o  ichister.o     runichi.o        \
                    ichiisot.o  ichiparm.o  ichirvr2.o  ichitaut.o     sha2.o           \
                    ichilnct.o  ichiprt1.o  ichirvr3.o  ikey_base26.o  strutil.o        \
                    ichimak2.o  ichiprt2.o  ichirvr4.o  ikey_dll.o     util.o           

INCHI_SRCDIR = inchi-1.03
INCHI_OBJECTS = $(addprefix $(INCHI_OBJDIR)/,$(_INCHI_OBJECTS))

JNI_SRCDIR = jni-inchi
JNI_OBJDIR = $(OBJDIR)/jni
JNI_OBJECTS = $(JNI_OBJDIR)/JniInchiWrapper.o
JNI_HEADER_DIR = ../../../target/generated-sources
JNI_HEADERS = $(JNI_HEADER_DIR)/net_sf_jniinchi_JniInchiWrapper.h


ifneq (,$(findstring WINDOWS, $(PLATFORM)))
include Makefile.windows
else
ifneq (,$(findstring LINUX, $(PLATFORM)))
 include Makefile.linux
else
 ifneq (,$(findstring MAC, $(PLATFORM)))
  include Makefile.mac
 else
  ifneq (,$(findstring FREEBSD, $(PLATFORM)))
   include Makefile.freebsd
  else
   ifeq ($(PLATFORM),SUNOS-X86)
    include Makefile.solaris.x86
   else
    ifeq ($(PLATFORM),SUNOS-AMD64)
     include Makefile.solaris.amd64
    else
     f := $(error Platform '$(PLATFORM)' not supported)
    endif
   endif
  endif
 endif
endif
endif

ifneq (,$(findstring WINDOWS-AMD64, $(PLATFORM)))
 include Makefile.vs
else
 include Makefile.gcc
endif

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
	-@ echo "<manifest id=\"jniinchi\" version=\"$(VERSION)\" platform=\"$(PLATFORM)\" >" > $(JAR_WORKDIR)/$(ARTIFACT_PATH)/MANIFEST.xml
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


$(JNI_HEADERS): $(JAVA_SOURCE) $(JAVA_CLASSPATH)/$(JAVA_CLASS_FILE) $(JNI_HEADER_DIR)
	$(JAVAH_CMD) -o $@ $(JAVA_CLASS)

$(JNI_HEADER_DIR):
	mkdir -p $(JNI_HEADER_DIR)	

$(JAVA_CLASSPATH)/$(JAVA_CLASS_FILE):
	mvn -f ../../../pom.xml compile

.PHONY: debug
debug:
	-@ echo "OS: $(LIB_OS)"
	-@ echo "ARCH: $(LIB_ARCH)"


jniinchi-$(VERSION)-%.jar:
	$(RM) -r $(TARGET_PATH)/jartemp
	mkdir -p $(TARGET_PATH)/jartemp/META-INF/jniinchi/$(VERSION)/$*
	cp ../resources/META-INF/jniinchi/$(VERSION)/$*/* $(TARGET_PATH)/jartemp/META-INF/jniinchi/$(VERSION)/$*
	jar -cMf ../../../jniinchi-$(VERSION)-$*.jar -C $(TARGET_PATH)/jartemp META-INF
