package net.sf.jniinchi.cml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlcml.cml.base.CMLException;
import org.xmlcml.cml.element.CMLAtom;
import org.xmlcml.cml.element.CMLBond;
import org.xmlcml.cml.element.CMLMolecule;
import org.xmlcml.cml.tools.AtomTool;

import net.sf.jniinchi.INCHI_BOND_TYPE;
import net.sf.jniinchi.JniInchiAtom;
import net.sf.jniinchi.JniInchiBond;
import net.sf.jniinchi.JniInchiException;
import net.sf.jniinchi.JniInchiInput;
import net.sf.jniinchi.JniInchiInputInchi;
import net.sf.jniinchi.JniInchiOutputStructure;
import net.sf.jniinchi.JniInchiWrapper;

public class InchiCMLGenerator {

	protected JniInchiInputInchi input;
	
	protected JniInchiOutputStructure output;
	
	protected CMLMolecule molecule;
	
	public InchiCMLGenerator(String inchi, String options) throws CMLException {
		input = new JniInchiInputInchi(inchi, options);
        generateCMLMoleculeFromInchi();
	}
	
	protected void generateCMLMoleculeFromInchi() throws CMLException {
		try {
			output = JniInchiWrapper.getStructureFromInchi(input);
        } catch (JniInchiException jie) {
            throw new CMLException("InChI generation failed: " + jie.getMessage());
        }
		
        molecule = new CMLMolecule();
        
        Map<JniInchiAtom, CMLAtom> inchiCmlAtomMap = new HashMap<JniInchiAtom, CMLAtom>();
        
        for (int i = 0; i < output.getNumAtoms(); i ++) {
        	JniInchiAtom iAt = output.getAtom(i);
        	CMLAtom cAt = new CMLAtom();
        	
        	inchiCmlAtomMap.put(iAt, cAt);
        	
        	cAt.setId("a" + i);
        	cAt.setElementType(iAt.getElementType());
        	
        	// Ignore coordinates - all zero
        	
        	int charge = iAt.getCharge();
        	if (charge != 0) {
        		cAt.setFormalCharge(charge);
        	}
        	
        	int numH = iAt.getNumberImplicitH();
        	if (numH != 0) {
        		cAt.setHydrogenCount(numH);
        	}
        	
        	molecule.addAtom(cAt, false);
        }
        
        for (int i = 0; i < output.getNumBonds(); i ++) {
        	JniInchiBond iBo = output.getBond(i);
        	CMLBond cBo = new CMLBond();
        	
        	CMLAtom atO = inchiCmlAtomMap.get(iBo.getOriginAtom());
        	CMLAtom atT = inchiCmlAtomMap.get(iBo.getTargetAtom());
        	cBo.setAtoms2(atO, atT);
        	
        	INCHI_BOND_TYPE type = iBo.getBondType();
        	if (type == INCHI_BOND_TYPE.SINGLE) {
        		cBo.setOrder(CMLBond.SINGLE);
        	} else if (type == INCHI_BOND_TYPE.DOUBLE) {
        		cBo.setOrder(CMLBond.DOUBLE);
        	} else if (type == INCHI_BOND_TYPE.TRIPLE) {
        		cBo.setOrder(CMLBond.TRIPLE);
        	} else if (type == INCHI_BOND_TYPE.ALTERN) {
        		cBo.setOrder(CMLBond.AROMATIC);
        	} else {
        		throw new CMLException("Unknown bond type: " + type);
        	}
        	
        	// TODO: bond sterochemistry
        	
        	molecule.addBond(cBo, false);
        }
        
        // Add explict hydrogens to hydrogen counts
        for (int i = 0; i < molecule.getAtomCount(); i ++) {
        	CMLAtom at = molecule.getAtom(i);
        	if (at.getHydrogenCountAttribute() != null) {
	        	AtomTool atTool = new AtomTool(at);
	        	List<CMLAtom> ligandList = atTool.getLigandList();
	        	int hLigands = 0;
	        	for (int j = 0; j < ligandList.size(); j ++) {
	        		if (ligandList.get(j).getElementType().equals("H")) {
	        			hLigands ++;
	        		}
	        	}
	        	
	        	if (hLigands > 0) {
	        		int numH = at.getHydrogenCount() + hLigands;
	        		at.setHydrogenCount(numH);
	        	}
        	}
        }
	}
	
	public CMLMolecule getMolecule() {
		return(molecule);
	}

}
