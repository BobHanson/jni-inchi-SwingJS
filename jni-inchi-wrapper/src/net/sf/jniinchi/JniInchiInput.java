/* File: JniInchiInput.java
 * Author: Sam Adams
 * 
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi;

import java.util.List;

/**
 * Encapsulates structure input for InChI generation.
 * @author Sam Adams
 */
public class JniInchiInput extends JniInchiStructure {
	
    /**
     * Options string,
     */
    protected String options;
    
    /**
     * Constructor.
     * @throws JniInchiException
     */
    public JniInchiInput() throws JniInchiException {
        this.options = "";
    }
    
    /**
     * Constructor.
     * @param opts	Options string.
     * @throws JniInchiException
     */
    public JniInchiInput(String opts) throws JniInchiException {
    	this.options = JniInchiWrapper.checkOptions(opts);
    }
    
    /**
     * Constructor.
     * @param opts	List of options.
     * @throws JniInchiException
     */
    public JniInchiInput(List opts) throws JniInchiException {
    	this.options = JniInchiWrapper.checkOptions(opts);
    }
    
    /**
     * Returns options string.
     * @return
     */
    public String getOptions() {
    	return(options);
    }
}
