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
LDLIBS=-linchi -L$(TARGET_DIR)


# os-dependent bits
UNAME := $(shell uname)

ifeq ($(UNAME),Linux)
LIB_PREFIX = lib
LIB_EXTN = .so
CPPFLAGS += -I$(JAVA_HOME)/include/linux
TARGET = $(TARGET_DIR)/$(LIB_PREFIX)$(LIBNAME)$(LIB_EXTN)$(CODE_VERSION)
else
ifeq ($(findstring MINGW,$(UNAME)),MINGW)
LIB_PREFIX = lib
LIB_EXTN = .dll
CPPFLAGS += -I"$(JAVA_HOME)/include/win32"
LDFLAGS += -Wl,--add-stdcall-alias
TARGET = $(TARGET_DIR)/$(LIB_PREFIX)$(LIBNAME)$(CODE_VERSION)$(LIB_EXTN)
else
ifeq ($(findstring CYGWIN,$(UNAME)),CYGWIN)
LIB_PREFIX = lib
LIB_EXTN = .dll
CPPFLAGS += -I"$(JAVA_HOME)/include/win32"
LDFLAGS += -Wl,--add-stdcall-alias
TARGET = $(TARGET_DIR)/$(LIB_PREFIX)$(LIBNAME)$(CODE_VERSION)$(LIB_EXTN)
else
f := $(error Platform $(UNAME) not supported)
endif
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
