/* File: JniInchiAtom.java
 * Author: Sam Adams
 * 
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi;

/**
 * Encapsulates properties of InChI Atom.  See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public class JniInchiAtom {
    
    /**
     * Indicates relative rather than absolute isotopic mass. Value
     * from inchi_api.h.
     */
    private static final int ISOTOPIC_SHIFT_FLAG = 10000;
    
    /**
     * Atom x-coordinate.
     */
    protected double x;
    
    /**
     * Atom y-coordinate.
     */
    protected double y;
    
    /**
     * Atom z-coordinate.
     */
    protected double z;
    
    /**
     * Chemical element symbol eg C, O, Fe, Hg. 
     */
    protected String elname;
    
    /**
     * Number of implicit hydrogens on atom. If set to -1, InChI will add
     * implicit H automatically.
     */
    protected int implicitH;
    
    /**
     * Number of implicit protiums (isotopic 1-H) on atom.
     */
    protected int implicitP;
    
    /**
     * Number of implicit deuteriums (isotopic 2-H) on atom.
     */
    protected int implicitD;
    
    /**
     * Number of implicit tritiums (isotopic 3-H) on atom.
     */
    protected int implicitT;
    
    /**
     * Mass of isotope. If set to 0, non isotopic; otherwise, isotopic mass, or
     * ISOTOPIC_SHIFT_FLAG + (mass - average atomic mass).
     */
    protected int isotopic_mass;
    
    /**
     * Radical status of atom.
     */
    protected INCHI_RADICAL radical = INCHI_RADICAL.NONE;
    
    /**
     * Charge on atom.
     */
    protected int charge;
    
    /**
     * <p>Create new atom.
     * 
     * <p>Coordinates and element symbol must be set (unknown
     * coordinates/dimensions should be set to zero).  All other
     * parameters are initialised to default values:
     * <p>
     * <tt>
     *    Num Implicit H = 0<br>
     *    Num Implicit 1H = 0<br>
     *    Num Implicit 2H = 0<br>
     *    Num Implicit 3H = 0<br>
     *    Isotopic mass = 0 (non isotopic)<br>
     *    Radical status = NONE  (radical status not defined)
     * </tt>
     * 
     * @param x     x-coordinate
     * @param y     y-coordinate
     * @param z     z-coordinate
     * @param el    Chemical element symbol
     */
    public JniInchiAtom(double x, double y, double z, String el) {
        this.x = x;
        this.y = y;
        this.z = z;
        
        this.elname = el;
    }
    
    /**
     * Sets charge on atom.
     * 
     * @param charge
     */
    public void setCharge(int charge) {
        this.charge = charge;
    }
    
    /**
     * Sets radical status of atom.
     * 
     * @param radical
     */
    public void setRadical(INCHI_RADICAL radical) {
        this.radical = radical;
    }
    
    /**
     * Sets isotopic mass. If set to 0, non-isotopic.
     * 
     * @param mass  Isotopic mass
     */
    public void setIsotopicMass(int mass) {
        this.isotopic_mass = mass;
    }
    
    /**
     * Sets isotopic mass, relative to standard mass.
     * 
     * @param shift  Isotopic mass minus average atomic mass
     */
    public void setIsotopicMassShift(int shift) {
        this.isotopic_mass = ISOTOPIC_SHIFT_FLAG + shift; 
    }
    
    /**
     * Sets number of implicit hydrogens on atom. If set to -1, InChI will add
     * implicit H automatically.
     * 
     * @param n  Number of implicit hydrogen
     */
    public void setImplictH(int n) {
        this.implicitH = n;
    }
    
    /**
     * Sets number of implicit protium (1H) on atom.
     * @param n  Number of implicit protium
     */
    public void setImplictProtium(int n) {
        this.implicitP = n;
    }
    
    /**
     * Sets number of implicit deuterium (2H) on atom.
     * 
     * @param n  Number of implicit deuterium
     */
    public void setImplictDeuterium(int n) {
        this.implicitD = n;
    }
    
    /**
     * Sets number of implicit tritium (3H) on atom.
     * @param n  Number of implicit tritium
     */
    public void setImplictTritium(int n) {
        this.implicitT = n;
    }
    
    
    /**
     * Returns chemical element symbol of atom.
     * @return
     */
    public String getElementType() {
    	return(elname);
    }
    
    /**
     * Returns charge on atom.
     * @return
     */
    public int getCharge() {
    	return(charge);
    }
    
    /**
     * Returns radical state of atom.
     * @return
     */
    public INCHI_RADICAL getRadical() {
    	return(radical);
    }
    
    /**
     * Returns atom's X-coordinate.
     * @return
     */
    public double getX() {
    	return(x);
    }
    
    /**
     * Returns atom's Y-coordinate.
     * @return
     */
    public double getY() {
    	return(y);
    }
    
    /**
     * Returns atom's Z-coordinate.
     * @return
     */
    public double getZ() {
    	return(z);
    }
    
    /**
     * Returns number of implicit hydrogens on atom.
     * @return
     */
    public int getImplicitH() {
    	return(implicitH);
    }
    
    /**
     * Returns number of implicit protiums (1H) on atom.
     * @return
     */
    public int getImplicitProtium() {
    	return(implicitP);
    }
    
    /**
     * Returns number of implicit deuteriums (2H) on atom.
     * @return
     */
    public int getImplicitDeuterium() {
    	return(implicitD);
    }
    
    /**
     * Returns number of implicit tritiums (3H) on atom.
     * @return
     */
    public int getImplicitTritium() {
    	return(implicitT);
    }
    
    /**
     * Outputs information on atom, for debugging purposes.
     */
    public void debug() {
    	System.out.println("InChI Atom: "
    			+ elname
    			+ " [" + x + "," + y + "," + z + "] "
    			+ "Charge:" + charge + " // "
    			+ "Iso Mass:" + isotopic_mass + " // "
    			+ "Implicit H:" + implicitH
    			+ " P:" + implicitP
    			+ " D:" + implicitD
    			+ " T:" + implicitT
    			+ " // Radical: " + radical
    			);
    }
}
