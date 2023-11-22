package org.pvuu.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pvuu.utils.tools.*;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.core.MethodParameter;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.util.*;

public class BeanUtils {
    private static final Log logger = LogFactory.getLog(BeanUtils.class);

    private static final Map<Class<?>, Boolean> unknownEditorTypes = Collections
            .synchronizedMap(new WeakHashMap<Class<?>, Boolean>());

    /**
     * @param ： @param <T>
     *          <p>
     * @param ： @param clazz
     *          <p>
     * @param ： @return
     *          <p>
     * @param ： @throws BeanInstantiationException 设定文件
     *          </p>
     * @return T 返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: instantiate</ p>
     * <p>
     */
    public static <T> T instantiate(Class<T> clazz)
            throws BeanInstantiationException {
        Validate.notNull(clazz, "Class must not be null");
        if (clazz.isInterface()) {
            throw new BeanInstantiationException(clazz,
                    "Specified class is an interface");
        }
        try {
            return clazz.newInstance();
        } catch (InstantiationException ex) {
            throw new BeanInstantiationException(clazz,
                    "Is it an abstract class?", ex);
        } catch (IllegalAccessException ex) {
            throw new BeanInstantiationException(clazz,
                    "Is the constructor accessible?", ex);
        }
    }

    /**
     * @param ： @param <T>
     *          <p>
     * @param ： @param clazz
     *          <p>
     * @param ： @return
     *          <p>
     * @param ： @throws BeanInstantiationException 设定文件
     *          </p>
     * @return T 返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: instantiateClass</ p>
     * <p>
     */
    public static <T> T instantiateClass(Class<T> clazz)
            throws BeanInstantiationException {
        Validate.notNull(clazz, "Class must not be null");
        if (clazz.isInterface()) {
            throw new BeanInstantiationException(clazz,
                    "Specified class is an interface");
        }
        try {
            return instantiateClass(clazz.getDeclaredConstructor());
        } catch (NoSuchMethodException ex) {
            throw new BeanInstantiationException(clazz,
                    "No default constructor found", ex);
        }
    }

    /**
     * @param ： @param <T>
     *          <p>
     * @param ： @param ctor
     *          <p>
     * @param ： @param args
     *          <p>
     * @param ： @return
     *          <p>
     * @param ： @throws BeanInstantiationException 设定文件
     *          </p>
     * @return T 返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: instantiateClass</ p>
     * <p>
     */
    public static <T> T instantiateClass(Constructor<T> ctor, Object... args)
            throws BeanInstantiationException {
        Validate.notNull(ctor, "Constructor must not be null");
        try {
            ReflectionUtils.makeAccessible(ctor);
            return ctor.newInstance(args);
        } catch (InstantiationException ex) {
            throw new BeanInstantiationException(ctor.getDeclaringClass(),
                    "Is it an abstract class?", ex);
        } catch (IllegalAccessException ex) {
            throw new BeanInstantiationException(ctor.getDeclaringClass(),
                    "Is the constructor accessible?", ex);
        } catch (IllegalArgumentException ex) {
            throw new BeanInstantiationException(ctor.getDeclaringClass(),
                    "Illegal arguments for constructor", ex);
        } catch (InvocationTargetException ex) {
            throw new BeanInstantiationException(ctor.getDeclaringClass(),
                    "Constructor threw exception", ex.getTargetException());
        }
    }

    /**
     * @param ： @param clazz
     *          <p>
     * @param ： @param methodName
     *          <p>
     * @param ： @param paramTypes
     *          <p>
     * @param ： @return 设定文件
     *          </p>
     * @return Method 返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: findMethod</ p>
     * <p>
     */
    public static Method findMethod(Class<?> clazz, String methodName,
                                    Class<?>... paramTypes) {
        try {
            return clazz.getMethod(methodName, paramTypes);
        } catch (NoSuchMethodException ex) {
            return findDeclaredMethod(clazz, methodName, paramTypes);
        }
    }

