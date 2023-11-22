package org.pvuu.utils.tools;

import java.util.*;
import java.util.regex.Pattern;

public class StringUtils {

    public static final Pattern PATTERN_BLANK = Pattern.compile("\\s*|\t|\r|\n");

    private static final String FOLDER_SEPARATOR = "/";

    private static final String WINDOWS_FOLDER_SEPARATOR = "\\";

    private static final String TOP_PATH = "..";

    private static final String CURRENT_PATH = ".";

    private static final char EXTENSION_SEPARATOR = '.';

    /**
     * 字符串大小比较
     *
     * @param a
     * @param b
     * @return 字符串相同时，返回 0
     */
    public int strCompare(String a, String b) {
        return a.compareTo(b);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String join(Object array[]) {
        return join(array, ((String)(null)));
    }

    public static String join(Object array[], char separator) {
        if (array == null) {
            return null;
        } else {
            return join(array, separator, 0, array.length);
        }
    }

    public static String join(Object array[], char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        int bufSize = endIndex - startIndex;
        if (bufSize <= 0) {
            return "";
        }
        bufSize *= (array[startIndex] != null ? array[startIndex].toString().length() : 16) + 1;
        StringBuffer buf = new StringBuffer(bufSize);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static String join(Object array[], String separator) {
        if (array == null) {
            return null;
        } else {
            return join(array, separator, 0, array.length);
        }
    }

    public static String join(Object array[], String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = "";
        }

        int bufSize = endIndex - startIndex;
        if (bufSize <= 0) {
            return "";
        }
        bufSize *= (array[startIndex] != null ? array[startIndex].toString().length() : 16) + separator.length();
        StringBuffer buf = new StringBuffer(bufSize);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    @SuppressWarnings("unchecked")
    public static String join(Iterator iterator, char separator) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return "";
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return ObjectUtils.toString(first);
        }
        StringBuffer buf = new StringBuffer(256);
        if (first != null) {
            buf.append(first);
        }
        do {
            if (!iterator.hasNext()) {
                break;
            }
            buf.append(separator);
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        } while (true);

        return buf.toString();
    }

    @SuppressWarnings("unchecked")
    public static String join(Collection collection, char separator) {
        if (collection == null) {
            return null;
        } else {
            return join(collection.iterator(), separator);
        }
    }

