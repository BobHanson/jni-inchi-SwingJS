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

INCHI_FILE = libinchi.so.1.01.00
INCHILINK = $(TARGET_DIR)/libinchi.so

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
CPPFLAGS += -I$(JAVA_HOME)/include/linux -fPIC
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
	$(RM) $(TARGET_DIR)/libinchi.so


$(CLASSES): $(JNIHEADERS)

$(OBJECT:S): $(CLASSES)

$(TARGET): $(OBJECTS) $(INCHILINK)
	@echo ${UNAME}
	@echo ${OSNAME}
	@echo ${PLATNAME}
	@echo ${OS}
	@echo ${OSPLAT}
	$(LINK.cpp) $(OBJECTS) $(LDFLAGS) $(LDLIBS) -o $(TARGET)

# Generate header file, using javah
$(JNIHEADERS): $(JAVA_BUILD_DIR)/$(JAVA_CLASS_FILE)
	$(RM) $@
	$(JAVAH) $(JAVAH_FLAGS) $(JAVA_CLASS)

$(INCHILINK): INCHI
ifdef LINUX
	$(RM) $@
	ln -s $(INCHI_FILE) $@
endif

INCHI:
	cd inchi && $(MAKE)
