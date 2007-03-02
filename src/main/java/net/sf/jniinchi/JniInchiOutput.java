/* File: JniInchiOutput.java
 * Author: Sam Adams
 *
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi;

/**
 * Encapsulates output from InChI generation.
 * @author Sam Adams
 */
public class JniInchiOutput {

    /**
     * InChI return status
     */
    private INCHI_RET retStatus;

    /**
     * InChI ASCIIZ string
     */
    private String sInchi;

    /**
     * Aux info ASCIIZ string
     */
    private String sAuxInfo;

    /**
     * Error/warning ASCIIZ message
     */
    private String sMessage;

    /**
     * log-file ASCIIZ string, contains a human-readable list of recognized
     * options and possibly an Error/warning message
     */
    private String sLog;


    /**
     * Gets return status from InChI process.  OKAY and WARNING indicate
     * InChI has been generated, in all other cases InChI generation
     * has failed.
     */
    public INCHI_RET getReturnStatus() {
        return retStatus;
    }

    /**
     * Gets generated InChI string.
     */
    public String getInchi() {
        return sInchi;
    }

    /**
     * Gets generated InChI string.
     */
    public String getAuxInfo() {
        return sAuxInfo;
    }

    /**
     * Gets generated (error/warning) messages.
     */
    public String getMessage() {
        return sMessage;
    }

    /**
     * Gets generated log.
     */
    public String getLog() {
        return sLog;
    }

    protected void setLog(final String log) {
        this.sLog = log;
    }

    protected void setMessage(final String message) {
        this.sMessage = message;
    }

    protected void setRetStatus(final INCHI_RET retStatus) {
        this.retStatus = retStatus;
    }

    protected void setInchi(final String inchi) {
        this.sInchi = inchi;
    }

    protected void setAuxInfo(final String auxInfo) {
        this.sAuxInfo = auxInfo;
    }

}
