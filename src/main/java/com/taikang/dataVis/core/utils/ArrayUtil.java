package com.taikang.dataVis.core.utils;

import java.util.Collection;

/**
 *
 * @author itw_mengtao
 * @Description 集合工具类
 * @date 2018/3/14 13:58
 * @param
 * @return
 */
public abstract class ArrayUtil {

  /**
   * 判断集合是否为空
   *
   * @param c
   * @return
   */
  public static boolean isEmpty(Collection c) {
    if (c == null) {
      return true;
    } else if (c.size() <= 0) {
      return true;
    }
    return false;
  }

  /**
   * 判断集合是否非空
   *
   * @param c
   * @return
   */
  public static boolean isNotEmpty(Collection c) {
    if (c == null) {
      return false;
    } else if (c.size() <= 0) {
      return false;
    }
    return true;
  }

  /**
   * 判断数组是否为空
   *
   * @param c
   * @return
   */
  public static boolean isEmpty(Object[] c) {
    if (c == null) {
      return true;
    } else if (c.length <= 0) {
      return true;
    }
    return false;
  }

  /**
   * 判断数组是否非空
   *
   * @param c
   * @return
   */
  public static boolean isNotEmpty(Object[] c) {
    if (c == null) {
      return false;
    } else if (c.length <= 0) {
      return false;
    }
    return true;
  }
}
