package net.sf.jniinchi;

import junit.framework.TestCase;

public class JniInchiInputInchiTest extends TestCase {

    /*
     * Test method for 'net.sf.jniinchi.JniInchiInputInchi.getOptions()'
     */
    public void testGetOptions() {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/C6H6/c1-2-4-6-5-3-1/h1-6H");
        input.options = "-compress";
        assertEquals(input.getOptions(), "-compress");
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiInputInchi.getInchi()'
     */
    public void testGetInchi() {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/C6H6/c1-2-4-6-5-3-1/h1-6H");
        assertEquals(input.getInchi(), "InChI=1/C6H6/c1-2-4-6-5-3-1/h1-6H");
    }
}