    /**
     * @param ： @param clazz
     *          <p>
     * @param ： @param methodName
     *          <p>
     * @param ： @param paramTypes
     *          <p>
     * @param ： @return 设定文件
     *          </p>
     * @return Method 返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: findDeclaredMethod</ p>
     * <p>
     */
    public static Method findDeclaredMethod(Class<?> clazz, String methodName,
                                            Class<?>[] paramTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, paramTypes);
        } catch (NoSuchMethodException ex) {
            if (clazz.getSuperclass() != null) {
                return findDeclaredMethod(clazz.getSuperclass(), methodName,
                        paramTypes);
            }
            return null;
        }
    }

    /**
     * @param ： @param clazz
     *          <p>
     * @param ： @param methodName
     *          <p>
     * @param ： @return
     *          <p>
     * @param ： @throws IllegalArgumentException 设定文件
     *          </p>
     * @return Method 返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: findMethodWithMinimalParameters</ p>
     * <p>
     */
    public static Method findMethodWithMinimalParameters(Class<?> clazz,
                                                         String methodName) throws IllegalArgumentException {

        Method targetMethod = findMethodWithMinimalParameters(clazz
                .getMethods(), methodName);
        if (targetMethod == null) {
            targetMethod = findDeclaredMethodWithMinimalParameters(clazz,
                    methodName);
        }
        return targetMethod;
    }

    /**
     * @param ： @param clazz
     *          <p>
     * @param ： @param methodName
     *          <p>
     * @param ： @return
     *          <p>
     * @param ： @throws IllegalArgumentException 设定文件
     *          </p>
     * @return Method 返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: findDeclaredMethodWithMinimalParameters</ p>
     * <p>
     */
    public static Method findDeclaredMethodWithMinimalParameters(
            Class<?> clazz, String methodName) throws IllegalArgumentException {

        Method targetMethod = findMethodWithMinimalParameters(clazz
                .getDeclaredMethods(), methodName);
        if (targetMethod == null && clazz.getSuperclass() != null) {
            targetMethod = findDeclaredMethodWithMinimalParameters(clazz
                    .getSuperclass(), methodName);
        }
        return targetMethod;
    }

    /**
     * @param ： @param methods
     *          <p>
     * @param ： @param methodName
     *          <p>
     * @param ： @return
     *          <p>
     * @param ： @throws IllegalArgumentException 设定文件
     *          </p>
     * @return Method 返回类型</p>
     * @throws
     * @Description: TODO)
     * @方法名: findMethodWithMinimalParameters</ p>
     * <p>
     */
    public static Method findMethodWithMinimalParameters(Method[] methods,
                                                         String methodName) throws IllegalArgumentException {

        Method targetMethod = null;
        int numMethodsFoundWithCurrentMinimumArgs = 0;
        for (Method method : methods) {
            if (method.getName()
                      .equals(methodName)) {
                int numParams = method.getParameterTypes().length;
                if (targetMethod == null
                        || numParams < targetMethod.getParameterTypes().length) {
                    targetMethod = method;
                    numMethodsFoundWithCurrentMinimumArgs = 1;
                } else {
                    if (targetMethod.getParameterTypes().length == numParams) {
                        // Additional candidate with same length.
                        numMethodsFoundWithCurrentMinimumArgs++;
                    }
                }
            }
        }
        if (numMethodsFoundWithCurrentMinimumArgs > 1) {
            throw new IllegalArgumentException(
                    "Cannot resolve method '"
                            + methodName
                            + "' to a unique method. Attempted to resolve to overloaded method with "
                            + "the least number of parameters, but there were "
                            + numMethodsFoundWithCurrentMinimumArgs
                            + " candidates.");
        }
        return targetMethod;
    }

    /**
     * @param ： @param signature
     *          <p>
     * @param ： @param clazz
     *          <p>
     * @param ： @return 设定文件
     *          </p>
     * @return Method 返回类型</p>
     * @throws
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @方法名: resolveSignature</ p>
     * <p>
     */
    public static Method resolveSignature(String signature, Class<?> clazz) {
        Validate.hasText(signature, "'signature' must not be empty");
        Validate.notNull(clazz, "Class must not be null");

        int firstParen = signature.indexOf("(");
        int lastParen = signature.indexOf(")");

        if (firstParen > -1 && lastParen == -1) {
            throw new IllegalArgumentException("Invalid method signature '"
                    + signature + "': expected closing ')' for args list");
        } else if (lastParen > -1 && firstParen == -1) {
            throw new IllegalArgumentException("Invalid method signature '"
                    + signature + "': expected opening '(' for args list");
        } else if (firstParen == -1 && lastParen == -1) {
            return findMethodWithMinimalParameters(clazz, signature);
        } else {
            String methodName = signature.substring(0, firstParen);
            String[] parameterTypeNames = StringUtils
                    .commaDelimitedListToStringArray(signature.substring(
                            firstParen + 1, lastParen));
            Class<?>[] parameterTypes = new Class[parameterTypeNames.length];
            for (int i = 0; i < parameterTypeNames.length; i++) {
                String parameterTypeName = parameterTypeNames[i].trim();
                try {
                    parameterTypes[i] = ClassUtils.forName(parameterTypeName,
                            clazz.getClassLoader());
                } catch (Throwable ex) {
                    throw new IllegalArgumentException(
                            "Invalid method signature: unable to resolve type ["
                                    + parameterTypeName + "] for argument " + i
                                    + ". Root cause: " + ex);
                }
            }
            return findMethod(clazz, methodName, parameterTypes);
        }
    }

    /**
     * @param ： @param clazz
     *          <p>
     * @param ： @return
     *          <p>
     * @param ： @throws BeansException 设定文件
     *          </p>
     * @return PropertyDescriptor[] 返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: getPropertyDescriptors</ p>
     * <p>
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz)
            throws BeansException {
        CachedIntrospectionResults cr = CachedIntrospectionResults
                .forClass(clazz);
        return cr.getPropertyDescriptors();
    }

    /**
     * @param ： @param clazz
     *          <p>
     * @param ： @param propertyName
     *          <p>
     * @param ： @return
     *          <p>
     * @param ： @throws BeansException 设定文件
     *          </p>
     * @return PropertyDescriptor 返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: getPropertyDescriptor</ p>
     * <p>
     */
    public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz,
                                                           String propertyName) throws BeansException {

        CachedIntrospectionResults cr = CachedIntrospectionResults
                .forClass(clazz);
        return cr.getPropertyDescriptor(propertyName);
    }

    /**
     * @param ： @param method
     *          <p>
     * @param ： @return
     *          <p>
     * @param ： @throws BeansException 设定文件
     *          </p>
     * @return PropertyDescriptor 返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: findPropertyForMethod</ p>
     * <p>
     */
    public static PropertyDescriptor findPropertyForMethod(Method method)
            throws BeansException {
        Validate.notNull(method, "Method must not be null");
        PropertyDescriptor[] pds = getPropertyDescriptors(method
                .getDeclaringClass());
        for (PropertyDescriptor pd : pds) {
            if (method.equals(pd.getReadMethod())
                    || method.equals(pd.getWriteMethod())) {
                return pd;
            }
        }
        return null;
    }

    /**
     * @param ： @param targetType
     *          <p>
     * @param ： @return 设定文件
     *          </p>
     * @return PropertyEditor 返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: findEditorByConvention</ p>
     * <p>
     */
    public static PropertyEditor findEditorByConvention(Class<?> targetType) {
        if (targetType == null || targetType.isArray()
                || unknownEditorTypes.containsKey(targetType)) {
            return null;
        }
        ClassLoader cl = targetType.getClassLoader();
        if (cl == null) {
            try {
                cl = ClassLoader.getSystemClassLoader();
                if (cl == null) {
                    return null;
                }
            } catch (Throwable ex) {
                // e.g. AccessControlException on Google App Engine
                if (logger.isDebugEnabled()) {
                    logger.debug("Could not access system ClassLoader: " + ex);
                }
                return null;
            }
        }
        String editorName = targetType.getName() + "Editor";
        try {
            Class<?> editorClass = cl.loadClass(editorName);
            if (!PropertyEditor.class.isAssignableFrom(editorClass)) {
                if (logger.isWarnEnabled()) {
                    logger
                            .warn("Editor class ["
                                    + editorName
                                    + "] does not implement [java.beans.PropertyEditor] interface");
                }
                unknownEditorTypes.put(targetType, Boolean.TRUE);
                return null;
            }
            return (PropertyEditor) instantiateClass(editorClass);
        } catch (ClassNotFoundException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug("No property editor [" + editorName
                        + "] found for type " + targetType.getName()
                        + " according to 'Editor' suffix convention");
            }
            unknownEditorTypes.put(targetType, Boolean.TRUE);
            return null;
        }
    }

    /**
     * @param ： @param propertyName
     *          <p>
     * @param ： @param beanClasses
     *          <p>
     * @param ： @return 设定文件
     *          </p>
     * @return Class<?> 返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: findPropertyType</ p>
     * <p>
     */
    public static Class<?> findPropertyType(String propertyName,
                                            Class<?>[] beanClasses) {
        if (beanClasses != null) {
            for (Class<?> beanClass : beanClasses) {
                PropertyDescriptor pd = getPropertyDescriptor(beanClass,
                        propertyName);
                if (pd != null) {
                    return pd.getPropertyType();
                }
            }
        }
        return Object.class;
    }

    /**
     * @param ： @param pd
     *          <p>
     * @param ： @return 设定文件
     *          </p>
     * @return MethodParameter 返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: getWriteMethodParameter</ p>
     * <p>
     */
    public static MethodParameter getWriteMethodParameter(PropertyDescriptor pd) {
        if (pd instanceof GenericTypeAwarePropertyDescriptor) {
            return new MethodParameter(
                    ((GenericTypeAwarePropertyDescriptor) pd)
                            .getWriteMethodParameter());
        } else {
            return new MethodParameter(pd.getWriteMethod(), 0);
        }
    }

    /**
     * @param ： @param clazz
     *          <p>
     * @param ： @return 设定文件
     *          </p>
     * @return boolean 返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: isSimpleProperty</ p>
     * <p>
     */
    public static boolean isSimpleProperty(Class<?> clazz) {
        Validate.notNull(clazz, "Class must not be null");
        return isSimpleValueType(clazz)
                || (clazz.isArray() && isSimpleValueType(clazz
                .getComponentType()));
    }

    /**
     * @param ： @param clazz
     *          <p>
     * @param ： @return 设定文件
     *          </p>
     * @return boolean 返回类型</p>
     * @throws
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @方法名: isSimpleValueType</ p>
     * <p>
     */
    public static boolean isSimpleValueType(Class<?> clazz) {
        return ClassUtils.isPrimitiveOrWrapper(clazz) || clazz.isEnum()
                || CharSequence.class.isAssignableFrom(clazz)
                || Number.class.isAssignableFrom(clazz)
                || Date.class.isAssignableFrom(clazz)
                || clazz.equals(URI.class) || clazz.equals(URL.class)
                || clazz.equals(Locale.class) || clazz.equals(Class.class);
    }

    /**
     * @param ： @param source
     *          <p>
     * @param ： @param target
     *          <p>
     * @param ： @throws BeansException 设定文件
     *          </p>
     * @return void 返回类型</p>
     * @throws
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @方法名: copyProperties</ p>
     * <p>
     */
    public static void copyProperties(Object source, Object target)
            throws BeansException {
        copyProperties(source, target, null, null);
    }

    /**
     * @param ： @param source
     *          <p>
     * @param ： @param target
     *          <p>
     * @param ： @param editable
     *          <p>
     * @param ： @throws BeansException 设定文件
     *          </p>
     * @return void 返回类型</p>
     * @throws
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @方法名: copyProperties</ p>
     * <p>
     */
    public static void copyProperties(Object source, Object target,
                                      Class<?> editable) throws BeansException {

        copyProperties(source, target, editable, null);
    }

    /**
     * @param ： @param source
     *          <p>
     * @param ： @param target
     *          <p>
     * @param ： @param ignoreProperties
     *          <p>
     * @param ： @throws BeansException 设定文件
     *          </p>
     * @return void 返回类型</p>
     * @throws
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @方法名: copyProperties</ p>
     * <p>
     */
    public static void copyProperties(Object source, Object target,
                                      String[] ignoreProperties) throws BeansException {

        copyProperties(source, target, null, ignoreProperties);
    }

    private static void copyProperties(Object source, Object target,
                                       Class<?> editable, String[] ignoreProperties) throws BeansException {

        Validate.notNull(source, "Source must not be null");
        Validate.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class ["
                        + target.getClass()
                                .getName()
                        + "] not assignable to Editable class ["
                        + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null) ? Arrays
                .asList(ignoreProperties) : null;

        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null
                    && (ignoreProperties == null || (!ignoreList
                    .contains(targetPd.getName())))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source
                        .getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass()
                                                         .getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        Method writeMethod = targetPd.getWriteMethod();
                        if (!Modifier.isPublic(writeMethod.getDeclaringClass()
                                                          .getModifiers())) {
                            writeMethod.setAccessible(true);
                        }
                        writeMethod.invoke(target, value);
                    } catch (Throwable ex) {
                        throw new FatalBeanException(
                                "Could not copy properties from source to target",
                                ex);
                    }
                }
            }
        }
    }
}
