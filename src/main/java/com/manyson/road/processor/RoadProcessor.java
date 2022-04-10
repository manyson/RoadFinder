package com.manyson.road.processor;

import com.manyson.road.api.RoadAPI;
import com.manyson.road.api.RoadAPIResponse;
import com.manyson.road.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 주소를 입력 받아서 도로명 주소를 찾기 위한 클래스
 * <pre>
 *     RoadProcessor roadProcessor = new RoadProcessor();
 *     roadProcessor.process();
 * </pre>
 *
 * @author  김정현
 * @version 1.0
 * @see     RoadAPI
 */
@Slf4j
public class RoadProcessor {

    /**
     * 도로명 API 를 사용하기 위한 객체
     * @see RoadAPI
     */
    private final RoadAPI roadAPI ;

    /**
     * 생성자 - 객체 생성
     */
    public RoadProcessor(){
        this.roadAPI         = new RoadAPI();
    }

    /**
     * 사용자로 부터 입력 받는 메소드
     * @return 사용자로 부터 받은 입력 문자열
     */
    public String getInputAddress(){
        System.out.print("입력 : ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * 입력된 문자열을 token 배열로 분리
     * @param data token 으로 분리 하기 위한 문자열
     * @return 분리된 token 문자열 배열
     */
    public String[] makeToken(String data){
        String[] token = data.split(Constants.TOKEN_SPRIT_REGEX);
        if(token.length == 0){
            return null;
        }
        return token;
    }

    /**
     * token 배열을 사용하여 최대한 얻을 수 있는 도로명을 조회 한다.
     * @param tokens 입력된 주소를 분리한 token 배열
     * @return 조회한 도로명 or 에러메시지
     */
    public String findRoad(String[] tokens) {

        StringBuilder stringBuilder     = new StringBuilder();

        // API 를 호출 하기 위한 현재 주소 문자열 정보
        String        currentString;

        // 입력한 도로명 주소가 API 도로명 주소와 일치했을 때 도로명 주소(최대 일치 값)
        String        responseString    = null;

        for (String token: tokens) {

            /*
             *  token 데이터에 대한 처리
             *  한글(가-힣), 영어(A-Za-z), 숫자(d), 툭수문자(·가운데점, .점) 제외하고 나머지는 "" 치환
             *  영어문자 가능 : 부산광역시 해운대구 APEC 로
             *  특수문자 가능 : 4.19(점), 3·1만세로(가운데점)
             *  regEX : [^가-힣A-Za-z·\d\.]
             */
            token = token.replaceAll(Constants.PATTERN_FILTER_STRING, Constants.EMPTY_WORD);

            // 해당 토큰을 StringBuilder 에 추가
            stringBuilder.append(token);

            // 현재 StringBuilder 에 있는 내용으로 문자열 만들기
            currentString = stringBuilder.toString();

            // 만약 '로' 또는 '길'로 끝나는 두자리 이상 주소일 때는 API 호출
            Pattern pattern = Pattern.compile(Constants.PATTERN_FIND_STRING);
            Matcher matcher = pattern.matcher(currentString);

            if (matcher.find()) {

                /*
                 * API 호출
                 * 도로명 주소를 찾은 경우에만 최신 값으로 업데이트 => 가장 긴 주소 값과 도로명이 일치되는 값으로 변경
                 */
                RoadAPIResponse response = this.callRoadAPI(currentString);

                switch (response.getErrorCode()){
                    case Constants.API_RESPONSE_SUCCESS:
                        if(response.getData() != null){
                            responseString = response.getData();
                        }
                        break;
                    case Constants.API_RESPONSE_ERROR:
                        responseString = Constants.ERROR_API_CALL;
                        break;
                    case Constants.API_RESPONSE_PARSE_ERROR:
                        responseString = Constants.ERROR_PARSE_JSON;
                        break;
                }
            }
        }

        return responseString ;
    }

    /**
     * 주소 문자열로 도로명 API 를 호출하고 응답 데이터에서 도로명에 대해서만 추출
     * @param searchData 도로명을 조회하기 위한 주소 문자열
     * @return 도로명 조회 API 를 호출 결과 정보
     * @see RoadAPIResponse
     */
    public RoadAPIResponse callRoadAPI(String searchData) {

        // 매칭된 도로명 주소
        String  matchingRoad    = null;

        // 조회하는 문자열의 길이
        int searchDataLength    = searchData.length();

        RoadAPIResponse response        = this.roadAPI.searchRoadData(searchData);

        // 응답 코드가 Error 인 경우에는 JSON parse 를 할 필요 없음
        if(response.getErrorCode().equals(Constants.API_RESPONSE_ERROR)){
            return response;
        }

        try {
            JSONParser jsonParse    = new JSONParser();
            JSONObject jsonObj      = (JSONObject)jsonParse.parse(response.getData());

            JSONObject results      = (JSONObject)jsonObj.get(Constants.JSON_ATTR_RESULTS)    ;

            JSONObject common       = (JSONObject)results.get(Constants.JSON_ATTR_COMMON)     ;

            String errorCode        = (String)common.get(Constants.JSON_ATTR_ERROR_CODE)  ;
            String errorMessage     = (String)common.get(Constants.JSON_ATTR_ERROR_MSG)   ;

            // 정상 응답을 받은 경우에만 도로명 주소 체크 ( errorCode == "0" )
            if(errorCode.equals(Constants.JSON_VALUE_SUCCESS)){

                JSONArray juso    = (JSONArray)results.get(Constants.JSON_ATTR_JUSO);

                for (Object dataRow: juso) {
                    if ( dataRow instanceof JSONObject ) {
                        JSONObject roadRow = (JSONObject) dataRow;

                        // 응답 받은 도로명 주소
                        String roadName = (String) roadRow.get(Constants.JSON_ATTR_ROAD_NAME);

                        // 응답 받은 도로명 주소 길이
                        int roadLength  = roadName.length();

                        // 조회하는 문자열의 길이는 도로명 주소보다 길어야 함.
                        if(searchDataLength >= roadLength){
                            // 조회하는 문자열에서 응답 받은 도로명 주소 길이 만큼 뒤에서 추출
                            String searchRoadStr = searchData.substring(searchDataLength-roadLength);

                            // 조회한 문자열과 응답받은 도로명 주소가 같을 때 매칭되고 응답 처리
                            if(searchRoadStr.equals(roadName)){
                                matchingRoad = roadName;
                                break;
                            }
                        }
                    }
                }
            }
            else{
                response.setData(errorMessage);
            }
        }
        catch (ParseException e){
            log.error(e.toString());
            response.setErrorCode(Constants.API_RESPONSE_PARSE_ERROR);
        }

        response.setData(matchingRoad);
        return response;
    }

    /**
     * 입력된 주소로 도로명을 찾기 위한 처리
     * @param address 주소 문자열
     * @return 도로명 또는 에러메시지
     */
    public String handleAddress(String address){

        // 입력 값이 없는 경우
        if(address.trim().equals(Constants.EMPTY_WORD)){
            System.out.println(Constants.WARN_EMPTY_STRING);
            return null;
        }

        // 입력 문자열에 대하여 구분자 space 로 token 처리
        String[] token = this.makeToken(address);
        if(token == null){
            System.out.println(Constants.WARN_NO_TOKEN);
            return null;
        }

        // token 배열로 도로명 주소 조회
        return this.findRoad(token);
    }

    /**
     * 프로그램 시작 시 사용자의 입력을 받기 전 정보 출력
     */
    public void preProcess(){
        System.out.println("*******************************************************************");
        System.out.println("** 주소가 어느 \"로\"(\"길\")인지 확인하는 프로그램 입니다. \t **");
        System.out.println("** 확인을 원하는 주소를 입력해 주세요                     \t **");
        System.out.println("** (종료를 원하면 exit 또는 quit 를 입력하세요)          \t **");
        System.out.println("-------------------------------------------------------------------");
    }

    /**
     * 도로명 찾기 처리 과정
     */
    public void process(){

        // 선행 처리
        this.preProcess();

        while (true){

            // 터미널 입력 데이터
            String address = this.getInputAddress();

            // 종료(exit, quit) 단어가 입력되었으면 종료 처리
            if(Arrays.asList(Constants.EXIT_WORD).contains(address)){
                break;
            }

            String responseData = this.handleAddress(address);

            if(responseData == null){
                System.out.println(Constants.WARN_NO_ROAD_NAME);
            }
            else{
                System.out.println(String.format("출력 : %s", responseData));
            }
        }

        this.postProcess();
    }

    /**
     *  프로그램 종료시 정보 출력
     */
    public void postProcess(){
        System.out.println("** 정상적으로 종료되었습니다. 감사합니다.                  \t **");
        System.out.println("*******************************************************************");
    }
}
