/* File: JniInchiOutputStructure.java
 * Author: Sam Adams
 *
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi;

/**
 * Encapsulates output from InChI to structure conversion.
 * @author Sam Adams
 */
public class JniInchiOutputStructure extends JniInchiStructure {

    /**
     * Return status from conversion.
     */
    private INCHI_RET retStatus;

    /**
     * Error/warning messages generated.
     */
    private String message;

    /**
     * Log generated.
     */
    private String log;

    /**
     * <p>Warning flags, see INCHIDIFF in inchicmp.h.
     *
     * <p>[x][y]:
     * <br>x=0 => Reconnected if present in InChI otherwise Disconnected/Normal
     * <br>x=1 => Disconnected layer if Reconnected layer is present
     * <br>y=1 => Main layer or Mobile-H
     * <br>y=0 => Fixed-H layer
     */
    private long[][] warningFlags = new long[2][2];


    /**
     * Gets return status from InChI process.  OKAY and WARNING indicate
     * InChI has been generated, in all other cases InChI generation
     * has failed.
     */
    public INCHI_RET getReturnStatus() {
        return retStatus;
    }

    /**
     * Gets generated (error/warning) messages.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets generated log.
     */
    public String getLog() {
        return log;
    }

    /**
     * <p>Returns warning flags, see INCHIDIFF in inchicmp.h.
     *
     * <p>[x][y]:
     * <br>x=0 => Reconnected if present in InChI otherwise Disconnected/Normal
     * <br>x=1 => Disconnected layer if Reconnected layer is present
     * <br>y=1 => Main layer or Mobile-H
     * <br>y=0 => Fixed-H layer
     */
    public long[][] getWarningFlags() {
        return warningFlags;
    }

    protected void setLog(final String log) {
        this.log = log;
    }

    protected void setMessage(final String message) {
        this.message = message;
    }

    protected void setRetStatus(final INCHI_RET retStatus) {
        this.retStatus = retStatus;
    }

    protected void setWarningFlags(final long[][] warningFlags) {
        this.warningFlags = warningFlags;
    }


}
