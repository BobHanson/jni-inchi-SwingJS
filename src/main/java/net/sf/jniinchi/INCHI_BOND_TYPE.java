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
 * Type-safe enumeration of InChI bond type definitions.  See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public class INCHI_BOND_TYPE {

	
	public static final INCHI_BOND_TYPE NONE =  new INCHI_BOND_TYPE("NONE", 0);

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
    private INCHI_BOND_TYPE(final String name, final int indx) { this.name = name; this.indx = indx; };

    public int getIndx() {
        return indx;
    }

    public String toString() {
        return name;
    }

	public static INCHI_BOND_TYPE getValue(int btype) {
		INCHI_BOND_TYPE type;
		switch (btype) {
			case 1: type = INCHI_BOND_TYPE.SINGLE; break;
			case 2: type = INCHI_BOND_TYPE.DOUBLE; break;
			case 3: type = INCHI_BOND_TYPE.TRIPLE; break;
			case 4: type = INCHI_BOND_TYPE.ALTERN; break;
			default:
				type = null;
        }
        return type;
	}
}
