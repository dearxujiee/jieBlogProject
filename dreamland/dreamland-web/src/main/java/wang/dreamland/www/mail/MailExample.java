package wang.dreamland.www.mail;

/**
 * @Author: 徐杰
 * @Date: 2019/5/5 0005 19:25
 * 用于测试
 */

public class MailExample {

    public static void main (String args[]) throws Exception {
        String email = "";
        String validateCode = "";
        SendEmail.sendEmailMessage(email,validateCode);

    }
}
