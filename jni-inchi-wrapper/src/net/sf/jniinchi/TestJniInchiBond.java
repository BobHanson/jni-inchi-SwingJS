package net.sf.jniinchi;

import org.junit.Assert;
import org.junit.Test;

public class TestJniInchiBond {
	
	protected static JniInchiAtom atO = new JniInchiAtom(0, 0, 0, "O");
	protected static JniInchiAtom atT = new JniInchiAtom(1.21, 0, 0, "O");
    
    protected static JniInchiBond getTestBond() {
        return(new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE));
    }
	
	/*
	 * Test method for 'net.sf.jniinchi.JniInchiBond.JniInchiBond(JniInchiAtom, JniInchiAtom, INCHI_BOND_TYPE, INCHI_BOND_STEREO)'
	 */
    @Test
	public void testJniInchiBondJniInchiAtomJniInchiAtomINCHI_BOND_TYPEINCHI_BOND_STEREO() {
		JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE, INCHI_BOND_STEREO.DOUBLE_EITHER);
		Assert.assertEquals(bond.atomOrigin, atO);
		Assert.assertEquals(bond.atomTarget, atT);
		Assert.assertEquals(bond.type, INCHI_BOND_TYPE.DOUBLE);
		Assert.assertEquals(bond.stereo, INCHI_BOND_STEREO.DOUBLE_EITHER);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiBond.JniInchiBond(JniInchiAtom, JniInchiAtom, INCHI_BOND_TYPE)'
	 */
    @Test
	public void testJniInchiBondJniInchiAtomJniInchiAtomINCHI_BOND_TYPE() {
		JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE);
		Assert.assertEquals(bond.atomOrigin, atO);
		Assert.assertEquals(bond.atomTarget, atT);
		Assert.assertEquals(bond.type, INCHI_BOND_TYPE.DOUBLE);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiBond.setStereoDefinition(INCHI_BOND_STEREO)'
	 */
    @Test
	public void testSetStereoDefinition() {
		JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE, null);
		bond.setStereoDefinition(INCHI_BOND_STEREO.DOUBLE_EITHER);
		Assert.assertEquals(bond.stereo, INCHI_BOND_STEREO.DOUBLE_EITHER);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiBond.getOriginAtom()'
	 */
    @Test
	public void testGetOriginAtom() {
		JniInchiBond bond = new JniInchiBond(null, null, INCHI_BOND_TYPE.DOUBLE);
		bond.atomOrigin = atO;
		Assert.assertEquals(bond.getOriginAtom(), atO);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiBond.getTargetAtom()'
	 */
    @Test
	public void testGetTargetAtom() {
		JniInchiBond bond = new JniInchiBond(null, null, INCHI_BOND_TYPE.DOUBLE);
		bond.atomTarget = atT;
		Assert.assertEquals(bond.getTargetAtom(), atT);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiBond.getBondType()'
	 */
    @Test
	public void testGetBondType() {
		JniInchiBond bond = new JniInchiBond(atO, atT, null);
		bond.type = INCHI_BOND_TYPE.DOUBLE;
		Assert.assertEquals(bond.getBondType(), INCHI_BOND_TYPE.DOUBLE);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiBond.getBondStereo()'
	 */
    @Test
	public void testGetBondStereo() {
		JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE, null);
		bond.stereo = INCHI_BOND_STEREO.DOUBLE_EITHER;
		Assert.assertEquals(bond.getBondStereo(), INCHI_BOND_STEREO.DOUBLE_EITHER);
	}

}
