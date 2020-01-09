package com.thinkenterprise.uuid.helpers;

import com.thinkenterprise.uuid.domain.TypeFormat;

/**
 * ParseUUID helps to parse UUID from Text. 
 *
 * @author Michael Schaefer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Mueller
 */
public class A2HS {
	
	/*  API method: format UUID into usual textual representation  */
	   public static String format(String type, long uuid[]) throws Exception {
	        String str = "";
	        String arr[];
	        if (type ==TypeFormat.Z85.getTypeFormat()) {
	            str =Encode.getZ85Encode(uuid, 16);
	        	 }
	        else if (type ==TypeFormat.B16.getTypeFormat()) {
	            arr = new String[32];
	             getA2hs(uuid, 0, 15, true, arr, 0);
	             // Convert Array to String
	             StringBuilder sbf = new StringBuilder(); 
	             for (int i = 0; i < arr.length; i++) {
	            	 if(arr[i]!=null) {
	            		 sbf.append(arr[i]);  
	            	 }
				}
	             
	            str =sbf.toString();
	        }
	        else if (type==TypeFormat.VIDE.getTypeFormat()|| type ==TypeFormat.STD.getTypeFormat()) {
	        	arr = new String[32];
	        	getA2hs(uuid,  0,  3, false, arr,  0); arr[ 8] = "-";
	        	getA2hs(uuid,  4,  5, false, arr,  9); arr[13] = "-";
	        	getA2hs(uuid,  6,  7, false, arr, 14); arr[18] = "-";
	        	getA2hs(uuid,  8,  9, false, arr, 19); arr[23] = "-";
	        	getA2hs(uuid, 10, 15, false, arr, 24);
	            str = convertTabToString(arr);
	        }
	        return str;
	    }
	   
	   /*  array to hex-string conversion  */
	    private static String[]  getA2hs(long[] bytes, int begin, int end, boolean uppercase,String[] str,int pos) {
	       
	        for (int i = begin; i <= end; i++)
	        	if(mkNum(bytes[i], uppercase)!=null) {
	        		 str[pos++] = mkNum(bytes[i], uppercase);
	        	}
	           
	        return str;
	    }
	    private static String  mkNum (long num, boolean uppercase) {
            String base16 = Long.toHexString(num); //num.toString(16);
            if (base16.length() < 2)
                base16 = "0" + base16;
            if (uppercase)
                base16 = base16.toUpperCase();
            return base16;
        }
	    
	    /**
	     * 
	     * @param arr
	     * @return
	     */
	    private static String convertTabToString( String arr[]) {
	    	String tabToString="";
	    	
	    	for (int i = 0; i < arr.length; i++) {
	    		if(arr[i]!=null) {
	    			tabToString=tabToString+arr[i];
	    		}
				
			}
	    	return tabToString;
	    }

}
