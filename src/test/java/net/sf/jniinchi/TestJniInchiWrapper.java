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
import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class TestJniInchiWrapper {

    // Test molecules

    /**
     * Generates input for a chlorine atom.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getChlorineAtom(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "Cl"));

        return input;
    }

    /**
     * Generates input for a chlorine atom.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getChlorineIon(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "Cl"));
        a1.setCharge(-1);
        input.getAtom(0).setRadical(INCHI_RADICAL.SINGLET);

        return input;
    }

    /**
     * Generates input for hydrogen chloride, with implicit H atom.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getHydrogenChlorideImplicitH(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "Cl"));
        a1.setImplicitH(1);

        return input;
    }

    /**
     * Generates input for hydrogen chloride, with implicit protium atom.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getHydrogenChlorideImplicitP(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "Cl"));
        a1.setImplicitProtium(1);

        return input;
    }

    /**
     * Generates input for hydrogen chloride, with implicit deuterium atom.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getHydrogenChlorideImplicitD(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "Cl"));
        a1.setImplicitDeuterium(1);

        return input;
    }

    /**
     * Generates input for hydrogen chloride, with implicit tritium atom.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getHydrogenChlorideImplicitT(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "Cl"));
        a1.setImplicitTritium(1);

        return input;
    }

    /**
     * Generates input for a 37Cl atom by isotopic mass.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getChlorine37Atom(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "Cl"));
        a1.setIsotopicMass(37);

        return input;
    }

    /**
     * Generates input for a 37Cl atom by isotopic mass shift.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getChlorine37ByIsotopicMassShiftAtom(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "Cl"));
        a1.setIsotopicMassShift(+2);

        return input;
    }

    /**
     * Generates input for a methyl radical, with implicit hydrogens.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getMethylRadical(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        a1.setImplicitH(3);
        a1.setRadical(INCHI_RADICAL.DOUBLET);

        return input;
    }

    /**
     * Generates input for an ethane molecule, with no coordinates and implicit
     * hydrogens.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getEthane(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        a1.setImplicitH(3);
        a2.setImplicitH(3);

        // Add bond
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));

        return input;
    }

    /**
     * Generates input for an ethene molecule, with no coordinates and implicit
     * hydrogens.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getEthene(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        a1.setImplicitH(2);
        a2.setImplicitH(2);

        // Add bond
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.DOUBLE));

        return input;
    }

    /**
     * Generates input for an ethyne molecule, with no coordinates and implicit
     * hydrogens.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getEthyne(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        a1.setImplicitH(1);
        a2.setImplicitH(1);

        // Add bond
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.TRIPLE));

        return input;
    }


    /**
     * Generates input for an (E)-1,2-dichloroethene molecule, with 2D
     * coordinates and implicit hydrogens.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getE12dichloroethene2D(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(2.866, -0.250, 0.000, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(3.732, 0.250, 0.000, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(2.000, 2.500, 0.000, "Cl"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(4.598, -0.250, 0.000, "Cl"));
        a1.setImplicitH(1);
        a2.setImplicitH(1);

        // Add bond
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a4, INCHI_BOND_TYPE.SINGLE));

        return input;
    }

    /**
     * Generates input for an (E)-1,2-dichloroethene molecule, with 2D
     * coordinates and implicit hydrogens.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getZ12dichloroethene2D(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(2.866, -0.440, 0.000, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(3.732, 0.060, 0.000, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(2.000, 0.060, 0.000, "Cl"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(3.732, 1.060, 0.000, "Cl"));
        a1.setImplicitH(1);
        a2.setImplicitH(1);

        // Add bond
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a4, INCHI_BOND_TYPE.SINGLE));

        return input;
    }

    /**
     * Generates input for an (E)-1,2-dichloroethene molecule, with 0D
     * coordinates.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput get12dichloroethene0D(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "Cl"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "Cl"));
        a1.setImplicitH(1);
        a2.setImplicitH(1);

        // Add bond
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a4, INCHI_BOND_TYPE.SINGLE));

        return input;
    }

    /**
     * Generates input for an (E)-1,2-dichloroethene molecule, with 0D
     * coordinates and stereo parities.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getE12dichloroethene0D(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "Cl"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "Cl"));
        a1.setImplicitH(1);
        a2.setImplicitH(1);

        // Add bond
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a4, INCHI_BOND_TYPE.SINGLE));

        // Add stereo parities
        input.addStereo0D(JniInchiStereo0D.createNewDoublebondStereo0D(a3, a1, a2, a4, INCHI_PARITY.EVEN));

        return input;
    }

    /**
     * Generates input for an (E)-1,2-dichloroethene molecule, with 2D
     * coordinates and stereo parities.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getZ12dichloroethene0D(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "Cl"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "Cl"));
        a1.setImplicitH(1);
        a2.setImplicitH(1);

        // Add bond
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a4, INCHI_BOND_TYPE.SINGLE));

        // Add stereo parities
        input.addStereo0D(JniInchiStereo0D.createNewDoublebondStereo0D(a3, a1, a2, a4, INCHI_PARITY.ODD));

        return input;
    }

    /**
     * Generates input for L-alanine molecule, with 3D coordinates and
     * implicit hydrogens.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getLAlanine3D(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(-0.358, 0.819, 20.655, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(-1.598, -0.032, 20.905, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(-0.275, 2.014, 21.574, "N"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.952, 0.043, 20.838, "C"));
        JniInchiAtom a5 = input.addAtom(new JniInchiAtom(-2.678, 0.479, 21.093, "O"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(-1.596, -1.239, 20.958, "O"));

        a1.setImplicitH(1);
        a3.setImplicitH(2);
        a4.setImplicitH(3);
        a5.setImplicitH(1);

        // Add bonds
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));

        return input;
    }

    /**
     * Generates input for D-alanine molecule, with 3D coordinates and
     * implicit hydrogens.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getDAlanine3D(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.358, 0.819, 20.655, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(1.598, -0.032, 20.905, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.275, 2.014, 21.574, "N"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(-0.952, 0.043, 20.838, "C"));
        JniInchiAtom a5 = input.addAtom(new JniInchiAtom(2.678, 0.479, 21.093, "O"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(1.596, -1.239, 20.958, "O"));

        a1.setImplicitH(1);
        a3.setImplicitH(2);
        a4.setImplicitH(3);
        a5.setImplicitH(1);

        // Add bonds
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));

        return input;
    }

    /**
     * Generates input for alanine molecule, with 2D coordinates.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getAlanine2D(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(264.0, 968.0, 0.0, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(295.0, 985.0, 0.0, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(233.0, 986.0, 0.0, "N"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(264.0, 932.0, 0.0, "C"));
        JniInchiAtom a5 = input.addAtom(new JniInchiAtom(326.0, 967.0, 0.0, "O"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(295.0, 1021.0, 0.0, "O"));

        a1.setImplicitH(1);
        a3.setImplicitH(2);
        a4.setImplicitH(3);
        a5.setImplicitH(1);

        // Add bonds
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));

        return input;
    }

    /**
     * Generates input for L-alanine molecule, with 2D coordinates and
     * bond stereo definitions.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getLAlanine2D(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(264.0, 968.0, 0.0, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(295.0, 985.0, 0.0, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(233.0, 986.0, 0.0, "N"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(264.0, 932.0, 0.0, "C"));
        JniInchiAtom a5 = input.addAtom(new JniInchiAtom(326.0, 967.0, 0.0, "O"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(295.0, 1021.0, 0.0, "O"));

        a1.setImplicitH(1);
        a3.setImplicitH(2);
        a4.setImplicitH(3);
        a5.setImplicitH(1);

        // Add bonds
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE)).setStereoDefinition(INCHI_BOND_STEREO.SINGLE_1DOWN);
        input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));

        return input;
    }

    /**
     * Generates input for D-alanine molecule, with 2D coordinates and
     * bond stereo definitions.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getDAlanine2D(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(264.0, 968.0, 0.0, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(295.0, 985.0, 0.0, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(233.0, 986.0, 0.0, "N"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(264.0, 932.0, 0.0, "C"));
        JniInchiAtom a5 = input.addAtom(new JniInchiAtom(326.0, 967.0, 0.0, "O"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(295.0, 1021.0, 0.0, "O"));

        a1.setImplicitH(1);
        a3.setImplicitH(2);
        a4.setImplicitH(3);
        a5.setImplicitH(1);

        // Add bonds
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE)).setStereoDefinition(INCHI_BOND_STEREO.SINGLE_1UP);
        input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));

        return input;
    }


    /**
     * Generates input for alanine molecule with no coordinates.
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getAlanine0D(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "N"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
        JniInchiAtom a5 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "O"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "O"));
        JniInchiAtom a7 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "H"));
        a3.setImplicitH(2);
        a4.setImplicitH(3);
        a5.setImplicitH(1);

        // Add bonds
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a1, a7, INCHI_BOND_TYPE.SINGLE));

        return input;
    }

    /**
     * Generates input for L-alanine molecule with no coordinates but 0D stereo
     * parities.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getLAlanine0D(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "N"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
        JniInchiAtom a5 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "O"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "O"));
        JniInchiAtom a7 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "H"));
        a3.setImplicitH(2);
        a4.setImplicitH(3);
        a5.setImplicitH(1);

        // Add bonds
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a1, a7, INCHI_BOND_TYPE.SINGLE));

        // Add stereo parities
        input.addStereo0D(JniInchiStereo0D.createNewTetrahedralStereo0D(a1, a3, a4, a7, a2, INCHI_PARITY.ODD));

        return input;
    }

    /**
     * Generates input for D-alanine molecule with no coordinates but 0D stereo
     * parities.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getDAlanine0D(final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "N"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
        JniInchiAtom a5 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "O"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "O"));
        JniInchiAtom a7 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "H"));
        a3.setImplicitH(2);
        a4.setImplicitH(3);
        a5.setImplicitH(1);

        // Add bonds
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a1, a7, INCHI_BOND_TYPE.SINGLE));

        // Add stereo parities
        input.addStereo0D(JniInchiStereo0D.createNewTetrahedralStereo0D(a1, a3, a4, a7, a2, INCHI_PARITY.EVEN));
        return input;
    }


    // Test atom handling

    /**
     * Tests element name is correctly passed to InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiFromChlorineAtom() throws Exception {
        JniInchiInput input = getChlorineAtom("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/Cl", output.getInchi());
    }

    /**
     * Tests charge is correctly passed to InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiFromChlorineIon() throws Exception {
        JniInchiInput input = getChlorineIon("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/Cl/q-1", output.getInchi());
    }

    /**
     * Tests isotopic mass is correctly passed to InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiFromChlorine37Atom() throws Exception {
        JniInchiInput input = getChlorine37Atom("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/Cl/i1+2", output.getInchi());
    }

    /**
     * Tests isotopic mass shift is correctly passed to InChI.
     * @throws Exception
     */
    @Test
    public void testGetInchiFromChlorine37ByIstopicMassShiftAtom() throws Exception {
        JniInchiInput input = getChlorine37ByIsotopicMassShiftAtom("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/Cl/i1+2", output.getInchi());
    }

    /**
     * Tests implicit hydrogen count is correctly passed to InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiFromHydrogenChlorideImplicitH() throws Exception {
        JniInchiInput input = getHydrogenChlorideImplicitH("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/ClH/h1H", output.getInchi());
    }

    /**
     * Tests implicit protium count is correctly passed to InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiFromHydrogenChlorideImplicitP() throws Exception {
        JniInchiInput input = getHydrogenChlorideImplicitP("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/ClH/h1H/i/hH", output.getInchi());
    }

    /**
     * Tests implicit deuterium count is correctly passed to InChi.
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiFromHydrogenChlorideImplicitD() throws Exception {
        JniInchiInput input = getHydrogenChlorideImplicitD("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/ClH/h1H/i/hD", output.getInchi());
    }

    /**
     * Tests implicit tritium count is correctly passed to InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiFromHydrogenChlorideImplicitT() throws Exception {
        JniInchiInput input = getHydrogenChlorideImplicitT("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/ClH/h1H/i/hT", output.getInchi());
    }

    /**
     * Tests radical state is correctly passed to InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiFromMethylRadical() throws Exception {
        JniInchiInput input = getMethylRadical("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/CH3/h1H3", output.getInchi());
    }



    // Test bond handling

    /**
     * Tests single bond is correctly passed to InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiFromEthane() throws Exception {
        JniInchiInput input = getEthane("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/C2H6/c1-2/h1-2H3", output.getInchi());
    }

    /**
     * Tests double bond is correctly passed to InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiFromEthene() throws Exception {
        JniInchiInput input = getEthene("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/C2H4/c1-2/h1-2H2", output.getInchi());
    }

    /**
     * Tests triple bond is correctly passed to InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiFromEthyne() throws Exception {
        JniInchiInput input = getEthyne("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/C2H2/c1-2/h1-2H", output.getInchi());
    }


    // Test 2D coordinate handling

    /**
     * Tests 2D coordinates are correctly passed to InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiEandZ12Dichloroethene2D() throws Exception {
        JniInchiInput inputE = getE12dichloroethene2D("");
        JniInchiOutput outputE = JniInchiWrapper.getInchi(inputE);
        Assert.assertEquals(INCHI_RET.OKAY, outputE.getReturnStatus());
        Assert.assertEquals("InChI=1/C2H2Cl2/c3-1-2-4/h1-2H/b2-1+", outputE.getInchi());

        JniInchiInput inputZ = getZ12dichloroethene2D("");
        JniInchiOutput outputZ = JniInchiWrapper.getInchi(inputZ);
        Assert.assertEquals(INCHI_RET.OKAY, outputZ.getReturnStatus());
        Assert.assertEquals("InChI=1/C2H2Cl2/c3-1-2-4/h1-2H/b2-1-", outputZ.getInchi());
    }


    // Test 3D coordinate handling

    /**
     * Tests InChI generation from L and D-Alanine molecules, with 3D
     * coordinates.
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiFromLandDAlanine3D() throws Exception {
        JniInchiInput inputL = getLAlanine3D("");
        JniInchiOutput outputL = JniInchiWrapper.getInchi(inputL);
        Assert.assertEquals(INCHI_RET.OKAY, outputL.getReturnStatus());
        Assert.assertEquals("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1", outputL.getInchi());

        JniInchiInput inputD = getDAlanine3D("");
        JniInchiOutput outputD = JniInchiWrapper.getInchi(inputD);
        Assert.assertEquals(INCHI_RET.OKAY, outputD.getReturnStatus());
        Assert.assertEquals("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m1/s1", outputD.getInchi());
    };



    // Test handling of 2D coordinates with bond stereo types

    /**
     * Tests InChI generation from L and D-Alanine molecules, with 3D
     * coordinates.
     *
     * Fails due to bug in InChI software.
     * See testGetInchiFromLandDAlanine2DWithFixSp3Bug() for workaround.
     *
     * @throws Exception
     */
    @Test
    @Ignore
    public void testGetInchiFromLandDAlanine2D() throws Exception {
        /*
        JniInchiInput input = getAlanine2D("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.WARNING, output.getReturnStatus());
        Assert.assertEquals("Omitted undefined stereo", output.getMessage());
        Assert.assertEquals("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)", output.getInchi());

        JniInchiInput inputL = getLAlanine2D("");
        JniInchiOutput outputL = JniInchiWrapper.getInchi(inputL);
        Assert.assertEquals(INCHI_RET.OKAY, outputL.getReturnStatus());
        Assert.assertEquals("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1", outputL.getInchi());

        JniInchiInput inputD = getDAlanine2D("");
        JniInchiOutput outputD = JniInchiWrapper.getInchi(inputD);
        Assert.assertEquals(INCHI_RET.OKAY, outputD.getReturnStatus());
        Assert.assertEquals("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m1/s1", outputD.getInchi());
        */
    };

    /**
     * Tests InChI generation from L and D-Alanine molecules, with 3D
     * coordinates, using FixSp3Bug option from InChI software v1.01
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiFromLandDAlanine2DWithFixSp3Bug() throws Exception {
        JniInchiInput input = getAlanine2D("-FixSp3Bug");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.WARNING, output.getReturnStatus());
        Assert.assertEquals("Omitted undefined stereo", output.getMessage());
        Assert.assertEquals("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)", output.getInchi());

        JniInchiInput inputL = getLAlanine2D("-FixSp3Bug");
        JniInchiOutput outputL = JniInchiWrapper.getInchi(inputL);
        Assert.assertEquals(INCHI_RET.OKAY, outputL.getReturnStatus());
        Assert.assertEquals("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1", outputL.getInchi());

        JniInchiInput inputD = getDAlanine2D("-FixSp3Bug");
        JniInchiOutput outputD = JniInchiWrapper.getInchi(inputD);
        Assert.assertEquals(INCHI_RET.OKAY, outputD.getReturnStatus());
        Assert.assertEquals("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m1/s1", outputD.getInchi());
    };


    // Test handling of no coordinates, with stereo parities

    /**
     * Tests InChI generation from L and D-Alanine molecules, with no
     * coordinates but tetrahedral stereo parities.
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiFromLandDAlanine0D() throws Exception {
        JniInchiInput input = getAlanine0D("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.WARNING, output.getReturnStatus());
        Assert.assertEquals("Omitted undefined stereo", output.getMessage());
        Assert.assertEquals("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)", output.getInchi());

        JniInchiInput inputL = getLAlanine0D("");
        JniInchiOutput outputL = JniInchiWrapper.getInchi(inputL);
        Assert.assertEquals(INCHI_RET.OKAY, outputL.getReturnStatus());
        Assert.assertEquals("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1", outputL.getInchi());

        JniInchiInput inputD = getDAlanine0D("");
        JniInchiOutput outputD = JniInchiWrapper.getInchi(inputD);
        Assert.assertEquals(INCHI_RET.OKAY, outputD.getReturnStatus());
        Assert.assertEquals("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m1/s1", outputD.getInchi());
    };

    /**
     * Tests InChI generation from E and Z 1,2-dichloroethene molecules, with no
     * coordinates but doublebond stereo parities.
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiEandZ12Dichloroethene0D() throws Exception {
        JniInchiInput input = get12dichloroethene0D("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.WARNING, output.getReturnStatus());
        Assert.assertEquals("Omitted undefined stereo", output.getMessage());
        Assert.assertEquals("InChI=1/C2H2Cl2/c3-1-2-4/h1-2H", output.getInchi());

        JniInchiInput inputE = getE12dichloroethene0D("");
        JniInchiOutput outputE = JniInchiWrapper.getInchi(inputE);
        Assert.assertEquals(INCHI_RET.OKAY, outputE.getReturnStatus());
        Assert.assertEquals("InChI=1/C2H2Cl2/c3-1-2-4/h1-2H/b2-1+", outputE.getInchi());

        JniInchiInput inputZ = getZ12dichloroethene0D("");
        JniInchiOutput outputZ = JniInchiWrapper.getInchi(inputZ);
        Assert.assertEquals(INCHI_RET.OKAY, outputZ.getReturnStatus());
        Assert.assertEquals("InChI=1/C2H2Cl2/c3-1-2-4/h1-2H/b2-1-", outputZ.getInchi());
    };


    // Test option checking

    /**
     * Tests option lists are canonicalised correctly.
     */
    @SuppressWarnings("unchecked")
	@Test
    public void testCheckOptionsList() throws JniInchiException {
        List opList = new ArrayList();
        opList.add(INCHI_OPTION.Compress);
        opList.add(INCHI_OPTION.SNon);
        String options = JniInchiWrapper.checkOptions(opList);
        String flag = JniInchiWrapper.flagChar;
        Assert.assertEquals(flag + "Compress " + flag + "SNon ", options);
    }

    /**
     * Tests option strings are handled checked canonicalised.
     * @throws JniInchiException
     */
    @Test
    public void testCheckOptionsString() throws JniInchiException {
        String options = JniInchiWrapper.checkOptions("  -ComPreSS  /SNon");
        String flag = JniInchiWrapper.flagChar;
        Assert.assertEquals(flag + "Compress " + flag + "SNon ", options);
    }


    // Test option handling

    /**
     * Tests passing options to inchi.
     * @throws Exception
     */
    // @Test
    public void testGetInchiWithOptions() throws Exception {
        JniInchiInput input = getLAlanine3D("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1", output.getInchi());

        input = getLAlanine3D("-compress");
        output = JniInchiWrapper.getInchi(input);
        //debug(output);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/C3H7NO2/cABBCC/hB1D2A3,1EF/tB1/m0/s1", output.getInchi());

        input = getLAlanine3D("/compress");
        output = JniInchiWrapper.getInchi(input);
        //debug(output);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/C3H7NO2/cABBCC/hB1D2A3,1EF/tB1/m0/s1", output.getInchi());

        input = getLAlanine3D("-cOMprEsS");
        output = JniInchiWrapper.getInchi(input);
        //debug(output);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/C3H7NO2/cABBCC/hB1D2A3,1EF/tB1/m0/s1", output.getInchi());
    }


    // Test structure generation from InChI strings


    /**
     * Tests element name is correctly read from InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetChlorineAtomFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/Cl");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(1, output.getNumAtoms());
        Assert.assertEquals(0, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert.assertEquals("InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", output.getAtom(0).getDebugString());
    }

    /**
     * Tests charge is correctly read from InChI.
     *
     * @throws Exception
     */
    @Test
    @Ignore
    // FIXME
    public void testGetChlorineIonFromInchi() throws Exception {
        /*
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/Cl/q-1", "-FixedH");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        //debug(output);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(1, output.getNumAtoms());
        Assert.assertEquals(0, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert.assertEquals("InChI Atom: Cl [0.0,0.0,0.0] Charge:-1 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", output.getAtom(0).getDebugString());
        */
    }

    /**
     * Tests isotopic mass is correctly read from InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetChlorine37AtomFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/Cl/i1+2");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(1, output.getNumAtoms());
        Assert.assertEquals(0, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert.assertEquals("InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:10002 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", output.getAtom(0).getDebugString());
    }

    /**
     * Tests implicit hydrogen count is correctly read from InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetHydrogenChlorideImplicitHFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/ClH/h1H");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(1, output.getNumAtoms());
        Assert.assertEquals(0, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert.assertEquals("InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE", output.getAtom(0).getDebugString());
    }

    /**
     * Tests implicit protium count is correctly read from InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetHydrogenChlorideImplicitPFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/ClH/h1H/i/hH");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(1, output.getNumAtoms());
        Assert.assertEquals(0, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert.assertEquals("InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:1 D:0 T:0 // Radical: NONE", output.getAtom(0).getDebugString());
    }

    /**
     * Tests implicit deuterium count is correctly read from InChi.
     *
     * @throws Exception
     */
    @Test
    public void testGetHydrogenChlorideImplicitDFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/ClH/h1H/i/hD");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(1, output.getNumAtoms());
        Assert.assertEquals(0, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert.assertEquals("InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:1 T:0 // Radical: NONE", output.getAtom(0).getDebugString());
    }

    /**
     * Tests implicit tritium count is correctly read from InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetHydrogenChlorideImplicitTFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/ClH/h1H/i/hT");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(1, output.getNumAtoms());
        Assert.assertEquals(0, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert.assertEquals("InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:1 // Radical: NONE", output.getAtom(0).getDebugString());
    }

    /**
     * Tests radical state is correctly read from InChI.
     *
     * @throws Exception
     */
    @Test
    @Ignore
    // Test fails due to problem with InChI library
    public void testGetMethylRadicalFromInchi() throws Exception {
        /*
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/CH3/h1H3");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(1, output.getNumAtoms());
        Assert.assertEquals(0, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:3 P:0 D:0 T:0 // Radical: DOUBLET", output.getAtom(0).getDebugString());
        */
    }


    // Test bond handling

    /**
     * Tests single bond is correctly read from InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetEthaneFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/C2H6/c1-2/h1-2H3");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(2, output.getNumAtoms());
        Assert.assertEquals(1, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:3 P:0 D:0 T:0 // Radical: NONE", output.getAtom(0).getDebugString());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:3 P:0 D:0 T:0 // Radical: NONE", output.getAtom(1).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: SINGLE // Stereo: NONE", output.getBond(0).getDebugString());
    }

    /**
     * Tests double bond is correctly read from InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetEtheneFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/C2H4/c1-2/h1-2H2");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(2, output.getNumAtoms());
        Assert.assertEquals(1, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:2 P:0 D:0 T:0 // Radical: NONE", output.getAtom(0).getDebugString());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:2 P:0 D:0 T:0 // Radical: NONE", output.getAtom(1).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: DOUBLE // Stereo: NONE", output.getBond(0).getDebugString());
    }

    /**
     * Tests triple bond is correctly read from InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetEthyneFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/C2H2/c1-2/h1-2H");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(2, output.getNumAtoms());
        Assert.assertEquals(1, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE", output.getAtom(0).getDebugString());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE", output.getAtom(1).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: TRIPLE // Stereo: NONE", output.getBond(0).getDebugString());
    }


// Test handling of no coordinates, with stereo parities

    /**
     * Tests generation of L and D-Alanine molecules, from InChIs with
     * tetrahedral stereo parities.
     *
     * @throws Exception
     */
    @Test
    public void testGetLandDAlanine0DFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(6, output.getNumAtoms());
        Assert.assertEquals(5, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:3 P:0 D:0 T:0 // Radical: NONE", output.getAtom(0).getDebugString());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE", output.getAtom(1).getDebugString());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", output.getAtom(2).getDebugString());
        Assert.assertEquals("InChI Atom: N [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:2 P:0 D:0 T:0 // Radical: NONE", output.getAtom(3).getDebugString());
        Assert.assertEquals("InChI Atom: O [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", output.getAtom(4).getDebugString());
        Assert.assertEquals("InChI Atom: O [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE", output.getAtom(5).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: SINGLE // Stereo: NONE", output.getBond(0).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: SINGLE // Stereo: NONE", output.getBond(1).getDebugString());
        Assert.assertEquals("InChI Bond: N-C // Type: SINGLE // Stereo: NONE", output.getBond(2).getDebugString());
        Assert.assertEquals("InChI Bond: O-C // Type: DOUBLE // Stereo: NONE", output.getBond(3).getDebugString());
        Assert.assertEquals("InChI Bond: O-C // Type: SINGLE // Stereo: NONE", output.getBond(4).getDebugString());

        JniInchiInputInchi inputL = new JniInchiInputInchi("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1");
        JniInchiOutputStructure outputL = JniInchiWrapper.getStructureFromInchi(inputL);
        Assert.assertEquals(INCHI_RET.OKAY, outputL.getReturnStatus());
        Assert.assertEquals(7, outputL.getNumAtoms());
        Assert.assertEquals(6, outputL.getNumBonds());
        Assert.assertEquals(1, outputL.getNumStereo0D());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:3 P:0 D:0 T:0 // Radical: NONE", outputL.getAtom(0).getDebugString());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputL.getAtom(1).getDebugString());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputL.getAtom(2).getDebugString());
        Assert.assertEquals("InChI Atom: N [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:2 P:0 D:0 T:0 // Radical: NONE", outputL.getAtom(3).getDebugString());
        Assert.assertEquals("InChI Atom: O [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputL.getAtom(4).getDebugString());
        Assert.assertEquals("InChI Atom: O [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE", outputL.getAtom(5).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: SINGLE // Stereo: NONE", outputL.getBond(0).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: SINGLE // Stereo: NONE", outputL.getBond(1).getDebugString());
        Assert.assertEquals("InChI Bond: N-C // Type: SINGLE // Stereo: NONE", outputL.getBond(2).getDebugString());
        Assert.assertEquals("InChI Bond: O-C // Type: DOUBLE // Stereo: NONE", outputL.getBond(3).getDebugString());
        Assert.assertEquals("InChI Bond: O-C // Type: SINGLE // Stereo: NONE", outputL.getBond(4).getDebugString());
        Assert.assertEquals("InChI Stereo0D: C [H,C,C,N] Type::TETRAHEDRAL // Parity:ODD", outputL.getStereo0D(0).getDebugString());

        JniInchiInputInchi inputD = new JniInchiInputInchi("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m1/s1");
        JniInchiOutputStructure outputD = JniInchiWrapper.getStructureFromInchi(inputD);
        Assert.assertEquals(INCHI_RET.OKAY, outputD.getReturnStatus());
        Assert.assertEquals(7, outputD.getNumAtoms());
        Assert.assertEquals(6, outputD.getNumBonds());
        Assert.assertEquals(1, outputD.getNumStereo0D());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:3 P:0 D:0 T:0 // Radical: NONE", outputD.getAtom(0).getDebugString());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputD.getAtom(1).getDebugString());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputD.getAtom(2).getDebugString());
        Assert.assertEquals("InChI Atom: N [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:2 P:0 D:0 T:0 // Radical: NONE", outputD.getAtom(3).getDebugString());
        Assert.assertEquals("InChI Atom: O [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputD.getAtom(4).getDebugString());
        Assert.assertEquals("InChI Atom: O [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE", outputD.getAtom(5).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: SINGLE // Stereo: NONE", outputD.getBond(0).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: SINGLE // Stereo: NONE", outputD.getBond(1).getDebugString());
        Assert.assertEquals("InChI Bond: N-C // Type: SINGLE // Stereo: NONE", outputD.getBond(2).getDebugString());
        Assert.assertEquals("InChI Bond: O-C // Type: DOUBLE // Stereo: NONE", outputD.getBond(3).getDebugString());
        Assert.assertEquals("InChI Bond: O-C // Type: SINGLE // Stereo: NONE", outputD.getBond(4).getDebugString());
        Assert.assertEquals("InChI Stereo0D: C [H,C,C,N] Type::TETRAHEDRAL // Parity:EVEN", outputD.getStereo0D(0).getDebugString());
    };

    /**
     * Tests generation of E and Z 1,2-dichloroethene molecules, from InChIs
     * with doublebond stereo parities.
     *
     * @throws Exception
     */
    @Test
    public void testGetEandZ12Dichloroethene0DFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/C2H2Cl2/c3-1-2-4/h1-2H");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(4, output.getNumAtoms());
        Assert.assertEquals(3, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE", output.getAtom(0).getDebugString());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE", output.getAtom(1).getDebugString());
        Assert.assertEquals("InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", output.getAtom(2).getDebugString());
        Assert.assertEquals("InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", output.getAtom(3).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: DOUBLE // Stereo: NONE", output.getBond(0).getDebugString());
        Assert.assertEquals("InChI Bond: Cl-C // Type: SINGLE // Stereo: NONE", output.getBond(1).getDebugString());
        Assert.assertEquals("InChI Bond: Cl-C // Type: SINGLE // Stereo: NONE", output.getBond(2).getDebugString());

        JniInchiInputInchi inputE = new JniInchiInputInchi("InChI=1/C2H2Cl2/c3-1-2-4/h1-2H/b2-1+");
        JniInchiOutputStructure outputE = JniInchiWrapper.getStructureFromInchi(inputE);
        Assert.assertEquals(INCHI_RET.OKAY, outputE.getReturnStatus());
        Assert.assertEquals(6, outputE.getNumAtoms());
        Assert.assertEquals(5, outputE.getNumBonds());
        Assert.assertEquals(1, outputE.getNumStereo0D());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputE.getAtom(0).getDebugString());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputE.getAtom(1).getDebugString());
        Assert.assertEquals("InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputE.getAtom(2).getDebugString());
        Assert.assertEquals("InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputE.getAtom(3).getDebugString());
        Assert.assertEquals("InChI Atom: H [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputE.getAtom(4).getDebugString());
        Assert.assertEquals("InChI Atom: H [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputE.getAtom(5).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: DOUBLE // Stereo: NONE", outputE.getBond(0).getDebugString());
        Assert.assertEquals("InChI Bond: Cl-C // Type: SINGLE // Stereo: NONE", outputE.getBond(1).getDebugString());
        Assert.assertEquals("InChI Bond: Cl-C // Type: SINGLE // Stereo: NONE", outputE.getBond(2).getDebugString());
        Assert.assertEquals("InChI Stereo0D: - [H,C,C,H] Type::DOUBLEBOND // Parity:EVEN", outputE.getStereo0D(0).getDebugString());

        JniInchiInputInchi inputZ = new JniInchiInputInchi("InChI=1/C2H2Cl2/c3-1-2-4/h1-2H/b2-1-");
        JniInchiOutputStructure outputZ = JniInchiWrapper.getStructureFromInchi(inputZ);
        Assert.assertEquals(INCHI_RET.OKAY, outputZ.getReturnStatus());
        Assert.assertEquals(6, outputZ.getNumAtoms());
        Assert.assertEquals(5, outputZ.getNumBonds());
        Assert.assertEquals(1, outputZ.getNumStereo0D());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputZ.getAtom(0).getDebugString());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputZ.getAtom(1).getDebugString());
        Assert.assertEquals("InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputZ.getAtom(2).getDebugString());
        Assert.assertEquals("InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputZ.getAtom(3).getDebugString());
        Assert.assertEquals("InChI Atom: H [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputZ.getAtom(4).getDebugString());
        Assert.assertEquals("InChI Atom: H [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", outputZ.getAtom(5).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: DOUBLE // Stereo: NONE", outputZ.getBond(0).getDebugString());
        Assert.assertEquals("InChI Bond: Cl-C // Type: SINGLE // Stereo: NONE", outputZ.getBond(1).getDebugString());
        Assert.assertEquals("InChI Bond: Cl-C // Type: SINGLE // Stereo: NONE", outputZ.getBond(2).getDebugString());
        Assert.assertEquals("InChI Stereo0D: - [H,C,C,H] Type::DOUBLEBOND // Parity:ODD", outputZ.getStereo0D(0).getDebugString());
    };

    /**
     * Tests thread safety - starts ten threads, and sets them generating
     * InChIs for randomly picked elements. Checks generated InChIs are as
     * expected.
     *
     */
    @Test
    public void multithreading() {
        int nthreads = 10;
        TestThread[] threads = new TestThread[nthreads];
        for (int i = 0; i < nthreads; i++) {
            threads[i] = new TestThread(i);
            threads[i].start();
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException ie) {
            Assert.assertTrue("Interrupted", false);
        }

        boolean allRunning = true;

        for (int i = 0; i < nthreads; i++) {
            if (threads[i].runCount < 1) {
                allRunning = false;
            }
            threads[i].timeToStop = true;
        }

        Assert.assertTrue("All threads running", allRunning);

        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {
            Assert.assertTrue("Interrupted", false);
        }

        boolean allDead = true;

        for (int i = 0; i < nthreads; i++) {
            if (threads[i].isAlive()) {
                allDead = false;
            }
        }

        Assert.assertTrue("All threads stopped", allDead);

        int failureCount = 0;

        for (int i = 0; i < nthreads; i++) {
            failureCount += threads[i].failCount;
        }

        Assert.assertEquals("Fail count", 0, failureCount);
    }



    private class TestThread extends Thread {

        private String[] ELS = {"H", "He", "Li", "Be", "B", "C", "N",
            "O", "F", "Ne", "Na", "Mg", "Al", "Si", "P", "S", "Cl", "Ar"};

        public boolean timeToStop;

        public int failCount;
        public int runCount;
        public JniInchiException ex;
        public int i;

        public TestThread(final int i) {
            this.i = i;
        }

        public void run() {
            timeToStop = false;
            failCount = 0;
            runCount = 0;
            Random rand = new Random();
            while (!timeToStop) {
                // System.out.println(i + " " + runCount + " " + failCount);
                runCount++;
                JniInchiInput input = new JniInchiInput();
                String element = ELS[rand.nextInt(ELS.length)];
                input.addAtom(new JniInchiAtom(0, 0, 0, element));
                try {
                    JniInchiOutput output = JniInchiWrapper.getInchi(input);
                    if (INCHI_RET.OKAY != output.getReturnStatus()) {
                        failCount++;
                    } else if (!("InChI=1/" + element).equals(output.getInchi())) {
                        failCount++;
                    }
                } catch (JniInchiException e) {
                    failCount++;
                    ex = e;
                    break;
                }
            }
        }

    }
}
