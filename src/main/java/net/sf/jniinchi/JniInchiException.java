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
     * Constructor.
     * 
     * @param message
     */
    public JniInchiException(String message) {
        super(message);
    }
    
    public JniInchiException(Exception ex) {
        super(ex);
    }
}
