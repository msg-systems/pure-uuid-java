package com.thinkenterprise.uuid.helpers;

import com.thinkenterprise.uuid.domain.GlobaleConstants;

/**
 * ParseUUID helps to parse UUID from Text. 
 *
 * @author Michael Schaefer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Mueller
 */
public final class UI64Common {

	/**
	 * 
	 * convert between individual digits and the UI64 representation
	 * @param tab
	 * @return
	 */
	public static final long[] getUi64D2i(long tab[]) {

		return new long[] {tab[7],tab[6],tab[5],tab[4],tab[3],tab[2],tab[1],tab[0]};
	}


	/*  multiply number (n) to UI64 (x) and return overflow/carry as number  */
	public static long getUi64Muln(long[] x, long n) {
		long carry = 0;
		for (int i = 0; i < GlobaleConstants.UI64_DIGITS; i++) {
			carry += x[i] * n;
			x[i]   = (long) Math.floor(carry % GlobaleConstants.UI64_DIGIT_BASE);
			carry  =  (long) Math.floor(carry / GlobaleConstants.UI64_DIGIT_BASE);
		}
		return carry;
	}

	/*  add UI64 (y) to UI64 (x) and return overflow/carry as number  
    /**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static int getUi64Add(long[] x,long[] y) {
		int carry = 0;
		y=getUi64D2i(y);
		for (int i = 0; i < GlobaleConstants.UI64_DIGITS; i++) {
			carry += x[i] + y[i];
			x[i]   = (long) Math.floor(carry % GlobaleConstants.UI64_DIGIT_BASE);
			carry  = (int) Math.floor(carry / GlobaleConstants.UI64_DIGIT_BASE);
		}
		return carry;
	}
	public static int getUi64Add_2(long[] x,long[] y) {
		int carry = 0;

		for (int i = 0; i < GlobaleConstants.UI64_DIGITS; i++) {
			carry += x[i] + y[i];
			x[i]   = (long) Math.floor(carry % GlobaleConstants.UI64_DIGIT_BASE);
			carry  = (int) Math.floor(carry / GlobaleConstants.UI64_DIGIT_BASE);
		}
		return carry;
	}

	/*  convert between UI64 representation and number  */
	/** Diese Methode gibt long als Wert (471779603359996160) manchmal rück**/
	public static long  getUi64I2n(long x[]) {
		long n = 0;
		for (long i = GlobaleConstants.UI64_DIGITS - 1; i >= 0; i--) {
			n *= GlobaleConstants.UI64_DIGIT_BASE;
			n += x[(int) i];
		}
		return (long) Math.floor(n);
	}



	/*  rotate right UI64 (x) by a "s" bits and return overflow/carry as number  */
	private static long getUi64Rorn(long []x, int s) {
		long[] ov =UI64.getUi64Zero(GlobaleConstants.TAB_0);
		if ((s % GlobaleConstants.UI64_DIGIT_BITS) != 0)
			throw new Error("ui64_rorn: only bit rotations supported with a multiple of digit bits");
		long k =  (long) Math.floor(s / GlobaleConstants.UI64_DIGIT_BITS);
		for (long i = 0; i < k; i++) {
			for (long j = GlobaleConstants.UI64_DIGITS - 1 - 1; j >= 0; j--)
				ov[(int) (j + 1)] = ov[(int) j];
			ov[0] = x[0];
			int m=0;
			// ?? zu prüfen Fall Fehler

			for ( m=0 ; m < GlobaleConstants.UI64_DIGITS - 1; m++) {
				x[m] = x[m + 1];
			}
			x[m] = 0;
		}
		return getUi64I2n(ov);
	}
	/**
	 * Die Methode gibt die generierende Liste UUID rück.
	 * store the 60 LSB of the time in the UUID  
	 * @param converTime
	 * @param n
	 * @return
	 */
	public static long[] getStoreLSBinUUID(long converTime[],int n) {
		long ov=0;
		long uuid[]=new long[16];

		ov = getUi64Rorn(converTime, n); 
		uuid[3] = (ov & 0xFF);
		ov = getUi64Rorn(converTime, n);
		uuid[2] = (ov & 0xFF);
		ov = getUi64Rorn(converTime, n); 
		uuid[1] = (ov & 0xFF);
		ov = getUi64Rorn(converTime, n);
		uuid[0] = (ov & 0xFF);
		ov = getUi64Rorn(converTime, n);
		uuid[5] = (ov & 0xFF);
		ov = getUi64Rorn(converTime, n);
		uuid[4] = (ov & 0xFF);
		ov = getUi64Rorn(converTime, n);
		uuid[7] = (ov & 0xFF);
		ov = getUi64Rorn(converTime, n);
		uuid[6] = (ov & 0x0F);

		return uuid;
	}

