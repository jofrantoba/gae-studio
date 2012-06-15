package com.arcbees.gaestudio.client.application.visualizer.entitydetails;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class EntityDetailsView extends ViewWithUiHandlers<EntityDetailsUiHandlers>
        implements EntityDetailsPresenter.MyView {

    public interface Binder extends UiBinder<Widget, EntityDetailsView> {
    }

    @UiField
    TextArea entityDetails;
    @UiField
    Button save;
    @UiField
    PopupPanel popup;
    @UiField
    Button cancel;
    @UiField
    DivElement error;

    @Inject
    public EntityDetailsView(final Binder uiBinder,
                             final UiHandlersStrategy<EntityDetailsUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void displayEntityDetails(String json) {
        error.setInnerText("");
        popup.center();
        entityDetails.setText(json);
    }

    @Override
    public void hide() {
        popup.hide();
    }

    @Override
    public void showError(String message) {
        error.setInnerText(message);
    }

    @UiHandler("save")
    void onEditClicked(ClickEvent event) {
        getUiHandlers().saveEntity(entityDetails.getValue());
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        hide();
    }

}
