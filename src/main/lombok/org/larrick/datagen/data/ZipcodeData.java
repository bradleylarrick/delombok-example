/*
 * Copyright (c) 2018-2021 Bradley Larrick. All rights reserved.
 * Licensed under the Apache License v2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package org.larrick.datagen.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * Data class for reading data from the {@code ZipcodeData.csv} file. The raw data source file can
 * be found at <a href="https://www.unitedstateszipcodes.org/zip-code-database/">
 * www.unitedstateszipcodes.org/zip-code-database</a>. It has been scrubbed for use in this module.
 */
@Data
public final class ZipcodeData implements Serializable, Cloneable, Comparable<ZipcodeData> {

  private static final long   serialVersionUID = 4142241599378102192L;

  /**
   * The lat/long delta for identifying nearby ZIP codes.
   */
  private static final double DELTA            = 0.2;

  /**
   * The status for an active ZIP code.
   */
  public static final int     ACTIVE           = 0;

  /**
   * The status for a de-commissioned ZIP code.
   */
  public static final int     DECOMMISSIONED   = 1;
  /**
   * The type for a military ZIP code.
   */
  public static final String  MILITARY         = "MILITARY";
  /**
   * The type for a PO boxes ZIP code.
   */
  public static final String  PO_BOX           = "PO BOX";
  /**
   * The type for a standard ZIP code.
   */
  public static final String  STANDARD         = "STANDARD";
  /**
   * The type for a unique ZIP code - usually for a specific building.
   */
  public static final String  UNIQUE           = "UNIQUE";

  /**
   * Default constructor. Constructs an empty {@code ZipcodeData} object.
   */
  public ZipcodeData() {}

  /**
   * The ZIP code value for the record.
   *
   * @param  zipcode the new ZIP code value
   * @return         the current ZIP code value
   */
  String            zipcode;

  /**
   * The record type for the ZIP code record. (should always be {@link #STANDARD}). Valid values
   * include {@link #MILITARY}, {@link #PO_BOX}, {@link #STANDARD} or {@link #UNIQUE}.
   *
   * @param  type the new record type
   * @return      the current record type
   */
  String            type;

  /**
   * Indicator for de-commissioned ZIP code records ("1" equals {@code true}).
   *
   * @param  decommissioned the new de-commissioned indicator
   * @return                the current de-commissioned indicator
   */
  int               decommissioned;

  /**
   * The city covered by the ZIP code.
   *
   * @param  city the new city value
   * @return      the current city value
   */
  String            city;

  /**
   * Other city names covered by the ZIP code that are acceptable substitutes for {@link #getCity}.
   *
   * @param  acceptableCities a list of acceptable city names
   * @return                  the current list of acceptable city names
   */
  List<String>      acceptableCities;

  /**
   * Other city names covered by the ZIP code that are <strong>not</strong> acceptable substitutes
   * for {@link #getCity}.
   *
   * @param  unacceptableCities a list of unacceptable city names
   * @return                    the current list of unacceptable city names
   */
  List<String>      unacceptableCities;

  /**
   * The state covered by the ZIP code.
   *
   * @param  state the new state covered by the ZIP code
   * @return       the current state covered by the ZIP code
   */
  String            state;

  /**
   * The county covered by the ZIP code.
   *
   * @param  county the new county covered by the ZIP code
   * @return        the current county covered by the ZIP code
   */
  String            county;

  /**
   * The timezone of the area covered by the ZIP code.
   *
   * @param  timezone the new timezone for the ZIP code
   * @return          the current timezone for the ZIP code
   */
  String            timezone;

  /**
   * List of valid area codes within the area covered by the ZIP code.
   *
   * @param  areacodes a new list of area codes for the ZIP code
   * @return           the current list of area codes for the ZIP code
   */
  List<String>      areacodes;

  /**
   * The world region of the area covered by the ZIP code.
   *
   * @param  region a new region code for the ZIP code
   * @return        the current region code for the ZIP code
   */
  String            region;

  /**
   * The country of the area covered by the ZIP code.
   *
   * @param  country a new country code for the ZIP code
   * @return         the current country code for the ZIP code
   */
  String            country;

  /**
   * The latitude of the area covered by the ZIP code.
   *
   * @param  latitude a new latitude for the ZIP code
   * @return          the current latitude for the ZIP code
   */
  double            latitude;

  /**
   * The longitude of the area covered by the ZIP code.
   *
   * @param  longitude a new longitude for the ZIP code
   * @return           the current longitude for the ZIP code
   */
  double            longitude;

  /**
   * The 2015 estimated population for the ZIP code.
   *
   * @param  population a new population value for the ZIP code
   * @return            the current population value for the ZIP code
   */
  int               population;

  /**
   * A list of ZIP codes near to this one.
   *
   * @param  nearbyZips a new list of nearby ZIP codes
   * @return            the current list of nearby ZIP codes
   */
  @JsonIgnore
  List<ZipcodeData> nearbyZips = null;

  /**
   * Indicator to specify a PO Box ZIP code.
   *
   * @param  poBox a new PO Box indicator
   * @return       {@code true} if the ZIP code is for PO Boxes
   */
  @JsonIgnore
  boolean           poBox      = false;

  /**
   * Returns <b>true</b> if this ZIP code is nearby to the given ZIP code. The given ZIP code is
   * considered "nearby" when the latitude and longitude values are both within 0.2 degrees of this
   * ZIP code.
   *
   * @param  orig the original ZIP code
   *
   * @return      <b>true</b> if nearby
   */
  public boolean isNear(ZipcodeData orig) {

    if (inRange(orig.latitude, this.latitude) && inRange(orig.longitude, this.longitude)) {
      return true;
    }

    return false;
  }

  /**
   * Tests the given coordinates against a pre-defined delta.
   *
   * @param  orig the original coordinate
   * @param  dest the new coordinate
   *
   * @return      <b>true</b> if new is within the defined delta of the original
   */
  private boolean inRange(double orig, double dest) {

    if (dest > (orig - DELTA) && dest < (orig + DELTA)) {
      return true;
    }

    return false;
  }

  /**
   * Generates a new copy of this ZIP code data.
   */
  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  /**
   * Compares this ZIP code object with the specified object.
   *
   * @param  obj the object to compare
   *
   * @return     a negative integer, zero, or a positive integer as this object is less than, equal
   *             to, or greater than the specified object
   */
  @Override
  public int compareTo(ZipcodeData obj) {

    return this.zipcode.compareTo(obj.zipcode);
  }
}
