package cn.mrxccc.easycv.util;

/**
 * @author mrxccc
 * @create 2020/12/16
 */
public class CommonUtil {
    public static boolean isAllNullOrEmpty(String... str) {
        int len=-1;
        if (str == null||(len=str.length)<1) {
            return true;
        }
        for (String obj : str) {
            if (isNullOrEmpty(obj)) {
                len--;
            }
        }
        if(len<1) {
            return true;
        }
        return false;
    }
    public static boolean isNullOrEmpty(String str) {
        return null == str || str.length() < 1 || "".equals(str.trim());
    }

    public static boolean isNullOrEmpty(String... str) {
        if (str == null) {
            return false;
        }
        for (String obj : str) {
            if (isNullOrEmpty(obj)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNull(Object obj) {
        return null == obj;
    }

    public static boolean isNull(Object... objarr) {
        if (objarr == null) {
            return false;
        }
        for (Object obj : objarr) {
            if (null == obj) {
                return true;
            }
        }
        return false;
    }
}
