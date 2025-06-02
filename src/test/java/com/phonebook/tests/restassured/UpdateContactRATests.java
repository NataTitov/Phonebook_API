package com.phonebook.tests.restassured;

import com.phonebook.dto.ContactDto;
import com.phonebook.dto.UpdateContactDto;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UpdateContactRATests extends TestBase {

    String id;

    @BeforeMethod
    public void precondition() {
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
        String[] split = message.split(": ");
        id = split[1];

    }
    @Test
    public void updateContactTest() {
        UpdateContactDto updateContact = UpdateContactDto.builder()
                .id(id)
                .name("Thomas")
                .lastName("Smith")
                .email("TS@gm.com")
                .phone("0987654321")
                .address("Berlin")
                .description("Description")
                .build();

        given().header(AUTHORIZATION, TOKEN)
                .body(updateContact)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was updated"));


    }
}