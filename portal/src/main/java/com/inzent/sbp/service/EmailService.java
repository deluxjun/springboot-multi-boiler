package com.inzent.sbp.service;

import com.inzent.sbp.error.AppException;
import com.inzent.sbp.error.ErrorCode;
import com.inzent.sbp.utils.Context;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class EmailService {

    @Qualifier("getJavaMailSender")
    @Autowired
    private JavaMailSender emailSender;

    @Qualifier("freemarkerClassLoaderConfig")
    @Autowired
    private FreeMarkerConfigurer freemarkerConfigurer;

//    @Value("classpath:/mail-logo.png")
//    private Resource resourceFile;

    @Autowired
    @Qualifier("emailMessages")
    private ResourceBundleMessageSource emailMessageSource;

//    @Autowired
//    @Qualifier("emailMessageSource")
//    public void setMessageSource(ResourceBundleMessageSource messageSource) {
//        this.messageSource = messageSource;
//    }

    @Value("${spring.mail.sender}")
    private String noReplyAddress;

//    public void sendMessageWithAttachment(String to,
//                                          String subject,
//                                          String text,
//                                          String pathToAttachment) {
//        try {
//            MimeMessage message = emailSender.createMimeMessage();
//            // pass 'true' to the constructor to create a multipart message
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setFrom(noReplyAddress);
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(text);
//
//            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
//            helper.addAttachment("Invoice", file);
//
//            emailSender.send(message);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }

    public void sendVerificationCode(String to) {
        String subject = "verification code";
        String code = "new code";   // TODO: generate

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("greetings", emailMessageSource.getMessage("mail.greetings", null, LocaleContextHolder.getLocale()));
        templateModel.put("code", code);
        templateModel.put("bottom", Context.getMessage("product.info"));

        Template freemarkerTemplate = null;
        String htmlBody = null;
        try {
            freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate("template-code.ftl");
            htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
        } catch (IOException|TemplateException e) {
            log.error(e.getMessage(), e);
            throw new AppException(ErrorCode.EMAIL, e.getMessage());
        }

        sendHtmlMessage(to, subject, htmlBody);
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(noReplyAddress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
        } catch (MessagingException e) {
            throw new AppException(ErrorCode.EMAIL, e.getMessage());
        }

        // for attaching a image
//        helper.addInline("attachment.png", resourceFile);
        emailSender.send(message);
    }
}