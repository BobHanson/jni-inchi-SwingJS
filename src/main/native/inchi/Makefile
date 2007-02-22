# === version ===
MAIN_VERSION = .1
VERSION = $(MAIN_VERSION).01.00
LIBNAME = libinchi
TARGET_DIR = ../../../../target/classes

TARGET = $(TARGET_DIR)/$(LIBNAME)$(LIB_EXTN)

LDFLAGS = -shared
CFLAGS = -ansi -O3 

# os-dependent bits
UNAME := $(shell uname)

ifeq ($(UNAME),Linux)
LIB_EXTN = .so
LDFLAGS += -Wl,-soname,libinchi.so
else
ifeq ($(findstring MINGW,$(UNAME)),MINGW)
LIB_EXTN = .dll
LDFLAGS += -Wl,-soname,libinchi.dll
else
ifeq ($(findstring CYGWIN,$(UNAME)),CYGWIN)
LIB_EXTN = .dll
LDFLAGS += -Wl,-soname,libinchi.dll
else
f := $(error Platform $(UNAME) not supported)
endif
endif
endif



# === InChI Library Object files ============

INCHI_LIB_OBJS = ichican2.o       ichicano.o \
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

all: $(TARGET)

clean:
	$(RM) $(INCHI_LIB_OBJS)
	$(RM) $(TARGET)

$(TARGET): $(INCHI_LIB_OBJS)
	$(CC) $(LDFLAGS) $(OUTPUT_OPTION) $^

