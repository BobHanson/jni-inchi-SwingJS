package net.sf.jniinchi;

public class Main {
	
	public static void main(String[] args) throws Exception {
		JniInchiWrapper jiw = new JniInchiWrapper();
		
		// L-Alanine
		JniInchiInput input = new JniInchiInput();
    	JniInchiAtom a1 = input.addAtom(new JniInchiAtom(264.0, 968.0, 0.0, "C"));
    	JniInchiAtom a2 = input.addAtom(new JniInchiAtom(295.0, 985.0, 0.0, "C"));
    	JniInchiAtom a3 = input.addAtom(new JniInchiAtom(233.0, 986.0, 0.0, "N"));
    	JniInchiAtom a4 = input.addAtom(new JniInchiAtom(264.0, 932.0, 0.0, "C"));
    	JniInchiAtom a5 = input.addAtom(new JniInchiAtom(326.0, 967.0, 0.0, "O"));
    	JniInchiAtom a6 = input.addAtom(new JniInchiAtom(295.0, 1021.0, 0.0, "O"));
    	a1.setImplicitH(1);
    	a3.setImplicitH(2);
    	a4.setImplicitH(3);
    	a5.setImplicitH(1);
    	input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE)).setStereoDefinition(INCHI_BOND_STEREO.SINGLE_1DOWN);
    	input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));
		
		JniInchiOutput output = jiw.getInchi(input);
		
		System.out.println("InChI generated: " + output.getInchi());
	}
}
