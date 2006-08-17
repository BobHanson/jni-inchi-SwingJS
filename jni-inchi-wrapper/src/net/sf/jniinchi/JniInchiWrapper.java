/* File: JniInchiWrapper.java
 * Author: Sam Adams
 * 
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * <p>JNI Wrapper for International Chemical Identifier (InChI) C++ library.
 * 
 * <p>This class is not intended to be used directly, but should be accessed
 * through subclasses that read data formats and load them into the InChI
 * data structures.
 * 
 * <p>Subclasses should load data through the addAtom, addBond and addParity
 * methods. Once the molecule is fully loaded then the generateInchi method
 * should be called. Ideally this should all take place within the subclass's
 * constructor. The public get methods will all return null until this has
 * happened.
 * 
 * <p>See <tt>inchi_api.h</tt>.
 * 
 * @author Sam Adams
 */
public class JniInchiWrapper {
    
    /**
     * Name of native library.
     */
    private static final String JNI_INCHI_LIB = "JniInchi";
    
    /**
     * Names of windows library files.
     */
    private static final String[] WINDOWS_FILES = {"JniInchi.dll", "libinchi.dll"};
    
    /**
     * Names of linux library files.
     */
    private static final String[] LINUX_FILES = {"libinchi.so.1", "libJniInchi.so"};
    
    /**
     * Size of atom neighbors (bonds) array (value from InChI library).
     */
    private static final int MAXVAL = 20;
    
    private static int WINDOWS = 1;
    private static int LINUX = 2;
    
    /**
     * Flag indicating windows or linux.
     */
    private static int PLATFORM =
        System.getProperty("os.name", "").toLowerCase().startsWith("windows") ? WINDOWS : LINUX;
    
    /**
     * Filenames to be loaded.
     */
    private static String[] FILENAMES = PLATFORM == WINDOWS ? WINDOWS_FILES : LINUX_FILES;
    
    /**
     * Switch character for passing options. / in windows, - on other systems.
     */
    private static String flagChar = PLATFORM == WINDOWS ? "/" : "-"; 
    
    /**
     * Flag indicating whether native files should be placed automatically. By
     * default set to true.
     */
    public static boolean AUTO_PLACE_NATIVE_CODE = true;
    
    
    /**
     * Records whether native library has been loaded by system.
     */
    private static boolean libraryLoaded = false;
    
    /**
     * Loads native library. If unable to load on first attempt (usually because
     * native library files are not in the library search path), method checks
     * whether the current working directory (either by full path, or as '.')
     * is in the library search path, and if so attempts to copy the native
     * library files there, and then load them.
     * 
     * @throws JniInchiException Library failed to load
     */
    protected static void loadLibrary() throws JniInchiException {
        if (!libraryLoaded) {
            try {
            	// Try to load native library
                System.loadLibrary(JNI_INCHI_LIB);
                
            } catch (UnsatisfiedLinkError ule) {
            	// Failure most likely due to library files not being in the
            	// correct location. Check whether current working directory
            	// is in the library search path, and if so copy the
            	// library files there and retry loading
            	
            	if (AUTO_PLACE_NATIVE_CODE) {
            	
	            	try {
		            	String librarySearchPath = System.getProperty("java.library.path");
		            	String workingDir = System.getProperty("user.dir");
		            	String pathSeparator = System.getProperty("path.separator");
		                StringTokenizer token = new StringTokenizer(librarySearchPath, pathSeparator);
		                
		                boolean match = false;
		                while (token.hasMoreTokens()) {
		                	String path = token.nextToken();
		                	if (path.equals(".") || path.equals(workingDir)) {
		                		match = true;
		                		break;
		                	}
		                }
		                
		                if (!match) {
		                	throw new IOException("Working directory not in library search path");
		                }
		            	
		                // Copy files to directory
		            	String pathSep = System.getProperty("file.separator");
		                ClassLoader cl = ClassLoader.getSystemClassLoader();
	                
	                    for (int i = 0; i < FILENAMES.length; i ++) {
	                    	// Locate file
	                        URL u = cl.getResource(FILENAMES[i]);
	                        if (u == null) {
	                        	throw new IOException("Unable to locate " + FILENAMES[i]);
	                        }
	                        
	                        // Check output path writable
	                        String outfilepath = workingDir + pathSep;
	                        File outpath = new File(outfilepath);
	                        if (!outpath.canWrite()) {
	                        	throw new IOException("Unable to write to " + outpath);
	                        }
	                        
	                        File outfile = new File(outfilepath + FILENAMES[i]);
	                        
	                        // Skip if file already exists
	                        if (outfile.exists()) {
	                        	continue;
	                        }
	                        
	                        // Open streams           
	                        InputStream is = u.openStream();
	                        OutputStream os = new FileOutputStream(outfile);
	                        
	                        // Copy file
	                        int n;
	                        byte[] b = new byte[1024];
	                        while ((n = is.read(b)) > -1) {
	                            os.write(b, 0, n);
	                        }
	                        os.close();
	                        is.close();
	                    }
	                    
	                    // Load libraries
	                    System.loadLibrary(JNI_INCHI_LIB);
	                } catch (IOException ioe) {
	                    throw new JniInchiException("Unable to load JniInchi library: "
	                            + ioe.getMessage());
	                } catch (UnsatisfiedLinkError ule2) {
	                    throw new JniInchiException("Unable to load JniInchi library: "
	                            + ule2.getMessage());
	                }
            	} else {
            		throw new JniInchiException("Unable to load JniInchi library: "
                            + ule.getMessage());
            	}
            }
            
            libraryLoaded = true;
        }
    }
    
    
    /**
     * Constructor
     */
    protected JniInchiWrapper() throws JniInchiException {
	    loadLibrary();
    }
    
    
    /**
     * Checks and canonicalises options.
     * 
     * @param ops  List of INCHI_OPTION
     */
    protected static String checkOptions(List ops) throws JniInchiException {
        StringBuffer sbOptions = new StringBuffer();
        
        for (int i = 0; i < ops.size(); i ++) {
            Object op = ops.get(i);
            if (op instanceof INCHI_OPTION) {
                sbOptions.append(flagChar + ((INCHI_OPTION) op).getName() + " ");
            } else {
                throw new JniInchiException("Unrecognised InChI option");
            }
        }
        
        return(sbOptions.toString()); 
    }
    
