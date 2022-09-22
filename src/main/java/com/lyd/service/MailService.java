package com.lyd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MailService {

    @Autowired
    private CrawlerService crawlerService;
    @Autowired
    private JavaMailSenderImpl mailSender;
    //设置返回日期格式
    private final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Value("${spring.mail.username}")
    private String from;

    /**
     * @author 222100209_李炎东
     * @param email 要发送的邮箱
     * @return
     */
    public boolean sendMail(String... email) {
//        String from = "362664609@qq.com";
        String time=sdf.format(new Date());
        MimeMessage mimeMessage = null;
        MimeMessageHelper helper = null;

//        System.out.println(from);
        try {
            //发送复杂的邮件
            mimeMessage = mailSender.createMimeMessage();
            //这个h5是俺自己写的
            //组装
            helper= new MimeMessageHelper(mimeMessage, true);
            //邮件标题
            helper.setSubject("【周常任务】 教务处通知");
            //因为设置了邮件格式所以html标签有点多，后面的ture为支持识别html标签
            
            String sendHTML = "  <h3>\n" +
                    "\t<span style=\"font-size:16px;\">亲爱的懒狗：</span>\n" +
                    "</h3>\n" +
                    "<p>\n" +
                    "\t<span style=\"font-size:14px;\">";
            String noticeHTML = crawlerService.getNoticeHTML();

            sendHTML += noticeHTML;
            sendHTML += "\t</span>\n" +
                    "</p>\n" +
                    "<p style=\"text-align:right;\">\n" +
                    "\t<span style=\"background-color:#FFFFFF;font-size:16px;color:#000000;\">\n" +
                    "\t\t<span style=\"color:#000000;font-size:16px;background-color:#FFFFFF;\">\n" +
                    "\t\t\t<span class=\"token string\" style=\"font-family:&quot;font-size:16px;color:#000000;line-height:normal !important;background-color:#FFFFFF;\">Send By 李炎东</span>\n" +
                    "\t\t</span>\n" +
                    "\t</span>\n" +
                    "</p>\n" +
                    "<p style=\"text-align:right;\">\n" +
                    "<span style=\"background-color:#FFFFFF;font-size:14px;\"><span style=\"color:#FF9900;font-size:18px;\"><span class=\"token string\" style=\"font-family:&quot;font-size:16px;color:#000000;line-height:normal !important;\"><span style=\"font-size:16px;color:#000000;background-color:#FFFFFF;\">"+time+"</span><span style=\"font-size:18px;color:#000000;background-color:#FFFFFF;\"></span></span></span></span>\n" +
                    "</p>";

            helper.setText(sendHTML,true);
            //发送方
            helper.setFrom(from);
            try {
                for (String to : email) {
                    //收件人
                    helper.setTo(to);
                    //发送邮件
                    mailSender.send(mimeMessage);
                }

            } catch (MailException e) {
                //邮箱是无效的，或者发送失败
                return false;
            }
        } catch (MessagingException | IOException | ParseException e) {
            //发送失败--服务器繁忙
            return false;
        }
        return true;
    }
}
