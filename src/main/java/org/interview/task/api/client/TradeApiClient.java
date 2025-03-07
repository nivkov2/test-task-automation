package org.interview.task.api.client;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.interview.task.dto.TradeDataDto;
import org.interview.task.dto.TradeDto;

import java.util.List;
import java.util.Map;


import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.interview.task.config.ConfigurationManager.config;

public class TradeApiClient {
    private final String TRADE_URL = config().baseBitmexUrl() + "/trade";
    private final String TRADE_BUCKETED_URL = config().baseBitmexUrl() + "/trade/bucketed";

    public List<TradeDto> getTrades(Map<String, String> params) {
        return given()
                .queryParams(params)
                .accept(JSON)
                .when()
                .get(TRADE_URL)
                .then()
                .statusCode(200)
                .extract().as(new TypeRef<>() {});
    }

    public Response getTradesResponse(Map<String, Object> params) {
        return given()
                .queryParams(params)
                .accept(JSON)
                .when()
                .get(TRADE_URL);
    }

    public List<TradeDataDto> getTradesData(Map<String, String> params) {
        return given()
                .queryParams(params)
                .accept(JSON)
                .when()
                .get(TRADE_BUCKETED_URL)
                .then()
                .statusCode(200)
                .extract().as(new TypeRef<>() {});
    }
}
