package net.sf.jniinchi;

import junit.framework.TestCase;

public class TestJniInchiInput extends TestCase {

    /*
     * Test method for 'net.sf.jniinchi.JniInchiInput.getOptions()'
     */
    public void testGetOptions() {
        JniInchiInput input = new JniInchiInput();
        input.options = "-compress";
        assertEquals(input.getOptions(), "-compress");
    }
}
