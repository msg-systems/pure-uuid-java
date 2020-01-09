package com.thinkenterprise.uuid.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.NumberUtils;

import com.thinkenterprise.uuid.domain.GlobaleConstants;

/**
 * ParseUUID helps to parse UUID from Text. 
 *
 * @author Michael Schaefer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Mueller
 */
public final class ParseUUID {

	/*  API method: parse UUID from usual textual representation  */
	//UUID.prototype.parse = function (str, type) {
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
	 * @param str
	 * @param begin
	 * @param end
	 * @param bytes
	 * @param pos
	 */
	private static void getHs2(String str, int begin, int end,long[] bytes,int pos) {
		for (int i = begin; i <= end; i += 2) {

			String strSub= i==2 ? str.substring(i, 4) : i<2 ? str.substring(i, 2): str.substring(i, 2+i);
			bytes[pos++] = Integer.parseInt(strSub, 16);
		}
	}

	/**
	 * 
	 * @param str
	 * @param dest
	 * @return
	 * @throws Exception 
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
