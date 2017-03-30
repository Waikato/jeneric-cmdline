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
 * JCClassLister.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.jenericcmdline.core;

import nz.ac.waikato.cms.locator.ClassLister;

/**
 * Class lister for the jeneric-cmdline framework.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class JCClassLister
  extends ClassLister {

  /** the singleton. */
  protected static JCClassLister m_Singleton;

  /**
   * Initializes the class lister.
   */
  protected JCClassLister() {
    super();

    m_Packages  = load("nz/ac/waikato/cms/jenericcmdline/core/ClassLister.props");
    m_Blacklist = load("nz/ac/waikato/cms/jenericcmdline/core/ClassLister.blacklist");

    initialize();
  }

  /**
   * Returns the singleton instance of the class lister.
   *
   * @return		the singleton
   */
  public static synchronized ClassLister getSingleton() {
    if (m_Singleton == null)
      m_Singleton = new JCClassLister();

    return m_Singleton;
  }

  /**
   * Just outputs all the managed superclasses and the associated classes.
   *
   * @param args	ignored
   */
  public static void main(String[] args) {
    System.out.println(JCClassLister.getSingleton());
  }
}
