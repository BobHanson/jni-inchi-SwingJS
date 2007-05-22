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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.TimeoutException;

/**
 * <p>JNI Wrapper for International Chemical Identifier (InChI) C++ library.
 *
 * <p>This class is not intended to be used directly, but should be accessed
 * through subclasses that read data formats and load them into the InChI
 * data structures.
 *
 * <p>Subclasses should load data through the addAtom, addBond and addParity
 * methods. Once the molecule is fully loaded then the generateInchi method
 * should be called. Ideally this should all take place within the subclass's
 * constructor. The public get methods will all return null until this has
 * happened.
 *
 * <p>See <tt>inchi_api.h</tt>.
 *
 * @author Sam Adams
 */
public class JniInchiWrapper {

    /**
     * Size of atom neighbors (bonds) array (value from InChI library).
     */
    private static final int MAXVAL = 20;

    /**
     * Maximum time to wait for a lock (in seconds).
     */
    private static final int MAX_LOCK_TIMEOUT = 15;

    private static final int WINDOWS = 1;
    private static final int LINUX = 2;

    private static JniInchiWrapper inchiWrapper;

        /**
     * Flag indicating windows or linux.
     */
    private static final int PLATFORM =
        System.getProperty("os.name", "").toLowerCase().startsWith("windows") ? WINDOWS : LINUX;

    /**
     * Switch character for passing options. / in windows, - on other systems.
     */
    protected static final String flagChar = PLATFORM == WINDOWS ? "/" : "-";

    /**
     * Records whether native library has been loaded by system.
     */
    private static boolean libraryLoaded = false;

    /**
     * Loads native library.
     * @throws JniInchiException Library failed to load
     */
    public static void loadLibrary() throws LoadNativeLibraryException {
        if (!libraryLoaded) {
            JniInchiNativeCodeLoader.getLoader();
        }
    }

    private static synchronized JniInchiWrapper getWrapper() throws LoadNativeLibraryException {
        if (inchiWrapper == null) {
            inchiWrapper = new JniInchiWrapper();
        }
        return inchiWrapper;
    }

    /**
     * Constructor
     */
    private JniInchiWrapper() throws LoadNativeLibraryException {
        loadLibrary();
    }


    /**
     * Checks and canonicalises options.
     *
     * @param ops  List of INCHI_OPTION
     */
    protected static String checkOptions(List ops) throws JniInchiException {
        StringBuffer sbOptions = new StringBuffer();

        for (int i = 0; i < ops.size(); i++) {
            Object op = ops.get(i);
            if (op instanceof INCHI_OPTION) {
                sbOptions.append(flagChar + ((INCHI_OPTION) op).getName() + " ");
            } else {
                throw new JniInchiException("Unrecognised InChI option");
            }
        }

        return sbOptions.toString();
    }

    /**
     * Checks and canonicalises options.
     *
     * @param ops          Space delimited string of options to pass to InChI library.
     *                     Each option may optionally be preceded by a command line
     *                     switch (/ or -).
     */
    protected static String checkOptions(final String ops) throws JniInchiException {
        Map optionMap = INCHI_OPTION.getLowercaseMap();

        StringBuffer sbOptions = new StringBuffer();

        StringTokenizer tok = new StringTokenizer(ops);
        while (tok.hasMoreTokens()) {
            String op = tok.nextToken();

            if (op.startsWith("-") || op.startsWith("/")) {
                op = op.substring(1);
            }

            String lcop = op.toLowerCase();
            if (optionMap.keySet().contains(lcop)) {
                sbOptions.append(flagChar + optionMap.get(lcop) + " ");
            } else {
                throw new JniInchiException("Unrecognised InChI option");
            }
        }

        return sbOptions.toString();
    }


