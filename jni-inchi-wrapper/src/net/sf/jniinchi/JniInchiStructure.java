package net.sf.jniinchi;

import java.util.ArrayList;
import java.util.List;

public class JniInchiStructure {

	/**
     * List of atoms.
     */
    List atomList = new ArrayList();
    
    /**
     * List of bonds.
     */
    List bondList = new ArrayList();
    
    /**
     * List of stero parities.
     */
    List stereoList = new ArrayList();
    
    
    /**
     * Returns number of atoms in structure.
     */
    public int getNumAtoms() {
    	return atomList.size();
    }
    
    /**
     * Returns number of bonds in structure.
     */
    public int getNumBonds() {
    	return bondList.size();
    }
    
    /**
     * Returns number of stereo parities in strucuture.
     */
    public int getNumStereo0D() {
    	return stereoList.size();
    }
    
    
    /**
     * Adds atom to inchi molecule.
     * 
     * @param atom  Atom to add
     * @return      Added atom
     */
    public JniInchiAtom addAtom(JniInchiAtom atom) {
        atomList.add(atom);
        return(atom);
    }
    
    /**
     * Adds bond to inchi molecule.
     * 
     * @param bond  Bond to add
     * @return      Added bond
     */
    public JniInchiBond addBond(JniInchiBond bond) {
        bondList.add(bond);
        return(bond);
    }
    
    /**
     * Adds 0D stereo parity to inchi molecule.
     * 
     * @param parity  Parity to add
     * @return        Added parity
     */
    public JniInchiStereo0D addParity(JniInchiStereo0D parity) {
        stereoList.add(parity);
        return(parity);
    }
    
    
    
    public JniInchiAtom getAtom(int i) {
    	return((JniInchiAtom) atomList.get(i));
    }
    
    public JniInchiBond getBond(int i) {
    	return((JniInchiBond) bondList.get(i));
    }

}
