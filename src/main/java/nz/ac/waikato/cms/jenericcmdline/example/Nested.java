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
 * Nested.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.jenericcmdline.example;

/**
 * Example class with a nested, non-primitive object.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class Nested {

  protected Simple m_Simple;

  protected Simple[] m_SimpleArray = new Simple[0];

  protected int m_Integral;

  protected double m_Floating;

  public Simple getSimple() {
    return m_Simple;
  }

  public void setSimple(Simple simple) {
    m_Simple = simple;
  }

  public int getIntegral() {
    return m_Integral;
  }

  public void setIntegral(int integral) {
    m_Integral = integral;
  }

  public double getFloating() {
    return m_Floating;
  }

  public void setFloating(double floating) {
    m_Floating = floating;
  }

  public Simple[] getSimpleArray() {
    return m_SimpleArray;
  }

  public void setSimpleArray(Simple[] simpleArray) {
    m_SimpleArray = simpleArray;
  }
}
