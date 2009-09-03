/**
 * Copyright (C) 2006-2009 Sam Adams <sam.adams@cantab.net>
 *
 * This file is part of JNI-InChI.
 *
 * JNI-InChI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * JNI-InChI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JNI-InChI. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jniinchi;

/**
 * Type-safe enumeration of InChI 2D stereo definitions.  See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public enum INCHI_BOND_STEREO {


    /**
     * No 2D stereo definition recorded.
     */
    NONE("NONE", 0),

    /**
     * Stereocenter-related; positive: the sharp end points to this atom.
     */
    SINGLE_1UP("SINGLE_1UP", 1),

    /**
     * Stereocenter-related; positive: the sharp end points to this atom.
     */
    SINGLE_1EITHER("SINGLE_1EITHER", 4),

    /**
     * Stereocenter-related; positive: the sharp end points to this atom.
     */
    SINGLE_1DOWN("SINGLE_1DOWN", 6),

    /**
     * Stereocenter-related; negative: the sharp end points to the opposite atom.
     */
    SINGLE_2UP("SINGLE_2UP", -1),

    /**
     * Stereocenter-related; negative: the sharp end points to the opposite atom.
     */
    SINGLE_2EITHER("SINGLE_2EITHER", -4),

    /**
     * Stereocenter-related; negative: the sharp end points to the opposite atom.
     */
    SINGLE_2DOWN("SINGLE_2DOWN", -6),

    /**
     * Unknown stereobond geometry.
     */
    DOUBLE_EITHER("DOUBLE_EITHER", 3);




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

    public static INCHI_BOND_STEREO getValue(int bster) {
        INCHI_BOND_STEREO stereo;
        switch (bster) {
            case 0: stereo = INCHI_BOND_STEREO.NONE; break;
            case 1: stereo = INCHI_BOND_STEREO.SINGLE_1UP; break;
            case 4: stereo = INCHI_BOND_STEREO.SINGLE_1EITHER; break;
            case 6: stereo = INCHI_BOND_STEREO.SINGLE_1DOWN; break;
            case 3: stereo = INCHI_BOND_STEREO.DOUBLE_EITHER; break;
            default:
                stereo = null;
        }
        return stereo;
    }
}
