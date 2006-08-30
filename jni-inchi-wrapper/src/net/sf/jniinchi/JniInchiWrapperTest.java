package net.sf.jniinchi;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class JniInchiWrapperTest extends TestCase {
    
    protected static JniInchiInput getBenzeneInput() {
        JniInchiInput input = new JniInchiInput();
        JniInchiAtom a1 = new JniInchiAtom(-0.010, 0.000, 0.000, "C");
        JniInchiAtom a2 = new JniInchiAtom(-0.707, 1.208, 0.000, "C");
        JniInchiAtom a3 = new JniInchiAtom(-2.102, 1.208, 0.000, "C");
        JniInchiAtom a4 = new JniInchiAtom(-2.799, 0.000, 0.000, "C");
        JniInchiAtom a5 = new JniInchiAtom(-2.102, -1.208, 0.000, "C");
        JniInchiAtom a6 = new JniInchiAtom(-0.707, -1.208, 0.000, "C");
        a1.setImplicitH(1);
        a2.setImplicitH(1);
        a3.setImplicitH(1);
        a4.setImplicitH(1);
        a5.setImplicitH(1);
        a6.setImplicitH(1);
        input.addAtom(a1);
        input.addAtom(a2);
        input.addAtom(a3);
        input.addAtom(a4);
        input.addAtom(a5);
        input.addAtom(a6);
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a3, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a3, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a4, a5, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a5, a6, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a6, a1, INCHI_BOND_TYPE.DOUBLE));
        
        return(input);
    }
    

    /*
     * Test method for 'net.sf.jniinchi.JniInchiWrapper.loadLibrary()'
     */
    public void testLoadLibrary() throws JniInchiException {
        JniInchiWrapper.loadLibrary();
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiWrapper.JniInchiWrapper()'
     */
    public void testJniInchiWrapper() throws JniInchiException {
        JniInchiWrapper wrapper = new JniInchiWrapper();
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiWrapper.checkOptions(List)'
     */
    public void testCheckOptionsList() throws JniInchiException {
        List opList = new ArrayList();
        opList.add(INCHI_OPTION.Compress);
        opList.add(INCHI_OPTION.SNon);
        String options = JniInchiWrapper.checkOptions(opList);
        String flag = JniInchiWrapper.flagChar;
        assertEquals(options, flag + "Compress " + flag + "SNon ");
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiWrapper.checkOptions(String)'
     */
    public void testCheckOptionsString() throws JniInchiException {
        String options = JniInchiWrapper.checkOptions("  -ComPreSS  /SNon");
        String flag = JniInchiWrapper.flagChar;
        assertEquals(options, flag + "Compress " + flag + "SNon ");
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiWrapper.getInchi(JniInchiInput)'
     */
    public void testGetInchi() throws JniInchiException {
        JniInchiInput input = getBenzeneInput();
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        assertEquals(output.getReturnStatus(), INCHI_RET.OKAY);
        assertEquals(output.getInchi(), "InChI=1/C6H6/c1-2-4-6-5-3-1/h1-6H");
        assertEquals(output.getAuxInfo(), "AuxInfo=1/0/N:1,2,6,3,5,4/E:(1,2,3,4,5,6)/rA:6CCCCCC/rB:s1;d2;s3;d4;d1s5;/rC:-.01,0,0;-.707,1.208,0;-2.102,1.208,0;-2.799,0,0;-2.102,-1.208,0;-.707,-1.208,0;");
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiWrapper.getStructureFromInchi(JniInchiInputInchi)'
     */
    public void testGetStructureFromInchi() throws JniInchiException {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/C6H6/c1-2-4-6-5-3-1/h1-6H");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        assertEquals(output.getReturnStatus(), INCHI_RET.OKAY);
        assertEquals(output.getNumAtoms(), 6);
        assertEquals(output.getAtom(0).getElementType(), "C");
        assertEquals(output.getAtom(1).getElementType(), "C");
        assertEquals(output.getAtom(2).getElementType(), "C");
        assertEquals(output.getAtom(3).getElementType(), "C");
        assertEquals(output.getAtom(4).getElementType(), "C");
        assertEquals(output.getAtom(5).getElementType(), "C");
        assertEquals(output.getNumBonds(), 6);
        assertEquals(output.getBond(0).getBondType(), INCHI_BOND_TYPE.DOUBLE);
        assertEquals(output.getBond(1).getBondType(), INCHI_BOND_TYPE.SINGLE);
        assertEquals(output.getBond(2).getBondType(), INCHI_BOND_TYPE.SINGLE);
        assertEquals(output.getBond(3).getBondType(), INCHI_BOND_TYPE.DOUBLE);
        assertEquals(output.getBond(4).getBondType(), INCHI_BOND_TYPE.DOUBLE);
        assertEquals(output.getBond(5).getBondType(), INCHI_BOND_TYPE.SINGLE);
    }
}
