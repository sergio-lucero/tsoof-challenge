package com.sooft.challenge.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.util.function.Supplier;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class ObjectMappers {

  private ObjectMappers() {
  }

  public static final ObjectMapper INSTANCE = Jackson2ObjectMapperBuilder.json().build()
      .registerModule(new ParameterNamesModule());

  public static final Supplier<ObjectMapper> SUPPLIER = () -> INSTANCE;
}
