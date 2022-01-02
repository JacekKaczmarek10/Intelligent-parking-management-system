package pl.kaczmarek.rest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * SearchCriteria
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class SearchCriteria {
  private String key;
  private String operation;
  private Object value;

  public static SearchCriteria create(String key, String operation, Object value) {
    return new SearchCriteria()
      .setKey(key)
      .setOperation(operation)
      .setValue(value);
  }
}
