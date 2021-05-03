/*
 * Copyright (c) 2018-2021 Bradley Larrick. All rights reserved.
 * Licensed under the Apache License v2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package org.larrick.datagen.data.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.larrick.datagen.data.Ethnicity;

/**
 * JAXB adapter for processing {@linkplain Ethnicity} values.
 */
public class EthnicityAdapter extends XmlAdapter<String, Ethnicity> {

  /**
   * Unmarshal a String value to an {@code Ethnicity} value.
   *
   * @param  label     the label to unmarshal
   *
   * @return           the {@code Ethnicity} value of the given label
   *
   * @throws Exception if an error occurs
   */
  @Override
  public Ethnicity unmarshal(String label) throws Exception {

    return Ethnicity.ofLabel(label);
  }

  /**
   * Marshal an {@code Ethnicity} value to a String.
   *
   * @param  value     the {@code Ethnicity} to marshal
   *
   * @return           the String representation of the given value
   *
   * @throws Exception if an error occurs
   */
  @Override
  public String marshal(Ethnicity value) throws Exception {
    return value.toString();
  }
}
