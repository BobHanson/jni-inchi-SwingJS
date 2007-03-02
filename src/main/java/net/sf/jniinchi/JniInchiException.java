/* File: JniInchiException.java
 * Author: Sam Adams
 *
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi;

/**
 * Exception thrown by JniInchi.
 * @author Sam Adams
 */
public class JniInchiException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     */
    public JniInchiException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message  the detail message.
     */
    public JniInchiException(final String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param ex    the cause.
     */
    public JniInchiException(final Exception ex) {
        super(ex);
    }
}
