/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.google.appengine.api.datastore;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

public class AppIdNamespaceInstanceCreator implements InstanceCreator<AppIdNamespace> {
    @Override
    public AppIdNamespace createInstance(Type type) {
        return KeyFactory.createKey("a", 1l).getAppIdNamespace();
    }
}
