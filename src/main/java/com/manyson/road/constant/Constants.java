package com.manyson.road.constant;

/**
 * 상수(Constant) 정보 클래스
 *
 * @author  김정현
 * @version 1.0
 */
public class Constants {

    /** properties 파일의 경로 정보 */
    public static final String      CONFIG_API_RESOURCE         =   "config/api.properties" ;

    /** properties URL 정보 */
    public static final String	    PROP_URL                    =   "url"                   ;

    /** 도로명 검색 api keyword 속성 */
    public static final String      PROP_KEYWORD                =   "keyword"               ;

    /** 도로명 검색 api confmKey 속성 */
    public static final String      PROP_CONFIRM_KEY            =   "confmKey"              ;

    /** 도로명 검색 api resultType 속성 */
    public static final String      PROP_RESULT_TYPE            =   "resultType"            ;

    /** 입력 값을 토큰으로 나누기 위한 separator */
    public static final String      TOKEN_SPRIT_REGEX           =   " "                     ;

    /** 프로그램 종료을 위한 문자열 종류 */
    public static final String[]    EXIT_WORD                   =   {"exit", "quit"}        ;

    /** 공백 문자 constant */
    public static final String      EMPTY_WORD                  =   ""                      ;

    /** 주소에서 사용되지 않는 문자를 제외하기 위한 정규식  */
    public static final String      PATTERN_FILTER_STRING       =   "[^가-힣A-Za-z·\\d\\.]"  ;

    /** '로' 또는 '길'로 끝나는 문자열을 찾기 위한 정규식  */
    public static final String      PATTERN_FIND_STRING         =   ".+(로|길)$"             ;

    /** 입력 주소가 없을 때 출력하는 메시지  */
    public static final String      WARN_EMPTY_STRING           =   "[WARN] 입력된 값이 없습니다.";

    /** 입력 주소가 토큰으로 나눌 수 없을 때 출력하는 메시지  */
    public static final String      WARN_NO_TOKEN               =   "[WARN] 입력된 값을 분석할 수 없습니다.";

    /** 입력 주소로 도로명을 찾을 수 없을 때 출력하는 메시지  */
    public static final String      WARN_NO_ROAD_NAME           =   "[WARN] 도로명을 찾을 수 없습니다.";

    /** 도로명 검색 API 호출 에러가 발생하였을 때 출력하는 메시지  */
    public static final String      ERROR_API_CALL              =   "[ERROR] 도로명 검색 API 호출 에러압니다.";

    /** 도로명 검색 API 호출 응답데이터의 parsing 에러가 발생하였을 때 출력하는 메시지  */
    public static final String      ERROR_PARSE_JSON            =   "[ERROR] 응답 데이터의 PARSE 에러입니다.";

    /** 도로명 검색 API 호출 응답데이터의 'results' 속성 키 */
    public static final String      JSON_ATTR_RESULTS           =   "results"        ;

    /** 도로명 검색 API 호출 응답데이터의 'common' 속성 키  */
    public static final String      JSON_ATTR_COMMON            =   "common"         ;

    /** 도로명 검색 API 호출 응답데이터의 'errorMessage' 속성 키  */
    public static final String      JSON_ATTR_ERROR_MSG         =   "errorMessage"   ;

    /** 도로명 검색 API 호출 응답데이터의 'errorCode' 속성 키  */
    public static final String      JSON_ATTR_ERROR_CODE        =   "errorCode"      ;

    /** 도로명 검색 API 호출 응답데이터의 'errorCode' 정상 값  */
    public static final String      JSON_VALUE_SUCCESS          =   "0"              ;

    /** 도로명 검색 API 호출 응답데이터의 'juso' 속성 키  */
    public static final String      JSON_ATTR_JUSO              =   "juso"           ;

    /** 도로명 검색 API 호출 응답데이터의 'rn'(도로명 주소) 속성 키  */
    public static final String      JSON_ATTR_ROAD_NAME         =   "rn"             ;

    /** 도로명 검색 API 응답 처리가 정상일 경우 사용되는 코드  */
    public static final String      API_RESPONSE_SUCCESS        =   "0"              ;

    /** 도로명 검색 API 응답 처리가 에러일 경우 사용되는 코드  */
    public static final String      API_RESPONSE_ERROR          =   "1"              ;

    /** 도로명 검색 API 응답 데이터가 parsing 에러일 경우 사용되는 코드  */
    public static final String      API_RESPONSE_PARSE_ERROR    =   "2"              ;

    /** Test 파일을 사용할 때, 입력값과 결과값을 구별하기 위한 상수 */
    public static final String      TEST_ADDRESS_DELIMIT        =   "####"           ;

    /** Test 파일을 사용할 때, 입력값을 위한 index */
    public static final int         TEST_ADDRESS_INPUT_INDEX    =   0                ;

    /** Test 파일을 사용할 때, 결과값을 위한 index */
    public static final int         TEST_ADDRESS_RESULT_INDEX   =   1                ;
}
