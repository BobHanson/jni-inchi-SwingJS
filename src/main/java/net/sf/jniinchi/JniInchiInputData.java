package net.sf.jniinchi;

/**
 * @author sea36
 */
public class JniInchiInputData {

    private JniInchiInput input;
    private INCHI_RET returnValue;
    private String errorMessage;

    public JniInchiInputData(int returnValue, JniInchiInput input, int chiral, String errorMessage) {
        this.input = input;
        this.returnValue = INCHI_RET.getValue(returnValue);
        this.errorMessage = errorMessage;
    }

    public JniInchiInput getInput() {
        return input;
    }

    public INCHI_RET getReturnValue() {
        return returnValue;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
}
