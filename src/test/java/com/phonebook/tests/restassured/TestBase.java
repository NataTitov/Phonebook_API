package com.phonebook.tests.restassured;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class TestBase {
    public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiMWFAMWIuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3NDk0NTY5MzksImlhdCI6MTc0ODg1NjkzOX0.B3A1HXmtKLJorzrUCXsb3kj3XyE7FzydQKBW6RUhHZo";
    public static final String AUTHORIZATION = "Authorization";

    @BeforeMethod
    public void init() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }
}
