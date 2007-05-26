# === version ===
MAIN_VERSION = .1
VERSION = $(MAIN_VERSION).01.00
LIBNAME = libinchi
TARGET_DIR = ../../../../target/native
OBJDIR = $(TARGET_DIR)/o/inchi

LDFLAGS = -shared
CFLAGS = -ansi -O3 

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
LIB_EXTN = .so
LDFLAGS += -Wl,-soname,libinchi.so
TARGET_FILE = $(LIBNAME)$(LIB_EXTN)$(VERSION)
CFLAGS += -fPIC
else
ifdef WINDOWS
LIB_EXTN = .dll
LDFLAGS += -Wl,-soname,libinchi.dll
TARGET_FILE = $(LIBNAME)$(VERSION)$(LIB_EXTN)
endif
endif

TARGET = $(TARGET_DIR)/$(TARGET_FILE)


# === InChI Library Object files ============

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

INCHI_LIB_OBJS = $(addprefix $(OBJDIR)/,$(_INCHI_LIB_OBJS))

all: $(TARGET)

clean:
	$(RM) $(INCHI_LIB_OBJS)
	$(RM) $(TARGET)

$(TARGET): $(INCHI_LIB_OBJS)
	$(CC) $(LDFLAGS) $(OUTPUT_OPTION) $^

$(OBJDIR):
	mkdir -p $@
	
$(TARGET_DIR):
	mkdir -p $@

$(OBJDIR)/%.o: %.c $(OBJDIR)
	$(CC) $(CFLAGS) -c -o $@ $<
