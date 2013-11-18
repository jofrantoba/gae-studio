/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.companion.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.companion.dao.CarDao;
import com.arcbees.gaestudio.companion.domain.Car;

@Path(TestEndPoints.CAR)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarResource {
    private final CarDao carDao;

    @Inject
    CarResource(CarDao carDao) {
        this.carDao = carDao;
    }

    @POST
    public Response createCar(Car car) {
        car.setId(null);

        carDao.put(car);

        return Response.ok(car.getId()).build();
    }

    @GET
    public Response getCar(@QueryParam(TestEndPoints.PARAM_ID) Long id) {
        Car car = carDao.get(id);

        return Response.ok(car).build();
    }

    @DELETE
    public Response deleteCar(Long id) {
        carDao.delete(id);

        return Response.noContent().build();
    }
}