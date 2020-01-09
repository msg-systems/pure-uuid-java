package com.thinkenterprise.uuid.helpers;

import com.thinkenterprise.uuid.domain.GlobaleConstants;

/**
 * ParseUUID helps to parse UUID from Text. 
 *
 * @author Michael Schaefer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Mueller
 */
public class Encode {
	
	
	/**
	 * 
	 * @param data
	 * @param size
	 * @return
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
