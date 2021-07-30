package com.ysd.visitor.utlis;
/**
 *@Package: RegularUtil.java
 *@describe(描述)：com.zr.carshop.utils
 *@ClassName: RegularUtil 正则验证
 *@data（日期）: 2020/6/22 0022
 *@time（时间）: 16:31
 *@author（作者）: 李建华
 *@UpdateRemark: 更新说明：Administrator
 *@Version: 3.5
 **/

public class RegularUtil {
    private static final String PASSWORD_REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
    public static final String regex = "^1[3456789][0-9]{9}$";

    public static boolean isMobilePhoneNumber(String string) {
        return string.matches(PASSWORD_REGEX);
    }
    public static boolean isMobilePhone(String string) {
        return string.matches(regex);
    }


}
