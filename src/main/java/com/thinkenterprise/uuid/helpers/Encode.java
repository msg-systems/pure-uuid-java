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

import com.thinkenterprise.uuid.domain.GlobaleConstants;

/**
 * Encoder 
 *
 * @author Michael Schüfer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Müller
 */
public class Encode {
	
	
	/**
	 * decodes data in the Base85 encoding scheme Z85
	 * @param data
	 * @param size
	 * @return the result string from the Z85 encoder
	 * @throws Exception 
	 */
	public static String  getZ85Encode (long [] data,int size) throws Exception {
        if ((size % 4) != 0)
            throw new Exception("z85_encode: invalid input length (multiple of 4 expected)");
        String str = "";
        long i = 0, value = 0;
        while (i < size) {
            value = (value * 256) + data[(int) i++];
            if ((i % 4) == 0) {
                int divisor = 85 * 85 * 85 * 85;
                while (divisor >= 1) {
                    int idx = (int) (Math.floor(value / divisor) % 85);
                     
                    // sometime idx kommt negative
                    if(idx>0) {
                    	 str += GlobaleConstants.Z85_ENCODER[idx];
                    }
                   
                    divisor /= 85;
                }
                value = 0;
             }
        }
        return str;
    };

}
