package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Gender;
import com.cydeo.enums.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mvc;

    static String token;

    static UserDTO manager;
    static ProjectDTO project;

    @BeforeAll
    static void setUp() {

        token = "Bearer " + "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJBUWZjcVVQNGs2UTQ5V3hZa0JTR3p2cUxod2RQVGJrems1UC02N2dxZVNZIn0.eyJleHAiOjE2NzE0MDk2MzIsImlhdCI6MTY3MTM5MTYzMiwianRpIjoiZmFlZjQwOTQtNTE3NS00ODc4LWJlZGYtMTcwZmQ2NDk3NzNjIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL2N5ZGVvLWRldiIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJkNWE5ZDQxZC0yM2VmLTRmYzAtODVkMC05OTUwNWRlMjc5YWEiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ0aWNrZXRpbmctYXBwIiwic2Vzc2lvbl9zdGF0ZSI6IjJiMzNhZTViLTE2NzAtNGI1MS04MjA0LWJmYmNlOTU0NTE0YyIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDo4MDgxIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIiwiZGVmYXVsdC1yb2xlcy1jeWRlby1kZXYiXX0sInJlc291cmNlX2FjY2VzcyI6eyJ0aWNrZXRpbmctYXBwIjp7InJvbGVzIjpbIk1hbmFnZXIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJzaWQiOiIyYjMzYWU1Yi0xNjcwLTRiNTEtODIwNC1iZmJjZTk1NDUxNGMiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwicHJlZmVycmVkX3VzZXJuYW1lIjoib3p6eSJ9.dIRLo8PIm8xqNCf4QDjMP2pkPv4NM6tmX0dJN8glesacXnbu3e0IYQP-AQvDb_hq6NXuoJTEXOAazJ_3tdvr2OMSZ0AjiirmZYlqZzMyiiKYVd8MoWNRwNOToHw1Em51EWgA4kTSRqpikVYfv6TNO-BM7uooqxh8OKLD7i05enmahWsWmt41DhBt0fxB8-QVzNrJBw8lOdryJKITPIiUQDmZHJyTN6cKnaxEVtpgTLQ8tmE9rO4I4kbBDNAroK_ly6kxj1gmLA-Vr2lW_R7bvyzAs_WfxShzJ6Pq6srxreiZEHYdqaVnyyDjaZIJ6VUrde3dziGrfK9_Dq_YYwfgbw";

        manager = new UserDTO(2L,
                "",
                "",
                "ozzy",
                "abc1",
                "",
                true,
                "",
                new RoleDTO(2L, "Manager"),
                Gender.MALE);

        project = new ProjectDTO(
                "API Project",
                "PR001",
                manager,
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                "Some details",
                Status.OPEN
        );

    }

    @Test
    void givenNoToken_getProjects() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/project"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void givenToken_getProjects() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/project")
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].projectCode").exists())
                .andExpect(jsonPath("$.data[0].assignedManager.userName").exists())
                .andExpect(jsonPath("$.data[0].assignedManager.userName").isNotEmpty())
                .andExpect(jsonPath("$.data[0].assignedManager.userName").isString())
                .andExpect(jsonPath("$.data[0].assignedManager.userName").value("ozzy"));

    }

    @Test
    void givenToken_createProject() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/project")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJsonString(project)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Project is successfully created"));

    }

    private String toJsonString(final Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(obj);
    }

}
