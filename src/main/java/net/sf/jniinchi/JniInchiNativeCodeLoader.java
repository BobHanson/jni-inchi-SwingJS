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

import java.io.IOException;

import net.sf.jnati.NativeCodeException;
import net.sf.jnati.NativeLibraryLoader;

class JniInchiNativeCodeLoader extends NativeLibraryLoader {

    private static final String NATIVE_CODE_VERSION = "1.5";

    /**
     * Singleton.
     */
    private static JniInchiNativeCodeLoader loader;

    /**
     * Singleton getter.
     *
     * @return
     * @throws LoadNativeLibraryException
     */
    public static JniInchiNativeCodeLoader getLoader() throws LoadNativeLibraryException {
    	if (loader == null) {
    		createLoader();
    	}
    	return loader;
    }

    private static synchronized void createLoader() throws LoadNativeLibraryException {
        if (loader == null) {
            try {
                loader = new JniInchiNativeCodeLoader();
            } catch (NativeCodeException ex) {
                throw new LoadNativeLibraryException(ex.getMessage());
            } catch (IOException ex) {
                throw new LoadNativeLibraryException(ex.getMessage());
            }
        }
    }

    /**
     * Constructor. Sets/detects properties.
     */
    private JniInchiNativeCodeLoader() throws NativeCodeException, IOException {
        super("jniinchi", NATIVE_CODE_VERSION);
    }

    /**
     * Call-back from constructor to load library file.
     * @throws NativeCodeException
     * @throws LoadNativeLibraryException
     */
    @Override
    protected void librariesLoaded() throws NativeCodeException {

        // Check expected version of native code is loaded

    	// Get native code version string
    	String nativeVersion;
        try {
        	nativeVersion = JniInchiWrapper.LibInchiGetVersion();
        } catch (UnsatisfiedLinkError e) {
        	logger.error("Unable to get native code version", e);
        	throw new NativeCodeException("Unable to check JNI InChI native code version", e);
        }

        // Compare to expected version
        if (!NATIVE_CODE_VERSION.equals(nativeVersion)) {
        	logger.error("Native code version mismatch; expected " + NATIVE_CODE_VERSION + ", found " + nativeVersion);
            throw new NativeCodeException("JNI InChI native code version mismatch: expected "
                    + NATIVE_CODE_VERSION + ", found " + nativeVersion);
        }
        logger.debug("Native code version found: " + nativeVersion);
    }

}