    /**
     * Generates InChI string for a chemical structure.
     * @param input
     * @return
     * @throws JniInchiException
     */
    @SuppressWarnings("unchecked")
    public static JniInchiOutput getInchi(JniInchiInput input) throws JniInchiException {
        JniInchiWrapper wrapper = getWrapper();
        try {
            wrapper.getLock();
        } catch (TimeoutException ex) {
            throw new JniInchiException(ex);
        }

        try {

            // Create arrays
            int nat = input.getNumAtoms();
            int nst = input.getNumStereo0D();

            String options = input.getOptions();
            if (options.length() == 0) {
                options = " ";
            }
            wrapper.LibInchiStartInput(nat, nst, options);

            // Load atom data
            Map atomIndxMap = new HashMap();
            for (int i = 0; i < nat; i++) {
                JniInchiAtom atom = input.getAtom(i);
                atomIndxMap.put(atom, new Integer(i));
                if (!wrapper.LibInchiSetAtom(i, atom.getX(), atom.getY(), atom.getZ(), atom.getElementType(),
                        atom.getIsotopicMass(), atom.getImplicitH(), atom.getImplicitProtium(),
                        atom.getImplicitDeuterium(), atom.getImplicitTritium(), atom.getRadical().getIndx(),
                        atom.getCharge())) {

                    wrapper.LibInchiFreeInputMem();
                    throw new JniInchiException("JNI: Failed to set atoms");
                }
            }

            int[]   atomNumNeighbors = new int[nat];
            int[][] atomNeighbors = new int[nat][MAXVAL];
            int[][] atomBondTypes = new int[nat][MAXVAL];
            int[][] atomBondStereo = new int[nat][MAXVAL];


            // Load bond data (note, bonds only need to be recorded in one atom's
            // neighbor list, not both
            for (int i = 0; i < input.getNumBonds(); i++) {
                JniInchiBond bond = input.getBond(i);
                int atOi = ((Integer) atomIndxMap.get(bond.getOriginAtom())).intValue();
                int atTi = ((Integer) atomIndxMap.get(bond.getTargetAtom())).intValue();

                int bondNo = atomNumNeighbors[atOi];
                atomNeighbors[atOi][bondNo] = atTi;
                atomBondTypes[atOi][bondNo] = bond.getBondType().getIndx();
                atomBondStereo[atOi][bondNo] = bond.getBondStereo().getIndx();

                atomNumNeighbors[atOi]++;
            }

            for (int i = 0; i < nat; i++) {
                if (!wrapper.LibInchiSetAtomBonds(i, atomNumNeighbors[i],
                        atomNeighbors[i], atomBondTypes[i], atomBondStereo[i])) {
                    wrapper.LibInchiFreeInputMem();
                    throw new JniInchiException("JNI: Failed to set atom neighbours");
                }
            }


            // Load 0D Stereo parities
            for (int i = 0; i < nst; i++) {
                JniInchiStereo0D stereo = (JniInchiStereo0D) input.getStereo0D(i);
                int parity = (int) (stereo.getParity().getIndx()
                        | (stereo.getDisconnectedParity().getIndx() << 3));

                int atCi = (stereo.getStereoType() == INCHI_STEREOTYPE.DOUBLEBOND ?
                        JniInchiStereo0D.NO_ATOM
                        : ((Integer) atomIndxMap.get(stereo.getCentralAtom())).intValue());
                JniInchiAtom[] neighbors = stereo.getNeighbors();
                int at0i = ((Integer) atomIndxMap.get(neighbors[0])).intValue();
                int at1i = ((Integer) atomIndxMap.get(neighbors[1])).intValue();
                int at2i = ((Integer) atomIndxMap.get(neighbors[2])).intValue();
                int at3i = ((Integer) atomIndxMap.get(neighbors[3])).intValue();

                if (!wrapper.LibInchiSetStereo(i, atCi, at0i, at1i, at2i, at3i,
                        stereo.getStereoType().getIndx(), parity)) {
                    wrapper.LibInchiFreeInputMem();
                    throw new JniInchiException("JNI: Failed to set stereos");
                }
            }

            // Call inchi library
            int retVal = wrapper.LibInchiGenerateInchi();

            // Fetch results
            JniInchiOutput output = new JniInchiOutput();

            List retCodes = INCHI_RET.getList();
            for (int i = 0; i < retCodes.size(); i++) {
                INCHI_RET ret = (INCHI_RET) retCodes.get(i);
                if (retVal == ret.getIndx()) {
                    output.setRetStatus(ret);
                    break;
                }
            }

            output.setInchi(wrapper.LibInchiGetInchi());
            output.setAuxInfo(wrapper.LibInchiGetAuxInfo());
            output.setMessage(wrapper.LibInchiGetMessage());
            output.setLog(wrapper.LibInchiGetLog());

            wrapper.LibInchiFreeInputMem();
            wrapper.LibInchiFreeOutputMem();

            return output;
        } finally {
            wrapper.releaseLock();
        }
    }


