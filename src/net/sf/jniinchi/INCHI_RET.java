/* File: INCHI_RET.java
 * Author: Sam Adams
 * 
 * Copyright (C) 2006 Sam Adams
 */
package net.sf.jniinchi;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Type-safe enumeration of InChI return codes.
 * 
 * <p>InChI library return values:<br>
 * <tt>
 * SKIP     (-2)    Not used in InChI library<br>
 * EOF      (-1)    No structural data has been provided<br>
 * OKAY     (0)     Success, no errors or warnings<br>
 * WARNING  (1)     Success, warning(s) issued<br>
 * ERROR    (2)     Error: no InChI has been created<br>
 * FATAL    (3)     Severe error: no InChI has been created (typically,
 *                  memory allocation failure)<br>
 * UNKNOWN  (4)     Unknown program error<br>
 * BUSY     (5)     Previous call to InChI has not returned yet<br>
 * </tt>
 * <p>See <tt>inchi_api.h</tt>.
 * @author Sam Adams
 */
public class INCHI_RET {
    
    // --- MUST BE BEFORE DECLARATIONS ---
    /**
     * List of return types.
     */
    private static List list = new ArrayList(18);
    // ---
    
    
    /**
     * Not used in InChI library.
     */
    public static final INCHI_RET SKIP      = new INCHI_RET("SKIP", -2);
    
    /**
     * No structural data has been provided.
     */
    public static final INCHI_RET EOF       = new INCHI_RET("EOF", -1);
    
    /**
     * Success; no errors or warnings.
     */
    public static final INCHI_RET OKAY      = new INCHI_RET("OKAY", 0);
    
    /**
     * Success; warning(s) issued.
     */
    public static final INCHI_RET WARNING   = new INCHI_RET("WARNING", 1);
    
    /**
     * Error: no InChI has been created.
     */
    public static final INCHI_RET ERROR     = new INCHI_RET("ERROR", 2);
    
    /**
     * Severe error: no InChI has been created (typically, memory allocation failure).
     */
    public static final INCHI_RET FATAL     = new INCHI_RET("FATAL", 3);
    
    /**
     * Unknown program error.
     */
    public static final INCHI_RET UNKNOWN   = new INCHI_RET("UNKNOWN", 4);
    
    /**
     * Previuos call to InChI has not returned yet.
     */
    public static final INCHI_RET BUSY      = new INCHI_RET("BUSY", 5);
    
    
    
    
    /**
     * Name.
     */
    private final String name;
    
    /**
     * Internal InChI index (from inchi_api.h).
     */
    private final int indx;
    
    /**
     * Constructor.
     */
    private INCHI_RET(String name, int indx) { this.name = name; this.indx = indx; list.add(this); };
    
    public int getIndx() {
        return indx;
    }
    
    public String toString() {
        return name;
    }
    
    protected static List getList() {
        return list;
    }
}
