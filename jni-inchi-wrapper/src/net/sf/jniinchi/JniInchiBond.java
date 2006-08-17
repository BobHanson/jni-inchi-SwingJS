/* File: JniInchiBond.java
 * Author: Sam Adams
 * 
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi;

/**
 * Encapsulates properties of InChI Bond.  See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public class JniInchiBond {
    
	/**
	 * Origin atom in bond.
	 */
    JniInchiAtom atomOrigin;
    
    /**
     * Target atom in bond.
     */
    JniInchiAtom atomTarget;
    
    /**
     * Bond type.
     */
    INCHI_BOND_TYPE type;
    
    /**
     * Bond 2D stereo definition.
     */
    INCHI_BOND_STEREO stereo;
    
    /**
     * Create bond.
     * 
     * @param atO		Origin atom
     * @param atT		Target atom
     * @param type		Bond type
     * @param stereo	Bond 2D stereo definition
     */
    public JniInchiBond(JniInchiAtom atO, JniInchiAtom atT, 
            INCHI_BOND_TYPE type, INCHI_BOND_STEREO stereo) {
        
        this.atomOrigin = atO;
        this.atomTarget = atT;
        this.type = type;
        this.stereo = stereo;
    }
    
    /**
     * Create bond.
     * 
     * @param atO		Origin atom
     * @param atT		Target atom
     * @param type		Bond type
     */
    public JniInchiBond(JniInchiAtom atO, JniInchiAtom atT, 
            INCHI_BOND_TYPE type) {
        
    	this(atO, atT, type, INCHI_BOND_STEREO.NONE);
    }
    
    /**
     * Set 2D stereo definition.
     * 
     * @param stereo	Bond 2D stereo definition
     */
    public void setStereoDefinition(INCHI_BOND_STEREO stereo) {
    	this.stereo = stereo;
    }
    
    /**
     * Returns atom at bond origin.
     * @return
     */
    public JniInchiAtom getOriginAtom() {
    	return(atomOrigin);
    }
    
    /**
     * Returns atom at bond target.
     * @return
     */
    public JniInchiAtom getTargetAtom() {
    	return(atomTarget);
    }
    
    /**
     * Returns bond type.
     * @return
     */
    public INCHI_BOND_TYPE getBondType() {
    	return(type);
    }
    
    /**
     * Returns bond stereochemistry.
     * @return
     */
    public INCHI_BOND_STEREO getBondStereo() {
    	return(stereo);
    }
    
    /**
     * Outputs information on bond, for debugging purposes.
     */
    public void debug() {
    	System.out.println("InChI Bond: "
    			+ atomOrigin.elname
    			+ "-" + atomTarget.elname
    			+ " // Type: " + type
    			+ " // Stereo: " + stereo
    			);
    }
}
