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
 * Type-safe enumeration of InChI 0D parity types.  See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public enum INCHI_STEREOTYPE {


    /**
     * None.
     */
    NONE("NONE", 0),

    /**
     * Stereogenic bond >A=B< or cumulene >A=C=C=B<.
     */
    DOUBLEBOND("DOUBLEBOND", 1),

    /**
     * Tetrahedral atom.
     */
    TETRAHEDRAL("TETRAHEDRAL", 2),

    /**
     * Allene.
     */
    ALLENE("ALLENE", 3);




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
    private INCHI_STEREOTYPE(final String name, final int indx) { this.name = name; this.indx = indx; };

    public int getIndx() {
        return indx;
    }

    public String toString() {
        return name;
    }

	public static INCHI_STEREOTYPE getValue(int type) {
		INCHI_STEREOTYPE stereoType;
		switch (type) {
			case 0: stereoType = INCHI_STEREOTYPE.NONE; break;
			case 1: stereoType = INCHI_STEREOTYPE.DOUBLEBOND; break;
			case 2: stereoType = INCHI_STEREOTYPE.TETRAHEDRAL; break;
			case 3: stereoType = INCHI_STEREOTYPE.ALLENE; break;
			default:
				stereoType = null;
		}
		return stereoType;
	}
}
