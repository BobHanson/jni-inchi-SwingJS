package net.sf.jniinchi;

import junit.framework.TestCase;

public class TestJniInchiBond extends TestCase {
	
	protected static JniInchiAtom atO = new JniInchiAtom(0, 0, 0, "O");
	protected static JniInchiAtom atT = new JniInchiAtom(1.21, 0, 0, "O");
    
    protected static JniInchiBond getTestBond() {
        return(new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE));
    }
	
	/*
	 * Test method for 'net.sf.jniinchi.JniInchiBond.JniInchiBond(JniInchiAtom, JniInchiAtom, INCHI_BOND_TYPE, INCHI_BOND_STEREO)'
	 */
	public void testJniInchiBondJniInchiAtomJniInchiAtomINCHI_BOND_TYPEINCHI_BOND_STEREO() {
		JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE, INCHI_BOND_STEREO.DOUBLE_EITHER);
		assertEquals(bond.atomOrigin, atO);
		assertEquals(bond.atomTarget, atT);
		assertEquals(bond.type, INCHI_BOND_TYPE.DOUBLE);
		assertEquals(bond.stereo, INCHI_BOND_STEREO.DOUBLE_EITHER);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiBond.JniInchiBond(JniInchiAtom, JniInchiAtom, INCHI_BOND_TYPE)'
	 */
	public void testJniInchiBondJniInchiAtomJniInchiAtomINCHI_BOND_TYPE() {
		JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE);
		assertEquals(bond.atomOrigin, atO);
		assertEquals(bond.atomTarget, atT);
		assertEquals(bond.type, INCHI_BOND_TYPE.DOUBLE);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiBond.setStereoDefinition(INCHI_BOND_STEREO)'
	 */
	public void testSetStereoDefinition() {
		JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE, null);
		bond.setStereoDefinition(INCHI_BOND_STEREO.DOUBLE_EITHER);
		assertEquals(bond.stereo, INCHI_BOND_STEREO.DOUBLE_EITHER);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiBond.getOriginAtom()'
	 */
	public void testGetOriginAtom() {
		JniInchiBond bond = new JniInchiBond(null, null, INCHI_BOND_TYPE.DOUBLE);
		bond.atomOrigin = atO;
		assertEquals(bond.getOriginAtom(), atO);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiBond.getTargetAtom()'
	 */
	public void testGetTargetAtom() {
		JniInchiBond bond = new JniInchiBond(null, null, INCHI_BOND_TYPE.DOUBLE);
		bond.atomTarget = atT;
		assertEquals(bond.getTargetAtom(), atT);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiBond.getBondType()'
	 */
	public void testGetBondType() {
		JniInchiBond bond = new JniInchiBond(atO, atT, null);
		bond.type = INCHI_BOND_TYPE.DOUBLE;
		assertEquals(bond.getBondType(), INCHI_BOND_TYPE.DOUBLE);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiBond.getBondStereo()'
	 */
	public void testGetBondStereo() {
		JniInchiBond bond = new JniInchiBond(atO, atT, INCHI_BOND_TYPE.DOUBLE, null);
		bond.stereo = INCHI_BOND_STEREO.DOUBLE_EITHER;
		assertEquals(bond.getBondStereo(), INCHI_BOND_STEREO.DOUBLE_EITHER);
	}

}
