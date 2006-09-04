package net.sf.jniinchi;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class TestJniInchiWrapper extends TestCase {
    
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
    
    protected static JniInchiInput getPropanolInput() {
        JniInchiInput input = new JniInchiInput();
        JniInchiAtom a1 = new JniInchiAtom(0.419000, -0.193000, 0.246000, "O");
        JniInchiAtom a2 = new JniInchiAtom(0.753000, 0.647000, 0.603000, "H");
        JniInchiAtom a3 = new JniInchiAtom(-0.965000, -0.011000, -0.023000, "C");
        JniInchiAtom a4 = new JniInchiAtom(-1.468000, 0.262000, 0.910000, "H");
        JniInchiAtom a5 = new JniInchiAtom(-1.075000, 0.808000, -0.740000, "H");
        JniInchiAtom a6 = new JniInchiAtom(-1.531000, -1.306000, -0.586000, "C");
        JniInchiAtom a7 = new JniInchiAtom(-1.366000, -2.121000, 0.129000, "H");
        JniInchiAtom a8 = new JniInchiAtom(-0.980000, -1.585000, -1.492000, "H");
        JniInchiAtom a9 = new JniInchiAtom(-3.012000, -1.187000, -0.898000, "C");
        JniInchiAtom a10 = new JniInchiAtom(-3.194000, -0.403000, -1.641000, "H");
        JniInchiAtom a11 = new JniInchiAtom(-3.585000, -0.946000, 0.003000, "H");
        JniInchiAtom a12 = new JniInchiAtom(-3.392000, -2.131000, -1.301000, "H");
        input.addAtom(a1);
        input.addAtom(a2);
        input.addAtom(a3);
        input.addAtom(a4);
        input.addAtom(a5);
        input.addAtom(a6);
        input.addAtom(a7);
        input.addAtom(a8);
        input.addAtom(a9);
        input.addAtom(a10);
        input.addAtom(a11);
        input.addAtom(a12);
        
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a3, a5, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a3, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a3, a6, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a6, a8, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a6, a7, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a6, a9, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a9, a12, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a9, a11, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a9, a10, INCHI_BOND_TYPE.SINGLE));
        
        return(input);
    }
    
    
    protected static JniInchiInput getRAlanineInput(String options) throws JniInchiException {
    	JniInchiInput input = new JniInchiInput(options);
    	
    	JniInchiAtom a1 = new JniInchiAtom(0.358, 0.819, 20.655, "C");
    	JniInchiAtom a2 = new JniInchiAtom(1.598, -0.032, 20.905, "C");
    	JniInchiAtom a3 = new JniInchiAtom(0.275, 2.014, 21.574, "N");
    	JniInchiAtom a4 = new JniInchiAtom(-0.952, 0.043, 20.838, "C");
    	JniInchiAtom a5 = new JniInchiAtom(2.678, 0.479, 21.093, "O");
    	JniInchiAtom a6 = new JniInchiAtom(1.596, -1.239, 20.958, "O");
    	JniInchiAtom a7 = new JniInchiAtom(0.433, 1.199, 19.609, "H");
    	JniInchiAtom a8 = new JniInchiAtom(-0.502, 2.634, 21.343, "H");
    	JniInchiAtom a9 = new JniInchiAtom(0.166, 1.759, 22.557, "H");
    	JniInchiAtom a10 = new JniInchiAtom(1.120, 2.587, 21.523, "H");
    	JniInchiAtom a11 = new JniInchiAtom(-1.041, -0.347, 21.878, "H");
    	JniInchiAtom a12 = new JniInchiAtom(-1.005, -0.824, 20.140, "H");
    	JniInchiAtom a13 = new JniInchiAtom(-1.837, 0.690, 20.637, "H");
    	
    	input.addAtom(a1);
    	input.addAtom(a2);
    	input.addAtom(a3);
    	input.addAtom(a4);
    	input.addAtom(a5);
    	input.addAtom(a6);
    	input.addAtom(a7);
    	input.addAtom(a8);
    	input.addAtom(a9);
    	input.addAtom(a10);
    	input.addAtom(a11);
    	input.addAtom(a12);
    	input.addAtom(a13);
    	
    	input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a1, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a3, a1, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a4, a1, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a7, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a7, a1, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a5, a2, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a6, a2, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a3, a8, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a8, a3, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a3, a9, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a9, a3, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a3, a10, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a10, a3, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a4, a11, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a11, a4, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a4, a12, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a12, a4, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a4, a13, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a13, a4, INCHI_BOND_TYPE.SINGLE));
    	
    	return(input);
    }
    
    
    protected static JniInchiInput getLAlanineInput(String options) throws JniInchiException {
    	JniInchiInput input = new JniInchiInput(options);
    	
    	JniInchiAtom a1 = new JniInchiAtom(-0.358, 0.819, 20.655, "C");
    	JniInchiAtom a2 = new JniInchiAtom(-1.598, -0.032, 20.905, "C");
    	JniInchiAtom a3 = new JniInchiAtom(-0.275, 2.014, 21.574, "N");
    	JniInchiAtom a4 = new JniInchiAtom(0.952, 0.043, 20.838, "C");
    	JniInchiAtom a5 = new JniInchiAtom(-2.678, 0.479, 21.093, "O");
    	JniInchiAtom a6 = new JniInchiAtom(-1.596, -1.239, 20.958, "O");
    	JniInchiAtom a7 = new JniInchiAtom(-0.433, 1.199, 19.609, "H");
    	JniInchiAtom a8 = new JniInchiAtom(0.502, 2.634, 21.343, "H");
    	JniInchiAtom a9 = new JniInchiAtom(-0.166, 1.759, 22.557, "H");
    	JniInchiAtom a10 = new JniInchiAtom(-1.120, 2.587, 21.523, "H");
    	JniInchiAtom a11 = new JniInchiAtom(1.041, -0.347, 21.878, "H");
    	JniInchiAtom a12 = new JniInchiAtom(1.005, -0.824, 20.140, "H");
    	JniInchiAtom a13 = new JniInchiAtom(1.837, 0.690, 20.637, "H");
    	
    	input.addAtom(a1);
    	input.addAtom(a2);
    	input.addAtom(a3);
    	input.addAtom(a4);
    	input.addAtom(a5);
    	input.addAtom(a6);
    	input.addAtom(a7);
    	input.addAtom(a8);
    	input.addAtom(a9);
    	input.addAtom(a10);
    	input.addAtom(a11);
    	input.addAtom(a12);
    	input.addAtom(a13);
    	
    	input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a1, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a3, a1, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a4, a1, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a7, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a7, a1, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a5, a2, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a6, a2, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a3, a8, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a8, a3, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a3, a9, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a9, a3, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a3, a10, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a10, a3, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a4, a11, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a11, a4, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a4, a12, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a12, a4, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a4, a13, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a13, a4, INCHI_BOND_TYPE.SINGLE));
    	
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
        
        input = getPropanolInput();
        output = JniInchiWrapper.getInchi(input);
        assertEquals(output.getInchi(), "InChI=1/C3H8O/c1-2-3-4/h4H,2-3H2,1H3");
        assertEquals(output.getAuxInfo(), "AuxInfo=1/0/N:9,6,3,1/rA:12OHCHHCHHCHHH/rB:s1;s1;s3;s3;s3;s6;s6;s6;s9;s9;s9;/rC:.419,-.193,.246;.753,.647,.603;-.965,-.011,-.023;-1.468,.262,.91;-1.075,.808,-.74;-1.531,-1.306,-.586;-1.366,-2.121,.129;-.98,-1.585,-1.492;-3.012,-1.187,-.898;-3.194,-.403,-1.641;-3.585,-.946,.003;-3.392,-2.131,-1.301;");
        
        input = getLAlanineInput("");
        output = JniInchiWrapper.getInchi(input);
        assertEquals(output.getInchi(), "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,1,4H3/t2-/m0/s1");
        
        input = getRAlanineInput("");
        output = JniInchiWrapper.getInchi(input);
        assertEquals(output.getInchi(), "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,1,4H3/t2-/m1/s1");
        
        input = getLAlanineInput("-compress");
        output = JniInchiWrapper.getInchi(input);
        assertEquals(output.getInchi(), "InChI=1/C3H7NO2/cABBCC/hB1A3D3/tB1/m0/s1");
        
        input = getLAlanineInput("/CoMpReSS");
        output = JniInchiWrapper.getInchi(input);
        assertEquals(output.getInchi(), "InChI=1/C3H7NO2/cABBCC/hB1A3D3/tB1/m0/s1");
        
        input = getLAlanineInput("-SNon");
        output = JniInchiWrapper.getInchi(input);
        assertEquals(output.getInchi(), "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,1,4H3");
        
        input = getLAlanineInput("-SAbs");
        output = JniInchiWrapper.getInchi(input);
        assertEquals(output.getInchi(), "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,1,4H3/t2-/m0/s1");
        
        input = getLAlanineInput("-SRac");
        output = JniInchiWrapper.getInchi(input);
        assertEquals(output.getInchi(), "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,1,4H3/t2-/s3");
        
        input = getRAlanineInput("-SRac");
        output = JniInchiWrapper.getInchi(input);
        assertEquals(output.getInchi(), "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,1,4H3/t2-/s3");
        
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
