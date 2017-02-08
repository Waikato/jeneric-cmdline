/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Commandline.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.jenericcmdline;

/**
 * Interface for classes that handle commandlines.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public interface Commandline {

  /**
   * Turns the object into a commandline.
   *
   * @param obj		the object to convert
   * @return		the commandline, including classname
   */
  public String toCommandline(Object obj);

  /**
   * Turns the commandline back into an object.
   *
   * @param s		the commandline
   * @return		the recreated object
   * @throws Exception	if failed to create object
   */
  public Object fromCommandline(String s) throws Exception;

  /**
   * Returns the options from the object.
   *
   * @param obj		the object to get the options from
   * @return		the options
   */
  public String[] getOptions(Object obj);

  /**
   * Sets the options for the object.
   *
   * @param obj		the object to update the options for
   * @param options	the options to set
   * @throws Exception	if failed to set options
   */
  public void setOptions(Object obj, String[] options) throws Exception;
}
