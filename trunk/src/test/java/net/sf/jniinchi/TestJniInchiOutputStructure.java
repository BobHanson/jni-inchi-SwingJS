package net.sf.jniinchi;

import org.junit.Assert;
import org.junit.Test;

public class TestJniInchiOutputStructure {

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutputStructure.getReturnStatus()'
     */
    @Test
    public void testGetReturnStatus() {
        JniInchiOutputStructure output = new JniInchiOutputStructure();
        output.retStatus = INCHI_RET.OKAY;
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutputStructure.getMessage()'
     */
    @Test
    public void testGetMessage() {
        JniInchiOutputStructure output = new JniInchiOutputStructure();
        output.message = "Test message";
        Assert.assertEquals("Test message", output.getMessage());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutputStructure.getLog()'
     */
    @Test
    public void testGetLog() {
        JniInchiOutputStructure output = new JniInchiOutputStructure();
        output.log = "Test log";
        Assert.assertEquals("Test log", output.getLog());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutputStructure.getWarningFlags()'
     */
    @Test
    public void testGetWarningFlags() {
        JniInchiOutputStructure output = new JniInchiOutputStructure();
        long[][] flags = {{1, 2}, {3, 4}};
        output.warningFlags = flags;
        Assert.assertEquals(flags, output.getWarningFlags());
    }

}
