/*
 * Copyright (c) 2018-2021 Bradley Larrick. All rights reserved.
 * Licensed under the Apache License v2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package org.larrick.datagen.data;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;

/**
 * Singleton class that provides access to extended zip code data.
 */
public class ZipcodeDataServer {

  private static final String      DATAFILE = "ZipcodeData.csv";

  private static ZipcodeDataServer INSTANCE = null;

  Map<String, ZipcodeData>         zipdata  = new HashMap<String, ZipcodeData>();
  private final Random             gen      = new Random();

  /**
   * Private constructor to prevent instantiation.
   */
  private ZipcodeDataServer() {}

  /**
   * Returns an instance of the zip code data reader.
   *
   * @return an instance of the reader
   */
  public static ZipcodeDataServer instance() {

    if (INSTANCE == null) {
      synchronized (ZipcodeDataServer.class) {
        if (INSTANCE == null) {
          INSTANCE = new ZipcodeDataServer();
          INSTANCE.loadData(true);
        }
      }
    }

    return INSTANCE;
  }

  /**
   * Returns an instance of the zip code data reader with all records intact. This includes Unique
   * and Military records, de-commissioned records and those records with zero population reported.
   * Used by {@code FixZipcodeData}.
   *
   * @return an instance of the reader
   */
  static ZipcodeDataServer instanceAllData() {

    var server = new ZipcodeDataServer();
    server.loadData(false);
    return server;
  }

  /**
   * Returns a {@link Stream} of the Zip code data.
   *
   * @return a {@link Stream} of the Zip code data
   */
  public Stream<ZipcodeData> stream() {

    return zipdata.entrySet().stream().map(s -> s.getValue());
  }

  /**
   * Returns a {@link Stream} of the Zip codes and weight value. Used by the weighted generator
   * {@link org.larrick.datagen.generators.ZipcodeGenerator}.
   *
   * @return a {@link Stream} of weighted zip code data
   */
  public Stream<String> weightedStream() {

    return this.stream().map(s -> s.zipcode + "," + s.population);
  }

  /**
   * Returns the zip code data record for the given zip code.
   *
   * @param  zipcode the zip code to return
   *
   * @return         the data for the given zip code; null if not found
   */
  public ZipcodeData get(String zipcode) {

    return zipdata.get(zipcode);
  }

  /**
   * Returns a city value for the given zip code. If there is only one city associated with the zip
   * code, that value is returned. If there are additional acceptable cities defined, a random
   * selection among the city and acceptable cities values is returned.
   *
   * @param  zipcode the zip code for which the city is requested
   *
   * @return         a city value; null of the zipcode if invalid or no city is defined
   */
  public String getCity(String zipcode) {

    var record = get(zipcode);
    return getCity(record);
  }

  /**
   * Returns a city value from the given zip code data record. If there is only one city associated
   * with the zip code, that value is returned. If there are additional acceptable cities defined, a
   * random selection among the city and acceptable cities values is returned.
   *
   * @param  record the zip code data record
   *
   * @return        a city value; null of the zip code if invalid or no city is defined
   */
  private String getCity(ZipcodeData record) {

    if (record == null) {
      return null;
    }

    // If no additional cities, return the base value
    if (record.acceptableCities.isEmpty()) {
      return record.city;
    }

    var cities = new ArrayList<String>(record.acceptableCities);
    cities.add(record.city);
    return cities.get(gen.nextInt(cities.size()));
  }

  /**
   * Returns the state value for the given zip code.
   *
   * @param  zipcode the zip code for which the state is requested
   *
   * @return         a state value; null if no record found
   */
  public String getState(String zipcode) {

    var record = get(zipcode);
    return getState(record);
  }

  /**
   * Returns the state value from the given zip code data record.
   *
   * @param  record the zip code data record
   *
   * @return        the state value; null if no record
   */
  String getState(ZipcodeData record) {

    return (record == null) ? null : record.state;
  }

  /**
   * Returns an {@link Address} object initialized with the city, state and zip code values for the
   * given zip code.
   *
   * @param  zipcode the zip code for which data is requested
   *
   * @return         a populated Address object
   *
   * @see            #getCity(String)
   * @see            #getState(String)
   */
  public Address loadCityState(String zipcode) {

    var record = get(zipcode);
    return new Address().setCity(getCity(record)).setState(getState(record))
        .setZipcode(record == null ? null : record.zipcode)
        .setPoBox(record == null ? false : record.type.equals(ZipcodeData.PO_BOX));
  }

  /**
   * Returns a valid area code for the given zip code.
   *
   * @param  zipcode the zip code for which the area code is requested
   *
   * @return         a valid area code; null of none defined
   */
  public String getAreaCode(String zipcode) {

    return getAreaCode(get(zipcode));
  }

  /**
   * Returns one of the area codes defined in the given zip code data record.
   *
   * @param  record the zip code data record
   *
   * @return        one of the defined area codes; null if none defined
   */
  public String getAreaCode(ZipcodeData record) {

    if (record == null) {
      return null;
    }

    // use the area codes specific to the zip code if they're available
    var acodes = record.areacodes;
    if (isEmpty(acodes)) {
      return null;
    }

    return acodes.get(gen.nextInt(acodes.size()));
  }

