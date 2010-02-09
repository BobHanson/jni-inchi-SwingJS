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
 * Type-safe enumeration of InChI options.  See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public enum INCHI_OPTION {

    /**
     * Exclude stereo (Default: Include Absolute stereo).
     */
    SNon,

    /**
     * Overrides inchi_Atom::num_iso_H[0] == -1.
     */
    DoNotAddH,

    /**
     * Set time-out per structure in seconds; W0 means unlimited. In InChI
     * library the default value is unlimited
     */
    Wnumber,

    /**
     * Output SDfile instead of InChI.
     */
    OutputSDF,

    /**
     * Warn and produce empty InChI for empty structure.
     */
    WarnOnEmptyStructure;


    public static INCHI_OPTION valueOfIgnoreCase(String string) {
        for (INCHI_OPTION option : INCHI_OPTION.values()) {
            if (option.name().equalsIgnoreCase(string)) {
                return option;
            }
        }
        return null;
    }

}
