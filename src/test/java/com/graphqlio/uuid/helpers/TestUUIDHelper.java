package com.graphqlio.uuid.helpers;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.graphqlio.uuid.domain.NsUrl;
import com.graphqlio.uuid.domain.UUIDDto;
import com.graphqlio.uuid.helpers.UUIDHelper;

public class TestUUIDHelper {

	@Test
	public void testWrongVersion() {
		UUIDDto uuidDto = new UUIDDto();
		uuidDto.setVersion(6);

		Assertions.assertThrows(Exception.class, () -> {
			long[] uuidLongArray = UUIDHelper.generateUUIDLongArray(uuidDto, uuidDto.getVersion());
		});
	}

	@Test
	public void testWithoutNsUrl() throws Exception {
		UUIDDto uuidDto = new UUIDDto();
		uuidDto.setVersion(5);

		Assertions.assertThrows(NullPointerException.class, () -> {
			long[] uuidLongArray = UUIDHelper.generateUUIDLongArray(uuidDto, uuidDto.getVersion());
		});
	}

	@Test
	public void WithNsUrl() throws Exception {
		UUIDDto uuidDto = new UUIDDto();
		uuidDto.setVersion(5);
		uuidDto.setNsUrl(NsUrl.NS_URL);

		long[] uuidLongArray = UUIDHelper.generateUUIDLongArray(uuidDto, uuidDto.getVersion());
		System.out.println("uuidLongArray = " + Arrays.toString(uuidLongArray));

		Assertions.assertTrue(uuidLongArray.length == 16);
		Assertions.assertTrue(uuidLongArray[1] == 139);
		Assertions.assertTrue(uuidLongArray[12] == 72);
	}

}
