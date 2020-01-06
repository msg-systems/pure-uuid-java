package com.thinkenterprise.uuid.domain;

/**
 * UUIDDto model
 *
 * @author Michael Schäfer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Müller
 */
public class UUIDDto {

	private int version;
	//	private String nsUrl;
	private NsUrl nsUrl;
	private String url;
	private String uuidFormat;
	private long ns[];
	private int versionSid;
	private String data;
	private String sidFormat;
	private TypeFormat typeFormatNs;
	private TypeFormat  typeFormatSid;


	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

	public NsUrl getNsUrl() {
		return nsUrl;
	}
	public void setNsUrl(NsUrl nsUrl) {
		this.nsUrl = nsUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUuidFormat() {
		return uuidFormat;
	}
	public void setUuidFormat(String uuidFormat) {
		this.uuidFormat = uuidFormat;
	}
	public long[] getNs() {
		return ns;
	}
	public void setNs(long[] ns) {
		this.ns = ns;
	}
	public int getVersionSid() {
		return versionSid;
	}
	public void setVersionSid(int versionSid) {
		this.versionSid = versionSid;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getSidFormat() {
		return sidFormat;
	}
	public void setSidFormat(String sidFormat) {
		this.sidFormat = sidFormat;
	}
	public TypeFormat getTypeFormatNs() {
		return typeFormatNs;
	}
	public void setTypeFormatNs(TypeFormat typeFormatNs) {
		this.typeFormatNs = typeFormatNs;
	}
	public TypeFormat getTypeFormatSid() {
		return typeFormatSid;
	}
	public void setTypeFormatSid(TypeFormat typeFormatSid) {
		this.typeFormatSid = typeFormatSid;
	}



}