    public static JniInchiOutput getInchiFromInchi(JniInchiInputInchi input) throws JniInchiException {
        JniInchiWrapper wrapper = getWrapper();
        try {
            wrapper.getLock();
        } catch (TimeoutException ex) {
            throw new JniInchiException(ex);
        }

        try {

            String options = input.getOptions();
            if (options.length() == 0) {
                options = " ";
            }
            String inchiString = input.getInchi();

            // Call inchi library
            int retVal = wrapper.LibInchiGenerateInchiFromInchi(inchiString, options);

            // Fetch results
            JniInchiOutput output = new JniInchiOutput();

            List retCodes = INCHI_RET.getList();
            for (int i = 0; i < retCodes.size(); i++) {
                INCHI_RET ret = (INCHI_RET) retCodes.get(i);
                if (retVal == ret.getIndx()) {
                    output.setRetStatus(ret);
                    break;
                }
            }

            output.setInchi(wrapper.LibInchiGetInchi());
            output.setAuxInfo(wrapper.LibInchiGetAuxInfo());
            output.setMessage(wrapper.LibInchiGetMessage());
            output.setLog(wrapper.LibInchiGetLog());

            wrapper.LibInchiFreeInputMem();
            wrapper.LibInchiFreeOutputMem();

            return output;

        } finally {
            wrapper.releaseLock();
        }

    }

