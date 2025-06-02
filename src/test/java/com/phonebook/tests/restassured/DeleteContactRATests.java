package com.phonebook.tests.restassured;

import com.phonebook.dto.ContactDto;
import com.phonebook.dto.ErrorDto;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactRATests extends TestBase {

    String id;

    @BeforeMethod
    public void precondition(){
        ContactDto contactDTO = ContactDto.builder()
                .name("testName")
                .lastName("testLastName")
                .email("testEmail@gm.com")
                .phone("1234567890")
                .address("testAddress")
                .description("testDescription")
                .build();

        String message = given().header(AUTHORIZATION, TOKEN)
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .extract().path("message");
        //System.out.println(message);
        //Contact was added! ID: 6f05c586-5885-4a4a-8e60-387bd7e63094
        String[] split = message.split(": ");
        id = split[1];
    }
    @Test
    public void deleteContactTest() {
        //String message =
                given()
                .header(AUTHORIZATION, TOKEN)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was deleted!"))
                //.extract().path("message")
                ;
        //System.out.println(message);
        //Contact was deleted!
    }
    @Test
    public void deleteContactByWrongTest() {
       // ErrorDto errorDto =
                given()
                .header(AUTHORIZATION, TOKEN)
                .when()
                .delete("contacts/6f05c586-5885-4a4a-8e60-387bd7e63095")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message", containsString("not found in your contacts"));
                //.extract().body().as(ErrorDto.class)
                //System.out.println(errorDto.getMessage());
    }
}
