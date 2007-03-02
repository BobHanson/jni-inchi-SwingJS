package net.sf.jniinchi;

public interface IInchiGenerator {

    public String getInchi();

    public String getAuxInfo();

    public String getMessage();

    public String getLog();

    public INCHI_RET getReturnStatus();

}
