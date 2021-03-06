/**
 * Copyright 2015 ArcBees Inc.
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

package com.arcbees.gaestudio.client.application.entity.editor;

import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

import static com.arcbees.gaestudio.client.application.entity.editor.PropertyUtil.getPropertyAsNumber;
import static com.arcbees.gaestudio.client.application.entity.editor.PropertyUtil.getPropertyAsObject;
import static com.arcbees.gaestudio.client.application.entity.editor.PropertyUtil.getPropertyAsString;
import static com.arcbees.gaestudio.shared.PropertyName.APP_ID;
import static com.arcbees.gaestudio.shared.PropertyName.APP_ID_NAMESPACE;
import static com.arcbees.gaestudio.shared.PropertyName.ID;
import static com.arcbees.gaestudio.shared.PropertyName.KIND;
import static com.arcbees.gaestudio.shared.PropertyName.NAME;
import static com.arcbees.gaestudio.shared.PropertyName.NAMESPACE;
import static com.arcbees.gaestudio.shared.PropertyName.PARENT_KEY;

public class Key {
    private String kind;
    private String name;
    private String appId;
    private Long id;
    private AppIdNamespaceDto appIdNamespace;
    private Key parentKey;

    public Key(
            String kind,
            String name,
            String appId,
            Long id,
            AppIdNamespaceDto appIdNamespace,
            Key parentKey) {
        this.kind = kind;
        this.name = name;
        this.appId = appId;
        this.id = id;
        this.appIdNamespace = appIdNamespace;
        this.parentKey = parentKey;
    }

    public static Key fromJsonObject(JSONObject jsonObject) {
        String kind = getPropertyAsString(jsonObject, KIND);
        String name = getPropertyAsString(jsonObject, NAME);
        String appId = getPropertyAsString(jsonObject, APP_ID);
        JSONNumber idAsNumber = getPropertyAsNumber(jsonObject, ID);
        Long id = idAsNumber == null ? null : (long) idAsNumber.doubleValue();

        JSONObject parentKeyObject = getPropertyAsObject(jsonObject, PARENT_KEY);
        Key parentKey = null;
        if (parentKeyObject != null && parentKeyObject.isNull() != null) {
            parentKey = fromJsonObject(parentKeyObject);
        }

        JSONObject appIdNamespaceObject = getPropertyAsObject(jsonObject, APP_ID_NAMESPACE);
        String appIdNamespace = getPropertyAsString(appIdNamespaceObject, APP_ID);
        String namespace = getPropertyAsString(appIdNamespaceObject, NAMESPACE);

        return new Key(kind, name, appId, id, new AppIdNamespaceDto(appIdNamespace, namespace), parentKey);
    }

    public JSONObject asJsonObject() {
        JSONObject key = createJsonKey(this);

        if (parentKey != null) {
            JSONObject parentKeyObject = createJsonKey(parentKey);
            key.put(PARENT_KEY, parentKeyObject);
        }

        return key;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppIdNamespaceDto getAppIdNamespace() {
        return appIdNamespace;
    }

    public void setAppIdNamespace(AppIdNamespaceDto appIdNamespace) {
        this.appIdNamespace = appIdNamespace;
    }

    public Key getParentKey() {
        return parentKey;
    }

    public void setParentKey(Key parentKey) {
        this.parentKey = parentKey;
    }

    private JSONObject createJsonKey(Key key) {
        JSONObject keyObject = new JSONObject();
        keyObject.put(KIND, new JSONString(key.kind));
        keyObject.put(NAME, nullOrString(key.name));
        keyObject.put(APP_ID, nullOrString(key.appId));
        keyObject.put(ID, new JSONNumber(key.id));

        JSONObject appIdNamespaceObject = new JSONObject();
        appIdNamespaceObject.put(APP_ID, new JSONString(key.appIdNamespace.getAppId()));
        appIdNamespaceObject.put(NAMESPACE, new JSONString(key.appIdNamespace.getNamespace()));
        keyObject.put(APP_ID_NAMESPACE, appIdNamespaceObject);

        return keyObject;
    }

    private JSONValue nullOrString(String value) {
        return value == null ? JSONNull.getInstance() : new JSONString(value);
    }
}
