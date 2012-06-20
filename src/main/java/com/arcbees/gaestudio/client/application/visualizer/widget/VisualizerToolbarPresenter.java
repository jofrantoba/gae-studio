/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.event.EditEntityEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityDeletedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityPageLoadedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.RefreshEntitiesEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntityAction;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntityResult;
import com.arcbees.gaestudio.shared.dispatch.GetEmptyKindEntityAction;
import com.arcbees.gaestudio.shared.dispatch.GetEmptyKindEntityResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class VisualizerToolbarPresenter extends PresenterWidget<VisualizerToolbarPresenter.MyView> implements
        VisualizerToolbarUiHandlers, KindSelectedEvent.KindSelectedHandler, EntitySelectedEvent.EntitySelectedHandler,
        EntityPageLoadedEvent.EntityPageLoadedHandler {

    public interface MyView extends View, HasUiHandlers<VisualizerToolbarUiHandlers> {
        void setKindSelected(boolean isSelected);

        void enableContextualMenu();

        void disableContextualMenu();
    }

    public static final Object TYPE_SetKindsContent = new Object();

    private final DispatchAsync dispatcher;
    private final KindListPresenter kindListPresenter;
    private String currentKind = "";
    private ParsedEntity currentParsedEntity;

    @Inject
    public VisualizerToolbarPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher,
                                      final KindListPresenter kindListPresenter) {
        super(eventBus, view);

        this.dispatcher = dispatcher;
        this.kindListPresenter = kindListPresenter;
    }

    @Override
    public void refresh() {
        RefreshEntitiesEvent.fire(this);
    }

    @Override
    public void create() {
        if (!currentKind.isEmpty()) {
            dispatcher.execute(new GetEmptyKindEntityAction(currentKind), new AsyncCallback<GetEmptyKindEntityResult>
                    () {
                @Override
                public void onFailure(Throwable caught) {
                    Message message = new Message("Unable to generate empty json", MessageStyle.ERROR);
                    DisplayMessageEvent.fire(VisualizerToolbarPresenter.this, message);
                }

                @Override
                public void onSuccess(GetEmptyKindEntityResult result) {
                    EntityDTO emptyEntityDto = result.getEntityDTO();
                    EditEntityEvent.fire(VisualizerToolbarPresenter.this, new ParsedEntity(emptyEntityDto));
                }
            });
        }
    }

    @Override
    public void edit() {
        if (currentParsedEntity != null) {
            EditEntityEvent.fire(this, currentParsedEntity);
        }
    }

    @Override
    public void delete() {
        if (currentParsedEntity != null) {
            final EntityDTO entityDTO = currentParsedEntity.getEntityDTO();
            dispatcher.execute(new DeleteEntityAction(entityDTO), new AsyncCallback<DeleteEntityResult>() {
                @Override
                public void onSuccess(DeleteEntityResult result) {
                    onEntityDeletedSuccess(entityDTO);
                }

                @Override
                public void onFailure(Throwable caught) {
                    Message message = new Message("Error while trying to delete the entity", MessageStyle.ERROR);
                    DisplayMessageEvent.fire(VisualizerToolbarPresenter.this, message);
                }
            });
        }
    }

    @Override
    public void onKindSelected(KindSelectedEvent event) {
        currentKind = event.getKind();
        getView().setKindSelected(!currentKind.isEmpty());
    }

    @Override
    public void onEntitySelected(EntitySelectedEvent event) {
        currentParsedEntity = event.getParsedEntity();
        getView().enableContextualMenu();
    }

    @Override
    public void onEntityPageLoaded(EntityPageLoadedEvent event) {
        getView().disableContextualMenu();
    }

    @Override
    protected void onBind() {
        super.onBind();

        setInSlot(TYPE_SetKindsContent, kindListPresenter);

        addRegisteredHandler(KindSelectedEvent.getType(), this);
        addRegisteredHandler(EntitySelectedEvent.getType(), this);
        addRegisteredHandler(EntityPageLoadedEvent.getType(), this);
    }

    private void onEntityDeletedSuccess(EntityDTO entityDTO) {
        Message message = new Message("Entity deleted", MessageStyle.SUCCESS);
        DisplayMessageEvent.fire(this, message);
        EntityDeletedEvent.fire(this, entityDTO);
    }
}

