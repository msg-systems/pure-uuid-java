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
package com.graphqlio.uuid.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Class testing pure-uuid
 *
 * @author Michael Schäfer
 * @author Torsten Kühnert
 */
public class UUIDDtoTest {

  @Test
  public void whenUUIDDtoIsCreatedThenOnlyIntFieldsAreSet() {
    UUIDDto uuidDto = new UUIDDto();

    Assertions.assertTrue(uuidDto.getVersion() == 0);
    Assertions.assertTrue(uuidDto.getNsUrl() == null);
    Assertions.assertTrue(uuidDto.getUrl() == null);
    Assertions.assertTrue(uuidDto.getUuidFormat() == null);
    Assertions.assertTrue(uuidDto.getNs() == null);
    Assertions.assertTrue(uuidDto.getVersionSid() == 0);
    Assertions.assertTrue(uuidDto.getSidFormat() == null);
    Assertions.assertTrue(uuidDto.getTypeFormatNs() == null);
    Assertions.assertTrue(uuidDto.getTypeFormatSid() == null);
  }

  @Test
  public void whenUUIDDtoSettersAreUsedThenFieldsAreSetCorrect() {
    UUIDDto uuidDto = new UUIDDto();
    uuidDto.setVersion(6);
    uuidDto.setVersionSid(-15);
    uuidDto.setNs(new long[] {34L, 15L, 26262L});
    uuidDto.setNsUrl(NsUrl.NS_X500);

    Assertions.assertTrue(uuidDto.getVersion() == 6);
    Assertions.assertTrue(uuidDto.getVersionSid() == -15);
    Assertions.assertTrue(uuidDto.getNs() != null);
    Assertions.assertTrue(uuidDto.getNs().length == 3);
    Assertions.assertTrue(uuidDto.getNsUrl() == NsUrl.NS_X500);
  }
}
