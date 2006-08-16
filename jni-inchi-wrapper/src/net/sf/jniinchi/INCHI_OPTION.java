/* File: INCHI_OPTION.java
 * Author: Sam Adams
 * 
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi;

import java.util.HashMap;
import java.util.Map;

/**
 * Type-safe enumeration of InChI options.  See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public class INCHI_OPTION {
    
    // --- MUST BE BEFORE DECLARATIONS ---
    /**
     * List of options.
     */
    private static Map lcMap = new HashMap(18);
    // ---
    
    
    /**
     * Use Chiral Flag.
     */
    public static final INCHI_OPTION SUCF = new INCHI_OPTION("SUCF");
    
    /**
     * Set Chiral Flag.
     */
    public static final INCHI_OPTION ChiralFlagON = new INCHI_OPTION("ChiralFlagON");
    
    /**
     * Set Not-Chiral Flag.
     */
    public static final INCHI_OPTION ChiralFlagOFF = new INCHI_OPTION("ChiralFlagOFF");
    
    /**
     * Exclude stereo (Default: Include Absolute stereo).
     */
    public static final INCHI_OPTION SNon = new INCHI_OPTION("SNon");
    
    /**
     * Absolute stereo.
     */
    public static final INCHI_OPTION SAbs = new INCHI_OPTION("SAbs");
    
    /**
     * Relative stereo.
     */
    public static final INCHI_OPTION SRel = new INCHI_OPTION("SRel");

    /**
     * Racemic stereo.
     */
    public static final INCHI_OPTION SRac = new INCHI_OPTION("SRac");
    
    /**
     * Include omitted unknown/undefined stereo.
     */
    public static final INCHI_OPTION SUU = new INCHI_OPTION("SUU");

    /**
     * Narrow end of wedge points to stereocenter (default: both).
     */
    public static final INCHI_OPTION NEWPS = new INCHI_OPTION("NEWPS");

    /**
     * Include reconnected bond to metal results.
     */
    public static final INCHI_OPTION RecMet = new INCHI_OPTION("RecMet");

    /**
     * Mobile H Perception Off (Default: On).
     */
    public static final INCHI_OPTION FixedH = new INCHI_OPTION("FixedH");

    /**
     * Omit auxiliary information (default: Include).
     */
    public static final INCHI_OPTION AuxNone = new INCHI_OPTION("AuxNone");

    /**
     * Disable Aggressive Deprotonation (for testing only).
     */
    public static final INCHI_OPTION NoADP = new INCHI_OPTION("NoADP");

    /**
     * Compressed output.
     */
    public static final INCHI_OPTION Compress = new INCHI_OPTION("Compress");

    /**
     * Overrides inchi_Atom::num_iso_H[0] == -1.
     */
    public static final INCHI_OPTION DoNotAddH = new INCHI_OPTION("DoNotAddH");

    /**
     * Set time-out per structure in seconds; W0 means unlimited. In InChI
     * library the default value is unlimited
     */
    public static final INCHI_OPTION Wnumber = new INCHI_OPTION("Wnumber");

    /**
     * Output SDfile instead of InChI.
     */
    public static final INCHI_OPTION OutputSDF = new INCHI_OPTION("OutputSDF");

    /**
     * Warn and produce empty InChI for empty structure.
     */
    public static final INCHI_OPTION WarnOnEmptyStructure = new INCHI_OPTION("WarnOnEmptyStructure");

    
    
    
    /**
     * Name.
     */
    private final String name;
    
    /**
     * Constructor.
     */
    private INCHI_OPTION(String name) { this.name = name; lcMap.put(name.toLowerCase(), this); };
    
    public String getName() {
        return name;
    }
    
    public String toString() {
        return name;
    }
    
    protected static Map getLowercaseMap() {
        return lcMap;
    }
}
