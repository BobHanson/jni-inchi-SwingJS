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
    protected void loadLibraries(ARCHITECTURE arch, PLATFORM os, File path) throws NativeCodeException {

        int i;
        if (PLATFORM.WINDOWS == os) {
            i = 0;
        } else if (PLATFORM.LINUX == os) {
            i = 1;
        } else {
            throw new NativeCodeException("Unsupported OS: " + os.name());
        }
        
        String filename;
        if (PLATFORM.WINDOWS == os) {
            filename = JNI_LIB_PREFIX + DOT + CURRENT_NATIVE_VERSION + DOT + JNI_LIB_SUFFIX[i];
        } else {
            filename = JNI_LIB_PREFIX + DOT + JNI_LIB_SUFFIX[i] + DOT + CURRENT_NATIVE_VERSION;
        }
        File jniFile = new File(path, filename);
        
        // Load JNI InChI native code
        try {
            logger.debug("Loading library: " + jniFile.getAbsolutePath());
            System.load(jniFile.getAbsolutePath());
        } catch (UnsatisfiedLinkError e) {
        	logger.error("Error loading JNI InChI library", e);
        	throw new NativeCodeException("Unable to load JNI InChI library", e);
        }

        // Check version match
        String nativeVersion;
        try {
        	nativeVersion = JniInchiWrapper.LibInchiGetVersion();
        } catch (UnsatisfiedLinkError e) {
        	logger.error("Unable to get native code version", e);
        	throw new NativeCodeException("Unable to check JNI InChI native code version", e);
        }
        if (!CURRENT_NATIVE_VERSION.equals(nativeVersion)) {
        	logger.error("Native code version mismatch; expected " + CURRENT_NATIVE_VERSION + ", found " + nativeVersion);
            throw new NativeCodeException("JNI InChI native code version mismatch: expected "
                    + CURRENT_NATIVE_VERSION + ", found " + nativeVersion);

        }
        logger.debug("Native code version found: " + nativeVersion);
        logger.info("Native code loaded");
    }

}
