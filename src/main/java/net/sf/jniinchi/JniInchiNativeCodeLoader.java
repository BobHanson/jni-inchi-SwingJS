package net.sf.jniinchi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

public class JniInchiNativeCodeLoader {
    
    private static final boolean DEBUG = false;
    
    private static final int CURRENT_NATIVE_VERSION_MAJOR = 1;
    private static final int CURRENT_NATIVE_VERSION_MINOR = 4;
    
    // Property names
    protected final static String P_PROPERTIES_PATH = "jniinchi.properties.path";
    protected final static String P_NATIVECODE_PATH = "jniinchi.nativecode.path";
    protected final static String P_AUTOEXTRACT = "jniinchi.autoextract";
    
    private final static String PROPERTIES_FILENAME = "jniinchi.properties";
    
    private static String[] INCHI_LIB_NAMES = {null, "libinchi.dll", "libinchi.so"};
    private static String[] JNI_LIB_PREFIX = {null, "JniInchi." , "libJniInchi."};
    private static String[] JNI_LIB_SUFFIX = {null, ".dll", ".so"};

    
    private final Properties properties;
    
    private final Environment env;

    protected String jniFilename;
    protected String inchiFilename;

    protected File jniFile;
    protected File inchiFile;

    protected StringBuffer log = new StringBuffer();

    
    
    
    /**
     * Singleton.
     */
    private static JniInchiNativeCodeLoader loader;

    /**
     * Singleton getter.
     * @return
     */
    public static JniInchiNativeCodeLoader getLoader() throws LoadNativeLibraryException {
        if (loader == null) {
            loader = new JniInchiNativeCodeLoader();
        }

        return loader;
    }

    
    
    

    /**
     * Constructor. Sets/detects properties.
     */
    private JniInchiNativeCodeLoader() throws LoadNativeLibraryException {
        // Detect environment
        env = Environment.getEnvironment();
        
        // Check platform has been recognised
        if (env.platform == Environment.PLAT_UNKNOWN) {
            die("Unknown platform");
        }

        // Find required library versions
        String version = getVersionString();
        log("Looking for native code version: " + version);

        // Determine native library filenames
        inchiFilename = INCHI_LIB_NAMES[env.platform];
        jniFilename = JNI_LIB_PREFIX[env.platform] + version + JNI_LIB_SUFFIX[env.platform];
        log("JNI library filename: " + jniFilename);
        log("InChI filename: " + inchiFilename);
        
        // Load properties
        properties = loadProperties();
    }
    
    /**
     * Loads property values. First, defaults are set. These are overridden by
     * a values found in a properties file (if it exists), which are in turn
     * overridden by values set at runtime.
     */
    private Properties loadProperties() {
        // Create properties structure
        Properties defaults = new Properties();
        // Loaded properties override defaults
        Properties loaded = new Properties(defaults);
        // System properties override loaded and default
        Properties runtime = new Properties(loaded);
        
        // Set default values
        log("Setting default property values");
        defaults.setProperty(P_PROPERTIES_PATH,
                System.getProperty("user.home") + File.pathSeparator
                + env.classRootDirectory); 
        defaults.setProperty(P_NATIVECODE_PATH, 
                env.classRootDirectory + File.pathSeparator
                + env.currentWorkingDirectory + File.pathSeparator
                + System.getProperty("java.library.path", ""));
        defaults.setProperty(P_AUTOEXTRACT, "true");
        
        // Import system set values
        log("Searching for system properties");
        for (Object o : defaults.keySet()) {
            String key = (String) o;
            String value = System.getProperty(key);
            if (value != null) {
                log("Found system propery " + key + " = " + value);
                runtime.setProperty(key, value);
            }
        }
        
        // Load properties file
        log("Searching for properties file");
        String searchPath = runtime.getProperty(P_PROPERTIES_PATH);
        for (String path : searchPath.split(File.pathSeparator)) {
            File f = new File(path, PROPERTIES_FILENAME);
            if (f.exists()) {
                log("Loading properties from " + f.getAbsolutePath());
                try {
                    loaded.load(new FileInputStream(f));
                } catch (IOException ioe) {
                    log("Failed to load properties. " + ioe.getMessage());
                }
                break;
            }
        }
        
        return runtime;
    }
    

