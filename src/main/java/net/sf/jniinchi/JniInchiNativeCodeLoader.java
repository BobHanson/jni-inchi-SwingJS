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

import java.io.File;
import java.io.IOException;

import net.sf.jnati.NativeCodeException;
import net.sf.jnati.NativeLibraryLoader;
import net.sf.jnati.JnatiConstants.ARCHITECTURE;
import net.sf.jnati.JnatiConstants.PLATFORM;

class JniInchiNativeCodeLoader extends NativeLibraryLoader {

    private static boolean DEBUG = false;

    private static final int CURRENT_NATIVE_VERSION_MAJOR = 1;

    private static final int CURRENT_NATIVE_VERSION_MINOR = 4;


    /**
     * InChI library file names - windows/linux
     */
    private static String[] INCHI_LIB_NAMES = { "libinchi.1.01.00.dll",
            "libinchi.so.1.01.00" };

    /**
     * JniInChI library file prefixes - windows/linux
     */
    private static String[] JNI_LIB_PREFIX = { "libJniInchi.",
            "libJniInchi." };

    /**
     * JniInChI library file suffixes - windows/linux
     */
    private static String[] JNI_LIB_SUFFIX = { ".dll", "so." };

    /**
     * Singleton.
     */
    private static JniInchiNativeCodeLoader loader;

    /**
     * Singleton getter.
     *
     * @return
     */
    public synchronized static JniInchiNativeCodeLoader getLoader()
            throws LoadNativeLibraryException {
        if (loader == null) {
            try {
                loader = new JniInchiNativeCodeLoader();
            } catch (NativeCodeException ex) {
                throw new LoadNativeLibraryException(ex.getMessage());
            } catch (IOException ex) {
                throw new LoadNativeLibraryException(ex.getMessage());
            }
        }

        return loader;
    }

    /**
     * Sets debug mode.
     *
     * @param debug
     */
    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    /**
     * Constructor. Sets/detects properties.
     */
    private JniInchiNativeCodeLoader() throws NativeCodeException, IOException {
        super("jniinchi", getVersionString());
    }

    /**
     * Load libraries in inchiFile and jniFile.
     *
     * @throws LoadNativeLibraryException
     */
    @Override
    protected void loadLibraries(ARCHITECTURE arch, PLATFORM os, String path) throws NativeCodeException {

        int i;
        if (PLATFORM.WINDOWS == os) {
            i = 0;
        } else if (PLATFORM.LINUX == os) {
            i = 1;
        } else {
            throw new NativeCodeException("Unsupported OS: " + os.name());
        }

        File inchiFile = new File(path, INCHI_LIB_NAMES[i]);
        File jniFile;
        if (PLATFORM.WINDOWS == os) {
            jniFile = new File(path, JNI_LIB_PREFIX[i] + getVersionString() + JNI_LIB_SUFFIX[i]);
        } else {
            jniFile = new File(path, JNI_LIB_PREFIX[i] + JNI_LIB_SUFFIX[i] + getVersionString());
        }

        // Load InChI native code
        try {
            log("Loading InChI library");
            System.load(inchiFile.getAbsolutePath());
        } catch (UnsatisfiedLinkError ule) {
            die("Error loading InChI library: " + ule.getMessage());
        }

        // Load JNI InChI native code
        try {
            log("Loading JNI library");
            System.load(jniFile.getAbsolutePath());
        } catch (UnsatisfiedLinkError ule) {
            die("Error loading JNI InChI library: " + ule.getMessage());
        }

        // Check version match
        try {
            log("Checking correct version is loaded");
            int majorVersion = JniInchiWrapper.LibInchiGetVersionMajor();
            int minorVersion = JniInchiWrapper.LibInchiGetVersionMinor();

            if (CURRENT_NATIVE_VERSION_MAJOR != majorVersion
                    || CURRENT_NATIVE_VERSION_MINOR != minorVersion) {
                die("Native code is version " + majorVersion + "."
                        + minorVersion + "; expected "
                        + CURRENT_NATIVE_VERSION_MAJOR + "."
                        + CURRENT_NATIVE_VERSION_MINOR);

            }
        } catch (UnsatisfiedLinkError ule) {
            die("Error getting native code version - cannot find native method. "
                    + ule.getMessage());
        }

        log("Native code loaded");
    }

    /**
     * Print message, if in debug mode.
     *
     * @param message
     */
    private void log(final String message) {
        if (DEBUG)
            System.out.println(message);
    }

    /**
     * Returns string representation of current native code version.
     *
     * @return
     */
    private static String getVersionString() {
        return (CURRENT_NATIVE_VERSION_MAJOR + "." + CURRENT_NATIVE_VERSION_MINOR);
    }

    private void die(final String message) throws NativeCodeException {

        System.err.println();
        System.err
                .println("JNI InChI has failed to load the native libraries required.");
        System.err.println();
        System.err
                .println("The most common problems are either the native library files being placed in");
        System.err
                .println("locations that JNI InChI does not know to search, or needing recompiling for");
        System.err.println("your system.");
        // 01234567890123456789012345678901234567890123456789012345678901234567890123456789
        System.err.println();
        System.err.println("ERROR MESSAGE: " + message);
        System.err.println();
        throw new NativeCodeException(
                "Failed to load native code. See STDERR for details.");
    }

}
