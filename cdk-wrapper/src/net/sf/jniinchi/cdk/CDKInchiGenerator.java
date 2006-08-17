/* File: CDKInchiGenerator.java
 * Author: Sam Adams <sea36@cam.ac.uk>
 * 
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi.cdk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jniinchi.INCHI_BOND_STEREO;
import net.sf.jniinchi.INCHI_BOND_TYPE;
import net.sf.jniinchi.INCHI_RET;
import net.sf.jniinchi.JniInchiAtom;
import net.sf.jniinchi.JniInchiBond;
import net.sf.jniinchi.JniInchiException;
import net.sf.jniinchi.JniInchiInput;
import net.sf.jniinchi.JniInchiOutput;
import net.sf.jniinchi.JniInchiWrapper;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;

/**
 * <p>This class generates the IUPAC International Chemical Identifier (InChI) for
 * a CDK AtomContainer. It subclasses a JNI wrapper for the InChI C++ library.
 * 
 * <p>If the atom container has 3D coordinates for all of its atoms then they
 * will be used, otherwise 2D coordinates will be used if available.
 * 
 * <p>Atom parities and spin multiplicities are not currently processed.
 * 
 * <h3>Example usage</h3>
 * 
 * <h4>Basic usage</h4>
 * 
 * <code>CDKInchiGenerator inchiGen = new CDKInchiGenerator(propanolMol);</code><br>
 * <code>System.out.println(inchiGen.getInchi());</code><br>
 * <code>System.out.println(inchiGen.getAuxInfo());</code><br>
 * 
 * 
 * <h4>Passing options as a string</h4>
 * <code>CDKInchiGenerator inchiGen = new CDKInchiGenerator(propanolMol, "-compress -FixedH");</code><br>
 * 
 * <h4>Passing options as a list</h4>
 * <code>List<INCHI_OPTION> opList = new ArrayList<INCHI_OPTION>();</code><br>
 * <code>opList.add(INCHI_OPTION.Compress);</code><br>
 * <code>opList.add(INCHI_OPTION.FixedH);</code><br>
 * <code>CDKInchiGenerator inchiGen = new CDKInchiGenerator(propanolMol, opList);</code><br>
 * 
 * <p><tt><b>
 * TODO: spin multiplicity<br/>
 * TODO: atom parities.
 * </b></tt>
 * 
 * @author Sam Adams
 */
public class CDKInchiGenerator {
    
	protected JniInchiInput input;
	
	protected JniInchiOutput output;
	
    /**
     * AtomContainer instance refers to.
     */
    protected IAtomContainer atomContainer;
    
    /**
     * <p>Constructor. Generates InChI from CDK AtomContainer.
     * 
     * <p>Reads atoms, bonds etc from atom container and converts to format
     * InChI library requires, then calls the library.
     * 
     * @param atomContainer      AtomContainer to generate InChI for.
     */
    public CDKInchiGenerator(IAtomContainer atomContainer) throws CDKException {
    	try {
    		input = new JniInchiInput("");
    		generateInchiFromCDKAtomContainer(atomContainer);
        } catch (JniInchiException jie) {
            throw new CDKException("InChI generation failed: " + jie.getMessage());
        }
    }
    
