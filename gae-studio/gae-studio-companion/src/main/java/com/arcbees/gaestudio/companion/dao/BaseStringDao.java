/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.companion.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.LoadType;

import static com.arcbees.gaestudio.companion.dao.OfyService.ofy;

public abstract class BaseStringDao<T extends HasStringId> {
    protected final Class<T> clazz;

    protected BaseStringDao(final Class<T> clazz) {
        this.clazz = clazz;
    }

    public boolean update(String id,
            T entity) {
        if (exists(id)) {
            entity.setName(id);
            put(entity);
            return true;
        }

        return false;
    }

    public void delete(String id) {
        ofy().delete().type(clazz).id(id).now();
    }

    public List<T> getAll() {
        return query().list();
    }

    public T put(T object) {
        ofy().save().entity(object).now();
        return object;
    }

    public Collection<T> put(Iterable<T> entities) {
        return ofy().save().entities(entities).now().values();
    }

    public T get(Key<T> key) {
        return ofy().load().key(key).now();
    }

    public List<T> get(List<Key<T>> keys) {
        return Lists.newArrayList(ofy().load().keys(keys).values());
    }

    public T get(String id) {
        return query().id(id).now();
    }

    public Boolean exists(String id) {
        return exists(clazz, id);
    }

    public boolean exists(Class<? extends HasStringId> clazz,
            String id) {
        return exists(Key.create(clazz, id));
    }

    public boolean exists(Key<? extends HasStringId> key) {
        ofy().clear(); // Needed to prevent datastore misses
        return ofy().load().key(key).now() != null;
    }

    public List<T> getSubset(Iterable<String> ids) {
        return new ArrayList<T>(query().ids(ids).values());
    }

    public Map<String, T> getSubsetMap(Iterable<String> ids) {
        return new HashMap<String, T>(query().ids(ids));
    }

    public void delete(T object) {
        ofy().delete().entity(object);
    }

    public void delete(List<T> objects) {
        ofy().delete().entities(objects);
    }

    public void delete(Object parent,
            String id) {
        ofy().delete().type(clazz).parent(parent).id(id).now();
    }

    protected LoadType<T> query() {
        return ofy().load().type(clazz);
    }

    public void deleteAll() {
        List<Key<T>> keys = ofy().load().type(clazz).keys().list();
        ofy().delete().entities(keys).now();
    }
}
