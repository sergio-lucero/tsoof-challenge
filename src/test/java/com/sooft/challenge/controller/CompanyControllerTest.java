package com.sooft.challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sooft.challenge.AbstractIntegrationTest;
import com.sooft.challenge.dto.controller.CompanyCreatedDto;
import com.sooft.challenge.dto.controller.CompanyDto;
import java.time.LocalDate;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest
class CompanyControllerTest extends AbstractIntegrationTest {

  private final MockMvc mockMvc;
  private final ObjectMapper objectMapper;

  @Autowired
  CompanyControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
    this.mockMvc = mockMvc;
    this.objectMapper = objectMapper;
  }

  @Test
  void findAll() throws Exception {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("limit", "100");
    params.add("page", "0");

    mockMvc.perform(get("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .queryParams(params))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.limit").value(100))
        .andExpect(jsonPath("$.page").value(0))
        .andExpect(jsonPath("$.data.length()").value(Matchers.greaterThanOrEqualTo(5)));
  }

  @Test
  void findAllFilterIdNotExists() throws Exception {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("limit", "100");
    params.add("page", "0");
    params.add("ids", UUID.randomUUID().toString());

    mockMvc.perform(get("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .queryParams(params))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.limit").value(100))
        .andExpect(jsonPath("$.page").value(0))
        .andExpect(jsonPath("$.data.length()").value(Matchers.equalTo(0)));
  }

  @Test
  void findAllFilterDate() throws Exception {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("limit", "100");
    params.add("page", "0");
    params.add("createdFrom", LocalDate.now().minusYears(5).toString());
    params.add("createdTo", LocalDate.now().minusYears(6).toString());


    mockMvc.perform(get("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .queryParams(params))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.limit").value(100))
        .andExpect(jsonPath("$.page").value(0))
        .andExpect(jsonPath("$.data.length()").value(Matchers.equalTo(0)));
  }

  @Test
  void findAllErrorType400() throws Exception {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("limit", "100");
    params.add("page", "0");
    params.add("ids", "cualquiera");


    mockMvc.perform(get("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .queryParams(params))
        .andExpect(status().isBadRequest());
  }

  @Test
  void findAllErrorType400WithOutPaginated() throws Exception {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();


    mockMvc.perform(get("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .queryParams(params))
        .andExpect(status().isBadRequest());
  }

  @Test
  void findById() throws Exception {
    mockMvc.perform(get("/api/companies/421dd326-244b-48ea-b3d2-96ca3fa437a7")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("421dd326-244b-48ea-b3d2-96ca3fa437a7"));
  }

  @Test
  void findByIdNotExists() throws Exception {
    mockMvc.perform(get("/api/companies/bc4967d5-36fc-43c0-960b-d96c7ef67087")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void create() throws Exception {

    CompanyCreatedDto request = new CompanyCreatedDto();
    request.setCompanyName("TestName");
    request.setCuit("12346527");

    var response =  mockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.companyName").value("TestName"))
        .andReturn()
        .getResponse()
        .getContentAsString();

    CompanyDto companyResponse = objectMapper.readValue(
        response, CompanyDto.class);

    mockMvc.perform(get("/api/companies/" + companyResponse.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(companyResponse.getId().toString()));
  }

  @Test
  void createCompanyNameEmpty() throws Exception {

    CompanyCreatedDto request = new CompanyCreatedDto();
    request.setCompanyName("");
    request.setCuit("12346527");

    mockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());

  }

  @Test
  void createCuitEmpty() throws Exception {

    CompanyCreatedDto request = new CompanyCreatedDto();
    request.setCompanyName("Test");
    request.setCuit("");

    mockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());

  }

  @Test
  void update() throws Exception {

    CompanyCreatedDto request = new CompanyCreatedDto();
    request.setCompanyName("NameChange");
    request.setCuit("1234652786");

    mockMvc.perform(put("/api/companies/119c6763-b33d-4426-98ad-9562a1b260a0")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.companyName").value("NameChange"))
        .andExpect(jsonPath("$.cuit").value("1234652786"));
  }

  @Test
  void updateNotExists() throws Exception {

    CompanyCreatedDto request = new CompanyCreatedDto();
    request.setCompanyName("NameChange");
    request.setCuit("1234652786");

    mockMvc.perform(put("/api/companies/bc4967d5-36fc-43c0-960b-d96c7ef67087")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound());
  }

  @Test
  void delete() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/companies/b37fbfb9-0a7e-4624-834a-5ab0712d8073")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteNotFount() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/companies/bc4967d5-36fc-43c0-960b-d96c7ef67087")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}