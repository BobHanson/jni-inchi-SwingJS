package net.sf.jniinchi;

import junit.framework.TestCase;

public class JniInchiStructureTest extends TestCase {

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getNumAtoms()'
     */
    public void testGetNumAtoms() {
        JniInchiStructure structure = new JniInchiStructure();
        assertEquals(structure.getNumAtoms(), 0);
        structure.atomList.add(JniInchiAtomTest.getTestAtom());
        assertEquals(structure.getNumAtoms(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getNumBonds()'
     */
    public void testGetNumBonds() {
        JniInchiStructure structure = new JniInchiStructure();
        assertEquals(structure.getNumBonds(), 0);
        structure.bondList.add(JniInchiBondTest.getTestBond());
        assertEquals(structure.getNumBonds(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getNumStereo0D()'
     */
    public void testGetNumStereo0D() {
        JniInchiStructure structure = new JniInchiStructure();
        assertEquals(structure.getNumStereo0D(), 0);
        structure.stereoList.add(JniInchiStereo0DTest.getTestStereo0D());
        assertEquals(structure.getNumStereo0D(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.addAtom(JniInchiAtom)'
     */
    public void testAddAtom() {
        JniInchiStructure structure = new JniInchiStructure();
        assertEquals(structure.getNumAtoms(), 0);
        structure.addAtom(JniInchiAtomTest.getTestAtom());
        assertEquals(structure.getNumAtoms(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.addBond(JniInchiBond)'
     */
    public void testAddBond() {
        JniInchiStructure structure = new JniInchiStructure();
        assertEquals(structure.getNumBonds(), 0);
        structure.addBond(JniInchiBondTest.getTestBond());
        assertEquals(structure.getNumBonds(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.addParity(JniInchiStereo0D)'
     */
    public void testAddStereo0D() {
        JniInchiStructure structure = new JniInchiStructure();
        assertEquals(structure.getNumStereo0D(), 0);
        structure.addStereo0D(JniInchiStereo0DTest.getTestStereo0D());
        assertEquals(structure.getNumStereo0D(), 1);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getAtom(int)'
     */
    public void testGetAtom() {
        JniInchiStructure structure = new JniInchiStructure();
        JniInchiAtom atom = JniInchiAtomTest.getTestAtom();
        structure.addAtom(atom);
        assertEquals(structure.getAtom(0), atom);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getBond(int)'
     */
    public void testGetBond() {
        JniInchiStructure structure = new JniInchiStructure();
        JniInchiBond bond = JniInchiBondTest.getTestBond();
        structure.addBond(bond);
        assertEquals(structure.getBond(0), bond);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStructure.getStereo0D(int)'
     */
    public void testGetStereo0D() {
        JniInchiStructure structure = new JniInchiStructure();
        JniInchiStereo0D stereo = JniInchiStereo0DTest.getTestStereo0D();
        structure.addStereo0D(stereo);
        assertEquals(structure.getStereo0D(0), stereo);
    }

}
