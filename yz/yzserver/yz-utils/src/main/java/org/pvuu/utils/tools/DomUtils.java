package org.pvuu.utils.tools;

import org.springframework.util.Assert;
import org.w3c.dom.CharacterData;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DomUtils {
    /**
     * Retrieve all child elements of the given DOM element that match any of the given element names. Only look at the
     * direct child level of the given element; do not go into further depth (in contrast to the DOM API's
     *
     * @return List<Element>    返回类型</p>
     * @throws
     * @方法名: getChildElementsByTagName</ p>
     * <p>@param ： @param ele
     * <p>@param ： @param childEleNames
     * <p>@param ： @return    设定文件</p>
     */
    public static List<Element> getChildElementsByTagName(Element ele, String[] childEleNames) {
        Assert.notNull(ele, "Element must not be null");
        Assert.notNull(childEleNames, "Element names collection must not be null");
        List<String> childEleNameList = Arrays.asList(childEleNames);
        NodeList nl = ele.getChildNodes();
        List<Element> childEles = new ArrayList<Element>();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element && nodeNameMatch(node, childEleNameList)) {
                childEles.add((Element) node);
            }
        }
        return childEles;
    }

    /**
     * @return List<Element>    返回类型</p>
     * @throws
     * @方法名: getChildElementsByTagName</ p>
     * <p>@param ： @param ele
     * <p>@param ： @param childEleName
     * <p>@param ： @return    设定文件</p>
     */
    public static List<Element> getChildElementsByTagName(Element ele, String childEleName) {
        return getChildElementsByTagName(ele, new String[]{childEleName});
    }

    /**
     * @return Element    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: getChildElementByTagName</ p>
     * <p>@param ： @param ele
     * <p>@param ： @param childEleName
     * <p>@param ： @return    设定文件</p>
     */
    public static Element getChildElementByTagName(Element ele, String childEleName) {
        Assert.notNull(ele, "Element must not be null");
        Assert.notNull(childEleName, "Element name must not be null");
        NodeList nl = ele.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element && nodeNameMatch(node, childEleName)) {
                return (Element) node;
            }
        }
        return null;
    }

    /**
     * @return String    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: getChildElementValueByTagName</ p>
     * <p>@param ： @param ele
     * <p>@param ： @param childEleName
     * <p>@param ： @return    设定文件</p>
     */
    public static String getChildElementValueByTagName(Element ele, String childEleName) {
        Element child = getChildElementByTagName(ele, childEleName);
        return (child != null ? getTextValue(child) : null);
    }

    /**
     * @return List<Element>    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: getChildElements</ p>
     * <p>@param ： @param ele
     * <p>@param ： @return    设定文件</p>
     */
    public static List<Element> getChildElements(Element ele) {
        Assert.notNull(ele, "Element must not be null");
        NodeList nl = ele.getChildNodes();
        List<Element> childEles = new ArrayList<Element>();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                childEles.add((Element) node);
            }
        }
        return childEles;
    }

    /**
     * @return String    返回类型</p>
     * @throws
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @方法名: getTextValue</ p>
     * <p>@param ： @param valueEle
     * <p>@param ： @return    设定文件</p>
     */
    public static String getTextValue(Element valueEle) {
        Assert.notNull(valueEle, "Element must not be null");
        StringBuilder sb = new StringBuilder();
        NodeList nl = valueEle.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node item = nl.item(i);
            if ((item instanceof CharacterData && !(item instanceof Comment)) || item instanceof EntityReference) {
                sb.append(item.getNodeValue());
            }
        }
        return sb.toString();
    }

    /**
     * @return boolean    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: nodeNameEquals</ p>
     * <p>@param ： @param node
     * <p>@param ： @param desiredName
     * <p>@param ： @return    设定文件</p>
     */
    public static boolean nodeNameEquals(Node node, String desiredName) {
        Assert.notNull(node, "Node must not be null");
        Assert.notNull(desiredName, "Desired name must not be null");
        return nodeNameMatch(node, desiredName);
    }

    public static ContentHandler createContentHandler(Node node) {
        return new DomContentHandler(node);
    }

    private static boolean nodeNameMatch(Node node, String desiredName) {
        return (desiredName.equals(node.getNodeName()) || desiredName.equals(node.getLocalName()));
    }

    @SuppressWarnings("unchecked")
    private static boolean nodeNameMatch(Node node, Collection desiredNames) {
        return (desiredNames.contains(node.getNodeName()) || desiredNames.contains(node.getLocalName()));
    }
}
