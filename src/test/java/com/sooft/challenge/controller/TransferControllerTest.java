package com.sooft.challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sooft.challenge.AbstractIntegrationTest;
import com.sooft.challenge.dto.controller.TransferCreatedDto;
import com.sooft.challenge.dto.controller.TransferDto;
import jakarta.transaction.Transactional;
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
class TransferControllerTest extends AbstractIntegrationTest {

  private final MockMvc mockMvc;
  private final ObjectMapper objectMapper;

  @Autowired
  TransferControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
    this.mockMvc = mockMvc;
    this.objectMapper = objectMapper;
  }

  @Test
  @Transactional
  void findAll() throws Exception {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("limit", "100");
    params.add("page", "0");

    mockMvc.perform(get("/api/transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .queryParams(params))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.limit").value(100))
        .andExpect(jsonPath("$.page").value(0))
        .andExpect(jsonPath("$.data.length()").value(Matchers.greaterThanOrEqualTo(14)));
  }

  @Test
  @Transactional
  void findAllPageNotFount() throws Exception {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();


    mockMvc.perform(get("/api/transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .queryParams(params))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Transactional
  void findAllById() throws Exception {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("limit", "100");
    params.add("page", "0");
    params.add("ids", "6bfeae05-397b-45a7-99b1-56a53f0da58e");

    mockMvc.perform(get("/api/transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .queryParams(params))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.limit").value(100))
        .andExpect(jsonPath("$.page").value(0))
        .andExpect(jsonPath("$.data.length()").value(Matchers.equalTo(1)));
  }

  @Test
  @Transactional
  void findById() throws Exception {
    mockMvc.perform(get("/api/transfers/6bfeae05-397b-45a7-99b1-56a53f0da58e")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("6bfeae05-397b-45a7-99b1-56a53f0da58e"));
  }

  @Test
  @Transactional
  void findByIdNotFount() throws Exception {
    mockMvc.perform(get("/api/transfers/7fa7f777-9a6e-455f-9beb-9a38e29ef5fd")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void create() throws Exception {

    TransferCreatedDto request = new TransferCreatedDto();
    request.setAmount(15.6);
    request.setDebitAccount("123213123");
    request.setCompanyId(UUID.fromString("421dd326-244b-48ea-b3d2-96ca3fa437a7"));

    var response =  mockMvc.perform(post("/api/transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.amount").value(15.6))
        .andExpect(jsonPath("$.debitAccount").value("123213123"))
        .andReturn()
        .getResponse()
        .getContentAsString();

    TransferDto transferResponse = objectMapper.readValue(
        response, TransferDto.class);

    mockMvc.perform(get("/api/transfers/" + transferResponse.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(transferResponse.getId().toString()));
  }

  @Test
  @Transactional
  void createAmountError() throws Exception {

    TransferCreatedDto request = new TransferCreatedDto();
    request.setAmount(-15.6);
    request.setDebitAccount("123213123");
    request.setCompanyId(UUID.fromString("421dd326-244b-48ea-b3d2-96ca3fa437a7"));

    mockMvc.perform(post("/api/transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Transactional
  void createDebitAndCreditError() throws Exception {

    TransferCreatedDto request = new TransferCreatedDto();
    request.setAmount(15.6);
    request.setDebitAccount("123213123");
    request.setCreditAccount("asdsadsad");
    request.setCompanyId(UUID.fromString("421dd326-244b-48ea-b3d2-96ca3fa437a7"));

    mockMvc.perform(post("/api/transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Transactional
  void createCompanyError() throws Exception {

    TransferCreatedDto request = new TransferCreatedDto();
    request.setAmount(15.6);
    request.setCreditAccount("asdsadsad");
    request.setCompanyId(UUID.fromString("7fa7f777-9a6e-455f-9beb-9a38e29ef5fd"));

    mockMvc.perform(post("/api/transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound());
  }

  @Test
  void delete() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/transfers/4612c92a-aa68-4b28-b98f-ee91223d9d67")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteNotFount() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/transfers/bc4967d5-36fc-43c0-960b-d96c7ef67087")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}