    /**
     * Generated 0D structure from an InChI string.
     * @param input
     * @return
     * @throws JniInchiException
     */
    public static JniInchiOutputStructure getStructureFromInchi(JniInchiInputInchi input) throws JniInchiException {
        JniInchiWrapper wrapper = getWrapper();
        try {
            wrapper.getLock();
        } catch (TimeoutException ex) {
            throw new JniInchiException(ex);
        }

        try {

            String options = input.getOptions();
            if (options.length() == 0) {
                options = " ";
            }
            String inchiString = input.getInchi();
            int retVal = wrapper.LibInchiGetStruct(inchiString, options);

            JniInchiOutputStructure output = new JniInchiOutputStructure();

            List retCodes = INCHI_RET.getList();
            for (int i = 0; i < retCodes.size(); i++) {
                INCHI_RET ret = (INCHI_RET) retCodes.get(i);
                if (retVal == ret.getIndx()) {
                    output.setRetStatus(ret);
                    break;
                }
            }

            // Get warning flags.
            long[][] flags = new long[2][2];
            flags[0][0] = wrapper.LibInchiGetStructWarningFlags00();
            flags[0][1] = wrapper.LibInchiGetStructWarningFlags01();
            flags[1][0] = wrapper.LibInchiGetStructWarningFlags10();
            flags[1][1] = wrapper.LibInchiGetStructWarningFlags11();
            output.setWarningFlags(flags);

            output.setMessage(wrapper.LibInchiGetMessage());
            output.setLog(wrapper.LibInchiGetLog());

            // Get structural data
            int numAtoms = wrapper.LibInchiGetNumAtoms();
            int numStereo = wrapper.LibInchiGetNumStereo();

            int[][] bondTypes = new int[numAtoms][numAtoms];
            int[][] bondStereos = new int[numAtoms][numAtoms];

            // Generate atoms
            for (int i = 0; i < numAtoms; i++) {
                String el = wrapper.LibInchiGetAtomElement(i);
                double x = wrapper.LibInchiGetAtomX(i);
                double y = wrapper.LibInchiGetAtomY(i);
                double z = wrapper.LibInchiGetAtomZ(i);

                JniInchiAtom atom = new JniInchiAtom(x, y, z, el);

                atom.setImplicitH(wrapper.LibInchiGetAtomImplicitH(i));
                atom.setImplicitProtium(wrapper.LibInchiGetAtomImplicitP(i));
                atom.setImplicitDeuterium(wrapper.LibInchiGetAtomImplicitD(i));
                atom.setImplicitTritium(wrapper.LibInchiGetAtomImplicitT(i));

                atom.setCharge(wrapper.LibInchiGetAtomCharge(i));
                atom.setIsotopicMass(wrapper.LibInchiGetAtomIsotopicMass(i));

                int radical = wrapper.LibInchiGetAtomRadical(i);
                if (radical == 0) {
                    atom.setRadical(INCHI_RADICAL.NONE);
                } else if (radical == 1) {
                    atom.setRadical(INCHI_RADICAL.SINGLET);
                } else if (radical == 2) {
                    atom.setRadical(INCHI_RADICAL.DOUBLET);
                } else if (radical == 3) {
                    atom.setRadical(INCHI_RADICAL.TRIPLET);
                } else {
                    throw new JniInchiException("Unknown radical state: " + radical);
                }

                output.addAtom(atom);
                //atom.debug();

                int numBonds = wrapper.LibInchiGetAtomNumBonds(i);
                for (int j = 0; j < numBonds; j++) {
                    int neighbour = wrapper.LibInchiGetAtomNeighbour(i, j);
                    int order = wrapper.LibInchiGetAtomBondType(i, j);
                    int stereo = wrapper.LibInchiGetAtomBondStereo(i, j);

                    bondTypes[i][neighbour] = order;
                    bondStereos[i][neighbour] = stereo;
                }
            }

            // Generate bonds
            for (int i = 0; i < numAtoms; i++) {
                for (int j = 0; j < i; j++) {
                    int bo0 = bondTypes[i][j];
                    int bo1 = bondTypes[j][i];
                    if (bo0 != bo1) {
                        throw new JniInchiException("Bond order mismatch: " + i + "-" + j);
                    }
                    if (bo0 > 0) {
                        INCHI_BOND_TYPE type = null;
                        if (bo0 == 1) {
                            type = INCHI_BOND_TYPE.SINGLE;
                        } else if (bo0 == 2) {
                            type = INCHI_BOND_TYPE.DOUBLE;
                        } else if (bo0 == 3) {
                            type = INCHI_BOND_TYPE.TRIPLE;
                        } else if (bo0 == 4) {
                            type = INCHI_BOND_TYPE.ALTERN;
                        } else {
                            throw new JniInchiException("Unknown bond type: " + bo0);
                        }

                        int bs0 = bondStereos[i][j];
                        int bs1 = bondStereos[j][i];

                        if (bs0 != bs1) {
                            throw new JniInchiException("Bond stereo mismatch: " + i + "-" + j);
                        }

                        INCHI_BOND_STEREO stereo = null;
                        if (bs0 == 0) {
                            stereo = INCHI_BOND_STEREO.NONE;
                        } else if (bs0 == 1) {
                            stereo = INCHI_BOND_STEREO.SINGLE_1UP;
                        } else if (bs0 == 4) {
                            stereo = INCHI_BOND_STEREO.SINGLE_1EITHER;
                        } else if (bs0 == 6) {
                            stereo = INCHI_BOND_STEREO.SINGLE_1DOWN;
                        } else if (bs0 == 3) {
                            stereo = INCHI_BOND_STEREO.DOUBLE_EITHER;
                        } else  {
                            throw new JniInchiException("Unknown bond stereo: " + bs0);
                        }

                        JniInchiBond bond = new JniInchiBond(output.getAtom(i), output.getAtom(j), type, stereo);

                        output.addBond(bond);
                        //bond.debug();
                    }
                }
            }

            // Generate stereo parities
            for (int i = 0; i < numStereo; i++) {
                int centralAt = wrapper.LibInchiGetStereoCentralAtom(i);
                int at0 = wrapper.LibInchiGetStereoNeighbourAtom(i, 0);
                int at1 = wrapper.LibInchiGetStereoNeighbourAtom(i, 1);
                int at2 = wrapper.LibInchiGetStereoNeighbourAtom(i, 2);
                int at3 = wrapper.LibInchiGetStereoNeighbourAtom(i, 3);

                int type = wrapper.LibInchiGetStereoType(i);
                int parity = wrapper.LibInchiGetStereoParity(i);

                INCHI_STEREOTYPE stereoType;
                if (type == 0) {
                    stereoType = INCHI_STEREOTYPE.NONE;
                } else if (type == 1) {
                    stereoType = INCHI_STEREOTYPE.DOUBLEBOND;
                } else if (type == 2) {
                    stereoType = INCHI_STEREOTYPE.TETRAHEDRAL;
                } else if (type == 3) {
                    stereoType = INCHI_STEREOTYPE.ALLENE;
                } else {
                    throw new JniInchiException("Unknown stereo0D type: " + type);
                }

                INCHI_PARITY stereoParity;
                if (parity == 0) {
                    stereoParity = INCHI_PARITY.NONE;
                } else if (parity == 1) {
                    stereoParity = INCHI_PARITY.ODD;
                } else if (parity == 2) {
                    stereoParity = INCHI_PARITY.EVEN;
                } else if (parity == 3) {
                    stereoParity = INCHI_PARITY.UNKNOWN;
                } else if (parity == 4) {
                    stereoParity = INCHI_PARITY.UNDEFINED;
                } else {
                    throw new JniInchiException("Unknown stereo0D parity: " + parity);
                }

                JniInchiStereo0D stereo = new JniInchiStereo0D(
                        stereoType == INCHI_STEREOTYPE.DOUBLEBOND ? null : output.getAtom(centralAt),
                        output.getAtom(at0), output.getAtom(at1), output.getAtom(at2),
                        output.getAtom(at3), stereoType, stereoParity);

                output.addStereo0D(stereo);
                //stereo.debug();
            }

            wrapper.LibInchiFreeStructMem();

            return output;

        } finally {
            wrapper.releaseLock();
        }
    }


