package net.sf.jniinchi;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestJniInchiWrapper {
	
	protected static final String ALANINE_INCHI = "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)";
	protected static final String LALANINE_INCHI = "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1";
	protected static final String DALANINE_INCHI = "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m1/s1";
	
	
    
    protected static JniInchiInput get0DBenzeneBondsSingleDoubleInput(String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);
        
        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a5 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        a1.setImplicitH(1);
        a2.setImplicitH(1);
        a3.setImplicitH(1);
        a4.setImplicitH(1);
        a5.setImplicitH(1);
        a6.setImplicitH(1);
        
        // Add bonds
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a3, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a3, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a4, a5, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a5, a6, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a6, a1, INCHI_BOND_TYPE.DOUBLE));
        
        return(input);
    }
    
    protected static JniInchiInput get0DBenzeneBondsDoubleSingleInput(String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);
        
        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a5 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(0.000, 0.000, 0.000, "C"));
        a1.setImplicitH(1);
        a2.setImplicitH(1);
        a3.setImplicitH(1);
        a4.setImplicitH(1);
        a5.setImplicitH(1);
        a6.setImplicitH(1);
        
        // Add bonds
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a2, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a3, a4, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a4, a5, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a5, a6, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a6, a1, INCHI_BOND_TYPE.SINGLE));
        
        return(input);
    }
    
    protected static JniInchiInput get2DBenzeneInput(String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);
        
        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(-0.010, 0.000, 0.000, "C"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(-0.707, 1.208, 0.000, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(-2.102, 1.208, 0.000, "C"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(-2.799, 0.000, 0.000, "C"));
        JniInchiAtom a5 = input.addAtom(new JniInchiAtom(-2.102, -1.208, 0.000, "C"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(-0.707, -1.208, 0.000, "C"));
        a1.setImplicitH(1);
        a2.setImplicitH(1);
        a3.setImplicitH(1);
        a4.setImplicitH(1);
        a5.setImplicitH(1);
        a6.setImplicitH(1);
        
        // Add bonds
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a3, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a3, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a4, a5, INCHI_BOND_TYPE.DOUBLE));
        input.addBond(new JniInchiBond(a5, a6, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a6, a1, INCHI_BOND_TYPE.DOUBLE));
        
        return(input);
    }
    
    protected static JniInchiInput getPropanolExplicitHydrogenInput(String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);
        
        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.419000, -0.193000, 0.246000, "O"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(-0.965000, -0.011000, -0.023000, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(-1.531000, -1.306000, -0.586000, "C"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(-3.012000, -1.187000, -0.898000, "C"));
        JniInchiAtom a5 = input.addAtom(new JniInchiAtom(0.753000, 0.647000, 0.603000, "H"));
        JniInchiAtom a6 = input.addAtom(new JniInchiAtom(-1.468000, 0.262000, 0.910000, "H"));
        JniInchiAtom a7 = input.addAtom(new JniInchiAtom(-1.075000, 0.808000, -0.740000, "H"));
        JniInchiAtom a8 = input.addAtom(new JniInchiAtom(-1.366000, -2.121000, 0.129000, "H"));
        JniInchiAtom a9 = input.addAtom(new JniInchiAtom(-0.980000, -1.585000, -1.492000, "H"));
        JniInchiAtom a10 = input.addAtom(new JniInchiAtom(-3.194000, -0.403000, -1.641000, "H"));
        JniInchiAtom a11 = input.addAtom(new JniInchiAtom(-3.585000, -0.946000, 0.003000, "H"));
        JniInchiAtom a12 = input.addAtom(new JniInchiAtom(-3.392000, -2.131000, -1.301000, "H"));
        
        // Add bonds
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a3, a4, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a1, a5, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a7, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a3, a9, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a3, a8, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a4, a12, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a4, a11, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a4, a10, INCHI_BOND_TYPE.SINGLE));
        
        return(input);
    }
    
    protected static JniInchiInput getPropanolImplicitHydrogenInput(String options) throws JniInchiException {
        JniInchiInput input = new JniInchiInput(options);
        
        // Generate atoms
        JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.419000, -0.193000, 0.246000, "O"));
        JniInchiAtom a2 = input.addAtom(new JniInchiAtom(-0.965000, -0.011000, -0.023000, "C"));
        JniInchiAtom a3 = input.addAtom(new JniInchiAtom(-1.531000, -1.306000, -0.586000, "C"));
        JniInchiAtom a4 = input.addAtom(new JniInchiAtom(-3.012000, -1.187000, -0.898000, "C"));
        a1.setImplicitH(1);
        a2.setImplicitH(2);
        a3.setImplicitH(2);
        a4.setImplicitH(3);
        
        // Add bonds
        input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a2, a3, INCHI_BOND_TYPE.SINGLE));
        input.addBond(new JniInchiBond(a3, a4, INCHI_BOND_TYPE.SINGLE));
        
        return(input);
    }
    

    protected static JniInchiInput get3DLAlanineInput(String options) throws JniInchiException {
    	JniInchiInput input = new JniInchiInput(options);
    	
    	// Generate atoms
    	JniInchiAtom a1 = input.addAtom(new JniInchiAtom(-0.358, 0.819, 20.655, "C"));
    	JniInchiAtom a2 = input.addAtom(new JniInchiAtom(-1.598, -0.032, 20.905, "C"));
    	JniInchiAtom a3 = input.addAtom(new JniInchiAtom(-0.275, 2.014, 21.574, "N"));
    	JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.952, 0.043, 20.838, "C"));
    	JniInchiAtom a5 = input.addAtom(new JniInchiAtom(-2.678, 0.479, 21.093, "O"));
    	JniInchiAtom a6 = input.addAtom(new JniInchiAtom(-1.596, -1.239, 20.958, "O"));
    	
    	a1.setImplicitH(1);
    	a3.setImplicitH(2);
    	a4.setImplicitH(3);
    	a5.setImplicitH(1);
    	
    	// Add bonds
    	input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));
    	
    	return(input);
    }
    

    protected static JniInchiInput get3DLAlanineZwiterionInput(String options) throws JniInchiException {
    	JniInchiInput input = new JniInchiInput(options);
    	
    	// Generate atoms
    	JniInchiAtom a1 = input.addAtom(new JniInchiAtom(-0.358, 0.819, 20.655, "C"));
    	JniInchiAtom a2 = input.addAtom(new JniInchiAtom(-1.598, -0.032, 20.905, "C"));
    	JniInchiAtom a3 = input.addAtom(new JniInchiAtom(-0.275, 2.014, 21.574, "N"));
    	JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.952, 0.043, 20.838, "C"));
    	JniInchiAtom a5 = input.addAtom(new JniInchiAtom(-2.678, 0.479, 21.093, "O"));
    	JniInchiAtom a6 = input.addAtom(new JniInchiAtom(-1.596, -1.239, 20.958, "O"));
    	
    	a1.setImplicitH(1);
    	a3.setImplicitH(3);
    	a4.setImplicitH(3);
    	
    	a3.setCharge(+1);
    	a5.setCharge(-1);
    	
    	// Add bonds
    	input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));
    	
    	return(input);
    }
    
    protected static JniInchiInput get2DLAlanineInput(String options) throws JniInchiException {
    	JniInchiInput input = new JniInchiInput(options);
    	
    	// Generate atoms
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
    	
    	// Add bonds
    	input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE)).setStereoDefinition(INCHI_BOND_STEREO.SINGLE_1DOWN);
    	input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));
    	
    	return(input);
    }
    
    protected static JniInchiInput get2DDALANINEInput(String options) throws JniInchiException {
    	JniInchiInput input = new JniInchiInput(options);
    	
    	// Generate atoms
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
    	
    	// Add bonds
    	input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE)).setStereoDefinition(INCHI_BOND_STEREO.SINGLE_1UP);
    	input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));
    	
    	return(input);
    }
    
    protected static JniInchiInput get2DUndefinedStereoAlanineInput(String options) throws JniInchiException {
    	JniInchiInput input = new JniInchiInput(options);
    	
    	// Generate atoms
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
    	
    	// Add bonds
    	input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));
    	
    	return(input);
    }
    
    protected static JniInchiInput get0DLAlanineInput(String options) throws JniInchiException {
    	JniInchiInput input = new JniInchiInput(options);
    	
    	// Generate atoms
    	JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
    	JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
    	JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "N"));
    	JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
    	JniInchiAtom a5 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "O"));
    	JniInchiAtom a6 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "O"));
    	JniInchiAtom a7 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "H"));
    	
    	a3.setImplicitH(2);
    	a4.setImplicitH(3);
    	a5.setImplicitH(1);
    	
    	input.addStereo0D(new JniInchiStereo0D(a1, a3, a4, a7, a2, INCHI_STEREOTYPE.TETRAHEDRAL, INCHI_PARITY.ODD));
    	
    	// Add bonds
    	input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));
    	input.addBond(new JniInchiBond(a1, a7, INCHI_BOND_TYPE.SINGLE));
    	
    	return(input);
    }
    
    protected static JniInchiInput get0DDAlanineInput(String options) throws JniInchiException {
    	JniInchiInput input = new JniInchiInput(options);
    	
//    	 Generate atoms
    	JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
    	JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
    	JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "N"));
    	JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
    	JniInchiAtom a5 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "O"));
    	JniInchiAtom a6 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "O"));
    	JniInchiAtom a7 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "H"));
    	
    	a3.setImplicitH(2);
    	a4.setImplicitH(3);
    	a5.setImplicitH(1);
    	
    	input.addStereo0D(new JniInchiStereo0D(a1, a3, a4, a7, a2, INCHI_STEREOTYPE.TETRAHEDRAL, INCHI_PARITY.EVEN));
    	
    	// Add bonds
    	input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));
    	input.addBond(new JniInchiBond(a1, a7, INCHI_BOND_TYPE.SINGLE));
    	
    	return(input);
    }
    
    protected static JniInchiInput get0DUndefinedStereoAlanineInput(String options) throws JniInchiException {
    	JniInchiInput input = new JniInchiInput(options);
    	
    	// Generate atoms
    	JniInchiAtom a1 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
    	JniInchiAtom a2 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
    	JniInchiAtom a3 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "N"));
    	JniInchiAtom a4 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "C"));
    	JniInchiAtom a5 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "O"));
    	JniInchiAtom a6 = input.addAtom(new JniInchiAtom(0.0, 0.0, 0.0, "O"));
    	
    	a1.setImplicitH(1);
    	a3.setImplicitH(2);
    	a4.setImplicitH(3);
    	a5.setImplicitH(1);
    	
    	// Add bonds
    	input.addBond(new JniInchiBond(a1, a2, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a3, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a1, a4, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a5, INCHI_BOND_TYPE.SINGLE));
    	input.addBond(new JniInchiBond(a2, a6, INCHI_BOND_TYPE.DOUBLE));
    	
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
        Assert.assertEquals(options, flag + "Compress " + flag + "SNon ");
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiWrapper.checkOptions(String)'
     */
    public void testCheckOptionsString() throws JniInchiException {
        String options = JniInchiWrapper.checkOptions("  -ComPreSS  /SNon");
        String flag = JniInchiWrapper.flagChar;
        Assert.assertEquals(options, flag + "Compress " + flag + "SNon ");
    }
    
    
    /**
     * Tests InChI generation from benzene molecule with no coordinates, and
     * bonds alternating single/double/single/double/single/double.
     * @throws Exception
     */
    @Test
    public void testGetInchiFrom0DBenzeneBondsSingleDouble() throws Exception {
    	JniInchiInput input = get0DBenzeneBondsSingleDoubleInput("");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.OKAY);
    	Assert.assertEquals(output.getInchi(), "InChI=1/C6H6/c1-2-4-6-5-3-1/h1-6H");
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/0/N:1,2,6,3,5,4/E:(1,2,3,4,5,6)/rA:6CCCCCC/rB:s1;d2;s3;d4;d1s5;/rC:;;;;;;");
    }
    
    /**
     * Tests InChI generation from benzene molecule with no coordinates, and
     * bonds alternating double/single/double/single/double/single.
     * @throws Exception
     */
    @Test
    public void testGetInchiFrom0DBenzeneBondsDoubleSingle() throws Exception {
    	JniInchiInput input = get0DBenzeneBondsDoubleSingleInput("");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.OKAY);
    	Assert.assertEquals(output.getInchi(), "InChI=1/C6H6/c1-2-4-6-5-3-1/h1-6H");
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/0/N:1,2,6,3,5,4/E:(1,2,3,4,5,6)/rA:6CCCCCC/rB:d1;s2;d3;s4;s1d5;/rC:;;;;;;");
    }
    
    /**
     * Tests InChI generation from benzene molecule with 2/3D coordinates.
     * @throws Exception
     */
    @Test
    public void testGetInchiFrom2DBenzene() throws Exception {
    	JniInchiInput input = get2DBenzeneInput("");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.OKAY);
    	Assert.assertEquals(output.getInchi(), "InChI=1/C6H6/c1-2-4-6-5-3-1/h1-6H");
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/0/N:1,2,6,3,5,4/E:(1,2,3,4,5,6)/rA:6CCCCCC/rB:s1;d2;s3;d4;d1s5;/rC:-.01,0,0;-.707,1.208,0;-2.102,1.208,0;-2.799,0,0;-2.102,-1.208,0;-.707,-1.208,0;");
    }
    
    /**
     * Tests InChI generation from 1-propanol molecule with explicit hydrogens.
     * 
     * Tests explicit hydrogen handling.
     * 
     * @throws Exception
     */
    @Test
    public void testGetInchiFromPropanolExplicitHydrogen() throws Exception {
    	JniInchiInput input = getPropanolExplicitHydrogenInput("");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.OKAY);
    	Assert.assertEquals(output.getInchi(), "InChI=1/C3H8O/c1-2-3-4/h4H,2-3H2,1H3");
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/0/N:4,3,2,1/rA:12OCCCHHHHHHHH/rB:s1;s2;s3;s1;s2;s2;s3;s3;s4;s4;s4;/rC:.419,-.193,.246;-.965,-.011,-.023;-1.531,-1.306,-.586;-3.012,-1.187,-.898;.753,.647,.603;-1.468,.262,.91;-1.075,.808,-.74;-1.366,-2.121,.129;-.98,-1.585,-1.492;-3.194,-.403,-1.641;-3.585,-.946,.003;-3.392,-2.131,-1.301;");
    }
    
    /**
     * Tests InChI generation from 1-propanol molecule with implicit hydrogens.
     * 
     * Tests implicit hydrogen handling.
     * 
     * @throws Exception
     */
    @Test
    public void testGetInchiFromPropanolImplicitHydrogen() throws Exception {
    	JniInchiInput input = getPropanolImplicitHydrogenInput("");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.OKAY);
    	Assert.assertEquals(output.getInchi(), "InChI=1/C3H8O/c1-2-3-4/h4H,2-3H2,1H3");
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/0/N:4,3,2,1/rA:4OCCC/rB:s1;s2;s3;/rC:.419,-.193,.246;-.965,-.011,-.023;-1.531,-1.306,-.586;-3.012,-1.187,-.898;");
    }
    
    /**
     * Tests InChI generation from L-Alanine molecule with 3D coordinates.
     * 
     * Tests stereo perception from 3d coordinates.
     * 
     * @throws Exception
     */
    @Test
    public void testGetInchiFrom3DLAlanine() throws Exception {
    	JniInchiInput input = get3DLAlanineInput("");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.OKAY);
    	Assert.assertEquals(output.getInchi(), LALANINE_INCHI);
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/1/N:4,1,2,3,5,6/E:(5,6)/it:im/rA:6CCNCOO/rB:s1;s1;s1;s2;d2;/rC:-.358,.819,20.655;-1.598,-.032,20.905;-.275,2.014,21.574;.952,.043,20.838;-2.678,.479,21.093;-1.596,-1.239,20.958;");
    };
    
    /**
     * Tests InChI generation from L-Alanine molecule with 3D coordinates, 
     * including fixed hydrogen layer.
     * 
     * @throws Exception
     */
    @Test
    public void testGetInchiFrom3DLAlanineFixedH() throws Exception {
    	JniInchiInput input = get3DLAlanineInput("-FixedH");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.OKAY);
    	Assert.assertEquals(output.getInchi(), "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1/f/h5H");
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/1/N:4,1,2,3,5,6/E:(5,6)/it:im/F:m/it:m/rA:6CCNCOO/rB:s1;s1;s1;s2;d2;/rC:-.358,.819,20.655;-1.598,-.032,20.905;-.275,2.014,21.574;.952,.043,20.838;-2.678,.479,21.093;-1.596,-1.239,20.958;");
    };
    
    /**
     * Tests InChI generation from L-Alanine zwiterion molecule with 3D
     * coordinates.
     * 
     * Tests stereo perception from 3d coordinates.
     * 
     * @throws Exception
     */
    @Test
    public void testGetInchiFrom3DLAlanineZwiterion() throws Exception {
    	JniInchiInput input = get3DLAlanineZwiterionInput("");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.WARNING);
    	Assert.assertEquals(output.getMessage(), "Proton(s) added/removed");
    	Assert.assertEquals(output.getInchi(), LALANINE_INCHI);
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/1/N:4,1,2,3,5,6/E:(5,6)/it:im/rA:6CCN+CO-O/rB:s1;s1;s1;s2;d2;/rC:-.358,.819,20.655;-1.598,-.032,20.905;-.275,2.014,21.574;.952,.043,20.838;-2.678,.479,21.093;-1.596,-1.239,20.958;");
    };
    
    /**
     * Tests InChI generation from L-Alanine zwiterion molecule with 3D
     * coordinates including fixed hydrogen layer.
     * 
     * Tests stereo perception from 3d coordinates.
     * 
     * @throws Exception
     */
    @Test
    public void testGetInchiFrom3DLAlanineZwiterionFixedH() throws Exception {
    	JniInchiInput input = get3DLAlanineZwiterionInput("-FixedH");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.WARNING);
    	Assert.assertEquals(output.getMessage(), "Proton(s) added/removed");
    	Assert.assertEquals(output.getInchi(), "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1/f/h4H");
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/1/N:4,1,2,3,5,6/E:(5,6)/it:im/F:m/E:m/it:m/rA:6CCN+CO-O/rB:s1;s1;s1;s2;d2;/rC:-.358,.819,20.655;-1.598,-.032,20.905;-.275,2.014,21.574;.952,.043,20.838;-2.678,.479,21.093;-1.596,-1.239,20.958;");
    };
    
    /**
     * Tests InChI generation from alanine molecule with 2D coordinates and
     * undefined stereochemistry, not using FixSp3Bug.
     * 
     * @throws Exception
     */
    @Test
    public void testGetInchiFrom2DUndefinedStereoAlanineWithoutFixSp3Bug() throws Exception {
    	JniInchiInput input = get2DUndefinedStereoAlanineInput("");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.WARNING);
    	Assert.assertEquals(output.getMessage(), "Omitted undefined stereo");
    	Assert.assertEquals(output.getInchi(), ALANINE_INCHI);
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/1/N:4,1,2,3,5,6/E:(5,6)/rA:6CCNCOO/rB:s1;s1;s1;s2;d2;/rC:264,968,0;295,985,0;233,986,0;264,932,0;326,967,0;295,1021,0;");
    }
    
    /**
     * Tests InChI generation from alanine molecule with 2D coordinates and
     * undefined stereochemistry, using FixSp3Bug.
     * @throws Exception
     */
    @Test
    public void testGetInchiFrom2DUndefinedStereoAlanineWithFixSp3Bug() throws Exception {
    	JniInchiInput input = get2DUndefinedStereoAlanineInput("-FixSp3Bug");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.WARNING);
    	Assert.assertEquals(output.getMessage(), "Omitted undefined stereo");
    	Assert.assertEquals(output.getInchi(), ALANINE_INCHI);
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/1/N:4,1,2,3,5,6/E:(5,6)/rA:6CCNCOO/rB:s1;s1;s1;s2;d2;/rC:264,968,0;295,985,0;233,986,0;264,932,0;326,967,0;295,1021,0;");
    }
    
    /**
     * Tests InChI generation from L-alanine molecule with 2D coordinates, not
     * using FixSp3Bug.
     * @throws Exception
     */
    @Test
    public void testGetInchiFrom2DLAlanineWithoutFixSp3Bug() throws Exception {
    	JniInchiInput input = get2DLAlanineInput("");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.WARNING);
    	Assert.assertEquals(output.getMessage(), "Omitted undefined stereo");
    	Assert.assertEquals(output.getInchi(), ALANINE_INCHI);
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/1/N:4,1,2,3,5,6/E:(5,6)/rA:6CCNCOO/rB:s1;N1;s1;s2;d2;/rC:264,968,0;295,985,0;233,986,0;264,932,0;326,967,0;295,1021,0;");
    }
    
    /**
     * Tests InChI generation from L-alanine molecule with 2D coordinates, using
     * FixSp3Bug.
     * 
     * Tests stereo perception from 2d coordinates and bond stereo.
     * 
     * @throws Exception
     */
    @Test
    public void testGetInchiFrom2DLAlanineWithFixSp3Bug() throws Exception {
    	JniInchiInput input = get2DLAlanineInput("-FixSp3Bug");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.OKAY);
    	Assert.assertEquals(output.getInchi(), LALANINE_INCHI);
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/1/N:4,1,2,3,5,6/E:(5,6)/it:im/rA:6CCNCOO/rB:s1;N1;s1;s2;d2;/rC:264,968,0;295,985,0;233,986,0;264,932,0;326,967,0;295,1021,0;");
    }
    
    /**
     * Tests InChI generation from R-alanine molecule with 2D coordinates, not 
     * using FixSp3Bug.
     * @throws Exception
     */
    @Test
    public void testGetInchiFrom2DDALANINEWithoutFixSp3Bug() throws Exception {
    	JniInchiInput input = get2DDALANINEInput("");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.WARNING);
    	Assert.assertEquals(output.getMessage(), "Omitted undefined stereo");
    	Assert.assertEquals(output.getInchi(), ALANINE_INCHI);
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/1/N:4,1,2,3,5,6/E:(5,6)/rA:6CCNCOO/rB:s1;P1;s1;s2;d2;/rC:264,968,0;295,985,0;233,986,0;264,932,0;326,967,0;295,1021,0;");
    }
    
    /**
     * Tests InChI generation from R-alanine molecule with 2D coordinates, using
     * FixSp3Bug.
     * 
     * Tests stereo perception from 2d coordinates and bond stereo.
     * 
     * @throws Exception
     */
    @Test
    public void testGetInchiFrom2DDALANINEWithFixSp3Bug() throws Exception {
    	JniInchiInput input = get2DDALANINEInput("-FixSp3Bug");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.OKAY);
    	Assert.assertEquals(output.getInchi(), DALANINE_INCHI);
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/1/N:4,1,2,3,5,6/E:(5,6)/it:im/rA:6CCNCOO/rB:s1;P1;s1;s2;d2;/rC:264,968,0;295,985,0;233,986,0;264,932,0;326,967,0;295,1021,0;");
    }
    
    /**
     * Tests InChI generation from alanine molecule without coordinates and
     * without stereochemistry.
     * @throws Exception
     */
    @Test
    public void testGetInchiFrom0DUndefinedStereoAlanine() throws Exception {
    	JniInchiInput input = get0DUndefinedStereoAlanineInput("");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.WARNING);
    	Assert.assertEquals(output.getMessage(), "Omitted undefined stereo");
    	Assert.assertEquals(output.getInchi(), ALANINE_INCHI);
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/1/N:4,1,2,3,5,6/E:(5,6)/rA:6CCNCOO/rB:s1;s1;s1;s2;d2;/rC:;;;;;;");
    }
    
    /**
     * Tests InChI generation from L-alanine molecule without coordinates.
     * 
     * Tests stereo perception from tetrahedral atom parity.
     * 
     * @throws Exception
     */
    @Test
    public void testGetInchiFrom0DLAlanine() throws Exception {
    	JniInchiInput input = get0DLAlanineInput("");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.OKAY);
    	Assert.assertEquals(output.getInchi(), LALANINE_INCHI);
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/1/N:4,1,2,3,5,6/E:(5,6)/rA:6CCNCOO/rB:s1;s1;s1;s2;d2;/rC:;;;;;;");
    }
    
    /**
     * Tests InChI generation from R-alanine molecule without coordinates.
     * 
     * Tests stereo perception from tetrahedral atom parity.
     * 
     * @throws Exception
     */
    @Test
    public void testGetInchiFrom0DDAlanine() throws Exception {
    	JniInchiInput input = get0DDAlanineInput("");
    	JniInchiOutput output = JniInchiWrapper.getInchi(input);
    	Assert.assertEquals(output.getReturnStatus(), INCHI_RET.OKAY);
    	Assert.assertEquals(output.getInchi(), DALANINE_INCHI);
    	// Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/1/N:4,1,2,3,5,6/E:(5,6)/rA:6CCNCOO/rB:s1;s1;s1;s2;d2;/rC:;;;;;;");
    }
    
    
    
    
    
    
    
    
    
    public void testGetInchi() throws JniInchiException {
        JniInchiInput input = get0DBenzeneBondsSingleDoubleInput("");
        JniInchiOutput output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(output.getReturnStatus(), INCHI_RET.OKAY);
        Assert.assertEquals(output.getInchi(), "InChI=1/C6H6/c1-2-4-6-5-3-1/h1-6H");
        // Assert.assertEquals(output.getAuxInfo(), "AuxInfo=1/0/N:1,2,6,3,5,4/E:(1,2,3,4,5,6)/rA:6CCCCCC/rB:s1;d2;s3;d4;d1s5;/rC:-.01,0,0;-.707,1.208,0;-2.102,1.208,0;-2.799,0,0;-2.102,-1.208,0;-.707,-1.208,0;");
        /*
        input = getLAlanineInput("");
        output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(output.getInchi(), "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,1,4H3/t2-/m0/s1");
        										InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,4H2,1H3,(H,5,6)/t2-/m0/s1/f/h5H
        input = getDALANINEInput("");
        output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(output.getInchi(), "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,1,4H3/t2-/m1/s1");
        
        input = getLAlanineInput("-compress");
        output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(output.getInchi(), "InChI=1/C3H7NO2/cABBCC/hB1A3D3/tB1/m0/s1");
        
        input = getLAlanineInput("/CoMpReSS");
        output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(output.getInchi(), "InChI=1/C3H7NO2/cABBCC/hB1A3D3/tB1/m0/s1");
        
        input = getLAlanineInput("-SNon");
        output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(output.getInchi(), "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,1,4H3");
        
        input = getLAlanineInput("-SAbs");
        output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(output.getInchi(), "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,1,4H3/t2-/m0/s1");
        
        input = getLAlanineInput("-SRac");
        output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(output.getInchi(), "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,1,4H3/t2-/s3");
        
        input = get0ALANINEInput("-SRac");
        output = JniInchiWrapper.getInchi(input);
        Assert.assertEquals(output.getInchi(), "InChI=1/C3H7NO2/c1-2(4)3(5)6/h2H,1,4H3/t2-/s3");
        */
    }

    /*
     * Test method for 'net.sf.jniinchi.JniInchiWrapper.getStructureFromInchi(JniInchiInputInchi)'
     */
    public void testGetStructureFromInchi() throws JniInchiException {
        JniInchiInputInchi input = new JniInchiInputInchi("InChI=1/C6H6/c1-2-4-6-5-3-1/h1-6H");
        JniInchiOutputStructure output = JniInchiWrapper.getStructureFromInchi(input);
        Assert.assertEquals(output.getReturnStatus(), INCHI_RET.OKAY);
        Assert.assertEquals(output.getNumAtoms(), 6);
        Assert.assertEquals(output.getAtom(0).getElementType(), "C");
        Assert.assertEquals(output.getAtom(1).getElementType(), "C");
        Assert.assertEquals(output.getAtom(2).getElementType(), "C");
        Assert.assertEquals(output.getAtom(3).getElementType(), "C");
        Assert.assertEquals(output.getAtom(4).getElementType(), "C");
        Assert.assertEquals(output.getAtom(5).getElementType(), "C");
        Assert.assertEquals(output.getNumBonds(), 6);
        Assert.assertEquals(output.getBond(0).getBondType(), INCHI_BOND_TYPE.DOUBLE);
        Assert.assertEquals(output.getBond(1).getBondType(), INCHI_BOND_TYPE.SINGLE);
        Assert.assertEquals(output.getBond(2).getBondType(), INCHI_BOND_TYPE.SINGLE);
        Assert.assertEquals(output.getBond(3).getBondType(), INCHI_BOND_TYPE.DOUBLE);
        Assert.assertEquals(output.getBond(4).getBondType(), INCHI_BOND_TYPE.DOUBLE);
        Assert.assertEquals(output.getBond(5).getBondType(), INCHI_BOND_TYPE.SINGLE);
    }
    
    
    
    
    protected void debug(JniInchiOutput output) {
    	System.out.println(output.getReturnStatus());
    	System.out.println(output.getMessage());
    	//System.out.println(output.getLog());
    	System.out.println(output.getInchi());
    	System.out.println(output.getAuxInfo());
    	System.out.println();
    }
}
