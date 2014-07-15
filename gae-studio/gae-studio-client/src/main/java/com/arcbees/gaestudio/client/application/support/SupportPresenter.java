/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.support;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.rest.MailService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class SupportPresenter extends PresenterWidget<SupportPresenter.MyView> implements SupportUiHandlers {
    interface MyView extends PopupView, HasUiHandlers<SupportUiHandlers> {
        void edit(SupportMessage supportMessage);
    }

    private static final String API_KEY = "apikey";

    private final AppConstants appConstants;
    private final RestDispatch dispatch;
    private final MailService mailService;

    @Inject
    SupportPresenter(EventBus eventBus,
                     MyView view,
                     AppConstants appConstants,
                     RestDispatch dispatch,
                     MailService mailService) {
        super(eventBus, view);

        this.appConstants = appConstants;
        this.dispatch = dispatch;
        this.mailService = mailService;

        getView().setUiHandlers(this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        getView().edit(new SupportMessage());
    }

    @Override
    public void send(SupportMessage supportMessage) {
        RestAction<Void> action = mailService.sendMessage(MessageRequest.fromSupportMessage(supportMessage), API_KEY);

        dispatch.execute(action, new AsyncCallbackImpl<Void>() {
            @Override
            public void onSuccess(Void result) {
                Message message = new Message(appConstants.thankYou(), MessageStyle.SUCCESS);
                displayMessage(message);
            }
        });
    }

    private void displayMessage(Message message) {
        DisplayMessageEvent.fire(SupportPresenter.this, message);
        getView().hide();
    }
}
