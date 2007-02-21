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

    private static final String PROP_PROPERTIES_PATH = "jniinchi.properties.path";
    private static final String PROP_NATIVECODE_PATH = "jniinchi.nativecode.path";
    private static final String PROP_AUTOPLACE = "jniinchi.autoplace";

    /**
     * Singleton.
     */
    private static JniInchiNativeCodeLoader loader;

    /**
     * Singleton getter.
     * @return
     */
    public static JniInchiNativeCodeLoader getLoader() {
        if (loader == null) {
            loader = new JniInchiNativeCodeLoader();
        }

        return loader;
    }

    private boolean debug = true;

    private Properties properties;


    /**
     * Constructor. Sets/detects properties.
     */
    private JniInchiNativeCodeLoader() {
        // Detect environment
        env = new Environment();

        String nativePath = System.getProperty("jniinchi.native.path");
        if (nativePath != null) {
            // TODO
            // findNativeLibrary(nativePath);
        } else {
            // Try reading properties file




        }






        Properties systemProperties = new Properties();
        systemProperties.setProperty(PROP_PROPERTIES_PATH, System.getProperty(PROP_PROPERTIES_PATH));
        systemProperties.setProperty(PROP_NATIVECODE_PATH, System.getProperty(PROP_NATIVECODE_PATH));
        systemProperties.setProperty(PROP_AUTOPLACE, System.getProperty(PROP_AUTOPLACE));

        Properties defaultProperties = new Properties();



        String propPath = System.getProperty("jniinchi.properties.path");
        if (propPath != null) {
            log("System has jniinchi.properties.path set");
        } else {
            propPath = env.classRootDirectory + File.pathSeparator
                + env.currentWorkingDirectory + File.pathSeparator
                + System.getProperty("user.home");
        }
        log("jniinchi.properties search path: " + propPath);

        // Set default properties values
        properties = new Properties();



        // Native code search path
        properties.setProperty("jniinchi.native.path",
            System.getProperty("jniinchi.native.path",
                  env.classRootDirectory + File.pathSeparator
                + env.currentWorkingDirectory + File.pathSeparator
                + System.getProperty("java.library.path")));

        // Auto extract native code if not found
        properties.setProperty("jniinchi.autoextract",
            System.getProperty("jniinchi.autoextract", "true"));

        // Look for jniinchi.properties file
        for (String path : propPath.split(File.pathSeparator)) {
            File f = new File(path + File.separator + "jniinchi.properties");
            if (f.exists()) {
                log("jniinchi.properties file found: " + f.getAbsolutePath());
                try {
                    properties.load(new FileInputStream(f));
                    break;
                } catch (IOException ioe) {
                    // TODO
                }
            }
        }

        properties.list(System.err);


    }


    private String getProperty(String key) {
        String value = System.getProperty(key, properties.getProperty(key, ""));
        return value;
    }





    protected static final int CURRENT_NATIVE_VERSION_MAJOR = 1;
    protected static final int CURRENT_NATIVE_VERSION_MINOR = 4;

    protected static String[] INCHI_LIB_NAMES = {null, "libinchi.dll", "libinchi.so"};
    protected static String[] JNI_LIB_PREFIX = {null, "JniInchi." , "libJniInchi."};
    protected static String[] JNI_LIB_SUFFIX = {null, ".dll", ".so"};



    protected Environment env;

    protected String jniFilename;
    protected String inchiFilename;

    protected File jniFile;
    protected File inchiFile;

    protected StringBuffer log = new StringBuffer();


    protected void log(String message) {
        log.append(message + "\n");
        if (debug) System.err.println(message);
    }

    /**
     * Constructor. Checks platform is compatable, and locates native files.
     *
     */
    public JniInchiNativeCodeLoader(int i) throws LoadNativeLibraryException {
        log("Detecting environment");
        env = new Environment();

        // Check platform has been recognised
        if (env.platform == Environment.PLAT_UNKNOWN) {
            die("Unknown platform");
        }

        // Find required library versions
        String version = getVersionString();
        log("Looking for native code version: " + version);


        // Check for system property
        if (System.getProperty("jniinchi.native.path") != null) {
            // TODO

        }

        // Check for properties file
        if (System.getProperty("jniinchi.properties.path") != null) {
            // TODO

        }

        // Search for properties file
        // 1. Root dir
        // 2. CWD
        // 3. User's home directory
        Properties props = new Properties();
        try {
            props.load(null);
        } catch (IOException ioe) {

        }
        System.getProperties();




        // Search for native library files
        // 1. Root dir
        // 2. CWD
        // 3. library.path



        // Extract from JAR file
        boolean autoplace = true;
        if (System.getProperty("jniinchi.autoplace") != null) {
            autoplace = Boolean.getBoolean("jniinchi.autoplace");
        }
        if (autoplace) {


        }



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


    public void loadNativeCode() throws LoadNativeLibraryException {
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

    private final String CLASSFILE = "net/sf/jniinchi/Environment.class";

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
