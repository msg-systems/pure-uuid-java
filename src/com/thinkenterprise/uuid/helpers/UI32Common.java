package com.thinkenterprise.uuid.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkenterprise.uuid.domain.GlobaleConstants;


/**
 * UI32Common with minimal UI32 functionality. 
 *
 * @author Michael Schäfer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Müller
 */
public final class UI32Common {

	/*
	 * this is just a really minimal UI32 functionality, just sufficient enough for
	 * the MD5 and SHA1 digests!
	 */

	/* string to array conversion */
	public static Long[] getS2a(String s, Map<String, String> _options) {
		/* determine options */
		// String[] options={"8","8","true"};
		Map<String, String> options = new HashMap<>();
		options.put("ibits", "8");
		options.put("obits", "8");
		options.put("obigendian", "true");

		for (Map.Entry<String, String> entry : _options.entrySet()) {
			options.put(entry.getKey(), entry.getValue());
		}

		/* convert string to array */
		Long a[] = {};
		List<Long> list = new ArrayList<>();
		int i = 0;
		int c, C = 0;
		int ck = 0;
		long w = 0;
		int wk = 0;
		int sl = s.length();
		for (;;) {
			/* fetch next octet from string */

			if (ck == 0 && i < sl) {// helps to avoid IndexOutBoundExcetion
				C = s.charAt(i++);
				c = (C >> (Integer.parseInt(options.get(GlobaleConstants.IBITS)) - (ck + 8))) & 0xFF;
			} else {
				c = 0;
			}

			ck = (ck + 8) % Integer.parseInt(options.get(GlobaleConstants.IBITS));

			/* place next word into array */
			if (Boolean.parseBoolean(options.get(GlobaleConstants.OBIGENDIAN))) {
				if (wk == 0)
					w = (c << (Integer.parseInt(options.get(GlobaleConstants.OBITS)) - 8));
				else
					w |= (c << ((Integer.parseInt(options.get(GlobaleConstants.OBITS)) - 8) - wk));
			} else {
				if (wk == 0)
					w = c;
				else
					w |= (c << wk);
			}
			wk = (wk + 8) % Integer.parseInt(options.get(GlobaleConstants.OBITS));
			if (wk == 0) {
				// a.push(w);
				list.add(w);
				if (i >= sl)
					break;
			}
		}
		a = list.toArray(a);
		return a;
	}

	/* bitwise rotate 32-bit number to the left */
	public static long getUi32Rol(long num, long cnt) {

		long sResult1 = 0;
		if (Integer.MIN_VALUE <= num && num <= Integer.MAX_VALUE) {
			int in = (int) num;
			sResult1 = ((in << cnt) & 0xFFFFFFFF);
		} else {
			sResult1 = ((num << cnt) & 0xFFFFFFFF);
		}

		long sResult2 = 0;
		if (Integer.MIN_VALUE <= num && num <= Integer.MAX_VALUE) {
			int in = (int) num;
			sResult2 = ((in >>> (32 - cnt)) & 0xFFFFFFFF);
		} else {
			sResult2 = ((num >>> (32 - cnt)) & 0xFFFFFFFF);
		}
		// long sResult2= ((num >>> (32 - cnt)) & 0xFFFFFFFF);
		long sResult = sResult1 | sResult2;
		return sResult;
	}

	/* safely add two integers (with wrapping at 2^32) */
	public static long getUi32Add(long x, long y) {
		long lsw = (x & 0xFFFF) + (y & 0xFFFF);
		long msw = (x >> 16) + (y >> 16) + (lsw >> 16);
		long sResult = ((int) msw << 16) | ((int) lsw & 0xFFFF);
		return sResult;
	}

	/* calculate the SHA-1 of an array of big-endian words, and a bit length */
	public static long[] getSha1Core(Long[] x, int len) {
		Long[] tab= {};
		if((len >> 5) >= x.length) {
			tab=new Long[(len >> 5)+1];
			for (int i = 0; i < x.length; i++) {
				tab[i]=x[i];
			}
			if (tab[len >> 5] == null)
				tab[len >> 5] = Long.valueOf(0x80 << (24 - len % 32));
			else 
				tab[len >> 5] |= 0x80 << (24 - len % 32);
		}
		else {
			tab=x;
			tab[len >> 5] |= 0x80 << (24 - len % 32);
		}
		/* append padding */
		
		// Length in javascript is dynamic, but in java works not
		// so we create new Table where length is groÃŸe.
		int currentLenght = ((len + 64 >> 9) << 4) + 15;
		long[] xx = new long[currentLenght + 1];

		for (int i = 0; i < xx.length; i++) {
			if (i < tab.length) {
				xx[i] = tab[i];
			} else {
				xx[i] = 0l;
			}

		}
		xx[currentLenght] = (long) len;

		long w[] = new long[80];
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

				long t = getUi32Add(getUi32Add(getUi32Rol(a, 5), getSha1Ft(j, b, c, d)),
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

	/*
	 * perform the appropriate triplet combination function for the current
	 * iteration
	 */
	public static final long getSha1Ft(long t, long b, long c, long d) {
		if (t < 20)
			return (b & c) | ((~b) & d);
		if (t < 40)
			return b ^ c ^ d;
		if (t < 60)
			return (b & c) | (b & d) | (c & d);
		return b ^ c ^ d;
	}

	/* determine the appropriate additive constant for the current iteration */
	public static final long getSha1Kt(long t) {
		return (t < 20) ? 1518500249 : (t < 40) ? 1859775393 : (t < 60) ? -1894007588 : -899497514;
	}

	/* array to string conversion */
	public static String getA2s(long[] a, Map<String, String> _options) {
		/* determine options */

		Map<String, String> options = new HashMap<>();
		options.put("ibits", "8");
		options.put("ibigendian", "true");

		for (Map.Entry<String, String> entry : _options.entrySet()) {
			options.put(entry.getKey(), entry.getValue());
		}

		/* convert array to string */
		String s = "";
		long imask = 0xFFFFFFFF;

		if (Integer.parseInt(options.get(GlobaleConstants.IBITS)) < 32)
			imask = (1 << Integer.parseInt(options.get(GlobaleConstants.IBITS))) - 1;
		int al = a.length;
		for (int i = 0; i < al; i++) {
			/* fetch next word from array */
			long w = a[i] & imask;

			/* place next octet into string */
			for (int j = 0; j < Integer.parseInt(options.get(GlobaleConstants.IBITS)); j += 8) {
				if (Boolean.parseBoolean(options.get(GlobaleConstants.IBIGENDIAN)))
					s += new String(new char[] {
							(char) (((int) w >> ((Integer.parseInt(options.get(GlobaleConstants.IBITS)) - 8) - j))
									& 0xFF) });
				else
					s += new String(new char[] { (char) (((int) w >> j) & 0xFF) });
			}
		}
		return s;
	}



}
