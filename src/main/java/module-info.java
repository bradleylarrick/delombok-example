/*
 * Copyright (c) 2021 Bradley Larrick. All rights reserved.
 * Licensed under the Apache License v2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * This is a data generator that creates random data sets of demographic data. The generated data
 * includes name, gender, ethnicity, birthdate and age, social security number, home and work
 * addresses and home, work and mobile phone numbers.
 * <p>
 * See the {@linkplain org.larrick.datagen.Launcher} class for details on invoking the program.
 */
module org.larrick.datagen {
  exports org.larrick.datagen.data;
  exports org.larrick.datagen.data.xml;

  requires com.fasterxml.jackson.annotation;
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.dataformat.csv;
  requires com.fasterxml.jackson.dataformat.xml;
  requires com.fasterxml.jackson.datatype.jsr310;
  requires java.xml;
  requires java.xml.bind;

  requires static lombok;
}
