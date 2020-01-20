package com.thinkenterprise.uuid.samples.generate;

import com.thinkenterprise.uuid.domain.NsUrl;
import com.thinkenterprise.uuid.domain.TypeFormat;
import com.thinkenterprise.uuid.domain.UUIDDto;
import com.thinkenterprise.uuid.helpers.A2HS;
import com.thinkenterprise.uuid.helpers.UUIDHelper;

public class GenerateUUID {

	private String variables = "a, b, c, d, e";
	private String query = "query { routes { id departure destinantion} }";

	public static void main(String[] args) {
		new GenerateUUID().runSample();
	}

	public void runSample() {
		String uuid = generateUUID(5, NsUrl.NS_URL, "http://engelschall.com/ns/graphql-query",
				this.variables + this.query);
		System.out.println("uuid = " + uuid);
	}

	/*
	 * code from class com.thinkenterprise.gts.tracking.GtsScope
	 */
	private String generateUUID(int version, NsUrl urlFormat, String url, String data) {
		UUIDDto uUUIDOptions = new UUIDDto();
		uUUIDOptions.setData(url);
		uUUIDOptions.setNsUrl(urlFormat);
		uUUIDOptions.setVersion(version);
		uUUIDOptions.setTypeFormatNs(TypeFormat.STD);

		try {
			long[] uuid = UUIDHelper.generateUUIDLongArray(uUUIDOptions, uUUIDOptions.getVersion());
			String uuidFormat = A2HS.format(uUUIDOptions.getTypeFormatNs().getTypeFormat(), uuid);

			uUUIDOptions.setNs(uuid);
			uUUIDOptions.setUuidFormat(uuidFormat);
			uUUIDOptions.setData(data);
			uUUIDOptions.setVersionSid(version);
			uUUIDOptions.setTypeFormatSid(TypeFormat.STD);

			long[] sid = UUIDHelper.generateUUIDLongArray(uUUIDOptions, uUUIDOptions.getVersionSid());
			return A2HS.format(uUUIDOptions.getTypeFormatSid().getTypeFormat(), sid);

		} catch (Exception e) {
			return null;
		}
	}

}
