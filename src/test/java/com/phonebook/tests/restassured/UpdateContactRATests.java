package com.phonebook.tests.restassured;

import com.phonebook.dto.ContactDto;
import com.phonebook.dto.UpdateContactDto;
import io.restassured.http.ContentType;
import org.testng.annotations.AfterMethod;
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
    public void updateContactPositiveTest() {
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
    @Test
    public void updateContactWithWrongPhoneNegativeTest() {
        UpdateContactDto updateContact = UpdateContactDto.builder()
                .id(id)
                .name("Thomas")
                .lastName("Smith")
                .email("TS@gm.com")
                .phone("123")
                .address("Berlin")
                .description("Description")
                .build();

                given().header(AUTHORIZATION, TOKEN)
                .body(updateContact)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.phone", equalTo("Phone number must contain only digits! And length min 10, max 15!"));

    }
    @Test
    public void updateContactWithWrongIDNegativeTest() {
        UpdateContactDto updateContact = UpdateContactDto.builder()
                .id("7f3eb125-53e3-4af2-b6e1-fbdf742c47e0")
                .name("Thomas")
                .lastName("Smith")
                .email("TS@gm.com")
                .phone("1234567890")
                .address("Berlin")
                .description("Description")
                .build();

        given().header(AUTHORIZATION, TOKEN)
                .body(updateContact)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(403)
                .assertThat().body("message", equalTo("Contact with id: 7f3eb125-53e3-4af2-b6e1-fbdf742c47e0 not found in your contacts!"));
    }
    @AfterMethod
    public void deleteContact() {
        given()
                .header(AUTHORIZATION, TOKEN)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was deleted!"))
        ;
    }
}