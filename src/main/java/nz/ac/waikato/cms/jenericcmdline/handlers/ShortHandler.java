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
 * ShortHandler.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.jenericcmdline.handlers;

import nz.ac.waikato.cms.locator.ClassLocator;

/**
 * Handler Short objects (primitives as well).
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class ShortHandler
  extends AbstractHandler {

  /**
   * Checks whether the class can be handled.
   *
   * @param cls		the class to check
   * @return		true if can be handled
   */
  @Override
  public boolean handles(Class cls) {
    return ClassLocator.isSubclass(Short.class, cls) || ClassLocator.isSubclass(Short.TYPE, cls);
  }

  /**
   * Turns the object into its string representation.
   *
   * @param obj		the object to convert
   * @return		the string representation
   */
  @Override
  public String toString(Object obj) {
    return obj.toString();
  }

  /**
   * Parses the string and turns it back into an object.
   *
   * @param base	the base class
   * @param s		the string to parse
   * @return		the generated object
   * @throws Exception	if failed to parse
   */
  @Override
  public Object fromString(Class base, String s) throws Exception {
    return Short.parseShort(s);
  }
}
