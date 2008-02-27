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

import net.sf.jnati.NativeCodeException;
import net.sf.jnati.NativeLibraryLoader;

class JniInchiNativeCodeLoader extends NativeLibraryLoader {

	/**
	 * Expected version of the native code.
	 */
    private static final String NATIVE_CODE_VERSION = "1.6";

    /**
     * Singleton.
     */
    private static JniInchiNativeCodeLoader loader;

    /**
     * Instance getter, for singleton pattern.
     * @return
     * @throws LoadNativeLibraryException
     */
    public static JniInchiNativeCodeLoader getLoader() throws LoadNativeLibraryException {
    	if (loader == null) {
    		createLoader();
    	}
    	return loader;
    }

    /**
     * Instance builder, for singleton pattern.
     * @throws LoadNativeLibraryException
     */
    private static synchronized void createLoader() throws LoadNativeLibraryException {
        if (loader == null) {
            try {
                loader = new JniInchiNativeCodeLoader();
            } catch (NativeCodeException ex) {
                throw new LoadNativeLibraryException(ex);
            }
        }
    }

    /**
     * Constructor. Sets/detects properties.
     */
    private JniInchiNativeCodeLoader() throws NativeCodeException {
        // Super checks/deploys native code, loads librarys into memory
    	// Throws NativeCodeException if error occurs
    	super("jniinchi", NATIVE_CODE_VERSION);

    	// Check expected version of native code loaded
    	// Throws NativeCodeException if unable to make call / wrong version
        checkNativeCodeVersion();

        // Everything is set up!
    }

    /**
     * Checks the expected native code version has been loaded.
     * @throws NativeCodeException
     */
    private void checkNativeCodeVersion() throws NativeCodeException {

    	logger.trace("Checking native code version");

    	// Get native code version string
    	String nativeVersion;
        try {
        	nativeVersion = JniInchiWrapper.LibInchiGetVersion();
        } catch (UnsatisfiedLinkError e) {
        	logger.error("Unable to get native code version", e);
        	throw new NativeCodeException("Unable get native code version", e);
        }

        // Compare to expected version
        if (!NATIVE_CODE_VERSION.equals(nativeVersion)) {
        	logger.error("Native code version mismatch; expected " + NATIVE_CODE_VERSION + ", found " + nativeVersion);
            throw new NativeCodeException("JNI InChI native code version mismatch: expected "
                    + NATIVE_CODE_VERSION + ", found " + nativeVersion);
        }

        logger.trace("Expected native code version found: " + nativeVersion);
    }

}
