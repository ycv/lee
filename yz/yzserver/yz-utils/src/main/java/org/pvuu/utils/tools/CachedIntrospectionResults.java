package org.pvuu.utils.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.*;

@SuppressWarnings("unchecked")
public class CachedIntrospectionResults {
    /**
     * Set of ClassLoaders that this CachedIntrospectionResults class will always
     * accept classes from, even if the classes do not qualify as cache-safe.
     */
    static final Set<ClassLoader> acceptedClassLoaders = Collections.synchronizedSet(new HashSet<ClassLoader>());
    /**
     * Map keyed by class containing CachedIntrospectionResults.
     * Needs to be a WeakHashMap with WeakReferences as values to allow
     * for proper garbage collection in case of multiple class loaders.
     */
    static final Map<Class, Object> classCache = Collections.synchronizedMap(new WeakHashMap<Class, Object>());
    private static final Log logger = LogFactory.getLog(CachedIntrospectionResults.class);
    /**
     * The BeanInfo object for the introspected bean class
     */
    private final BeanInfo beanInfo;
    /**
     * PropertyDescriptor objects keyed by property name String
     */
    private final Map<String, PropertyDescriptor> propertyDescriptorCache;

    /**
     * Create a new CachedIntrospectionResults instance for the given class.
     *
     * @param beanClass the bean class to analyze
     * @throws BeansException in case of introspection failure
     */
    private CachedIntrospectionResults(Class beanClass, boolean cacheFullMetadata) throws BeansException {
        try {
            if (logger.isTraceEnabled()) {
                logger.trace("Getting BeanInfo for class [" + beanClass.getName() + "]");
            }
            this.beanInfo = Introspector.getBeanInfo(beanClass);

            // Immediately remove class from Introspector cache, to allow for proper
            // garbage collection on class loader shutdown - we cache it here anyway,
            // in a GC-friendly manner. In contrast to CachedIntrospectionResults,
            // Introspector does not use WeakReferences as values of its WeakHashMap!
            Class classToFlush = beanClass;
            do {
                Introspector.flushFromCaches(classToFlush);
                classToFlush = classToFlush.getSuperclass();
            }
            while (classToFlush != null);

            if (logger.isTraceEnabled()) {
                logger.trace("Caching PropertyDescriptors for class [" + beanClass.getName() + "]");
            }
            this.propertyDescriptorCache = new LinkedHashMap<String, PropertyDescriptor>();

            // This call is slow so we do it once.
            PropertyDescriptor[] pds = this.beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                if (Class.class.equals(beanClass) && "classLoader".equals(pd.getName())) {
                    // Ignore Class.getClassLoader() method - nobody needs to bind to that
                    continue;
                }
                if (logger.isTraceEnabled()) {
                    logger.trace("Found bean property '" + pd.getName() + "'" +
                            (pd.getPropertyType() != null ? " of type [" + pd.getPropertyType()
                                                                             .getName() + "]" : "") +
                            (pd.getPropertyEditorClass() != null ?
                                    "; editor [" + pd.getPropertyEditorClass()
                                                     .getName() + "]" : ""));
                }
                if (cacheFullMetadata) {
                    pd = buildGenericTypeAwarePropertyDescriptor(beanClass, pd);
                }
                this.propertyDescriptorCache.put(pd.getName(), pd);
            }
        } catch (IntrospectionException ex) {
            throw new FatalBeanException("Failed to obtain BeanInfo for class [" + beanClass.getName() + "]", ex);
        }
    }

    /**
     * Accept the given ClassLoader as cache-safe, even if its classes would
     * not qualify as cache-safe in this CachedIntrospectionResults class.
     * <p>This configuration method is only relevant in scenarios where the Spring
     * classes reside in a 'common' ClassLoader (e.g. the system ClassLoader)
     * whose lifecycle is not coupled to the application. In such a scenario,
     * CachedIntrospectionResults would by default not cache any of the application's
     * classes, since they would create a leak in the common ClassLoader.
     * <p>Any <code>acceptClassLoader</code> call at application startup should
     * be paired with a {@link #clearClassLoader} call at application shutdown.
     *
     * @param classLoader the ClassLoader to accept
     */
    public static void acceptClassLoader(ClassLoader classLoader) {
        if (classLoader != null) {
            acceptedClassLoaders.add(classLoader);
        }
    }

    /**
     * Clear the introspection cache for the given ClassLoader, removing the
     * introspection results for all classes underneath that ClassLoader,
     * and deregistering the ClassLoader (and any of its children) from the
     * acceptance list.
     *
     * @param classLoader the ClassLoader to clear the cache for
     */
    public static void clearClassLoader(ClassLoader classLoader) {
        if (classLoader == null) {
            return;
        }
        synchronized (classCache) {
            for (Iterator<Class> it = classCache.keySet()
                                                .iterator(); it.hasNext(); ) {
                Class beanClass = it.next();
                if (isUnderneathClassLoader(beanClass.getClassLoader(), classLoader)) {
                    it.remove();
                }
            }
        }
        synchronized (acceptedClassLoaders) {
            for (Iterator<ClassLoader> it = acceptedClassLoaders.iterator(); it.hasNext(); ) {
                ClassLoader registeredLoader = it.next();
                if (isUnderneathClassLoader(registeredLoader, classLoader)) {
                    it.remove();
                }
            }
        }
    }

    /**
     * Create CachedIntrospectionResults for the given bean class.
     * <P>We don't want to use synchronization here. Object references are atomic,
     * so we can live with doing the occasional unnecessary lookup at startup only.
     *
     * @param beanClass the bean class to analyze
     * @return the corresponding CachedIntrospectionResults
     * @throws BeansException in case of introspection failure
     */
    public static CachedIntrospectionResults forClass(Class beanClass) throws BeansException {
        CachedIntrospectionResults results;
        Object value = classCache.get(beanClass);
        if (value instanceof Reference) {
            Reference ref = (Reference) value;
            results = (CachedIntrospectionResults) ref.get();
        } else {
            results = (CachedIntrospectionResults) value;
        }
        if (results == null) {
            // On JDK 1.5 and higher, it is almost always safe to cache the bean class...
            // The sole exception is a custom BeanInfo class being provided in a non-safe ClassLoader.
            boolean fullyCacheable =
                    ClassUtils.isCacheSafe(beanClass, CachedIntrospectionResults.class.getClassLoader()) ||
                            isClassLoaderAccepted(beanClass.getClassLoader());
            if (fullyCacheable || !ClassUtils.isPresent(beanClass.getName() + "BeanInfo", beanClass.getClassLoader())) {
                results = new CachedIntrospectionResults(beanClass, fullyCacheable);
                classCache.put(beanClass, results);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("Not strongly caching class [" + beanClass.getName() + "] because it is not cache-safe");
                }
                results = new CachedIntrospectionResults(beanClass, true);
                classCache.put(beanClass, new WeakReference<CachedIntrospectionResults>(results));
            }
        }
        return results;
    }

    /**
     * Check whether this CachedIntrospectionResults class is configured
     * to accept the given ClassLoader.
     *
     * @param classLoader the ClassLoader to check
     * @return whether the given ClassLoader is accepted
     * @see #acceptClassLoader
     */
    private static boolean isClassLoaderAccepted(ClassLoader classLoader) {
        // Iterate over array copy in order to avoid synchronization for the entire
        // ClassLoader check (avoiding a synchronized acceptedClassLoaders Iterator).
        ClassLoader[] acceptedLoaderArray =
                acceptedClassLoaders.toArray(new ClassLoader[acceptedClassLoaders.size()]);
        for (ClassLoader registeredLoader : acceptedLoaderArray) {
            if (isUnderneathClassLoader(classLoader, registeredLoader)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the given ClassLoader is underneath the given parent,
     * that is, whether the parent is within the candidate's hierarchy.
     *
     * @param candidate the candidate ClassLoader to check
     * @param parent    the parent ClassLoader to check for
     */
    private static boolean isUnderneathClassLoader(ClassLoader candidate, ClassLoader parent) {
        if (candidate == null) {
            return false;
        }
        if (candidate == parent) {
            return true;
        }
        ClassLoader classLoaderToCheck = candidate;
        while (classLoaderToCheck != null) {
            classLoaderToCheck = classLoaderToCheck.getParent();
            if (classLoaderToCheck == parent) {
                return true;
            }
        }
        return false;
    }

    BeanInfo getBeanInfo() {
        return this.beanInfo;
    }

    Class getBeanClass() {
        return this.beanInfo.getBeanDescriptor()
                            .getBeanClass();
    }

    public PropertyDescriptor getPropertyDescriptor(String name) {
        PropertyDescriptor pd = this.propertyDescriptorCache.get(name);
        if (pd == null && StringUtils.hasLength(name)) {
            // Same lenient fallback checking as in PropertyTypeDescriptor...
            pd = this.propertyDescriptorCache.get(name.substring(0, 1)
                                                      .toLowerCase() + name.substring(1));
            if (pd == null) {
                pd = this.propertyDescriptorCache.get(name.substring(0, 1)
                                                          .toUpperCase() + name.substring(1));
            }
        }
        return (pd == null || pd instanceof GenericTypeAwarePropertyDescriptor ? pd :
                buildGenericTypeAwarePropertyDescriptor(getBeanClass(), pd));
    }

    public PropertyDescriptor[] getPropertyDescriptors() {
        PropertyDescriptor[] pds = new PropertyDescriptor[this.propertyDescriptorCache.size()];
        int i = 0;
        for (PropertyDescriptor pd : this.propertyDescriptorCache.values()) {
            pds[i] = (pd instanceof GenericTypeAwarePropertyDescriptor ? pd :
                    buildGenericTypeAwarePropertyDescriptor(getBeanClass(), pd));
            i++;
        }
        return pds;
    }

    private PropertyDescriptor buildGenericTypeAwarePropertyDescriptor(Class beanClass, PropertyDescriptor pd) {
        try {
            return new GenericTypeAwarePropertyDescriptor(beanClass, pd.getName(), pd.getReadMethod(),
                    pd.getWriteMethod(), pd.getPropertyEditorClass());
        } catch (IntrospectionException ex) {
            throw new FatalBeanException("Failed to re-introspect class [" + beanClass.getName() + "]", ex);
        }
    }

}
