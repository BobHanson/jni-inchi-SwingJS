/*
 *  JNI InChI Wrapper - A Java Native Interface Wrapper for InChI.
 *  Copyright 2006, 2007, 2008 Sam Adams <sea36 at users.sourceforge.net>
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301 USA
 * or see <http://www.gnu.org/licenses/>.
 */
package net.sf.jniinchi;
import org.junit.Assert;
import org.junit.Test;

public class TestJniInchiAtom {

    protected static JniInchiAtom getNewTestAtom() {
        return new JniInchiAtom(1, 2, 3, "C");
    }


    /**
     * Test JniInchiAtom constructor.
     *
     */
    @Test
    public void testJniInchiAtomConstructor() {
        JniInchiAtom atom = getNewTestAtom();
        // Check configured parameters
        Assert.assertEquals(1.0, atom.getX(), 1E-6);
        Assert.assertEquals(2.0, atom.getY(), 1E-6);
        Assert.assertEquals(3.0, atom.getZ(), 1E-6);
        Assert.assertEquals("C", atom.getElementType());

        // Check default values set correctly
        Assert.assertEquals(0, atom.getCharge());
        Assert.assertEquals(0, atom.getImplicitH());
        Assert.assertEquals(0, atom.getImplicitProtium());
        Assert.assertEquals(0, atom.getImplicitDeuterium());
        Assert.assertEquals(0, atom.getImplicitTritium());
        Assert.assertEquals(0, atom.getIsotopicMass());
        Assert.assertEquals(INCHI_RADICAL.NONE, atom.getRadical());
    }

    /**
     * Test setCharge.
     *
     */
    @Test
    public void testSetCharge() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setCharge(+1);
        Assert.assertEquals(+1, atom.getCharge());
    }

    /**
     * Test setRadical.
     *
     */
    @Test
    public void testSetRadical() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setRadical(INCHI_RADICAL.DOUBLET);
        Assert.assertEquals(INCHI_RADICAL.DOUBLET, atom.getRadical());
    }

    /**
     * Test setIsotopicMass.
     *
     */
    @Test
    public void testSetIsotopicMass() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setIsotopicMass(13);
        Assert.assertEquals(13, atom.getIsotopicMass());
    }

    /**
     * Test setIsotopicMassShift.
     *
     */
    @Test
    public void testSetIsotopicMassShift() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setIsotopicMassShift(+1);
        Assert.assertEquals(JniInchiAtom.ISOTOPIC_SHIFT_FLAG + 1, atom.getIsotopicMass());
    }

    /**
     * Test setImplicitH.
     *
     */
    @Test
    public void testSetImplictH() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setImplicitH(3);
        Assert.assertEquals(3, atom.getImplicitH());
    }

    /**
     * Test setImplicitProtium.
     *
     */
    @Test
    public void testSetImplictProtium() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setImplicitProtium(2);
        Assert.assertEquals(2, atom.getImplicitProtium());
    }

    /**
     * Test setImplicitDeuterium.
     *
     */
    @Test
    public void testSetImplictDeuterium() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setImplicitDeuterium(2);
        Assert.assertEquals(2, atom.getImplicitDeuterium());
    }

    /**
     * Test setImplicitTritium.
     *
     */
    @Test
    public void testSetImplictTritium() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setImplicitTritium(2);
        Assert.assertEquals(2, atom.getImplicitTritium());
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
        atom.setCharge(+1);
        Assert.assertEquals(+1, atom.getCharge());
    }

    /**
     * Test getRadical.
     *
     */
    @Test
    public void testGetRadical() {
        JniInchiAtom atom = getNewTestAtom();
        atom.setRadical(INCHI_RADICAL.TRIPLET);
        Assert.assertEquals(INCHI_RADICAL.TRIPLET, atom.getRadical());
    }

    /**
     * Test getX.
     *
     */
    @Test
    public void testGetX() {
        JniInchiAtom atom = getNewTestAtom();
        Assert.assertEquals(1.0, atom.getX(), 1E-6);
    }

    /**
     * Test getY.
     *
     */
    @Test
    public void testGetY() {
        JniInchiAtom atom = getNewTestAtom();
        Assert.assertEquals(2.0, atom.getY(), 1E-6);
    }

    /**
     * Test getZ.
     *
     */
    @Test
    public void testGetZ() {
        JniInchiAtom atom = getNewTestAtom();
        Assert.assertEquals(3.0, atom.getZ(), 1E-6);
    }

    /**
     * Test getImplicitH.
     *
     */
    @Test
    public void testGetImplicitH() {
        JniInchiAtom atom = getNewTestAtom();
        Assert.assertEquals(0, atom.getImplicitH());
        atom.setImplicitH(3);
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
        atom.setImplicitProtium(2);
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
        atom.setImplicitDeuterium(2);
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
        atom.setImplicitTritium(2);
        Assert.assertEquals(2, atom.getImplicitTritium());
    }
}
