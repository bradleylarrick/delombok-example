/*
 * Copyright (c) 2018-2021 Bradley Larrick. All rights reserved.
 * Licensed under the Apache License v2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package org.larrick.datagen.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import lombok.Data;

/**
 * A data class for storing address information. Each address consists of street address, city,
 * state and a ZIP code values.
 */
@Data
public final class Address implements Serializable, Cloneable {

  private static final long serialVersionUID = -6008352891965343241L;

  /**
   * Default constructor.
   */
  public Address() {}

  /**
   * Constructs an Address object initialized to the given values.
   *
   * @param street  the street address
   * @param city    the city
   * @param state   the state
   * @param zipcode the zipcode
   */
  public Address(String street, String city, String state, String zipcode) {

    this.street = street;
    this.city = city;
    this.state = state;
    this.zipcode = zipcode;
  }

  /**
   * The address's street address value.
   *
   * @param  street the new street address
   * @return        the current street address
   */
  String  street  = "";

  /**
   * The address's city value.
   *
   * @param  city the new city
   * @return      the current city
   */
  String  city    = "";

  /**
   * The address's state value.
   *
   * @param  state the new state
   * @return       the current state
   */
  String  state   = "";

  /**
   * The address's ZIP code.
   *
   * @param  zipcode the new ZIP code
   * @return         the current ZIP code
   */
  String  zipcode = "";

  /**
   * An indicator of whether the address represents a P.O. Box address.
   *
   * @param  poBox new P.O. Box address indicator
   * @return       {@code true} if the address is a P.O. Box
   */
  @JsonIgnore
  boolean poBox   = false;

  /**
   * Generates a new copy of this address.
   */
  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

}