	/*  multiply UI64 (y) to UI64 (x) and return overflow/carry as UI64  */
	public static void  getUi64Mul(long []x, long y []) {

		int i, j;

		/*  clear temporary result buffer zx  */
		double zx[] = new double[(int) (GlobaleConstants.UI64_DIGITS + GlobaleConstants.UI64_DIGITS)];
		for (i = 0; i < (GlobaleConstants.UI64_DIGITS + GlobaleConstants.UI64_DIGITS); i++)
			zx[i] = 0;

		/*  perform multiplication operation  */
		double carry;
		for (i = 0; i < GlobaleConstants.UI64_DIGITS; i++) {
			/*  calculate partial product and immediately add to zx  */
			carry = 0;
			for (j = 0; j < GlobaleConstants.UI64_DIGITS; j++) {
				carry += (x[i] * y[j]) + zx[i + j];
				zx[i + j] = (carry % GlobaleConstants.UI64_DIGIT_BASE);
				carry /= GlobaleConstants.UI64_DIGIT_BASE;
			}

			/*  add carry to remaining digits in zx  */
			for ( ; j < GlobaleConstants.UI64_DIGITS + GlobaleConstants.UI64_DIGITS - i; j++) {
				carry += zx[i + j];
				zx[i + j] = (carry % GlobaleConstants.UI64_DIGIT_BASE);
				carry /= GlobaleConstants.UI64_DIGIT_BASE;
			}
		}

		/*  provide result by splitting zx into x and ov  */
		for (i = 0; i < GlobaleConstants.UI64_DIGITS; i++)
			x[i] = (int) zx[i];// zu prüfen (Konflikt vielleicht und falsche Ergebnis Konvert zu)

		// Verleeren die Tabelle
		//return null; //;zx.slice(GlobaleConstants.UI64_DIGITS, GlobaleConstants.UI64_DIGITS);
	}


	/*  rotate right UI64 (x) by a "s" bits and return overflow/carry as number  */


