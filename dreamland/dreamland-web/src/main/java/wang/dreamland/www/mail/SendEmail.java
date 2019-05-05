package wang.dreamland.www.mail;

import org.apache.log4j.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @Author: 徐杰
 * @Date: 2019/5/5 0005 19:25
 * 用于发送邮件的，主要注意将发件人的邮箱改成我的邮箱，还有输入我的授权码。
 */
public class SendEmail {
    private final static Logger log = Logger.getLogger( SendEmail.class);
    public static void sendEmailMessage(String email,String validateCode) {
       try {
           String host = "smtp.qq.com";   //发件人使用发邮件的电子信箱服务器
           String from = "发件人邮箱";    //发邮件的出发地（发件人的信箱）
           String to = email;   //发邮件的目的地（收件人信箱）
           // Get system properties
           Properties props = System.getProperties();

           // Setup mail server
           props.put("mail.smtp.host", host);

           // Get session
           props.put("mail.smtp.auth", "true"); //这样才能通过验证

           MyAuthenticator myauth = new MyAuthenticator(from, "vwgjvavkksaybecf");
           Session session = Session.getDefaultInstance(props, myauth);

//    session.setDebug(true);

           // Define message
           MimeMessage message = new MimeMessage(session);


           // Set the from address
           message.setFrom(new InternetAddress(from));

           // Set the to address
           message.addRecipient( Message.RecipientType.TO,
                   new InternetAddress(to));

           // Set the subject
           message.setSubject("杰の博客激活邮件通知");

           // Set the content
           message.setContent( "<a href=\"http://localhost:8080/activecode?email="+email+"&validateCode="+validateCode+"\" target=\"_blank\">请于24小时内点击激活</a>","text/html;charset=gb2312");
           message.saveChanges();

           Transport.send(message);

           log.info( "send validateCode to " + email );
       }catch (Exception e){

           log.info( "Send Email Exception:"+e.getMessage() );
       }

    }
}
