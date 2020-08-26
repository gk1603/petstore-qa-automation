package test.java.apitestcases;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import main.java.apipojo.AddPet;
import main.java.apipojo.Category;
import main.java.apipojo.Tag;
import main.java.restclient.RestClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class UpdatePetStatus {

    String addPetUrl = "https://petstore.swagger.io/v2/pet";
    String getPetById="https://petstore.swagger.io/v2/pet/";

    CloseableHttpResponse closebaleHttpResponse;
    RestClient restClient;

    @Test
    /**
     * Given Accept the content in JSON format
     * When I perform the PUT Request to update pet status from 'available' to 'sold'
     * Then Status code 200 OK should be returned
     * And The response content of get pet by id api should have status as sold
     *
     * **/
    public void updatePetStatusToSold() throws IOException {
        restClient = new RestClient();
        Category category = new Category();
        category.setId(2342);
        category.setName("PetToBeUpdatedCategory");
        Tag tag = new Tag();
        tag.setId(2342);
        tag.setName("PetToBeUpdatedTag");

        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag);


        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");

        //jackson API:
        ObjectMapper mapper = new ObjectMapper();
        AddPet addPet = new AddPet();
        List<String> photoUrl = new ArrayList<>();
        photoUrl.add("String");
        addPet.setId(2342);
        addPet.setName("PetToBeUpdated");
        addPet.setPhotoUrls(photoUrl);
        addPet.setStatus("available");
        addPet.setCategory(category);
        addPet.setTags(tagList);
        //object to json file:
        mapper.writeValue(new File("src/main/resources/addpet.json"), addPet);

        //java object to json in String:
        String addPetJsonString = mapper.writeValueAsString(addPet);
        System.out.println(addPetJsonString);
        /*
        adding a pet on the store
         */
        closebaleHttpResponse = restClient.post(addPetUrl, addPetJsonString, headerMap); //call the API

        //validate response from API:
        //1. status code:
        int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, 200);



        //jackson API:
        ObjectMapper mapper2 = new ObjectMapper();
        photoUrl.add("String");
        addPet.setId(2342);
        addPet.setName("PetToBeUpdated");
        addPet.setPhotoUrls(photoUrl);
        addPet.setStatus("sold");
        addPet.setCategory(category);
        addPet.setTags(tagList);
        //object to json file:
        mapper2.writeValue(new File("src/main/resources/updatepet.json"), addPet);

        //java object to json in String:
        String updatePetJsonString = mapper2.writeValueAsString(addPet);
        System.out.println(updatePetJsonString);
        /*
        updating the pet status from available to sold
         */
        closebaleHttpResponse = restClient.put(addPetUrl, updatePetJsonString, headerMap);
        int statusCode2 = closebaleHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode2, 200);


        /*
        validating that the pet status is now sold
         */
        String s = given()
                .accept(ContentType.JSON)
                .when().log().parameters()
                .get(getPetById+"2342")
                .thenReturn()
                .asString();

        JsonPath jsonPath = new JsonPath(s);
        String name = jsonPath.getString("status");
        Assert.assertEquals(name,"sold");

    }
}
