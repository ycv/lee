package org.pvuu.utils.tools;

import java.util.Collection;
import java.util.Map;

public class Validate {
    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: isTrue</ p>
     * <p>@param ： @param expression
     * <p>@param ： @param message    设定文件</p>
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: isTrue</ p>
     * <p>@param ： @param expression    设定文件</p>
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: isNull</ p>
     * <p>@param ： @param object
     * <p>@param ： @param message    设定文件</p>
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: isNull</ p>
     * <p>@param ： @param object    设定文件</p>
     */
    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: notNull</ p>
     * <p>@param ： @param object
     * <p>@param ： @param message    设定文件</p>
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: notNull</ p>
     * <p>@param ： @param object    设定文件</p>
     */
    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: hasLength</ p>
     * <p>@param ： @param text
     * <p>@param ： @param message    设定文件</p>
     */
    public static void hasLength(String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: hasLength</ p>
     * <p>@param ： @param text    设定文件</p>
     */
    public static void hasLength(String text) {
        hasLength(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: hasText</ p>
     * <p>@param ： @param text
     * <p>@param ： @param message    设定文件</p>
     */
    public static void hasText(String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: hasText</ p>
     * <p>@param ： @param text    设定文件</p>
     */
    public static void hasText(String text) {
        hasText(text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: doesNotContain</ p>
     * <p>@param ： @param textToSearch
     * <p>@param ： @param substring
     * <p>@param ： @param message    设定文件</p>
     */
    public static void doesNotContain(String textToSearch, String substring, String message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
                textToSearch.indexOf(substring) != -1) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: doesNotContain</ p>
     * <p>@param ： @param textToSearch
     * <p>@param ： @param substring    设定文件</p>
     */
    public static void doesNotContain(String textToSearch, String substring) {
        doesNotContain(textToSearch, substring, "[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: notEmpty</ p>
     * <p>@param ： @param array
     * <p>@param ： @param message    设定文件</p>
     */
    public static void notEmpty(Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: notEmpty</ p>
     * <p>@param ： @param array    设定文件</p>
     */
    public static void notEmpty(Object[] array) {
        notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: noNullElements</ p>
     * <p>@param ： @param array
     * <p>@param ： @param message    设定文件</p>
     */
    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    throw new IllegalArgumentException(message);
                }
            }
        }
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: noNullElements</ p>
     * <p>@param ： @param array    设定文件</p>
     */
    public static void noNullElements(Object[] array) {
        noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: notEmpty</ p>
     * <p>@param ： @param collection
     * <p>@param ： @param message    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static void notEmpty(Collection collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: notEmpty</ p>
     * <p>@param ： @param collection    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static void notEmpty(Collection collection) {
        notEmpty(collection,
                "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: notEmpty</ p>
     * <p>@param ： @param map
     * <p>@param ： @param message    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static void notEmpty(Map map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: notEmpty</ p>
     * <p>@param ： @param map    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static void notEmpty(Map map) {
        notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: isInstanceOf</ p>
     * <p>@param ： @param clazz
     * <p>@param ： @param obj    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static void isInstanceOf(Class clazz, Object obj) {
        isInstanceOf(clazz, obj, "");
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: isInstanceOf</ p>
     * <p>@param ： @param type
     * <p>@param ： @param obj
     * <p>@param ： @param message    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static void isInstanceOf(Class type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(message +
                    "Object of class [" + (obj != null ? obj.getClass()
                                                            .getName() : "null") +
                    "] must be an instance of " + type);
        }
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: isAssignable</ p>
     * <p>@param ： @param superType
     * <p>@param ： @param subType    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static void isAssignable(Class superType, Class subType) {
        isAssignable(superType, subType, "");
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: isAssignable</ p>
     * <p>@param ： @param superType
     * <p>@param ： @param subType
     * <p>@param ： @param message    设定文件</p>
     */
    @SuppressWarnings("unchecked")
    public static void isAssignable(Class superType, Class subType, String message) {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new IllegalArgumentException(message + subType + " is not assignable to " + superType);
        }
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: state</ p>
     * <p>@param ： @param expression
     * <p>@param ： @param message    设定文件</p>
     */
    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * @return void    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: state</ p>
     * <p>@param ： @param expression    设定文件</p>
     */
    public static void state(boolean expression) {
        state(expression, "[Assertion failed] - this state invariant must be true");
    }
}
