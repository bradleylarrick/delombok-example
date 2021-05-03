/*
 * Copyright (c) 2018-2021 Bradley Larrick. All rights reserved.
 * Licensed under the Apache License v2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package org.larrick.datagen.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The root class for generating a collection of {@linkplain Person} objects in CSV or XML format.
 */
public final class People implements Serializable {

  private static final long serialVersionUID = -1959265873924512699L;

  /**
   * A collection of {@link Person} records.
   */
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "Person")
  @JsonProperty("Person")
  List<Person>              people;

  /**
   * Default constructor. Initializes the list capacity to 16.
   */
  public People() {
    this(16);
  }

  /**
   * Constructs a list for Person objects with the given capacity.
   *
   * @param count the capacity of the list
   */
  public People(int count) {

    people = new ArrayList<Person>(count);
  }

  /**
   * Adds a Person object to the list.
   *
   * @param person the Person to add
   */
  public void add(Person person) {
    people.add(person);
  }

  /**
   * Returns the List of Person objects.
   *
   * @return the List of Person objects
   */
  @JsonIgnore
  public List<Person> getList() {

    return people;
  }
}
