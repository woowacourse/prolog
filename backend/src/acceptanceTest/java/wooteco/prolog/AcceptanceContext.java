package wooteco.prolog;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import wooteco.prolog.member.application.dto.MemberScrapRequest;

@Component
@Scope(scopeName = "cucumber-glue")
public class AcceptanceContext {

    public RequestSpecification request;
    public Response response;
    public String accessToken;

    public AcceptanceContext() {
        reset();
    }

    private void reset() {
        request = null;
        response = null;
        accessToken = "";
    }

    public void invokeHttpGet(String path, Object... pathParams) {
        request = RestAssured.given().log().all();
        response = request.when().get(path, pathParams);
        response.then().log().all();
    }

    public void invokeHttpPost(String path, Object data) {
        request = RestAssured
            .given().log().all()
            .body(data).contentType(ContentType.JSON);
        response = request.post(path);
        response.then().log().all();
    }

    public void invokeHttpGetWithToken(String path, Object... pathParams) {
        request = RestAssured.given().log().all()
            .auth().oauth2(accessToken);
        response = request.when().get(path, pathParams);
        response.then().log().all();
    }

    public void invokeHttpPutWithToken(String path, Object data) {
        request = RestAssured
            .given().log().all()
            .body(data).contentType(ContentType.JSON)
            .auth().oauth2(accessToken);
        response = request.put(path);
        response.then().log().all();
    }

    public void invokeHttpPostWithToken(String path, Object data) {
        request = RestAssured
            .given().log().all()
            .body(data).contentType(ContentType.JSON)
            .auth().oauth2(accessToken);
        response = request.post(path);
        response.then().log().all();
    }

    public void invokeHttpDeleteWithToken(String path) {
        request = RestAssured
            .given().log().all()
            .auth().oauth2(accessToken);
        response = request.delete(path);
        response.then().log().all();
    }

    public void invokeHttpDeleteWithToken(String path, Object data) {
        request = RestAssured
            .given().log().all()
            .body(data).contentType(ContentType.JSON)
            .auth().oauth2(accessToken);
        response = request.delete(path);
        response.then().log().all();
    }
}
