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

/**
 * UUIDDto model
 *
 * @author Michael Schüfer
 * @author Ahmed Amedlous
 * @author Dr. Edgar Müller
 */
public class UUIDDto {

  private int version;
  // private String nsUrl;
  private NsUrl nsUrl;
  private String url;
  private String uuidFormat;
  private long ns[];
  private int versionSid;
  private String data;
  private String sidFormat;
  private TypeFormat typeFormatNs;
  private TypeFormat typeFormatSid;

  /** @return the version */
  public int getVersion() {
    return version;
  }

  /** @param version the version to set */
  public void setVersion(int version) {
    this.version = version;
  }

  /** @return the nsUrl */
  public NsUrl getNsUrl() {
    return nsUrl;
  }

  /** @param nsUrl the nsUrl to set */
  public void setNsUrl(NsUrl nsUrl) {
    this.nsUrl = nsUrl;
  }

  /** @return the url */
  public String getUrl() {
    return url;
  }

  /** @param url the url to set */
  public void setUrl(String url) {
    this.url = url;
  }

  /** @return the uuidFormat */
  public String getUuidFormat() {
    return uuidFormat;
  }

  /** @param uuidFormat the uuidFormat to set */
  public void setUuidFormat(String uuidFormat) {
    this.uuidFormat = uuidFormat;
  }

  /** @return the ns */
  public long[] getNs() {
    return ns;
  }

  /** @param ns the ns to set */
  public void setNs(long[] ns) {
    this.ns = ns;
  }

  /** @return the versionSid */
  public int getVersionSid() {
    return versionSid;
  }

  /** @param versionSid the versionSid to set */
  public void setVersionSid(int versionSid) {
    this.versionSid = versionSid;
  }

  /** @return the data */
  public String getData() {
    return data;
  }

  /** @param data the data to set */
  public void setData(String data) {
    this.data = data;
  }

  /** @return the sidFormat */
  public String getSidFormat() {
    return sidFormat;
  }

  /** @param sidFormat the sidFormat to set */
  public void setSidFormat(String sidFormat) {
    this.sidFormat = sidFormat;
  }

  /** @return the typeFormatSid */
  public TypeFormat getTypeFormatNs() {
    return typeFormatNs;
  }

  /** @param typeFormatNs the typeFormatNs to set */
  public void setTypeFormatNs(TypeFormat typeFormatNs) {
    this.typeFormatNs = typeFormatNs;
  }

  /** @return the typeFormatSid */
  public TypeFormat getTypeFormatSid() {
    return typeFormatSid;
  }

  /** @param typeFormatSid the typeFormatSid to set */
  public void setTypeFormatSid(TypeFormat typeFormatSid) {
    this.typeFormatSid = typeFormatSid;
  }
}
