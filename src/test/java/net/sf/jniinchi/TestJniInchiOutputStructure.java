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
        output.setRetStatus(INCHI_RET.OKAY);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutputStructure.getMessage()'
     */
    @Test
    public void testGetMessage() {
        JniInchiOutputStructure output = new JniInchiOutputStructure();
        output.setMessage("Test message");
        Assert.assertEquals("Test message", output.getMessage());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutputStructure.getLog()'
     */
    @Test
    public void testGetLog() {
        JniInchiOutputStructure output = new JniInchiOutputStructure();
        output.setLog("Test log");
        Assert.assertEquals("Test log", output.getLog());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutputStructure.getWarningFlags()'
     */
    @Test
    public void testGetWarningFlags() {
        JniInchiOutputStructure output = new JniInchiOutputStructure();
        long[][] flags = {{1, 2}, {3, 4}};
        output.setWarningFlags(flags);
        Assert.assertEquals(flags, output.getWarningFlags());
    }

}
