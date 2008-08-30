/*
 *  JNI InChI Wrapper - A Java Native Interface Wrapper for InChI.
 *  Copyright 2006, 2007, 2008 Sam Adams <sea36 at users.sourceforge.net>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published
 *  by the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
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

	public static INCHI_PARITY getValue(int parity) {
		INCHI_PARITY stereoParity;
        switch (parity) {
	    	case 0:	stereoParity = INCHI_PARITY.NONE; break;
	    	case 1:	stereoParity = INCHI_PARITY.ODD; break;
	    	case 2:	stereoParity = INCHI_PARITY.EVEN; break;
	    	case 3:	stereoParity = INCHI_PARITY.UNKNOWN; break;
	    	case 4:	stereoParity = INCHI_PARITY.UNDEFINED; break;
	    	default:
	    		stereoParity = null;
        }
        return stereoParity;
	}
}
