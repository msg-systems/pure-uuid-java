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

import java.util.HashMap;
import java.util.Map;

/**
 * MD5 Encoder.
 *
 * @author Michael Schüfer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Müller
 */
public class Md5 {

  /**
   * calculate the MD5 of an octet string.
   *
   * @param s the octet string to calculate
   * @return the result string for the md5
   */
  public static String md5(String s) {

    Map<String, String> options = new HashMap<>();
    options.put("ibits", "8");
    options.put("obits", "32");
    options.put("obigendian", "false");

    Map<String, String> options2 = new HashMap<>();
    options2.put("ibits", "32");
    options2.put("ibigendian", "false");

    String result =
        UI32Common.getA2s(getMd5Core(UI32Common.getS2a(s, options), s.length() * 8), options2);
    return result;
  }

  /**
   * calculate the MD5 of an array of little-endian words, and a bit length.
   *
   * @param xx array of little-endian words
   * @param len the bit length
   * @return the result MD5
   */
  private static long[] getMd5Core(Long[] xx, int len) {

    /* append padding */
    xx[len >> 5] |= 0x80 << ((len) % 32);
    int index = (((len + 64) >>> 9) << 4) + 14;
    Long[] x = new Long[index + 1];

    for (int i = 0; i < xx.length; i++) {
      x[i] = xx[i];
    }
    x[index] = (long) len;

    for (int i = 0; i < x.length; i++) {
      if (x[i] == null) {
        x[i] = 0L;
      }
    }

    long a = 1732584193;
    long b = -271733879;
    long c = -1732584194;
    long d = 271733878;

    for (int i = 0; i < x.length; i += 16) {
      a = getMd5FF(a, b, c, d, x[i + 0], 7, -680876936);
      d = getMd5FF(d, a, b, c, x[i + 1], 12, -389564586);
      c = getMd5FF(c, d, a, b, x[i + 2], 17, 606105819);
      b = getMd5FF(b, c, d, a, x[i + 3], 22, -1044525330);
      a = getMd5FF(a, b, c, d, x[i + 4], 7, -176418897);
      d = getMd5FF(d, a, b, c, x[i + 5], 12, 1200080426);
      c = getMd5FF(c, d, a, b, x[i + 6], 17, -1473231341);
      b = getMd5FF(b, c, d, a, x[i + 7], 22, -45705983);
      a = getMd5FF(a, b, c, d, x[i + 8], 7, 1770035416);
      d = getMd5FF(d, a, b, c, x[i + 9], 12, -1958414417);
      c = getMd5FF(c, d, a, b, x[i + 10], 17, -42063);
      b = getMd5FF(b, c, d, a, x[i + 11], 22, -1990404162);
      a = getMd5FF(a, b, c, d, x[i + 12], 7, 1804603682);
      d = getMd5FF(d, a, b, c, x[i + 13], 12, -40341101);
      c = getMd5FF(c, d, a, b, x[i + 14], 17, -1502002290);
      // b = getMd5FF(b, c, d, a, x[i+15], 22, 1236535329);
      b = getMd5FF(b, c, d, a, 0, 22, 1236535329);

      a = getMd5GG(a, b, c, d, x[i + 1], 5, -165796510);
      d = getMd5GG(d, a, b, c, x[i + 6], 9, -1069501632);
      c = getMd5GG(c, d, a, b, x[i + 11], 14, 643717713);
      b = getMd5GG(b, c, d, a, x[i + 0], 20, -373897302);
      a = getMd5GG(a, b, c, d, x[i + 5], 5, -701558691);
      d = getMd5GG(d, a, b, c, x[i + 10], 9, 38016083);
      // c = getMd5GG(c, d, a, b, x[i+15], 14, -660478335);
      c = getMd5GG(c, d, a, b, 0, 14, -660478335);

      b = getMd5GG(b, c, d, a, x[i + 4], 20, -405537848);
      a = getMd5GG(a, b, c, d, x[i + 9], 5, 568446438);
      d = getMd5GG(d, a, b, c, x[i + 14], 9, -1019803690);
      c = getMd5GG(c, d, a, b, x[i + 3], 14, -187363961);
      b = getMd5GG(b, c, d, a, x[i + 8], 20, 1163531501);
      a = getMd5GG(a, b, c, d, x[i + 13], 5, -1444681467);
      d = getMd5GG(d, a, b, c, x[i + 2], 9, -51403784);
      c = getMd5GG(c, d, a, b, x[i + 7], 14, 1735328473);
      b = getMd5GG(b, c, d, a, x[i + 12], 20, -1926607734);

      a = getMd5HH(a, b, c, d, x[i + 5], 4, -378558);
      d = getMd5HH(d, a, b, c, x[i + 8], 11, -2022574463);
      c = getMd5HH(c, d, a, b, x[i + 11], 16, 1839030562);
      b = getMd5HH(b, c, d, a, x[i + 14], 23, -35309556);
      a = getMd5HH(a, b, c, d, x[i + 1], 4, -1530992060);
      d = getMd5HH(d, a, b, c, x[i + 4], 11, 1272893353);
      c = getMd5HH(c, d, a, b, x[i + 7], 16, -155497632);
      b = getMd5HH(b, c, d, a, x[i + 10], 23, -1094730640);
      a = getMd5HH(a, b, c, d, x[i + 13], 4, 681279174);
      d = getMd5HH(d, a, b, c, x[i + 0], 11, -358537222);
      c = getMd5HH(c, d, a, b, x[i + 3], 16, -722521979);
      b = getMd5HH(b, c, d, a, x[i + 6], 23, 76029189);
      a = getMd5HH(a, b, c, d, x[i + 9], 4, -640364487);
      d = getMd5HH(d, a, b, c, x[i + 12], 11, -421815835);
      // c = getMd5HH(c, d, a, b, x[i+15], 16, 530742520);

      c = getMd5HH(c, d, a, b, 0, 16, 530742520);

      b = getMd5HH(b, c, d, a, x[i + 2], 23, -995338651);

      a = getMd5II(a, b, c, d, x[i + 0], 6, -198630844);
      d = getMd5II(d, a, b, c, x[i + 7], 10, 1126891415);
      c = getMd5II(c, d, a, b, x[i + 14], 15, -1416354905);
      b = getMd5II(b, c, d, a, x[i + 5], 21, -57434055);
      a = getMd5II(a, b, c, d, x[i + 12], 6, 1700485571);
      d = getMd5II(d, a, b, c, x[i + 3], 10, -1894986606);
      c = getMd5II(c, d, a, b, x[i + 10], 15, -1051523);
      b = getMd5II(b, c, d, a, x[i + 1], 21, -2054922799);
      a = getMd5II(a, b, c, d, x[i + 8], 6, 1873313359);
      // d = getMd5II(d, a, b, c, x[i+15], 10, -30611744);

      d = getMd5II(d, a, b, c, 0, 10, -30611744);
      c = getMd5II(c, d, a, b, x[i + 6], 15, -1560198380);
      b = getMd5II(b, c, d, a, x[i + 13], 21, 1309151649);
      a = getMd5II(a, b, c, d, x[i + 4], 6, -145523070);
      d = getMd5II(d, a, b, c, x[i + 11], 10, -1120210379);
      c = getMd5II(c, d, a, b, x[i + 2], 15, 718787259);
      b = getMd5II(b, c, d, a, x[i + 9], 21, -343485551);

      long olda = a;
      a = UI32Common.getUi32Add(a, olda);
      long oldb = b;
      b = UI32Common.getUi32Add(b, oldb);
      long oldc = c;
      c = UI32Common.getUi32Add(c, oldc);
      long oldd = d;
      d = UI32Common.getUi32Add(d, oldd);
    }
    long[] tabResult = {a, b, c, d};

    return tabResult;
  }

