package net.sf.jniinchi;

public class LoadNativeLibraryException extends JniInchiException {

private static final long serialVersionUID = 2L;

    /**
     * Constructor.
     */
    public LoadNativeLibraryException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message
     */
    public LoadNativeLibraryException(final String message) {
        super(message);
    }

}
