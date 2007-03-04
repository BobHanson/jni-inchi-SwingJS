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
 * Encapsulates properties of InChI Bond.  See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public class JniInchiBond {

    /**
     * Origin atom in bond.
     */
    private JniInchiAtom atomOrigin;

    /**
     * Target atom in bond.
     */
    private JniInchiAtom atomTarget;

    /**
     * Bond type.
     */
    private INCHI_BOND_TYPE type;

    /**
     * Bond 2D stereo definition.
     */
    private INCHI_BOND_STEREO stereo;

    /**
     * Create bond.
     *
     * @param atO        Origin atom
     * @param atT        Target atom
     * @param type        Bond type
     * @param stereo    Bond 2D stereo definition
     */
    public JniInchiBond(JniInchiAtom atO, JniInchiAtom atT,
            INCHI_BOND_TYPE type, INCHI_BOND_STEREO stereo) {

        this.atomOrigin = atO;
        this.atomTarget = atT;
        this.type = type;
        this.stereo = stereo;
    }

    /**
     * Create bond.
     *
     * @param atO        Origin atom
     * @param atT        Target atom
     * @param type        Bond type
     */
    public JniInchiBond(JniInchiAtom atO, JniInchiAtom atT,
            INCHI_BOND_TYPE type) {

        this(atO, atT, type, INCHI_BOND_STEREO.NONE);
    }

    /**
     * Set 2D stereo definition.
     *
     * @param stereo    Bond 2D stereo definition
     */
    public void setStereoDefinition(INCHI_BOND_STEREO stereo) {
        this.stereo = stereo;
    }

    /**
     * Returns atom at bond origin.
     * @return
     */
    public JniInchiAtom getOriginAtom() {
        return atomOrigin;
    }

    /**
     * Returns atom at bond target.
     * @return
     */
    public JniInchiAtom getTargetAtom() {
        return atomTarget;
    }

    /**
     * Returns bond type.
     * @return
     */
    public INCHI_BOND_TYPE getBondType() {
        return type;
    }

    /**
     * Returns bond stereochemistry.
     * @return
     */
    public INCHI_BOND_STEREO getBondStereo() {
        return stereo;
    }

    /**
     * Generates string representation of information on bond,
     * for debugging purposes.
     */
    public String getDebugString() {
        return("InChI Bond: "
        + atomOrigin.getElementType()
        + "-" + atomTarget.getElementType()
        + " // Type: " + type
        + " // Stereo: " + stereo
        );
    }

    /**
     * Outputs information on bond, for debugging purposes.
     */
    public void debug() {
        System.out.println(getDebugString());
    }
}
