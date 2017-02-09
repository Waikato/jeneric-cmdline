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
 * SpecificClasses.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.jenericcmdline.traversal;

/**
 * Allows only specific classes to be further traversed.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class SpecificClasses
  extends AbstractTraverser {

  /** the classes allowed to be traversed. */
  protected Class[] m_Allowed;

  /**
   * Initializes the widgets.
   */
  protected void initialize() {
    super.initialize();

    m_Allowed = new Class[0];
  }

  /**
   * Sets the allowed classes.
   *
   * @param value	the classes
   */
  public void setAllowed(Class[] value) {
    m_Allowed = value;
  }

  /**
   * Returns the allowed classes.
   *
   * @return		the classes
   */
  public Class[] getAllowed() {
    return m_Allowed;
  }

  /**
   * Checks whether the property of the object can be traversed deeper.
   *
   * @param obj		the current object under investigation
   * @param property	the property of the object to check
   * @return		true if it can traverse deeper
   */
  @Override
  public boolean canTraverse(Class obj, Class property) {
    boolean	result;

    result = false;

    for (Class cls: m_Allowed) {
      if (cls.equals(property)) {
	result = true;
	break;
      }
    }

    return result;
  }
}
