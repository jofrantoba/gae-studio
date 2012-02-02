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

package com.arcbees.gae.querylogger.common.dto;

import com.google.apphosting.api.DatastorePb.DeleteRequest;
import com.google.apphosting.api.DatastorePb.DeleteResponse;

public class DeleteRecord extends DbOperationRecord {

    private static final long serialVersionUID = 3745865294476274476L;

    private final DeleteRequest deleteRequest;

    private final DeleteResponse deleteResponse;

    public DeleteRecord(DeleteRequest deleteRequest, DeleteResponse deleteResponse, StackTraceElement caller,
                        String requestId, int executionTimeMs) {
        super(caller, requestId, executionTimeMs);
        this.deleteRequest = deleteRequest;
        this.deleteResponse = deleteResponse;
    }

    public DeleteRequest getDeleteRequest() {
        return deleteRequest;
    }

    public DeleteResponse getDeleteResponse() {
        return deleteResponse;
    }

}
