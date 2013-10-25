package com.arcbees.gaestudio.companion.guice;

import com.arcbees.gaestudio.companion.dao.DaoModule;
import com.arcbees.gaestudio.companion.rest.RestModule;
import com.google.inject.AbstractModule;

public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new RestModule());
        install(new DaoModule());
        install(new DispatchServletModule());
    }
}
