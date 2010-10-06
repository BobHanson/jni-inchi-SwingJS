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

import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeoutException;

import net.sf.jnati.NativeCodeException;
import net.sf.jnati.deploy.NativeLibraryLoader;

import org.apache.log4j.Logger;

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

    // TODO CheckINCHI
    // TODO AuxInfo to InChI

    private static final Logger LOG = Logger.getLogger(JniInchiWrapper.class);

    private static final String ID = "jniinchi";
    private static final String VERSION = "1.03_1";

    /**
     * Maximum time to wait for a lock (in seconds).
     */
    private static final int MAX_LOCK_TIMEOUT = 15;

    /**
     * Flag indicating windows or linux.
     */
    private static final boolean IS_WINDOWS = System.getProperty("os.name", "").toLowerCase().startsWith("windows");

    /**
     * Switch character for passing options. / in windows, - on other systems.
     */
    static final String flagChar = IS_WINDOWS ? "/" : "-";

    /**
     * Records whether native library has been loaded by system.
     */
    private static boolean libraryLoaded = false;

    private static JniInchiWrapper inchiWrapper;


    /**
     * Loads native library.
     * @throws JniInchiException Library failed to load
     */
    public static synchronized void loadLibrary() throws LoadNativeLibraryException {
        if (!libraryLoaded) {
            try {
                NativeLibraryLoader.loadLibrary(ID, VERSION);

                // Check expected version of native code loaded
                // Throws NativeCodeException if unable to make call / wrong version
                checkNativeCodeVersion();

                // Everything is set up!
                libraryLoaded = true;
            } catch (NativeCodeException ex) {
                System.err.println();
                System.err.println("Error loading JNI InChI native code.");
                System.err.println("You may need to compile the native code for your platform.");
                System.err.println("See http://jni-inchi.sourceforge.net for instructions.");
                System.err.println();
                throw new LoadNativeLibraryException(ex);
            }
        }
    }

    /**
     * Checks the expected native code version has been loaded.
     * @throws NativeCodeException
     */
    private static void checkNativeCodeVersion() throws NativeCodeException {

        LOG.trace("Checking native code version");

        // Get native code version string
        String nativeVersion;
        try {
            nativeVersion = JniInchiWrapper.LibInchiGetVersion();
        } catch (UnsatisfiedLinkError e) {
            LOG.error("Unable to get native code version", e);
            throw new NativeCodeException("Unable get native code version", e);
        }

        // Compare to expected version
        if (!VERSION.equals(nativeVersion)) {
            LOG.error("Native code version mismatch; expected " + VERSION + ", found " + nativeVersion);
            throw new NativeCodeException("JNI InChI native code version mismatch: expected "
                    + VERSION + ", found " + nativeVersion);
        }

        LOG.trace("Expected native code version found: " + nativeVersion);
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
        init();
    }


    /**
     * Checks and canonicalises options.
     *
     * @param ops  List of INCHI_OPTION
     */
    protected static String checkOptions(List<INCHI_OPTION> ops) throws JniInchiException {
        if (ops == null) {
            throw new IllegalArgumentException("Null options");
        }
        StringBuffer sbOptions = new StringBuffer();

        for (int i = 0; i < ops.size(); i++) {
            Object op = ops.get(i);
            if (op instanceof INCHI_OPTION) {
                sbOptions.append(flagChar + ((INCHI_OPTION) op).name() + " ");
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
        if (ops == null) {
            throw new IllegalArgumentException("Null options");
        }
        StringBuilder sbOptions = new StringBuilder();

        StringTokenizer tok = new StringTokenizer(ops);
        while (tok.hasMoreTokens()) {
            String op = tok.nextToken();

            if (op.startsWith("-") || op.startsWith("/")) {
                op = op.substring(1);
            }

            INCHI_OPTION option = INCHI_OPTION.valueOfIgnoreCase(op);
            if (option != null) {
                sbOptions.append(flagChar + option.name());
                if (tok.hasMoreTokens()) {
                     sbOptions.append(" ");
                }
            } else {
                throw new JniInchiException("Unrecognised InChI option");
            }
        }

        return sbOptions.toString();
    }


    /**
     * <p>Calculates the InChI for a chemical structure.</p>
     * <p>If no InChI creation/stereo modification options are specified then a standard InChI is produced.
     * If any of SUU/SLUUD/RecMet/FixedH/Ket/15T/SRel/SRac/SUCF options are specified, then the generated InChI
     * will be non-standard one.</p>
     * @param input
     * @return
     * @throws JniInchiException
     */
    @SuppressWarnings("unchecked")
    public static JniInchiOutput getInchi(JniInchiInput input) throws JniInchiException {
        if (input == null) {
            throw new IllegalArgumentException("Null input");
        }
        JniInchiWrapper wrapper = getWrapper();
        try {
            wrapper.getLock();
        } catch (TimeoutException ex) {
            throw new JniInchiException(ex);
        }

        try {
            return wrapper.GetINCHI(input);
        } finally {
            wrapper.releaseLock();
        }
    }


    /**
     * <p>Calculates the Standard InChI string for a chemical structure.</p>
     * <p>The only valid structure perception options are NEWPSOFF/DoNotAddH/SNon. In any other structural
     * perception options are specified then the calculation will fail.</p>
     * @param input
     * @return
     * @throws JniInchiException
     */
    @SuppressWarnings("unchecked")
    public static JniInchiOutput getStdInchi(JniInchiInput input) throws JniInchiException {
        if (input == null) {
            throw new IllegalArgumentException("Null input");
        }
        JniInchiWrapper wrapper = getWrapper();
        try {
            wrapper.getLock();
        } catch (TimeoutException ex) {
            throw new JniInchiException(ex);
        }

        try {
            return wrapper.GetStdINCHI(input);
        } finally {
            wrapper.releaseLock();
        }
    }


    /**
     * <p>Converts an InChI into an InChI for validation purposes (the same as the -InChI2InChI option).</p>
     * <p>This method may also be used to filter out specific layers. For instance, /Snon would remove the
     * stereochemical layer; Omitting /FixedH and/or /RecMet would remove Fixed-H or Reconnected layers.
     * In order to keep all InChI layers use options string "/FixedH /RecMet"; option /InChI2InChI is not needed.</p>         
     * @param input
     * @return
     * @throws JniInchiException
     */
    public static JniInchiOutput getInchiFromInchi(JniInchiInputInchi input) throws JniInchiException {
        if (input == null) {
            throw new IllegalArgumentException("Null input");
        }
        JniInchiWrapper wrapper = getWrapper();
        try {
            wrapper.getLock();
        } catch (TimeoutException ex) {
            throw new JniInchiException(ex);
        }

        try {
            return wrapper.GetINCHIfromINCHI(input.getInchi(), input.getOptions());
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
        if (input == null) {
            throw new IllegalArgumentException("Null input");
        }
        JniInchiWrapper wrapper = getWrapper();
        try {
            wrapper.getLock();
        } catch (TimeoutException ex) {
            throw new JniInchiException(ex);
        }

        try {
            return wrapper.GetStructFromINCHI(input.getInchi(), input.getOptions());
        } finally {
            wrapper.releaseLock();
        }
    }


    /**
     * Calculates the InChIKey for an InChI string.
     * @param inchi     source InChI string
     * @return  InChIKey output
     * @throws  JniInchiException
     */
    public static JniInchiOutputKey getInChIKey(final String inchi) throws JniInchiException {
        if (inchi == null) {
            throw new IllegalArgumentException("Null InChI");
        }
        JniInchiWrapper wrapper = getWrapper();
        try {
            wrapper.getLock();
        } catch (TimeoutException ex) {
            throw new JniInchiException(ex);
        }

        try {

            return wrapper.GetINCHIKeyFromINCHI(inchi);

        } finally {
            wrapper.releaseLock();
        }

    }


    /**
     * Checks whether a string represents valid InChIKey.
     * @param key
     * @return
     * @throws JniInchiException
     */
    public static INCHI_KEY_STATUS checkInChIKey(final String key) throws JniInchiException {
        if (key == null) {
            throw new IllegalArgumentException("Null InChI key");
        }
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


    public static void getInputFromAuxInfo(String auxInfo) throws JniInchiException {
        if (auxInfo == null) {
            throw new IllegalArgumentException("Null AuxInfo");
        }
        JniInchiWrapper wrapper = getWrapper();
        try {
            wrapper.getLock();
        } catch (TimeoutException ex) {
            throw new JniInchiException(ex);
        }

        try {
            wrapper.GetINCHIInputFromAuxInfo(auxInfo, false, false);
//            INCHI_KEY_STATUS retStatus = INCHI_KEY_STATUS.getValue(ret);
//            if (retStatus == null) {
//                throw new JniInchiException("Unknown return status: " + ret);
//            }

            return;

        } finally {
            wrapper.releaseLock();
        }
    }


    private volatile boolean locked = false;

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


    protected native static String LibInchiGetVersion();


    private native void init();

    private native JniInchiOutput GetINCHI(JniInchiInput input);

    private native JniInchiOutput GetStdINCHI(JniInchiInput input);

    private native JniInchiOutput GetINCHIfromINCHI(String inchi, String options);

    private native JniInchiOutputStructure GetStructFromINCHI(String inchi, String options);

    private native JniInchiOutputKey GetINCHIKeyFromINCHI(String inchi);

    private native JniInchiOutputKey GetStdINCHIKeyFromStdINCHI(String inchi);

    private native int CheckINCHIKey(String key);

    private native JniInchiInput GetINCHIInputFromAuxInfo(String auxInfo, boolean bDoNotAddH, boolean bDiffUnkUndfStereo);

}
