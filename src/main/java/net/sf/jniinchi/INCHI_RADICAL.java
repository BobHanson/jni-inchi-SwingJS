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
 * Type-safe enumeration of InChI radical definitions.  See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public class INCHI_RADICAL {


    /**
     * No radical status recorded.
     */
    public static final INCHI_RADICAL NONE    = new INCHI_RADICAL("NONE", 0);

    /**
     * Singlet.
     */
    public static final INCHI_RADICAL SINGLET = new INCHI_RADICAL("SINGLET", 1);

    /**
     * Doublet.
     */
    public static final INCHI_RADICAL DOUBLET = new INCHI_RADICAL("DOUBLET", 2);

    /**
     * Triplet.
     */
    public static final INCHI_RADICAL TRIPLET = new INCHI_RADICAL("TRIPLET", 3);




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
}
