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

    private static final String CURRENT_NATIVE_VERSION = "1.5";

    /**
     * JniInChI library file prefix
     */
    private static String JNI_LIB_PREFIX = "libJniInchi";
    
    private static String DOT = ".";

    /**
     * JniInChI library file suffixes - windows/linux
     */
    private static String[] JNI_LIB_SUFFIX = { "dll", "so" };

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
        super("jniinchi", CURRENT_NATIVE_VERSION);
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
        
        File jniFile;
        if (PLATFORM.WINDOWS == os) {
            jniFile = new File(path, JNI_LIB_PREFIX + DOT + CURRENT_NATIVE_VERSION + DOT + JNI_LIB_SUFFIX[i]);
        } else {
            jniFile = new File(path, JNI_LIB_PREFIX + DOT + JNI_LIB_SUFFIX[i] + DOT + CURRENT_NATIVE_VERSION);
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
            String nativeVersion = JniInchiWrapper.LibInchiGetVersion();

            if (!CURRENT_NATIVE_VERSION.equals(nativeVersion)) {
                die("Native code is version " + nativeVersion + "; expected "
                        + CURRENT_NATIVE_VERSION);

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
