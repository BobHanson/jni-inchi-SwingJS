/* File: INCHI_BOND_TYPE.java
 * Author: Sam Adams
 * 
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi;

/**
 * Type-safe enumeration of InChI bond type definitions.  See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public class INCHI_BOND_TYPE {
    
    
    /**
     * Single bond.
     */
    public static final INCHI_BOND_TYPE SINGLE   = new INCHI_BOND_TYPE("SINGLE", 1);
    
    /**
     * Double bond.
     */
    public static final INCHI_BOND_TYPE DOUBLE   = new INCHI_BOND_TYPE("DOUBLE", 2);
    
    /**
     * Triple bond.
     */
    public static final INCHI_BOND_TYPE TRIPLE   = new INCHI_BOND_TYPE("TRIPLE", 3);
    
    /**
     * Alternating (single-double) bond.
     */
    public static final INCHI_BOND_TYPE ALTERN   = new INCHI_BOND_TYPE("ALTERN", 4);
    
    
    
    
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
    private INCHI_BOND_TYPE(String name, int indx) { this.name = name; this.indx = indx; };
    
    public int getIndx() {
        return indx;
    } 
    
    public String toString() {
        return name;
    }
}