	public static void getUi64Ror(long[] x,long s) throws Exception {

		/*  sanity check shifting  */
		if (s > (GlobaleConstants.UI64_DIGITS * GlobaleConstants.UI64_DIGIT_BITS))
			throw new Exception("ui64_ror: invalid number of bits to shift");

		/*  prepare temporary buffer zx  */
		long zx[] = new long[(int) (GlobaleConstants.UI64_DIGITS + GlobaleConstants.UI64_DIGITS)];
		long i;
		for (i = 0; i < GlobaleConstants.UI64_DIGITS; i++) {
			zx[(int) (i + GlobaleConstants.UI64_DIGITS)] = x[(int) i];
			zx[(int) i] = 0;
		}

		/*  shift bits inside zx  */
		long k1 = (long) Math.floor(s / GlobaleConstants.UI64_DIGIT_BITS);
		long k2 = s % GlobaleConstants.UI64_DIGIT_BITS;
		for (i = k1; i < GlobaleConstants.UI64_DIGITS + GlobaleConstants.UI64_DIGITS - 1; i++) {
			zx[(int) (i - k1)] =
					((zx[(int) i] >>> k2) |
							(zx[(int) (i + 1)] << (GlobaleConstants.UI64_DIGIT_BITS - k2))) &
					((1 << GlobaleConstants.UI64_DIGIT_BITS) - 1);
		}
		zx[(int) (GlobaleConstants.UI64_DIGITS + GlobaleConstants.UI64_DIGITS - 1 - k1)] =
				(zx[(int) (GlobaleConstants.UI64_DIGITS + GlobaleConstants.UI64_DIGITS - 1)] >>> k2) &
				((1 << GlobaleConstants.UI64_DIGIT_BITS) - 1);
		for (i = (int) (GlobaleConstants.UI64_DIGITS + GlobaleConstants.UI64_DIGITS - 1 - k1 + 1); i < GlobaleConstants.UI64_DIGITS + GlobaleConstants.UI64_DIGITS; i++)
			zx[(int) i] = 0;

		/*  provide result by splitting zx into x and ov  */
		for (i = 0; i < GlobaleConstants.UI64_DIGITS; i++)
			x[(int) i] = zx[(int) (i + GlobaleConstants.UI64_DIGITS)];

	}



	/*  XOR UI64 (y) onto UI64 (x) and return x  */
	public static void getUi64Xor(long []x,long [] y) {
		for (int i = 0; i < GlobaleConstants.UI64_DIGITS; i++)
			x[i] ^= y[i];

	}

	/*  AND operation: UI64 (x) &= UI64 (y)  */
	public static long [] getUi64And(long x[], long y[]) {
		for (int i = 0; i < GlobaleConstants.UI64_DIGITS; i++)
			x[i] &= y[i];
		return x;
	}

	/*  OR operation: UI64 (x) |= UI64 (y)  */
	public static int[] getUi64Or(int []x,int[] y) {
		for (int i = 0; i < GlobaleConstants.UI64_DIGITS; i++)
			x[i] |= y[i];
		return x;
	}

	/*  rotate left UI64 (x) by a "s" bits and return overflow/carry as UI64  */
	public static void getUi64Rol(long x[], long s) throws Exception {
		/*  sanity check shifting  */
		if (s > (GlobaleConstants.UI64_DIGITS * GlobaleConstants.UI64_DIGIT_BITS))
			throw new Exception("ui64_rol: invalid number of bits to shift");

		/*  prepare temporary buffer zx  */
		long[] zx = new long[(int) (GlobaleConstants.UI64_DIGITS + GlobaleConstants.UI64_DIGITS)];
		long i;
		for (i = 0; i < GlobaleConstants.UI64_DIGITS; i++) {
			zx[(int) (i + GlobaleConstants.UI64_DIGITS)] = 0;
			zx[(int) i] = x[(int) i];
		}

		/*  shift bits inside zx  */
		long k1 = (int) Math.floor(s / GlobaleConstants.UI64_DIGIT_BITS);
		long k2 = s % GlobaleConstants.UI64_DIGIT_BITS;
		for (i = GlobaleConstants.UI64_DIGITS - 1 - k1; i > 0; i--) {
			zx[(int) (i + k1)] =
					((zx[(int) i] << k2) |
							(zx[(int) (i - 1)] >>> (GlobaleConstants.UI64_DIGIT_BITS - k2))) &
					((1 << GlobaleConstants.UI64_DIGIT_BITS) - 1);
		}
		zx[(int) (0 + k1)] = (zx[0] << k2) & ((1 << GlobaleConstants.UI64_DIGIT_BITS) - 1);
		for (i = 0 + k1 - 1; i >= 0; i--)
			zx[(int) i] = 0;

		/*  provide result by splitting zx into x and ov  */
		for (i = 0; i < GlobaleConstants.UI64_DIGITS; i++)
			x[(int) i] = zx[(int) i];
		// return zx.slice(UI64_DIGITS, UI64_DIGITS);
	}

}
