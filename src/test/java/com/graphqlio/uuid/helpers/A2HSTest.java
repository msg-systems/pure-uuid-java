/**
 * *****************************************************************************
 *
 * <p>Design and Development by msg Applied Technology Research Copyright (c) 2019-2020 msg systems
 * ag (http://www.msg-systems.com/) All Rights Reserved.
 *
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * <p>The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * <p>****************************************************************************
 */
package com.graphqlio.uuid.helpers;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.graphqlio.uuid.domain.NsUrl;
import com.graphqlio.uuid.domain.TypeFormat;
import com.graphqlio.uuid.domain.UUIDDto;

/**
 * Class testing pure-uuid
 *
 * @author Michael Schäfer
 * @author Torsten Kühnert
 */
public class A2HSTest {

  @Test
  public void whenA2HSFormatIsUsedThenUUIDIsCorrect() throws Exception {
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
