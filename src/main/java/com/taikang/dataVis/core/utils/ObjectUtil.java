package com.taikang.dataVis.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


/**
 * 对Object的一些操作
 * Object的转换、克隆
 *
 * @author 余灏
 */
public class ObjectUtil {

    private ObjectUtil() {
    }

    /**
     * 将子类的对象赋值到父类的对象中
     *
     * @param parentClass 父类的类型
     *                    主要针对com.taikang.cus.domian的对象
     * @param child       子类的对象
     *                    主要针对com.taikang.cus.***.web.rest.vm的对象
     * @return
     */
    public static Object toParentObject(Class parentClass, Object child) {
        Object parent = null;
        try {
            parent = parentClass.newInstance();
            Field[] parentFields = parentClass.getDeclaredFields();

            Field parentField = null;
            Field childField = null;

            Object childFieldValue = null;
            String parentFieldName = null;

            for (int i = 0, parentLength = parentFields.length; i < parentLength; i++) {
                parentField = parentFields[i];
                // 判断是否被final修饰的字段
                if (Modifier.isFinal(parentField.getModifiers())) {
                    continue;
                }
                parentFieldName = parentField.getName();

                // 取子类的parentFieldName的Field对象
                childField = child.getClass().getSuperclass().getDeclaredField(parentFieldName);
                childField.setAccessible(true);
                childFieldValue = childField.get(child);

                // 将父类的parentFieldName属性的值设置为childFieldValue
                parentField.setAccessible(true);
                parentField.set(parent, childFieldValue);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return parent;
    }

    /**
     * 将父类的对象赋值到子类的对象中
     *
     * @param parent     父类的对象
     *                   主要针对com.taikang.cus.domian的对象
     * @param childClass 子类的class
     *                   主要针对com.taikang.cus.***.web.rest.vm的对象
     * @return
     */
    public static Object toChildObject(Object parent, Class childClass) {
        Object child = null;
        try {
            child = childClass.newInstance();
            Field[] parentFields = parent.getClass().getDeclaredFields();

            Field parentField = null;
            Field childField = null;

            Object parentFieldValue = null;
            String fieldName = null;

            for (int i = 0, parentLength = parentFields.length; i < parentLength; i++) {
                parentField = parentFields[i];
                if (Modifier.isFinal(parentField.getModifiers())) {
                    continue;
                }
                fieldName = parentField.getName();
                childField = parent.getClass().getDeclaredField(fieldName);

                // 从父类取fieldName属性的值
                parentField.setAccessible(true);
                parentFieldValue = parentField.get(parent);

                // 将子类的fieldName属性的值设置为childFieldValue
                childField.setAccessible(true);
                childField.set(child, parentFieldValue);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return child;
    }

}
