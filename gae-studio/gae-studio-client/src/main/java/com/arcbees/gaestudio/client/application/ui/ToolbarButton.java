/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.ui;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import static com.google.gwt.query.client.GQuery.$;

public class ToolbarButton implements AttachEvent.Handler, IsWidget {
    interface Binder extends UiBinder<Widget, ToolbarButton> {
    }

    @UiField(provided = true)
    AppResources resources;
    @UiField
    HTMLPanel button;
    @UiField
    ParagraphElement text;
    @UiField
    DivElement icon;

    private final ToolbarButtonCallback callback;
    private final Widget widget;

    private Function buttonClickFunction;

    @AssistedInject
    ToolbarButton(Binder binder,
                  AppResources resources,
                  @Assisted("text") String text,
                  @Assisted("iconClass") String iconClass,
                  @Assisted ToolbarButtonCallback callback) {
        this(binder, resources, text, iconClass, callback, "");
    }

    @AssistedInject
    ToolbarButton(Binder binder,
                  AppResources resources,
                  @Assisted("text") String text,
                  @Assisted("iconClass") String iconClass,
                  @Assisted ToolbarButtonCallback callback,
                  @Assisted("debugId") String debugId) {
        this.resources = resources;

        widget = binder.createAndBindUi(this);

        this.text.setInnerSafeHtml(SafeHtmlUtils.fromString(text));

        icon.addClassName(iconClass);

        this.callback = callback;

        widget.ensureDebugId(debugId);

        widget.addAttachHandler(this);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public void onAttachOrDetach(AttachEvent event) {
        if (event.isAttached()) {
            register(callback);
        }
    }

    private void register(final ToolbarButtonCallback callback) {
        buttonClickFunction = new Function() {
            @Override
            public boolean f(Event e) {
                callback.onClicked();
                return true;
            }
        };

        setEnabled(!$(button).hasClass(resources.styles().toolbarButtonDisabled()));
    }

    public void setEnabled(Boolean enabled) {
        $(button).toggleClass(resources.styles().toolbarButtonDisabled(), !enabled);
        if (enabled) {
            $(button).click(buttonClickFunction);
        } else {
            $(button).unbind(BrowserEvents.CLICK, buttonClickFunction);
        }
    }

    public void setAddStyleNames(String style) {
        asWidget().addStyleName(style);
    }
}
