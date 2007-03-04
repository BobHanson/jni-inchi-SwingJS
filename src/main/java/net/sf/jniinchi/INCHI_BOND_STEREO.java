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
    private INCHI_BOND_STEREO(final String name, final int indx) { this.name = name; this.indx = indx; };

    public int getIndx() {
        return indx;
    }

    public String toString() {
        return name;
    }
}
