/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.shared.dispatch.UpdateEntityAction;
import com.arcbees.gaestudio.shared.dispatch.UpdateEntityResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

// TODO add logging
public class UpdateEntityHandler
        extends AbstractActionHandler<UpdateEntityAction, UpdateEntityResult> {

    @Inject
    public UpdateEntityHandler() {
        super(UpdateEntityAction.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public UpdateEntityResult execute(UpdateEntityAction action, ExecutionContext context)
            throws ActionException {
        DispatchHelper.disableApiHooks();

        EntityDTO entityDTO = action.getEntityDTO();

        try {
            Class clazz = Class.forName(entityDTO.getClassName());

            // TODO Will only work with bag of primitive, use a more sophisticated way to edit changes
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            Object object = gson.fromJson(entityDTO.getJson(), clazz);

            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            datastore.put((Entity) clazz.cast(object));
        } catch (JsonSyntaxException e) {
            throw new ActionException("Error in json syntax");
        } catch (Exception e) {
            throw new ActionException("Unknown class");
        }

        return new UpdateEntityResult();
    }

    @Override
    public void undo(UpdateEntityAction action, UpdateEntityResult result, ExecutionContext context)
            throws ActionException {
        // Nothing to do here
    }

}
