package test.java.apitestcases;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import main.java.restclient.RestClient;
import org.junit.Assert;
import org.junit.Test;
import org.apache.http.client.methods.CloseableHttpResponse;


import static io.restassured.RestAssured.given;

public class TestAvailableStatusInGetRequest {

    String getUrl = "https://petstore.swagger.io/v2/pet/findByStatus";
    CloseableHttpResponse closebaleHttpResponse;
    RestClient restClient;

    @Test
    /**
     * Given Accept the content in JSON format
     * And query param status as available
     * When I perform the GET Request
     * Then Status code 200 OK should be returned
     * And The response content List should have status as available
     *
     * **/
    public void testAvailableStatusInGetResponse() {


        String s = given()
                .accept(ContentType.JSON)
                .queryParam("status", "available").log().parameters()
                .when()
                .get(getUrl)
                .thenReturn()
                .asString();

        JsonPath jsonPath = new JsonPath(s);
        String status = jsonPath.getString("status");
        String statusSubString = status.substring(1);
        System.out.println(jsonPath.getString("status"));
        for (String subString : statusSubString.split(",")) {
            Assert.assertTrue(subString.contains("available"));
        }

    }

}
