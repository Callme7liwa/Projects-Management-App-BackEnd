package com.pfe.projectsmanagements.services.Email;

import com.pfe.projectsmanagements.entities.others.EmailDetails;

public interface EmailService {


    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
