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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.graphqlio.uuid.domain.GlobaleConstants;

/**
 * UI32Common with minimal UI32 functionality.
 *
 * @author Michael Schüfer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Müller
 */
public final class UI32Common {

  /*
   * this is just a really minimal UI32 functionality, just sufficient enough for
   * the MD5 and SHA1 digests!
   */

  /**
   * string to array conversion.
   *
   * @param s string to encode
   * @param options2 the map for the options
   * @return the s2a
   */
  public static Long[] getS2a(String s, Map<String, String> options2) {
    /* determine options */
    // String[] options={"8","8","true"};
    Map<String, String> options = new HashMap<>();
    options.put("ibits", "8");
    options.put("obits", "8");
    options.put("obigendian", "true");

    for (Map.Entry<String, String> entry : options2.entrySet()) {
      options.put(entry.getKey(), entry.getValue());
    }

    /* convert string to array */
    Long[] a = {};
    List<Long> list = new ArrayList<>();
    int i = 0;
    int c;
    int cc = 0;
    int ck = 0;
    long w = 0;
    int wk = 0;
    int sl = s.length();
    for (; ; ) {
      /* fetch next octet from string */

      if (ck == 0 && i < sl) { // helps to avoid IndexOutBoundExcetion
        cc = s.charAt(i++);
        c = (cc >> (Integer.parseInt(options.get(GlobaleConstants.IBITS)) - (ck + 8))) & 0xFF;
      } else {
        c = 0;
      }

      ck = (ck + 8) % Integer.parseInt(options.get(GlobaleConstants.IBITS));

      /* place next word into array */
      if (Boolean.parseBoolean(options.get(GlobaleConstants.OBIGENDIAN))) {
        if (wk == 0) {
          w = (c << (Integer.parseInt(options.get(GlobaleConstants.OBITS)) - 8));
        } else {
          w |= (c << ((Integer.parseInt(options.get(GlobaleConstants.OBITS)) - 8) - wk));
        }
      } else {
        if (wk == 0) {
          w = c;
        } else {
          w |= (c << wk);
        }
      }
      wk = (wk + 8) % Integer.parseInt(options.get(GlobaleConstants.OBITS));
      if (wk == 0) {
        // a.push(w);
        list.add(w);
        if (i >= sl) {
          break;
        }
      }
    }
    a = list.toArray(a);
    return a;
  }

  /**
   * bitwise rotate 32-bit number to the left.
   *
   * @param num a 32-bit number
   * @param cnt the count of the number
   * @return the to the left rotated 32-bit number
   */
  public static long getUi32Rol(long num, long cnt) {

    long result1 = 0;
    if (Integer.MIN_VALUE <= num && num <= Integer.MAX_VALUE) {
      int in = (int) num;
      result1 = ((in << cnt) & 0xFFFFFFFF);
    } else {
      result1 = ((num << cnt) & 0xFFFFFFFF);
    }

    long result2 = 0;
    if (Integer.MIN_VALUE <= num && num <= Integer.MAX_VALUE) {
      int in = (int) num;
      result2 = ((in >>> (32 - cnt)) & 0xFFFFFFFF);
    } else {
      result2 = ((num >>> (32 - cnt)) & 0xFFFFFFFF);
    }
    // long sResult2= ((num >>> (32 - cnt)) & 0xFFFFFFFF);
    long sresult = result1 | result2;
    return sresult;
  }

  /**
   * safely add two integers (with wrapping at 2^32).
   *
   * @param x the first integer
   * @param y the second integer to add
   * @return the safely added result with wrapping at 2^32
   */
  public static long getUi32Add(long x, long y) {
    long lsw = (x & 0xFFFF) + (y & 0xFFFF);
    long msw = (x >> 16) + (y >> 16) + (lsw >> 16);
    long sresult = ((int) msw << 16) | ((int) lsw & 0xFFFF);
    return sresult;
  }

