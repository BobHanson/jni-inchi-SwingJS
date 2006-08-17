/* File: TestCMLInchiGenerator.java
 * Author: Sam Adams
 * 
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi.cml;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import net.sf.jniinchi.JniInchiException;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.xmlcml.cml.base.CMLConstants;
import org.xmlcml.cml.base.CMLException;
import org.xmlcml.cml.element.CMLBuilder;
import org.xmlcml.cml.element.CMLMolecule;
import org.xmlcml.cml.tools.MoleculeTool;


/**
 * Provides test and example code for usage of CMLInchiGenerator.
 * 
 * @author Sam Adams
 */
public class TestCMLInchiGenerator {
    
    /**
     * Test, and example usage.
     */
    public static void test() throws ValidityException, IOException, ParsingException, CMLException, JniInchiException {
        
        CMLMolecule propanolMol = (CMLMolecule) new CMLBuilder().build(
                new StringReader(
                "<?xml version='1.0'?>"
                + "<molecule " + CMLConstants.CML_XMLNS + ">"
                + " <atomArray>"
                + "  <atom id='a1' elementType='O' x3='0.419000' y3='-0.193000' z3='0.246000'/>"
                + "  <atom id='a2' elementType='H' x3='0.753000' y3='0.647000' z3='0.603000'/>"
                + "  <atom id='a3' elementType='C' x3='-0.965000' y3='-0.011000' z3='-0.023000'/>"
                + "  <atom id='a4' elementType='H' x3='-1.468000' y3='0.262000' z3='0.910000'/>"
                + "  <atom id='a5' elementType='H' x3='-1.075000' y3='0.808000' z3='-0.740000'/>"
                + "  <atom id='a6' elementType='C' x3='-1.531000' y3='-1.306000' z3='-0.586000'/>"
                + "  <atom id='a7' elementType='H' x3='-1.366000' y3='-2.121000' z3='0.129000'/>"
                + "  <atom id='a8' elementType='H' x3='-0.980000' y3='-1.585000' z3='-1.492000'/>"
                + "  <atom id='a9' elementType='C' x3='-3.012000' y3='-1.187000' z3='-0.898000'/>"
                + "  <atom id='a10' elementType='H' x3='-3.194000' y3='-0.403000' z3='-1.641000'/>"
                + "  <atom id='a11' elementType='H' x3='-3.585000' y3='-0.946000' z3='0.003000'/>"
                + "  <atom id='a12' elementType='H' x3='-3.392000' y3='-2.131000' z3='-1.301000'/>"
                + " </atomArray>"
                + " <bondArray>"
                + "  <bond atomRefs2='a1 a2' order='1'/>"
                + "  <bond atomRefs2='a1 a3' order='1'/>"
                + "  <bond atomRefs2='a3 a5' order='1'/>"
                + "  <bond atomRefs2='a3 a4' order='1'/>"
                + "  <bond atomRefs2='a3 a6' order='1'/>"
                + "  <bond atomRefs2='a6 a8' order='1'/>"
                + "  <bond atomRefs2='a6 a7' order='1'/>"
                + "  <bond atomRefs2='a6 a9' order='1'/>"
                + "  <bond atomRefs2='a9 a12' order='1'/>"
                + "  <bond atomRefs2='a9 a11' order='1'/>"
                + "  <bond atomRefs2='a9 a10' order='1'/>"
                + " </bondArray>"
                + "</molecule>"
        )).getRootElement();
        

        CMLInchiGenerator inchiGen = new CMLInchiGenerator(propanolMol);
        System.out.println("Generated inchi: " + inchiGen.getInchi());
        
        InchiCMLGenerator cmlGen = new InchiCMLGenerator(inchiGen.getInchi(), "");
        System.out.println(cmlGen.getMolecule().toXML());
        
        /*
        inchiGen = new CMLInchiGenerator(propanolMol, "-compress");
        System.out.println("Generated compressed inchi (1): " + inchiGen.getInchi());
        
        List<INCHI_OPTION> opList = new ArrayList<INCHI_OPTION>();
        opList.add(INCHI_OPTION.Compress);
        inchiGen = new CMLInchiGenerator(propanolMol, opList);
        System.out.println("Generated compressed inchi (2): " + inchiGen.getInchi());
        
        inchiGen = new CMLInchiGenerator(propanolMol);
        inchiGen.appendToElement(propanolMol);
        System.out.println("XML with identifier:");
        System.out.println(propanolMol.toXML());
        */
        
        testCrystalMol();
    }
    
    private static void testCrystalMol() throws JniInchiException, ValidityException,
			ParsingException, IOException, CMLException {
    	CMLMolecule mol = (CMLMolecule) new CMLBuilder().build(new File("crystal.cml.xml")).getRootElement();
    	MoleculeTool molTool = new MoleculeTool(mol);
		molTool.adjustBondOrdersAndChargeToValency();
		
        CMLInchiGenerator inchiGen = new CMLInchiGenerator(mol);
        System.out.println("Generated inchi: " + inchiGen.getInchi());
        
        InchiCMLGenerator cmlGen = new InchiCMLGenerator(inchiGen.getInchi(), "");
        System.out.println(cmlGen.getMolecule().toXML());
        
        inchiGen = new CMLInchiGenerator(cmlGen.getMolecule());
        System.out.println("Generated inchi: " + inchiGen.getInchi());
        
        
        /*
        inchiGen = new CMLInchiGenerator(mol, "-compress");
        System.out.println("Generated compressed inchi: " + inchiGen.getInchi());
        
        List<INCHI_OPTION> opList = new ArrayList<INCHI_OPTION>();
        opList.add(INCHI_OPTION.Compress);
        inchiGen = new CMLInchiGenerator(mol, opList);
        System.out.println("Generated compressed inchi (2): " + inchiGen.getInchi());
        
        inchiGen = new CMLInchiGenerator(mol);
        inchiGen.appendToElement(mol);
        System.out.println("XML with identifier:");
        System.out.println(mol.toXML());
    	*/
	}

	/**
     * Runs test.
     */
    public static void main(String[] args) throws Throwable {        
        test();
    }
}
