Interface between CDK and jniInChI
==================================

(C) 2006 Sam Adams, sea36@cam.ac.uk
Unilever Centre for Molecular Science Informatics, University of Cambridge, UK


1. Contents

    1. Contents
    2. Introduction
    3. Package contents
    4. Installation
    5. Usage
	6. License


2. Introduction

This package an interface between CDK and jniInChI, enabling the generation of
InChI strings from CDK AtomContainers, and CDK AtomContainers from InChIs.
It is intended to act as an example as to how the jniInChI library can be used.

* http://www.iupac.org/inchi/
* http://sourceforge.net/projects/cdk/


3. Package contents

    /                       readme and license files
    /lib                    the CDK library
    /src                    source code


4. Installation

Use of this package requires that both the jniInChI and CDK libraries are
available, and contained within the Java classpath.


5. Usage

	// Generate InChI and AuxInfo from a CDK AtomContainer
	
	CDKInchiGenerator inchiGen = new CDKInchiGenerator(atomContainer);
	INCHI_RET ret = inchiGen.getReturnStatus();
	if (ret == INCHI_RET.WARNING) {
		// InChI generated, but with warning message
		System.out.println("InChI warning: " + inchiGen.getMessage());
	} else if (ret != INCHI_RET.OKAY) {
		// InChI generation failed
		throw new JniInchiException("InChI failed: " + ret.toString() + " ("
			+ inchiGen.getMessage() + ")");
	}
	
	String inchi = inchiGen.getInchi();
	String auxinfo = inchiGen.getAuxInfo();

Options can be passed, as they can to the InChI executable.  This can be done
either as a string, or as a list of INCHI_OPTIONs:
	
	// Passing options as a string
	CDKInchiGenerator inchiGen = new CDKInchiGenerator(atomContainer, "-compress -FixedH");
	
	// Passing options as a list
	List opList = new ArrayList();
	opList.add(INCHI_OPTION.Compress);
	opList.add(INCHI_OPTION.FixedH);
	CDKInchiGenerator inchiGen = new CDKInchiGenerator(atomContainer, opList);

See the javadoc or InChI documentation for the list of available options.


    // Generate a CDK AtomContainer from an InChI
    InchiCDKGenerator cdkGen = new InchiCDKGenerator(inchiString);
    INCHI_RET ret = cdkGen.getReturnStatus();
    if (ret == INCHI_RET.WARNING) {
		// CDK AtomContainer generated, but with warning message
		System.out.println("InChI warning: " + inchiGen.getMessage());
	} else if (ret != INCHI_RET.OKAY) {
		// CDK AtomContainer generation failed
		throw new JniInchiException("InChI failed: " + ret.toString() + " ("
			+ inchiGen.getMessage() + ")");
	}
    
    IAtomContainer molecule = cdkGen.getMolecule();


6. License

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
