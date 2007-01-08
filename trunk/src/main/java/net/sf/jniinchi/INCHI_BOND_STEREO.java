/* File: INCHI_BOND_STEREO.java
 * Author: Sam Adams
 * 
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi;

/**
 * Type-safe enumeration of InChI 2D stereo definitions.  See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public class INCHI_BOND_STEREO {
    
    
    /**
     * No 2D stereo definition recorded.
     */
    public static final INCHI_BOND_STEREO NONE              = new INCHI_BOND_STEREO("NONE", 0);
    
    /**
     * Stereocenter-related; positive: the sharp end points to this atom.
     */
    public static final INCHI_BOND_STEREO SINGLE_1UP        = new INCHI_BOND_STEREO("SINGLE_1UP", 1);
    
    /**
     * Stereocenter-related; positive: the sharp end points to this atom.
     */
    public static final INCHI_BOND_STEREO SINGLE_1EITHER    = new INCHI_BOND_STEREO("SINGLE_1EITHER", 4);
    
    /**
     * Stereocenter-related; positive: the sharp end points to this atom.
     */
    public static final INCHI_BOND_STEREO SINGLE_1DOWN      = new INCHI_BOND_STEREO("SINGLE_1DOWN", 6);
    
    /**
     * Stereocenter-related; negative: the sharp end points to the opposite atom.
     */
    public static final INCHI_BOND_STEREO SINGLE_2UP        = new INCHI_BOND_STEREO("SINGLE_2UP", -1);
    
    /**
     * Stereocenter-related; negative: the sharp end points to the opposite atom.
     */
    public static final INCHI_BOND_STEREO SINGLE_2EITHER    = new INCHI_BOND_STEREO("SINGLE_2EITHER", -4);
    
    /**
     * Stereocenter-related; negative: the sharp end points to the opposite atom.
     */
    public static final INCHI_BOND_STEREO SINGLE_2DOWN      = new INCHI_BOND_STEREO("SINGLE_2DOWN", -6);
    
    /**
     * Unknown stereobond geometry.
     */
    public static final INCHI_BOND_STEREO DOUBLE_EITHER   = new INCHI_BOND_STEREO("DOUBLE_EITHER", 3);
    
    
    
    
    /**
     * Name.
     */
    private final String name;
    
    /**
     * Internal InChI index (from inchi_api.h).
     */
    private final int indx;
    
    /**
     * Constructor.
     */
    private INCHI_BOND_STEREO(String name, int indx) { this.name = name; this.indx = indx; };

    public int getIndx() {
        return indx;
    } 
    
    public String toString() {
        return name;
    }
}
