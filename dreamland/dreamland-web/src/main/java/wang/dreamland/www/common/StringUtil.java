package wang.dreamland.www.common;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: 徐杰
 * @Date: 2019/5/5 0005 20:17
 * 转String工具类
 */
public class StringUtil {

    public static String safeToString(Object o, String dv) {
        String r = dv;
        if (o != null) {
            r = String.valueOf(o);
            if (isBlank(r)) {
                r = dv;
            }
        }

        return r;
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
}
