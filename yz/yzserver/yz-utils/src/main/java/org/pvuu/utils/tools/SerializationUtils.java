package org.pvuu.utils.tools;

import java.io.*;

public class SerializationUtils {
    /**
     * @return byte[]    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: serialize</ p>
     * <p>@param ： @param object
     * <p>@param ： @return    设定文件</p>
     */
    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), ex);
        }
        return baos.toByteArray();
    }

    /**
     * @return Object    返回类型</p>
     * @throws
     * @Description: TODO()
     * @方法名: deserialize</ p>
     * <p>@param ： @param bytes
     * <p>@param ： @return    设定文件</p>
     */
    public static Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to deserialize object", ex);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Failed to deserialize object type", ex);
        }
    }
}
