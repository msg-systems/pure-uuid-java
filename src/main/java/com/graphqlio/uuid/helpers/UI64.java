/**
 * *****************************************************************************
 *
 * <p>Design and Development by msg Applied Technology Research Copyright (c) 2019-2020 msg systems
 * ag (http://www.msg-systems.com/) All Rights Reserved.
 *
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * <p>The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * <p>****************************************************************************
 */
package com.graphqlio.uuid.helpers;

import com.graphqlio.uuid.domain.GlobaleConstants;

/**
 * UI64 helpers.
 *
 * @author Michael Schüfer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Müller
 */
public final class UI64 {

  /**
   * convert between individual digits and the UI64 representation, Null handling
   *
   * @param tab array of long
   * @return UI64 for zero
   */
  public static final long[] getUi64Zero(long tab[]) {

    return UI64Common.getUi64D2i(tab);
  }

  /**
   * Convert long to UI64
   *
   * @param tab, array of long
   * @param n, long to convert
   * @return the converted UI64 from the long
   */
  public static final long[] getUi64N2i(long tab[], long n) {
    long[] ui64 = getUi64Zero(tab);
    for (int i = 0; i < GlobaleConstants.UI64_DIGITS; i++) {
      ui64[i] = (long) Math.floor(n % GlobaleConstants.UI64_DIGIT_BASE);
      n /= GlobaleConstants.UI64_DIGIT_BASE;
    }
    return ui64;
  }
}
