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
package com.graphqlio.uuid.samples.generate;

import com.graphqlio.uuid.domain.NsUrl;
import com.graphqlio.uuid.domain.TypeFormat;
import com.graphqlio.uuid.domain.UUIDDto;
import com.graphqlio.uuid.helpers.A2HS;
import com.graphqlio.uuid.helpers.UUIDHelper;

/**
 * Class showing use of pure-uuid
 *
 * @author Michael Schäfer
 * @author Torsten Kühnert
 */
public class GenerateUUID {

  private String variables = "a, b, c, d, e";
  private String query = "query { routes { id departure destinantion} }";

  public static void main(String[] args) {
    new GenerateUUID().runSample();
  }

  public void runSample() {
    String uuid =
        generateUUID(
            5,
            NsUrl.NS_URL,
            "http://engelschall.com/ns/graphql-query",
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
