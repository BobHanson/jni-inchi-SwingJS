/* File: TestCDKInchiGenerator.java
 * Author: Sam Adams <sea36@cam.ac.uk>
 * 
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi.cdk;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3d;

import net.sf.jniinchi.INCHI_OPTION;

import org.openscience.cdk.Atom;
import org.openscience.cdk.Bond;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.exception.CDKException;

/**
 * Provides test and example code for usage of CDKInchiGenerator.
 * 
 * @author Sam Adams
 */
public class TestCDKInchiGenerator {
    
    /**
     * Test, and example usage.
     */
    public static void test() throws CDKException {
        
    	// Build test molecule
    	Molecule propanolMol = new Molecule();
        Atom a1 = new Atom("O", new Point3d(0.419000, -0.193000, 0.246000)); propanolMol.addAtom(a1);
        Atom a2 = new Atom("H", new Point3d(0.753000, 0.647000, 0.603000)); propanolMol.addAtom(a2);
        Atom a3 = new Atom("C", new Point3d(-0.965000, -0.011000, -0.023000)); propanolMol.addAtom(a3);
        Atom a4 = new Atom("H", new Point3d(-1.468000, 0.262000, 0.910000)); propanolMol.addAtom(a4);
        Atom a5 = new Atom("H", new Point3d(-1.075000, 0.808000, -0.740000)); propanolMol.addAtom(a5);
        Atom a6 = new Atom("C", new Point3d(-1.531000, -1.306000, -0.586000)); propanolMol.addAtom(a6);
        Atom a7 = new Atom("H", new Point3d(-1.366000, -2.121000, 0.129000)); propanolMol.addAtom(a7);
        Atom a8 = new Atom("H", new Point3d(-0.980000, -1.585000, -1.492000)); propanolMol.addAtom(a8);
        Atom a9 = new Atom("C", new Point3d(-3.012000, -1.187000, -0.898000)); propanolMol.addAtom(a9);
        Atom a10 = new Atom("H", new Point3d(-3.194000, -0.403000, -1.641000)); propanolMol.addAtom(a10);
        Atom a11 = new Atom("H", new Point3d(-3.585000, -0.946000, 0.003000)); propanolMol.addAtom(a11);
        Atom a12 = new Atom("H", new Point3d(-3.392000, -2.131000, -1.301000)); propanolMol.addAtom(a12);
        
        Bond b1 = new Bond(a1, a2, 1); propanolMol.addBond(b1);
        Bond b2 = new Bond(a1, a3, 1); propanolMol.addBond(b2);
        Bond b3 = new Bond(a3, a5, 1); propanolMol.addBond(b3);
        Bond b4 = new Bond(a3, a4, 1); propanolMol.addBond(b4);
        Bond b5 = new Bond(a3, a6, 1); propanolMol.addBond(b5);
        Bond b6 = new Bond(a6, a8, 1); propanolMol.addBond(b6);
        Bond b7 = new Bond(a6, a7, 1); propanolMol.addBond(b7);
        Bond b8 = new Bond(a6, a9, 1); propanolMol.addBond(b8);
        Bond b9 = new Bond(a9, a12, 1); propanolMol.addBond(b9);
        Bond b10 = new Bond(a9, a11, 1); propanolMol.addBond(b10);
        Bond b11 = new Bond(a9, a10, 1); propanolMol.addBond(b11);
        
        CDKInchiGenerator inchiGen = new CDKInchiGenerator(propanolMol);
        System.out.println("Generated inchi: " + inchiGen.getInchi());
        
        
        InchiCDKGenerator cdkGen = new InchiCDKGenerator(inchiGen.getInchi());
        System.out.println(cdkGen.getMolecule().toString());
        
        
        inchiGen = new CDKInchiGenerator(propanolMol, "-compress");
        System.out.println("Generated compressed inchi: " + inchiGen.getInchi());
        
        List<INCHI_OPTION> opList = new ArrayList<INCHI_OPTION>();
        opList.add(INCHI_OPTION.Compress);
        inchiGen = new CDKInchiGenerator(propanolMol, opList);
        System.out.println("Generated compressed inchi (2): " + inchiGen.getInchi());
        
        
        
    }
    
    /**
     * Runs test.
     */
    public static void main(String[] args) throws Throwable {        
        test();
    }
}
