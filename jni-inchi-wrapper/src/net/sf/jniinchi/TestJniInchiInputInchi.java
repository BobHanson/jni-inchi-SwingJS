package net.sf.jniinchi;

import org.junit.Assert;
import org.junit.Test;

public class TestJniInchiInputInchi {

    /*
     * Test method for 'net.sf.jniinchi.JniInchiInputInchi.getOptions()'
     */
	@Test
    public void testGetOptions() {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/C6H6/c1-2-4-6-5-3-1/h1-6H");
        input.options = "-compress";
        Assert.assertEquals(input.getOptions(), "-compress");
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiInputInchi.getInchi()'
     */
	@Test
    public void testGetInchi() {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/C6H6/c1-2-4-6-5-3-1/h1-6H");
        Assert.assertEquals(input.getInchi(), "InChI=1/C6H6/c1-2-4-6-5-3-1/h1-6H");
    }
}
