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
 * Simple test class, for debugging purposes.
 * @author sea36
 */
public class Main {

    /**
     * Provide test structure.
     * @return
     */
    public static JniInchiStructure getTestMolecule() {
        JniInchiStructure struct = new JniInchiStructure();
        JniInchiAtom a1 = struct.addAtom(new JniInchiAtom(264.0, 968.0, 0.0, "C"));
        JniInchiAtom a2 = struct.addAtom(new JniInchiAtom(295.0, 985.0, 0.0, "C"));
        JniInchiAtom a3 = struct.addAtom(new JniInchiAtom(233.0, 986.0, 0.0, "N"));
        JniInchiAtom a4 = struct.addAtom(new JniInchiAtom(264.0, 932.0, 0.0, "C"));
        JniInchiAtom a5 = struct.addAtom(new JniInchiAtom(326.0, 967.0, 0.0, "O"));
        JniInchiAtom a6 = struct.addAtom(new JniInchiAtom(295.0, 1021.0, 0.0, "O"));
        a1.setImplicitH(1);
        a3.setImplicitH(2);
        a4.setImplicitH(3);
        a5.setImplicitH(1);
        struct.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        struct.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE)).setStereoDefinition(INCHI_BOND_STEREO.SINGLE_1DOWN);
        struct.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
        struct.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
        struct.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));

        return struct;
    }

    public static void runChecks() throws JniInchiException {
        System.out.println("Loading native code");
        System.out.println();
        JniInchiNativeCodeLoader.setDebug(true);
        JniInchiNativeCodeLoader.getLoader();

        try {
            System.out.println();

            System.out.println("Running checks");
            System.out.println();

            System.out.println("Generating InChI from structure");
            JniInchiStructure mol = getTestMolecule();
            JniInchiOutput out1 = JniInchiWrapper.getInchi(new JniInchiInput(mol));
            if ("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)".equals(out1.getInchi())) {
                System.out.println(" - OKAY");
            } else {
                System.out.println(" - ERROR");
            }

            System.out.println("Generating structure from InChI");
            JniInchiOutputStructure out2 = JniInchiWrapper.getStructureFromInchi(new JniInchiInputInchi("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)"));
            if (out2.getNumAtoms() == 6 && out2.getNumBonds() == 5) {
                System.out.println(" - OKAY");
            } else {
                System.out.println(" - ERROR");
            }

            System.out.println("Converting structure back to InChI");
            JniInchiOutput out3 = JniInchiWrapper.getInchi(new JniInchiInput(out2));
            if ("InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)".equals(out3.getInchi())) {
                System.out.println(" - OKAY");
            } else {
                System.out.println(" - ERROR");
            }
        } catch (LoadNativeLibraryException lnle) {
            System.out.println(" - ERROR");
        }

        System.out.println();
        System.out.println("Checks done.");
        System.out.println();
    }

    public static void main(final String[] args) throws Exception {
        // Output header message
        System.out.println();
        System.out.println("** JniInchi debugger **");
        System.out.println();

        runChecks();
    }
}
