package com.sooft.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class TestConfiguration {

  private final WebApplicationContext webApplicationContext;

  public TestConfiguration(WebApplicationContext webApplicationContext) {
    this.webApplicationContext = webApplicationContext;
  }

  @Bean
  public Boolean isTestEnvironment() {
    return true;
  }

  @Bean
  public MockMvc mockMvc() {
    return MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .build();
  }



}
