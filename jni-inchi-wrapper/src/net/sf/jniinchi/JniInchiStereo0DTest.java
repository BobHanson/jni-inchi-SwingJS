package net.sf.jniinchi;

import junit.framework.TestCase;

public class JniInchiStereo0DTest extends TestCase {
    
    protected static JniInchiAtom atC = new JniInchiAtom(0, 0, 0, "C");
    protected static JniInchiAtom at0 = new JniInchiAtom(0, 0, 0, "N");
    protected static JniInchiAtom at1 = new JniInchiAtom(0, 0, 0, "O");
    protected static JniInchiAtom at2 = new JniInchiAtom(0, 0, 0, "S");
    protected static JniInchiAtom at3 = new JniInchiAtom(0, 0, 0, "F");
    
    protected static JniInchiStereo0D getTestStereo0D() {
        return(new JniInchiStereo0D(atC, at0, at1, at2, at3, INCHI_STEREOTYPE.TETRAHEDRAL, INCHI_PARITY.ODD));
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStereo0D.JniInchiStereo0D(JniInchiAtom, JniInchiAtom, JniInchiAtom, JniInchiAtom, JniInchiAtom, INCHI_STEREOTYPE, INCHI_PARITY)'
     */
    public void testJniInchiStereo0D() {
        JniInchiStereo0D stereo = new JniInchiStereo0D(atC, at0, at1, at2, at3, INCHI_STEREOTYPE.TETRAHEDRAL, INCHI_PARITY.ODD);
        assertEquals(stereo.centralAtom, atC);
        assertEquals(stereo.neighbors[0], at0);
        assertEquals(stereo.neighbors[1], at1);
        assertEquals(stereo.neighbors[2], at2);
        assertEquals(stereo.neighbors[3], at3);
        assertEquals(stereo.type, INCHI_STEREOTYPE.TETRAHEDRAL);
        assertEquals(stereo.parity, INCHI_PARITY.ODD);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStereo0D.setDisconnectedParity(INCHI_PARITY)'
     */
    public void testSetDisconnectedParity() {
        JniInchiStereo0D stereo = getTestStereo0D();
        assertEquals(stereo.disconParity, INCHI_PARITY.NONE);
        stereo.setDisconnectedParity(INCHI_PARITY.EVEN);
        assertEquals(stereo.disconParity, INCHI_PARITY.EVEN);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStereo0D.getCentralAtom()'
     */
    public void testGetCentralAtom() {
        JniInchiStereo0D stereo = new JniInchiStereo0D(null, null, null, null, null, null, null);
        assertNull(stereo.getCentralAtom());
        stereo.centralAtom = atC;
        assertEquals(stereo.getCentralAtom(), atC);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStereo0D.getNeighbors()'
     */
    public void testGetNeighbors() {
        JniInchiStereo0D stereo = new JniInchiStereo0D(atC, at0, at1, at2, at3, INCHI_STEREOTYPE.TETRAHEDRAL, INCHI_PARITY.EVEN);
        JniInchiAtom[] neighbours = {at0, at1, at2, at3};
        stereo.neighbors = neighbours;
        assertEquals(stereo.getNeighbors(), neighbours);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStereo0D.getParity()'
     */
    public void testGetParity() {
        JniInchiStereo0D stereo = new JniInchiStereo0D(atC, at0, at1, at2, at3, INCHI_STEREOTYPE.TETRAHEDRAL, INCHI_PARITY.EVEN);
        assertEquals(stereo.getParity(), INCHI_PARITY.EVEN);
        stereo.parity = INCHI_PARITY.ODD;
        assertEquals(stereo.getParity(), INCHI_PARITY.ODD);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiStereo0D.getStereoType()'
     */
    public void testGetStereoType() {
        JniInchiStereo0D stereo = new JniInchiStereo0D(atC, at0, at1, at2, at3, INCHI_STEREOTYPE.TETRAHEDRAL, INCHI_PARITY.EVEN);
        assertEquals(stereo.getStereoType(), INCHI_STEREOTYPE.TETRAHEDRAL);
        stereo.type = INCHI_STEREOTYPE.ALLENE;
        assertEquals(stereo.getStereoType(), INCHI_STEREOTYPE.ALLENE);
    }
}
