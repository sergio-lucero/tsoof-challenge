package com.sooft.challenge.exception.utils;

import com.sooft.challenge.util.ObjectMappers;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExceptionUtil {
  private ExceptionUtil() {
    throw new IllegalStateException("Utility class");
  }

  private static final int START_LINES = 3;

  public static String format(Throwable t) {

    final StringWriter sw = new StringWriter();
    t.printStackTrace(new PrintWriter(sw, true));

    String[] stackTraceLines = sw.toString().split(System.lineSeparator());

    int min = Math.min(START_LINES, stackTraceLines.length);

    List<String> lines = new ArrayList<>();

    for (int i = 0; i < min; i++) {
      lines.add(stackTraceLines[i].trim());
    }

    for (int i = min; i < stackTraceLines.length; i++) {
      if (stackTraceLines[i].contains("com.osana")) {
        lines.add(stackTraceLines[i].trim());
      }
    }

    return String.join(" ", lines);
  }

  @SuppressWarnings("unchecked")
  public static Map<String, String> extractErrorMap(String errorMessage) {
    Map<String, String> errorMap = new HashMap<>();
    try {
      String json = errorMessage.substring(errorMessage.indexOf('{'),
          errorMessage.lastIndexOf('}') + 1);
      errorMap = ObjectMappers.INSTANCE.readValue(json, Map.class);
    } catch (Exception ignored) {
      // nothing to do
    }

    return errorMap;
  }
}