    /**
     * Checks and canonicalises options.
     * 
     * @param ops  	    Space delimited string of options to pass to InChI library.
     * 					Each option may optionally be preceded by a command line
     * 					switch (/ or -). 
     */
    protected static String checkOptions(String ops) throws JniInchiException {
        Map optionMap = INCHI_OPTION.getLowercaseMap();
        
        StringBuffer sbOptions = new StringBuffer();
        
        StringTokenizer tok = new StringTokenizer(ops);
        while (tok.hasMoreTokens()) {
            String op = tok.nextToken();
            
            if (op.startsWith("-") || op.startsWith("/")) {
                op = op.substring(1);
            }
            
            if (optionMap.keySet().contains(op)) {
                sbOptions.append(flagChar + op + " ");
            } else {
                throw new JniInchiException("Unrecognised InChI option");
            }
        }
        
        return(sbOptions.toString());
    }
    
    
    /**
     * Generates InChI string for a chemical structure.
     * @param input
     * @return
     * @throws JniInchiException
     */
    public static JniInchiOutput getInchi(JniInchiInput input) throws JniInchiException {
    	JniInchiWrapper wrapper = new JniInchiWrapper();
    	
    	// Create arrays
        int nat = input.getNumAtoms();
        int nst = input.getNumStereo0D();
        
        wrapper.LibInchiStartInput(nat, nst, input.options.length() == 0 ? " " : input.options);
        
        // Load atom data
        Map atomIndxMap = new HashMap();
        for (int i = 0; i < nat; i ++) {
            JniInchiAtom atom = input.getAtom(i);
            atomIndxMap.put(atom, new Integer(i));
            
            wrapper.LibInchiSetAtom(i, atom.x, atom.y, atom.z, atom.elname,
                    atom.isotopic_mass, atom.implicitH, atom.implicitP,
                    atom.implicitD, atom.implicitT, atom.radical.getIndx(),
                    atom.charge);
        }
        
        int[]   atomNumNeighbors = new int[nat];
        int[][] atomNeighbors = new int[nat][MAXVAL];
        int[][] atomBondTypes = new int[nat][MAXVAL];
        int[][] atomBondStereo = new int[nat][MAXVAL];
                                                
        
        // Load bond data (note, bonds only need to be recorded in one atom's
        // neighbor list, not both
        for (int i = 0; i < input.bondList.size(); i ++) {
            JniInchiBond bond = input.getBond(i);
            int atOi = ((Integer) atomIndxMap.get(bond.atomOrigin)).intValue();
            int atTi = ((Integer) atomIndxMap.get(bond.atomTarget)).intValue();
            
            int bondNo = atomNumNeighbors[atOi];
            atomNeighbors[atOi][bondNo] = atTi;
            atomBondTypes[atOi][bondNo] = bond.type.getIndx();
            atomBondStereo[atOi][bondNo] = bond.stereo.getIndx();
            
            atomNumNeighbors[atOi] ++;
        }
        
        for (int i = 0; i < nat; i ++) {
            wrapper.LibInchiSetAtomBonds(i, atomNumNeighbors[i], atomNeighbors[i],
                    atomBondTypes[i], atomBondStereo[i]);
        }
        
        
        // Load 0D Stereo parities
        for (int i = 0; i < nst; i ++) {
            JniInchiStereo0D stereo = (JniInchiStereo0D) input.stereoList.get(i);
            int parity = (int) (stereo.parity.getIndx()
                    | (stereo.disconParity.getIndx() << 3));
            
            int atCi = ((Integer) atomIndxMap.get(stereo.centralAtom)).intValue();
            int at0i = ((Integer) atomIndxMap.get(stereo.neighbors[0])).intValue();
            int at1i = ((Integer) atomIndxMap.get(stereo.neighbors[1])).intValue();
            int at2i = ((Integer) atomIndxMap.get(stereo.neighbors[2])).intValue();
            int at3i = ((Integer) atomIndxMap.get(stereo.neighbors[3])).intValue();
            
            wrapper.LibInchiSetStereo(i, atCi, at0i, at1i, at2i, at3i,
                    stereo.type.getIndx(), parity);
        }
        
        // Call inchi library
        int retVal = wrapper.LibInchiGenerateInchi();
        
        // Fetch results
        JniInchiOutput output = new JniInchiOutput();
        
        List retCodes = INCHI_RET.getList();
        for (int i = 0; i < retCodes.size(); i ++) {
            INCHI_RET ret = (INCHI_RET) retCodes.get(i);
            if (retVal == ret.getIndx()) {
                output.retStatus = ret;
                break;
            }
        } 
        
        output.sInchi = wrapper.LibInchiGetInchi();
        output.sAuxInfo = wrapper.LibInchiGetAuxInfo();
        output.sMessage = wrapper.LibInchiGetMessage();
        output.sLog = wrapper.LibInchiGetLog();
        
        wrapper.LibInchiFreeMem();
    	
    	return(output);
    }
    
    
    /**
     * Generated 0D structure from an InChI string.
     * @param input
     * @return
     * @throws JniInchiException
     */
    public static JniInchiOutputStructure getStructureFromInchi(JniInchiInputInchi input) throws JniInchiException {
    	JniInchiWrapper wrapper = new JniInchiWrapper();
    	int retVal = wrapper.LibInchiGetStruct(input.inchiString, input.options);
    	
    	JniInchiOutputStructure output = new JniInchiOutputStructure();
    	
    	List retCodes = INCHI_RET.getList();
    	for (int i = 0; i < retCodes.size(); i ++) {
            INCHI_RET ret = (INCHI_RET) retCodes.get(i);
            if (retVal == ret.getIndx()) {
                output.retStatus = ret;
                break;
            }
        }
    	
    	// Get warning flags.
    	output.warningFlags[0][0] = wrapper.LibInchiGetStructWarningFlags00();
    	output.warningFlags[0][1] = wrapper.LibInchiGetStructWarningFlags01();
    	output.warningFlags[1][0] = wrapper.LibInchiGetStructWarningFlags10();
    	output.warningFlags[1][1] = wrapper.LibInchiGetStructWarningFlags11();
    	
    	// Get structural data
    	int numAtoms = wrapper.LibInchiGetNumAtoms();
    	int numStereo = wrapper.LibInchiGetNumStereo();
    	
    	int[][] bondTypes = new int[numAtoms][numAtoms];
    	int[][] bondStereos = new int[numAtoms][numAtoms];
    	
    	// Generate atoms
    	for (int i = 0; i < numAtoms; i ++) {
    		String el = wrapper.LibInchiGetAtomElement(i);
    		double x = wrapper.LibInchiGetAtomX(i);
    		double y = wrapper.LibInchiGetAtomY(i);
    		double z = wrapper.LibInchiGetAtomZ(i);
    		
    		JniInchiAtom atom = new JniInchiAtom(x, y, z, el);
    		
    		atom.setImplictH(wrapper.LibInchiGetAtomImplicitH(i));
    		atom.setImplictProtium(wrapper.LibInchiGetAtomImplicitP(i));
    		atom.setImplictDeuterium(wrapper.LibInchiGetAtomImplicitD(i));
    		atom.setImplictTritium(wrapper.LibInchiGetAtomImplicitT(i));
    		
    		atom.setCharge(wrapper.LibInchiGetAtomCharge(i));
    		atom.setIsotopicMass(wrapper.LibInchiGetAtomIsotopicMass(i));
    		
    		int radical = wrapper.LibInchiGetAtomRadical(i);
    		if (radical == 0) {
    			atom.setRadical(INCHI_RADICAL.NONE);
    		} else if (radical == 1) {
    			atom.setRadical(INCHI_RADICAL.SINGLET);
    		} else if (radical == 2) {
    			atom.setRadical(INCHI_RADICAL.DOUBLET);
    		} else if (radical == 3) {
    			atom.setRadical(INCHI_RADICAL.TRIPLET);
    		} else {
    			throw new JniInchiException("Unknown radical state: " + radical);
    		}
    		
    		output.addAtom(atom);
    		//atom.debug();
    		
    		int numBonds = wrapper.LibInchiGetAtomNumBonds(i);
    		for (int j = 0; j < numBonds; j ++) {
    			int neighbour = wrapper.LibInchiGetAtomNeighbour(i, j);
    			int order = wrapper.LibInchiGetAtomBondType(i, j);
    			int stereo = wrapper.LibInchiGetAtomBondStereo(i, j);
    			
    			bondTypes[i][neighbour] = order;
    			bondStereos[i][neighbour] = stereo;
    		}
    	}
    	
    	// Generate bonds
    	for (int i = 0; i < numAtoms; i ++) {
    		for (int j = 0; j < i; j ++) {
    			int bo0 = bondTypes[i][j];
    			int bo1 = bondTypes[j][i];
    			if (bo0 != bo1) {
    				throw new JniInchiException("Bond order mismatch: " + i + "-" + j);
    			}
    			if (bo0 > 0) {
    				INCHI_BOND_TYPE type = null;
    				if (bo0 == 1) {
    					type = INCHI_BOND_TYPE.SINGLE;
    				} else if (bo0 == 2) {
    					type = INCHI_BOND_TYPE.DOUBLE;
    				} else if (bo0 == 3) {
    					type = INCHI_BOND_TYPE.TRIPLE;
    				} else if (bo0 == 4) {
    					type = INCHI_BOND_TYPE.ALTERN;
    				} else {
    					throw new JniInchiException("Unknown bond type: " + bo0);
    				}
    				
    				int bs0 = bondStereos[i][j];
    				int bs1 = bondStereos[j][i];
    				
    				if (bs0 != bs1) {
        				throw new JniInchiException("Bond stereo mismatch: " + i + "-" + j);
        			}
    				
    				INCHI_BOND_STEREO stereo = null;
    				if (bs0 == 0) {
    					stereo = INCHI_BOND_STEREO.NONE;
    				} else if (bs0 == 1) {
    					stereo = INCHI_BOND_STEREO.SINGLE_1UP;
    				} else if (bs0 == 4) {
    					stereo = INCHI_BOND_STEREO.SINGLE_1EITHER;
    				} else if (bs0 == 6) {
    					stereo = INCHI_BOND_STEREO.SINGLE_1DOWN;
    				} else if (bs0 == 3) {
    					stereo = INCHI_BOND_STEREO.DOUBLE_EITHER;
    				} else  {
    					throw new JniInchiException("Unknown bond stereo: " + bs0);
    				}
    				
    				JniInchiBond bond = new JniInchiBond(output.getAtom(i), output.getAtom(j), type, stereo);
    				
    				output.addBond(bond);
    				//bond.debug();
    			}
    		}
    	}
    	
    	// Generate stereo parities
    	for (int i = 0; i < numStereo; i ++) {
    		int centralAt = wrapper.LibInchiGetStereoCentralAtom(i);
    		int at0 = wrapper.LibInchiGetStereoNeighbourAtom(i, 0);
    		int at1 = wrapper.LibInchiGetStereoNeighbourAtom(i, 1);
    		int at2 = wrapper.LibInchiGetStereoNeighbourAtom(i, 2);
    		int at3 = wrapper.LibInchiGetStereoNeighbourAtom(i, 3);
    		
    		int type = wrapper.LibInchiGetStereoType(i);
    		int parity = wrapper.LibInchiGetStereoParity(i);
    		
    		INCHI_STEREOTYPE stereoType;
    		if (type == 0) {
    			stereoType = INCHI_STEREOTYPE.NONE;
    		} else if (type == 1) {
    			stereoType = INCHI_STEREOTYPE.DOUBLEBOND;
    		} else if (type == 2) {
    			stereoType = INCHI_STEREOTYPE.TETRAHEDRAL;
    		} else if (type == 3) {
    			stereoType = INCHI_STEREOTYPE.ALLENE;
    		} else {
    			throw new JniInchiException("Unknown stereo0D type: " + type);
    		}
    		
    		INCHI_PARITY stereoParity;
    		if (parity == 0) {
    			stereoParity = INCHI_PARITY.NONE;
    		} else if (parity == 1) {
    			stereoParity = INCHI_PARITY.ODD;
    		} else if (parity == 2) {
    			stereoParity = INCHI_PARITY.EVEN;
    		} else if (parity == 3) {
    			stereoParity = INCHI_PARITY.UNKNOWN;
    		} else if (parity == 4) {
    			stereoParity = INCHI_PARITY.UNDEFINED;
    		} else {
    			throw new JniInchiException("Unknown stereo0D parity: " + parity);
    		}
    		
    		JniInchiStereo0D stereo = new JniInchiStereo0D(
    				stereoType == INCHI_STEREOTYPE.DOUBLEBOND ? null : output.getAtom(centralAt),
    				output.getAtom(at0), output.getAtom(at1), output.getAtom(at2),
    				output.getAtom(at3), stereoType, stereoParity);
    		
    		output.addParity(stereo);
    		//stereo.debug();
    	}
    	
    	wrapper.LibInchiFreeStructMem();
    	
    	return(output);
    }
    

