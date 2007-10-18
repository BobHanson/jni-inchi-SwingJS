#
# Root makefile for JNI-InChI
#
# Author: Sam Adams <sea36@cam.ac.uk>
#         Unilever Centre for Molecular Science Informatics
#         University Of Cambrige, UK
#
all:
	cd src/main/native && $(MAKE) all

clean:
	cd src/main/native && $(MAKE) clean

jar:
	cd src/main/native && $(MAKE) jar
