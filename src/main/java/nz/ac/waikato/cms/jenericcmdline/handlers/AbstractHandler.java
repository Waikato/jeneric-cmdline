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
 * AbstractHandler.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.jenericcmdline.handlers;

import nz.ac.waikato.cms.jenericcmdline.core.JCClassLister;
import nz.ac.waikato.cms.locator.LoggingHelper;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Ancestor for handlers.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractHandler
  implements Serializable, Handler{

  /** for logging. */
  protected Logger m_Logger;

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
   * Checks whether the object can be handled.
   *
   * @param obj		the object to check
   * @return		true if can be handled
   */
  public boolean handles(Object obj) {
    if (obj.getClass().isArray())
      return handles(obj.getClass().getComponentType());
    else
      return handles(obj.getClass());
  }

  /**
   * Returns all the available handlers.
   *
   * @return 		the handler classes
   */
  public static Class[] getHandlers() {
    return JCClassLister.getSingleton().getClasses(Handler.class);
  }
}