    public void loadNativeCode_() throws LoadNativeLibraryException {
        String path = findNativeFilesPath();
        if (path == null) {
            if (env.usingJarFile && Boolean.valueOf(properties.getProperty(P_AUTOEXTRACT))) {
                path = extractNativeFiles();
            }
        }
        
        if (path != null) {
            loadNativeCode(path);
        } else {
            // Print error message
        }
    }

    
    private String findNativeFilesPath() {
        log("Searching for native libraries");
        String searchPath = properties.getProperty(P_NATIVECODE_PATH);
        for (String path : searchPath.split(File.pathSeparator)) {
            File jniLib = new File(path, jniFilename);
            File inchiLib = new File(path, inchiFilename);
            if (jniLib.exists() && inchiLib.exists()) {
                log("Native libraries found: " + path);
                return path;
            }
        }
        log("Native libraries not found");
        return null;
    }
    
    private String extractNativeFiles() {
        return null;
    }
    
    private void loadNativeCode(String path) throws LoadNativeLibraryException {
        File inchiFile = new File(path, inchiFilename);
        File jniFile = new File(path, jniFilename);
        
        // Load InChI native code
        try {
            log("Loading InChI library");
            System.load(inchiFile.getAbsolutePath());
        } catch (UnsatisfiedLinkError ule) {
            die("Error loading InChI library: " + ule.getMessage());
        }

        // Load JNI InChI native code
        try {
            log("Loading JNI library");
            System.load(jniFile.getAbsolutePath());
        } catch (UnsatisfiedLinkError ule) {
            die("Error loading JNI InChI library: " + ule.getMessage());
        }

        // Check version match
        try {
            log("Checking correct version is loaded");
            int majorVersion = JniInchiWrapper.LibInchiGetVersionMajor();
            int minorVersion = JniInchiWrapper.LibInchiGetVersionMinor();

            if ( CURRENT_NATIVE_VERSION_MAJOR != majorVersion
              || CURRENT_NATIVE_VERSION_MINOR != minorVersion) {
                die("Native code is version " + majorVersion + "." + minorVersion
                  + "; expected " + CURRENT_NATIVE_VERSION_MAJOR + "." + CURRENT_NATIVE_VERSION_MINOR);

            }
        } catch (UnsatisfiedLinkError ule) {
            die("Error getting native code version - cannot find native method: " + ule.getMessage());
        }
    }

    






    protected void log(String message) {
        log.append(message + "\n");
        if (DEBUG) System.err.println(message);
    }

    /**
     * Constructor. Checks platform is compatable, and locates native files.
     *
     */
    private JniInchiNativeCodeLoader(int i) throws LoadNativeLibraryException {
        properties = null;
        
        log("Detecting environment");
        env = new Environment();

        // Check platform has been recognised
        if (env.platform == Environment.PLAT_UNKNOWN) {
            die("Unknown platform");
        }

        // Find required library versions
        String version = getVersionString();
        log("Looking for native code version: " + version);


        // Determine native library filenames
        inchiFilename = INCHI_LIB_NAMES[env.platform];
        jniFilename = JNI_LIB_PREFIX[env.platform] + version + JNI_LIB_SUFFIX[env.platform];
        log("JNI library file: " + jniFilename);
        log("InChI file: " + inchiFilename);

        log("Searching for JNI file");
        // Search for JNI library file
        if (!findJniFile()) {
            log("JNI file not found");

            // JniInchi native library not found
            if (env.usingJarFile) {
                log("Running from JAR file - checking for libraries");

                // Try to autoplace from jar
                autoplaceJniFile();

                // See if InChI library file is already in location
                log("Checking for InChI file in " + jniFile.getParentFile().getAbsolutePath());
                if (!checkForInchiFile()) {
                    log("InChI file not found");
                    autoplaceInchiFile();
                }

            } else {
                die("Unable to locate native libraries");
            }
        } else {
            log("JNI file found: " + jniFile.toString());
            log("Checking for InChI file in " + jniFile.getParentFile().getAbsolutePath());
            if (!checkForInchiFile()) {
                log("InChI file not found");
            }
            log("InChI file found: " + inchiFile.toString());
        }
    }


