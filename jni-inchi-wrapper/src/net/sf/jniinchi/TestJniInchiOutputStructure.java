package net.sf.jniinchi;

import junit.framework.TestCase;

public class TestJniInchiOutputStructure extends TestCase {

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutputStructure.getReturnStatus()'
     */
    public void testGetReturnStatus() {
        JniInchiOutputStructure output = new JniInchiOutputStructure();
        output.retStatus = INCHI_RET.OKAY;
        assertEquals(output.getReturnStatus(), INCHI_RET.OKAY);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutputStructure.getMessage()'
     */
    public void testGetMessage() {
        JniInchiOutputStructure output = new JniInchiOutputStructure();
        output.message = "Test message";
        assertEquals(output.getMessage(), "Test message");
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutputStructure.getLog()'
     */
    public void testGetLog() {
        JniInchiOutputStructure output = new JniInchiOutputStructure();
        output.log = "Test log";
        assertEquals(output.getLog(), "Test log");
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutputStructure.getWarningFlags()'
     */
    public void testGetWarningFlags() {
        JniInchiOutputStructure output = new JniInchiOutputStructure();
        long[][] flags = {{1, 2}, {3, 4}};
        output.warningFlags = flags;
        assertEquals(output.getWarningFlags(), flags);
    }

}
