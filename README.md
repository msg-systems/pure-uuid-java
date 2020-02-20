# About
pure-uuid-java is the **Java** implementation of the **JavaScript** pure-uuid reference implementation. 
The documentation of the reference implementation can be found at [Project Github] (https://github.com/rse/pure-uuid). 

 
# Installation
## Maven
   
```
mvn clean install
```

# Usage 

## Maven 
```
<dependency>
	<groupId>com.graphqlio</groupId>
	<artifactId>pure-uuid-java</artifactId>
	<version>0.0.9</version>
</dependency>

```

## Gradle 

```
dependencies {
  compile 'com.graphqlio:pure-uuid-java:0.0.9'
}
```


# Sample 

Working with UUIDDto

``` java
		UUIDDto uuidDto = new UUIDDto();
		uuidDto.setVersion(4);
		uuidDto.setVersionSid(15);
		uuidDto.setData("http://engelschall.com/ns/graphql-query");
		uuidDto.setNs(new long[] {});
		uuidDto.setNsUrl(NsUrl.NS_X500);
```

Working with UUIDHelper

``` java
		long[] uuid = UUIDHelper.generateUUIDLongArray(uuidDto, uuidDto.getVersion());
```

Working with A2HS

``` java
		String uuidFormat = A2HS.format(uuidDto.getTypeFormatNs().getTypeFormat(), uuid);
```


# License 
Design and Development by msg Applied Technology Research
Copyright (c) 2019-2020 msg systems ag (http://www.msg-systems.com/)
All Rights Reserved.
 
Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:
 
The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.
 
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
