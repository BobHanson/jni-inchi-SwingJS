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
        Assert.assertEquals(structure.getNumAtoms(), 0);
        structure.atomList.add(TestJniInchiAtom.getNewTestAtom());
        Assert.assertEquals(structure.getNumAtoms(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getNumBonds()'
     */
    @Test
    public void testGetNumBonds() {
        JniInchiStructure structure = new JniInchiStructure();
        Assert.assertEquals(structure.getNumBonds(), 0);
        structure.bondList.add(TestJniInchiBond.getTestBond());
        Assert.assertEquals(structure.getNumBonds(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getNumStereo0D()'
     */
    @Test
    public void testGetNumStereo0D() {
        JniInchiStructure structure = new JniInchiStructure();
        Assert.assertEquals(structure.getNumStereo0D(), 0);
        structure.stereoList.add(TestJniInchiStereo0D.getTestStereo0D());
        Assert.assertEquals(structure.getNumStereo0D(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.addAtom(JniInchiAtom)'
     */
    @Test
    public void testAddAtom() {
        JniInchiStructure structure = new JniInchiStructure();
        Assert.assertEquals(structure.getNumAtoms(), 0);
        structure.addAtom(TestJniInchiAtom.getNewTestAtom());
        Assert.assertEquals(structure.getNumAtoms(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.addBond(JniInchiBond)'
     */
    @Test
    public void testAddBond() {
        JniInchiStructure structure = new JniInchiStructure();
        Assert.assertEquals(structure.getNumBonds(), 0);
        structure.addBond(TestJniInchiBond.getTestBond());
        Assert.assertEquals(structure.getNumBonds(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.addParity(JniInchiStereo0D)'
     */
    @Test
    public void testAddStereo0D() {
        JniInchiStructure structure = new JniInchiStructure();
        Assert.assertEquals(structure.getNumStereo0D(), 0);
        structure.addStereo0D(TestJniInchiStereo0D.getTestStereo0D());
        Assert.assertEquals(structure.getNumStereo0D(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getAtom(int)'
     */
    @Test
    public void testGetAtom() {
        JniInchiStructure structure = new JniInchiStructure();
        JniInchiAtom atom = TestJniInchiAtom.getNewTestAtom();
        structure.addAtom(atom);
        Assert.assertEquals(structure.getAtom(0), atom);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getBond(int)'
     */
    @Test
    public void testGetBond() {
        JniInchiStructure structure = new JniInchiStructure();
        JniInchiBond bond = TestJniInchiBond.getTestBond();
        structure.addBond(bond);
        Assert.assertEquals(structure.getBond(0), bond);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getStereo0D(int)'
     */
    @Test
    public void testGetStereo0D() {
        JniInchiStructure structure = new JniInchiStructure();
        JniInchiStereo0D stereo = TestJniInchiStereo0D.getTestStereo0D();
        structure.addStereo0D(stereo);
        Assert.assertEquals(structure.getStereo0D(0), stereo);
    }

}
