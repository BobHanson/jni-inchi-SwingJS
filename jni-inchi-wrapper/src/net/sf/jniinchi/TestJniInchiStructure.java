package net.sf.jniinchi;

import junit.framework.TestCase;

public class TestJniInchiStructure extends TestCase {

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getNumAtoms()'
     */
    public void testGetNumAtoms() {
        JniInchiStructure structure = new JniInchiStructure();
        assertEquals(structure.getNumAtoms(), 0);
        structure.atomList.add(TestJniInchiAtom.getNewTestAtom());
        assertEquals(structure.getNumAtoms(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getNumBonds()'
     */
    public void testGetNumBonds() {
        JniInchiStructure structure = new JniInchiStructure();
        assertEquals(structure.getNumBonds(), 0);
        structure.bondList.add(TestJniInchiBond.getTestBond());
        assertEquals(structure.getNumBonds(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getNumStereo0D()'
     */
    public void testGetNumStereo0D() {
        JniInchiStructure structure = new JniInchiStructure();
        assertEquals(structure.getNumStereo0D(), 0);
        structure.stereoList.add(TestJniInchiStereo0D.getTestStereo0D());
        assertEquals(structure.getNumStereo0D(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.addAtom(JniInchiAtom)'
     */
    public void testAddAtom() {
        JniInchiStructure structure = new JniInchiStructure();
        assertEquals(structure.getNumAtoms(), 0);
        structure.addAtom(TestJniInchiAtom.getNewTestAtom());
        assertEquals(structure.getNumAtoms(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.addBond(JniInchiBond)'
     */
    public void testAddBond() {
        JniInchiStructure structure = new JniInchiStructure();
        assertEquals(structure.getNumBonds(), 0);
        structure.addBond(TestJniInchiBond.getTestBond());
        assertEquals(structure.getNumBonds(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.addParity(JniInchiStereo0D)'
     */
    public void testAddStereo0D() {
        JniInchiStructure structure = new JniInchiStructure();
        assertEquals(structure.getNumStereo0D(), 0);
        structure.addStereo0D(TestJniInchiStereo0D.getTestStereo0D());
        assertEquals(structure.getNumStereo0D(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getAtom(int)'
     */
    public void testGetAtom() {
        JniInchiStructure structure = new JniInchiStructure();
        JniInchiAtom atom = TestJniInchiAtom.getNewTestAtom();
        structure.addAtom(atom);
        assertEquals(structure.getAtom(0), atom);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getBond(int)'
     */
    public void testGetBond() {
        JniInchiStructure structure = new JniInchiStructure();
        JniInchiBond bond = TestJniInchiBond.getTestBond();
        structure.addBond(bond);
        assertEquals(structure.getBond(0), bond);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getStereo0D(int)'
     */
    public void testGetStereo0D() {
        JniInchiStructure structure = new JniInchiStructure();
        JniInchiStereo0D stereo = TestJniInchiStereo0D.getTestStereo0D();
        structure.addStereo0D(stereo);
        assertEquals(structure.getStereo0D(0), stereo);
    }

}
