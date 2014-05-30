/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service.mail;

import com.arcbees.gaestudio.server.email.ConfirmRegistrationEmailBuilder;
import com.arcbees.gaestudio.server.email.EmailMessageGenerator;
import com.arcbees.gaestudio.server.email.ResetPasswordEmailBuilder;
import com.arcbees.gaestudio.shared.dto.EmailDto;
import com.google.inject.Inject;

public class MessageServiceImpl implements MessageService {
    private static final String CONFIRM_SUBJECT = "GAE Studio - Confirm your registration";
    private static final String RESET_PASSWORD_SUBJECT = "ArcBees - Reset your password";

    private final EmailMessageGenerator emailMessageGenerator;
    private final ResetPasswordEmailBuilder resetPasswordEmailBuilder;
    private final ConfirmRegistrationEmailBuilder confirmRegistrationEmailBuilder;

    @Inject
    MessageServiceImpl(EmailMessageGenerator emailMessageGenerator,
                       ResetPasswordEmailBuilder resetPasswordEmailBuilder,
                       ConfirmRegistrationEmailBuilder confirmRegistrationEmailBuilder) {

        this.confirmRegistrationEmailBuilder = confirmRegistrationEmailBuilder;
        this.resetPasswordEmailBuilder = resetPasswordEmailBuilder;
        this.emailMessageGenerator = emailMessageGenerator;
    }

    @Override
    public void sendConfirmationEmail(String emailAddress, String tokenId, String redirectionUri) {
        String body = confirmRegistrationEmailBuilder.generateBody(tokenId, redirectionUri);
        String message = emailMessageGenerator.generateBody(CONFIRM_SUBJECT, body);

        EmailDto emailDto = buildEmail(emailAddress, CONFIRM_SUBJECT, message);

        sendEmail(emailDto);
    }

    @Override
    public void sendResetPasswordEmail(String emailAddress, String token) {
        String body = resetPasswordEmailBuilder.generateBody(emailAddress, token);
        String message = emailMessageGenerator.generateBody(RESET_PASSWORD_SUBJECT, body);

        EmailDto emailDto = buildEmail(emailAddress, RESET_PASSWORD_SUBJECT, message);

        sendEmail(emailDto);
    }

    private EmailDto buildEmail(String emailAddress, String subject, String message) {
        return new EmailDto(emailAddress, "GAE Studio", message, subject);
    }

    private void sendEmail(EmailDto emailDto) {
        //TODO : Call ArcBees Mail Service
    }
}
