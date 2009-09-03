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
 * Type-safe enumeration of InChI radical definitions.  See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public enum INCHI_RADICAL {

    /**
     * No radical status recorded.
     */
    NONE("NONE", 0),

    /**
     * Singlet.
     */
    SINGLET("SINGLET", 1),

    /**
     * Doublet.
     */
    DOUBLET("DOUBLET", 2),

    /**
     * Triplet.
     */
    TRIPLET("TRIPLET", 3);


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
     * @param name  name of radical.
     * @param indx  index of radical.
     */
    private INCHI_RADICAL(final String name, final int indx) {
        this.name = name;
        this.indx = indx;
    };

   /**
    * Returns index.
    * @return
    */
    public int getIndx() {
        return indx;
    }

    /**
     * Returns name.
     */
    public String toString() {
        return name;
    }

    public static INCHI_RADICAL getValue(int rad) {
        INCHI_RADICAL radical;
        switch (rad) {
            case 0: radical = NONE; break;
            case 1: radical = SINGLET; break;
            case 2: radical = DOUBLET; break;
            case 3: radical = TRIPLET; break;
            default:
                radical = null;
        }
        return radical;
    }
}
