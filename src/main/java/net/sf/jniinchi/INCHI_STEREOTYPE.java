/*
 *  JNI InChI Wrapper - A Java Native Interface Wrapper for InChI.
 *  Copyright 2006, 2007, 2008 Sam Adams <sea36 at users.sourceforge.net>
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301 USA
 * or see <http://www.gnu.org/licenses/>.
 */
package net.sf.jniinchi;

/**
 * Type-safe enumeration of InChI 0D parity types.  See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public class INCHI_STEREOTYPE {


    /**
     * None.
     */
    public static final INCHI_STEREOTYPE NONE           = new INCHI_STEREOTYPE("NONE", 0);

    /**
     * Stereogenic bond >A=B< or cumulene >A=C=C=B<.
     */
    public static final INCHI_STEREOTYPE DOUBLEBOND     = new INCHI_STEREOTYPE("DOUBLEBOND", 1);

    /**
     * Tetrahedral atom.
     */
    public static final INCHI_STEREOTYPE TETRAHEDRAL    = new INCHI_STEREOTYPE("TETRAHEDRAL", 2);

    /**
     * Allene.
     */
    public static final INCHI_STEREOTYPE ALLENE         = new INCHI_STEREOTYPE("ALLENE", 3);




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