    private boolean locked = false;

    private synchronized void getLock() throws TimeoutException {
        long timeout = System.currentTimeMillis() + 1000 * MAX_LOCK_TIMEOUT;
        while (locked) {
            if (timeout < System.currentTimeMillis()) {
                throw new TimeoutException("Unable to get lock");
            }
        }
        locked = true;
    }

    private void releaseLock() {
        locked = false;
    }



    /**
     * Start new InChI library data record.  See <tt>inchi_api.h</tt> for details.
     *
     * @param numAtoms        Number of atoms in molecule.
     * @param numStereo        Number of stereo parities defined.
     * @param options        Options string.
     */
    private native void LibInchiStartInput(final int numAtoms, final int numStereo,
            final String options);

    /**
     * Add atom definition.  See <tt>inchi_api.h</tt> for details.
     *
     * @param indx        Atom number
     * @param x            x-coordinate
     * @param y            y-coordinate
     * @param z            z-coordinate
     * @param elname    Element chemical symbol
     * @param isoMass    Isotopic mass
     * @param numH        Number of implicit hydrogens
     * @param numP        Number of implicit 1H
     * @param numD        Number of implicit 2H
     * @param numT        Number of implicit 3H
     * @param radical    Radical definition
     * @param charge    Charge on atom
     */
    private native boolean LibInchiSetAtom(final int indx, final double x, final double y, final double z,
            final String elname, final int isoMass, final int numH, final int numP, final int numD,
            final int numT, final int radical, final int charge);

    /**
     * Set atom neighbours.  See <tt>inchi_api.h</tt> for details.
     *
     * @param indx        Atom number
     * @param nBonds    Number of bonds
     * @param neighbors Array of other atom in each bond
     * @param type        Array of type of each bond
     * @param stereo    Array of stereo definition for each bond
     */
    private native boolean LibInchiSetAtomBonds(final int indx, final int nBonds,
            final int[] neighbors, final int[] type, final int[] stereo);

    /**
     * Add stereo parity definition.  See <tt>inchi_api.h</tt> for details.
     *
     * @param indx        Parity Number
     * @param atC        Central atom
     * @param at0        Atom 0
     * @param at1        Atom 1
     * @param at2        Atom 2
     * @param at3        Atom 3
     * @param type        Stereo parity type
     * @param parity    Parity
     */
    private native boolean LibInchiSetStereo(final int indx, final int atC, final int at0, final int at1,
            final int at2, final int at3, final int type, final int parity);

    /**
     * Generate InChI from loaded data.  See <tt>inchi_api.h</tt> for details.
     * @return    Return status
     */
    private native int LibInchiGenerateInchi();

    /**
     * Generates InChI from InChI string.
     * @param inchi     InChI string
     * @param options   Options
     * @return      Return status.
     */
    private native int LibInchiGenerateInchiFromInchi(final String inchi, final String options);;

    /**
     * Fetches InChI string from InChI library.
     */
    private native String LibInchiGetInchi();

        /**
     * Fetches AuxInfo string from InChI library.
     */
    private native String LibInchiGetAuxInfo();

    /**
     * Fetches Message string from InChI library.
     */
    private native String LibInchiGetMessage();

