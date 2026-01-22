package com.contactapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for ChatController.
 * Tests the chat endpoint with various prompts using test data from the database.
 * 
 * SETUP REQUIRED:
 * 1. Start docker-compose: docker-compose up
 * 2. Wait for all services to be ready (PostgreSQL, Backend, Frontend)
 * 3. Run these tests: mvn test -Dtest=ChatControllerIntegrationTest
 * 
 * Test data is initialized from init-db/01-init.sql by docker-compose
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName("ChatController Integration Tests")
class ChatControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should handle chat prompt to get all contacts")
    void testChatGetAllContacts() throws Exception {
        String prompt = "List all available contacts";

        MvcResult result = mockMvc.perform(post("/api/chat")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new ChatController.Input(prompt))))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).isNotEmpty();
        assertThat(response).contains("content");
    }

    @Test
    @DisplayName("Should handle chat prompt to search for a specific contact")
    void testChatSearchContact() throws Exception {
        String prompt = "Search for John Doe";

        MvcResult result = mockMvc.perform(post("/api/chat")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new ChatController.Input(prompt))))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).isNotEmpty();
        assertThat(response).contains("content");
    }

    @Test
    @DisplayName("Should handle chat prompt to search for contacts by company")
    void testChatSearchByCompany() throws Exception {
        String prompt = "Show me all contacts from Tech Corp";

        MvcResult result = mockMvc.perform(post("/api/chat")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new ChatController.Input(prompt))))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).isNotEmpty();
        assertThat(response).contains("content");
    }

    @Test
    @DisplayName("Should handle chat prompt to search by email")
    void testChatSearchByEmail() throws Exception {
        String prompt = "Find contact with email jane.smith@example.com";

        MvcResult result = mockMvc.perform(post("/api/chat")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new ChatController.Input(prompt))))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).isNotEmpty();
        assertThat(response).contains("content");
    }

    @Test
    @DisplayName("Should handle chat prompt to search by job title")
    void testChatSearchByJobTitle() throws Exception {
        String prompt = "Show me all Software Engineers";

        MvcResult result = mockMvc.perform(post("/api/chat")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new ChatController.Input(prompt))))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).isNotEmpty();
        assertThat(response).contains("content");
    }

    @Test
    @DisplayName("Should handle chat prompt to search by location")
    void testChatSearchByLocation() throws Exception {
        String prompt = "Find all contacts in San Francisco";

        MvcResult result = mockMvc.perform(post("/api/chat")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new ChatController.Input(prompt))))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).isNotEmpty();
        assertThat(response).contains("content");
    }

    @Test
    @DisplayName("Should validate input prompt is not blank")
    void testChatWithBlankPrompt() throws Exception {
        mockMvc.perform(post("/api/chat")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new ChatController.Input(""))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle chat prompt to get contact count")
    void testChatGetContactCount() throws Exception {
        String prompt = "How many contacts are in the system?";

        MvcResult result = mockMvc.perform(post("/api/chat")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new ChatController.Input(prompt))))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).isNotEmpty();
        assertThat(response).contains("content");
    }

    @Test
    @DisplayName("Should handle chat prompt for current date/time")
    void testChatWithDateTimeQuery() throws Exception {
        String prompt = "What is today's date?";

        MvcResult result = mockMvc.perform(post("/api/chat")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new ChatController.Input(prompt))))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).isNotEmpty();
        assertThat(response).contains("content");
    }

    @Test
    @DisplayName("Should handle chat prompt to search by phone number")
    void testChatSearchByPhone() throws Exception {
        String prompt = "Find contact with phone +12025551234";

        MvcResult result = mockMvc.perform(post("/api/chat")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new ChatController.Input(prompt))))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).isNotEmpty();
        assertThat(response).contains("content");
    }


    /**
     * Inner class to match the ChatController's Input record for serialization
     */
    record Input(String prompt) {}
}
