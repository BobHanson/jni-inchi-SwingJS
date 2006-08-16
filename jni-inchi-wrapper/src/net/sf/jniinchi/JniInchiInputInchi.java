package net.sf.jniinchi;

public class JniInchiInputInchi {
	
	/**
	 * InChI ASCIIZ string to be converted to a strucure
	 */
	String inchiString;
	
	/**
	 * InChI options: space-delimited
	 */
	String options;
	
	
	public JniInchiInputInchi(String inchi, String opts) {
		this.inchiString = inchi;
		this.options = opts;
	}
}
