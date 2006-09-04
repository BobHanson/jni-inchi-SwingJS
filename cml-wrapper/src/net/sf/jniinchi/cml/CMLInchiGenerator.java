/* File: CMLInchiGenerator.java
 * Author: Sam Adams
 * 
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi.cml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jniinchi.INCHI_BOND_TYPE;
import net.sf.jniinchi.INCHI_RADICAL;
import net.sf.jniinchi.INCHI_RET;
import net.sf.jniinchi.JniInchiAtom;
import net.sf.jniinchi.JniInchiBond;
import net.sf.jniinchi.JniInchiInput;
import net.sf.jniinchi.JniInchiOutput;
import net.sf.jniinchi.JniInchiException;
import net.sf.jniinchi.JniInchiWrapper;
import nu.xom.Text;

import org.xmlcml.cml.base.CMLElement;
import org.xmlcml.cml.base.CMLException;
import org.xmlcml.cml.base.CMLRuntime;
import org.xmlcml.cml.element.CMLAtom;
import org.xmlcml.cml.element.CMLBond;
import org.xmlcml.cml.element.CMLIdentifier;
import org.xmlcml.cml.element.CMLMolecule;



/**
 * <p>This class generates the IUPAC International Chemical Identifier (InChI) for
 * a CMLMolecule. It subclasses a JNI wrapper for the InChI C++ library.
 * 
 * <p>If the molecule has 3D coordinates for all of its atoms then they will be
 * used, otherwise 2D coordinates will be used if available.
 * 
 * <p>Bond stereochemistry and atom parities are not currently
 * processed. If 3D coordinates are available then the bond stereochemistry and
 * atom parities would be ignored by InChI anyway.
 * 
 * <h3>Example usage</h3>
 * 
 * <h4>Basic usage</h4>
 * 
 * <code>CMLInchiGenerator inchiGen = new CMLInchiGenerator(cmlMolecule);</code><br>
 * <code>System.out.println(inchiGen.getInchi());</code><br>
 * <code>System.out.println(inchiGen.getAuxInfo());</code><br>
 * 
 * 
 * <h4>Passing options as a string</h4>
 * <code>CMLInchiGenerator inchiGen = new CMLInchiGenerator(cmlMolecule, "-compress -FixedH");</code><br>
 * 
 * <h4>Passing options as a list</h4>
 * <code>List<INCHI_OPTION> opList = new ArrayList<INCHI_OPTION>();</code><br>
 * <code>opList.add(INCHI_OPTION.Compress);</code><br>
 * <code>opList.add(INCHI_OPTION.FixedH);</code><br>
 * <code>CMLInchiGenerator inchiGen = new CMLInchiGenerator(cmlMolecule, opList);</code><br>
 * 
 * 
 * <h4>Storing InChI in a CMLIdentifier</h4>
 * <code>CMLInchiGenerator inchiGen = new CMLInchiGenerator(cmlMolecule);</code><br>
 * <code>inchiGen.appendToMolecule();</code><br>
 * 
 * 
 * <p><tt><b>
 * TODO: bond stereochemistry<br>
 * TODO: atom parities.
 * </b></tt>
 * 
 * @author Sam Adams
 */
public class CMLInchiGenerator {
	
	protected JniInchiInput input;
	
	protected JniInchiOutput output;
	
    
	/**
	 * Convention to use when constructing CMLIdentifier to hold InChI.
	 */
    protected static final String CML_INCHI_CONVENTION = "iupac:inchi";
    
    /**
     * Molecule instance refers to.
     */
    protected CMLMolecule molecule;
    
