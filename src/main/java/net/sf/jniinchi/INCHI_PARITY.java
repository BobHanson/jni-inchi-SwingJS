/* File: INCHI_PARITY.java
 * Author: Sam Adams
 *
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi;

/**
 * Type-safe enumeration of InChI 0D parities.  See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public class INCHI_PARITY {


    /**
     * None.
     */
    public static final INCHI_PARITY NONE   = new INCHI_PARITY("NONE", 0);

    /**
     * Odd.
     */
    public static final INCHI_PARITY ODD   = new INCHI_PARITY("ODD", 1);

    /**
     * Even.
     */
    public static final INCHI_PARITY EVEN   = new INCHI_PARITY("EVEN", 2);

    /**
     * Unknown.
     */
    public static final INCHI_PARITY UNKNOWN   = new INCHI_PARITY("UNKNOWN", 3);

    /**
     * Undefined.
     */
    public static final INCHI_PARITY UNDEFINED   = new INCHI_PARITY("UNDEFINED", 4);




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
    private INCHI_PARITY(final String name, final int indx) { this.name = name; this.indx = indx; };

    public int getIndx() {
        return indx;
    }

    public String toString() {
        return name;
    }
}
