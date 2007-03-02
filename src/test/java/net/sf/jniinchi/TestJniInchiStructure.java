package net.sf.jniinchi;

import org.junit.Assert;
import org.junit.Test;

public class TestJniInchiStructure {

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getNumAtoms()'
     */
    @Test
    public void testGetNumAtoms() {
        JniInchiStructure structure = new JniInchiStructure();
        Assert.assertEquals(0, structure.getNumAtoms());
        structure.addAtom(TestJniInchiAtom.getNewTestAtom());
        Assert.assertEquals(1, structure.getNumAtoms());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getNumBonds()'
     */
    @Test
    public void testGetNumBonds() {
        JniInchiStructure structure = new JniInchiStructure();
        Assert.assertEquals(0, structure.getNumBonds());
        structure.addBond(TestJniInchiBond.getTestBond());
        Assert.assertEquals(1, structure.getNumBonds());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getNumStereo0D()'
     */
    @Test
    public void testGetNumStereo0D() {
        JniInchiStructure structure = new JniInchiStructure();
        Assert.assertEquals(0, structure.getNumStereo0D());
        structure.addStereo0D(TestJniInchiStereo0D.getTestStereo0D());
        Assert.assertEquals(1, structure.getNumStereo0D());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.addAtom(JniInchiAtom)'
     */
    @Test
    public void testAddAtom() {
        JniInchiStructure structure = new JniInchiStructure();
        Assert.assertEquals(0, structure.getNumAtoms());
        structure.addAtom(TestJniInchiAtom.getNewTestAtom());
        Assert.assertEquals(1, structure.getNumAtoms());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.addBond(JniInchiBond)'
     */
    @Test
    public void testAddBond() {
        JniInchiStructure structure = new JniInchiStructure();
        Assert.assertEquals(0, structure.getNumBonds());
        structure.addBond(TestJniInchiBond.getTestBond());
        Assert.assertEquals(1, structure.getNumBonds());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.addParity(JniInchiStereo0D)'
     */
    @Test
    public void testAddStereo0D() {
        JniInchiStructure structure = new JniInchiStructure();
        Assert.assertEquals(0, structure.getNumStereo0D());
        structure.addStereo0D(TestJniInchiStereo0D.getTestStereo0D());
        Assert.assertEquals(1, structure.getNumStereo0D());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getAtom(int)'
     */
    @Test
    public void testGetAtom() {
        JniInchiStructure structure = new JniInchiStructure();
        JniInchiAtom atom = TestJniInchiAtom.getNewTestAtom();
        structure.addAtom(atom);
        Assert.assertEquals(atom, structure.getAtom(0));
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getBond(int)'
     */
    @Test
    public void testGetBond() {
        JniInchiStructure structure = new JniInchiStructure();
        JniInchiBond bond = TestJniInchiBond.getTestBond();
        structure.addBond(bond);
        Assert.assertEquals(bond, structure.getBond(0));
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getStereo0D(int)'
     */
    @Test
    public void testGetStereo0D() {
        JniInchiStructure structure = new JniInchiStructure();
        JniInchiStereo0D stereo = TestJniInchiStereo0D.getTestStereo0D();
        structure.addStereo0D(stereo);
        Assert.assertEquals(stereo, structure.getStereo0D(0));
    }

}
