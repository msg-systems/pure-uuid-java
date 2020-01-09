package com.thinkenterprise.uuid.domain;

/**
 * Enum propose List of ns URL. 
 *
 * @author Michael Schaefer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Mueller
 * 
 */
public enum NsUrl {
	NIL("nil"),
	NS_DNS("ns:DNS"),
	NS_URL("ns:URL"),
	NS_OID("ns:OID"),
	NS_X500("ns:X500");

	private final String nsUrl;

	private NsUrl(String nsUrl) {
		this.nsUrl = nsUrl;
	}

	public String getNsUrl() {
		return nsUrl;
	}	

}
