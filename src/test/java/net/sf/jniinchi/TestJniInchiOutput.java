package net.sf.jniinchi;

import org.junit.Assert;
import org.junit.Test;

public class TestJniInchiOutput {

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutput.getReturnStatus()'
     */
    @Test
    public void testGetReturnStatus() {
        JniInchiOutput output = new JniInchiOutput();
        output.setRetStatus(INCHI_RET.OKAY);
        Assert.assertEquals(INCHI_RET.OKAY, output.getReturnStatus());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutput.getInchi()'
     */
    @Test
    public void testGetInchi() {
        JniInchiOutput output = new JniInchiOutput();
        output.setInchi("Inchi=1/C6H6/c1-2-4-6-5-3-1/h1-6H");
        Assert.assertEquals("Inchi=1/C6H6/c1-2-4-6-5-3-1/h1-6H", output.getInchi());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutput.getAuxInfo()'
     */
    @Test
    public void testGetAuxInfo() {
        JniInchiOutput output = new JniInchiOutput();
        output.setAuxInfo("AuxInfo=1/0/N:1,2,6,3,5,4/E:(1,2,3,4,5,6)/rA:6nCCCCCC/rB:d1;s2;d3;s4;s1d5;/rC:-.7145,.4125,0;-.7145,-.4125,0;0,-.825,0;.7145,-.4125,0;.7145,.4125,0;0,.825,0;");
        Assert.assertEquals("AuxInfo=1/0/N:1,2,6,3,5,4/E:(1,2,3,4,5,6)/rA:6nCCCCCC/rB:d1;s2;d3;s4;s1d5;/rC:-.7145,.4125,0;-.7145,-.4125,0;0,-.825,0;.7145,-.4125,0;.7145,.4125,0;0,.825,0;", output.getAuxInfo());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutput.getMessage()'
     */
    @Test
    public void testGetMessage() {
        JniInchiOutput output = new JniInchiOutput();
        output.setMessage("Test message");
        Assert.assertEquals("Test message", output.getMessage());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiOutput.getLog()'
     */
    @Test
    public void testGetLog() {
        JniInchiOutput output = new JniInchiOutput();
        output.setLog("Test log");
        Assert.assertEquals("Test log", output.getLog());
    }
}
