/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.event;

import java.util.Set;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.google.common.collect.Sets;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class DeleteEntitiesEvent extends GwtEvent<DeleteEntitiesEvent.DeleteEntitiesHandler> {
    public interface DeleteEntitiesHandler extends EventHandler {
        public void onDeleteEntities(DeleteEntitiesEvent event);
    }

    public static Type<DeleteEntitiesHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, DeleteEntities deleteEntities) {
        source.fireEvent(new DeleteEntitiesEvent(deleteEntities, null, null));
    }

    public static void fire(HasHandlers source, DeleteEntities deleteEntities, String kind, String namespace) {
        source.fireEvent(new DeleteEntitiesEvent(deleteEntities, kind, namespace));
    }

    public static void fire(HasHandlers source, DeleteEntities deleteEntities, String kind) {
        source.fireEvent(new DeleteEntitiesEvent(deleteEntities, kind, null));
    }

    public static void fire(HasHandlers source, DeleteEntities deleteEntities, Set<ParsedEntity> entities) {
        source.fireEvent(new DeleteEntitiesEvent(deleteEntities, entities));
    }

    private static final Type<DeleteEntitiesHandler> TYPE = new Type<DeleteEntitiesHandler>();

    private DeleteEntities deleteEntities;
    private Set<ParsedEntity> entities;
    private String kind;
    private String namespace;

    DeleteEntitiesEvent(
            DeleteEntities deleteEntities,
            String kind,
            String namespace) {
        this.deleteEntities = deleteEntities;
        this.kind = kind;
        this.namespace = namespace;
        this.entities = Sets.newHashSet();
    }

    DeleteEntitiesEvent(
            DeleteEntities deleteEntities,
            Set<ParsedEntity> entities) {
        this.deleteEntities = deleteEntities;
        this.entities = entities;
    }

    public DeleteEntities getDeleteEntities() {
        return deleteEntities;
    }

    public String getKind() {
        return kind;
    }

    public String getNamespace() {
        return namespace;
    }

    public Set<ParsedEntity> getEntities() {
        return entities;
    }

    @Override
    public Type<DeleteEntitiesHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DeleteEntitiesHandler handler) {
        handler.onDeleteEntities(this);
    }
}
