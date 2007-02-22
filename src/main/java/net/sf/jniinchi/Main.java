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

    public static void displayHelp() {
        System.out.println("Usage: java -jar JniInchi.jar OPTION");
        System.out.println("   or: java net.sf.jniinchi.Main OPTION");
        System.out.println("Where OPTION is one of:");
        System.out.println("    -help       Show this message.");
        System.out.println("    -check      Perform simple test.");
        System.out.println("    -debug      Show debug information.");
        System.out.println();
        System.out.println("If you are running JNI InChI from a jar file, then either -check or -debug");
        System.out.println("will extract the required native library files, as necessary.");
        System.out.println();
    }

    public static void check() throws JniInchiException {
        System.out.println("Loading native code");
        JniInchiNativeCodeLoader loader = JniInchiNativeCodeLoader.getLoader();
        try {
            loader.load();
            System.out.println(" - OKAY");

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
            if (out2.atomList.size() == 6 && out2.bondList.size() == 5) {
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

    public static void debug() throws LoadNativeLibraryException {
        JniInchiNativeCodeLoader.setDebug(true);
        JniInchiNativeCodeLoader loader = JniInchiNativeCodeLoader.getLoader();
        loader.load();
    }

    public static void main(String[] args) throws Exception {
        // Output header message
        System.out.println();
        System.out.println("** JniInchi debugger **");
        System.out.println();

        // Parse option
        if (args.length == 1) {
            if ("-help".equals(args[0])) {
                displayHelp();
                return;
            } else if ("-check".equals(args[0])) {
                check();
                return;
            } else if ("-debug".equals(args[0])) {
                debug();
                return;
            }
        }

        // No option/option unknown - print error message
        System.out.println("Unrecognised input. For list of options, use -help.");
        System.out.println();
    }
}
