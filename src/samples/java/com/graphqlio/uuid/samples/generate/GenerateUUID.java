package com.graphqlio.uuid.samples.generate;

import com.graphqlio.uuid.domain.NsUrl;
import com.graphqlio.uuid.domain.TypeFormat;
import com.graphqlio.uuid.domain.UUIDDto;
import com.graphqlio.uuid.helpers.A2HS;
import com.graphqlio.uuid.helpers.UUIDHelper;

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
	 * code from class com.graphqlio.gts.tracking.GtsScope
	 */
	private String generateUUID(int version, NsUrl urlFormat, String url, String data) {
		try {
			UUIDDto uuidDto = new UUIDDto();
			uuidDto.setData(url);
			uuidDto.setNsUrl(urlFormat);
			uuidDto.setVersion(version);
			uuidDto.setTypeFormatNs(TypeFormat.STD);

			long[] uuid = UUIDHelper.generateUUIDLongArray(uuidDto, uuidDto.getVersion());
			String uuidFormat = A2HS.format(uuidDto.getTypeFormatNs().getTypeFormat(), uuid);

			uuidDto.setNs(uuid);
			uuidDto.setUuidFormat(uuidFormat);
			uuidDto.setData(data);
			uuidDto.setVersionSid(version);
			uuidDto.setTypeFormatSid(TypeFormat.STD);

			long[] sid = UUIDHelper.generateUUIDLongArray(uuidDto, uuidDto.getVersionSid());
			return A2HS.format(uuidDto.getTypeFormatSid().getTypeFormat(), sid);

		} catch (Exception e) {
			return null;
		}
	}

}
