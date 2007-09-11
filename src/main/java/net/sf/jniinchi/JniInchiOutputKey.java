package net.sf.jniinchi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JniInchiOutputKey {
	
	/**
	 * Group 1 (chars 1-14) first block.
	 * - (char 15)
	 * Group 2 (chars 16-23) second block
	 * Group 3 (char 24) flag
	 * Group 4 (char 15) check char
	 */
	private final static Pattern pattern = Pattern.compile("([A-Z]{14})-([A-Z]{8})([A-Z])([A-Z])");
	
	private final INCHI_KEY retStatus;
	
	private final String key;
	
	private String block1, block2;
	private char flagChar, checkChar;
	
	public JniInchiOutputKey(final INCHI_KEY retStatus, final String key) throws JniInchiException {
		if (retStatus == null) {
			throw new NullPointerException("Null return status");
		}

		if (retStatus == INCHI_KEY.OK) {
			if (key == null) {
				throw new NullPointerException("Null InChIkey");
			}
			
			Matcher m = pattern.matcher(key);
			if (!m.matches()) {
				throw new JniInchiException("Invalid key format: " + key);
			}
			
			block1 = m.group(1);
			block2 = m.group(2);
			flagChar = m.group(3).charAt(0);
			checkChar = m.group(4).charAt(0);
			
			if (flagChar < 'A' || flagChar > 'X') {
				throw new JniInchiException("Invalid flag character: " + flagChar);
			}
			
			
		}
		this.retStatus = retStatus;
		this.key = key;
		
	}
	
	public String getKey() {
		return key;
	}
	
	public INCHI_KEY getReturnStatus() {
		return retStatus;
	}
	
	public String getFirstBlock() {
		return block1;
	}
	
	public String getSecondBlock() {
		return block2;
	}
	
	public char getFlagChar() {
		return flagChar;
	}
	
	public char getCheckChar() {
		return checkChar;
	}
	
	public int getFlagVersion() {
		int version = 0;
		if (flagChar >= 'A' && flagChar <= 'H') {
			version = 1;
		} else if (flagChar >= 'I' && flagChar <= 'P') {
			version = 2;
		} else if (flagChar >= 'Q' && flagChar <= 'X') {
			version = 3;
		}
		return version;
	}
	
	public boolean getFlagIsotopic() {
		boolean hasIsotopic;
		if ("ABCDIJKLQRST".indexOf(flagChar) != -1) {
			hasIsotopic = false;
		} else {
			hasIsotopic = true;
		}
		return hasIsotopic;
	}
	
	public boolean getFlagFixedH() {
		boolean hasFixedH;
		if ("ABEFIJMNQRUV".indexOf(flagChar) != -1) {
			hasFixedH = false;
		} else {
			hasFixedH = true;
		}
		return hasFixedH;
	}
	
	public boolean getFlagStereo() {
		boolean hasStereo;
		if ("ACEGIKMOQSUW".indexOf(flagChar) != -1) {
			hasStereo = false;
		} else {
			hasStereo = true;
		}
		return hasStereo;
	}

}