    protected void autoplaceJniFile() throws LoadNativeLibraryException {
        // Check for library inside jar
        log("Looking for JNI file in jar");
        ClassLoader cldr = this.getClass().getClassLoader();
        URL url = cldr.getResource(jniFilename);
        if (url == null) {
            die("JNI file not found");
        }

        // Try autoplacing JNI library in class root directory
        jniFile = new File(env.classRootDirectory, jniFilename);
        log("Copying JNI file to " + env.classRootDirectory.getAbsolutePath());

        try {
            copyStreamToFile(url.openStream(), jniFile);
        } catch (IOException ioe) {
            log("Failed: " + ioe.getMessage());
            if (jniFile.exists()) {
                jniFile.delete();
            }

            if (!env.classRootDirectory.equals(env.currentWorkingDirectory)) {
                // Try autoplacing JNI library in current working directory
                jniFile = new File(env.classRootDirectory, jniFilename);
                log("Copying JNI file to " + env.currentWorkingDirectory.getAbsolutePath());

                try {
                    copyStreamToFile(url.openStream(), jniFile);
                } catch (IOException ioe2) {
                    log("Failed: " + ioe2.getMessage());
                    if (jniFile.exists()) {
                        jniFile.delete();
                    }

                    die("Unable to autoplace JNI file");
                }
            }
        }
    }

    protected void autoplaceInchiFile() throws LoadNativeLibraryException {
        // Check for library inside jar
        log("Looking for InChI file in jar");
        ClassLoader cldr = this.getClass().getClassLoader();
        URL url = cldr.getResource(inchiFilename);
        if (url == null) {
            die("InChI file not found");
        }

        // Try autoplacing InChI file in same directory as JNI file
        inchiFile = new File(jniFile.getParentFile(), inchiFilename);
        log("Copying InChI file to " + env.classRootDirectory.getAbsolutePath());
        try {
            copyStreamToFile(url.openStream(), inchiFile);
        } catch (IOException ioe) {
            log("Failed: " + ioe.getMessage());
            die("Unable to autoplace InChI file");
        }
    }







    protected void copyStreamToFile(InputStream in, File file) throws IOException {
        byte[] bytes = new byte[1024];
        int n;

        OutputStream out = new FileOutputStream(file);
        while ((n = in.read(bytes)) > -1) {
            out.write(bytes, 0, n);
        }
        out.flush();
        out.close();
        in.close();
    }


    /**
     * Returns string representation of current native code version.
     * @return
     */
    protected static String getVersionString() {
        return(CURRENT_NATIVE_VERSION_MAJOR + "." + CURRENT_NATIVE_VERSION_MINOR);
    }

    /**
     * Searches for JNI native library file.
     * @return
     */
    protected boolean findJniFile() {
        int i = 0;
        while (i < env.librarySearchLocations.size()) {
            File dir = (File) env.librarySearchLocations.get(i);
            File f = new File(dir, jniFilename);
            if (f.exists()) {
                // JNI library found
                jniFile = f;
                break;
            }
            i ++;
        }

        return(jniFile != null);
    }

    protected boolean checkForInchiFile() {
        if (jniFile != null) {
            File f = new File(jniFile.getParent(), inchiFilename);
            if (f.exists()) {
                inchiFile = f;
            }
        }

        return(inchiFile != null);
    }

    protected boolean findInchiFile() {
        if (inchiFile == null) {
            int i = 0;
            while (i < env.librarySearchLocations.size()) {
                File dir = (File) env.librarySearchLocations.get(i);
                File f = new File(dir, inchiFilename);
                if (f.exists()) {
                    // JNI library found
                    inchiFile = f;
                    break;
                }
                i ++;
            }
        }

        return(inchiFile != null);
    }


    private void loadNativeCode() throws LoadNativeLibraryException {
        // Load InChI native code
        try {
            log("Loading InChI library");
            System.load(inchiFile.getAbsolutePath());
        } catch (UnsatisfiedLinkError ule) {
            die("Error loading InChI library: " + ule.getMessage());
        }

        // Load JNI InChI native code
        try {
            log("Loading JNI library");
            System.load(jniFile.getAbsolutePath());
        } catch (UnsatisfiedLinkError ule) {
            die("Error loading JNI InChI library: " + ule.getMessage());
        }

        // Check version match
        try {
            log("Checking correct version is loaded");
            int majorVersion = JniInchiWrapper.LibInchiGetVersionMajor();
            int minorVersion = JniInchiWrapper.LibInchiGetVersionMinor();

            if ( CURRENT_NATIVE_VERSION_MAJOR != majorVersion
              || CURRENT_NATIVE_VERSION_MINOR != minorVersion) {
                die("Native code is version " + majorVersion + "." + minorVersion
                  + "; expected " + CURRENT_NATIVE_VERSION_MAJOR + "." + CURRENT_NATIVE_VERSION_MINOR);

            }
        } catch (UnsatisfiedLinkError ule) {
            die("Error getting native code version: " + ule.getMessage());
        }
    }

