package org.pvuu.utils.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class ResourceUtils {
    /**
     * Pseudo URL prefix for loading from the class path: "classpath:"
     */
    public static final String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * URL prefix for loading from the file system: "file:"
     */
    public static final String FILE_URL_PREFIX = "file:";

    /**
     * URL protocol for a file in the file system: "file"
     */
    public static final String URL_PROTOCOL_FILE = "file";

    /**
     * URL protocol for an entry from a jar file: "jar"
     */
    public static final String URL_PROTOCOL_JAR = "jar";

    /**
     * URL protocol for an entry from a zip file: "zip"
     */
    public static final String URL_PROTOCOL_ZIP = "zip";

    /**
     * URL protocol for an entry from a JBoss jar file: "vfszip"
     */
    public static final String URL_PROTOCOL_VFSZIP = "vfszip";

    /**
     * URL protocol for a JBoss VFS resource: "vfs"
     */
    public static final String URL_PROTOCOL_VFS = "vfs";

    /**
     * URL protocol for an entry from a WebSphere jar file: "wsjar"
     */
    public static final String URL_PROTOCOL_WSJAR = "wsjar";

    /**
     * URL protocol for an entry from an OC4J jar file: "code-source"
     */
    public static final String URL_PROTOCOL_CODE_SOURCE = "code-source";

    /**
     * Separator between JAR URL and file path within the JAR
     */
    public static final String JAR_URL_SEPARATOR = "!/";

    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: isUrl</ p>
     * <p>@param ： @param resourceLocation
     * <p>@param ： @return    设定文件</p>
     */
    public static boolean isUrl(String resourceLocation) {
        if (resourceLocation == null) {
            return false;
        }
        if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
            return true;
        }
        try {
            new URL(resourceLocation);
            return true;
        } catch (MalformedURLException ex) {
            return false;
        }
    }

    /**
     * @return URL    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: getURL</ p>
     * <p>@param ： @param resourceLocation
     * <p>@param ： @return
     * <p>@param ： @throws FileNotFoundException    设定文件</p>
     */
    public static URL getURL(String resourceLocation) throws FileNotFoundException {
        Validate.notNull(resourceLocation, "Resource location must not be null");
        if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
            String path = resourceLocation.substring(CLASSPATH_URL_PREFIX.length());
            URL url = ClassUtils.getDefaultClassLoader()
                                .getResource(path);
            if (url == null) {
                String description = "class path resource [" + path + "]";
                throw new FileNotFoundException(
                        description + " cannot be resolved to URL because it does not exist");
            }
            return url;
        }
        try {
            // try URL
            return new URL(resourceLocation);
        } catch (MalformedURLException ex) {
            // no URL -> treat as file path
            try {
                return new File(resourceLocation).toURI()
                                                 .toURL();
            } catch (MalformedURLException ex2) {
                throw new FileNotFoundException("Resource location [" + resourceLocation +
                        "] is neither a URL not a well-formed file path");
            }
        }
    }

    /**
     * @return File    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: getFile</ p>
     * <p>@param ： @param resourceLocation
     * <p>@param ： @return
     * <p>@param ： @throws FileNotFoundException    设定文件</p>
     */
    public static File getFile(String resourceLocation) throws FileNotFoundException {
        Validate.notNull(resourceLocation, "Resource location must not be null");
        if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
            String path = resourceLocation.substring(CLASSPATH_URL_PREFIX.length());
            String description = "class path resource [" + path + "]";
            URL url = ClassUtils.getDefaultClassLoader()
                                .getResource(path);
            if (url == null) {
                throw new FileNotFoundException(
                        description + " cannot be resolved to absolute file path " +
                                "because it does not reside in the file system");
            }
            return getFile(url, description);
        }
        try {
            return getFile(new URL(resourceLocation));
        } catch (MalformedURLException ex) {
            return new File(resourceLocation);
        }
    }

    /**
     * @return File    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: getFile</ p>
     * <p>@param ： @param resourceUrl
     * <p>@param ： @return
     * <p>@param ： @throws FileNotFoundException    设定文件</p>
     */
    public static File getFile(URL resourceUrl) throws FileNotFoundException {
        return getFile(resourceUrl, "URL");
    }

    /**
     * @return File    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: getFile</ p>
     * <p>@param ： @param resourceUrl
     * <p>@param ： @param description
     * <p>@param ： @return
     * <p>@param ： @throws FileNotFoundException    设定文件</p>
     */
    public static File getFile(URL resourceUrl, String description) throws FileNotFoundException {
        Validate.notNull(resourceUrl, "Resource URL must not be null");
        if (!URL_PROTOCOL_FILE.equals(resourceUrl.getProtocol())) {
            throw new FileNotFoundException(
                    description + " cannot be resolved to absolute file path " +
                            "because it does not reside in the file system: " + resourceUrl);
        }
        try {
            return new File(toURI(resourceUrl).getSchemeSpecificPart());
        } catch (URISyntaxException ex) {
            // Fallback for URLs that are not valid URIs (should hardly ever happen).
            return new File(resourceUrl.getFile());
        }
    }

    /**
     * @return File    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: getFile</ p>
     * <p>@param ： @param resourceUri
     * <p>@param ： @return
     * <p>@param ： @throws FileNotFoundException    设定文件</p>
     */
    public static File getFile(URI resourceUri) throws FileNotFoundException {
        return getFile(resourceUri, "URI");
    }

    /**
     * @return File    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: getFile</ p>
     * <p>@param ： @param resourceUri
     * <p>@param ： @param description
     * <p>@param ： @return
     * <p>@param ： @throws FileNotFoundException    设定文件</p>
     */
    public static File getFile(URI resourceUri, String description) throws FileNotFoundException {
        Validate.notNull(resourceUri, "Resource URI must not be null");
        if (!URL_PROTOCOL_FILE.equals(resourceUri.getScheme())) {
            throw new FileNotFoundException(
                    description + " cannot be resolved to absolute file path " +
                            "because it does not reside in the file system: " + resourceUri);
        }
        return new File(resourceUri.getSchemeSpecificPart());
    }

    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: isFileURL</ p>
     * <p>@param ： @param url
     * <p>@param ： @return    设定文件</p>
     */
    public static boolean isFileURL(URL url) {
        String protocol = url.getProtocol();
        return (URL_PROTOCOL_FILE.equals(protocol) || protocol.startsWith(URL_PROTOCOL_VFS));
    }

    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: isJarURL</ p>
     * <p>@param ： @param url
     * <p>@param ： @return    设定文件</p>
     */
    public static boolean isJarURL(URL url) {
        String protocol = url.getProtocol();
        return (URL_PROTOCOL_JAR.equals(protocol) ||
                URL_PROTOCOL_ZIP.equals(protocol) ||
                URL_PROTOCOL_WSJAR.equals(protocol) ||
                (URL_PROTOCOL_CODE_SOURCE.equals(protocol) && url.getPath()
                                                                 .contains(JAR_URL_SEPARATOR)));
    }

    /**
     * @return URL    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: extractJarFileURL</ p>
     * <p>@param ： @param jarUrl
     * <p>@param ： @return
     * <p>@param ： @throws MalformedURLException    设定文件</p>
     */
    public static URL extractJarFileURL(URL jarUrl) throws MalformedURLException {
        String urlFile = jarUrl.getFile();
        int separatorIndex = urlFile.indexOf(JAR_URL_SEPARATOR);
        if (separatorIndex != -1) {
            String jarFile = urlFile.substring(0, separatorIndex);
            try {
                return new URL(jarFile);
            } catch (MalformedURLException ex) {
                // Probably no protocol in original jar URL, like "jar:C:/mypath/myjar.jar".
                // This usually indicates that the jar file resides in the file system.
                if (!jarFile.startsWith("/")) {
                    jarFile = "/" + jarFile;
                }
                return new URL(FILE_URL_PREFIX + jarFile);
            }
        } else {
            return jarUrl;
        }
    }

    /**
     * @return URI    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: toURI</ p>
     * <p>@param ： @param url
     * <p>@param ： @return
     * <p>@param ： @throws URISyntaxException    设定文件</p>
     */
    public static URI toURI(URL url) throws URISyntaxException {
        return toURI(url.toString());
    }

    /**
     * @return URI    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: toURI</ p>
     * <p>@param ： @param location
     * <p>@param ： @return
     * <p>@param ： @throws URISyntaxException    设定文件</p>
     */
    public static URI toURI(String location) throws URISyntaxException {
        return new URI(StringUtils.replace(location, " ", "%20"));
    }

}
