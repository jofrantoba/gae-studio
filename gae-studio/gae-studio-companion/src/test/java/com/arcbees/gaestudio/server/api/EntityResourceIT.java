/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.api;

import org.junit.Test;

import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.companion.rest.TestEndPoints;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;

public class EntityResourceIT extends RestIT {
    private Long UNEXISTENT_ID = 99999999l;

    @Test
    public void getEntity_createCar_shouldReturnEntity() {
        //given
        Long carId = createRemoteCar();

        //when
        Response response = getCarEntityResponse(carId);
        EntityDto entityDto = responseToEntityDto(response);

        //then
        assertEquals(OK.getStatusCode(), response.getStatusCode());
        assertEquals(CAR_KIND, entityDto.getKey().getKind());
        assertEquals((long) carId, (long) entityDto.getKey().getId());
    }

    @Test
    public void getEntity_createStringIdEntity_shouldReturnEntity() {
        //given
        createStringIdEntity(AN_ENTITY_NAME);

        //when
        Response response = getStringIdEntityResponse(AN_ENTITY_NAME);
        EntityDto entityDto = responseToEntityDto(response);

        //then
        assertEquals(OK.getStatusCode(), response.getStatusCode());
        assertEquals(STRING_ENTITY_KIND, entityDto.getKey().getKind());
        assertEquals(AN_ENTITY_NAME, entityDto.getKey().getName());
    }

    @Test
    public void getEntityWithIdAndName_createStringIdEntity_shouldReturnBadRequest() {
        //given
        Long carId = createRemoteCar();

        //when
        Response response = getEntityWithIdAndNameResponse(AN_ENTITY_NAME, carId);

        //then
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void getUnexistentEntity_shouldReturnNotFound() {
        //when
        Response response = getCarEntityResponse(UNEXISTENT_ID);

        //then
        assertEquals(NOT_FOUND.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void deleteCar_createCar_shouldReturnNoContent() {
        //given
        Long carId = createRemoteCar();

        //when
        Response response = deleteEntityResponse(carId);

        //then
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void updateCar_createCar_shouldReturnOK() {
        //given
        Car car = new Car();
        car.setMake("OldMake");
        Long carId = createRemoteCar(car);

        //when
        Response response = getCarEntityResponse(carId);
        EntityDto entityDto = responseToEntityDto(response);
        String newJson = entityDto.getJson().replaceFirst("OldMake", "NewMake");
        entityDto.setJson(newJson);

        Response updateResponse = updateEntityResponse(entityDto);

        //then
        assertEquals(OK.getStatusCode(), updateResponse.getStatusCode());
    }

    private Response updateEntityResponse(EntityDto newEntityDto) {
        return given()
                .body(newEntityDto)
                .put(getAbsoluteUri(EndPoints.ENTITY));
    }

    private Response deleteEntityResponse(Long carId) {
        return given()
                .queryParam(TestEndPoints.PARAM_KIND, CAR_KIND)
                .queryParam(TestEndPoints.PARAM_ID, carId)
                .delete(getAbsoluteUri(EndPoints.ENTITY));
    }

    private EntityDto responseToEntityDto(Response response) {
        return gson.fromJson(response.asString(), EntityDto.class);
    }

    private Response getCarEntityResponse(Long carId) {
        return given()
                .queryParam(TestEndPoints.PARAM_KIND, CAR_KIND)
                .queryParam(TestEndPoints.PARAM_ID, carId)
                .get(getAbsoluteUri(EndPoints.ENTITY));
    }

    private Response getStringIdEntityResponse(String name) {
        return given()
                .queryParam(TestEndPoints.PARAM_KIND, STRING_ENTITY_KIND)
                .queryParam(TestEndPoints.PARAM_NAME, name)
                .get(getAbsoluteUri(EndPoints.ENTITY));
    }

    private Response getEntityWithIdAndNameResponse(String name, Long id) {
        return given()
                .queryParam(TestEndPoints.PARAM_KIND, CAR_KIND)
                .queryParam(TestEndPoints.PARAM_ID, id)
                .queryParam(TestEndPoints.PARAM_NAME, name)
                .get(getAbsoluteUri(EndPoints.ENTITY));
    }
}