    protected void die(String message) throws LoadNativeLibraryException {
        System.err.println();
        System.err.println("JNI InChI has failed to load the native libraries required.");
        System.err.println();
        System.err.println("INFORMATION:");
        //                  01234567890123456789012345678901234567890123456789012345678901234567890123456789
        System.err.println("The most likely problem is that the PATH or LD_LIBRARY_PATH environmental");
        System.err.println("variables are not correctly configured. If you have placed the native library");
        System.err.println("files yourself, then unless you have placed them in either the current working");
        System.err.println("directory or the directory containing the JNI-InChI class files or JAR file,");
        System.err.println("then the location must be included in the PATH, if you are using Windows, or");
        System.err.println("LD_LIBRARY_PATH, if you are using Linux, environmental variable. The list of");
        System.err.println("locations that JNI-InChI is currently searching is shown below. Both the JNI");
        System.err.println("and the InChI library must be in the same directory.");
        System.err.println();
        //					01234567890123456789012345678901234567890123456789012345678901234567890123456789
        System.err.println("If you are using the JNI-InChI JAR, then JNI-InChI will attempt to automatically");
        System.err.println("place the native files either in the directory containing the JAR file, or in");
        System.err.println("the current working directory.");
        System.err.println();
        System.err.println("If java is finding the native files, but is failing to load them then they may");
        System.err.println("need to be recompiled for your system");
        System.err.println();
        System.err.println("ERROR MESSAGE:");
        System.err.println(message);
        System.err.println();
        System.err.println("LOG:");
        System.err.println(log);
        System.err.println();
        System.err.println("CURRENT ENVIRONMENT:");
        env.debug();

        throw new LoadNativeLibraryException("Failed to load native code. See STDERR for details.");
    }
}


/**
 * Provides information of the current operating environment.
 */
class Environment {

    private static Environment environment;
    
    private final String CLASSFILE = this.getClass().getCanonicalName().replace(".", File.separator) + ".class";

    protected static final int PLAT_UNKNOWN = 0;
    protected static final int PLAT_WINDOWS = 1;
    protected static final int PLAT_LINUX = 2;

    protected static String[] PLATFORM_NAME = {"UNKNOWN", "WINDOWS", "LINUX"};

    protected static final int ARCH_UNKNOWN = 0;
    protected static final int ARCH_X86 = 1;
    protected static final int ARCH_A64 = 2;

    protected static String[] ARCHITECTURE_NAME = {"UNKNOWN", "x86", "A64"};

    protected int platform;
    protected int architecture;

    protected File currentWorkingDirectory;
    protected File classRootDirectory;
    protected List librarySearchLocations;

    protected boolean usingJarFile;
    protected File jarFile;

    
    
    
    
    public static Environment getEnvironment() {
        if (environment == null) {
            environment = new Environment();
        }
        return environment;
    }
    
    
    
    
    
    public Environment() {
        findPlatform();
        findArchitecture();
        findClassRootDirectory();
        findCurrentWorkingDirectory();
        findLibrarySearchLocations();
    }


    /**
     * Detects which operating system is running.
     * @return
     */
    protected int findPlatform() {
        String osname = System.getProperty("os.name", "").toLowerCase();
        if (osname.startsWith("windows")) {
            platform = PLAT_WINDOWS;
        } else if (osname.startsWith("linux")) {
            platform = PLAT_LINUX;
        } else {
            platform = PLAT_UNKNOWN;
        }

        return(platform);
    }

