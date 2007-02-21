
all:
	cd src/main/native/inchi && $(MAKE) all
	cd src/main/native && $(MAKE) all

clean:
	cd src/main/native/inchi && $(MAKE) clean
	cd src/main/native && $(MAKE) clean

