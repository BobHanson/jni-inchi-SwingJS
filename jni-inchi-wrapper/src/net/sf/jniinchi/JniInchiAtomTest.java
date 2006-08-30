package net.sf.jniinchi;

import junit.framework.TestCase;

public class JniInchiAtomTest extends TestCase {

	protected JniInchiAtom getTestAtom() {
		return(new JniInchiAtom(1, 2, 3, "C"));
	}
	
	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.JniInchiAtom(double, double, double, String)'
	 */
	public void testJniInchiAtom() {
		JniInchiAtom atom = new JniInchiAtom(1, 2, 3, "C");
		assertEquals(atom.x, 1.0);
		assertEquals(atom.y, 2.0);
		assertEquals(atom.z, 3.0);
		assertEquals(atom.elname, "C");
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.setCharge(int)'
	 */
	public void testSetCharge() {
		JniInchiAtom atom = getTestAtom();
		atom.setCharge(+1);
		assertEquals(atom.charge, +1);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.setRadical(INCHI_RADICAL)'
	 */
	public void testSetRadical() {
		JniInchiAtom atom = getTestAtom();
		atom.setRadical(INCHI_RADICAL.DOUBLET);
		assertEquals(atom.radical, INCHI_RADICAL.DOUBLET);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.setIsotopicMass(int)'
	 */
	public void testSetIsotopicMass() {
		JniInchiAtom atom = getTestAtom();
		atom.setIsotopicMass(13);
		assertEquals(atom.isotopic_mass, 13);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.setIsotopicMassShift(int)'
	 */
	public void testSetIsotopicMassShift() {
		JniInchiAtom atom = getTestAtom();
		atom.setIsotopicMassShift(+1);
		assertEquals(atom.isotopic_mass, JniInchiAtom.ISOTOPIC_SHIFT_FLAG + 1);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.setImplictH(int)'
	 */
	public void testSetImplictH() {
		JniInchiAtom atom = getTestAtom();
		atom.setImplictH(3);
		assertEquals(atom.implicitH, 3);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.setImplictProtium(int)'
	 */
	public void testSetImplictProtium() {
		JniInchiAtom atom = getTestAtom();
		atom.setImplictProtium(2);
		assertEquals(atom.implicitP, 2);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.setImplictDeuterium(int)'
	 */
	public void testSetImplictDeuterium() {
		JniInchiAtom atom = getTestAtom();
		atom.setImplictDeuterium(2);
		assertEquals(atom.implicitD, 2);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.setImplictTritium(int)'
	 */
	public void testSetImplictTritium() {
		JniInchiAtom atom = getTestAtom();
		atom.setImplictTritium(2);
		assertEquals(atom.implicitT, 2);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.getElementType()'
	 */
	public void testGetElementType() {
		JniInchiAtom atom = getTestAtom();
		assertEquals(atom.getElementType(), "C");
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.getCharge()'
	 */
	public void testGetCharge() {
		JniInchiAtom atom = getTestAtom();
		atom.charge = +1;
		assertEquals(atom.getCharge(), +1);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.getRadical()'
	 */
	public void testGetRadical() {
		JniInchiAtom atom = getTestAtom();
		atom.radical = INCHI_RADICAL.TRIPLET;
		assertEquals(atom.getRadical(), INCHI_RADICAL.TRIPLET);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.getX()'
	 */
	public void testGetX() {
		JniInchiAtom atom = getTestAtom();
		assertEquals(atom.getX(), 1.0);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.getY()'
	 */
	public void testGetY() {
		JniInchiAtom atom = getTestAtom();
		assertEquals(atom.getY(), 2.0);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.getZ()'
	 */
	public void testGetZ() {
		JniInchiAtom atom = getTestAtom();
		assertEquals(atom.getZ(), 3.0);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.getImplicitH()'
	 */
	public void testGetImplicitH() {
		JniInchiAtom atom = getTestAtom();
		atom.implicitH = 3;
		assertEquals(atom.getImplicitH(), 3);
		atom.implicitH = 4;
		assertEquals(atom.getImplicitH(), 4);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.getImplicitProtium()'
	 */
	public void testGetImplicitProtium() {
		JniInchiAtom atom = getTestAtom();
		atom.implicitP = 1;
		assertEquals(atom.getImplicitProtium(), 1);
		atom.implicitP = 2;
		assertEquals(atom.getImplicitProtium(), 2);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.getImplicitDeuterium()'
	 */
	public void testGetImplicitDeuterium() {
		JniInchiAtom atom = getTestAtom();
		atom.implicitD = 1;
		assertEquals(atom.getImplicitDeuterium(), 1);
		atom.implicitD = 2;
		assertEquals(atom.getImplicitDeuterium(), 2);
	}

	/*
	 * Test method for 'net.sf.jniinchi.JniInchiAtom.getImplicitTritium()'
	 */
	public void testGetImplicitTritium() {
		JniInchiAtom atom = getTestAtom();
		atom.implicitT = 1;
		assertEquals(atom.getImplicitTritium(), 1);
		atom.implicitT = 2;
		assertEquals(atom.getImplicitTritium(), 2);
	}

}
