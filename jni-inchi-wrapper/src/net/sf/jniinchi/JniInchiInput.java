package net.sf.jniinchi;

import java.util.ArrayList;
import java.util.List;

public class JniInchiInput extends JniInchiStructure {
	
    /**
     * Options string,
     */
    String options;
    
    
    public JniInchiInput(String opts) throws JniInchiException {
    	this.options = JniInchiWrapper.checkOptions(opts);
    }
    
    public JniInchiInput(List opts) throws JniInchiException {
    	this.options = JniInchiWrapper.checkOptions(opts);
    }
    
    public String getOptions() {
    	return(options);
    }
}
