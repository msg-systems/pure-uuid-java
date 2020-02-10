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

import java.util.Date;

import com.graphqlio.uuid.domain.GlobaleConstants;
import com.graphqlio.uuid.domain.UUIDDto;

/**
 * Helper to create UUID Table.
 *
 * @author Michael Schüfer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Müller
 */
public class UUIDHelper {

  public static long[] generateUUIDLongArray(UUIDDto uUUIDDto, int version) throws Exception {

    return createUUIDByVersion(uUUIDDto, version);
  }

  /**
   * Create UUID table supported versions: 1, 3, 4 or 5 actually, same implementation for 3 and 5
   *
   * @param version
   * @return Nthing
   * @throws Exception
   */
  private static long[] createUUIDByVersion(UUIDDto uUIDDto, int version) throws Exception {

    long timeLast = 0;
    long timeSeq = 0;
    long[] uuidTab = new long[16];
    long cuurentTime = new Date().getTime();

    if (version == 1) {

      timeSeq = timeLast != cuurentTime ? 0 : timeSeq++;

      timeLast = cuurentTime;

      /* Konvertiert Zeit zu 100*nsec */

      long[] timeConverted = UI64.getUi64N2i(GlobaleConstants.TAB_0, cuurentTime);

      //		long carryMuln=UI64Common.getUi64Muln(timeConverted, GlobaleConstants.TIME_TO_100_NSEC);
      UI64Common.getUi64Muln(timeConverted, GlobaleConstants.TIME_TO_100_NSEC);

      /* adjust for offset between UUID and Unix Epoch time */
      // long [] unix_epoch_time_tab= {0x01, 0xB2, 0x1D, 0xD2, 0x13, 0x81, 0x40,
      // 0x00};
      //		long carry=UI64Common.getUi64Add(timeConverted, GlobaleConstants.UNIX_EPOCH_TIME_TAB);
      UI64Common.getUi64Add(timeConverted, GlobaleConstants.UNIX_EPOCH_TIME_TAB);

      /*
       * compensate for low resolution system clock by adding the time/tick sequence
       * counter
       */
      int time_tick = 0;
      if (timeSeq > 0)
        time_tick =
            UI64Common.getUi64Add(timeConverted, UI64.getUi64N2i(GlobaleConstants.TAB_0, timeSeq));

      /* store the 60 LSB of the time in the UUID */
      uuidTab = UI64Common.getStoreLSBinUUID(timeConverted, 8);

      /* generate a random clock sequence */
      long storeState[] = new long[8];
      long clock[] = PcgPrng.getPrngClock(2, 255, storeState);
      uuidTab[8] = clock[0];
      uuidTab[9] = clock[1];

      /* generate a random local multicast node address */
      long node[] = PcgPrng.getPrngLocal(6, 255, storeState);
      node[0] |= 0x01;
      node[0] |= 0x02;
      for (int i = 0; i < 6; i++) uuidTab[10 + i] = node[i];

    } else if (version == 4) {
      long storeState[] = new long[8];
      long node[] = PcgPrng.getPrngClock(16, 255, storeState);
      uuidTab = node;

    } else if (version == 3 || version == 5) {

      /* generate UUID version 3/5 (MD5/SHA-1 based) */

      String input = "";
      long[] nsUUID = {};
      String argument2 = ""; // Es wird data oder url als Wert anehmen.
      if (uUIDDto.getNs() == null) {
        String type = null;
        nsUUID = ParseUUID.getParse(uUIDDto.getNsUrl().getNsUrl(), type);
        argument2 = uUIDDto.getUrl();
      } else {
        nsUUID = uUIDDto.getNs();
        argument2 = uUIDDto.getData();
      }
      for (int i = 0; i < nsUUID.length; i++) {
        String strFromNumber = new String(new char[] {(char) nsUUID[i]});
        input += strFromNumber;
      }

      input += argument2;

      String s = version == 3 ? Md5.md5(input) : SHA1.getSHA1(input);
      for (int i = 0; i < 16; i++) {
        int value = (int) s.charAt(i);
        uuidTab[i] = value;
      }
    } else throw new Exception("UUID: make: invalid version");

    /* brand with particular UUID version */
    uuidTab[6] &= 0x0F;
    uuidTab[6] |= (version << 4);

    /* brand as UUID variant 2 (DCE 1.1) */
    uuidTab[8] &= 0x3F;
    uuidTab[8] |= (0x02 << 6);

    return uuidTab;
  }
}
