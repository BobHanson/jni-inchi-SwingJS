/*
 * JNI InChI Wrapper - A Java Native Interface Wrapper for InChI.
 * Copyright (C) 2006-2007  Sam Adams
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA
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
    private static Map lcMap = new HashMap(21);
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
     * Fix bug leading to missing or undefined sp3 parity.
     */
    public static final INCHI_OPTION FixSp3Bug  = new INCHI_OPTION("FixSp3Bug");

    /**
     * Include Phosphines Stereochemistry.
     */
    public static final INCHI_OPTION SPXYZ = new INCHI_OPTION("SPXYZ");

    /**
     * Include Arsines Stereochemistry
     */
    public static final INCHI_OPTION SAsXYZ = new INCHI_OPTION("SPXYZ");




    /**
     * Name.
     */
    private final String name;

    /**
     * Constructor.
     */
    private INCHI_OPTION(final String name) { this.name = name; lcMap.put(name.toLowerCase(), this); };

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
