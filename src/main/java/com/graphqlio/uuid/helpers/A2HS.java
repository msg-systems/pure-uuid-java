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

import com.graphqlio.uuid.domain.TypeFormat;

/**
 * A2HS helper to format UUID into text
 *
 * @author Michael Schüfer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Müller
 */
public class A2HS {
	/**
	 * API method: format UUID into usual textual representation
	 *
	 * @param  type  the type of the uuid to be format
	 * @param  uuid[] the uuid to be format
	 * @return the result as a string from the uuid
	 * @throws Exception (mabe thrown by getZ85Encode)
	 */
	public static String format(String type, long uuid[]) throws Exception {
		String str = "";
		String arr[];
		if (type == TypeFormat.Z85.getTypeFormat()) {
			str = Encode.getZ85Encode(uuid, 16);
		} else if (type == TypeFormat.B16.getTypeFormat()) {
			arr = new String[32];
			getA2hs(uuid, 0, 15, true, arr, 0);
			// Convert Array to String
			StringBuilder sbf = new StringBuilder();
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != null) {
					sbf.append(arr[i]);
				}
			}

			str = sbf.toString();
		} else if (type == TypeFormat.VIDE.getTypeFormat() || type == TypeFormat.STD.getTypeFormat()) {
			arr = new String[32];
			getA2hs(uuid, 0, 3, false, arr, 0);
			arr[8] = "-";
			getA2hs(uuid, 4, 5, false, arr, 9);
			arr[13] = "-";
			getA2hs(uuid, 6, 7, false, arr, 14);
			arr[18] = "-";
			getA2hs(uuid, 8, 9, false, arr, 19);
			arr[23] = "-";
			getA2hs(uuid, 10, 15, false, arr, 24);
			str = convertTabToString(arr);
		}
		return str;
	}

	/**
	 * array to hex-string conversion
	 *
	 * @param  bytes		the array of the input bytes
	 * @param  begin		the begin of the initial array bytes to convert
	 * @param  end			the end of the initial array bytes to convert
	 * @param  uppercase	if {@code true}, return uppercase of all letters.
	 * @param  str			string to convert
	 * @param  pos			position of the string
	 * @return the result as a string from the uuid
	 */
	private static String[] getA2hs(long[] bytes, int begin, int end, boolean uppercase, String[] str, int pos) {

		for (int i = begin; i <= end; i++)
			if (mkNum(bytes[i], uppercase) != null) {
				str[pos++] = mkNum(bytes[i], uppercase);
			}

		return str;
	}

	private static String mkNum(long num, boolean uppercase) {
		String base16 = Long.toHexString(num); // num.toString(16);
		if (base16.length() < 2)
			base16 = "0" + base16;
		if (uppercase)
			base16 = base16.toUpperCase();
		return base16;
	}

	/**
	 * 
	 * @param arr array of the string to convert
	 * @return the convertion result of the string
	 */
	private static String convertTabToString(String arr[]) {
		String tabToString = "";

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null) {
				tabToString = tabToString + arr[i];
			}

		}
		return tabToString;
	}

}
