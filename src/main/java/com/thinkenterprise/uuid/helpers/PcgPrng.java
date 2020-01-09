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
package com.thinkenterprise.uuid.helpers;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.thinkenterprise.uuid.domain.GlobaleConstants;

/**
 * PcgPrng helps to create random Number from Time((PRNG)).
 *
 * @author Michael Schüfer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Müller
 */
public final class PcgPrng {

	/**
	 * utility function: simple Pseudo Random Number Generator (PRNG)
	 * 
	 * @param len   length of the result
	 * @param radix the radix to create the random number
	 * @param store the store
	 * @return the array which create with the pseudo random number generator
	 */
	public static long[] getPrngClock(int len, int radix, long store[]) throws Exception {
		long bytes[] = new long[len];
		long storeStateInner[] = { -9223372036854775808l, 0, 0, 0, 0, 0, 0, 9223372036854775807l };
		for (int i = 0; i < len; i++)
			// zu machen
			// bytes[i] = (pcg.next() % radix);
			bytes[i] = createMagicConstant(storeStateInner) % radix;
		// Copy current state into store
		for (int i = 0; i < storeStateInner.length; i++) {
			store[i] = storeStateInner[i];
		}

		return bytes;
	}

	/**
	 * utility function: simple Pseudo Random Number Generator (PRNG)
	 * 
	 * @param len   length of the result
	 * @param radix the radix to create the random number
	 * @param store the store
	 * @return the array which create with the pseudo random number generator
	 */
	public static long[] getPrngLocal(int len, int radix, long store[]) throws Exception {
		long bytes[] = new long[len];
		for (int i = 0; i < len; i++)
			// zu machen
			// bytes[i] = (pcg.next() % radix);
			bytes[i] = createMagicConstant(store) % radix;

		return bytes;
	}

	/**
	 * clone the UI64
	 * 
	 * @param x the UI64 to clone
	 * @return the cloned UI64
	 */
	private static long[] getUi64Clone(long[] x) {
		// x.slice(0) zu pruffen, but x.slice(0) gibt immer x als Wert
		return x;
	}

	/**
	 * clone the UI64
	 * 
	 * @param x the UI64 to clone
	 * @param y a UI64 
	 * @return the cloned copy UI64
	 */
	private static long[] getUi64CloneCopy(long[] x, long[] y) {
		// x.slice(0) zu pruffen, but x.slice(0) gibt immer x als Wert
		for (int i = 0; i < x.length; i++) {
			y[i] = x[i];
		}
		return y;
	}

	/*
	 * PCG Pseudo-Random-Number-Generator (PRNG)
	 * http://www.pcg-random.org/pdf/hmc-cs-2014-0905.pdf This is the PCG-XSH-RR
	 * variant ("xorshift high (bits), random rotation"), based on 32-bit output,
	 * 64-bit internal state and the formulas: state = state * MUL + INC output =
	 * rotate32((state ^ (state >> 18)) >> 27, state >> 59)
	 */

	// var PCG = function (seed)
	public static long createMagicConstant(long store[]) throws Exception {

		/* pre-load some "magic" constants */
		long mul[] = UI64Common.getUi64D2i(GlobaleConstants.MUL_TAB);
		long inc[] = UI64Common.getUi64D2i(GlobaleConstants.INC_TAB);
		long mask[] = UI64Common.getUi64D2i(GlobaleConstants.MASK_TAB);

		// Zu pruffen Art : int oder double !!!!!
		/* generate an initial internal state */

		long state[] = new long[8];
		long stateNew[] = new long[8];
		if (store[0] == Long.MIN_VALUE && store[7] == Long.MAX_VALUE) {

			// Generate state from current Time
			// long n= new Date().getTime();
			long n = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			System.out.println("n : " + n);
			state = UI64.getUi64N2i(GlobaleConstants.TAB_0, n);
			// state = getUi64Clone(inc);// use before ???

			/// ***** ---- ******\\\
			/* save current state */
			getUi64CloneCopy(state, stateNew);

			// PCG.prototype.next = function ()

			/* advance internal state */

			UI64Common.getUi64Mul(state, mul);
			UI64Common.getUi64Add_2(state, inc);
		} else {
			state = getUi64Clone(store);

			/// ***** ---- ******\\\
			/* save current state */
			// stateNew=getUi64Clone(state);
			getUi64CloneCopy(state, stateNew);

			// PCG.prototype.next = function ()

			/* advance internal state */

			UI64Common.getUi64Mul(state, mul);
			UI64Common.getUi64Add_2(state, inc);
		}
		// Copy current state into store
		for (int i = 0; i < state.length; i++) {
			store[i] = state[i];
		}

		/* calculate: (state ^ (state >> 18)) >> 27 */

		long outPut[] = getUi64Clone(stateNew);

		/* rotate right UI64 (x) by a "s" bits and return overflow/carry as number */
		long stateNew_64[] = new long[8];
		for (int i = 0; i < stateNew.length; i++) {
			stateNew_64[i] = stateNew[i];
		}
		UI64Common.getUi64Ror(outPut, GlobaleConstants.OUTPUT_18);
		UI64Common.getUi64Xor(outPut, stateNew_64);
		UI64Common.getUi64Ror(outPut, GlobaleConstants.OUTPUT_27);

		/* calculate: state >> 59 */
		long[] rot = getUi64Clone(stateNew_64);

		UI64Common.getUi64Ror(rot, GlobaleConstants.OUTPUT_59);

		/* calculate: rotate32(xorshifted, rot) */
		UI64Common.getUi64And(outPut, mask);

		long k = UI64Common.getUi64I2n(rot);

		// OutPut 2 from OutPut
		long outPut2[] = getUi64Clone(outPut);

		long outPut_k[] = new long[8];
		for (int i = 0; i < outPut.length; i++) {
			outPut_k[i] = outPut[i];
		}
		UI64Common.getUi64Rol(outPut2, 32 - k);

		UI64Common.getUi64Ror(outPut_k, k);

		UI64Common.getUi64Xor(outPut_k, outPut2);

		/* return pseudo-random number */
		long pseudoNumber = UI64Common.getUi64I2n(outPut_k);
		return pseudoNumber;

	}

	// Copy Table into Table

	public static void CopyTabltInoTable(long[] table1, long[] table2) {

	}

}
