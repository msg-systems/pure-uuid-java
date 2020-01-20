package com.thinkenterprise.uuid.helpers;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thinkenterprise.uuid.domain.NsUrl;
import com.thinkenterprise.uuid.domain.TypeFormat;
import com.thinkenterprise.uuid.domain.UUIDDto;

public class TestA2HS {

	@Test
	public void testMethodFormat() throws Exception {
		UUIDDto uuidDto = new UUIDDto();
		uuidDto.setData("http://engelschall.com/ns/graphql-query");
		uuidDto.setNsUrl(NsUrl.NS_URL);
		uuidDto.setVersion(5);
		uuidDto.setTypeFormatNs(TypeFormat.STD);

		long[] uuidLongArray = UUIDHelper.generateUUIDLongArray(uuidDto, uuidDto.getVersion());
		System.out.println("uuidLongArray = " + Arrays.toString(uuidLongArray));

		Assertions.assertTrue(uuidLongArray.length == 16);
		Assertions.assertTrue(uuidLongArray[1] == 139);
		Assertions.assertTrue(uuidLongArray[12] == 72);

		String uuidFormat = A2HS.format(uuidDto.getTypeFormatNs().getTypeFormat(), uuidLongArray);
		System.out.println("uuidFormat = " + uuidFormat);

		Assertions.assertTrue("148bdfa9-7596-5319-a197-ead64880df40".equals(uuidFormat));
	}

}
