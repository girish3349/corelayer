package org.framework.utilities;


import io.restassured.response.Response;
import io.restassured.specification.*;
import org.json.JSONObject;
import org.testng.Assert;

import java.net.http.HttpResponse;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiUtils {

    public static RequestSpecification request;
    public static QueryableRequestSpecification queryRequest;

    static {
        request = given().contentType("application/json");
        queryRequest = SpecificationQuerier.query(request);
    }

    protected String url;
    private Response response;

    public static void validateStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        if (actualStatusCode != expectedStatusCode) {
            Screenshots.addStepInReport("Expected status code : " + expectedStatusCode + ", actual status code : " + actualStatusCode);
            throw new AssertionError("Expected status code : " + expectedStatusCode + ", but got actual status code : " + actualStatusCode);
        } else {
            AssertionLibrary.assertTrue(true, "Expeccted status code : " + expectedStatusCode + " is same as Expected status code :" + actualStatusCode);
        }
    }

    public void setBaseURI(String baseURI) {
        request.baseUri(baseURI);
        if (baseURI.startsWith("https")) {
            request.relaxedHTTPSValidation("TLS");
        }
    }

    public void initializeRequest() {
        request = given().contentType("application/json");
        queryRequest = SpecificationQuerier.query(request);
    }

    protected void constructUrl(String endPoint) {
        url = queryRequest.getBaseUri() + endPoint;
    }

    public void addHeader(String key, String value) {
        request.header(key, value);
    }

    public void addHeaders(Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.header(entry.getKey(), entry.getValue());
        }
    }

    public void setRequestBody(String body) {
        request.body(body);
    }

    public void addFormParameter(String key, String value) {
        request.formParams(key, value);
    }

    public void addQueryParameter(String key, String value) {
        request.queryParams(key, value);
    }

    public void addAuthBearerToken(String token) {
        request.header("Authorization", "Bearer " + token);
    }

    //API Caller Methods
    public void post(String endPoint) {
        constructUrl(endPoint);
        response = request.post(url);
    }

    public void post() {
        response = request.post();
    }

    public void put(String endPoint) {
        constructUrl(endPoint);
        response = request.put(url);
    }

    public void put() {
        response = request.put();
    }

    public void get(String endPoint) {
        constructUrl(endPoint);
        response = request.get(url);
    }

    public void get() {
        response = request.get();
    }

    public void delete(String endPoint) {
        constructUrl(endPoint);
        response = request.delete(url);
    }

    public void delete() {
        response = request.delete();
    }


    // Resopnse/ Validation Methods

    public Response getResponse() {
        return response;
    }


    public void validateStatusCode(int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status code does not match.");
    }
}