    /**
     * Fetches Log string from InChI library.
     */
    private native String LibInchiGetLog();

    /**
     * Frees memory used by InChI library.  Must be called once InChI has
     * been generated and all data fetched.
     */
    private native void LibInchiFreeInputMem();

    /**
     * Frees memory used by InChI library.  Must be called once InChI has
     * been generated and all data fetched.
     */
    private native void LibInchiFreeOutputMem();


    /**
     * Generates structure from InChI string.
     * @param inchi        InChI string
     * @param options    Options
     * @return        Return status.
     */
    private native int LibInchiGetStruct(final String inchi, final String options);

    private native long LibInchiGetStructWarningFlags00();
    private native long LibInchiGetStructWarningFlags01();
    private native long LibInchiGetStructWarningFlags10();
    private native long LibInchiGetStructWarningFlags11();

    /**
     * Frees memory used by InChI library.  Must be called once structure has
     * been generated and all data fetched.
     */
    private native void LibInchiFreeStructMem();

    /**
     * Fetches number of atoms in InChI structure.
     */
    private native int LibInchiGetNumAtoms();

    /**
     * Fetches number of stereo parities in InChI structure.
     */
    private native int LibInchiGetNumStereo();

    /**
     * Fetches X coordinate of atom.
     * @param atIndx    Atom index number
     */
    private native double LibInchiGetAtomX(final int atIndx);

    /**
     * Fetches Y coordinate of atom.
     * @param atIndx    Atom index number
     */
    private native double LibInchiGetAtomY(final int atIndx);

    /**
     * Fetches Z coordinate of atom.
     * @param atIndx    Atom index number
     */
    private native double LibInchiGetAtomZ(final int atIndx);

    /**
     * Fetches chemical element of atom.
     * @param atIndx    Atom index number
     */
    private native String LibInchiGetAtomElement(final int atIndx);

    /**
     * Fetches isotopic mass/isotopic mass shift of atom.
     * @param atIndx    Atom index number
     */
    private native int LibInchiGetAtomIsotopicMass(final int atIndx);

    /**
     * Fetches radical status of atom.
     * @param atIndx    Atom index number
     */
    private native int LibInchiGetAtomRadical(final int atIndx);

    /**
     * Fetches charge on atom.
     * @param atIndx    Atom index number
     */
    private native int LibInchiGetAtomCharge(final int atIndx);

    /**
     * Fetches number of implicit hydrogens on atom.
     * @param atIndx    Atom index number
     */
    private native int LibInchiGetAtomImplicitH(final int atIndx);

    /**
     * Fetches number of implicit protium (1H) on atom.
     * @param atIndx    Atom index number
     */
    private native int LibInchiGetAtomImplicitP(final int atIndx);

    /**
     * Fetches number of implicit deuterium (2H) on atom.
     * @param atIndx    Atom index number
     */
    private native int LibInchiGetAtomImplicitD(final int atIndx);

    /**
     * Fetches number of implicit tritum (3H) on atom.
     * @param atIndx    Atom index number
     */
    private native int LibInchiGetAtomImplicitT(final int atIndx);

    /**
     * Fetches number of bonds on atom.
     * @param atIndx    Atom index number
     */
    private native int LibInchiGetAtomNumBonds(final int atIndx);

    /**
     * Fetches index of neighbouring atom.
     * @param atIndx    Atom index number
     * @param bondIndx    Bond index number
     */
    private native int LibInchiGetAtomNeighbour(final int atIndx, final int bondIndx);

    /**
     * Fetches bond type (order).
     * @param atIndx    Atom index number
     * @param bondIndx    Bond index number
     */
    private native int LibInchiGetAtomBondType(final int atIndx, final int bondIndx);

    /**
     * Fetches bond stereochemistry.
     * @param atIndx    Atom index number
     * @param bondIndx    Bond index number
     */
    private native int LibInchiGetAtomBondStereo(final int atIndx, final int bondIndx);


    private native int LibInchiGetStereoCentralAtom(final int stIndex);

    private native int LibInchiGetStereoNeighbourAtom(final int stIndex, final int atIndx);

    private native int LibInchiGetStereoType(final int stIndex);

    private native int LibInchiGetStereoParity(final int stIndex);

    protected native static int LibInchiGetVersionMajor();

    protected native static int LibInchiGetVersionMinor();

}
