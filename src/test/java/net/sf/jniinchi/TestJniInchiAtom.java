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
		Assert.assertEquals(1.0, atom.x);
		Assert.assertEquals(2.0, atom.y);
		Assert.assertEquals(3.0, atom.z);
		Assert.assertEquals("C", atom.elname);
		
		// Check default values set correctly
		Assert.assertEquals(0, atom.charge);
		Assert.assertEquals(0, atom.implicitH);
		Assert.assertEquals(0, atom.implicitP);
		Assert.assertEquals(0, atom.implicitD);
		Assert.assertEquals(0, atom.implicitT);
		Assert.assertEquals(0, atom.isotopic_mass);
		Assert.assertEquals(INCHI_RADICAL.NONE, atom.radical);
	}

	/**
	 * Test setCharge.
	 * 
	 */
	@Test
	public void testSetCharge() {
		JniInchiAtom atom = getNewTestAtom();
		atom.setCharge(+1);
		Assert.assertEquals(+1, atom.charge);
	}

	/**
	 * Test setRadical.
	 *
	 */
	@Test
	public void testSetRadical() {
		JniInchiAtom atom = getNewTestAtom();
		atom.setRadical(INCHI_RADICAL.DOUBLET);
		Assert.assertEquals(INCHI_RADICAL.DOUBLET, atom.radical);
	}

	/**
	 * Test setIsotopicMass.
	 *
	 */
	@Test
	public void testSetIsotopicMass() {
		JniInchiAtom atom = getNewTestAtom();
		atom.setIsotopicMass(13);
		Assert.assertEquals(13, atom.isotopic_mass);
	}

	/**
	 * Test setIsotopicMassShift.
	 *
	 */
	@Test
	public void testSetIsotopicMassShift() {
		JniInchiAtom atom = getNewTestAtom();
		atom.setIsotopicMassShift(+1);
		Assert.assertEquals(JniInchiAtom.ISOTOPIC_SHIFT_FLAG + 1, atom.isotopic_mass);
	}

	/**
	 * Test setImplicitH.
	 *
	 */
	@Test
	public void testSetImplictH() {
		JniInchiAtom atom = getNewTestAtom();
		atom.setImplicitH(3);
		Assert.assertEquals(3, atom.implicitH);
	}

	/**
	 * Test setImplicitProtium.
	 *
	 */
	@Test
	public void testSetImplictProtium() {
		JniInchiAtom atom = getNewTestAtom();
		atom.setImplicitProtium(2);
		Assert.assertEquals(2, atom.implicitP);
	}

	/**
	 * Test setImplicitDeuterium.
	 *
	 */
	@Test
	public void testSetImplictDeuterium() {
		JniInchiAtom atom = getNewTestAtom();
		atom.setImplicitDeuterium(2);
		Assert.assertEquals(2, atom.implicitD);
	}

	/**
	 * Test setImplicitTritium.
	 *
	 */
	@Test
	public void testSetImplictTritium() {
		JniInchiAtom atom = getNewTestAtom();
		atom.setImplicitTritium(2);
		Assert.assertEquals(2, atom.implicitT);
	}

	/**
	 * Test getElementType.
	 *
	 */
	@Test
	public void testGetElementType() {
		JniInchiAtom atom = getNewTestAtom();
		Assert.assertEquals("C", atom.getElementType());
	}

	/**
	 * Test getCharge.
	 *
	 */
	@Test
	public void testGetCharge() {
		JniInchiAtom atom = getNewTestAtom();
		atom.charge = +1;
		Assert.assertEquals(+1, atom.getCharge());
	}

	/**
	 * Test getRadical.
	 *
	 */
	@Test
	public void testGetRadical() {
		JniInchiAtom atom = getNewTestAtom();
		atom.radical = INCHI_RADICAL.TRIPLET;
		Assert.assertEquals(INCHI_RADICAL.TRIPLET, atom.getRadical());
	}

	/**
	 * Test getX.
	 *
	 */
	@Test
	public void testGetX() {
		JniInchiAtom atom = getNewTestAtom();
		Assert.assertEquals(1.0, atom.getX());
	}

	/**
	 * Test getY.
	 *
	 */
	@Test
	public void testGetY() {
		JniInchiAtom atom = getNewTestAtom();
		Assert.assertEquals(2.0, atom.getY());
	}

	/**
	 * Test getZ.
	 *
	 */
	@Test
	public void testGetZ() {
		JniInchiAtom atom = getNewTestAtom();
		Assert.assertEquals(3.0, atom.getZ());
	}

	/**
	 * Test getImplicitH.
	 *
	 */
	@Test
	public void testGetImplicitH() {
		JniInchiAtom atom = getNewTestAtom();
		Assert.assertEquals(0, atom.getImplicitH());
		atom.implicitH = 3;
		Assert.assertEquals(3, atom.getImplicitH());
	}
	
	/**
	 * Test getImplicitProtium.
	 *
	 */
	@Test
	public void testGetImplicitProtium() {
		JniInchiAtom atom = getNewTestAtom();
		Assert.assertEquals(0, atom.getImplicitProtium());
		atom.implicitP = 2;
		Assert.assertEquals(2, atom.getImplicitProtium());
	}

	/**
	 * Test getImplicitDeuterium.
	 *
	 */
	@Test
	public void testGetImplicitDeuterium() {
		JniInchiAtom atom = getNewTestAtom();
		Assert.assertEquals(0, atom.getImplicitDeuterium());
		atom.implicitD = 2;
		Assert.assertEquals(2, atom.getImplicitDeuterium());
	}

	/**
	 * Test getImplicitTritium.
	 *
	 */
	@Test
	public void testGetImplicitTritium() {
		JniInchiAtom atom = getNewTestAtom();
		Assert.assertEquals(0, atom.getImplicitTritium());
		atom.implicitT = 2;
		Assert.assertEquals(2, atom.getImplicitTritium());
	}
}