    /**
     * Start new InChI library data record.  See <tt>inchi_api.h</tt> for details.
     * 
     * @param numAtoms		Number of atoms in molecule.
     * @param numStereo		Number of stereo parities defined.
     * @param options		Options string.
     */
    private native void LibInchiStartInput(int numAtoms, int numStereo,
            String options);
    
    /**
     * Add atom definition.  See <tt>inchi_api.h</tt> for details.
     * 
     * @param indx		Atom number
     * @param x			x-coordinate
     * @param y			y-coordinate
     * @param z			z-coordinate
     * @param elname	Element chemical symbol
     * @param isoMass	Isotopic mass
     * @param numH		Number of implicit hydrogens
     * @param numP		Number of implicit 1H
     * @param numD		Number of implicit 2H
     * @param numT		Number of implicit 3H
     * @param radical	Radical definition
     * @param charge	Charge on atom
     */
    private native void LibInchiSetAtom(int indx, double x, double y, double z,
            String elname, int isoMass, int numH, int numP, int numD,
            int numT, int radical, int charge);
    
    /**
     * Set atom neighbours.  See <tt>inchi_api.h</tt> for details.
     * 
     * @param indx		Atom number
     * @param nBonds	Number of bonds 
     * @param neighbors Array of other atom in each bond
     * @param type		Array of type of each bond
     * @param stereo	Array of stereo definition for each bond
     */
    private native void LibInchiSetAtomBonds(int indx, int nBonds, 
            int[] neighbors, int[] type, int[] stereo);
    
