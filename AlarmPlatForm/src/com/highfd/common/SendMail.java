package com.highfd.common;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import com.highfd.vo.Item;
import com.highfd.vo.MailInfo;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

public class SendMail{
    private String username ;
    private String password ;
	
    //无参数的构造器
    public SendMail(){
    }
    
    public SendMail(MailInfo mailinfo){
    	this.username = mailinfo.getUsername();
    	this.password = mailinfo.getPassword();
    }
    
    //把邮件主题转换为中文
    public String transferChinese(String strText){
        try{
            strText = MimeUtility.encodeText(
                new String(strText.getBytes()
                , "GB2312"), "GB2312", "B");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return strText;
    }
    
    //增加附件，将附件文件名添加的List集合中
    public void attachfile(String fname, MailInfo mailinfo){
        mailinfo.getAttachments().add(fname);
    }
    
    //发送邮件
    public boolean send(MailInfo mailinfo){
    	String smtpserver = mailinfo.getSmtpServer(); 
    	String from = mailinfo.getFrom(); 
    	String to = mailinfo.getTo(); 
    	String subject = mailinfo.getSubject(); 
    	String content = mailinfo.getContent(); 
    	List<String> attachments = mailinfo.getAttachments(); 
    	
    	//创建邮件Session所需的Properties对象
        Properties props = new Properties();
        props.put("mail.smtp.host" , smtpserver);
        props.put("mail.smtp.auth" , "true");
        //创建Session对象
        Session session = Session.getDefaultInstance(props
            //以匿名内部类的形式创建登录服务器的认证对象
            , new Authenticator(){
                public PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(username,password); 
                }
            });
        try{
            //构造MimeMessage并设置相关属性值
            MimeMessage msg = new MimeMessage(session);
            //设置发件人
            msg.setFrom(new InternetAddress(from));
            //设置收件人
            InternetAddress[] addresses = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO , addresses);
            //设置邮件主题
            //subject = transferChinese(subject);
            msg.setSubject(subject);    
            //构造Multipart
            Multipart mp = new MimeMultipart();
            //向Multipart添加正文
            MimeBodyPart mbpContent = new MimeBodyPart();
            mbpContent.setText(content);
            //将BodyPart添加到MultiPart中
            mp.addBodyPart(mbpContent);
            //向Multipart添加附件
            //遍历附件列表，将所有文件添加到邮件消息里
            for(String efile : attachments){
                MimeBodyPart mbpFile = new MimeBodyPart();
                //以文件名创建FileDataSource对象
                FileDataSource fds = new FileDataSource(efile);
                //处理附件
                mbpFile.setDataHandler(new DataHandler(fds));
                mbpFile.setFileName(fds.getName());
                //将BodyPart添加到MultiPart中
                mp.addBodyPart(mbpFile);
            }
            //清空附件列表
            attachments.clear();
            //向Multipart添加MimeMessage
            msg.setContent(mp);
            //设置发送日期
            msg.setSentDate(new Date());
            //发送邮件
            Transport.send(msg);
        }
        catch (MessagingException mex){
            mex.printStackTrace();
            return false;
        }
        return true;
    }
    
    
    public static void mainConfig(String content){
    	MailInfo mailinfo = new MailInfo();
        /*mailinfo.setSmtpServer("smtp.163.com");
        //此处设置登录的用户名
        mailinfo.setUsername("wandou0624@163.com");
        //此处设置登录的密码
        mailinfo.setPassword("wandou0624@");
        //设置收件人的地址
        mailinfo.setTo("913494568@qq.com");
        //设置发送人地址
        mailinfo.setFrom("wandou0624@163.com");*/

        mailinfo.setSmtpServer("smtp.sina.com");
        //此处设置登录的用户名
        mailinfo.setUsername("liuying_a@sina.com");
        //此处设置登录的密码
        mailinfo.setPassword("liuying521521");
        //设置收件人的地址
        mailinfo.setTo("370014402@qq.com");
        //设置发送人地址
        mailinfo.setFrom("liuying_a@sina.com");
        
        //设置标题
        mailinfo.setSubject("抓取配置报警");
        //设置内容
        mailinfo.setContent(content); 
        //粘贴附件
        //sendMail.attachfile("C:/Login6 (1).jpg",mailinfo);
        SendMail sendMail = new SendMail(mailinfo);
        if (sendMail.send(mailinfo)){
            System.out.println("---邮件发送成功---");
        }
    }
    public static void main(String[] args) throws InterruptedException{
    	mainConfig("11");
    }
    
    
    
}
