/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.server.GaConstants;
import com.arcbees.gaestudio.server.recorder.HookRegistrar;
import com.arcbees.gaestudio.server.recorder.MemcacheKey;
import com.arcbees.gaestudio.server.recorder.authentication.Listener;
import com.arcbees.gaestudio.server.recorder.authentication.ListenerProvider;
import com.arcbees.gaestudio.shared.dispatch.SetRecordingAction;
import com.arcbees.gaestudio.shared.dispatch.SetRecordingResult;
import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class SetRecordingHandler extends AbstractActionHandler<SetRecordingAction, SetRecordingResult> {
    private static final String SET_RECORDING = "Set Recording";

    private final HookRegistrar hookRegistrar;
    private final ListenerProvider listenerProvider;
    private final GoogleAnalytic googleAnalytic;
    private final MemcacheService memcacheService;

    @Inject
    SetRecordingHandler(HookRegistrar hookRegistrar,
                        ListenerProvider listenerProvider,
                        GoogleAnalytic googleAnalytic,
                        MemcacheService memcacheService) {
        super(SetRecordingAction.class);

        this.hookRegistrar = hookRegistrar;
        this.listenerProvider = listenerProvider;
        this.googleAnalytic = googleAnalytic;
        this.memcacheService = memcacheService;
    }

    @Override
    public SetRecordingResult execute(SetRecordingAction action,
                                      ExecutionContext context) throws ActionException {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, SET_RECORDING);

        Listener listener = listenerProvider.get();

        if (action.isStarting()) {
            hookRegistrar.putListener(listener);
        } else {
            hookRegistrar.removeListener(listener);
        }

        return new SetRecordingResult(getMostRecentId());
    }

    private Long getMostRecentId() {
        Long mostRecentId = (Long) memcacheService.get(MemcacheKey.DB_OPERATION_COUNTER.getName());
        if (mostRecentId == null) {
            mostRecentId = 0L;
        }
        return mostRecentId;
    }
}