    /**
     * Add stereo parity definition.  See <tt>inchi_api.h</tt> for details.
     * 
     * @param indx		Parity Number
     * @param atC		Central atom
     * @param at0		Atom 0
     * @param at1		Atom 1
     * @param at2		Atom 2
     * @param at3		Atom 3
     * @param type		Stereo parity type
     * @param parity	Parity
     */
    private native void LibInchiSetStereo(int indx, int atC, int at0, int at1,
            int at2, int at3, int type, int parity);
    
    /**
     * Generate InChI from loaded data.  See <tt>inchi_api.h</tt> for details.
     * @return	Return status
     */
    private native int LibInchiGenerateInchi();
    
    /**
     * Fetches InChI string from InChI library.
     */
    private native String LibInchiGetInchi();
    
    /**
     * Fetches AuxInfo string from InChI library.
     */
    private native String LibInchiGetAuxInfo();
    
    /**
     * Fetches Message string from InChI library.
     */
    private native String LibInchiGetMessage();
    
    /**
     * Fetches Log string from InChI library.
     */
    private native String LibInchiGetLog();
    
    /**
     * Frees memory used by InChI library.  Must be called once InChI has
     * been generated and all data fetched. 
     */
    private native void LibInchiFreeMem();
    
    
    /**
     * Generates structure from InChI string.
     * @param inchi		InChI string
     * @param options	Options
     * @return		Return status.
     */
    private native int LibInchiGetStruct(String inchi, String options);
    