    /**
     * <p>Constructor. Generates InChI from CMLMolecule.
     * 
     * <p>Reads atoms, bonds etc from molecule and converts to format InChI library
     * requires, then calls the library.
     * 
     * @param molecule      Molecule to generate InChI for.
     * @throws CMLException
     */
    public CMLInchiGenerator(CMLMolecule molecule) throws CMLException {
    	try {
    		input = new JniInchiInput("");
            generateInchiFromCMLMolecule(molecule);
        } catch (JniInchiException jie) {
            throw new CMLException("InChI generation failed: " + jie.getMessage());
        }
    }
    
    
    /**
     * <p>Constructor. Generates InChI from CMLMolecule.
     * 
     * <p>Reads atoms, bonds etc from molecule and converts to format InChI library
     * requires, then calls the library.
     * 
     * @param molecule  Molecule to generate InChI for.
     * @param options   Space delimited string of options to pass to InChI library.
     * 					Each option may optionally be preceded by a command line
     * 					switch (/ or -).
     * @throws CMLException
     */
    public CMLInchiGenerator(CMLMolecule molecule, String options) throws CMLException {
    	try {
    		input = new JniInchiInput(options);
            generateInchiFromCMLMolecule(molecule);
        } catch (JniInchiException jie) {
            throw new CMLException("InChI generation failed: " + jie.getMessage());
        }
    }
    
    
    /**
     * <p>Constructor. Generates InChI from CMLMolecule.
     * 
     * <p>Reads atoms, bonds etc from molecule and converts to format InChI library
     * requires, then calls the library.
     * 
     * @param molecule      Molecule to generate InChI for.
     * @param options       List of INCHI_OPTION.
     * @throws CMLException
     */
    public CMLInchiGenerator(CMLMolecule molecule, List options) throws CMLException {
    	try {
    		input = new JniInchiInput(options);
            generateInchiFromCMLMolecule(molecule);
        } catch (JniInchiException jie) {
            throw new CMLException("InChI generation failed: " + jie.getMessage());
        }
    }
    
    
    /**
     * <p>Reads atoms, bonds etc from molecule and converts to format InChI library
     * requires, then makes call to library, generating InChI.
     * 
     * <p>Used by constructors.
     * 
     * @param molecule
     * @throws CMLException
     */
    protected void generateInchiFromCMLMolecule(CMLMolecule molecule) throws CMLException {
    	
        this.molecule = molecule;
        
        List<CMLAtom> atoms = molecule.getAtoms();
        List<CMLBond> bonds = molecule.getBonds();
        
        // Create map of atom neighbours - required to calculate implicit
        // hydrogen counts
        Map<CMLAtom, List<CMLAtom>> atomNeighbours = new HashMap<CMLAtom, List<CMLAtom>>();
        for (int i = 0; i < atoms.size(); i ++) {
            atomNeighbours.put(atoms.get(i), new ArrayList<CMLAtom>(4));
        }
        for (int i = 0; i < bonds.size(); i ++) {
            CMLBond bond = (CMLBond) bonds.get(i);
            List<CMLAtom> bondlist = bond.getAtoms(molecule);
            CMLAtom at0 = bondlist.get(0);
            CMLAtom at1 = bondlist.get(1);
            atomNeighbours.get(at0).add(at1);
            atomNeighbours.get(at1).add(at0);
        }
        
        // Check for 3d coordinates
        boolean all3d = true;
        boolean all2d = true;
        for (int i = 0; i < atoms.size(); i ++) {
            CMLAtom atom = atoms.get(i);
            if (!atom.hasCoordinates(CMLElement.CoordinateType.CARTESIAN)) {
                all3d = false;
            }
            if (!atom.hasCoordinates(CMLElement.CoordinateType.TWOD)) {
                all2d = false;
            }
        }
        
        // Process atoms
        Map<CMLAtom, JniInchiAtom> atomMap = new HashMap<CMLAtom, JniInchiAtom>();
        for (int i = 0; i < atoms.size(); i ++) {
            CMLAtom atom = atoms.get(i);
            double x, y, z;
            if (all3d) {
                x = atom.getX3();
                y = atom.getY3();
                z = atom.getZ3();
            } else if (all2d) {
                x = atom.getX2();
                y = atom.getY2();
                z = 0;
            } else {
                x = 0;
                y = 0;
                z = 0;
            }
            String el = atom.getElementType();
            
            JniInchiAtom iatom = input.addAtom(new JniInchiAtom(x, y, z, el));
            atomMap.put(atom, iatom);
            
            int charge = atom.getFormalCharge(CMLElement.FormalChargeControl.DEFAULT);
            if (charge != 0) {
                iatom.setCharge(charge);
            }
            
            try {
                int spinMultiplicity = atom.getSpinMultiplicity();
                if (spinMultiplicity == 0) {
                    iatom.setRadical(INCHI_RADICAL.NONE);
                } else if (spinMultiplicity == 1) {
                    iatom.setRadical(INCHI_RADICAL.SINGLET);
                } else if (spinMultiplicity == 2) {
                    iatom.setRadical(INCHI_RADICAL.DOUBLET);
                } else if (spinMultiplicity == 3) {
                    iatom.setRadical(INCHI_RADICAL.TRIPLET);
                } else {
                    throw new CMLException("Failed to generate InChI: Unsupported spin multiplicity: " + spinMultiplicity);
                }
            } catch (CMLRuntime cre) {
                // Spin multiplicity not set 
            }
            
            try {
                int isotopeNumber = atom.getIsotopeNumber();
                iatom.setIsotopicMass(isotopeNumber);
            } catch (CMLRuntime cre) {
                // Isotope number not set 
            }
            
            // Calculate implicit hydrogens
            int hcount = -1;
            try {
                hcount = atom.getHydrogenCount();
            } catch (CMLRuntime cre) {
                // Hydrogen count not set 
            }
            
            if (hcount > -1) {
                List<CMLAtom> neighbours = atomNeighbours.get(atom);
                for (int j = 0; j < neighbours.size(); j ++) {
                	CMLAtom neigh = neighbours.get(j);
                	if (neigh.getElementType().equals("H")) {
                		hcount --;
                	}
                }
                
                if (hcount < 0) {
                	throw new CMLRuntime("Negative implicit hydrogen count: " + atom);
                }
                
                iatom.setImplicitH(hcount);
            }
        }
        
        // Process bonds
        for (int i = 0; i < bonds.size(); i ++) {
            CMLBond bond = (CMLBond) bonds.get(i);
            List<CMLAtom> bondlist = bond.getAtoms(molecule);
            
            JniInchiAtom at0 = atomMap.get(bondlist.get(0));
            JniInchiAtom at1 = atomMap.get(bondlist.get(1));
            
            INCHI_BOND_TYPE order;
            String bo = bond.getOrder();
            
            if (CMLBond.SINGLE.equals(bo) || CMLBond.SINGLE_S.equals(bo)) {
                order = INCHI_BOND_TYPE.SINGLE;
            } else if (CMLBond.DOUBLE.equals(bo) || CMLBond.DOUBLE_D.equals(bo)) {
                order = INCHI_BOND_TYPE.DOUBLE;
            } else if (CMLBond.TRIPLE.equals(bo) || CMLBond.TRIPLE_T.equals(bo)) {
                order = INCHI_BOND_TYPE.TRIPLE;
            } else if (CMLBond.AROMATIC.equals(bo)) {
                order = INCHI_BOND_TYPE.ALTERN;
            } else {
                throw new CMLException("Failed to generate InChI: Unsupported bond order (" + bo + ")");
            }
            
            
            input.addBond(new JniInchiBond(at0, at1, order));
        }
        
        // TODO: Stereo chemistry
        try {
        	output = JniInchiWrapper.getInchi(input);
        } catch (JniInchiException jie) {
        	throw new CMLException("Failed to generate InChI: " + jie.getMessage());
        }
    }
    
    
    /**
     * Adds CMLIdentifier containing InChI to CMLMolecule.
     * 
     * @throws JniInchiException
     */
    public void appendToMolecule() throws CMLException {
        appendToElement(molecule);
    }
    
    
    /**
     * Adds CMLIdentifier containing InChI to specified element.
     * 
     * @param element
     * @throws JniInchiException
     */
    public void appendToElement(CMLElement element) throws CMLException {
        if (output.getInchi() == null) {
            throw new CMLException("Failed to generate InChI");
        }
        
        CMLIdentifier identifier = CMLIdentifier.makeElementInContext(molecule);
        identifier.setConvention(CML_INCHI_CONVENTION);
        identifier.appendChild(new Text(output.getInchi()));
        
        element.appendChild(identifier);
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
