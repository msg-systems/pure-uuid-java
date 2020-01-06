package com.thinkenterprise.uuid.helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * SHA1 helps to create random Number from Time((PRNG)). 
 *
 * @author Michael Schäfer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Müller
 */
public final class SHA1 {


	/**
	 * 
	 * @param s
	 * @return
	 */
	public static final String getSHA1(String s) {

		Map<String, String> options=new HashMap<>();
		options.put("ibits", "8");
		options.put("obits", "32");
		options.put("obigendian", "true");

		Map<String, String> _options=new HashMap<>();
		_options.put("ibits", "32");
		_options.put("ibigendian", "true");

		return UI32Common.getA2s(UI32Common.getSha1Core(UI32Common.getS2a(s, options), s.length()*8), _options);
	}

}
