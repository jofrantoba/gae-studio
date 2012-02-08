package com.arcbees.gaestudio.client.application;

import com.arcbees.gaestudio.client.application.header.HeaderPresenter;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

public class ApplicationPresenter extends
        Presenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy> {

    public interface MyView extends View {
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetMainContent = new Type<RevealContentHandler<?>>();
    
    public static final Object TYPE_SetHeaderContent = new Object();

    @ProxyStandard
    public interface MyProxy extends Proxy<ApplicationPresenter> {
    }

    private final HeaderPresenter headerPresenter;

    @Inject
    public ApplicationPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                                final HeaderPresenter headerPresenter) {
        super(eventBus, view, proxy);
        this.headerPresenter = headerPresenter;
    }

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this, this);
    }
    
    @Override
    protected void onBind() {
        super.onBind();
        setInSlot(TYPE_SetHeaderContent, headerPresenter);
    }

}