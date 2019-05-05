package wang.dreamland.www.mail;

import javax.mail.PasswordAuthentication;

/**
 * @Author: 徐杰
 * @Date: 2019/5/5 0005 19:25
 * 用于验证邮箱和授权码的正确性
 */

public class MyAuthenticator extends javax.mail.Authenticator {
    private String strUser;
    private String strPwd;
    public MyAuthenticator(String user, String password) {
        this.strUser = user;
        this.strPwd = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(strUser, strPwd);
    }
}