  /**
   * Reads zip code data from the csv source file.
   *
   * @param  clean load only clean data if true;
   *
   * @return       a Map of the zip code data
   */
  private void loadData(boolean clean) {

    try (var file = getClass().getClassLoader().getResourceAsStream(DATAFILE)) {
      if (file == null) {
        throw new IllegalArgumentException("Cannot access file \"" + DATAFILE + "\"");
      }

      var in = new BufferedReader(new InputStreamReader(file));

      // skip comments at the beginning of the file
      while (true) {
        var line = in.readLine();
        if (line.charAt(0) != '#') {
          break;
        }
      }
      // once here, we've read the header line, which we want to skip anyway

      var mapper = new CsvMapper();
      mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false);
      mapper.configure(CsvParser.Feature.TRIM_SPACES, true);
      var schema = mapper.schemaFor(ZipcodeData.class).withArrayElementSeparator(",");
      MappingIterator<ZipcodeData> iterator =
          mapper.readerFor(ZipcodeData.class).with(schema).readValues(in);

      // go through records and keep those that fit the criteria shown below (in isValid()).
      iterator.forEachRemaining(rec -> {
        var valid = clean ? isValid(rec) : true;
        if (valid) {
          zipdata.put(rec.getZipcode(), rec);
        }
      });
    } catch (Exception exc) {
      System.err.println("ZipcodeDataReader.loadData(): " + exc.toString());
      exc.printStackTrace();
    }
  }

  /**
   * Tests the given records for the criteria specified for inclusion in the data set.
   *
   * @param  rec the Zip code record to test
   *
   * @return     <b>true</b> if the record should be included
   */
  boolean isValid(ZipcodeData rec) {

    // ignore de-commissioned records
    if (decommissioned(rec)) {
      return false;
    }

    // ignore records with no census data
    if (rec.population == 0) {
      return false;
    }

    // only return STANDARD and PO BOX records
    if (!rec.type.equals(ZipcodeData.STANDARD) && !rec.type.equals(ZipcodeData.PO_BOX)) {
      return false;
    }

    /*
     * Don't need this code because there's no census data for the territories
     */
    // if (!usState(rec) {
    // return false;

    // set the PO Box flag for this record
    rec.poBox = rec.getType().equals(ZipcodeData.PO_BOX);
    return true;
  }

  /**
   * Returns <b>true</b> if the zip code record is de-commissioned.
   *
   * @param  rec the Zip code record to test
   *
   * @return     <b>true</b> if the record is de-commissioned
   */
  boolean decommissioned(ZipcodeData rec) {

    return rec.decommissioned == ZipcodeData.DECOMMISSIONED;
  }

  /**
   * Returns <b>true</b> if the zip code record is for a U.S. state.
   *
   * @param  rec the Zip code record to test
   *
   * @return     <b>true</b> if the record is for a state
   */
  boolean usState(ZipcodeData rec) {

    switch (rec.state) {
      case "AA": // Armed Forces Americas
      case "AE": // Armed Forces Europe
      case "AP": // Armed Forces Pacific
      case "AS": // American Samoa
      case "FM": // Micronesia
      case "GU": // Guam
      case "MH": // Marshall Islands
      case "MP": // Mariana Islands
      case "PR": // Puerto Rico
      case "PW": // Palau
      case "VI": // Virgin Islands
        return false;
      default:
        break;
    }

    return true;
  }

  /**
   * Dumps the list of {@link org.larrick.datagen.data.ZipcodeData} records to the given
   * {@link OutputStream}.
   *
   * @param out the output stream to dump data
   */
  void dumpList(OutputStream out) {

    var mapper = new CsvMapper();
    mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false);

    var schema = mapper.schemaFor(ZipcodeData.class).withArrayElementSeparator(",").withHeader();
    var writer = mapper.writer(schema);

    try {
      var data = this.stream().sorted().collect(Collectors.toList());
      writer.writeValue(out, data);
    } catch (IOException exc) {
      System.err.println(exc.getMessage());
      exc.printStackTrace();
    }

    return;
  }

  /**
   * Returns <b>{@code true}</b> if the given {@list List} is null or empty.
   *
   * @param  list the List to test
   *
   * @return      <b>{@code true}</b> if empty
   */
  boolean isEmpty(List<String> list) {

    return (list == null || list.isEmpty()) ? true : false;
  }

  /**
   * Utility class for handling area code data.
   *
   */
  @Data
  final class StateCity implements Comparable<StateCity>, Cloneable {

    String state;
    String county;
    String city;

    protected StateCity(String state, String county, String city) {
      this.state = state;
      this.county = county;
      this.city = city;
    }

    @Override
    public String toString() {

      return "[" + city + ", " + county + ", " + state + "]";
    }

    /**
     * Generates a new copy of this State-City record.
     */
    @Override
    public StateCity clone() throws CloneNotSupportedException {
      return (StateCity) super.clone();
    }

    @Override
    public int compareTo(StateCity that) {

      if (this.state == null) {
        return -1;
      } else if (this.state.equals(that.state)) {
        if (this.county == null) {
          return -1;
        } else if (this.county.equals(that.county)) {
          if (this.city == null) {
            return -1;
          } else if (that.city == null) {
            return 1;
          } else {
            return this.city.compareTo(that.city);
          }
        } else {
          return this.county.compareTo(that.county);
        }
      }

      return this.state.compareTo(that.state);
    }
  }
}