    /**
     * <p>Constructor. Generates InChI from CDK AtomContainer.
     * 
     * <p>Reads atoms, bonds etc from atom container and converts to format
     * InChI library requires, then calls the library.
     * 
     * @param atomContainer      AtomContainer to generate InChI for.
     * @param options   Space delimited string of options to pass to InChI library.
     * 					Each option may optionally be preceded by a command line
     * 					switch (/ or -).
     * @throws CDKException
     */
    public CDKInchiGenerator(IAtomContainer atomContainer, String options) throws CDKException {
    	try {
    		input = new JniInchiInput(options);
    		generateInchiFromCDKAtomContainer(atomContainer);
        } catch (JniInchiException jie) {
            throw new CDKException("InChI generation failed: " + jie.getMessage());
        }
    }
    
    
    /**
     * <p>Constructor. Generates InChI from CDK AtomContainer.
     * 
     * <p>Reads atoms, bonds etc from atom container and converts to format
     * InChI library requires, then calls the library.
     * 
     * @param atomContainer     AtomContainer to generate InChI for.
     * @param options       	List of INCHI_OPTION.
     * @throws CDKException
     */
    public CDKInchiGenerator(IAtomContainer atomContainer, List options) throws CDKException {
    	try {
    		input = new JniInchiInput(options);
    		generateInchiFromCDKAtomContainer(atomContainer);
        } catch (JniInchiException jie) {
            throw new CDKException("InChI generation failed: " + jie.getMessage());
        }
    }
    
    
    /**
     * <p>Reads atoms, bonds etc from atom container and converts to format
     * InChI library requires, then calls the library.
     * 
     * @param atomContainer      AtomContainer to generate InChI for.
     * @throws CDKException
     */
    protected void generateInchiFromCDKAtomContainer(IAtomContainer atomContainer) throws CDKException {
        this.atomContainer = atomContainer;
        
        IAtom[] atoms = atomContainer.getAtoms();
        
        // Check for 3d coordinates
        boolean all3d = true;
        boolean all2d = true;
        for (int i = 0; i < atoms.length; i ++) {
            IAtom atom = atoms[i];
            if (atom.getPoint3d() == null) {
                all3d = false;
            }
            if (atom.getPoint2d() == null) {
                all2d = false;
            }
        }
        
        // Process atoms
        Map<IAtom, JniInchiAtom> atomMap = new HashMap<IAtom, JniInchiAtom>();
        for (int i = 0; i < atoms.length; i ++) {
        	IAtom atom = atoms[i];
            
        	// Get coordinates
        	// Use 3d if possible, otherwise 2d or none
        	double x, y, z;
            if (all3d) {
                x = atom.getX3d();
                y = atom.getY3d();
                z = atom.getZ3d();
            } else if (all2d) {
                x = atom.getX2d();
                y = atom.getY2d();
                z = 0;
            } else {
                x = 0;
                y = 0;
                z = 0;
            }
            
            // Chemical element symbol
            String el = atom.getSymbol();
            
            // Generate InChI atom
            JniInchiAtom iatom = input.addAtom(new JniInchiAtom(x, y, z, el));
            atomMap.put(atom, iatom);
            
            // Check if charged
            int charge = atom.getFormalCharge();
            if (charge != 0) {
                iatom.setCharge(charge);
            }
            
            // Check whether isotopic
            int isotopeNumber = atom.getMassNumber();
            if (isotopeNumber != 0) {
            	iatom.setIsotopicMass(isotopeNumber);
            }
            
            // Check for implicit hydrogens
            // atom.getHydrogenCount() returns number of implict hydrogens, not
            // total number
            // Ref: Posting to cdk-devel list by Egon Willighagen 2005-09-17
            int implicitH = atom.getHydrogenCount();
            if (implicitH != 0) {
            	iatom.setImplictH(implicitH);
            }
        }
        
        
        // Process bonds
        IBond[] bonds = atomContainer.getBonds();
        for (int i = 0; i < bonds.length; i ++) {
            IBond bond = bonds[i];
            IAtom[] bondAtoms = bond.getAtoms();
            
            // Assumes 2 centre bond
            JniInchiAtom at0 = atomMap.get(bondAtoms[0]);
            JniInchiAtom at1 = atomMap.get(bondAtoms[1]);
            
            // Get bond order
            INCHI_BOND_TYPE order;
            double bo = bond.getOrder();
            if (bo == CDKConstants.BONDORDER_SINGLE) {
                order = INCHI_BOND_TYPE.SINGLE;
            } else if (bo == CDKConstants.BONDORDER_DOUBLE) {
                order = INCHI_BOND_TYPE.DOUBLE;
            } else if (bo == CDKConstants.BONDORDER_TRIPLE) {
                order = INCHI_BOND_TYPE.TRIPLE;
            } else if (bo == CDKConstants.BONDORDER_AROMATIC) {
                order = INCHI_BOND_TYPE.ALTERN;
            } else {
                throw new CDKException("Failed to generate InChI: Unsupported bond type");
            }
            
            // Create InChI bond
            JniInchiBond ibond = new JniInchiBond(at0, at1, order);
            input.addBond(ibond);
            
            // Check for bond stereo definitions
            int stereo = bond.getStereo();
            // No stereo definition
            if (stereo == CDKConstants.STEREO_BOND_NONE) {
            	ibond.setStereoDefinition(INCHI_BOND_STEREO.NONE);
            }
            // Bond ending (fat end of wedge) below the plane
            else if (stereo == CDKConstants.STEREO_BOND_DOWN) {
            	ibond.setStereoDefinition(INCHI_BOND_STEREO.SINGLE_1DOWN);
            }
            // Bond ending (fat end of wedge) above the plane
            else if (stereo == CDKConstants.STEREO_BOND_UP) {
            	ibond.setStereoDefinition(INCHI_BOND_STEREO.SINGLE_1UP);
            } 
            // Bond starting (pointy end of wedge) below the plane
            else if (stereo == CDKConstants.STEREO_BOND_DOWN_INV) {
            	ibond.setStereoDefinition(INCHI_BOND_STEREO.SINGLE_2DOWN);
            }
            // Bond starting (pointy end of wedge) above the plane
            else if (stereo == CDKConstants.STEREO_BOND_UP_INV) {
            	ibond.setStereoDefinition(INCHI_BOND_STEREO.SINGLE_2UP);
            } 
            // Bond with undefined stereochemistry
            else if (stereo == CDKConstants.STEREO_BOND_UNDEFINED) {
            	if (order == INCHI_BOND_TYPE.SINGLE) {
            		ibond.setStereoDefinition(INCHI_BOND_STEREO.SINGLE_1EITHER);
            	} else if (order == INCHI_BOND_TYPE.DOUBLE) {
            		ibond.setStereoDefinition(INCHI_BOND_STEREO.DOUBLE_EITHER);
            	}
            }
        }
        
        try {
        	output = JniInchiWrapper.getInchi(input);
        } catch (JniInchiException jie) {
        	throw new CDKException("Failed to generate InChI: " + jie.getMessage());
        }
    }
    
    
    /**
     * Gets return status from InChI process.  OKAY and WARNING indicate
     * InChI has been generated, in all other cases InChI generation
     * has failed.
     */
    public INCHI_RET getReturnStatus() {
        return(output.getReturnStatus());
    }
    
    /**
     * Gets generated InChI string.
     */
    public String getInchi() {
    	return(output.getInchi());
    }
    
    /**
     * Gets generated InChI string.
     */
    public String getAuxInfo() {
    	return(output.getAuxInfo());
    }
    
    /**
     * Gets generated (error/warning) messages.
     */
    public String getMessage() {
    	return(output.getMessage());
    }
    
    /**
     * Gets generated log.
     */
    public String getLog() {
    	return(output.getLog());
    }
}
