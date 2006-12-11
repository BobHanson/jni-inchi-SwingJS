package net.sf.jniinchi;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class TestJniInchiInput extends TestCase {

    /*
     * Test method for 'net.sf.jniinchi.JniInchiInput.getOptions()'
     */
	@Test
    public void testGetOptions() {
        JniInchiInput input = new JniInchiInput();
        input.options = "-compress";
        Assert.assertEquals(input.getOptions(), "-compress");
    }
}
