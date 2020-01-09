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
package com.thinkenterprise.uuid.domain;


/**
 * GlobaleConstants providing constants 
 *
 * @author Michael Schüfer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Müller
 */

public final class GlobaleConstants {

	/*  UI64 constants  */
	public static final long UI64_DIGITS     = 8;    /* number of digits */
	public static final long UI64_DIGIT_BITS = 8;    /* number of bits in a digit */
	public static final long UI64_DIGIT_BASE = 256; /* the numerical base of a digit */

	public static final int TIME_TO_100_NSEC=10000;//1000 * 10

	public static final long TAB_0[]= {0,0,0,0,0,0,0,0};

	public static final long MUL_TAB[]= {0x58, 0x51, 0xf4, 0x2d, 0x4c, 0x95, 0x7f, 0x2d};
	public static final long INC_TAB[]= {0x14, 0x05, 0x7b, 0x7e, 0xf7, 0x67, 0x81, 0x4f};
	public static final long MASK_TAB[]= {0x00, 0x00, 0x00, 0x00, 0xff, 0xff, 0xff, 0xff};

	public static final int OUTPUT_18     = 18;
	public static final int OUTPUT_27     = 27;
	public static final int OUTPUT_59     = 59;

	public static final long [] UNIX_EPOCH_TIME_TAB= {0x01, 0xB2, 0x1D, 0xD2, 0x13, 0x81, 0x40, 0x00};


	public static final String[] OPTIONS= {"8","8","true"};

	public static final String IBITS="ibits";
	public static final String OBITS="obits";
	public static final String OBIGENDIAN="obigendian";
	public static final String IBIGENDIAN="ibigendian";


	public static long[] Z85_DECODER = {
			0x00, 0x44, 0x00, 0x54, 0x53, 0x52, 0x48, 0x00,
			0x4B, 0x4C, 0x46, 0x41, 0x00, 0x3F, 0x3E, 0x45,
			0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
			0x08, 0x09, 0x40, 0x00, 0x49, 0x42, 0x4A, 0x47,
			0x51, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2A,
			0x2B, 0x2C, 0x2D, 0x2E, 0x2F, 0x30, 0x31, 0x32,
			0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3A,
			0x3B, 0x3C, 0x3D, 0x4D, 0x00, 0x4E, 0x43, 0x00,
			0x00, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10,
			0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18,
			0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F, 0x20,
			0x21, 0x22, 0x23, 0x4F, 0x00, 0x50, 0x00, 0x00
	};

	/*  This library provides Z85: ZeroMQ's Base-85 encoding/decoding
    (see http://rfc.zeromq.org/spec:32 for details)  */

    public final static String[] Z85_ENCODER ="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.-:+=^!/*?&<>()[]{}@%$#".split("");

    public static final String SCAN_PACKAGE= "com.thinkenterprise.uuid";
    public static final String API="/uuid";
    public static final String API_GENERATE_UUID="generateUUID";





}
