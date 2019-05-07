package wang.dreamland.www.common;

/**
 * @Author: 徐杰
 * @Date: 2019/5/5 0005 20:20
 * 转int工具类
 */
public class NumberTool {
    public static Integer safeToInteger(Object o, Integer dv) {
        Integer r = dv;
        if(o != null) {
            try {
                r = new Integer(String.valueOf(o));
            } catch (Exception var4) {
                ;
            }
        }

        return r;
    }
}
