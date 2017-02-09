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
 * AbstractProcessor.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.jenericcmdline;

import nz.ac.waikato.cms.jenericcmdline.core.JCClassLister;
import nz.ac.waikato.cms.jenericcmdline.handlers.AbstractHandler;
import nz.ac.waikato.cms.jenericcmdline.handlers.Handler;
import nz.ac.waikato.cms.jenericcmdline.traversal.All;
import nz.ac.waikato.cms.jenericcmdline.traversal.Traverser;
import nz.ac.waikato.cms.locator.LoggingHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ancestor for processors.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractProcessor
  implements Serializable, Processor {

  /** for logging. */
  protected Logger m_Logger;

  /** the cache for the handlers. */
  protected Map<Class,Handler> m_Cache;

  /** the available handlers. */
  protected Handler[] m_Handlers;

  /** the traverser to use. */
  protected Traverser m_Traverser;

  /**
   * Default constructor.
   */
  protected AbstractProcessor() {
    super();
    initialize();
  }

  /**
   * Initializes the members.
   */
  protected void initialize() {
    Class[]		classes;
    List<Handler>	handlers;

    m_Cache  = new HashMap<>();
    classes  = AbstractHandler.getHandlers();
    handlers = new ArrayList<>();
    for (Class cls: classes) {
      try {
	handlers.add((Handler) cls.newInstance());
      }
      catch (Exception e) {
	getLogger().log(Level.SEVERE, "Failed to instantiate handler: " + cls.getName(), e);
      }
    }
    m_Handlers  = handlers.toArray(new Handler[handlers.size()]);
    m_Traverser = new All();
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
   * Sets the traverser to use.
   *
   * @param value	the traverser
   */
  public void setTraverser(Traverser value) {
    m_Traverser = value;
  }

  /**
   * Returtns the traverser in use.
   *
   * @return		the traverser
   */
  public Traverser getTraverser() {
    return m_Traverser;
  }

  /**
   * Returns the handler for the object.
   *
   * @param obj		the object to get the handler for
   * @return		the handler, null if none found
   */
  public Handler getHandler(Object obj) {
    return getHandler(obj.getClass());
  }

  /**
   * Returns the handler for the class.
   *
   * @param cls		the class to get the handler for
   * @return		the handler, null if none found
   */
  public Handler getHandler(Class cls) {
    Handler	result;

    // cached?
    if (m_Cache.containsKey(cls)) {
      getLogger().info("Cached: " + cls.getName() + " -> " + m_Cache.get(cls).getClass().getName());
      return m_Cache.get(cls);
    }

    result = null;

    for (Handler handler: m_Handlers) {
      if (handler.handles(cls)) {
	result = handler;
	getLogger().info("Found handler: " + cls.getName() + " -> " + handler.getClass().getName());
	m_Cache.put(cls, handler);
	break;
      }
    }

    return result;
  }

  /**
   * Returns all the available processors.
   *
   * @return 		the processor classes
   */
  public static Class[] getProcessors() {
    return JCClassLister.getSingleton().getClasses(Processor.class);
  }
}
