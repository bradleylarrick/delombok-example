/*
 * Copyright (c) 2018-2021 Bradley Larrick. All rights reserved.
 * Licensed under the Apache License v2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package org.larrick.datagen.data.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.larrick.datagen.data.PhoneNumber;

/**
 * JAXB adapter for processing {@linkplain PhoneNumber} objects.
 */
public class PhoneAdapter extends XmlAdapter<String, PhoneNumber> {

  /**
   * Unmarshal a String value to a {@code PhoneNumber} object.
   *
   * @param  value     the value to unmarshal
   *
   * @return           the {@code PhoneNumber} representation of the given value
   *
   * @throws Exception if an error occurs
   */
  @Override
  public PhoneNumber unmarshal(String value) throws Exception {

    return PhoneNumber.parse(value);
  }

  /**
   * Marshal a {@code PhoneNumber} to a String.
   *
   * @param  value     the {@code PhoneNumber} to marshal
   *
   * @return           the String representation of the given value
   *
   * @throws Exception if an error occurs
   */
  @Override
  public String marshal(PhoneNumber value) throws Exception {

    return value.toString();
  }

}