    private native long LibInchiGetStructWarningFlags00();
    private native long LibInchiGetStructWarningFlags01();
    private native long LibInchiGetStructWarningFlags10();
    private native long LibInchiGetStructWarningFlags11();
    
    /**
     * Frees memory used by InChI library.  Must be called once structure has
     * been generated and all data fetched. 
     */
    private native void LibInchiFreeStructMem();
    
    /**
     * Fetches number of atoms in InChI structure.
     */
    private native int LibInchiGetNumAtoms();
    
    /**
     * Fetches number of stereo parities in InChI structure.
     */
    private native int LibInchiGetNumStereo();
    
    /**
     * Fetches X coordinate of atom.
     * @param atIndx	Atom index number
     */
    private native double LibInchiGetAtomX(int atIndx);
    
    /**
     * Fetches Y coordinate of atom.
     * @param atIndx	Atom index number
     */
    private native double LibInchiGetAtomY(int atIndx);
    
    /**
     * Fetches Z coordinate of atom.
     * @param atIndx	Atom index number
     */
    private native double LibInchiGetAtomZ(int atIndx);
    
    /**
     * Fetches chemical element of atom.
     * @param atIndx	Atom index number
     */
    private native String LibInchiGetAtomElement(int atIndx);
    
    /**
     * Fetches isotopic mass/isotopic mass shift of atom.
     * @param atIndx	Atom index number
     */
    private native int LibInchiGetAtomIsotopicMass(int atIndx);
    
