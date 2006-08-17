/* File: JniInchiStereo0D.java
 * Author: Sam Adams
 * 
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi;

/**
 * Encapsulates properites of InChI Stereo Parity.  See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public class JniInchiStereo0D {
    
	/**
	 * Neighbouring atoms.
	 */
    protected JniInchiAtom[] neighbors = new JniInchiAtom[4];
    
    /**
     * Central atom.
     */
    protected JniInchiAtom centralAtom;
    
    /**
     * Stereo parity type.
     */
    protected INCHI_STEREOTYPE type;
    
    /**
     * Parity.
     */
    protected INCHI_PARITY parity;
    
    /**
     * Second parity (for disconnected systems). 
     */
    protected INCHI_PARITY disconParity = INCHI_PARITY.NONE;
    
    /**
     * Constructor.  See <tt>inchi_api.h</tt> for details of usage.
     * 
     * @param atC	Central atom
     * @param at0	Neighbour atom 0
     * @param at1	Neighbour atom 1
     * @param at2	Neighbour atom 2
     * @param at3	Neighbour atom 3
     * @param type	  	Stereo parity type
     * @param parity	Parity
     */
    public JniInchiStereo0D(JniInchiAtom atC, JniInchiAtom at0, 
            JniInchiAtom at1, JniInchiAtom at2, JniInchiAtom at3,
            INCHI_STEREOTYPE type, INCHI_PARITY parity) {
        
        centralAtom = atC;
        neighbors[0] = at0;
        neighbors[1] = at1;
        neighbors[2] = at2;
        neighbors[3] = at3;
        
        this.type = type;
        this.parity = parity;
    }
    
    /**
     * Set second parity (for disconnected systems)
     * @param parity
     */
    public void setDisconnectedParity(INCHI_PARITY parity) {
        this.disconParity = parity;
    }
    
    /**
     * Returns central atom of stereo parity.
     * @return
     */
    public JniInchiAtom getCentralAtom() {
    	return(centralAtom);
    }
    
    /**
     * Returns neighboring atoms of stereo parity.
     * @return
     */
    public JniInchiAtom[] getNeighbors() {
    	return(neighbors);
    }
    
    /**
     * Returns parity.
     * @return
     */
    public INCHI_PARITY getParity() {
    	return(parity);
    }
    
    /**
     * Returns type of stereochemistry.
     * @return
     */
    public INCHI_STEREOTYPE getStereoType() {
    	return(type);
    }
    
    /**
     * Outputs information on stereo parity, for debugging purposes.
     */
    public void debug() {
    	System.out.println("InChI Stereo0D: "
    			+ (centralAtom == null ? "-" : centralAtom.elname)
    			+ " [" + neighbors[0].elname + "," + neighbors[1].elname
    			+ "," + neighbors[2].elname + "," + neighbors[3].elname + "] "
    			+ "Type::" + type + " // "
    			+ "Parity:" + parity
    			);
    }
}
