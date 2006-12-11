/* File: INCHI_STEREOTYPE.java
 * Author: Sam Adams
 * 
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi;

/**
 * Type-safe enumeration of InChI 0D parity types.  See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public class INCHI_STEREOTYPE {
    
    
    /**
     * None.
     */
    public static final INCHI_STEREOTYPE NONE           = new INCHI_STEREOTYPE("NONE", 0);
    
    /**
     * Stereogenic bond >A=B< or cumulene >A=C=C=B<.
     */
    public static final INCHI_STEREOTYPE DOUBLEBOND     = new INCHI_STEREOTYPE("DOUBLEBOND", 1);
    
    /**
     * Tetrahedral atom.
     */
    public static final INCHI_STEREOTYPE TETRAHEDRAL    = new INCHI_STEREOTYPE("TETRAHEDRAL", 2);
    
    /**
     * Allene.
     */
    public static final INCHI_STEREOTYPE ALLENE         = new INCHI_STEREOTYPE("ALLENE", 3);
    
    
    
    
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
    private INCHI_STEREOTYPE(String name, int indx) { this.name = name; this.indx = indx; };
    
    public int getIndx() {
        return indx;
    } 
    
    public String toString() {
        return name;
    }
}
