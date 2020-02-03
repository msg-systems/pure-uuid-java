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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.NumberUtils;

import com.graphqlio.uuid.domain.GlobaleConstants;

/**
 * ParseUUID helps to parse UUID from Text. 
 *
  * @author Michael Schüfer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Müller
 */
public final class ParseUUID {

	//UUID.prototype.parse = function (str, type) {
	/**
	 * API method: parse UUID from usual textual representation
	 * @param str a usual textual representation
	 * @param type type of encoding form
	 * @return the result uuid of 
	 * @throws Exception 
	 */
	public static long [] getParse(String str, String type) throws Exception {
		long[] bytes=new long[16];
		Map<String, String> map =new HashMap<>();
		///^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/  in javascript
		String pattern="^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
		Pattern r = Pattern.compile(pattern);
		if (NumberUtils.isNumber(str))
			throw new Exception("UUID: parse: invalid argument (type string expected)");
		if (type == "z85")
			getZ85Decode(str, bytes);
		else if (type == "b16")
			getHs2(str, 0, 35, bytes, 0);
		else if (type == null ||type == "Nothing" || type == "std") {

			map.put("nil","00000000-0000-0000-0000-000000000000");
			map.put("ns:DNS","6ba7b810-9dad-11d1-80b4-00c04fd430c8");
			map.put("ns:URL","6ba7b811-9dad-11d1-80b4-00c04fd430c8");
			map.put("ns:OID","6ba7b812-9dad-11d1-80b4-00c04fd430c8");
			map.put("ns:X500", "6ba7b814-9dad-11d1-80b4-00c04fd430c8");

		}
		if (map.get(str)!= null)
			str = map.get(str);
		//            else if (!str.match())
		else if (!r.matcher(str).find())
			throw new Exception("UUID: parse: invalid string representation " +
					"(expected \"xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx\")");
		getHs2(str,  0,  7, bytes,  0);
		getHs2(str,  9, 12, bytes,  4);
		getHs2(str, 14, 17, bytes,  6);
		getHs2(str, 19, 22, bytes,  8);
		getHs2(str, 24, 35, bytes, 10);

		return bytes;
	}

	/**
	 * 
	 * @param str str
	 * @param begin begin
	 * @param end end
	 * @param bytes bytes
	 * @param pos pos
	 */
	private static void getHs2(String str, int begin, int end,long[] bytes,int pos) {
		for (int i = begin; i <= end; i += 2) {

			String strSub= i==2 ? str.substring(i, 4) : i<2 ? str.substring(i, 2): str.substring(i, 2+i);
			bytes[pos++] = Integer.parseInt(strSub, 16);
		}
	}

	/**
	 * 
	 * @param str str
	 * @param dest dest
	 * @return Nothing Nothing
	 * @throws Exception Exception
	 */
	private static long[] getZ85Decode (String str,long[] dest) throws Exception {
		int l = str.length();
		if ((l % 5) != 0)
			throw new Exception("z85_decode: invalid input length (multiple of 5 expected)");
		if (dest ==null)
			dest = new long[l * 4 / 5];
		int i = 0, j = 0, value = 0;
		while (i < l) {
			//  int idx = str.charCodeAt(i++) - 32;
			int idx = str.charAt(i++) - 32;
			if (idx < 0 || idx >= GlobaleConstants.Z85_DECODER.length)
				break;
			value = (int) ((value * 85) + GlobaleConstants.Z85_DECODER[idx]);
			if ((i % 5) == 0) {
				int divisor = 256 * 256 * 256;
				while (divisor >= 1) {
					dest[j++] = ((int)(value / divisor) % 256);
					divisor /= 256;
				}
				value = 0;
			}
		}
		return dest;
	}



}
