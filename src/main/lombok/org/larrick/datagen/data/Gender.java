/*
 * Copyright (c) 2018-2021 Bradley Larrick. All rights reserved.
 * Licensed under the Apache License v2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package org.larrick.datagen.data;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of valid Gender values.
 */
public enum Gender {

  /**
   * Female gender.
   */
  FEMALE("Female"),
  /**
   * Male gender.
   */
  MALE("Male");

  /*
   * Map for returning the enum for a given String value
   */
  private static final Map<String, Gender> map = new HashMap<String, Gender>(values().length, 1);

  static {
    for (var e : values()) {
      map.put(e.label, e);
    }
  }

  private final String label;

  Gender(String label) {
    this.label = label;
  }

  /**
   * Returns the label for this enum constant.
   *
   * @return the label for this enum constant
   */
  @JsonValue
  @Override
  public String toString() {
    return label;
  }

  /**
   * Returns the enum constant of this type for the given label.
   *
   * @param  label the label of the enum constant to be returned
   *
   * @return       the enum constant with the specified label
   */
  public static Gender ofLabel(String label) {

    var result = map.get(label);
    if (result == null) {
      throw new IllegalArgumentException("Invalid Gender: " + label);
    }

    return result;
  }
}
