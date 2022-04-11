# RoadFinder - 주소에서 도로명을 추출
입력된 주소에서 정규식 패턴으로는 확인 할 수 없는 도로명을 출력 하는 프로그램

## :books: 개요
* 특수 문자나 공백 등으로 주소를 정규식 패턴으로 확인 할 수 없을 때 어느 "로"("길")인지를 출력 하는 프로그램 작성
* 최대한 많은 주소를 올바른 주소인지 확인할 수 있도록 작성

## :package: 개발 환경
* **JDK 버전** - OpenJDK Runtime Environment Corretto-11.0.14.10.1
* **사용 라이브러리** - JUnit, Slf4j, logback, Lombok, json-simple
* **Open API** - 도로명주소API (https://www.juso.go.kr/addrlink/addrLinkApi.do)
* **개발 IDE** - IntelliJ IDEA 2021.3.2


## :rocket: 프로그램 실행 & 종료
* Gradle에서 실행 파일을 만들어 실행파일(RoadFinder-1.0.jar)이 위치한 경로에서 터미널에 다음과 같이 입력합니다.
```
java -jar RoadFinder-1.0.jar
```
![image](https://user-images.githubusercontent.com/18624766/160564306-9fb6a009-ef24-4aca-b049-a7b74e37dc56.png)

* 확인이 필요한 주소를 입력하고 Enter Key를 누르면 결과 값이 출력 됩니다.

![image](https://user-images.githubusercontent.com/18624766/160564361-c6344bb6-2442-46e7-870d-53f34094e893.png)

* 종료는 exit 또는 quit 입력하면 정상적으로 종료됩니다.

![image](https://user-images.githubusercontent.com/18624766/160564480-24d23a81-b855-401b-979d-913075319d0c.png)

## :hammer_and_wrench: 주요 기능 구현 설명
1. 입력된 문자열을 구분자(space)로 나누어 토큰화 합니다.
   ![image](https://user-images.githubusercontent.com/18624766/160732897-664595f0-294e-4348-844b-2c0c9bf6208e.png)

2. 토큰에서 사용할 수 있는 문자를 제외하고 제거 합니다.
+ 사용할 수 있는 문자 : 한글, 영어, 숫자, 특수문자(가운데점(•), 마침표(.))
+ 주의 해야 할 주소
    - :white_check_mark: 부산 해운대구 APEC로
    - :white_check_mark: 광주광역시 남구 3·1 만세운동길
    - :white_check_mark: 경기도 화성시 3.1만세로

3. 앞의 토큰 부터 ‘로’ 또는 ‘길’이 나올때 까지 토큰들을 결합합니다.
   ![image](https://user-images.githubusercontent.com/18624766/160732988-d1931269-e746-4b4e-81db-0c709a16d878.png)

4. 도로명 API를 호출 하여 받은 응답 데이터의 도로명과 비교 합니다.
   ![image](https://user-images.githubusercontent.com/18624766/160733084-9b38b789-e68f-4c43-a638-66659d37d25a.png)

5. 가장 긴 문자열 검색으로 매칭된 도로명을 응답으로 출력합니다.
   ![image](https://user-images.githubusercontent.com/18624766/160733155-208512e9-9a0d-4648-ba95-d24a3745e12b.png)

## :memo: JUnit 테스트
### JUnit를 사용하여 주요 기능에 대한 테스트 진행
1. src/test/com.truefriend.road.api.RoadAPITest  
   - searchRoadDataTest() : RoadAPI 클래스의 searchRoadData() 메소드 테스트
2. src/test/com.truefriend.road.config.PropertiesDataTest  
   - loadPropertiesTest() : PropertiesData 의 loadProperties(), isLoaded() 메소드 테스트  
   - getPropertyTest() : PropertiesData 의 getProperty() 메소드 테스트
3. src/test/com.truefriend.road.processor.RoadProcessorTest  
   - makeTokenTest() : RoadProcessor 의 makeToken() 메소드 테스트  
   - handleAddressTest() : RoadProcessor 의 handleAddress() 메소드 테스트

## :artificial_satellite: 특수한 주소에 대한 실행 결과
1. 프로그램을 실행합니다.
```
	java -jar RoadFinder-1.0.jar
```
2. 주소를 넣어서 확인해 주세요.
+ 성북 길 음 로 9 길 50 로 보내주세요.
+ 해 운대 구 A P~E C 로 17 센텀리 더 스 마 크 길
+ 강 남, 자-곡 로 3 길 21 로 길 L H 강 남 힐 스 테 이 트
+ 서울특별시 종로 구 종 로 5 0 다 길
+ 제 주, 서귀 포시 상모-이교로 39 번 길
+ 광주광역시 남구 3·1 만세, 운-동길

![image](https://user-images.githubusercontent.com/18624766/160564603-b9730baa-263c-498f-8e05-e21ee3c9ee50.png)

## :envelope_with_arrow: 프로젝트 소스 구성
* Package와 Class들의 설명입니다.  
  ![image](https://user-images.githubusercontent.com/18624766/160565901-893bee5d-f7d7-4c7d-b09b-1cc885b4e736.png)

* Resource 파일 들의 설명입니다.  
  ![image](https://user-images.githubusercontent.com/18624766/160565989-170f5edc-c078-457d-bbf0-075cc4460ad4.png)

## :man_in_tuxedo: Authors
- [iu00](https://github.com/manyson) - **junghyun Kim** - <artjung77@gmail.com>

<!--
See also the list of [contributors](https://github.com/iu00/readmeTemplate/contributors)
who participated in this project.

## Used or Referenced Projects
 - [referenced Project](project link) - **LICENSE** - little-bit introduce
-->


## :scroll: License [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

```
MIT License

Copyright (c) 2020 iu00

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
