/*
 * Copyright (c) 2018-2021 Bradley Larrick. All rights reserved.
 * Licensed under the Apache License v2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package org.larrick.datagen.data;

import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;
import lombok.Data;

/**
 * A data class for storing phone number information. Phone numbers are structured in three parts: a
 * three-digit area code, a three-digit prefix (or central office code), and a four-digit line
 * number (or station code).
 */
@Data
public final class PhoneNumber implements Serializable, Cloneable {

  private static final long serialVersionUID = 8277887720189909314L;

  /**
   * Constructs an empty phone number object.
   */
  public PhoneNumber() {}

  /**
   * Constructs a phone number object initialized to the given values.
   *
   * @param areaCode the number's area code
   * @param prefix   the number's prefix
   * @param number   the number's line number
   */
  public PhoneNumber(String areaCode, String prefix, String number) {

    this.areaCode = areaCode;
    this.prefix = prefix;
    this.number = number;
  }

  /**
   * The phone number's Area Code.
   *
   * @param  areaCode the new area code
   * @return          the current area code
   */
  String areaCode;

  /**
   * The phone number's prefix value.
   *
   * @param  prefix the new prefix
   * @return        the current prefix
   */
  String prefix;

  /**
   * The phone number's line number value.
   *
   * @param  number the new line number
   * @return        the current line number
   */
  String number;

  /**
   * Generates a new copy of this phone number.
   */
  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  /**
   * Obtains an instance of {@code PhoneNumber} from a text string. The string must be in the format
   * {@code (###) ###-####} or {@code ###-####}.
   *
   * @param  text                     the test to parse
   *
   * @return                          the parsed phone number, not null
   *
   * @throws IllegalArgumentException if given string cannot be parsed
   */
  public static PhoneNumber parse(CharSequence text) {

    // If the string is null or empty, return an empty instance.
    if (isNull(text.toString())) {
      return new PhoneNumber();
    }

    PhoneNumber ret = null;
    try {
      String phone = text.toString();
      if (phone.length() > 8) {
        String[] vals = phone.split("\\(|\\) |-");
        ret = new PhoneNumber(vals[1], vals[2], vals[3]);
      } else {
        String[] vals = phone.split("-");
        ret = new PhoneNumber(null, vals[0], vals[1]);
      }

    } catch (ArrayIndexOutOfBoundsException iob) {
      throw new IllegalArgumentException("Invalid phone number format: " + text.toString());
    }

    return ret;
  }

  /**
   * Returns a string representation of this phone number. The string returned is in the form
   * {@code (###) ###-####}, or if no area code defined, {@code ###-####}.
   *
   * @return string representation of the phone number
   */
  @Override
  @JsonValue
  public String toString() {

    // If prefix is empty assume whole object is empty
    if (isNull(prefix)) {
      return "";
    }

    return (isNull(areaCode) ? "" : "(" + areaCode + ") ") + prefix + "-" + number;
  }

  /**
   * Returns {@code true} if the given string is null or empty.
   *
   * @param  text the string to test
   *
   * @return      true if null or empty
   */
  private static boolean isNull(String text) {

    return (text == null || text.length() == 0) ? true : false;
  }
}
