package org.pvuu.utils.tools;

import java.lang.reflect.Array;
import java.util.*;


public class CollectionUtils {
    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO(判断一个集合是否为null)
     * @方法名: isEmpty</ p>
     * <p>@param ： @param collection
     * <p>@param ： @return    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }

    @SuppressWarnings("unchecked")
    public static boolean isEmpty(Map map) {
        return (map == null || map.isEmpty());
    }

    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO(判断一个Map是否为null)
     * @方法名: isEmpty</ p>
     * <p>@param ： @param map
     * <p>@param ： @return    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static boolean isMapNullAndSize(Map map) {
        if (null != map && map.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO(判断一个List是否为null)
     * @方法名: isListNullAndSize</ p>
     * <p>@param ： @param list
     * <p>@param ： @return    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static boolean isListNullAndSize(List list) {
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * @return List    返回类型</p>
     * @throws
     * @Description: TODO(将数组转换成List)
     * @方法名: arrayToList</ p>
     * <p>@param ： @param source
     * <p>@param ： @return    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static List arrayToList(Object source) {
        return Arrays.asList(toObjectArray(source));
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO(将数组合并成集合)
     * @方法名: mergeArrayIntoCollection</ p>
     * <p>@param ： @param array
     * <p>@param ： @param collection    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static void mergeArrayIntoCollection(Object array, Collection collection) {
        if (collection == null) {
            throw new IllegalArgumentException("Collection must not be null");
        }
        Object[] arr = toObjectArray(array);
        for (Object elem : arr) {
            collection.add(elem);
        }
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO(Propertiess 转换成 Map)
     * @方法名: mergePropertiesIntoMap</ p>
     * <p>@param ： @param props
     * <p>@param ： @param map    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static void mergePropertiesIntoMap(Properties props, Map map) {
        if (map == null) {
            throw new IllegalArgumentException("Map must not be null");
        }
        if (props != null) {
            for (Enumeration en = props.propertyNames(); en.hasMoreElements(); ) {
                String key = (String) en.nextElement();
                Object value = props.getProperty(key);
                if (value == null) {
                    value = props.get(key);
                }
                map.put(key, value);
            }
        }
    }

    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO(判断一个迭代器 是否包含某个对象)
     * @方法名: contains</ p>
     * <p>@param ： @param iterator
     * <p>@param ： @param element
     * <p>@param ： @return    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static boolean contains(Iterator iterator, Object element) {
        if (iterator != null) {
            while (iterator.hasNext()) {
                Object candidate = iterator.next();
                if (nullSafeEquals(candidate, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO(判断一个Enumeration 是否包含 一个元素对象)
     * @方法名: contains</ p>
     * <p>@param ： @param enumeration
     * <p>@param ： @param element
     * <p>@param ： @return    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static boolean contains(Enumeration enumeration, Object element) {
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                Object candidate = enumeration.nextElement();
                if (nullSafeEquals(candidate, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO(验证给定集合 是否包含给定的元素实例)
     * @方法名: containsInstance</ p>
     * <p>@param ： @param collection
     * <p>@param ： @param element
     * <p>@param ： @return    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static boolean containsInstance(Collection collection, Object element) {
        if (collection != null) {
            for (Object candidate : collection) {
                if (candidate == element) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO(判断一个集合是否 包含另一个集合)
     * @方法名: containsAny</ p>
     * <p>@param ： @param source
     * <p>@param ： @param candidates
     * <p>@param ： @return    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static boolean containsAny(Collection source, Collection candidates) {
        if (isEmpty(source) || isEmpty(candidates)) {
            return false;
        }
        for (Object candidate : candidates) {
            if (source.contains(candidate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return Object    返回类型</p>
     * @throws
     * @Description: TODO(两个集合 判断是否包含 并返回第一个匹配的)
     * @方法名: findFirstMatch</ p>
     * <p>@param ： @param source
     * <p>@param ： @param candidates
     * <p>@param ： @return    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static Object findFirstMatch(Collection source, Collection candidates) {
        if (isEmpty(source) || isEmpty(candidates)) {
            return null;
        }
        for (Object candidate : candidates) {
            if (source.contains(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    /**
     * @return T    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: findValueOfType</ p>
     * <p>@param ： @param <T>
     * <p>@param ： @param collection
     * <p>@param ： @param type
     * <p>@param ： @return    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static <T> T findValueOfType(Collection<?> collection, Class<T> type) {
        if (isEmpty(collection)) {
            return null;
        }
        T value = null;
        for (Object element : collection) {
            if (type == null || type.isInstance(element)) {
                if (value != null) {
                    // More than one value found... no clear single value.
                    return null;
                }
                value = (T) element;
            }
        }
        return value;
    }

    /**
     * @return Object    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: findValueOfType</ p>
     * <p>@param ： @param collection
     * <p>@param ： @param types
     * <p>@param ： @return    设定文件</p>
     */
    public static Object findValueOfType(Collection<?> collection, Class<?>[] types) {
        if (isEmpty(collection) || isEmpty(types)) {
            return null;
        }
        for (Class<?> type : types) {
            Object value = findValueOfType(collection, type);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: hasUniqueObject</ p>
     * <p>@param ： @param collection
     * <p>@param ： @return    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static boolean hasUniqueObject(Collection collection) {
        if (isEmpty(collection)) {
            return false;
        }
        boolean hasCandidate = false;
        Object candidate = null;
        for (Object elem : collection) {
            if (!hasCandidate) {
                hasCandidate = true;
                candidate = elem;
            } else if (candidate != elem) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return Class<?>    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: findCommonElementType</ p>
     * <p>@param ： @param collection
     * <p>@param ： @return    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static Class<?> findCommonElementType(Collection collection) {
        if (isEmpty(collection)) {
            return null;
        }
        Class<?> candidate = null;
        for (Object val : collection) {
            if (val != null) {
                if (candidate == null) {
                    candidate = val.getClass();
                } else if (candidate != val.getClass()) {
                    return null;
                }
            }
        }
        return candidate;
    }

    @SuppressWarnings("unchecked")
    private static Object[] toObjectArray(Object source) {
        if (source instanceof Object[]) {
            return (Object[]) source;
        }
        if (source == null) {
            return new Object[0];
        }
        if (!source.getClass()
                   .isArray()) {
            throw new IllegalArgumentException("Source is not an array: " + source);
        }
        int length = Array.getLength(source);
        if (length == 0) {
            return new Object[0];
        }
        Class wrapperType = Array.get(source, 0)
                                 .getClass();
        Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
        for (int i = 0; i < length; i++) {
            newArray[i] = Array.get(source, i);
        }
        return newArray;
    }


    private static boolean nullSafeEquals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (o1.equals(o2)) {
            return true;
        }
        if (o1.getClass()
              .isArray() && o2.getClass()
                              .isArray()) {
            if (o1 instanceof Object[] && o2 instanceof Object[]) {
                return Arrays.equals((Object[]) o1, (Object[]) o2);
            }
            if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
                return Arrays.equals((boolean[]) o1, (boolean[]) o2);
            }
            if (o1 instanceof byte[] && o2 instanceof byte[]) {
                return Arrays.equals((byte[]) o1, (byte[]) o2);
            }
            if (o1 instanceof char[] && o2 instanceof char[]) {
                return Arrays.equals((char[]) o1, (char[]) o2);
            }
            if (o1 instanceof double[] && o2 instanceof double[]) {
                return Arrays.equals((double[]) o1, (double[]) o2);
            }
            if (o1 instanceof float[] && o2 instanceof float[]) {
                return Arrays.equals((float[]) o1, (float[]) o2);
            }
            if (o1 instanceof int[] && o2 instanceof int[]) {
                return Arrays.equals((int[]) o1, (int[]) o2);
            }
            if (o1 instanceof long[] && o2 instanceof long[]) {
                return Arrays.equals((long[]) o1, (long[]) o2);
            }
            if (o1 instanceof short[] && o2 instanceof short[]) {
                return Arrays.equals((short[]) o1, (short[]) o2);
            }
        }
        return false;
    }


    public static <E> Iterator<E> toIterator(Enumeration<E> enumeration) {
        return new EnumerationIterator<E>(enumeration);
    }

    /**
     * Iterator wrapping an Enumeration.
     */
    private static class EnumerationIterator<E> implements Iterator<E> {

        private Enumeration<E> enumeration;

        public EnumerationIterator(Enumeration<E> enumeration) {
            this.enumeration = enumeration;
        }

        public boolean hasNext() {
            return this.enumeration.hasMoreElements();
        }

        public E next() {
            return this.enumeration.nextElement();
        }

        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Not supported");
        }
    }

}
