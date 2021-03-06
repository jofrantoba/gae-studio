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

package com.arcbees.gaestudio.server.service.visualizer;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.json.JSONException;

import com.arcbees.gaestudio.server.channel.ChannelMessageSender;
import com.arcbees.gaestudio.server.channel.ClientId;
import com.arcbees.gaestudio.server.util.JsonToCsvConverter;
import com.arcbees.gaestudio.shared.Constants;
import com.arcbees.gaestudio.shared.channel.Message;
import com.arcbees.gaestudio.shared.channel.Topic;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

public class ExportServiceImpl implements ExportService {
    private final EntitiesService entitiesService;
    private final Gson gson;
    private final JsonToCsvConverter jsonToCsvConverter;
    private final ChannelMessageSender channelMessageSender;
    private final Provider<String> clientIdProvider;

    @Inject
    ExportServiceImpl(
            EntitiesService entitiesService,
            Gson gson,
            JsonToCsvConverter jsonToCsvConverter,
            ChannelMessageSender channelMessageSender,
            @ClientId Provider<String> clientIdProvider) {
        this.entitiesService = entitiesService;
        this.gson = gson;
        this.jsonToCsvConverter = jsonToCsvConverter;
        this.channelMessageSender = channelMessageSender;
        this.clientIdProvider = clientIdProvider;
    }

    @Override
    public String exportToJson(String kind, String namespace, String encodedKeys) {
        Iterable<Entity> entities;
        if (Strings.isNullOrEmpty(encodedKeys)) {
            entities = entitiesService.getEntities(kind, namespace, null, Constants.FREE_IMPORT_EXPORT_QUOTA);
        } else {
            List<Key> keys = decodeKeys(encodedKeys);

            entities = entitiesService.getEntities(keys);
        }

        String json = gson.toJson(entities);

        channelMessageSender.sendMessage(clientIdProvider.get(), new Message(Topic.EXPORT_COMPLETED));

        return json;
    }

    @Override
    public String exportToCsv(String kind, String namespace, String encodedKeys) throws JSONException {
        String json = exportToJson(kind, namespace, encodedKeys);
        String csv = jsonToCsvConverter.convert(json);

        channelMessageSender.sendMessage(clientIdProvider.get(), new Message(Topic.EXPORT_COMPLETED));

        return csv;
    }

    private List<Key> decodeKeys(String encodedKeys) {
        List<String> encodedKeysList = Splitter.on(",").limit(Constants.FREE_IMPORT_EXPORT_QUOTA)
                .splitToList(encodedKeys);
        return Lists.transform(encodedKeysList, new Function<String, Key>() {
            @Override
            public Key apply(String input) {
                return KeyFactory.stringToKey(input);
            }
        });
    }
}
