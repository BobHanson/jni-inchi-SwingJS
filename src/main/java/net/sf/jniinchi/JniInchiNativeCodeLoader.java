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

    private static boolean DEBUG = false;

    private static final int CURRENT_NATIVE_VERSION_MAJOR = 1;

    private static final int CURRENT_NATIVE_VERSION_MINOR = 4;

    // Property names
    protected final static String P_PROPERTIES_PATH = "jniinchi.properties.path";

    protected final static String P_NATIVECODE_PATH = "jniinchi.nativecode.path";

    protected final static String P_AUTOEXTRACT = "jniinchi.autoextract";

    protected final static String P_AUTOEXTRACT_PATH = "jniinchi.autoextract.path";

    private final static String PROPERTIES_FILENAME = "jniinchi.properties";

    private static String[] INCHI_LIB_NAMES = { null, "libinchi.1.01.00.dll",
            "libinchi.so.1.01.00" };

    private static String[] JNI_LIB_PREFIX = { null, "libJniInchi.",
            "libJniInchi." };

    private static String[] JNI_LIB_SUFFIX = { null, ".dll", "so." };

    private final Properties properties;

    private final Environment env;

    protected String jniFilename;

    protected String inchiFilename;

    protected File jniFile;

    protected File inchiFile;
    
    private boolean loaded = false;

    /**
     * Singleton.
     */
    private static JniInchiNativeCodeLoader loader;

    /**
     * Singleton getter.
     * 
     * @return
     */
    public static JniInchiNativeCodeLoader getLoader()
            throws LoadNativeLibraryException {
        if (loader == null) {
            loader = new JniInchiNativeCodeLoader();
        }

        return loader;
    }

    /**
     * Sets debug mode.
     * 
     * @param debug
     */
    public static void setDebug(boolean debug) {
        DEBUG = debug;
        Environment.setDebug(DEBUG);
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
        log("Expecting native code version: " + version);

        // Determine native library filenames
        inchiFilename = INCHI_LIB_NAMES[env.platform];
        if (env.platform == Environment.PLAT_WINDOWS) {
            jniFilename = JNI_LIB_PREFIX[env.platform] + version
                    + JNI_LIB_SUFFIX[env.platform];
        } else if (env.platform == Environment.PLAT_LINUX) {
            jniFilename = JNI_LIB_PREFIX[env.platform]
                    + JNI_LIB_SUFFIX[env.platform] + version;
        }
        log("Files: [" + jniFilename + ", " + inchiFilename + "]");

        // Load properties
        properties = loadProperties();
    }

    /**
     * Loads property values. First, defaults are set. These are overridden by a
     * values found in a properties file (if it exists), which are in turn
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
        defaults.setProperty(P_PROPERTIES_PATH, System.getProperty("user.home")
                + File.pathSeparator + env.classRootDirectory);
        defaults.setProperty(P_NATIVECODE_PATH, env.classRootDirectory
                + File.pathSeparator + env.currentWorkingDirectory
                + File.pathSeparator
                + System.getProperty("java.library.path", ""));
        defaults.setProperty(P_AUTOEXTRACT, "true");
        defaults.setProperty(P_AUTOEXTRACT_PATH, env.classRootDirectory
                + File.pathSeparator + env.currentWorkingDirectory
                + File.pathSeparator);

        // Import system set values
        log("Searching for runtime properties");
        for (Object o : defaults.keySet()) {
            String key = (String) o;
            String value = System.getProperty(key);
            if (value != null) {
                log("Found runtime propery " + key + " = " + value);
                runtime.setProperty(key, value);
            }
        }
        if (runtime.containsKey(P_NATIVECODE_PATH)) {
            runtime.setProperty(P_AUTOEXTRACT_PATH, runtime
                    .getProperty(P_NATIVECODE_PATH));
        } else {
            runtime.remove(P_AUTOEXTRACT_PATH);
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
        if (loaded.containsKey(P_NATIVECODE_PATH)) {
            loaded.setProperty(P_AUTOEXTRACT_PATH, runtime
                    .getProperty(P_NATIVECODE_PATH));
        } else {
            loaded.remove(P_AUTOEXTRACT_PATH);
        }

        return runtime;
    }

    /**
     * Load native libraries.
     * 
     * @throws LoadNativeLibraryException
     *             if loading fails.
     */
    public void load() throws LoadNativeLibraryException {
        if (!loaded) {
            if (!findNativeFiles()) {
                boolean autoExtract = Boolean.valueOf(properties
                        .getProperty(P_AUTOEXTRACT));
                if (env.usingJarFile) {
                    log("Running from JAR file");
                    if (autoExtract) {
                        log("Auto-extract enabled");
                        extractNativeFiles();
                    } else {
                        log("Auto-extract disabled");
                    }
                } else {
                    log("Not running from JAR file");
                }
            }
    
            if (jniFile != null && inchiFile != null) {
                loadNativeLibraries();
            } else {
                log("Native libraries not found");
                // Print error message
                throw new LoadNativeLibraryException();
            }
            
            loaded = true;
        }
    }

    /**
     * Searches for native files.
     * 
     * @return True if both files found, otherwise false.
     */
    private boolean findNativeFiles() {
        log("Searching for native libraries - " + properties.getProperty(P_NATIVECODE_PATH));
        findJniFile();
        findInchiFile();
        return (jniFile != null) && (inchiFile != null);
    }

    /**
     * Searches for JNI native library file. Looks in the locations stored in
     * the jniinchi.nativecode.path property.
     * 
     * @return The JNI file if found, otherwise null.
     */
    private File findJniFile() {
        jniFile = null;

        String[] searchPaths = properties.getProperty(P_NATIVECODE_PATH).split(
                File.pathSeparator);
        for (String path : searchPaths) {
            File f = new File(path, jniFilename);
            if (f.exists()) {
                // JNI library found
                jniFile = f;
                break;
            }
        }

        if (jniFile != null) {
            log("JNI file found: " + jniFile.getAbsolutePath());
        } else {
            log("JNI file not found");
        }

        return jniFile;
    }

    /**
     * Searches for InChI native library file. Looks first in the directory
     * containing the JNI native library, and then in the locations stored in
     * the jniinchi.nativecode.path property.
     * 
     * @return The InChI file if found, otherwise null.
     */
    private File findInchiFile() {
        inchiFile = null;

        if (jniFile != null) {
            File f = new File(jniFile.getParent(), inchiFilename);
            if (f.exists()) {
                inchiFile = f;
            }
        }
        if (inchiFile == null) {
            String[] searchPaths = properties.getProperty(P_NATIVECODE_PATH)
                    .split(File.pathSeparator);
            for (String path : searchPaths) {
                File f = new File(path, inchiFilename);
                if (f.exists()) {
                    // InChI library found
                    inchiFile = f;
                    break;
                }
            }
        }

        if (inchiFile != null) {
            log("InChI file found: " + inchiFile.getAbsolutePath());
        } else {
            log("InChI file not found");
        }

        return inchiFile;
    }

    /**
     * Extract native libraries from jar file.
     * 
     * @throws LoadNativeLibraryException
     */
    private void extractNativeFiles() throws LoadNativeLibraryException {
        if (jniFile == null) {
            extractJniFile();
            if (inchiFile != null) {
                if (!inchiFile.getParentFile().equals(jniFile.getParentFile())) {
                    log("Resetting InChI file location");
                    inchiFile = null;
                }
            }
        }
        if (inchiFile == null) {
            extractInchiFile();
        }
    }

    /**
     * Load libraries in inchiFile and jniFile.
     * 
     * @throws LoadNativeLibraryException
     */
    private void loadNativeLibraries() throws LoadNativeLibraryException {

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

            if (CURRENT_NATIVE_VERSION_MAJOR != majorVersion
                    || CURRENT_NATIVE_VERSION_MINOR != minorVersion) {
                die("Native code is version " + majorVersion + "."
                        + minorVersion + "; expected "
                        + CURRENT_NATIVE_VERSION_MAJOR + "."
                        + CURRENT_NATIVE_VERSION_MINOR);

            }
        } catch (UnsatisfiedLinkError ule) {
            die("Error getting native code version - cannot find native method: "
                    + ule.getMessage());
        }

        log("Native code loaded");
    }

    /**
     * Print message, if in debug mode.
     * 
     * @param message
     */
    private void log(String message) {
        if (DEBUG)
            System.out.println(message);
    }

    /**
     * Extract JNI library file from jar.
     * 
     * @throws LoadNativeLibraryException
     */
    private void extractJniFile() throws LoadNativeLibraryException {
        // Check for library inside jar
        log("Getting JNI file from jar");
        ClassLoader cldr = this.getClass().getClassLoader();
        URL url = cldr.getResource(jniFilename);
        if (url == null) {
            die("JNI file not found");
        }

        // Try autoplacing JNI library
        String[] paths = properties.getProperty(P_AUTOEXTRACT_PATH).split(
                File.pathSeparator);
        for (String path : paths) {
            File f = new File(path, jniFilename);
            log("Copying JNI file to " + f.getAbsolutePath());
            try {
                copyStreamToFile(url.openStream(), f);
                jniFile = f;
                break;
            } catch (IOException ioe) {
                log("Failed: " + ioe.getMessage());
            }
        }

        if (jniFile == null) {
            die("Failed to extract JNI file");
        }
    }

    /**
     * Extract InChI library file from jar.
     * 
     * @throws LoadNativeLibraryException
     */
    private void extractInchiFile() throws LoadNativeLibraryException {
        // Check for library inside jar
        log("Getting InChI file from jar");
        ClassLoader cldr = this.getClass().getClassLoader();
        URL url = cldr.getResource(inchiFilename);
        if (url == null) {
            die("InChI file not found");
        }

        // Try autoplacing InChI file in same directory as JNI file
        File f = new File(jniFile.getParentFile(), inchiFilename);
        log("Copying InChI file to " + f.getAbsolutePath());
        try {
            copyStreamToFile(url.openStream(), f);
            inchiFile = f;
        } catch (IOException ioe) {
            log("Failed: " + ioe.getMessage());

            // Try paths from properties
            String[] paths = properties.getProperty(P_AUTOEXTRACT_PATH).split(
                    File.pathSeparator);
            for (String path : paths) {
                f = new File(path, inchiFilename);
                log("Copying InChI file to " + f.getAbsolutePath());
                try {
                    copyStreamToFile(url.openStream(), f);
                    inchiFile = f;
                    break;
                } catch (IOException ioe2) {
                    log("Failed: " + ioe2.getMessage());
                }
            }
        }

        if (inchiFile == null) {
            die("Failed to extract InChI file");
        }
    }

    /**
     * Writes the contents of an input stream to a file.
     * 
     * @param in
     * @param file
     * @throws IOException
     */
    private void copyStreamToFile(InputStream in, File file) throws IOException {
        byte[] bytes = new byte[1024];
        int n;

        OutputStream out = new FileOutputStream(file);
        try {
            while ((n = in.read(bytes)) > -1) {
                out.write(bytes, 0, n);
            }
            out.flush();
        } finally {
            try {
                out.close();
            } finally {
                in.close();
            }
        }
    }

    /**
     * Returns string representation of current native code version.
     * 
     * @return
     */
    private static String getVersionString() {
        return (CURRENT_NATIVE_VERSION_MAJOR + "." + CURRENT_NATIVE_VERSION_MINOR);
    }

    private void die(String message) throws LoadNativeLibraryException {

        System.err.println();
        System.err
                .println("JNI InChI has failed to load the native libraries required.");
        System.err.println();
        System.err
                .println("The most common problems are either the native library files being placed in");
        System.err
                .println("locations that JNI InChI does not know to search, or needing recompiling for");
        System.err.println("your system.");
        // 01234567890123456789012345678901234567890123456789012345678901234567890123456789
        System.err.println();
        System.err.println("ERROR MESSAGE: " + message);
        System.err.println();
        throw new LoadNativeLibraryException(
                "Failed to load native code. See STDERR for details.");
    }

    /**
     * Provides information of the current operating environment.
     */
    static class Environment {
        
        private static boolean DEBUG = false;

        private static Environment environment;

        private final Class CLAZZ = JniInchiNativeCodeLoader.class;

        private final String CLASSFILE = CLAZZ.getCanonicalName().replace(".",
                "/")
                + ".class";

        protected static final int PLAT_UNKNOWN = 0;

        protected static final int PLAT_WINDOWS = 1;

        protected static final int PLAT_LINUX = 2;

        protected static String[] PLATFORM_NAME = { "UNKNOWN", "WINDOWS",
                "LINUX" };

        protected static final int ARCH_UNKNOWN = 0;

        protected static final int ARCH_X86 = 1;

        protected static final int ARCH_A64 = 2;

        protected static String[] ARCHITECTURE_NAME = { "UNKNOWN", "x86", "A64" };

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
        
        public static void setDebug(boolean debug) {
            DEBUG = debug;
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
         * 
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
            
            log("Detected plaform " + PLATFORM_NAME[platform]);

            return platform;
        }

        /**
         * Detects which architecture the sytem is running.
         * 
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
            
            log("Detected architecture " + ARCHITECTURE_NAME[architecture]);

            return architecture;
        }

        /**
         * Gets absolute path to current working directory.
         * 
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
            log("CWD: " + currentWorkingDirectory);
            return (currentWorkingDirectory);
        }

        /**
         * Gets directory that contains class files or jar file.
         * 
         * @return
         */
        protected File findClassRootDirectory() {
            ClassLoader cldr = CLAZZ.getClassLoader();
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
                String path = classFilePath.substring(9, classFilePath
                        .indexOf("!"));
                jarFile = new File(path);
                classRootDirectory = jarFile.getParentFile().getAbsoluteFile();
            } else {
                usingJarFile = false;
                String path = classFilePath.substring(5, classFilePath
                        .indexOf(CLASSFILE));
                classRootDirectory = new File(path).getAbsoluteFile();
            }
            
            log("Root dir: " + classRootDirectory);
            log("Using jar file: " + usingJarFile);

            return (classRootDirectory);
        }

        /**
         * Gets list of paths that Java will search to load native libraries.
         * 
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

            StringTokenizer tok = new StringTokenizer(librarySearchPath,
                    pathSep);
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

            return (librarySearchLocations);
        }
        
        /**
         * Print message, if in debug mode.
         * 
         * @param message
         */
        private void log(String message) {
            if (DEBUG)
                System.out.println(message);
        }
        
    }

}
