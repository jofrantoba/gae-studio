/*
 * Copyright 2012 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.gaestudio.server.recorder;

import com.arcbees.gaestudio.server.guice.RequestIdProvider;
import com.arcbees.gaestudio.server.recorder.authentication.AuthenticationModule;
import com.arcbees.gaestudio.shared.formatters.ObjectifyRecordFormatter;
import com.arcbees.gaestudio.shared.formatters.RecordFormatter;
import com.arcbees.gaestudio.shared.util.SimpleStackInspector;
import com.arcbees.gaestudio.shared.util.StackInspector;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;
import com.google.inject.servlet.RequestScoped;

// TODO externalize magic strings
public class GaeStudioRecorderModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new AuthenticationModule());

        bind(Long.class).annotatedWith(Names.named("requestId")).toProvider(RequestIdProvider.class)
                .in(RequestScoped.class);

        bind(StackInspector.class).to(SimpleStackInspector.class);
        bind(DbOperationRecorder.class).to(MemcacheDbOperationRecorder.class);
        bind(RecordFormatter.class).to(ObjectifyRecordFormatter.class);

        install(new FactoryModuleBuilder()
                .implement(DbOperationRecorderHook.class, DbOperationRecorderHook.class)
                .build(DbOperationRecorderHookFactory.class));

        bind(HookRegistrar.class).asEagerSingleton();
    }

    @Provides
    @RequestScoped
    private MemcacheService memcacheServiceProvider() {
        return MemcacheServiceFactory.getMemcacheService("gae.visualizer");
    }

}
