package net.sf.jniinchi;

import org.junit.Assert;
import org.junit.Test;

public class TestJniInchiAtom {

	protected static JniInchiAtom getNewTestAtom() {
		return(new JniInchiAtom(1, 2, 3, "C"));
	}
	
	
	/**
	 * Test JniInchiAtom constructor.
	 * 
	 */
	@Test
	public void testJniInchiAtomConstructor() {
		JniInchiAtom atom = getNewTestAtom();
		// Check configured parameters
		Assert.assertEquals(atom.x, 1.0);
		Assert.assertEquals(atom.y, 2.0);
		Assert.assertEquals(atom.z, 3.0);
		Assert.assertEquals(atom.elname, "C");
		
		// Check default values set correctly
		Assert.assertEquals(atom.charge, 0);
		Assert.assertEquals(atom.implicitH, 0);
		Assert.assertEquals(atom.implicitP, 0);
		Assert.assertEquals(atom.implicitD, 0);
		Assert.assertEquals(atom.implicitT, 0);
		Assert.assertEquals(atom.isotopic_mass, 0);
		Assert.assertEquals(atom.radical, INCHI_RADICAL.NONE);
	}

	/**
	 * Test setCharge.
	 * 
	 */
	@Test
	public void testSetCharge() {
		JniInchiAtom atom = getNewTestAtom();
		atom.setCharge(+1);
		Assert.assertEquals(atom.charge, +1);
	}

	/**
	 * Test setRadical.
	 *
	 */
	@Test
	public void testSetRadical() {
		JniInchiAtom atom = getNewTestAtom();
		atom.setRadical(INCHI_RADICAL.DOUBLET);
		Assert.assertEquals(atom.radical, INCHI_RADICAL.DOUBLET);
	}

	/**
	 * Test setIsotopicMass.
	 *
	 */
	@Test
	public void testSetIsotopicMass() {
		JniInchiAtom atom = getNewTestAtom();
		atom.setIsotopicMass(13);
		Assert.assertEquals(atom.isotopic_mass, 13);
	}

	/**
	 * Test setIsotopicMassShift.
	 *
	 */
	@Test
	public void testSetIsotopicMassShift() {
		JniInchiAtom atom = getNewTestAtom();
		atom.setIsotopicMassShift(+1);
		Assert.assertEquals(atom.isotopic_mass, JniInchiAtom.ISOTOPIC_SHIFT_FLAG + 1);
	}

	/**
	 * Test setImplicitH.
	 *
	 */
	@Test
	public void testSetImplictH() {
		JniInchiAtom atom = getNewTestAtom();
		atom.setImplicitH(3);
		Assert.assertEquals(atom.implicitH, 3);
	}

	/**
	 * Test setImplicitProtium.
	 *
	 */
	@Test
	public void testSetImplictProtium() {
		JniInchiAtom atom = getNewTestAtom();
		atom.setImplicitProtium(2);
		Assert.assertEquals(atom.implicitP, 2);
	}

	/**
	 * Test setImplicitDeuterium.
	 *
	 */
	@Test
	public void testSetImplictDeuterium() {
		JniInchiAtom atom = getNewTestAtom();
		atom.setImplicitDeuterium(2);
		Assert.assertEquals(atom.implicitD, 2);
	}

	/**
	 * Test setImplicitTritium.
	 *
	 */
	@Test
	public void testSetImplictTritium() {
		JniInchiAtom atom = getNewTestAtom();
		atom.setImplicitTritium(2);
		Assert.assertEquals(atom.implicitT, 2);
	}

	/**
	 * Test getElementType.
	 *
	 */
	@Test
	public void testGetElementType() {
		JniInchiAtom atom = getNewTestAtom();
		Assert.assertEquals(atom.getElementType(), "C");
	}

	/**
	 * Test getCharge.
	 *
	 */
	@Test
	public void testGetCharge() {
		JniInchiAtom atom = getNewTestAtom();
		atom.charge = +1;
		Assert.assertEquals(atom.getCharge(), +1);
	}

	/**
	 * Test getRadical.
	 *
	 */
	@Test
	public void testGetRadical() {
		JniInchiAtom atom = getNewTestAtom();
		atom.radical = INCHI_RADICAL.TRIPLET;
		Assert.assertEquals(atom.getRadical(), INCHI_RADICAL.TRIPLET);
	}

	/**
	 * Test getX.
	 *
	 */
	@Test
	public void testGetX() {
		JniInchiAtom atom = getNewTestAtom();
		Assert.assertEquals(atom.getX(), 1.0);
	}

	/**
	 * Test getY.
	 *
	 */
	@Test
	public void testGetY() {
		JniInchiAtom atom = getNewTestAtom();
		Assert.assertEquals(atom.getY(), 2.0);
	}

	/**
	 * Test getZ.
	 *
	 */
	@Test
	public void testGetZ() {
		JniInchiAtom atom = getNewTestAtom();
		Assert.assertEquals(atom.getZ(), 3.0);
	}

	/**
	 * Test getImplicitH.
	 *
	 */
	@Test
	public void testGetImplicitH() {
		JniInchiAtom atom = getNewTestAtom();
		Assert.assertEquals(atom.getImplicitH(), 0);
		atom.implicitH = 3;
		Assert.assertEquals(atom.getImplicitH(), 3);
	}
	
	/**
	 * Test getImplicitProtium.
	 *
	 */
	@Test
	public void testGetImplicitProtium() {
		JniInchiAtom atom = getNewTestAtom();
		Assert.assertEquals(atom.getImplicitProtium(), 0);
		atom.implicitP = 2;
		Assert.assertEquals(atom.getImplicitProtium(), 2);
	}

	/**
	 * Test getImplicitDeuterium.
	 *
	 */
	@Test
	public void testGetImplicitDeuterium() {
		JniInchiAtom atom = getNewTestAtom();
		Assert.assertEquals(atom.getImplicitDeuterium(), 0);
		atom.implicitD = 2;
		Assert.assertEquals(atom.getImplicitDeuterium(), 2);
	}

	/**
	 * Test getImplicitTritium.
	 *
	 */
	@Test
	public void testGetImplicitTritium() {
		JniInchiAtom atom = getNewTestAtom();
		Assert.assertEquals(atom.getImplicitTritium(), 0);
		atom.implicitT = 2;
		Assert.assertEquals(atom.getImplicitTritium(), 2);
	}
}
