package org.pvuu.utils.tools;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ObjectUtils {

    private static final int INITIAL_HASH = 7;
    private static final int MULTIPLIER = 31;

    private static final String EMPTY_STRING = "";
    private static final String NULL_STRING = "null";
    private static final String ARRAY_START = "{";
    private static final String ARRAY_END = "}";
    private static final String EMPTY_ARRAY = ARRAY_START + ARRAY_END;
    private static final String ARRAY_ELEMENT_SEPARATOR = ", ";

    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: isCheckedException</ p>
     * <p>@param ： @param ex
     * <p>@param ： @return    设定文件</p>
     */
    public static boolean isCheckedException(Throwable ex) {
        return !(ex instanceof RuntimeException || ex instanceof Error);
    }

    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @方法名: isCompatibleWithThrowsClause</ p>
     * <p>@param ： @param ex
     * <p>@param ： @param declaredExceptions
     * <p>@param ： @return    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static boolean isCompatibleWithThrowsClause(Throwable ex, Class[] declaredExceptions) {
        if (!isCheckedException(ex)) {
            return true;
        }
        if (declaredExceptions != null) {
            int i = 0;
            while (i < declaredExceptions.length) {
                if (declaredExceptions[i].isAssignableFrom(ex.getClass())) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: isArray</ p>
     * <p>@param ： @param obj
     * <p>@param ： @return    设定文件</p>
     */
    public static boolean isArray(Object obj) {
        return (obj != null && obj.getClass()
                                  .isArray());
    }

    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: isEmpty</ p>
     * <p>@param ： @param array
     * <p>@param ： @return    设定文件</p>
     */
    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }

    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: containsElement</ p>
     * <p>@param ： @param array
     * <p>@param ： @param element
     * <p>@param ： @return    设定文件</p>
     */
    public static boolean containsElement(Object[] array, Object element) {
        if (array == null) {
            return false;
        }
        for (Object arrayEle : array) {
            if (nullSafeEquals(arrayEle, element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return Object[]    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: addObjectToArray</ p>
     * <p>@param ： @param array
     * <p>@param ： @param obj
     * <p>@param ： @return    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static Object[] addObjectToArray(Object[] array, Object obj) {
        Class compType = Object.class;
        if (array != null) {
            compType = array.getClass()
                            .getComponentType();
        } else if (obj != null) {
            compType = obj.getClass();
        }
        int newArrLength = (array != null ? array.length + 1 : 1);
        Object[] newArr = (Object[]) Array.newInstance(compType, newArrLength);
        if (array != null) {
            System.arraycopy(array, 0, newArr, 0, array.length);
        }
        newArr[newArr.length - 1] = obj;
        return newArr;
    }

    /**
     * @return Object[]    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: toObjectArray</ p>
     * <p>@param ： @param source
     * <p>@param ： @return    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static Object[] toObjectArray(Object source) {
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

    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: nullSafeEquals</ p>
     * <p>@param ： @param o1
     * <p>@param ： @param o2
     * <p>@param ： @return    设定文件</p>
     */
    public static boolean nullSafeEquals(Object o1, Object o2) {
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

    /**
     * @return int    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: nullSafeHashCode</ p>
     * <p>@param ： @param obj
     * <p>@param ： @return    设定文件</p>
     */
    public static int nullSafeHashCode(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj.getClass()
               .isArray()) {
            if (obj instanceof Object[]) {
                return nullSafeHashCode((Object[]) obj);
            }
            if (obj instanceof boolean[]) {
                return nullSafeHashCode((boolean[]) obj);
            }
            if (obj instanceof byte[]) {
                return nullSafeHashCode((byte[]) obj);
            }
            if (obj instanceof char[]) {
                return nullSafeHashCode((char[]) obj);
            }
            if (obj instanceof double[]) {
                return nullSafeHashCode((double[]) obj);
            }
            if (obj instanceof float[]) {
                return nullSafeHashCode((float[]) obj);
            }
            if (obj instanceof int[]) {
                return nullSafeHashCode((int[]) obj);
            }
            if (obj instanceof long[]) {
                return nullSafeHashCode((long[]) obj);
            }
            if (obj instanceof short[]) {
                return nullSafeHashCode((short[]) obj);
            }
        }
        return obj.hashCode();
    }

    /**
     * @return int    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: nullSafeHashCode</ p>
     * <p>@param ： @param array
     * <p>@param ： @return    设定文件</p>
     */
    public static int nullSafeHashCode(Object[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            hash = MULTIPLIER * hash + nullSafeHashCode(array[i]);
        }
        return hash;
    }

    /**
     * @return int    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: nullSafeHashCode</ p>
     * <p>@param ： @param array
     * <p>@param ： @return    设定文件</p>
     */
    public static int nullSafeHashCode(boolean[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            hash = MULTIPLIER * hash + hashCode(array[i]);
        }
        return hash;
    }

    /**
     * @return int    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: nullSafeHashCode</ p>
     * <p>@param ： @param array
     * <p>@param ： @return    设定文件</p>
     */
    public static int nullSafeHashCode(short[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            hash = MULTIPLIER * hash + array[i];
        }
        return hash;
    }

    /**
     * @return int    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: hashCode</ p>
     * <p>@param ： @param bool
     * <p>@param ： @return    设定文件</p>
     */
    public static int hashCode(boolean bool) {
        return bool ? 1231 : 1237;
    }

    /**
     * @return int    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: hashCode</ p>
     * <p>@param ： @param dbl
     * <p>@param ： @return    设定文件</p>
     */
    public static int hashCode(double dbl) {
        long bits = Double.doubleToLongBits(dbl);
        return hashCode(bits);
    }

    /**
     * @return int    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: hashCode</ p>
     * <p>@param ： @param flt
     * <p>@param ： @return    设定文件</p>
     */
    public static int hashCode(float flt) {
        return Float.floatToIntBits(flt);
    }

    /**
     * @return int    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: hashCode</ p>
     * <p>@param ： @param lng
     * <p>@param ： @return    设定文件</p>
     */
    public static int hashCode(long lng) {
        return (int) (lng ^ (lng >>> 32));
    }

    /**
     * @return String    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: identityToString</ p>
     * <p>@param ： @param obj
     * <p>@param ： @return    设定文件</p>
     */
    public static String identityToString(Object obj) {
        if (obj == null) {
            return EMPTY_STRING;
        }
        return obj.getClass()
                  .getName() + "@" + getIdentityHexString(obj);
    }

    /**
     * @return String    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: getIdentityHexString</ p>
     * <p>@param ： @param obj
     * <p>@param ： @return    设定文件</p>
     */
    public static String getIdentityHexString(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }

    public static String getDisplayString(Object obj) {
        if (obj == null) {
            return EMPTY_STRING;
        }
        return null;
    }

    /**
     * @return String    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: nullSafeClassName</ p>
     * <p>@param ： @param obj
     * <p>@param ： @return    设定文件</p>
     */
    public static String nullSafeClassName(Object obj) {
        return (obj != null ? obj.getClass()
                                 .getName() : NULL_STRING);
    }

    /**
     * @return String    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: nullSafeToString</ p>
     * <p>@param ： @param obj
     * <p>@param ： @return    设定文件</p>
     */
    public static String nullSafeToString(Object obj) {
        if (obj == null) {
            return NULL_STRING;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Object[]) {
            return nullSafeToString((Object[]) obj);
        }
        if (obj instanceof boolean[]) {
            return nullSafeToString((boolean[]) obj);
        }
        if (obj instanceof byte[]) {
            return nullSafeToString((byte[]) obj);
        }
        if (obj instanceof char[]) {
            return nullSafeToString((char[]) obj);
        }
        if (obj instanceof double[]) {
            return nullSafeToString((double[]) obj);
        }
        if (obj instanceof float[]) {
            return nullSafeToString((float[]) obj);
        }
        if (obj instanceof int[]) {
            return nullSafeToString((int[]) obj);
        }
        if (obj instanceof long[]) {
            return nullSafeToString((long[]) obj);
        }
        if (obj instanceof short[]) {
            return nullSafeToString((short[]) obj);
        }
        String str = obj.toString();
        return (str != null ? str : EMPTY_STRING);
    }

    /**
     * @return String    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: nullSafeToString</ p>
     * <p>@param ： @param array
     * <p>@param ： @return    设定文件</p>
     */
    public static String nullSafeToString(Object[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append(String.valueOf(array[i]));
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * @return String    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: nullSafeToString</ p>
     * <p>@param ： @param array
     * <p>@param ： @return    设定文件</p>
     */
    public static String nullSafeToString(boolean[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }

            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * @return String    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: nullSafeToString</ p>
     * <p>@param ： @param array
     * <p>@param ： @return    设定文件</p>
     */
    public static String nullSafeToString(byte[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * @return String    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: nullSafeToString</ p>
     * <p>@param ： @param array
     * <p>@param ： @return    设定文件</p>
     */
    public static String nullSafeToString(char[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append("'")
              .append(array[i])
              .append("'");
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * @return String    返回类型</p>
     * @throws
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @方法名: nullSafeToString</ p>
     * <p>@param ： @param array
     * <p>@param ： @return    设定文件</p>
     */
    public static String nullSafeToString(double[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }

            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * @return String    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: nullSafeToString</ p>
     * <p>@param ： @param array
     * <p>@param ： @return    设定文件</p>
     */
    public static String nullSafeToString(float[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }

            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * @return String    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: nullSafeToString</ p>
     * <p>@param ： @param array
     * <p>@param ： @return    设定文件</p>
     */
    public static String nullSafeToString(int[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * @return String    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: nullSafeToString</ p>
     * <p>@param ： @param array
     * <p>@param ： @return    设定文件</p>
     */
    public static String nullSafeToString(long[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * @return String    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: nullSafeToString</ p>
     * <p>@param ： @param array
     * <p>@param ： @return    设定文件</p>
     */
    public static String nullSafeToString(short[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    public static String toString(Object obj) {
        return obj != null ? obj.toString() : "";
    }

    public static String toString(Object obj, String nullStr) {
        return obj != null ? obj.toString() : nullStr;
    }

    @SuppressWarnings("unchecked")
    public static Object min(Comparable c1, Comparable c2) {
        if (c1 != null && c2 != null) {
            return c1.compareTo(c2) >= 1 ? c2 : c1;
        } else {
            return c1 == null ? c2 : c1;
        }
    }

    @SuppressWarnings("unchecked")
    public static Object max(Comparable c1, Comparable c2) {
        if (c1 != null && c2 != null) {
            return c1.compareTo(c2) < 0 ? c2 : c1;
        } else {
            return c1 == null ? c2 : c1;
        }
    }
}
