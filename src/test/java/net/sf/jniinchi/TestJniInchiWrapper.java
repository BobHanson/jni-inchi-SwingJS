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
    private static JniInchiInput getChlorineAtom(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "Cl"));
        input.getAtom(0).setImplicitH(0);
        return input;
    }

    /**
     * Generates input for a chlorine atom.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getChlorineIon(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "Cl"));
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
    private static JniInchiInput getHydrogenChlorideImplicitH(
            final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "Cl"));
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
    private static JniInchiInput getHydrogenChlorideImplicitP(
            final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "Cl"));
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
    private static JniInchiInput getHydrogenChlorideImplicitD(
            final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "Cl"));
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
    private static JniInchiInput getHydrogenChlorideImplicitT(
            final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "Cl"));
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
    private static JniInchiInput getChlorine37Atom(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "Cl"));
        a1.setIsotopicMass(37);
        a1.setImplicitH(0);

        return input;
    }

    /**
     * Generates input for a 37Cl atom by isotopic mass shift.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getChlorine37ByIsotopicMassShiftAtom(
            final String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "Cl"));
        a1.setIsotopicMassShift(+2);
        input.getAtom(0).setImplicitH(0);

        return input;
    }

    /**
     * Generates input for a methyl radical, with implicit hydrogens.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getMethylRadical(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "C"));
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
    private static JniInchiInput getEthane(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "C"));
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
    private static JniInchiInput getEthene(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "C"));
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
    private static JniInchiInput getEthyne(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "C"));
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
    private static JniInchiInput getE12dichloroethene2D(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(2.866, -0.250, 0.000,
                "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(3.732, 0.250, 0.000,
                "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(2.000, 2.500, 0.000,
                "Cl"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(4.598, -0.250, 0.000,
                "Cl"));
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
    private static JniInchiInput getZ12dichloroethene2D(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(2.866, -0.440, 0.000,
                "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(3.732, 0.060, 0.000,
                "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(2.000, 0.060, 0.000,
                "Cl"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(3.732, 1.060, 0.000,
                "Cl"));
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
    private static JniInchiInput get12dichloroethene0D(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "Cl"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "Cl"));
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
    private static JniInchiInput getE12dichloroethene0D(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "Cl"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "Cl"));
        a1.setImplicitH(1);
        a2.setImplicitH(1);

        // Add bond
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a4, INCHI_BOND_TYPE.SINGLE));

        // Add stereo parities
        input.addStereo0D(JniInchiStereo0D.createNewDoublebondStereo0D(a3, a1,
                a2, a4, INCHI_PARITY.EVEN));

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
    private static JniInchiInput getZ12dichloroethene0D(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "Cl"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000,
                "Cl"));
        a1.setImplicitH(1);
        a2.setImplicitH(1);

        // Add bond
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a4, INCHI_BOND_TYPE.SINGLE));

        // Add stereo parities
        input.addStereo0D(JniInchiStereo0D.createNewDoublebondStereo0D(a3, a1,
                a2, a4, INCHI_PARITY.ODD));

        return input;
    }

    /**
     * Generates input for L-alanine molecule, with 3D coordinates and implicit
     * hydrogens.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getLAlanine3D(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(-0.358, 0.819, 20.655,
                "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(-1.598, -0.032,
                20.905, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(-0.275, 2.014, 21.574,
                "N"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.952, 0.043, 20.838,
                "C"));
        JniInchiAtom a5 = input.addAtom(new JniInchiAtom(-2.678, 0.479, 21.093,
                "O"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(-1.596, -1.239,
                20.958, "O"));

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
     * Generates input for D-alanine molecule, with 3D coordinates and implicit
     * hydrogens.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getDAlanine3D(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.358, 0.819, 20.655,
                "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(1.598, -0.032, 20.905,
                "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.275, 2.014, 21.574,
                "N"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(-0.952, 0.043, 20.838,
                "C"));
        JniInchiAtom a5 = input.addAtom(new JniInchiAtom(2.678, 0.479, 21.093,
                "O"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(1.596, -1.239, 20.958,
                "O"));

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
    private static JniInchiInput getAlanine2D(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input
                .addAtom(new JniInchiAtom(264.0, 968.0, 0.0, "C"));
        JniInchiAtom a2 = input
                .addAtom(new JniInchiAtom(295.0, 985.0, 0.0, "C"));
        JniInchiAtom a3 = input
                .addAtom(new JniInchiAtom(233.0, 986.0, 0.0, "N"));
        JniInchiAtom a4 = input
                .addAtom(new JniInchiAtom(264.0, 932.0, 0.0, "C"));
        JniInchiAtom a5 = input
                .addAtom(new JniInchiAtom(326.0, 967.0, 0.0, "O"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(295.0, 1021.0, 0.0,
                "O"));

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
     * Generates input for L-alanine molecule, with 2D coordinates and bond
     * stereo definitions.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getLAlanine2Da(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input
                .addAtom(new JniInchiAtom(264.0, 968.0, 0.0, "C"));
        JniInchiAtom a2 = input
                .addAtom(new JniInchiAtom(295.0, 985.0, 0.0, "C"));
        JniInchiAtom a3 = input
                .addAtom(new JniInchiAtom(233.0, 986.0, 0.0, "N"));
        JniInchiAtom a4 = input
                .addAtom(new JniInchiAtom(264.0, 932.0, 0.0, "C"));
        JniInchiAtom a5 = input
                .addAtom(new JniInchiAtom(326.0, 967.0, 0.0, "O"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(295.0, 1021.0, 0.0,
                "O"));

        a1.setImplicitH(1);
        a3.setImplicitH(2);
        a4.setImplicitH(3);
        a5.setImplicitH(1);

        // Add bonds
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE))
                .setStereoDefinition(INCHI_BOND_STEREO.SINGLE_1DOWN);
        input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));

        return input;
    }


    private static JniInchiInput getLAlanine2Db(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input
                .addAtom(new JniInchiAtom(264.0, 968.0, 0.0, "C"));
        JniInchiAtom a2 = input
                .addAtom(new JniInchiAtom(295.0, 985.0, 0.0, "C"));
        JniInchiAtom a3 = input
                .addAtom(new JniInchiAtom(233.0, 986.0, 0.0, "N"));
        JniInchiAtom a4 = input
                .addAtom(new JniInchiAtom(264.0, 932.0, 0.0, "C"));
        JniInchiAtom a5 = input
                .addAtom(new JniInchiAtom(326.0, 967.0, 0.0, "O"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(295.0, 1021.0, 0.0,
                "O"));

        a1.setImplicitH(1);
        a3.setImplicitH(2);
        a4.setImplicitH(3);
        a5.setImplicitH(1);

        // Add bonds
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a3, a1, INCHI_BOND_TYPE.SINGLE))
                .setStereoDefinition(INCHI_BOND_STEREO.SINGLE_1UP);
        input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));

        return input;
    }

    /**
     * Generates input for D-alanine molecule, with 2D coordinates and bond
     * stereo definitions.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getDAlanine2D(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input
                .addAtom(new JniInchiAtom(264.0, 968.0, 0.0, "C"));
        JniInchiAtom a2 = input
                .addAtom(new JniInchiAtom(295.0, 985.0, 0.0, "C"));
        JniInchiAtom a3 = input
                .addAtom(new JniInchiAtom(233.0, 986.0, 0.0, "N"));
        JniInchiAtom a4 = input
                .addAtom(new JniInchiAtom(264.0, 932.0, 0.0, "C"));
        JniInchiAtom a5 = input
                .addAtom(new JniInchiAtom(326.0, 967.0, 0.0, "O"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(295.0, 1021.0, 0.0,
                "O"));

        a1.setImplicitH(1);
        a3.setImplicitH(2);
        a4.setImplicitH(3);
        a5.setImplicitH(1);

        // Add bonds
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE))
                .setStereoDefinition(INCHI_BOND_STEREO.SINGLE_1UP);
        input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));

        return input;
    }

    /**
     * Generates input for alanine molecule with no coordinates.
     *
     * @param options
     * @return
     * @throws JniInchiException
     */
    private static JniInchiInput getAlanine0D(final String options)
            throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a0 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "N"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "O"));
        JniInchiAtom a5 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "O"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "H"));
        a2.setImplicitH(2);
        a3.setImplicitH(3);
        a4.setImplicitH(1);

        // Add bonds
        input.addBond(new JniInchiBond(a0, a1, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a0, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a0, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a5, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a0, a6, INCHI_BOND_TYPE.SINGLE));

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
    private static JniInchiInput getLAlanine0D(final String options)
            throws JniInchiException {
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
        input.addStereo0D(JniInchiStereo0D.createNewTetrahedralStereo0D(a1, a3,
                a4, a7, a2, INCHI_PARITY.ODD));

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
    private static JniInchiInput getDAlanine0D(final String options)
            throws JniInchiException {
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
        input.addStereo0D(JniInchiStereo0D.createNewTetrahedralStereo0D(a1, a3,
                a4, a7, a2, INCHI_PARITY.EVEN));
        return input;
    }


    private JniInchiInput getNSC7414a(String options) throws JniInchiException {

        JniInchiInput input = new JniInchiInput(options);

        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(-1.1292, -0.5292, 0.0, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(-1.1333, -1.5917, 0.0, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(-1.1333, 0.5333, 0.0, "C"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(-1.1375, -2.6542, 0.0, "C"));
        JniInchiAtom a5 = input.addAtom(new JniInchiAtom(0.8375, 0.5625, 0.0, "C"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(0.9917, -2.4667, 0.0, "C"));
        JniInchiAtom a7 = input.addAtom(new JniInchiAtom(2.2417, -0.6542, 0.0, "C"));
        JniInchiAtom a8 = input.addAtom(new JniInchiAtom(4.3000, -0.5000, 0.0, "C"));
        JniInchiAtom a9 = input.addAtom(new JniInchiAtom(5.8583, 0.9667, 0.0, "C"));
        JniInchiAtom a10 = input.addAtom(new JniInchiAtom(6.0167, -1.7500, 0.0, "C"));
        JniInchiAtom a11 = input.addAtom(new JniInchiAtom(6.2042, -3.3417, 0.0, "C"));
        a1.setImplicitH(-1);
        a2.setImplicitH(-1);
        a3.setImplicitH(-1);
        a4.setImplicitH(-1);
        a5.setImplicitH(-1);
        a6.setImplicitH(-1);
        a7.setImplicitH(-1);
        a8.setImplicitH(-1);
        a9.setImplicitH(-1);
        a10.setImplicitH(-1);
        a11.setImplicitH(-1);

        input.addBond(new JniInchiBond(a6, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a7, a5, INCHI_BOND_TYPE.SINGLE, INCHI_BOND_STEREO.SINGLE_1UP));
        input.addBond(new JniInchiBond(a7, a6, INCHI_BOND_TYPE.SINGLE, INCHI_BOND_STEREO.SINGLE_1UP));
        input.addBond(new JniInchiBond(a2, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a8, a7, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a9, a8, INCHI_BOND_TYPE.SINGLE, INCHI_BOND_STEREO.SINGLE_1UP));
        input.addBond(new JniInchiBond(a5, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a8, a10, INCHI_BOND_TYPE.SINGLE, INCHI_BOND_STEREO.SINGLE_1UP));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a11, a10, INCHI_BOND_TYPE.SINGLE));

//        input.addStereo0D(new JniInchiStereo0D(a7, a7, a5, a6, a8, INCHI_STEREOTYPE.TETRAHEDRAL, INCHI_PARITY.EVEN));
//        input.addStereo0D(new JniInchiStereo0D(a8, a8, a7, a9, a10, INCHI_STEREOTYPE.TETRAHEDRAL, INCHI_PARITY.ODD));

        return input;
    }

    /*
     *


  -ClnMol-06180618052D

 11 11  0  0  0  0  0  0  0  0999 V2000
   -1.1292   -0.5292    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0	1
   -1.1333   -1.5917    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0	2
   -1.1333    0.5333    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0	3
   -1.1375   -2.6542    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0	4
    0.8375    0.5625    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0	5
    0.9917   -2.4667    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0	6
    2.2417   -0.6542    0.0000 C   0  0  2  0  0  0  0  0  0  0  0  0	7
    4.3000   -0.5000    0.0000 C   0  0  1  0  0  0  0  0  0  0  0  0	8
    5.8583    0.9667    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0	9
    6.0167   -1.7500    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0	10
    6.2042   -3.3417    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0	11
  6  4  1  0  0  0  0
  1  2  2  0  0  0  0
  7  5  1  1  0  0  0
  7  6  1  1  0  0  0
  2  4  1  0  0  0  0
  8  7  1  0  0  0  0
  9  8  1  1  0  0  0
  5  3  1  0  0  0  0
  8 10  1  1  0  0  0
  1  3  1  0  0  0  0
 11 10  1  0  0  0  0
M  END
>  <ID>
NSC-7414a

     *
     *
     */

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
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiFromChlorine37ByIstopicMassShiftAtom()
            throws Exception {
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
        Assert.assertEquals("InChI=1/C2H2Cl2/c3-1-2-4/h1-2H/b2-1+", outputE
                .getInchi());

        JniInchiInput inputZ = getZ12dichloroethene2D("");
        JniInchiOutput outputZ = JniInchiWrapper.getInchi(inputZ);
        Assert.assertEquals(INCHI_RET.OKAY, outputZ.getReturnStatus());
        Assert.assertEquals("InChI=1/C2H2Cl2/c3-1-2-4/h1-2H/b2-1-", outputZ
                .getInchi());
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
        Assert.assertEquals(
                "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1",
                outputL.getInchi());

        JniInchiInput inputD = getDAlanine3D("");
        JniInchiOutput outputD = JniInchiWrapper.getInchi(inputD);
        Assert.assertEquals(INCHI_RET.OKAY, outputD.getReturnStatus());
        Assert.assertEquals(
                "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m1/s1",
                outputD.getInchi());
    };

    // Test handling of 2D coordinates with bond stereo types

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
        Assert.assertEquals("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)",
                output.getInchi());

        JniInchiInput inputL = getLAlanine2Da("-FixSp3Bug");
        JniInchiOutput outputL = JniInchiWrapper.getInchi(inputL);
        Assert.assertEquals(INCHI_RET.OKAY, outputL.getReturnStatus());
        Assert.assertEquals(
                "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1",
                outputL.getInchi());

        JniInchiInput inputD = getDAlanine2D("-FixSp3Bug");
        JniInchiOutput outputD = JniInchiWrapper.getInchi(inputD);
        Assert.assertEquals(INCHI_RET.OKAY, outputD.getReturnStatus());
        Assert.assertEquals(
                "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m1/s1",
                outputD.getInchi());
    };


    /**
     * Tests InChI generation from L-Alanine molecules, with 2D coordinates
     * and wedge/hatch bonds, with bond drawn in opposite directions.
     *
     * @throws Exception
     */
    @Test
    public void testGetInchiStereoBondDirection() throws Exception {
        JniInchiInput input = getLAlanine2Da("-FixSp3Bug");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(
                "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1",
                output.getInchi());

        JniInchiInput inputL = getLAlanine2Db("-FixSp3Bug");
        JniInchiOutput outputL = JniInchiWrapper.getInchi(inputL);
        Assert.assertEquals(INCHI_RET.OKAY, outputL.getReturnStatus());
        Assert.assertEquals(
                "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1",
                outputL.getInchi());
    }

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
        Assert.assertEquals("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)",
                output.getInchi());

        JniInchiInput inputL = getLAlanine0D("");
        JniInchiOutput outputL = JniInchiWrapper.getInchi(inputL);
        Assert.assertEquals(INCHI_RET.OKAY, outputL.getReturnStatus());
        Assert.assertEquals(
                "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1",
                outputL.getInchi());

        JniInchiInput inputD = getDAlanine0D("");
        JniInchiOutput outputD = JniInchiWrapper.getInchi(inputD);
        Assert.assertEquals(INCHI_RET.OKAY, outputD.getReturnStatus());
        Assert.assertEquals(
                "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m1/s1",
                outputD.getInchi());
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
        Assert
                .assertEquals("InChI=1/C2H2Cl2/c3-1-2-4/h1-2H", output
                        .getInchi());

        JniInchiInput inputE = getE12dichloroethene0D("");
        JniInchiOutput outputE = JniInchiWrapper.getInchi(inputE);
        Assert.assertEquals(INCHI_RET.OKAY, outputE.getReturnStatus());
        Assert.assertEquals("InChI=1/C2H2Cl2/c3-1-2-4/h1-2H/b2-1+", outputE
                .getInchi());

        JniInchiInput inputZ = getZ12dichloroethene0D("");
        JniInchiOutput outputZ = JniInchiWrapper.getInchi(inputZ);
        Assert.assertEquals(INCHI_RET.OKAY, outputZ.getReturnStatus());
        Assert.assertEquals("InChI=1/C2H2Cl2/c3-1-2-4/h1-2H/b2-1-", outputZ
                .getInchi());
    };



    @Test
    public void testGetInchiFromNSC7414a() throws JniInchiException {
          JniInchiInput input = getNSC7414a("/fixedh /srel /fixsp3bug");
          JniInchiOutput output = JniInchiWrapper.getInchi(input);
          Assert.assertEquals(INCHI_RET.WARNING, output.getReturnStatus());
          Assert.assertEquals("Omitted undefined stereo; Ambiguous stereo: center(s)", output.getMessage());
          Assert.assertEquals(
                  "InChI=1/C11H20/c1-3-10(2)11-8-6-4-5-7-9-11/h4-5,10-11H,3,6-9H2,1-2H3",
                  output.getInchi());
    }



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
     *
     * @throws JniInchiException
     */
    @Test
    public void testCheckOptionsString() throws JniInchiException {
        String options = JniInchiWrapper.checkOptions("  -ComPreSS  /SNon");
        String flag = JniInchiWrapper.flagChar;
        Assert.assertEquals(flag + "Compress " + flag + "SNon", options);
    }

    // Test option handling

    /**
     * Tests passing options to inchi.
     *
     * @throws Exception
     */
    // @Test
    public void testGetInchiWithOptions() throws Exception {
        JniInchiInput input = getLAlanine3D("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(
                "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1",
                output.getInchi());

        input = getLAlanine3D("-compress");
        output = JniInchiWrapper.getInchi(input);
        // debug(output);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/C3H7NO2/cABBCC/hB1D2A3,1EF/tB1/m0/s1",
                output.getInchi());

        input = getLAlanine3D("/compress");
        output = JniInchiWrapper.getInchi(input);
        // debug(output);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/C3H7NO2/cABBCC/hB1D2A3,1EF/tB1/m0/s1",
                output.getInchi());

        input = getLAlanine3D("-cOMprEsS");
        output = JniInchiWrapper.getInchi(input);
        // debug(output);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals("InChI=1/C3H7NO2/cABBCC/hB1D2A3,1EF/tB1/m0/s1",
                output.getInchi());
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
        JniInchiOutputStructure output = JniInchiWrapper
                .getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(1, output.getNumAtoms());
        Assert.assertEquals(0, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert
                .assertEquals(
                        "InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(0).getDebugString());
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

        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/Cl/q-1", "-FixedH");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        System.out.println(output.getLog());
        Assert.assertEquals("InChI Atom: Cl [0.0,0.0,0.0] Charge:-1 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE", output.getAtom(0).getDebugString());
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(1, output.getNumAtoms());
        Assert.assertEquals(0, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());

    }

    /**
     * Tests isotopic mass is correctly read from InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetChlorine37AtomFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/Cl/i1+2");
        JniInchiOutputStructure output = JniInchiWrapper
                .getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(1, output.getNumAtoms());
        Assert.assertEquals(0, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert
                .assertEquals(
                        "InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:10002 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(0).getDebugString());
    }

    /**
     * Tests implicit hydrogen count is correctly read from InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetHydrogenChlorideImplicitHFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/ClH/h1H");
        JniInchiOutputStructure output = JniInchiWrapper
                .getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(1, output.getNumAtoms());
        Assert.assertEquals(0, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert
                .assertEquals(
                        "InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(0).getDebugString());
    }

    /**
     * Tests implicit protium count is correctly read from InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetHydrogenChlorideImplicitPFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi(
                "InChI=1/ClH/h1H/i/hH");
        JniInchiOutputStructure output = JniInchiWrapper
                .getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(1, output.getNumAtoms());
        Assert.assertEquals(0, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert
                .assertEquals(
                        "InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:1 D:0 T:0 // Radical: NONE",
                        output.getAtom(0).getDebugString());
    }

    /**
     * Tests implicit deuterium count is correctly read from InChi.
     *
     * @throws Exception
     */
    @Test
    public void testGetHydrogenChlorideImplicitDFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi(
                "InChI=1/ClH/h1H/i/hD");
        JniInchiOutputStructure output = JniInchiWrapper
                .getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(1, output.getNumAtoms());
        Assert.assertEquals(0, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert
                .assertEquals(
                        "InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:1 T:0 // Radical: NONE",
                        output.getAtom(0).getDebugString());
    }

    /**
     * Tests implicit tritium count is correctly read from InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetHydrogenChlorideImplicitTFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi(
                "InChI=1/ClH/h1H/i/hT");
        JniInchiOutputStructure output = JniInchiWrapper
                .getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(1, output.getNumAtoms());
        Assert.assertEquals(0, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert
                .assertEquals(
                        "InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:1 // Radical: NONE",
                        output.getAtom(0).getDebugString());
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

        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/CH3/h1H3");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(1, output.getNumAtoms());
        Assert.assertEquals(0, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert.assertEquals("InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:3 P:0 D:0 T:0 // Radical: DOUBLET", output.getAtom(0).getDebugString());
    }



    // Test bond handling

    /**
     * Tests single bond is correctly read from InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetEthaneFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi(
                "InChI=1/C2H6/c1-2/h1-2H3");
        JniInchiOutputStructure output = JniInchiWrapper
                .getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(2, output.getNumAtoms());
        Assert.assertEquals(1, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:3 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(0).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:3 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(1).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: SINGLE // Stereo: NONE",
                output.getBond(0).getDebugString());
    }

    /**
     * Tests double bond is correctly read from InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetEtheneFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi(
                "InChI=1/C2H4/c1-2/h1-2H2");
        JniInchiOutputStructure output = JniInchiWrapper
                .getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(2, output.getNumAtoms());
        Assert.assertEquals(1, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:2 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(0).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:2 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(1).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: DOUBLE // Stereo: NONE",
                output.getBond(0).getDebugString());
    }

    /**
     * Tests triple bond is correctly read from InChI.
     *
     * @throws Exception
     */
    @Test
    public void testGetEthyneFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi(
                "InChI=1/C2H2/c1-2/h1-2H");
        JniInchiOutputStructure output = JniInchiWrapper
                .getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(2, output.getNumAtoms());
        Assert.assertEquals(1, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(0).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(1).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: TRIPLE // Stereo: NONE",
                output.getBond(0).getDebugString());
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
        JniInchiInputInchi input = new JniInchiInputInchi(
                "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)");
        JniInchiOutputStructure output = JniInchiWrapper
                .getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(6, output.getNumAtoms());
        Assert.assertEquals(5, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:3 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(0).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(1).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(2).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: N [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:2 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(3).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: O [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(4).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: O [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(5).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: SINGLE // Stereo: NONE",
                output.getBond(0).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: SINGLE // Stereo: NONE",
                output.getBond(1).getDebugString());
        Assert.assertEquals("InChI Bond: N-C // Type: SINGLE // Stereo: NONE",
                output.getBond(2).getDebugString());
        Assert.assertEquals("InChI Bond: O-C // Type: DOUBLE // Stereo: NONE",
                output.getBond(3).getDebugString());
        Assert.assertEquals("InChI Bond: O-C // Type: SINGLE // Stereo: NONE",
                output.getBond(4).getDebugString());

        JniInchiInputInchi inputL = new JniInchiInputInchi(
                "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1");
        JniInchiOutputStructure outputL = JniInchiWrapper
                .getStructureFromInchi(inputL);
        Assert.assertEquals(INCHI_RET.OKAY, outputL.getReturnStatus());
        Assert.assertEquals(7, outputL.getNumAtoms());
        Assert.assertEquals(6, outputL.getNumBonds());
        Assert.assertEquals(1, outputL.getNumStereo0D());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:3 P:0 D:0 T:0 // Radical: NONE",
                        outputL.getAtom(0).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputL.getAtom(1).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputL.getAtom(2).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: N [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:2 P:0 D:0 T:0 // Radical: NONE",
                        outputL.getAtom(3).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: O [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputL.getAtom(4).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: O [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE",
                        outputL.getAtom(5).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: SINGLE // Stereo: NONE",
                outputL.getBond(0).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: SINGLE // Stereo: NONE",
                outputL.getBond(1).getDebugString());
        Assert.assertEquals("InChI Bond: N-C // Type: SINGLE // Stereo: NONE",
                outputL.getBond(2).getDebugString());
        Assert.assertEquals("InChI Bond: O-C // Type: DOUBLE // Stereo: NONE",
                outputL.getBond(3).getDebugString());
        Assert.assertEquals("InChI Bond: O-C // Type: SINGLE // Stereo: NONE",
                outputL.getBond(4).getDebugString());
        Assert.assertEquals(
                "InChI Stereo0D: C [H,C,C,N] Type::TETRAHEDRAL // Parity:ODD",
                outputL.getStereo0D(0).getDebugString());

        JniInchiInputInchi inputD = new JniInchiInputInchi(
                "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m1/s1");
        JniInchiOutputStructure outputD = JniInchiWrapper
                .getStructureFromInchi(inputD);
        Assert.assertEquals(INCHI_RET.OKAY, outputD.getReturnStatus());
        Assert.assertEquals(7, outputD.getNumAtoms());
        Assert.assertEquals(6, outputD.getNumBonds());
        Assert.assertEquals(1, outputD.getNumStereo0D());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:3 P:0 D:0 T:0 // Radical: NONE",
                        outputD.getAtom(0).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputD.getAtom(1).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputD.getAtom(2).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: N [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:2 P:0 D:0 T:0 // Radical: NONE",
                        outputD.getAtom(3).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: O [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputD.getAtom(4).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: O [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE",
                        outputD.getAtom(5).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: SINGLE // Stereo: NONE",
                outputD.getBond(0).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: SINGLE // Stereo: NONE",
                outputD.getBond(1).getDebugString());
        Assert.assertEquals("InChI Bond: N-C // Type: SINGLE // Stereo: NONE",
                outputD.getBond(2).getDebugString());
        Assert.assertEquals("InChI Bond: O-C // Type: DOUBLE // Stereo: NONE",
                outputD.getBond(3).getDebugString());
        Assert.assertEquals("InChI Bond: O-C // Type: SINGLE // Stereo: NONE",
                outputD.getBond(4).getDebugString());
        Assert.assertEquals(
                "InChI Stereo0D: C [H,C,C,N] Type::TETRAHEDRAL // Parity:EVEN",
                outputD.getStereo0D(0).getDebugString());
    };

    /**
     * Tests generation of E and Z 1,2-dichloroethene molecules, from InChIs
     * with doublebond stereo parities.
     *
     * @throws Exception
     */
    @Test
    public void testGetEandZ12Dichloroethene0DFromInchi() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi(
                "InChI=1/C2H2Cl2/c3-1-2-4/h1-2H");
        JniInchiOutputStructure output = JniInchiWrapper
                .getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
        Assert.assertEquals(4, output.getNumAtoms());
        Assert.assertEquals(3, output.getNumBonds());
        Assert.assertEquals(0, output.getNumStereo0D());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(0).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:1 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(1).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(2).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        output.getAtom(3).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: DOUBLE // Stereo: NONE",
                output.getBond(0).getDebugString());
        Assert.assertEquals("InChI Bond: Cl-C // Type: SINGLE // Stereo: NONE",
                output.getBond(1).getDebugString());
        Assert.assertEquals("InChI Bond: Cl-C // Type: SINGLE // Stereo: NONE",
                output.getBond(2).getDebugString());

        JniInchiInputInchi inputE = new JniInchiInputInchi(
                "InChI=1/C2H2Cl2/c3-1-2-4/h1-2H/b2-1+");
        JniInchiOutputStructure outputE = JniInchiWrapper
                .getStructureFromInchi(inputE);
        Assert.assertEquals(INCHI_RET.OKAY, outputE.getReturnStatus());
        Assert.assertEquals(6, outputE.getNumAtoms());
        Assert.assertEquals(5, outputE.getNumBonds());
        Assert.assertEquals(1, outputE.getNumStereo0D());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputE.getAtom(0).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputE.getAtom(1).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputE.getAtom(2).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputE.getAtom(3).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: H [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputE.getAtom(4).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: H [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputE.getAtom(5).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: DOUBLE // Stereo: NONE",
                outputE.getBond(0).getDebugString());
        Assert.assertEquals("InChI Bond: Cl-C // Type: SINGLE // Stereo: NONE",
                outputE.getBond(1).getDebugString());
        Assert.assertEquals("InChI Bond: Cl-C // Type: SINGLE // Stereo: NONE",
                outputE.getBond(2).getDebugString());
        Assert.assertEquals(
                "InChI Stereo0D: - [H,C,C,H] Type::DOUBLEBOND // Parity:EVEN",
                outputE.getStereo0D(0).getDebugString());

        JniInchiInputInchi inputZ = new JniInchiInputInchi(
                "InChI=1/C2H2Cl2/c3-1-2-4/h1-2H/b2-1-");
        JniInchiOutputStructure outputZ = JniInchiWrapper
                .getStructureFromInchi(inputZ);
        Assert.assertEquals(INCHI_RET.OKAY, outputZ.getReturnStatus());
        Assert.assertEquals(6, outputZ.getNumAtoms());
        Assert.assertEquals(5, outputZ.getNumBonds());
        Assert.assertEquals(1, outputZ.getNumStereo0D());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputZ.getAtom(0).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: C [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputZ.getAtom(1).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputZ.getAtom(2).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: Cl [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputZ.getAtom(3).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: H [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputZ.getAtom(4).getDebugString());
        Assert
                .assertEquals(
                        "InChI Atom: H [0.0,0.0,0.0] Charge:0 // Iso Mass:0 // Implicit H:0 P:0 D:0 T:0 // Radical: NONE",
                        outputZ.getAtom(5).getDebugString());
        Assert.assertEquals("InChI Bond: C-C // Type: DOUBLE // Stereo: NONE",
                outputZ.getBond(0).getDebugString());
        Assert.assertEquals("InChI Bond: Cl-C // Type: SINGLE // Stereo: NONE",
                outputZ.getBond(1).getDebugString());
        Assert.assertEquals("InChI Bond: Cl-C // Type: SINGLE // Stereo: NONE",
                outputZ.getBond(2).getDebugString());
        Assert.assertEquals(
                "InChI Stereo0D: - [H,C,C,H] Type::DOUBLEBOND // Parity:ODD",
                outputZ.getStereo0D(0).getDebugString());
    };


    /**
     * Tests InchiKey generation. (Example from InChI 1.02-beta release notes).
     * @throws JniInchiException
     */
    @Test
    public void testGetInchiKeyForCafeine() throws JniInchiException {
//    	for (int i = 0; i < 200000; i++) {
        String inchi = "InChI=1/C8H10N4O2/c1-10-4-9-6-5(10)7(13)12(3)8(14)11(6)2/h4H,1-3H3";
        JniInchiOutputKey output = JniInchiWrapper.getInChIKey(inchi);
        Assert.assertEquals(INCHI_KEY.OK, output.getReturnStatus());
        Assert.assertEquals("RYYVLZVUVIJVGH-UHFFFAOYAW", output.getKey());
//    	}
    }

    @Test
    public void testCheckInchiKeyValid() throws JniInchiException {
        String key = "RYYVLZVUVIJVGH-UHFFFAOYAW";
        INCHI_KEY_STATUS status = JniInchiWrapper.checkInChIKey(key);
        Assert.assertEquals(INCHI_KEY_STATUS.VALID, status);
    }

    @Test
    public void testCheckInchiKeyInvalidLength() throws JniInchiException {
        String key1 = "RYYVLZVUVIJVGH-UHFFFAOYA";
        INCHI_KEY_STATUS status1 = JniInchiWrapper.checkInChIKey(key1);
        Assert.assertEquals(INCHI_KEY_STATUS.INVALID_LENGTH, status1);

        String key2 = "RYYVLZVUVIJVGH-UHFFFAOYAWX";
        INCHI_KEY_STATUS status2 = JniInchiWrapper.checkInChIKey(key2);
        Assert.assertEquals(INCHI_KEY_STATUS.INVALID_LENGTH, status2);
    }

    @Test
    public void testCheckInchiKeyInvalidLayout() throws JniInchiException {
        String key = "RYYVLZVUVIJVGHXUHFFFAOYAW";
        INCHI_KEY_STATUS status = JniInchiWrapper.checkInChIKey(key);
        Assert.assertEquals(INCHI_KEY_STATUS.INVALID_LAYOUT, status);
    }

    @Test
    public void testCheckInchiKeyInvalidChecksum() throws JniInchiException {
        String key = "RYYVLZVUVIJVGH-UHFFFAOYAA";
        INCHI_KEY_STATUS status = JniInchiWrapper.checkInChIKey(key);
        Assert.assertEquals(INCHI_KEY_STATUS.INVALID_CHECKSUM, status);
    }

    /* Option doesn't work yet
    @Test
    @Ignore
    public void testGenerateInchiKeyViaOptions() throws JniInchiException {
        JniInchiInput input = getLAlanine3D("-key");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
//        Assert.assertEquals(null, output.getInchi());
//        Assert.assertEquals(null, output.getAuxInfo());
//        Assert.assertEquals(null, output.getMessage());
//        Assert.assertEquals(null, output.getLog());
    }
    */


    /**
     * Tests thread safety - starts ten threads, and sets them generating InChIs
     * for randomly picked elements. Checks generated InChIs are as expected.
     *
     */
    @Test
    public void multithreading() {

        // Start threads
        int nthreads = 5;
        TestThread[] threads = new TestThread[nthreads];
        for (int i = 0; i < nthreads; i++) {
            threads[i] = new TestThread(i);
            threads[i].start();
            Thread.yield();
        }

        // Wait for threads to get going
        try {
            Thread.sleep(500);
        } catch (InterruptedException ie) {
            Assert.fail("Interrupted");
        }

        boolean allRunning = true;

        // Stop threads
        for (int i = 0; i < nthreads; i++) {
            if (threads[i].runCount < 1) {
                allRunning = false;
            }
            threads[i].finish();
            Thread.yield();
        }

        Assert.assertTrue("All threads running", allRunning);

        // Wait for threads to stop
        long tstop = System.currentTimeMillis() + 30000;
        try {
            for (int i = 0; i < nthreads; i++) {
                long t0 = System.currentTimeMillis();
                threads[i].join(Math.max(1,tstop-t0));
            }
        } catch (InterruptedException ie) {
            Assert.fail("Interrupted");
        }

        // Check threads have finished
        boolean allFinished = true;
        for (int i = 0; i < nthreads; i++) {
            if (!threads[i].isDone()) {
                allFinished = false;
                break;
            }
        }
        Assert.assertTrue("All finished", allFinished);

        int failureCount = 0;
        for (int i = 0; i < nthreads; i++) {
            failureCount += threads[i].failCount;
        }

        Assert.assertEquals("Fail count", 0, failureCount);
    }


    @Test
    public void testTooManyAtoms() throws JniInchiException {
        JniInchiInput input = new JniInchiInput();
        for (int i = 0; i < 2000; i++) {
            input.addAtom(new JniInchiAtom(0, 0, 0, "C"));
        }
        try {
            JniInchiWrapper.getInchi(input);
            Assert.fail("too many atoms");
        } catch (IllegalArgumentException e) {
            ; // pass
        }
    }


    @Test
    public void testStructureToInchiBug2085031a() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi(
                "InChI=1/C24H33N3O5/c1-23(2,3)26-21(29)20(17-11-8-7-9-12-17)27(16-18-13-10-14-31-18)19(28)15-25-22(30)32-24(4,5)6/h7-14,20H,15-16H2,1-6H3,(H,25,30)(H,26,29)");
        JniInchiOutputStructure output = JniInchiWrapper
                .getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
    }

    @Test
    public void testStructureToInchiBug2085031b() throws Exception {
        JniInchiInputInchi input = new JniInchiInputInchi(
                "InChI=1/C24H33N3O5/c1-23(2,3)26-21(29)20(17-11-8-7-9-12-17)27(16-18-13-10-14-31-18)19(28)15-25-22(30)32-24(4,5)6/h7-14,20H,15-16H2,1-6H3,(H,25,30)(H,26,29) ");
        JniInchiOutputStructure output = JniInchiWrapper
                .getStructureFromInchi(input);
        Assert.assertEquals(INCHI_RET.EOF, output.getReturnStatus());
    }




    private class TestThread extends Thread {

        private String[] ELS = { "H", "He", "Li", "Be", "B", "C", "N", "O",
                "F", "Ne", "Na", "Mg", "Al", "Si", "P", "S", "Cl", "Ar" };

        private volatile boolean stop = false;
        private volatile boolean done = false;

        public volatile int failCount = 0;
        public volatile int runCount = 0;

        public Exception ex;

        public final int threadIndex;

        public TestThread(final int i) {
            this.threadIndex = i;
        }

        public void run() {
            System.err.println("Thread "+threadIndex+" starting");
            Random rand = new Random();
            while (!stop) {
                yield();
                runCount++;
                JniInchiInput input = new JniInchiInput();
                String element = ELS[rand.nextInt(ELS.length)];
                input.addAtom(new JniInchiAtom(0, 0, 0, element));
                input.getAtom(0).setImplicitH(0);
                try {
                    JniInchiOutput output = JniInchiWrapper.getInchi(input);
                    if (INCHI_RET.OKAY != output.getReturnStatus()) {
                        failCount++;
                    } else if (!("InChI=1/" + element)
                            .equals(output.getInchi())) {
                        failCount++;
                    }
#                    System.err.print("("+threadIndex+")");
#                    System.err.flush();
                } catch (Exception e) {
                    failCount++;
                    ex = e;
                    System.err.println("Thread "+threadIndex+ " error: " + e.getMessage());
                    break;
                }
                yield();
            }
            done = true;
            System.err.println("Thread "+threadIndex+" stopping");
        }

        public void finish() {
            this.stop = true;
        }

        public boolean isDone() {
            return done;
        }

    }
}
