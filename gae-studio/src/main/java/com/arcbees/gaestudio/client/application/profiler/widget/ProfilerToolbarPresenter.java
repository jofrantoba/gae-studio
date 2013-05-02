/*
 * Copyright 2013 ArcBees Inc.
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

package com.arcbees.gaestudio.client.application.profiler.widget;

import com.arcbees.gaestudio.client.application.profiler.event.ClearOperationRecordsEvent;
import com.arcbees.gaestudio.client.application.profiler.event.RecordingStateChangedEvent;
import com.arcbees.gaestudio.shared.dispatch.SetRecordingAction;
import com.arcbees.gaestudio.shared.dispatch.SetRecordingResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class ProfilerToolbarPresenter extends PresenterWidget<ProfilerToolbarPresenter.MyView> implements
        ProfilerToolbarUiHandlers {
    public interface MyView extends View, HasUiHandlers<ProfilerToolbarUiHandlers> {
        void setRecordingState(Boolean isRecording);
    }

    private final DispatchAsync dispatcher;

    @Inject
    public ProfilerToolbarPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher) {
        super(eventBus, view);

        getView().setUiHandlers(this);
        
        this.dispatcher = dispatcher;
    }

    @Override
    public void onToggleRecording(final Boolean start) {
        dispatcher.execute(new SetRecordingAction(start), new AsyncCallback<SetRecordingResult>() {
            @Override
            public void onFailure(Throwable caught) {
                getView().setRecordingState(!start);
            }

            @Override
            public void onSuccess(SetRecordingResult result) {
                getView().setRecordingState(start);
                RecordingStateChangedEvent.fire(ProfilerToolbarPresenter.this, start, result.getCurrentRecordId());
            }
        });
    }

    @Override
    public void clearOperationRecords() {
        ClearOperationRecordsEvent.fire(this);
    }
}
