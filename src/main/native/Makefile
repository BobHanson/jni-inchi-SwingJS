#
# Makefile for JniInchi
#

CODE_VERSION = 1.4
SOURCE_FILE = JniInchiWrapper.cpp

OBJECTS = JniInchiWrapper.o
HEADERS = net_sf_jniinchi_JniInchiWrapper.h
JAVA_CLASS = net.sf.jniinchi.JniInchiWrapper

TARGET_DIR = ../../../target/classes

JAVA_BUILD_DIR = ../../../target/classes
CPP_SRC_DIR = .

LIBNAME = JniInchi

JAVAH = $(JAVA_HOME)/bin/javah
JAVAH_FLAGS += -classpath $(JAVAH_CLASSPATH)
JAVAH_CMD = $(JAVAH) $(JAVAH_FLAGS) $(OUTPUT_OPTION)

# Preprocessor and compiler flags
CXXFLAGS = -Wall
CPPFLAGS = -I. -I"${JAVA_HOME}/include"

# Linker flags
LDFLAGS = -shared
LDLIBS=-linchi -L../resources


# os-dependent bits
UNAME := $(shell uname)

ifeq ($(UNAME),Linux)
LIB_PREFIX = lib
LIB_EXTN = .so
CPPFLAGS += -I$(JAVA_HOME)/include/linux
else
ifeq ($(findstring MINGW,$(UNAME)),MINGW)
LIB_PREFIX =
LIB_EXTN = .dll
CPPFLAGS += -I"$(JAVA_HOME)/include/win32"
LDFLAGS += -Wl,--add-stdcall-alias
else
ifeq ($(findstring CYGWIN,$(UNAME)),CYGWIN)
LIB_PREFIX =
LIB_EXTN = .dll
CPPFLAGS += -I"$(JAVA_HOME)/include/win32"
LDFLAGS += -Wl,--add-stdcall-alias
else
f := $(error Platform $(UNAME) not supported)
endif
endif
endif


# the name of the file we build
TARGET = $(TARGET_DIR)/$(LIB_PREFIX)$(LIBNAME).$(CODE_VERSION)$(LIB_EXTN)

# classpath for javah
ifdef CLASSPATH
JAVAH_CLASSPATH = $(JAVA_BUILD_DIR):$(CLASSPATH)
else
JAVAH_CLASSPATH = $(JAVA_BUILD_DIR)
endif



# default target
all: $(TARGET)

clean:
	$(RM) $(HEADERS)
	$(RM) $(OBJECTS)
	$(RM) $(TARGET)

# C++ depends on header file from java native calls
JniInchiWrapper.cpp: $(HEADERS)

# Generate header file, with javah
net_sf_jniinchi_JniInchiWrapper.h: $(JAVA_BUILD_DIR)/net/sf/jniinchi/JniInchiWrapper.class
	$(RM) $@
	$(JAVAH) $(JAVAH_FLAGS) $(JAVA_CLASS)


$(TARGET): $(OBJECTS)
	$(LINK.cpp) $(OBJECTS) $(LDFLAGS) $(LDLIBS) -o $(TARGET)


