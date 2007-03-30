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
 * SKIP     (-2)    Not used in InChI library<br>
 * EOF      (-1)    No structural data has been provided<br>
 * OKAY     (0)     Success, no errors or warnings<br>
 * WARNING  (1)     Success, warning(s) issued<br>
 * ERROR    (2)     Error: no InChI has been created<br>
 * FATAL    (3)     Severe error: no InChI has been created (typically,
 *                  memory allocation failure)<br>
 * UNKNOWN  (4)     Unknown program error<br>
 * BUSY     (5)     Previous call to InChI has not returned yet<br>
 * </tt>
 * <p>See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public class INCHI_RET {

    // --- MUST BE BEFORE DECLARATIONS ---
    /**
     * List of return types.
     */
    private static List list = new ArrayList(18);
    // ---


    /**
     * Not used in InChI library.
     */
    public static final INCHI_RET SKIP      = new INCHI_RET("SKIP", -2);

    /**
     * No structural data has been provided.
     */
    public static final INCHI_RET EOF       = new INCHI_RET("EOF", -1);

    /**
     * Success; no errors or warnings.
     */
    public static final INCHI_RET OKAY      = new INCHI_RET("OKAY", 0);

    /**
     * Success; warning(s) issued.
     */
    public static final INCHI_RET WARNING   = new INCHI_RET("WARNING", 1);

    /**
     * Error: no InChI has been created.
     */
    public static final INCHI_RET ERROR     = new INCHI_RET("ERROR", 2);

    /**
     * Severe error: no InChI has been created (typically, memory allocation failure).
     */
    public static final INCHI_RET FATAL     = new INCHI_RET("FATAL", 3);

    /**
     * Unknown program error.
     */
    public static final INCHI_RET UNKNOWN   = new INCHI_RET("UNKNOWN", 4);

    /**
     * Previuos call to InChI has not returned yet.
     */
    public static final INCHI_RET BUSY      = new INCHI_RET("BUSY", 5);




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
	private INCHI_RET(final String name, final int indx) { this.name = name; this.indx = indx; list.add(this); };

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
