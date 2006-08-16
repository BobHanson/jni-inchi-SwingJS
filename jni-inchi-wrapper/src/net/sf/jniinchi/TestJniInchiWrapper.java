/* File: TestJniInchi.java
 * Author: Sam Adams
 * 
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi;

/**
 * Provides test of library's functionality.
 * @author Sam Adams
 */
public class TestJniInchiWrapper {

    /**
     * Runs test.
     */
    public static void test() throws JniInchiException {
        
    	JniInchiInput input = new JniInchiInput("");
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
        
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        
        System.out.println(output.getReturnStatus());
        System.out.println(output.getInchi());
        System.out.println(output.getAuxInfo());
        System.out.println(output.getMessage());
        System.out.println(output.getLog());
    }
    
    /**
     * Runs test.
     */
    public static void main(String[] args) throws Throwable {
    	test();
    }
}
