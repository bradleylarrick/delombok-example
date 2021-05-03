/*
 * Copyright (c) 2018-2021 Bradley Larrick. All rights reserved.
 * Licensed under the Apache License v2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package org.larrick.datagen.data.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.larrick.datagen.data.Gender;

/**
 * JAXB adapter for processing {@linkplain Gender} values.
 */
public class GenderAdapter extends XmlAdapter<String, Gender> {

  /**
   * Unmarshal a String value to a {@code Gender} value.
   *
   * @param  label     the label to unmarshal
   *
   * @return           the {@code Gender} value of the given label
   *
   * @throws Exception if an error occurs
   */
  @Override
  public Gender unmarshal(String label) throws Exception {

    return Gender.ofLabel(label);
  }

  /**
   * Marshal a {@code Gender} value to a String.
   *
   * @param  value     the {@code Gender} to marshal
   *
   * @return           the String representation of the given value
   *
   * @throws Exception if an error occurs
   */
  @Override
  public String marshal(Gender value) throws Exception {
    return value.toString();
  }
}