  /**
   * basic operations the algorithm uses.
   *
   * @param a a
   * @param b b
   * @param c c
   * @param d c
   * @param x x
   * @param s s
   * @param t t
   * @return the result MD5Mmn
   */
  private static long getMd5Mmn(long q, long a, long b, long x, long s, long t) {
    long add32Arg1 = UI32Common.getUi32Add(a, q);
    long add32Arg2 = UI32Common.getUi32Add(x, t);
    long rolArg1 = UI32Common.getUi32Add(add32Arg1, add32Arg2);
    long add32Arg = UI32Common.getUi32Rol(rolArg1, s);

    long result = UI32Common.getUi32Add(add32Arg, b);
    return result;
  }

  /**
   * getMd5FF.
   *
   * @param a a
   * @param b b
   * @param c c
   * @param d c
   * @param x x
   * @param s s
   * @param t t
   * @return Md5FF
   */
  private static long getMd5FF(long a, long b, long c, long d, long x, long s, long t) {

    long result = getMd5Mmn((b & c) | ((~b) & d), a, b, x, s, t);
    return result;
  }

  /**
   * getMd5GG.
   *
   * @param a a
   * @param b b
   * @param c c
   * @param d c
   * @param x x
   * @param s s
   * @param t t
   * @return Md5GG
   */
  private static long getMd5GG(long a, long b, long c, long d, long x, long s, long t) {
    return getMd5Mmn((b & d) | (c & (~d)), a, b, x, s, t);
  }

  /**
   * getMd5HH.
   *
   * @param a a
   * @param b b
   * @param c c
   * @param d c
   * @param x x
   * @param s s
   * @param t t
   * @return md5HH
   */
  private static long getMd5HH(long a, long b, long c, long d, long x, long s, long t) {
    return getMd5Mmn(b ^ c ^ d, a, b, x, s, t);
  }

  /**
   * getMd5II.
   *
   * @param a a
   * @param b b
   * @param c c
   * @param d c
   * @param x x
   * @param s s
   * @param t t
   * @return md5II
   */
  private static long getMd5II(long a, long b, long c, long d, long x, long s, long t) {
    return getMd5Mmn(c ^ (b | (~d)), a, b, x, s, t);
  }
}