    /**
     * @return boolean 返回类型
     *         </p>
     * @throws
     * @方法名: hasLength</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    /**
     * @return boolean 返回类型
     *         </p>
     * @throws
     * @方法名: hasLength</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static boolean hasLength(String str) {
        return hasLength((CharSequence)str);
    }

    /**
     * @return boolean 返回类型
     *         </p>
     * @throws
     * @方法名: hasText</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return boolean 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: hasText</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static boolean hasText(String str) {
        return hasText((CharSequence)str);
    }

    /**
     * @return boolean 返回类型
     *         </p>
     * @throws
     * @方法名: containsWhitespace</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static boolean containsWhitespace(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return boolean 返回类型
     *         </p>
     * @throws
     * @方法名: containsWhitespace</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static boolean containsWhitespace(String str) {
        return containsWhitespace((CharSequence)str);
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @方法名: trimWhitespace</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String trimWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: trimAllWhitespace</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        while (sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index))) {
                sb.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sb.toString();
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: trimLeadingWhitespace</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String trimLeadingWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: trimTrailingWhitespace</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String trimTrailingWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @方法名: trimLeadingCharacter</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @param leadingCharacter
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String trimLeadingCharacter(String str, char leadingCharacter) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && sb.charAt(0) == leadingCharacter) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: trimTrailingCharacter</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @param trailingCharacter
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String trimTrailingCharacter(String str, char trailingCharacter) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == trailingCharacter) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * @return boolean 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: startsWithIgnoreCase</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @param prefix
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if (str == null || prefix == null) {
            return false;
        }
        if (str.startsWith(prefix)) {
            return true;
        }
        if (str.length() < prefix.length()) {
            return false;
        }
        String lcStr = str.substring(0, prefix.length()).toLowerCase();
        String lcPrefix = prefix.toLowerCase();
        return lcStr.equals(lcPrefix);
    }

    /**
     * @return boolean 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: endsWithIgnoreCase</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @param suffix
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static boolean endsWithIgnoreCase(String str, String suffix) {
        if (str == null || suffix == null) {
            return false;
        }
        if (str.endsWith(suffix)) {
            return true;
        }
        if (str.length() < suffix.length()) {
            return false;
        }

        String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
        String lcSuffix = suffix.toLowerCase();
        return lcStr.equals(lcSuffix);
    }

    /**
     * @return boolean 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: substringMatch</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @param index
     *            <p>
     * @param ： @param substring
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
        for (int j = 0; j < substring.length(); j++) {
            int i = index + j;
            if (i >= str.length() || str.charAt(i) != substring.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return int 返回类型
     *         </p>
     * @throws
     * @方法名: countOccurrencesOf</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @param sub
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static int countOccurrencesOf(String str, String sub) {
        if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
            return 0;
        }
        int count = 0;
        int pos = 0;
        int idx;
        while ((idx = str.indexOf(sub, pos)) != -1) {
            ++count;
            pos = idx + sub.length();
        }
        return count;
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @方法名: replace</ p>
     *       <p>
     * @param ： @param inString
     *            <p>
     * @param ： @param oldPattern
     *            <p>
     * @param ： @param newPattern
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String replace(String inString, String oldPattern, String newPattern) {
        if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
            return inString;
        }
        StringBuilder sb = new StringBuilder();
        int pos = 0; // our position in the old string
        int index = inString.indexOf(oldPattern);
        // the index of an occurrence we've found, or -1
        int patLen = oldPattern.length();
        while (index >= 0) {
            sb.append(inString.substring(pos, index));
            sb.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }
        sb.append(inString.substring(pos));
        // remember to append any characters to the right of a match
        return sb.toString();
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @方法名: delete</ p>
     *       <p>
     * @param ： @param inString
     *            <p>
     * @param ： @param pattern
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String delete(String inString, String pattern) {
        return replace(inString, pattern, "");
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: deleteAny</ p>
     *       <p>
     * @param ： @param inString
     *            <p>
     * @param ： @param charsToDelete
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String deleteAny(String inString, String charsToDelete) {
        if (!hasLength(inString) || !hasLength(charsToDelete)) {
            return inString;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inString.length(); i++) {
            char c = inString.charAt(i);
            if (charsToDelete.indexOf(c) == -1) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: quote</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String quote(String str) {
        return (str != null ? "'" + str + "'" : null);
    }

    /**
     * @return Object 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: quoteIfString</ p>
     *       <p>
     * @param ： @param obj
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static Object quoteIfString(Object obj) {
        return (obj instanceof String ? quote((String)obj) : obj);
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: unqualify</ p>
     *       <p>
     * @param ： @param qualifiedName
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String unqualify(String qualifiedName) {
        return unqualify(qualifiedName, '.');
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @方法名: unqualify</ p>
     *       <p>
     * @param ： @param qualifiedName
     *            <p>
     * @param ： @param separator
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String unqualify(String qualifiedName, char separator) {
        return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: capitalize</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: uncapitalize</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: changeFirstCharacterCase</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @param capitalize
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length());
        if (capitalize) {
            sb.append(Character.toUpperCase(str.charAt(0)));
        } else {
            sb.append(Character.toLowerCase(str.charAt(0)));
        }
        sb.append(str.substring(1));
        return sb.toString();
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: getFilename</ p>
     *       <p>
     * @param ： @param path
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String getFilename(String path) {
        if (path == null) {
            return null;
        }
        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: getFilenameExtension</ p>
     *       <p>
     * @param ： @param path
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String getFilenameExtension(String path) {
        if (path == null) {
            return null;
        }
        int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
        return (sepIndex != -1 ? path.substring(sepIndex + 1) : null);
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: stripFilenameExtension</ p>
     *       <p>
     * @param ： @param path
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String stripFilenameExtension(String path) {
        if (path == null) {
            return null;
        }
        int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
        return (sepIndex != -1 ? path.substring(0, sepIndex) : path);
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: applyRelativePath</ p>
     *       <p>
     * @param ： @param path
     *            <p>
     * @param ： @param relativePath
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String applyRelativePath(String path, String relativePath) {
        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        if (separatorIndex != -1) {
            String newPath = path.substring(0, separatorIndex);
            if (!relativePath.startsWith(FOLDER_SEPARATOR)) {
                newPath += FOLDER_SEPARATOR;
            }
            return newPath + relativePath;
        } else {
            return relativePath;
        }
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: cleanPath</ p>
     *       <p>
     * @param ： @param path
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String cleanPath(String path) {
        if (path == null) {
            return null;
        }
        String pathToUse = replace(path, WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);

        int prefixIndex = pathToUse.indexOf(":");
        String prefix = "";
        if (prefixIndex != -1) {
            prefix = pathToUse.substring(0, prefixIndex + 1);
            pathToUse = pathToUse.substring(prefixIndex + 1);
        }
        if (pathToUse.startsWith(FOLDER_SEPARATOR)) {
            prefix = prefix + FOLDER_SEPARATOR;
            pathToUse = pathToUse.substring(1);
        }

        String[] pathArray = delimitedListToStringArray(pathToUse, FOLDER_SEPARATOR);
        List<String> pathElements = new LinkedList<String>();
        int tops = 0;

        for (int i = pathArray.length - 1; i >= 0; i--) {
            String element = pathArray[i];
            if (CURRENT_PATH.equals(element)) {
                // Points to current directory - drop it.
            } else if (TOP_PATH.equals(element)) {
                // Registering top path found.
                tops++;
            } else {
                if (tops > 0) {
                    // Merging path element with element corresponding to top path.
                    tops--;
                } else {
                    // Normal path element found.
                    pathElements.add(0, element);
                }
            }
        }

        // Remaining top paths need to be retained.
        for (int i = 0; i < tops; i++) {
            pathElements.add(0, TOP_PATH);
        }

        return prefix + collectionToDelimitedString(pathElements, FOLDER_SEPARATOR);
    }

    /**
     * @return boolean 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: pathEquals</ p>
     *       <p>
     * @param ： @param path1
     *            <p>
     * @param ： @param path2
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static boolean pathEquals(String path1, String path2) {
        return cleanPath(path1).equals(cleanPath(path2));
    }

    /**
     * @return Locale 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: parseLocaleString</ p>
     *       <p>
     * @param ： @param localeString
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static Locale parseLocaleString(String localeString) {
        String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
        String language = (parts.length > 0 ? parts[0] : "");
        String country = (parts.length > 1 ? parts[1] : "");
        String variant = "";
        if (parts.length >= 2) {
            // There is definitely a variant, and it is everything after the country
            // code sans the separator between the country code and the variant.
            int endIndexOfCountryCode = localeString.indexOf(country) + country.length();
            // Strip off any leading '_' and whitespace, what's left is the variant.
            variant = trimLeadingWhitespace(localeString.substring(endIndexOfCountryCode));
            if (variant.startsWith("_")) {
                variant = trimLeadingCharacter(variant, '_');
            }
        }
        return (language.length() > 0 ? new Locale(language, country, variant) : null);
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: toLanguageTag</ p>
     *       <p>
     * @param ： @param locale
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String toLanguageTag(Locale locale) {
        return locale.getLanguage() + (hasText(locale.getCountry()) ? "-" + locale.getCountry() : "");
    }

    /**
     * @return String[] 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: addStringToArray</ p>
     *       <p>
     * @param ： @param array
     *            <p>
     * @param ： @param str
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String[] addStringToArray(String[] array, String str) {
        if (ObjectUtils.isEmpty(array)) {
            return new String[] {str};
        }
        String[] newArr = new String[array.length + 1];
        System.arraycopy(array, 0, newArr, 0, array.length);
        newArr[array.length] = str;
        return newArr;
    }

    /**
     * @return String[] 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: concatenateStringArrays</ p>
     *       <p>
     * @param ： @param array1
     *            <p>
     * @param ： @param array2
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String[] concatenateStringArrays(String[] array1, String[] array2) {
        if (ObjectUtils.isEmpty(array1)) {
            return array2;
        }
        if (ObjectUtils.isEmpty(array2)) {
            return array1;
        }
        String[] newArr = new String[array1.length + array2.length];
        System.arraycopy(array1, 0, newArr, 0, array1.length);
        System.arraycopy(array2, 0, newArr, array1.length, array2.length);
        return newArr;
    }

    /**
     * @return String[] 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: mergeStringArrays</ p>
     *       <p>
     * @param ： @param array1
     *            <p>
     * @param ： @param array2
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String[] mergeStringArrays(String[] array1, String[] array2) {
        if (ObjectUtils.isEmpty(array1)) {
            return array2;
        }
        if (ObjectUtils.isEmpty(array2)) {
            return array1;
        }
        List<String> result = new ArrayList<String>();
        result.addAll(Arrays.asList(array1));
        for (String str : array2) {
            if (!result.contains(str)) {
                result.add(str);
            }
        }
        return toStringArray(result);
    }

    /**
     * @return String[] 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: sortStringArray</ p>
     *       <p>
     * @param ： @param array
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String[] sortStringArray(String[] array) {
        if (ObjectUtils.isEmpty(array)) {
            return new String[0];
        }
        Arrays.sort(array);
        return array;
    }

    /**
     * @return String[] 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: toStringArray</ p>
     *       <p>
     * @param ： @param collection
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String[] toStringArray(Collection<String> collection) {
        if (collection == null) {
            return null;
        }
        return collection.toArray(new String[collection.size()]);
    }

    /**
     * @return String[] 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: toStringArray</ p>
     *       <p>
     * @param ： @param enumeration
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String[] toStringArray(Enumeration<String> enumeration) {
        if (enumeration == null) {
            return null;
        }
        List<String> list = Collections.list(enumeration);
        return list.toArray(new String[list.size()]);
    }

    /**
     * @return String[] 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: trimArrayElements</ p>
     *       <p>
     * @param ： @param array
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String[] trimArrayElements(String[] array) {
        if (ObjectUtils.isEmpty(array)) {
            return new String[0];
        }
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            String element = array[i];
            result[i] = (element != null ? element.trim() : null);
        }
        return result;
    }

    /**
     * @return String[] 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: removeDuplicateStrings</ p>
     *       <p>
     * @param ： @param array
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String[] removeDuplicateStrings(String[] array) {
        if (ObjectUtils.isEmpty(array)) {
            return array;
        }
        Set<String> set = new TreeSet<String>();
        for (String element : array) {
            set.add(element);
        }
        return toStringArray(set);
    }

    /**
     * @return String[] 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: split</ p>
     *       <p>
     * @param ： @param toSplit
     *            <p>
     * @param ： @param delimiter
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String[] split(String toSplit, String delimiter) {
        if (!hasLength(toSplit) || !hasLength(delimiter)) {
            return null;
        }
        int offset = toSplit.indexOf(delimiter);
        if (offset < 0) {
            return null;
        }
        String beforeDelimiter = toSplit.substring(0, offset);
        String afterDelimiter = toSplit.substring(offset + delimiter.length());
        return new String[] {beforeDelimiter, afterDelimiter};
    }

    /**
     * @return Properties 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: splitArrayElementsIntoProperties</ p>
     *       <p>
     * @param ： @param array
     *            <p>
     * @param ： @param delimiter
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter) {
        return splitArrayElementsIntoProperties(array, delimiter, null);
    }

    /**
     * @return Properties 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: splitArrayElementsIntoProperties</ p>
     *       <p>
     * @param ： @param array
     *            <p>
     * @param ： @param delimiter
     *            <p>
     * @param ： @param charsToDelete
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter, String charsToDelete) {

        if (ObjectUtils.isEmpty(array)) {
            return null;
        }
        Properties result = new Properties();
        for (String element : array) {
            if (charsToDelete != null) {
                element = deleteAny(element, charsToDelete);
            }
            String[] splittedElement = split(element, delimiter);
            if (splittedElement == null) {
                continue;
            }
            result.setProperty(splittedElement[0].trim(), splittedElement[1].trim());
        }
        return result;
    }

    /**
     * @return String[] 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: tokenizeToStringArray</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @param delimiters
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String[] tokenizeToStringArray(String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    /**
     * @return String[] 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: tokenizeToStringArray</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @param delimiters
     *            <p>
     * @param ： @param trimTokens
     *            <p>
     * @param ： @param ignoreEmptyTokens
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens,
        boolean ignoreEmptyTokens) {

        if (str == null) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }
        return toStringArray(tokens);
    }

    /**
     * @return String[] 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: delimitedListToStringArray</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @param delimiter
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String[] delimitedListToStringArray(String str, String delimiter) {
        return delimitedListToStringArray(str, delimiter, null);
    }

    /**
     * @return String[] 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: delimitedListToStringArray</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @param delimiter
     *            <p>
     * @param ： @param charsToDelete
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String[] delimitedListToStringArray(String str, String delimiter, String charsToDelete) {
        if (str == null) {
            return new String[0];
        }
        if (delimiter == null) {
            return new String[] {str};
        }
        List<String> result = new ArrayList<String>();
        if ("".equals(delimiter)) {
            for (int i = 0; i < str.length(); i++) {
                result.add(deleteAny(str.substring(i, i + 1), charsToDelete));
            }
        } else {
            int pos = 0;
            int delPos;
            while ((delPos = str.indexOf(delimiter, pos)) != -1) {
                result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
                pos = delPos + delimiter.length();
            }
            if (str.length() > 0 && pos <= str.length()) {
                // Add rest of String, but not in case of empty input.
                result.add(deleteAny(str.substring(pos), charsToDelete));
            }
        }
        return toStringArray(result);
    }

    /**
     * @return String[] 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: commaDelimitedListToStringArray</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String[] commaDelimitedListToStringArray(String str) {
        return delimitedListToStringArray(str, ",");
    }

    /**
     * @return Set<String> 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: commaDelimitedListToSet</ p>
     *       <p>
     * @param ： @param str
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static Set<String> commaDelimitedListToSet(String str) {
        Set<String> set = new TreeSet<String>();
        String[] tokens = commaDelimitedListToStringArray(str);
        for (String token : tokens) {
            set.add(token);
        }
        return set;
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: collectionToDelimitedString</ p>
     *       <p>
     * @param ： @param coll
     *            <p>
     * @param ： @param delim
     *            <p>
     * @param ： @param prefix
     *            <p>
     * @param ： @param suffix
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    @SuppressWarnings("unchecked")
    public static String collectionToDelimitedString(Collection coll, String delim, String prefix, String suffix) {
        if (CollectionUtils.isEmpty(coll)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = coll.iterator();
        while (it.hasNext()) {
            sb.append(prefix).append(it.next()).append(suffix);
            if (it.hasNext()) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: collectionToDelimitedString</ p>
     *       <p>
     * @param ： @param coll
     *            <p>
     * @param ： @param delim
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    @SuppressWarnings("unchecked")
    public static String collectionToDelimitedString(Collection coll, String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: collectionToCommaDelimitedString</ p>
     *       <p>
     * @param ： @param coll
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    @SuppressWarnings("unchecked")
    public static String collectionToCommaDelimitedString(Collection coll) {
        return collectionToDelimitedString(coll, ",");
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: arrayToDelimitedString</ p>
     *       <p>
     * @param ： @param arr
     *            <p>
     * @param ： @param delim
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String arrayToDelimitedString(Object[] arr, String delim) {
        if (ObjectUtils.isEmpty(arr)) {
            return "";
        }
        if (arr.length == 1) {
            return ObjectUtils.nullSafeToString(arr[0]);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(delim);
            }
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    /**
     * @return String 返回类型
     *         </p>
     * @throws
     * @Description: TODO()
     * @方法名: arrayToCommaDelimitedString</ p>
     *       <p>
     * @param ： @param arr
     *            <p>
     * @param ： @return 设定文件
     *            </p>
     */
    public static String arrayToCommaDelimitedString(Object[] arr) {
        return arrayToDelimitedString(arr, ",");
    }

    public static String capitalise(String str) {
        return capitalize(str);
    }

    public static String uncapitalizel(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        } else {
            return (new StringBuffer(strLen)).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1))
                .toString();
        }
    }

    public static String format5(double value) {
        return String.format("%.2f", value).toString();
    }

}