    /**
     * Detects which architecture the sytem is running.
     * @return
     */
    protected int findArchitecture() {
        String osarch = System.getProperty("os.arch", "").toLowerCase();
        if (osarch.endsWith("86")) {
            architecture = ARCH_X86;
        } else if (osarch.equals("a64")) {
            architecture = ARCH_A64;
        } else {
            architecture = ARCH_UNKNOWN;
        }

        return(architecture);
    }

    /**
     * Gets absolute path to current working directory.
     * @return
     */
    protected File findCurrentWorkingDirectory() {
        File f = new File(".");
        String abspath = f.getAbsolutePath();
        if (abspath.endsWith(File.separator + ".")) {
            abspath = abspath.substring(0, abspath.length() - 2);
            f = new File(abspath);
        }
        currentWorkingDirectory = f.getAbsoluteFile();
        return(currentWorkingDirectory);
    }

    /**
     * Gets directory that contains class files or jar file.
     * @return
     */
    protected File findClassRootDirectory() {
        ClassLoader cldr = this.getClass().getClassLoader();
        // Find this class
        URL clUrl = cldr.getResource(CLASSFILE);
        String classFilePath = clUrl.toString();
        try {
            classFilePath = URLDecoder.decode(classFilePath, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            // Decode manually
            int indx;
            while ((indx = classFilePath.indexOf('%')) > -1) {
                if (classFilePath.length() > indx + 1) {
                    String lccfp = classFilePath.toLowerCase();
                    char ch = lccfp.charAt(indx + 1);
                    char cl = lccfp.charAt(indx + 2);
                    int ih = "0123456789abcdef".indexOf(ch);
                    int il = "0123456789abcdef".indexOf(cl);
                    if (ih > -1 && il > -1) {
                        classFilePath = classFilePath.substring(0, indx)
                            + ((char) (16 * ih + il))
                            + classFilePath.substring(indx + 3);
                    }
                }
            }
        }

        // Check if loaded from jar
        if (classFilePath.startsWith("jar:file")) {
            usingJarFile = true;
            String path = classFilePath.substring(9, classFilePath.indexOf("!"));
            jarFile = new File(path);
            classRootDirectory = jarFile.getParentFile().getAbsoluteFile();
        } else {
            usingJarFile = false;
            String path = classFilePath.substring(5, classFilePath.indexOf(CLASSFILE));
            classRootDirectory = new File(path).getAbsoluteFile();
        }

        return(classRootDirectory);
    }

    /**
     * Gets list of paths that Java will search to load native libraries.
     * @return
     */
    protected List findLibrarySearchLocations() {
        librarySearchLocations = new ArrayList();

        // Put code root and current working directory at top of seach list
        librarySearchLocations.add(classRootDirectory);
        if (!classRootDirectory.equals(currentWorkingDirectory)) {
            librarySearchLocations.add(currentWorkingDirectory);
        }

        String librarySearchPath = System.getProperty("java.library.path");
        String pathSep = File.pathSeparator;
        String fileSep = File.separator;

        StringTokenizer tok = new StringTokenizer(librarySearchPath, pathSep);
        while (tok.hasMoreTokens()) {
            String path = tok.nextToken();

            // Remove wrapping quotes
            if (path.startsWith("\"") && path.endsWith("\"")) {
                path = path.substring(1, path.length() - 1);
            }

            // Remove trailing '/.'
            File f = new File(path);
            String abspath = f.getAbsolutePath();
            if (abspath.endsWith(fileSep + ".")) {
                abspath = abspath.substring(0, abspath.length() - 2);
                f = new File(abspath);
            }
            if (!librarySearchLocations.contains(f)) {
                librarySearchLocations.add(f);
            }
        }

        return(librarySearchLocations);
    }

    public void debug() {
        System.err.println("Platform: " + PLATFORM_NAME[platform]);
        System.err.println("Architecture: " + ARCHITECTURE_NAME[architecture]);
        System.err.println("Current Working Directory: " + currentWorkingDirectory);
        System.err.println("Class Root Directory: " + classRootDirectory);
        System.err.println("Using JAR: " + usingJarFile);
        if (usingJarFile) {
            System.err.println("JAR location: " + jarFile.getAbsolutePath());
        }
        System.err.println("Library search locations:");
        for (int i = 0; i < librarySearchLocations.size(); i ++) {
            System.err.println("  " + ((File)librarySearchLocations.get(i)).getAbsolutePath());
        }

    }
}
