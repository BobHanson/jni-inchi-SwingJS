#
# Makefile for JniInchi
#

LIBNAME = JniInchi
CODE_VERSION = .1.4

CLASSES = JniInchiWrapper.cpp
OBJECTS = JniInchiWrapper.o
JNIHEADERS = net_sf_jniinchi_JniInchiWrapper.h

TARGET_DIR = ../../../target/classes

JAVA_CLASS = net.sf.jniinchi.JniInchiWrapper
JAVA_BUILD_DIR = ../../../target/classes


# classpath for javah
JAVAH_CLASSPATH = $(JAVA_BUILD_DIR)

# Javah bits
JAVAH = "$(JAVA_HOME)/bin/javah"
JAVAH_FLAGS += -classpath $(JAVAH_CLASSPATH)
JAVAH_CMD = $(JAVAH) $(JAVAH_FLAGS) $(OUTPUT_OPTION)
JAVA_CLASS_FILE = $(addsuffix .class, $(subst .,/,$(JAVA_CLASS)))

# Preprocessor and compiler flags
CXXFLAGS = -Wall
CPPFLAGS = -I. -Iinchi -I"${JAVA_HOME}/include"

# Linker flags
LDFLAGS = -shared


# os-dependent bits
UNAME := $(shell uname)

ifeq ($(UNAME),Linux)
LINUX = 1
else
ifeq ($(findstring MINGW,$(UNAME)),MINGW)
WINDOWS = 1
else
ifeq ($(findstring CYGWIN,$(UNAME)),CYGWIN)
WINDOWS = 1
else
f := $(error Platform $(UNAME) not supported)
endif
endif
endif


ifdef LINUX
LIB_PREFIX = lib
LIB_EXTN = .so
CPPFLAGS += -I$(JAVA_HOME)/include/linux
LDLIBS=-linchi -L$(TARGET_DIR)
TARGET = $(TARGET_DIR)/$(LIB_PREFIX)$(LIBNAME)$(LIB_EXTN)$(CODE_VERSION)
else
ifdef WINDOWS
LIB_PREFIX = lib
LIB_EXTN = .dll
CPPFLAGS += -I"$(JAVA_HOME)/include/win32"
LDFLAGS += -Wl,--add-stdcall-alias
LDLIBS=-linchi.1.01.00 -L$(TARGET_DIR)
TARGET = $(TARGET_DIR)/$(LIB_PREFIX)$(LIBNAME)$(CODE_VERSION)$(LIB_EXTN)
endif
endif


all: $(TARGET)

clean:
	$(RM) $(JNIHEADERS)
	$(RM) $(OBJECTS)
	$(RM) $(TARGET)


$(CLASSES): $(JNIHEADERS)

$(OBJECT:S): $(CLASSES)

$(TARGET): $(OBJECTS) INCHI
	$(LINK.cpp) $(OBJECTS) $(LDFLAGS) $(LDLIBS) -o $(TARGET)

# Generate header file, using javah
$(JNIHEADERS): $(JAVA_BUILD_DIR)/$(JAVA_CLASS_FILE)
	$(RM) $@
	$(JAVAH) $(JAVAH_FLAGS) $(JAVA_CLASS)

INCHI:
	cd inchi && $(MAKE)
