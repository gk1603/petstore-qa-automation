package test.java.apitestcases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import junit.framework.TestCase;
import main.java.restclient.RestClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Assert;
import org.junit.Test;
import main.java.apipojo.AddPet;
import main.java.apipojo.Category;
import main.java.apipojo.Tag;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TestAddPetApiRequest extends TestCase {

    String addPetUrl = "https://petstore.swagger.io/v2/pet";
    String getUrl = "https://petstore.swagger.io/v2/pet/findByStatus";
    String getPetById = "https://petstore.swagger.io/v2/pet/";
    ExtentTest testlog;
    ExtentReports report;
    ExtentHtmlReporter htmlreport = new ExtentHtmlReporter("./reports/PetStoreTestCases.html");

    Properties properties = new Properties();

    CloseableHttpResponse closebaleHttpResponse;
    RestClient restClient;

    @Test
    /**
     * Given Accept the content in JSON format
     * When I perform the POST Request
     * Then Status code 200 OK should be returned
     * Now trigger query by id API to validate newly added pet on the store
     *
     * **/


    public void testNewAddedPet() throws IOException {
        report = new ExtentReports();
        report.attachReporter(htmlreport);
        testlog = report.createTest("JAck Test");
        restClient = new RestClient();
        Category category = new Category();
        category.setId(1002);
        category.setName("JackCategory");
        Tag tag = new Tag();
        tag.setId(1003);
        tag.setName("JackTag");

        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag);

        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");

        //jackson API:
        ObjectMapper mapper = new ObjectMapper();
        AddPet addPet = new AddPet();
        List<String> photoUrl = new ArrayList<>();
        photoUrl.add("String");
        addPet.setId(1001);
        addPet.setName("Jack");
        addPet.setPhotoUrls(photoUrl);
        addPet.setStatus("available");
        addPet.setCategory(category);
        addPet.setTags(tagList);
        //object to json file:
        mapper.writeValue(new File("src/main/resources/addpet.json"), addPet);

        //java object to json in String:
        String addPetJsonString = mapper.writeValueAsString(addPet);
        System.out.println(addPetJsonString);

        closebaleHttpResponse = restClient.post(addPetUrl, addPetJsonString, headerMap); //call the API

        //validate response from API:
        int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, 200);


        //Now we will trigger get pet by id api to check if the added pet is visible on the store
        String s = given()
                .accept(ContentType.JSON)
                .when().log().parameters()
                .get(getPetById + "1001")
                .thenReturn()
                .asString();

        JsonPath jsonPath = new JsonPath(s);
        String name = jsonPath.getString("name");
        Assert.assertEquals(name, "Jack");
        testlog.log(Status.PASS, "Jack verified successfully");
    }
    public void tearDown() {
        report.flush();
    }

}
