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
     * @param inchi InChI string
     * @param opts  Options
     */
    public JniInchiInputInchi(final String inchi) {
        this.inchiString = inchi;
        this.options = "";
    }

    /**
     * Constructor.
     * @param inchi    InChI string
     * @param opts    Options
     */
    public JniInchiInputInchi(final String inchi, final String opts) throws JniInchiException {
        this.inchiString = inchi;
        this.options = JniInchiWrapper.checkOptions(opts);
    }

    /**
     * Constructor.
     * @param inchi    InChI string
     * @param opts    Options
     */
    public JniInchiInputInchi(final String inchi, List opts) throws JniInchiException {
        this.inchiString = inchi;
        this.options = JniInchiWrapper.checkOptions(opts);
    }

    /**
     * Returns options string.
     * @return
     */
    public String getOptions() {
        return options;
    }

    /**
     * Returns options string.
     * @return
     */
    public String getInchi() {
        return inchiString;
    }
}
