/*
 * Copyright (c) 2018-2021 Bradley Larrick. All rights reserved.
 * Licensed under the Apache License v2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package org.larrick.datagen.data;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.io.Serializable;
import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;
import org.larrick.datagen.data.xml.EthnicityAdapter;
import org.larrick.datagen.data.xml.GenderAdapter;
import org.larrick.datagen.data.xml.PhoneAdapter;

/**
 * A data class for storing information for a single individual.
 */
@Data
@JsonRootName("Person")
public final class Person implements Serializable, Cloneable {

  private static final long serialVersionUID = 4795338098703812612L;

  /**
   * Default constructor.
   */
  public Person() {}

  /**
   * The person's ethnicity.
   *
   * @param  ethnicity the new ethnicity
   * @return           the current ethnicity
   */
  @XmlJavaTypeAdapter(EthnicityAdapter.class)
  Ethnicity   ethnicity;

  /**
   * The person's gender.
   *
   * @param  gender the new gender
   * @return        the current gender
   */
  @XmlJavaTypeAdapter(GenderAdapter.class)
  Gender      gender;

  /**
   * The person's surname.
   *
   * @param  surname the new surname
   * @return         the current surname
   */
  String      surname;

  /**
   * The person's given name.
   *
   * @param  givenName the new given name
   * @return           the current given name
   */
  String      givenName;

  /**
   * The person's middle initial.
   *
   * @param  middleInit the new middle initial
   * @return            the current middle initial
   */
  String      middleInit;

  /**
   * The person's birth date.
   *
   * @param  birthdate the new birth date
   * @return           the current birth date
   */
  LocalDate   birthdate;

  /**
   * The person's current age.
   *
   * @param  age the new age
   * @return     the current age
   */
  int         age;

  /**
   * The person's federal ID number.
   *
   * @param  ssn the new social security number
   * @return     the current social security number
   */
  String      ssn;

  /**
   * The person's home address.
   *
   * @param  home the new home address
   * @return      the current home address
   */
  Address     home        = new Address();

  /**
   * The person's home address.
   *
   * @param  work the new work address
   * @return      the current work address
   */
  Address     work        = new Address();

  /**
   * The person's home phone number.
   *
   * @param  homePhone the new home phone number
   * @return           the current home phone number
   */
  @XmlJavaTypeAdapter(PhoneAdapter.class)
  PhoneNumber homePhone   = new PhoneNumber();

  /**
   * The person's work phone number.
   *
   * @param  workPhone the new work phone number
   * @return           the current work phone number
   */
  @XmlJavaTypeAdapter(PhoneAdapter.class)
  PhoneNumber workPhone   = new PhoneNumber();

  /**
   * The person's mobile phone number.
   *
   * @param  mobilePhone the new mobile phone number
   * @return             the current mobile phone number
   */
  @XmlJavaTypeAdapter(PhoneAdapter.class)
  PhoneNumber mobilePhone = new PhoneNumber();

  /**
   * Generates a new copy of this person.
   */
  @Override
  public Object clone() throws CloneNotSupportedException {

    return super.clone();
  }

  /**
   * MixIn interface for unwrapping addresses during CSV output.
   */
  public interface PersonMixIn {

    /**
     * Returns the Home address for the Person.
     *
     * @return the person's home address
     */
    @JsonUnwrapped(prefix = "home.")
    Address getHome();

    /**
     * Returns the Work address for the Person.
     *
     * @return the person's work address
     */
    @JsonUnwrapped(prefix = "work.")
    Address getWork();
  }

}
