/* File: InchiCDKGenerator.java
 * Author: Sam Adams
 * 
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi.cdk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jniinchi.INCHI_BOND_TYPE;
import net.sf.jniinchi.INCHI_RET;
import net.sf.jniinchi.JniInchiAtom;
import net.sf.jniinchi.JniInchiBond;
import net.sf.jniinchi.JniInchiException;
import net.sf.jniinchi.JniInchiInputInchi;
import net.sf.jniinchi.JniInchiOutputStructure;
import net.sf.jniinchi.JniInchiWrapper;

import org.openscience.cdk.Atom;
import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.Bond;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.xmlcml.cml.base.CMLException;

/**
 * This class generates a CDK AtomContainer from an InChI string.
 * @author Sam Adams
 */
public class InchiCDKGenerator {

protected JniInchiInputInchi input;
	
	protected JniInchiOutputStructure output;
	
	protected IAtomContainer molecule;
	
	/**
	 * Constructor. Generates CDK AtomContainer from InChI.
	 * @param inchi
	 * @throws CMLException
	 */
	public InchiCDKGenerator(String inchi) throws CDKException {
		try {
			input = new JniInchiInputInchi(inchi, "");
		} catch (JniInchiException jie) {
			throw new CDKException("Failed to convert InChI to molecule: " + jie.getMessage());
		}
        generateAtomContainerFromInchi();
	}
	
	/**
	 * Constructor. Generates CMLMolecule from InChI.
	 * @param inchi
	 * @param options
	 * @throws CMLException
	 */
	public InchiCDKGenerator(String inchi, String options) throws CDKException {
		try {
			input = new JniInchiInputInchi(inchi, options);
		} catch (JniInchiException jie) {
			throw new CDKException("Failed to convert InChI to molecule: " + jie.getMessage());
		}
		generateAtomContainerFromInchi();
	}
	
	/**
	 * Constructor. Generates CMLMolecule from InChI.
	 * @param inchi
	 * @param options
	 * @throws CMLException
	 */
	public InchiCDKGenerator(String inchi, List options) throws CDKException {
		try {
			input = new JniInchiInputInchi(inchi, options);
		} catch (JniInchiException jie) {
			throw new CDKException("Failed to convert InChI to molecule: " + jie.getMessage());
		}
		generateAtomContainerFromInchi();
	}
	
	protected void generateAtomContainerFromInchi() throws CDKException {
		try {
			output = JniInchiWrapper.getStructureFromInchi(input);
        } catch (JniInchiException jie) {
        	throw new CDKException("Failed to convert InChI to molecule: " + jie.getMessage());
        }
		
        molecule = new AtomContainer();
        
        Map<JniInchiAtom, IAtom> inchiCdkAtomMap = new HashMap<JniInchiAtom, IAtom>();
        
        for (int i = 0; i < output.getNumAtoms(); i ++) {
        	JniInchiAtom iAt = output.getAtom(i);
        	IAtom cAt = new Atom();
        	
        	inchiCdkAtomMap.put(iAt, cAt);
        	
        	cAt.setID("a" + i);
        	cAt.setSymbol(iAt.getElementType());
        	
        	// Ignore coordinates - all zero
        	
        	int charge = iAt.getCharge();
        	if (charge != 0) {
        		cAt.setFormalCharge(charge);
        	}
        	
        	int numH = iAt.getImplicitH();
        	if (numH != 0) {
        		cAt.setHydrogenCount(numH);
        	}
        	
        	molecule.addAtom(cAt);
        }
        
        for (int i = 0; i < output.getNumBonds(); i ++) {
        	JniInchiBond iBo = output.getBond(i);
        	IBond cBo = new Bond();
        	
        	IAtom atO = inchiCdkAtomMap.get(iBo.getOriginAtom());
        	IAtom atT = inchiCdkAtomMap.get(iBo.getTargetAtom());
        	IAtom[] atoms = new IAtom[2];
        	atoms[0] = atO;
        	atoms[1] = atT;
        	cBo.setAtoms(atoms);
        	
        	INCHI_BOND_TYPE type = iBo.getBondType();
        	if (type == INCHI_BOND_TYPE.SINGLE) {
        		cBo.setOrder(CDKConstants.BONDORDER_SINGLE);
        	} else if (type == INCHI_BOND_TYPE.DOUBLE) {
        		cBo.setOrder(CDKConstants.BONDORDER_DOUBLE);
        	} else if (type == INCHI_BOND_TYPE.TRIPLE) {
        		cBo.setOrder(CDKConstants.BONDORDER_TRIPLE);
        	} else if (type == INCHI_BOND_TYPE.ALTERN) {
        		cBo.setOrder(CDKConstants.BONDORDER_AROMATIC);
        	} else {
        		throw new CDKException("Unknown bond type: " + type);
        	}
        	
        	// TODO: bond sterochemistry
        	
        	molecule.addBond(cBo);
        }
        
        // Add explict hydrogens to hydrogen counts
        for (int i = 0; i < molecule.getAtomCount(); i ++) {
        	IAtom at = molecule.getAtomAt(i);
        	if (at.getHydrogenCount() != 0) {
        		IAtom[] ligands = molecule.getConnectedAtoms(at);;
	        	int hLigands = 0;
	        	for (int j = 0; j < ligands.length; j ++) {
	        		if (ligands[j].getSymbol().equals("H")) {
	        			hLigands ++;
	        		}
	        	}
	        	
	        	if (hLigands > 0) {
	        		int numH = at.getHydrogenCount() + hLigands;
	        		at.setHydrogenCount(numH);
	        	}
        	}
        }
	}
	
	/**
	 * Returns generated molecule.
	 * @return
	 */
	public IAtomContainer getMolecule() {
		return(molecule);
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
	
    /**
	 * <p>Returns warning flags, see INCHIDIFF in inchicmp.h.
	 * 
	 * <p>[x][y]:
	 * <br>x=0 => Reconnected if present in InChI otherwise Disconnected/Normal
	 * <br>x=1 => Disconnected layer if Reconnected layer is present
	 * <br>y=1 => Main layer or Mobile-H
	 * <br>y=0 => Fixed-H layer
	 */
    public long[][] getWarningFlags() {
    	return(output.getWarningFlags());
    }

}
