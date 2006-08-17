/* File: JniInchiInputInchi.java
 * Author: Sam Adams
 * 
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi;

import java.util.List;

/**
 * Encapsulates input for InChI to structure conversion.
 * @author Sam Adams
 */
public class JniInchiInputInchi {
	
	/**
	 * InChI ASCIIZ string to be converted to a strucure
	 */
	protected String inchiString;
	
	/**
	 * InChI options: space-delimited
	 */
	protected String options;
	
	/**
	 * Constructor.
	 * @param inchi	InChI string
	 * @param opts	Options
	 */
	public JniInchiInputInchi(String inchi, String opts) throws JniInchiException {
		this.inchiString = inchi;
		this.options = JniInchiWrapper.checkOptions(opts);
	}
	
	/**
	 * Constructor.
	 * @param inchi	InChI string
	 * @param opts	Options
	 */
	public JniInchiInputInchi(String inchi, List opts) throws JniInchiException {
		this.inchiString = inchi;
		this.options = JniInchiWrapper.checkOptions(opts);
	}
}
