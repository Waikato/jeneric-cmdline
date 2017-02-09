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
 * Simple.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.jenericcmdline.example;

/**
 * Example class for testing commandline handling.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class Simple {

  protected boolean m_Truth;

  protected byte m_EightBit;

  protected short m_SixteenBit;

  protected int m_ThirtyTwoBit;

  protected long m_SixtyFourBit;

  protected float m_Floatie;

  protected double m_QuadrupleHalf;

  protected boolean[] m_MoreThanOneTruth = new boolean[0];

  protected int[] m_ManyInts = new int[0];

  protected double[] m_SomeDoubles = new double[0];

  public boolean getTruth() {
    return m_Truth;
  }

  public void setTruth(boolean value) {
    m_Truth = value;
  }

  public byte getEightBit() {
    return m_EightBit;
  }

  public void setEightBit(byte value) {
    m_EightBit = value;
  }

  public short getSixteenBit() {
    return m_SixteenBit;
  }

  public void setSixteenBit(short value) {
    m_SixteenBit = value;
  }

  public int getThirtyTwoBit() {
    return m_ThirtyTwoBit;
  }

  public void setThirtyTwoBit(int value) {
    m_ThirtyTwoBit = value;
  }

  public long getSixtyFourBit() {
    return m_SixtyFourBit;
  }

  public void setSixtyFourBit(long value) {
    m_SixtyFourBit = value;
  }

  public float getFloatie() {
    return m_Floatie;
  }

  public void setFloatie(float value) {
    m_Floatie = value;
  }

  public double getQuadrupleHalf() {
    return m_QuadrupleHalf;
  }

  public void setQuadrupleHalf(double value) {
    m_QuadrupleHalf = value;
  }

  public boolean[] getMoreThanOneTruth() {
    return m_MoreThanOneTruth;
  }

  public void setMoreThanOneTruth(boolean[] moreThanOneTruth) {
    m_MoreThanOneTruth = moreThanOneTruth;
  }

  public int[] getManyInts() {
    return m_ManyInts;
  }

  public void setManyInts(int[] manyInts) {
    m_ManyInts = manyInts;
  }

  public double[] getSomeDoubles() {
    return m_SomeDoubles;
  }

  public void setSomeDoubles(double[] someDoubles) {
    m_SomeDoubles = someDoubles;
  }
}
