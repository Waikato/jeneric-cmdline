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
 * AbstractTraverser.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.jenericcmdline.traversal;

import nz.ac.waikato.cms.jenericcmdline.core.JCClassLister;
import nz.ac.waikato.cms.locator.LoggingHelper;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Ancestor for traversal handlers.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractTraverser
  implements Serializable, Traverser {

  /** for logging. */
  protected Logger m_Logger;

  /**
   * Default constructor
   */
  protected AbstractTraverser() {
    super();
    initialize();
  }

  /**
   * Initializes the widgets.
   */
  protected void initialize() {

  }

  /**
   * Returns the logger in use.
   *
   * @return		the logger
   */
  protected Logger getLogger() {
    if (m_Logger == null) {
      m_Logger = Logger.getLogger(getClass().getName());
      m_Logger.setLevel(LoggingHelper.getLevel(getClass()));
    }
    return m_Logger;
  }

  /**
   * Returns all the available traversers.
   *
   * @return 		the traverser classes
   */
  public static Class[] getTraversers() {
    return JCClassLister.getSingleton().getClasses(Traverser.class);
  }
}
