#
# Makefile for JniInchi
#

LIBNAME = JniInchi
CODE_VERSION = 1.5

TARGET_DIR = ../../../target/native
OBJDIR = $(TARGET_DIR)/o
JNI_OBJDIR = $(OBJDIR)
INCHI_OBJDIR = $(OBJDIR)/inchi

JNI_OBJECTS = $(OBJDIR)/JniInchiWrapper.o
JNI_HEADERS = net_sf_jniinchi_JniInchiWrapper.h

JAVA_CLASS = net.sf.jniinchi.JniInchiWrapper
JAVA_BUILD_DIR = ../../../target/classes

_INCHI_LIB_OBJS = ichican2.o       ichicano.o \
                 ichicans.o       ichiisot.o \
                 ichilnct.o       ichimak2.o \
                 ichimake.o       ichimap1.o \
                 ichimap2.o       ichimap4.o \
                 ichinorm.o       ichiparm.o \
                 ichiprt1.o       ichiprt2.o \
                 ichiprt3.o       ichiqueu.o \
                 ichiring.o       ichisort.o \
                 ichister.o       ichitaut.o \
                 ichi_bns.o       inchi_dll.o \
                 ichiread.o       ichirvr1.o \
                 ichirvr2.o       ichirvr3.o \
                 ichirvr4.o       ichirvr5.o \
                 ichirvr6.o       ichirvr7.o \
                 inchi_dll_main.o runichi.o  \
                 strutil.o        util.o
INCHI_OBJECTS = $(addprefix $(OBJDIR)/inchi/,$(_INCHI_LIB_OBJS))

# classpath for javah
JAVAH_CLASSPATH = $(JAVA_BUILD_DIR)

# Javah bits
JAVAH = "$(JAVA_HOME)/bin/javah"
JAVAH_FLAGS += -classpath $(JAVAH_CLASSPATH)
JAVAH_CMD = $(JAVAH) $(JAVAH_FLAGS) $(OUTPUT_OPTION)
JAVA_CLASS_FILE = $(addsuffix .class, $(subst .,/,$(JAVA_CLASS)))

# Preprocessor and compiler flags
CXXFLAGS = -I. -Iinchi -I"${JAVA_HOME}/include"
CFLAGS = -Os


# Linker flags
LDFLAGS = -s -shared


# os-dependent bits
UNAME := $(shell uname)
OSNAME := $(shell uname -o)
PLATNAME := $(shell uname -m)

ifeq ($(UNAME),Linux)
LINUX = 1
OS = "LINUX"
else
ifeq ($(findstring MINGW,$(UNAME)),MINGW)
WINDOWS = 1
OS = "WINDOWS"
else
ifeq ($(findstring CYGWIN,$(UNAME)),CYGWIN)
WINDOWS = 1
OS = "WINDOWS"
else
f := $(error Platform $(UNAME) not supported)
endif
endif
endif

PLAT = "X86"

OSPLAT := ${OS}-${PLAT}


ifdef LINUX
LIB_PREFIX = lib
LIB_EXTN = .so
CXXFLAGS += -I$(JAVA_HOME)/include/linux -fPIC
#LDLIBS=-linchi -L$(TARGET_DIR)
TARGET = $(TARGET_DIR)/$(LIB_PREFIX)$(LIBNAME)$(LIB_EXTN).$(CODE_VERSION)
else
ifdef WINDOWS
LIB_PREFIX = lib
LIB_EXTN = .dll
CXXFLAGS += -Wall -Os -I"$(JAVA_HOME)/include/win32"
LDFLAGS += -Wl,--add-stdcall-alias
#LDLIBS=-linchi.1.01.00 -L$(TARGET_DIR)
TARGET = $(TARGET_DIR)/$(LIB_PREFIX)$(LIBNAME).$(CODE_VERSION)$(LIB_EXTN)
endif
endif


all: $(TARGET)

clean:
	$(RM) $(JNI_HEADERS)
	$(RM) $(JNI_OBJECTS)
	$(RM) $(INCHI_OBJECTS)
	rmdir $(OBJDIR)/inchi
	rmdir $(OBJDIR)

$(TARGET): $(JNI_OBJECTS) $(INCHI_OBJECTS)
	$(LINK.cpp) $(JNI_OBJECTS) $(INCHI_OBJECTS) $(LDFLAGS) $(LDLIBS) -o $(TARGET)


$(OBJDIR)/inchi/%.o: $(INCHI_OBJDIR)
	$(CC) $(CFLAGS) -c -o $@ inchi/$*.c

$(OBJDIR)/%.o: $(JNI_HEADERS) $(JNI_OBJDIR)
	-@ echo compiling $@
	$(CC) $(CFLAGS) $(CXXFLAGS) -c -o $@ $*.cc

# Generate header file, using javah
$(JNI_HEADERS): $(JAVA_BUILD_DIR)/$(JAVA_CLASS_FILE)
	$(RM) $@
	$(JAVAH) $(JAVAH_FLAGS) $(JAVA_CLASS)

$(JNI_OBJDIR):
	mkdir -p $@
	
$(INCHI_OBJDIR):
	mkdir -p $@
	
	