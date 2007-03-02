/* File: JniInchiStructure.java
 * Author: Sam Adams
 *
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sam Adams
 */
public class JniInchiStructure {

    /**
     * List of atoms.
     */
    private List atomList = new ArrayList();

    /**
     * List of bonds.
     */
    private List bondList = new ArrayList();

    /**
     * List of stero parities.
     */
    private List stereoList = new ArrayList();


    /**
     * Returns number of atoms in structure.
     */
    public int getNumAtoms() {
        return atomList.size();
    }

    /**
     * Returns number of bonds in structure.
     */
    public int getNumBonds() {
        return bondList.size();
    }

    /**
     * Returns number of stereo parities in strucuture.
     */
    public int getNumStereo0D() {
        return stereoList.size();
    }


    /**
     * Adds atom to inchi molecule.
     *
     * @param atom  Atom to add
     * @return      Added atom
     */
    public JniInchiAtom addAtom(JniInchiAtom atom) {
        atomList.add(atom);
        return atom;
    }

    /**
     * Adds bond to inchi molecule.
     *
     * @param bond  Bond to add
     * @return      Added bond
     */
    public JniInchiBond addBond(JniInchiBond bond) {
        bondList.add(bond);
        return bond;
    }

    /**
     * Adds 0D stereo parity to inchi molecule.
     *
     * @param parity  Parity to add
     * @return        Added parity
     */
    public JniInchiStereo0D addStereo0D(JniInchiStereo0D parity) {
        stereoList.add(parity);
        return parity;
    }


    /**
     * Returns atom from structure.
     * @param i    Index of atom to return.
     * @return
     */
    public JniInchiAtom getAtom(final int i) {
        return (JniInchiAtom) atomList.get(i);
    }

    /**
     * Returns bond from structure.
     * @param i    Index of bond to return.
     * @return
     */
    public JniInchiBond getBond(final int i) {
        return (JniInchiBond) bondList.get(i);
    }

    /**
     * Returns stereo parity from structure.
     * @param i    Index of stereo parity to return.
     * @return
     */
    public JniInchiStereo0D getStereo0D(final int i) {
        return (JniInchiStereo0D) stereoList.get(i);
    }

    public void setStructure(JniInchiStructure structure) {
        this.atomList = structure.atomList;
        this.bondList = structure.bondList;
        this.stereoList = structure.stereoList;
    }
}
