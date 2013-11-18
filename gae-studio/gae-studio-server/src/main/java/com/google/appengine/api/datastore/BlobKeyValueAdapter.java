/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.google.appengine.api.datastore;

import java.lang.reflect.Type;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BlobKeyValueAdapter implements JsonSerializer<BlobKey>, JsonDeserializer<BlobKey> {
    @Override
    public BlobKey deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String value = context.deserialize(json, String.class);

        return new BlobKey(value);
    }

    @Override
    public JsonElement serialize(BlobKey src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getKeyString());
    }
}