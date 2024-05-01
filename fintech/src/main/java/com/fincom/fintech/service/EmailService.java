package com.fincom.fintech.service;

import org.springframework.stereotype.Service;


public interface EmailService {

    void sendSimpleEmail(String to, String subject, String text);


}
