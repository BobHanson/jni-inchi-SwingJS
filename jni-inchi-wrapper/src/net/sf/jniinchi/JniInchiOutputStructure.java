package net.sf.jniinchi;


public class JniInchiOutputStructure extends JniInchiStructure {
	
	INCHI_RET retStatus;
	
    String message;
	
	String log;
	
	long[][] warningFlags = new long[2][2];
	
	
	
	
	
	/* InChI -> Structure * /
	typedef struct tagINCHI_OutputStruct {
	    /* 4 pointers are allocated by GetStructFromINCHI()     * /
	    /* to deallocate all of them call FreeStructFromINCHI() * /
	    inchi_Atom     *atom;         /* array of num_atoms elements * / 
	    inchi_Stereo0D *stereo0D;     /* array of num_stereo0D 0D stereo elements or NULL  * /
	    AT_NUM          num_atoms;    /* number of atoms in the structure < 1024 * /
	    AT_NUM          num_stereo0D; /* number of 0D stereo elements * /
	    char           *szMessage;    /* Error/warning ASCIIZ message * /
	    char           *szLog;        /* log-file ASCIIZ string, contains a human-readable list * /
	                                  /* of recognized options and possibly an Error/warning message * /
	    unsigned long  WarningFlags[2][2]; /* warnings, see INCHIDIFF in inchicmp.h * /
	                                       /* [x][y]: x=0 => Reconnected if present in InChI otherwise Disconnected/Normal
	                                                  x=1 => Disconnected layer if Reconnected layer is present
	                                                  y=1 => Main layer or Mobile-H
	                                                  y=0 => Fixed-H layer
	                                        * /
	}inchi_OutputStruct;
	*/
}