  /**
   * calculate the SHA-1 of an array of big-endian words, and a bit length.
   *
   * @param x the array of big-endian words
   * @param len the bit length
   * @return the SHA-1 result of the calculation
   */
  public static long[] getSha1Core(Long[] x, int len) {
    Long[] tab = {};
    if ((len >> 5) >= x.length) {
      tab = new Long[(len >> 5) + 1];
      for (int i = 0; i < x.length; i++) {
        tab[i] = x[i];
      }
      if (tab[len >> 5] == null) {
        tab[len >> 5] = Long.valueOf(0x80 << (24 - len % 32));
      } else {
        tab[len >> 5] |= 0x80 << (24 - len % 32);
      }
    } else {
      tab = x;
      tab[len >> 5] |= 0x80 << (24 - len % 32);
    }
    /* append padding */

    // Length in javascript is dynamic, but in java works not
    // so we create new Table where length is große.
    int currentLenght = ((len + 64 >> 9) << 4) + 15;
    long[] xx = new long[currentLenght + 1];

    for (int i = 0; i < xx.length; i++) {
      if (i < tab.length) {
        xx[i] = tab[i];
      } else {
        xx[i] = 0L;
      }
    }
    xx[currentLenght] = (long) len;

    long[] w = new long[80];
    long a = 1732584193;
    long b = -271733879;
    long c = -1732584194;
    long d = 271733878;
    long e = -1009589776;

    for (int i = 0; i < xx.length; i += 16) {
      long olda = a;
      long oldb = b;
      long oldc = c;
      long oldd = d;
      long olde = e;

      for (int j = 0; j < 80; j++) {

        if (j < 16) {
          if ((i + j) < xx.length) {
            w[j] = xx[i + j];
          } else {
            w[j] = 0;
          }
        } else {
          w[j] = getUi32Rol(w[j - 3] ^ w[j - 8] ^ w[j - 14] ^ w[j - 16], 1);
        }

        long t =
            getUi32Add(
                getUi32Add(getUi32Rol(a, 5), getSha1Ft(j, b, c, d)),
                getUi32Add(getUi32Add(e, w[j]), getSha1Kt(j)));
        e = d;
        d = c;
        c = getUi32Rol(b, 30);
        b = a;
        a = t;
      }

      a = getUi32Add(a, olda);
      b = getUi32Add(b, oldb);
      c = getUi32Add(c, oldc);
      d = getUi32Add(d, oldd);
      e = getUi32Add(e, olde);
    }
    long[] values = new long[5];
    values[0] = a;
    values[1] = b;
    values[2] = c;
    values[3] = d;
    values[4] = e;

    return values;
  }

  /**
   * perform the appropriate triplet combination function for the current iteration.
   *
   * @param t the current UI32
   * @param b the first element of the triplet to combine
   * @param c the second element of the triplet to combine
   * @param d the third element of the triplet to combine
   * @return the appropriate triplet combination function for the current
   */
  public static final long getSha1Ft(long t, long b, long c, long d) {
    if (t < 20) {
      return (b & c) | ((~b) & d);
    }
    if (t < 40) {
      return b ^ c ^ d;
    }
    if (t < 60) {
      return (b & c) | (b & d) | (c & d);
    }
    return b ^ c ^ d;
  }

  /**
   * determine the appropriate additive constant for the current iteration.
   *
   * @param t the current to determine
   * @return the appropriate additive constant for the current iteration
   */
  public static final long getSha1Kt(long t) {
    return (t < 20) ? 1518500249 : (t < 40) ? 1859775393 : (t < 60) ? -1894007588 : -899497514;
  }

  /**
   * array to string conversion.
   *
   * @param a the input array to convert
   * @param options2 map of options
   * @return the appropriate additive constant for the current iteration
   */
  public static String getA2s(long[] a, Map<String, String> options2) {
    /* determine options */

    Map<String, String> options = new HashMap<>();
    options.put("ibits", "8");
    options.put("ibigendian", "true");

    for (Map.Entry<String, String> entry : options2.entrySet()) {
      options.put(entry.getKey(), entry.getValue());
    }

    /* convert array to string */
    String s = "";
    long imask = 0xFFFFFFFF;

    if (Integer.parseInt(options.get(GlobaleConstants.IBITS)) < 32) {
      imask = (1 << Integer.parseInt(options.get(GlobaleConstants.IBITS))) - 1;
    }
    int al = a.length;
    for (int i = 0; i < al; i++) {
      /* fetch next word from array */
      long w = a[i] & imask;

      /* place next octet into string */
      for (int j = 0; j < Integer.parseInt(options.get(GlobaleConstants.IBITS)); j += 8) {
        if (Boolean.parseBoolean(options.get(GlobaleConstants.IBIGENDIAN))) {
          s +=
              new String(
                  new char[] {
                    (char)
                        (((int) w
                                >> ((Integer.parseInt(options.get(GlobaleConstants.IBITS)) - 8)
                                    - j))
                            & 0xFF)
                  });
        } else {
          s += new String(new char[] {(char) (((int) w >> j) & 0xFF)});
        }
      }
    }
    return s;
  }
}