    /**
     * Fetches radical status of atom.
     * @param atIndx	Atom index number
     */
    private native int LibInchiGetAtomRadical(int atIndx);
    
    /**
     * Fetches charge on atom.
     * @param atIndx	Atom index number
     */
    private native int LibInchiGetAtomCharge(int atIndx);
    
    /**
     * Fetches number of implicit hydrogens on atom.
     * @param atIndx	Atom index number
     */
    private native int LibInchiGetAtomImplicitH(int atIndx);
    
    /**
     * Fetches number of implicit protium (1H) on atom.
     * @param atIndx	Atom index number
     */
    private native int LibInchiGetAtomImplicitP(int atIndx);
    
    /**
     * Fetches number of implicit deuterium (2H) on atom.
     * @param atIndx	Atom index number
     */
    private native int LibInchiGetAtomImplicitD(int atIndx);
    
    /**
     * Fetches number of implicit tritum (3H) on atom.
     * @param atIndx	Atom index number
     */
    private native int LibInchiGetAtomImplicitT(int atIndx);
    
    /**
     * Fetches number of bonds on atom.
     * @param atIndx	Atom index number
     */
    private native int LibInchiGetAtomNumBonds(int atIndx);
    
    /**
     * Fetches index of neighbouring atom.
     * @param atIndx	Atom index number
     * @param bondIndx	Bond index number
     */
    private native int LibInchiGetAtomNeighbour(int atIndx, int bondIndx);
    
    /**
     * Fetches bond type (order).
     * @param atIndx	Atom index number
     * @param bondIndx	Bond index number
     */
    private native int LibInchiGetAtomBondType(int atIndx, int bondIndx);
    
    /**
     * Fetches bond stereochemistry.
     * @param atIndx	Atom index number
     * @param bondIndx	Bond index number
     */
    private native int LibInchiGetAtomBondStereo(int atIndx, int bondIndx);
    
    
    private native int LibInchiGetStereoCentralAtom(int stIndex);
    
    private native int LibInchiGetStereoNeighbourAtom(int stIndex, int atIndx);
    
    private native int LibInchiGetStereoType(int stIndex);
    
    private native int LibInchiGetStereoParity(int stIndex);
    
    
    
}
