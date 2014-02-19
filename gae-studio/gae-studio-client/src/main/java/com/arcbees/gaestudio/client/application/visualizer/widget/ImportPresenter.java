/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.rest.ImportService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.dto.ObjectWrapper;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.client.RestDispatchAsync;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class ImportPresenter extends PresenterWidget<ImportPresenter.MyView> implements UploadForm.Handler {
    interface MyView extends PopupView {
        void hide();

        void setUploadWidget(UploadForm uploadWidget);

        void onFileChosen(String chosenFileName);

        void prepareForNewUpload();
    }

    private final RestDispatchAsync restDispatch;
    private final ImportService importService;
    private final UploadFormFactory uploadFormFactory;

    @Inject
    ImportPresenter(EventBus eventBus,
                    MyView view,
                    RestDispatchAsync restDispatch,
                    ImportService importService,
                    UploadFormFactory uploadFormFactory) {
        super(eventBus, view);

        this.restDispatch = restDispatch;
        this.importService = importService;
        this.uploadFormFactory = uploadFormFactory;

        getUploadUrl();
    }

    @Override
    public void onUploadSuccess() {
        getView().hide();
    }

    @Override
    public void onFileChosen(String chosenFileName) {
        getView().onFileChosen(chosenFileName);
    }

    @Override
    public void onUploadFailure(String errorMessage) {
        getView().prepareForNewUpload();
        getUploadUrl();
    }

    private void getUploadUrl() {
        restDispatch.execute(importService.getUploadUrl(), new AsyncCallbackImpl<ObjectWrapper<String>>() {
            @Override
            public void onSuccess(ObjectWrapper<String> result) {
                getView().setUploadWidget(uploadFormFactory.createForm(result.getValue(), ImportPresenter.this));
            }
        });
    }
}