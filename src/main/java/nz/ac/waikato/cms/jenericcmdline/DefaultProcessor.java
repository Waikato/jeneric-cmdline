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
 * DefaultProcessor.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.jenericcmdline;

import nz.ac.waikato.cms.jenericcmdline.core.IntrospectionContainer;
import nz.ac.waikato.cms.jenericcmdline.core.IntrospectionHelper;
import nz.ac.waikato.cms.jenericcmdline.core.OptionUtils;
import nz.ac.waikato.cms.jenericcmdline.example.Nested;
import nz.ac.waikato.cms.jenericcmdline.example.Simple;
import nz.ac.waikato.cms.jenericcmdline.example.Simple.OneTwoThree;
import nz.ac.waikato.cms.jenericcmdline.example.SimpleDeprecated;
import nz.ac.waikato.cms.jenericcmdline.handlers.Handler;
import nz.ac.waikato.cms.jenericcmdline.traversal.All;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Default command-line processor.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class DefaultProcessor
  extends AbstractProcessor {

  /** the array prefix. */
  public final static String ARRAY_PREFIX = "array-";

  /** whether to skip deprecated methods. */
  protected boolean m_SkipDeprecated;

  /**
   * Sets whether to skip deprecated methods.
   *
   * @param value	true if to skip
   */
  public void setSkipDeprecated(boolean value) {
    m_SkipDeprecated = value;
  }

  /**
   * Returns whether deprecated methods are skipped.
   *
   * @return		true if skipped
   */
  public boolean getSkipDeprecated() {
    return m_SkipDeprecated;
  }

  /**
   * Turns the object into a commandline.
   *
   * @param obj		the object to convert
   * @return		the commandline, including classname
   * @throws Exception	if failed to convert
   */
  @Override
  public String toCommandline(Object obj) throws Exception {
    String	result;
    String[]	options;

    result  = obj.getClass().getName();
    options = getOptions(obj);
    if (options.length > 0)
      result += " " + OptionUtils.joinOptions(options);

    return result;
  }

  /**
   * Turns the commandline back into an object.
   *
   * @param s		the commandline
   * @return		the recreated object
   * @throws Exception	if failed to create object
   */
  @Override
  public Object fromCommandline(String s) throws Exception {
    Object	result;
    String[]	parts;
    String[]	options;

    if (s.trim().length() == 0)
      throw new IllegalArgumentException("No commandline provided!");
    parts = OptionUtils.splitOptions(s);
    if (parts.length == 0)
      throw new IllegalArgumentException("Could not split commandline: '" + s + "'");
    result  = Class.forName(parts[0]).newInstance();
    options = new String[parts.length - 1];
    System.arraycopy(parts, 1, options, 0, options.length);
    setOptions(result, options);

    return result;
  }

  /**
   * Returns the options from the object.
   *
   * @param obj		the object to get the options from
   * @return		the options
   * @throws Exception	if failed to get options
   */
  @Override
  public String[] getOptions(Object obj) throws Exception {
    List<String>		result;
    IntrospectionContainer	cont;
    boolean			array;
    String			name;
    String			flag;
    Class			cls;
    Class			base;
    Handler			handler;
    int				i;
    Object			value;
    Method			method;

    result = new ArrayList<>();
    cont   = IntrospectionHelper.introspect(obj.getClass());

    for (PropertyDescriptor desc: cont.properties) {
      // check for deprecated methods?
      if (getSkipDeprecated()) {
        method = desc.getReadMethod();
        if (method.getAnnotation(Deprecated.class) != null)
          continue;
        method = desc.getWriteMethod();
        if (method.getAnnotation(Deprecated.class) != null)
          continue;
      }

      cls   = desc.getReadMethod().getReturnType();
      name  = desc.getDisplayName();
      flag  = OptionUtils.displayNameToFlag(name);
      array = cls.isArray();
      if (array)
	base = cls.getComponentType();
      else
	base = cls;

      handler = getHandler(base);
      if (handler == null) {
	getLogger().fine("No handler found for: " + name + "/" + base.getName() + (array ? "[]" : ""));
	if (!m_Traverser.canTraverse(cls, base)) {
	  getLogger().fine("Traversal not possible: " + name + "/" + base.getName() + (array ? "[]" : ""));
	  continue;
	}
	value = desc.getReadMethod().invoke(obj);
	if (value != null) {
	  if (array) {
	    for (i = 0; i < Array.getLength(value); i++) {
	      result.add("-" + ARRAY_PREFIX + flag);
	      result.add(toCommandline(Array.get(value, i)));
	    }
	  }
	  else {
	    result.add("-" + flag);
	    result.add(toCommandline(value));
	  }
	}
      }
      else {
	value = desc.getReadMethod().invoke(obj);
	if (value != null) {
	  if (array) {
	    for (i = 0; i < Array.getLength(value); i++) {
	      result.add("-" + ARRAY_PREFIX + flag);
	      result.add(handler.toString(Array.get(value, i)));
	    }
	  }
	  else {
	    result.add("-" + flag);
	    result.add(handler.toString(value));
	  }
	}
      }
    }

    return result.toArray(new String[result.size()]);
  }

  /**
   * Sets the options for the object.
   *
   * @param obj		the object to update the options for
   * @param options	the options to set
   * @throws Exception	if failed to set options
   */
  @Override
  public void setOptions(Object obj, String[] options) throws Exception {
    IntrospectionContainer	cont;
    boolean			array;
    String			name;
    String			flag;
    Class			cls;
    Class			base;
    Handler			handler;
    List<String>		values;
    int				i;
    String			search;
    Object			value;

    cont = IntrospectionHelper.introspect(obj.getClass());
    for (PropertyDescriptor desc: cont.properties) {
      cls   = desc.getReadMethod().getReturnType();
      name  = desc.getDisplayName();
      flag  = OptionUtils.displayNameToFlag(name);
      array = cls.isArray();
      if (array)
	base = cls.getComponentType();
      else
	base = cls;

      // collect values
      values = new ArrayList<>();
      i = -2;
      if (array)
	search = "-" + ARRAY_PREFIX + flag;
      else
	search = "-" + flag;
      while (i < options.length - 2) {
	i += 2;
	if (options[i].equals(search)) {
	  options[i] = "";
	  if (i < options.length - 1) {
	    values.add(options[i + 1]);
	    options[i + 1] = "";
	  }
	}
      }

      handler = getHandler(base);
      if (handler == null) {
	getLogger().fine("No handler found for: " + name + "/" + base.getName() + (array ? "[]" : ""));
	if (!m_Traverser.canTraverse(cls, base)) {
	  getLogger().fine("Traversal not possible: " + name + "/" + base.getName() + (array ? "[]" : ""));
	  continue;
	}
	// convert values
	if (array) {
	  value = Array.newInstance(base, values.size());
	  for (i = 0; i < values.size(); i++)
	    Array.set(value, i, fromCommandline(values.get(i)));
	  desc.getWriteMethod().invoke(obj, value);
	}
	else if (values.size() > 0) {
	  value = fromCommandline(values.get(0));
	  desc.getWriteMethod().invoke(obj, value);
	}
      }
      else {
	// convert values
	if (array) {
	  value = Array.newInstance(base, values.size());
	  for (i = 0; i < values.size(); i++)
	    Array.set(value, i, handler.fromString(base, values.get(i)));
	  desc.getWriteMethod().invoke(obj, value);
	}
	else if (values.size() > 0) {
	  value = handler.fromString(base, values.get(0));
	  desc.getWriteMethod().invoke(obj, value);
	}
      }
    }
  }

  /**
   * Only for testing.
   *
   * @param args	ignored
   * @throws Exception	if tests fail
   */
  public static void main(String[] args) throws Exception {
    Simple simple = new Simple();
    simple.setTruth(true);
    simple.setEightBit((byte) 7);
    simple.setSixteenBit((short) 42);
    simple.setThirtyTwoBit(314);
    simple.setSixtyFourBit(31415);
    simple.setFloatie(0.123f);
    simple.setQuadrupleHalf(123.456);
    simple.setMoreThanOneTruth(new boolean[]{true, false, true});
    simple.setManyInts(new int[]{1, 2, 3});
    simple.setSomeDoubles(new double[]{0.1, 0.2, 0.3});
    simple.setOneTwoThree(OneTwoThree.THREE);
    simple.setMany(new OneTwoThree[]{OneTwoThree.ONE, OneTwoThree.TWO});

    Simple simple2 = new Simple();
    simple2.setTruth(false);
    simple2.setEightBit((byte) 1);
    simple2.setFloatie(1.234f);

    Nested nested = new Nested();
    nested.setSimple(simple);
    nested.setSimpleArray(new Simple[]{new Simple(), simple2});
    nested.setFloating(0.456);
    nested.setIntegral(1234);

    SimpleDeprecated sdep = new SimpleDeprecated();
    sdep.setEightBit((byte) 6);

    DefaultProcessor processor = new DefaultProcessor();
    processor.setTraverser(new All());
    String cmdline;

    System.out.println("\nSimple");
    cmdline = processor.toCommandline(simple);
    System.out.println("-initial commandline:\n" + cmdline);
    Simple simpleNew = (Simple) processor.fromCommandline(cmdline);
    cmdline = processor.toCommandline(simpleNew);
    System.out.println("-to and from commandline:\n" + cmdline);

    System.out.println("\nNested");
    cmdline = processor.toCommandline(nested);
    System.out.println("-initial commandline:\n" + cmdline);
    Nested nestedNew = (Nested) processor.fromCommandline(cmdline);
    cmdline = processor.toCommandline(nestedNew);
    System.out.println("-to and from commandline:\n" + cmdline);

    System.out.println("\nSimple/deprecated (no check)");
    cmdline = processor.toCommandline(sdep);
    System.out.println("-initial commandline:\n" + cmdline);

    processor.setSkipDeprecated(true);

    System.out.println("\nSimple/deprecated (with check)");
    cmdline = processor.toCommandline(sdep);
    System.out.println("-initial commandline:\n" + cmdline);
  }
}
