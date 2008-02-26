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
        	return wrapper.getINCHI(input);
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
            int ret = wrapper.LibInchiGenerateInchiFromInchi(inchiString, options);

            INCHI_RET retStatus = INCHI_RET.getValue(ret);
            if (retStatus == null) {
            	throw new JniInchiException("Unrecognised return status: " + ret);
            }
            
            JniInchiOutput output = 
            	new JniInchiOutput(
            			retStatus,
            			wrapper.LibInchiGetInchi(),
            			wrapper.LibInchiGetAuxInfo(),
            			wrapper.LibInchiGetMessage(),
            			wrapper.LibInchiGetLog());
            
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

        	return wrapper.GetStructFromINCHI(input.getInchi(), input.getOptions());
        	
        	/*
//            String options = input.getOptions();
//            if (options.length() == 0) {
//                options = " ";
//            }
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

                int rad = wrapper.LibInchiGetAtomRadical(i);
                INCHI_RADICAL radical = INCHI_RADICAL.getValue(rad);
                if (radical == null) {
                	throw new JniInchiException("Unknown radical state: " + rad);
                }
                atom.setRadical(radical);

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
                        INCHI_BOND_TYPE type = INCHI_BOND_TYPE.getValue(bo0);
                        if (type == null) {
                            throw new JniInchiException("Unknown bond type: " + bo0);
                        }

                        int bs0 = bondStereos[i][j];
                        int bs1 = bondStereos[j][i];

                        if (bs0 != bs1) {
                            throw new JniInchiException("Bond stereo mismatch: " + i + "-" + j);
                        }

                        INCHI_BOND_STEREO stereo = INCHI_BOND_STEREO.getValue(bs0);
                        if (stereo == null) {
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

                INCHI_STEREOTYPE stereoType = INCHI_STEREOTYPE.getValue(type);
                if (stereoType == null) {
                	throw new JniInchiException("Unknown stereo0D type: " + type);
                }

                INCHI_PARITY stereoParity = INCHI_PARITY.getValue(parity);
                if (stereoParity == null) {
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
        	 */
        } finally {
            wrapper.releaseLock();
        }
    }


    public static JniInchiOutputKey getInChIKey(final String inchi) throws JniInchiException {
    	JniInchiWrapper wrapper = getWrapper();
        try {
            wrapper.getLock();
        } catch (TimeoutException ex) {
            throw new JniInchiException(ex);
        }
        
        try {

        	return wrapper.getINCHIKeyFromINCHI(inchi);
        	
        } finally {
        	wrapper.releaseLock();
        }

    }


    public static INCHI_KEY_STATUS checkInChIKey(final String key) throws JniInchiException {
    	JniInchiWrapper wrapper = getWrapper();
        try {
            wrapper.getLock();
        } catch (TimeoutException ex) {
            throw new JniInchiException(ex);
        }

        try {
	        int ret = wrapper.CheckINCHIKey(key);
	        INCHI_KEY_STATUS retStatus = INCHI_KEY_STATUS.getValue(ret);
	        if (retStatus == null) {
	        	throw new JniInchiException("Unknown return status: " + ret);
	        }
	        
	        return retStatus;
	        
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



    protected native static String LibInchiGetVersion();

    
    
    
    private native JniInchiOutput getINCHI(JniInchiInput input);
    
    private native JniInchiOutputKey getINCHIKeyFromINCHI(String inchi);
    
    private native int CheckINCHIKey(String key);
    
    private native JniInchiOutputStructure GetStructFromINCHI(String inchi, String options);
    
}



