package com.inzent.sbp.controller;

import com.inzent.sbp.dto.CompanyDto;
import com.inzent.sbp.error.AppException;
import com.inzent.sbp.service.CompanyService;
import com.inzent.sbp.service.EmailService;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/service/confirm")
@RequiredArgsConstructor
public class VerificationController {

    private final MessageSource messages;

    private final EmailService emailService;


    @PostMapping("/sendCode")
    public void sendVerificationCode(@RequestBody String id) {
        emailService.sendVerificationCode(id);
        // TODO: save to table
    }

    // TODO: verify code
    public void verifyCode() {

    }

}
