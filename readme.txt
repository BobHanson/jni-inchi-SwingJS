Java Native Interface for InChI
===============================

(C) 2006 Sam Adams, sea36@cam.ac.uk
Unilever Centre for Molecular Science Informatics, University of Cambridge, UK

Precompiled InChI library files and the API header file have been included in
this distribution, for convenience.  These files are Copyright (C) The
International Union of Pure and Applied Chemistry 2005: IUPAC International
Chemical Identifier (InChI) (contact: secretariat@iupac.org).  The complete
InChI distribution, including source code, is available from the IUPAC website
(http://www.iupac.org/inchi).


1. Contents

    1. Contents
    2. Introduction
    3. Package contents
    4. Installation
    5. Usage
	6. Compatibility
    7. License


2. Introduction

This package provides Java Native Interface (JNI) wrappers for software version
1.01 of the InChI chemical identifier C++ library distributed by IUPAC. The
following features are supported:

    * InChI generation from structure
    * 0D structure generation from InChI
    * Full range of options supported by InChI
    
Structure generation from AuxInfo has not yet been implemented, but will
hopefully be soon.

* http://www.iupac.org/inchi/
* http://java.sun.com/j2se/1.4.2/docs/guide/jni/


3. Package contents

    /                       readme and license and build files
    /cpp-bin				precompiled copies of the native (C++) code, for
    						both Windows and Linux (Fedora Core 5)
    /cpp-src                C++ component of the JNI code wrapper
    /inchi					InChI binary and header files
    /src  java code wrapping the InChI C++ API


4. Installation

The simplest method of using this package is to make use of the jniInChI.jar
file, which can be downloaded from sourceforge or built using ant with the
target 'jar'.  The jar file contains both the JNI InChI Wrapper classes and the
required C++ libraries for both Windows and Linux.

Alternatively you can use the individual files themselves; you will require all
of the classes within net.sf.jniinchi, and the appropriate C++ library
files: JniInchi.dll and libinchi.dll under windows, or libJniInchi.so and
libinchi.so under Linux (in order for the libinchi.so library to load correctly
you must either rename it libinchi.so.1, or create a link to that name i.e.
ln -s libinchi.so.1 libinchi.so).

When the JNI InChI wrapper is used, it attempts to load the required native
(C++) libraries.  To do this Java searches the directory containing the class
files or JAR file, the current working directory and the directories found in the
java.library.path system property.  These are usually directories from the jdk
or jre installation, and either the contents of the PATH environmental variable,
in Windows, or the contents of LD_LIBRARY_PATH in Linux.  Therefore, in order to
use the JNI InChI wrapper the native library files must be placed in one of
these directories.  If, however, you are using the jniInChI.jar file and it
fails to load the native code it will attempt to extract the copies of the
libraries contained in the jar and place them into either the directory containing
the class files, or the current working directory and try loading the
native code again automatically.  For this to work the current user must have
write access to the appropriate directory.

To test whether this distribution is working on your system ensure that either
the jar file, or the class files, are in your Java classpath and run:

	java sea36.inchitools.jni.Main


5. Usage

It is not expected that most users interact with the InChI API wrappers
directly, but rather through further wrappers which provide an interface to
whichever molecular handling library or toolkit they are using.  InChI 
generators for both CML and CDK have been written as examples, and are available
from both the sourceforge project page (http://sourceforge.net/jni-inchi) and
subversion.

The class net.sf.jniinchi.JniInchiWrapper provides access to the InChI library's
functionality through two methods:

    static JniInchiOutput getInchi(JniInchiInput input)

and 
    static JniInchiOutputStructure getStructureFromInchi(JniInchiInputInchi input)

The first of which is used to generate an InChI from a structure, and the second
to generate a structure from an InChI.


6. Compatability

The JNI wrapper is written in Java 1.4, to allow compatability with as many 
applications as possible.


7. License

This library is free software; you can redistribute it and/or modify it under
the terms of the GNU Lesser General Public License as published by the Free
Software Foundation (http://www.opensource.org/licenses/lgpl-license.php);
either version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along
with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.

