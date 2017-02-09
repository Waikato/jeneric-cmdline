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
 * IntrospectionHelper.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.jenericcmdline.core;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for introspection.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class IntrospectionHelper {

  /**
   * Introspects the specified class.
   *
   * @param cls			the class to introspect
   * @return			the information gathered
   * @throws Exception		if introspection fails
   */
  public static IntrospectionContainer introspect(Class cls) throws Exception{
    IntrospectionContainer	result;
    BeanInfo 			bi;
    PropertyDescriptor[] 	properties;
    List<PropertyDescriptor> 	propdesc;
    Class 			cl;

    bi         = Introspector.getBeanInfo(cls);
    properties = bi.getPropertyDescriptors();
    propdesc   = new ArrayList<>();
    for (PropertyDescriptor desc: properties) {
      if ((desc == null) || (desc.getReadMethod() == null) || (desc.getWriteMethod() == null))
        continue;
      cl = desc.getReadMethod().getReturnType();
      // TODO blacklist?
      propdesc.add(desc);
    }
    properties = propdesc.toArray(new PropertyDescriptor[propdesc.size()]);

    // assemble result
    result            = new IntrospectionContainer();
    result.properties = properties;
    result.methods    = bi.getMethodDescriptors();

    return result;
  }
}
