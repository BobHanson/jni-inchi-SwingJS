package net.sf.jniinchi;

public class JniInchiOutput {
	
	/**
	 * InChI return status
	 */
	INCHI_RET retStatus;
	
	/**
	 * InChI ASCIIZ string
	 */
	String sInchi;
	
	/**
	 * Aux info ASCIIZ string
	 */
	String sAuxInfo;
	
	/**
	 * Error/warning ASCIIZ message
	 */
	String sMessage;
	
	/**
	 * log-file ASCIIZ string, contains a human-readable list of recognized 
	 * options and possibly an Error/warning message
	 */
	String sLog;
	
	
	/**
     * Gets return status from InChI process.  OKAY and WARNING indicate
     * InChI has been generated, in all other cases InChI generation
     * has failed.
     */
    public INCHI_RET getReturnStatus() {
        return(retStatus);
    }
    
    /**
     * Gets generated InChI string.
     */
    public String getInchi() {
        return(sInchi);
    }
    
    /**
     * Gets generated AuxInfo string.
     */
    public String getAuxInfo() {
        return(sAuxInfo);
    }
    
    /**
     * Gets generated (error/warning) messages.
     */
    public String getMessage() {
        return(sMessage);
    }
    
    /**
     * Gets generated log.
     */
    public String getLog() {
        return(sLog);
    }
}
