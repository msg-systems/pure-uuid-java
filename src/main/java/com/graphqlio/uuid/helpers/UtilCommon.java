/*******************************************************************************
 * *
 * **  Design and Development by msg Applied Technology Research
 * **  Copyright (c) 2019-2020 msg systems ag (http://www.msg-systems.com/)
 * **  All Rights Reserved.
 * ** 
 * **  Permission is hereby granted, free of charge, to any person obtaining
 * **  a copy of this software and associated documentation files (the
 * **  "Software"), to deal in the Software without restriction, including
 * **  without limitation the rights to use, copy, modify, merge, publish,
 * **  distribute, sublicense, and/or sell copies of the Software, and to
 * **  permit persons to whom the Software is furnished to do so, subject to
 * **  the following conditions:
 * **
 * **  The above copyright notice and this permission notice shall be included
 * **  in all copies or substantial portions of the Software.
 * **
 * **  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * **  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * **  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * **  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * **  CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * **  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * **  SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * *
 ******************************************************************************/
package com.graphqlio.uuid.helpers;

import org.apache.commons.lang.math.NumberUtils;

/**
 * Common Util
 *
 * @author Michael Schüfer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Müller
 */
public class UtilCommon {

	/**
	 * API method: import UUID from standard array of numbers
	 * 
	 * @param arr a standard array od numbers
	 * @return imported uuid
	 */
	public static long[] importUUID(long arr[]) throws Exception {
		long[] tab = {};
//	    if (!(typeof arr === "object" && arr instanceof Array))
		if (arr != null && arr.getClass().isArray())
			throw new Exception("UUID: import: invalid argument (type Array expected)");
		if (arr.length != 16)
			throw new Exception("UUID: import: invalid argument (Array of length 16 expected)");
		for (int i = 0; i < 16; i++) {
			if (!NumberUtils.isNumber(arr[i] + ""))
				throw new Exception("UUID: import: invalid array element #" + i + " (type Number expected)");
			if (!(Double.isInfinite(arr[i]) && Math.floor(arr[i]) == arr[i]))// isInfinie zu prufen
				throw new Exception(
						"UUID: import: invalid array element #" + i + " (Number with integer value expected)");
			if (!(arr[i] >= 0 && arr[i] <= 255))
				throw new Exception("UUID: import: invalid array element #" + i
						+ " (Number with integer value in range 0...255 expected)");
			tab[i] = arr[i];
		}
		return tab;
	}

	/**
	 * TODO
	 * API method: compare UUID against another one
	 * 
	 * @param other the uuid to compare
	 * @return imported uuid
	 */
	public static long compareUUID(Object other) {
		// Zurzeit keine Idde
//	    if (typeof other !== "object")
//	      throw new Error("UUID: compare: invalid argument (type UUID expected)");
//	    if (!(other instanceof UUID))
//	      throw new Error("UUID: compare: invalid argument (type UUID expected)");
//	    for (var i = 0; i < 16; i++) {
//	      if (this[i] < other[i]) return -1;
//	      else if (this[i] > other[i]) return +1;
//	    }
		return 0;
	}

	/**
	 * API method: hash UUID by XOR-folding it k times
	 * 
	 * @param k times
	 * @return hash UUID by XOR-folding it k times
	 */
	private static long[] fond(long k) throws Exception {
		if (k == 0)
			throw new Exception("UUID: fold: invalid argument (number of fold operations expected)");
		if (k < 1 || k > 4)
			throw new Exception("UUID: fold: invalid argument (1-4 fold operations expected)");
		int n = (int) (16 / Math.pow(2, k));
		long[] hash = new long[(int) n];
		for (int i = 0; i < n; i++) {
			int h = 0;
			// for (int j = 0; i + j < 16; j += n) h ^= this[i + j]; zu pufuen
			hash[i] = h;
		}
		return hash;
	}

}
