package com.traningsprint1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailSendService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendEmail(String email, String name, String code) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,"utf-8");

        helper.setFrom("tuanpa3@rikkeisoft.com", "Book Store - Phan Anh Tuan");
        helper.setTo(email);

        String subject = "Mã xác nhận thay đổi mật khẩu";


        String content = "<p style='font-size: 15px'>Xin chào, <strong style='font-size: 20px; color: blue'>" + name + "</strong></p> "
                + "<p style='font-size: 15px'>Chúng tôi nhận được yêu cầu thay đổi mật khẩu cho tài khoản Tuan 's Book Store của bạn.</p> "
                + "<p style='font-size: 15px'>Sau đây là mã xác nhận của bạn: </p> "
                + "<h2 style='font-size: 30px; color: red'>" +'<a href=""> code + "</h2>"
                + "<p>Vui lòng không chia sẻ mã xác nhận này với người khác, mã này chỉ có hiệu lực trong <span style='color: red'> 60s </span>"
                + "<p>Trân trọng,</p>"
                + "<p>Đội ngũ Tuan 's Book Store.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

}
