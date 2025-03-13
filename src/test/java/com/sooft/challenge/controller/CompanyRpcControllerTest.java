package com.sooft.challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sooft.challenge.AbstractIntegrationTest;
import com.sooft.challenge.dto.controller.CompanyCreatedDto;
import com.sooft.challenge.dto.controller.TransferCreatedDto;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest
class CompanyRpcControllerTest extends AbstractIntegrationTest {

  private final MockMvc mockMvc;
  private final ObjectMapper objectMapper;

  @Autowired
  CompanyRpcControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
    this.mockMvc = mockMvc;
    this.objectMapper = objectMapper;
  }

  @Test
  void findCompaniesCreatedBeforeMonths() throws Exception {

    CompanyCreatedDto request = new CompanyCreatedDto();
    request.setCompanyName("TestCreated");
    request.setCuit("1234652744");

    mockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.companyName").value("TestCreated"));

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("limit", "100");
    params.add("page", "0");
    params.add("months", "1");

    mockMvc.perform(get("/rpc/companies/created")
            .contentType(MediaType.APPLICATION_JSON)
            .params(params))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.limit").value(100))
        .andExpect(jsonPath("$.page").value(0))
        .andExpect(jsonPath("$.data.length()").value(Matchers.greaterThanOrEqualTo(1)));
  }

  @Test
  void findCompaniesCreatedBeforeMonthsErrorMonthNull() throws Exception {

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("limit", "100");
    params.add("page", "0");
    params.add("months", "-3");

    mockMvc.perform(get("/rpc/companies/created")
            .contentType(MediaType.APPLICATION_JSON)
            .params(params))
        .andExpect(status().isBadRequest());
  }

  @Test
  void findCompaniesCreatedBeforeMonthsErrorMonthNegative() throws Exception {

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("limit", "100");
    params.add("page", "0");

    mockMvc.perform(get("/rpc/companies/created")
            .contentType(MediaType.APPLICATION_JSON)
            .params(params))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Transactional
  void findCompaniesWithTransfers() throws Exception {

    TransferCreatedDto request = new TransferCreatedDto();
    request.setAmount(15.6);
    request.setDebitAccount("123213123");
    request.setCompanyId(UUID.fromString("421dd326-244b-48ea-b3d2-96ca3fa437a7"));

    mockMvc.perform(post("/api/transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.amount").value(15.6))
        .andExpect(jsonPath("$.debitAccount").value("123213123"));

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("limit", "100");
    params.add("page", "0");
    params.add("months", "1");

    mockMvc.perform(get("/rpc/companies/with/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .params(params))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.limit").value(100))
        .andExpect(jsonPath("$.page").value(0))
        .andExpect(jsonPath("$.data.length()").value(Matchers.greaterThanOrEqualTo(1)));
  }

  @Test
  @Transactional
  void findCompaniesWithTransfersMonthNegative() throws Exception {

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("limit", "100");
    params.add("page", "0");
    params.add("months", "-1");

    mockMvc.perform(get("/rpc/companies/with/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .params(params))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Transactional
  void findCompaniesWithTransfersMonthNull() throws Exception {

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("limit", "100");
    params.add("page", "0");

    mockMvc.perform(get("/rpc/companies/with/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .params(params))
        .andExpect(status().isBadRequest());
  }
}