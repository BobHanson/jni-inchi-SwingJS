package net.sf.jniinchi;

/**
 * @author sea36
 */
public enum INCHI_STATUS {

    VALID_STANDARD(0),
    VALID_NON_STANDARD(-1),
    INVALID_PREFIX(1),
    INVALID_VERSION(2),
    INVALID_LAYOUT(3),
    FAIL_I2I(4);

    /**
     * Internal InChI index (from inchi_api.h).
     */
    private final int indx;

    /**
     * Constructor.
     */
    private INCHI_STATUS(final int indx) {
        this.indx = indx;
    }

    public int getIndx() {
        return indx;
    }

    public static INCHI_STATUS getValue(int value) {
        switch (value) {
            case -1:
                return VALID_NON_STANDARD;
            case 0:
                return VALID_STANDARD;
            case 1:
                return INVALID_PREFIX;
            case 2:
                return INVALID_VERSION;
            case 3:
                return INVALID_LAYOUT;
            case 4:
                return FAIL_I2I;
            default:
                return null;
        }
    }

}
