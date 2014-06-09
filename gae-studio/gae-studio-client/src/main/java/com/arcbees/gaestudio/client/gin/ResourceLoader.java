/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.gin;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.resources.CellTableResource;
import com.arcbees.gaestudio.client.resources.PagerResources;
import com.arcbees.gaestudio.client.resources.VisualizerResources;
import com.arcbees.gaestudio.client.resources.WidgetResources;
import com.arcbees.gaestudio.client.ui.MessageResources;
import com.google.inject.Inject;

public class ResourceLoader {
    @Inject
    public ResourceLoader(AppResources resources,
                          MessageResources messageResources,
                          CellTableResource cellTableResource,
                          PagerResources pagerResources,
                          VisualizerResources visualizerResources,
                          WidgetResources widgetResources) {
        resources.styles().ensureInjected();
        resources.authStyles().ensureInjected();
        cellTableResource.cellTableStyle().ensureInjected();
        messageResources.styles().ensureInjected();
        pagerResources.simplePagerStyle().ensureInjected();
        visualizerResources.styles().ensureInjected();
        visualizerResources.entityList().ensureInjected();
        widgetResources.header().ensureInjected();
    }
}
