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

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Type-safe enumeration of InChI return codes.
 *
 * <p>InChI library return values:<br>
 * <tt>
 * OK 					(0)
 * UNKNOWN_ERROR 		(1)
 * EMPTY_INPUT 			(2)
 * NOT_INCHI_INPUT 		(3)
 * NOT_ENOUGH_MEMORY	(4)
 * ERROR_IN_FLAG_CHAR 	(5)
 * </tt>
 * <p>See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public class INCHI_KEY {

    // --- MUST BE BEFORE DECLARATIONS ---
    /**
     * List of return types.
     */
    private static List list = new ArrayList(6);
    // ---


    public static final INCHI_KEY OK      = new INCHI_KEY("OK", 0);

    public static final INCHI_KEY UNKNOWN_ERROR = new INCHI_KEY("UNKNOWN_ERROR", 1);

    public static final INCHI_KEY EMPTY_INPUT      = new INCHI_KEY("EMPTY_INPUT", 2);

    public static final INCHI_KEY NOT_INCHI_INPUT   = new INCHI_KEY("NOT_INCHI_INPUT", 3);

    public static final INCHI_KEY NOT_ENOUGH_MEMORY     = new INCHI_KEY("NOT_ENOUGH_MEMORY", 4);

    public static final INCHI_KEY ERROR_IN_FLAG_CHAR     = new INCHI_KEY("ERROR_IN_FLAG_CHAR", 5);





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
    @SuppressWarnings("unchecked")
	private INCHI_KEY(final String name, final int indx) { this.name = name; this.indx = indx; list.add(this); };

    public int getIndx() {
        return indx;
    }

    public String toString() {
        return name;
    }

    protected static List getList() {
        return list;
    }
}
