package com.example.bookapi;

import com.example.bookapi.model.Book;
import com.example.bookapi.dto.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    private String jwtToken;

    @BeforeEach
    public void authenticate() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user");
        loginRequest.setPassword("password");

        String response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        jwtToken = "Bearer " + response;
    }

    @Test
    public void testAddAndGetBook() throws Exception {
        Book book = new Book();
        book.setIsbn("1234567890");
        book.setTitle("The Test Book");
        book.setAuthor("Tester");
        book.setPublicationYear(2023);

        mockMvc.perform(post("/api/books")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn", is("1234567890")));

        mockMvc.perform(get("/api/books/1234567890")
                .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("The Test Book")));
    }

@Test
public void testDeleteBook() throws Exception {
    // First: add a book so we can delete it
    Book book = new Book();
    book.setIsbn("9999999999");
    book.setTitle("Temp Book");
    book.setAuthor("Delete Me");
    book.setPublicationYear(2020);

    mockMvc.perform(post("/api/books")
            .header("Authorization", jwtToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(book)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isbn", is("9999999999")));

    // Now: delete that book
    mockMvc.perform(delete("/api/books/9999999999")
            .header("Authorization", jwtToken))
            .andExpect(status().isOk());

    // Optional: Try to get the deleted book and expect 404 or failure (if you return null)
    mockMvc.perform(get("/api/books/9999999999")
            .header("Authorization", jwtToken))
            .andExpect(status().isNotFound()); // adjust based on your controller logic
}
}