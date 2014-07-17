/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.server.EmbeddedStaticResourcesServlet;
import com.google.inject.servlet.ServletModule;

public class EmbeddedPathModule extends ServletModule {
    private static final String EMBEDDED_PATH = "gae-studio-admin";
    private static final String GAE_STUDIO_HTML = "/gae-studio.*";

    @Override
    protected void configureServlets() {
        serve("/", "/" + GAE_STUDIO_HTML).with(RootServlet.class);
        serve("/" + EMBEDDED_PATH + "/", "/" + EMBEDDED_PATH + GAE_STUDIO_HTML).with(RootServlet.class);
        serve("/" + EMBEDDED_PATH + "/*").with(EmbeddedStaticResourcesServlet.class);
    }
}
