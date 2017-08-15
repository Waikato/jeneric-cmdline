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

/*
 * OptionUtils.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package nz.ac.waikato.cms.jenericcmdline.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for option-related operations.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class OptionUtils {

  /**
   * Converts specified characters into the string equivalents.
   *
   * @param string 	the string
   * @param find	the characters to replace
   * @param replace	the replacement strings for the characters
   * @return 		the converted string
   * @see		#unbackQuoteChars(String, String[], char[])
   */
  public static String backQuoteChars(String string, char[] find, String[] replace) {
    int 		index;
    StringBuilder 	newStr;
    int			i;

    if (string == null)
      return null;

    for (i = 0; i < find.length; i++) {
      if (string.indexOf(find[i]) > -1 ) {
	newStr = new StringBuilder();
	while ((index = string.indexOf(find[i])) > -1) {
	  if (index > 0)
	    newStr.append(string.substring(0, index));
	  newStr.append(replace[i]);
	  if ((index + 1) < string.length())
	    string = string.substring(index + 1);
	  else
	    string = "";
	}
	newStr.append(string);
	string = newStr.toString();
      }
    }

    return string;
  }

  /**
   * Converts carriage returns and new lines in a string into \r and \n.
   * Backquotes the following characters: ` " \ \t and %
   *
   * @param string 	the string
   * @return 		the converted string
   * @see		#unbackQuoteChars(String)
   */
  public static String backQuoteChars(String string) {
    return backQuoteChars(
	string,
	new char[]  {'\\',   '\'',  '\t',  '\n',  '\r',  '"'},
	new String[]{"\\\\", "\\'", "\\t", "\\n", "\\r", "\\\""});
  }

  /**
   * The inverse operation of backQuoteChars().
   * Converts the specified strings into their character representations.
   *
   * @param string 	the string
   * @param find	the string to find
   * @param replace	the character equivalents of the strings
   * @return 		the converted string
   * @see		#backQuoteChars(String, char[], String[])
   */
  public static String unbackQuoteChars(String string, String[] find, char[] replace) {
    int 		index;
    StringBuilder 	newStr;
    int[] 		pos;
    int			curPos;
    String 		str;
    int			i;

    if (string == null)
      return null;

    pos = new int[find.length];

    str = new String(string);
    newStr = new StringBuilder();
    while (str.length() > 0) {
      // get positions and closest character to replace
      curPos = str.length();
      index  = -1;
      for (i = 0; i < pos.length; i++) {
	pos[i] = str.indexOf(find[i]);
	if ( (pos[i] > -1) && (pos[i] < curPos) ) {
	  index  = i;
	  curPos = pos[i];
	}
      }

      // replace character if found, otherwise finished
      if (index == -1) {
	newStr.append(str);
	str = "";
      }
      else {
	newStr.append(str.substring(0, pos[index]));
	newStr.append(replace[index]);
	str = str.substring(pos[index] + find[index].length());
      }
    }

    return newStr.toString();
  }

  /**
   * The inverse operation of backQuoteChars().
   * Converts back-quoted carriage returns and new lines in a string
   * to the corresponding character ('\r' and '\n').
   * Also "un"-back-quotes the following characters: ` " \ \t and %
   *
   * @param string 	the string
   * @return 		the converted string
   * @see		#backQuoteChars(String)
   */
  public static String unbackQuoteChars(String string) {
    return unbackQuoteChars(
      string,
      new String[]{"\\\\", "\\'", "\\t", "\\n", "\\r", "\\\""},
      new char[]{'\\', '\'', '\t', '\n', '\r', '"'});
  }

  /**
   * Split up a string containing options into an array of strings,
   * one for each option.
   *
   * @param 		quotedOptionString the string containing the options
   * @return 		the array of options
   * @throws Exception 	in case of an unterminated string, unknown character or
   * 			a parse error
   */
  public static String[] splitOptions(String quotedOptionString) throws Exception{
    List<String> result;
    StringBuilder 	str;
    int 		i;
    String 		optStr;

    result = new ArrayList<>();
    str    = new StringBuilder(quotedOptionString);

    while (true) {
      // trimLeft
      i = 0;
      while ((i < str.length()) && (Character.isWhitespace(str.charAt(i))))
	i++;
      str = str.delete(0, i);

      // stop when str is empty
      if (str.length() == 0)
	break;

      // if str start with a double quote
      if (str.charAt(0) == '"') {
	// find the first not anti-slashed double quote
	i = 1;
	while (i < str.length()) {
	  if (str.charAt(i) == str.charAt(0))
	    break;
	  if (str.charAt(i) == '\\') {
	    i += 1;
	    if (i >= str.length())
	      throw new Exception("String should not finish with \\");
	  }
	  i += 1;
	}

	if (i >= str.length())
	  throw new Exception("Quote parse error.");

	// add the found string to the option vector (without quotes)
	optStr = str.substring(1, i);
	optStr = unbackQuoteChars(optStr);
	result.add(optStr);
	str = str.delete(0, i+1);
      }
      else {
	// find first whiteSpace
	i = 0;
	while ((i < str.length()) && (!Character.isWhitespace(str.charAt(i))))
	  i++;

	// add the found string to the option vector
	optStr = str.substring(0, i);
	result.add(optStr);
	str = str.delete(0, i);
      }
    }

    return result.toArray(new String[result.size()]);
  }

  /**
   * Joins all the options in an option array into a single string,
   * as might be used on the command line.
   *
   * @param optionArray the array of options
   * @return the string containing all options.
   */
  public static String joinOptions(String[] optionArray) {
    boolean 		escape;
    StringBuilder 	optionString;
    int			i;
    int			n;

    optionString = new StringBuilder();
    for (i = 0; i < optionArray.length; i++) {
      escape = false;

      // an invalid option encountered?
      if (optionArray[i] == null)
	continue;

      if (optionArray[i].equals(""))
	escape = true;

      for (n = 0; n < optionArray[i].length(); n++) {
        if ( Character.isWhitespace(optionArray[i].charAt(n))
          || (optionArray[i].charAt(n) == '"')
          || (optionArray[i].charAt(n) == '\'') ) {
	  escape = true;
	  break;
	}
      }

      if (escape)
	optionString.append('"' + backQuoteChars(optionArray[i]) + '"');
      else
	optionString.append(optionArray[i]);

      optionString.append(" ");
    }
    return optionString.toString().trim();
  }

  /**
   * Turns a camel case display name of a property into a commandline flag
   * that uses lowercase and hyphens.
   *
   * @param name	the display name of the property
   * @return		the flag
   */
  public static String displayNameToFlag(String name) {
    StringBuilder	result;
    int			i;

    result = new StringBuilder();

    for (i = 0; i < name.length(); i++) {
      if (Character.isUpperCase(name.charAt(i))) {
	if (i > 0)
	  result.append("-");
	result.append(Character.toLowerCase(name.charAt(i)));
      }
      else {
	result.append(name.charAt(i));
      }
    }

    return result.toString();
  }
}
