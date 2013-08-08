package com.arcbees.gaestudio.client.rest;

import javax.inject.Singleton;

import com.arcbees.gaestudio.shared.BaseRestPath;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;

public class RestModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(ResourceFactory.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    @BaseRestPath
    String getBaseRestPath() {
        return Dictionary.getDictionary("AppConfiguration").get("restPath");
    }

    @Provides
    @Singleton
    EntitiesService getEntitiesService(ResourceFactory resourceFactory) {
        return resourceFactory.setupProxy(GWT.<EntitiesService>create(EntitiesService.class), EndPoints.ENTITIES);
    }

    @Provides
    @Singleton
    NamespacesService getNamespacesService(ResourceFactory resourceFactory) {
        return resourceFactory.setupProxy(GWT.<NamespacesService>create(NamespacesService.class), EndPoints.NAMESPACES);
    }

    @Provides
    @Singleton
    KindsService getKindsService(ResourceFactory resourceFactory) {
        return resourceFactory.setupProxy(GWT.<KindsService>create(KindsService.class), EndPoints.KINDS);
    }

    @Provides
    @Singleton
    OperationsService getOperationsService(ResourceFactory resourceFactory) {
        return resourceFactory.setupProxy(GWT.<OperationsService>create(OperationsService.class), EndPoints.OPERATIONS);
    }

    @Provides
    @Singleton
    RecordService getRecordService(ResourceFactory resourceFactory) {
        return resourceFactory.setupProxy(GWT.<RecordService>create(RecordService.class), EndPoints.RECORD);
    }
}
