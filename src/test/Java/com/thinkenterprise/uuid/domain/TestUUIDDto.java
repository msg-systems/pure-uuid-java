package com.thinkenterprise.uuid.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestUUIDDto {

	@Test
	public void testSimpleValues() {
		UUIDDto uuidDto = new UUIDDto();
		uuidDto.setVersion(6);

		Assertions.assertTrue(uuidDto.getVersion() == 6);
		Assertions.assertTrue(uuidDto.getVersionSid() == 0);
		Assertions.assertTrue(uuidDto.getNs() == null);
		Assertions.assertTrue(uuidDto.getNsUrl() == null);
	}

	@Test
	public void testOtherValues() {
		UUIDDto uuidDto = new UUIDDto();
		uuidDto.setVersion(4);
		uuidDto.setVersionSid(15);
		uuidDto.setNs(new long[] {});
		uuidDto.setNsUrl(NsUrl.NS_X500);

		Assertions.assertTrue(uuidDto.getVersion() == 4);
		Assertions.assertTrue(uuidDto.getVersionSid() == 15);
		Assertions.assertTrue(uuidDto.getNs() != null);
		Assertions.assertTrue(uuidDto.getNsUrl() != null);
	}

}
