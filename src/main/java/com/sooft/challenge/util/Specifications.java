package com.sooft.challenge.util;

import static java.util.stream.Collectors.toSet;

import com.sooft.challenge.dto.common.FilterDto;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import lombok.Generated;
import org.springframework.data.jpa.domain.Specification;

public class Specifications {

  @Generated
  private Specifications() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * @param specs List of Specification to be concatenated
   * @return List of Specification concatenated
   */
  public static <T> Specification<T> concatenate(List<Specification<T>> specs) {
    Specification<T> ret = Specification.where(null);
    for (Specification<T> spec : specs) {
      ret = ret.and(spec);
    }
    return ret;
  }

  /**
   * @param filter is the object which contains the filter fields.
   * @return Specification<T> which contains the filter criteria
   */
  public static <T> Specification<T> create(FilterDto filter) {
    Set<Entry<String, Object>> entries = filter.getFilterMap().entrySet();
    Set<Entry<String, Object>> nonNullEntries = entries.stream()
        .filter(e -> e.getValue() != null).collect(toSet());

    Specification<T> spec = Specification.where(null);

    for (Entry<String, Object> entry : nonNullEntries) {
      spec = spec.and(identical(entry.getKey(), entry.getValue()));
    }

    return spec;
  }

  /**
   * @param key   entity property used to filter
   * @param value value property used to filter
   * @return Specification<T> with one single equals filter
   */
  private static <T> Specification<T> identical(String key, Object value) {
    return (root, cq, cb) -> cb.equal(root.get(key), value);
  }
}
