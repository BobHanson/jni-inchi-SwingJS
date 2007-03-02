package net.sf.jniinchi;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class TestJniInchiBond extends TestCase {

    private static JniInchiAtom atO = new JniInchiAtom(0, 0, 0, "O");
    private static JniInchiAtom atT = new JniInchiAtom(1.21, 0, 0, "O");

    protected static JniInchiBond getTestBond() {
        return new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE);
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiBond.JniInchiBond(JniInchiAtom, JniInchiAtom, INCHI_BOND_TYPE, INCHI_BOND_STEREO)'
     */
    @Test
    public void testJniInchiBondJniInchiAtomJniInchiAtomINCHI_BOND_TYPEINCHI_BOND_STEREO() {
        JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE, INCHI_BOND_STEREO.DOUBLE_EITHER);
        Assert.assertEquals(atO, bond.getOriginAtom());
        Assert.assertEquals(atT, bond.getTargetAtom());
        Assert.assertEquals(INCHI_BOND_TYPE.DOUBLE, bond.getBondType());
        Assert.assertEquals(INCHI_BOND_STEREO.DOUBLE_EITHER, bond.getBondStereo());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiBond.JniInchiBond(JniInchiAtom, JniInchiAtom, INCHI_BOND_TYPE)'
     */
    @Test
    public void testJniInchiBondJniInchiAtomJniInchiAtomINCHI_BOND_TYPE() {
        JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE);
        Assert.assertEquals(atO, bond.getOriginAtom());
        Assert.assertEquals(atT, bond.getTargetAtom());
        Assert.assertEquals(INCHI_BOND_TYPE.DOUBLE, bond.getBondType());
        Assert.assertEquals(INCHI_BOND_STEREO.NONE, bond.getBondStereo());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiBond.setStereoDefinition(INCHI_BOND_STEREO)'
     */
    @Test
    public void testSetStereoDefinition() {
        JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE);
        Assert.assertEquals(INCHI_BOND_STEREO.NONE, bond.getBondStereo());
        bond.setStereoDefinition(INCHI_BOND_STEREO.DOUBLE_EITHER);
        Assert.assertEquals(INCHI_BOND_STEREO.DOUBLE_EITHER, bond.getBondStereo());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiBond.getOriginAtom()'
     */
    @Test
    public void testGetOriginAtom() {
        JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE);
        Assert.assertEquals(atO, bond.getOriginAtom());
        bond = new JniInchiBond(atT, atO, INCHI_BOND_TYPE.DOUBLE);
        Assert.assertEquals(atT, bond.getOriginAtom());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiBond.getTargetAtom()'
     */
    @Test
    public void testGetTargetAtom() {
        JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE);
        Assert.assertEquals(atT, bond.getTargetAtom());
        bond = new JniInchiBond(atT, atO, INCHI_BOND_TYPE.DOUBLE);
        Assert.assertEquals(atO, bond.getTargetAtom());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiBond.getBondType()'
     */
    @Test
    public void testGetBondType() {
        JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE);
        Assert.assertEquals(INCHI_BOND_TYPE.DOUBLE, bond.getBondType());
        bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.SINGLE);
        Assert.assertEquals(INCHI_BOND_TYPE.SINGLE, bond.getBondType());
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiBond.getBondStereo()'
     */
    @Test
    public void testGetBondStereo() {
        JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE, INCHI_BOND_STEREO.DOUBLE_EITHER);
        Assert.assertEquals(INCHI_BOND_STEREO.DOUBLE_EITHER, bond.getBondStereo());
        bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.SINGLE, INCHI_BOND_STEREO.SINGLE_1UP);
        Assert.assertEquals(INCHI_BOND_STEREO.SINGLE_1UP, bond.getBondStereo());
    }

}
