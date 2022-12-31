package com.rich.budgetapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.rich.budgetapi.core.io.Base64ProtocolResolver;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

import java.io.File;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = Base64ProtocolResolver.class)
@TestPropertySource("classpath:application-test.properties")
@WithMockUser(
        username = "maria.joaquina@budget.com",
        authorities = {
            "SCOPE_WRITE",
            "SCOPE_READ",
            "CHANGE_USERS_PROFILES_PERMISSIONS",
            "CONSULT_USERS_PROFILES_PERMISSIONS"
        }
)
public class UserPhotoIntegrationTest {
    
    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @BeforeEach
	public void setUp() {
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
		RestAssuredMockMvc.mockMvc(mockmvc);
		RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssuredMockMvc.basePath = "/users";
	}

    @Test
    public void shouldReturnStatus200_WhenConsultPhotoUser() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/{userId}/photo", 1)
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shoulReturnStatus200_WhenUpdatePhotoUser() {
        given()
            .body(new File("C:/Users/MatheusVieira/Downloads/user.jpeg"))
            .contentType(ContentType.MULTIPART)
        .when()
            .put("/{userId}/photo", 1)
        .then()
            .statusCode(HttpStatus.OK.value());
    }

}
