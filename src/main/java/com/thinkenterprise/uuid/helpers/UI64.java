package com.thinkenterprise.uuid.helpers;

import com.thinkenterprise.uuid.domain.GlobaleConstants;


/**
 * ParseUUID helps to parse UUID from Text. 
 *
 * @author Michael Schaefer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Mueller
 */
public final class UI64 {


	/**
	 * Null handling
	 * @param tab
	 * @return
	 */
	public static final long[] getUi64Zero(long tab[]) {

		return UI64Common.getUi64D2i(tab);

	}
	/**
	 * Convert long to UI64

	 * @param tab
	 * @param n
	 * @return
	 */
	public static final long[] getUi64N2i(long tab[],long n) {
		long [] ui64 = getUi64Zero(tab);
		for (int i = 0; i < GlobaleConstants.UI64_DIGITS; i++) {
			ui64[i] = (long) Math.floor(n % GlobaleConstants.UI64_DIGIT_BASE);
			n /= GlobaleConstants.UI64_DIGIT_BASE;
		}
		return ui64;


	}



}
