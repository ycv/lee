package org.pvuu.utils.tools;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 判空工具类
 */
public class EmptyUtils {

    private EmptyUtils() {
        throw new UnsupportedOperationException("EmptyUtils禁止实例化");
    }

    public static boolean isNull(Object obj) {
        return obj == null ? true : false;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isNotEmpty(Object obj) {
        if (isNull(obj)) {
            return false;
        }
        if (obj.getClass()
               .isArray()) {
            return Array.getLength(obj) > 0 ? true : false;
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).size() > 0 ? true : false;
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).size() > 0 ? true : false;
        }
        if (obj instanceof String) {
            return obj.toString()
                      .trim()
                      .length() > 0 ? true : false;
        }
        if (obj instanceof File) {
            return ((File) obj).exists();
        }
        return true;
    }

    public static boolean isEmpty(Object obj) {
        return !isNotEmpty(obj);
    }

}