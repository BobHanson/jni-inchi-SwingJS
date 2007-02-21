
SOURCE_FILE = JniInchiWrapper

LIBNAME = JniInchi

CXXFLAGS = -Wall -O3
CPPFLAGS = -I. -I"${JAVA_HOME}/include"

JAVAH = $(JAVA_HOME)/bin/javah
JAVAH_FLAGS += -classpath $(JAVAH_CLASSPATH)
JAVAH_CMD = $(JAVAH) $(JAVAH_FLAGS) $(OUTPUT_OPTION)

LDFLAGS = -shared
LDLIBS=-linchi -L../resources

# os-dependent bits
UNAME := $(shell uname)

ifeq ($(UNAME),Linux)
LIB_PREFIX = lib
LIB_EXTN = .so
CPPFLAGS += -I$(JAVA_HOME)/include/linux
else
ifeq ($(findstring CYGWIN,$(UNAME)),CYGWIN)
LIB_PREFIX =
LIB_EXTN = .dll
CPPFLAGS += -I"$(JAVA_HOME)/include/win32"
else
f := $(error Platform $(UNAME) not supported)
endif
endif


# the name of the file we build
TARGET = $(LIB_PREFIX)$(LIBNAME)$(LIB_EXTN)

all: $(TARGET)

$(TARGET): JniInchiWrapper

JniInchiWrapper:
	$(LINK.cpp) JniInchiWrapper.cpp $(LDLIBS) -o $(TARGET)

clean:
	$(RM) $(TARGET)

