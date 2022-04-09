package com.manyson.road.api;

import com.manyson.road.config.PropertiesData;
import com.manyson.road.constant.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 도로명 주소 API 를 사용하기 위한 클래스
 * <pre>
 *  RoadAPI roadAPI = new RoadAPI();
 *  roadAPI.searchRoadData(address);
 * </pre>
 *
 * @author  김정현
 * @version 1.0
 * @see     PropertiesData
 */
@Slf4j
public class RoadAPI {

    /**
     * 도로명 API 를 사용하기 위한 properties 정보
     * @see PropertiesData
     */
    private PropertiesData propertiesData;

    /**
     * 생성자 ,도로명 API 를 사용하기 위한 properties 정보를 loading
     */
    public RoadAPI(){
        this.propertiesData = new PropertiesData();
        this.propertiesData.loadProperties(Constants.CONFIG_API_RESOURCE);
    }

    /**
     * 주소 정보로 도로명 주소 API 호출
     * @param address API 검색에 사용되는 주소 정보
     * @return 검색 결과 데이터
     * @see RoadAPIResponse
     */
    public RoadAPIResponse searchRoadData(String address) {

        RoadAPIResponse response = new RoadAPIResponse();

        try {
            /**
             *  api.properties 을 통해 도로명 API 를 사용하기 위한 정보
             *  url : 호출 URL
             */
            String confirmKey   = this.propertiesData.getProperty(Constants.PROP_CONFIRM_KEY) ;
            String resultType   = this.propertiesData.getProperty(Constants.PROP_RESULT_TYPE);
            String urlStr       = this.propertiesData.getProperty(Constants.PROP_URL);

            // 검색어를 UTF-8로 encode
            address = URLEncoder.encode(address, StandardCharsets.UTF_8);

            // 도로명 검색 API 호출 query string 만들기
            List<String> queryList = new ArrayList<>();
            queryList.add(String.format("%s=%s",Constants.PROP_KEYWORD, address));
            queryList.add(String.format("%s=%s",Constants.PROP_CONFIRM_KEY, confirmKey));
            queryList.add(String.format("%s=%s",Constants.PROP_RESULT_TYPE, resultType));

            String queryString = String.join("&", queryList);

            // 도로명 검색 API 호출 URI 정보 만들기
            String apiUri = String.format("%s?%s", urlStr, queryString);

            // 도로명 검색 API 호출
            URL url = new URL(apiUri);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            StringBuffer stringBuffer = new StringBuffer();
            String tempString = null;

            // 도로명 검색 응답 데이터 취합
            while(true){
                tempString = bufferedReader.readLine();
                if(tempString == null) break;
                stringBuffer.append(tempString);
            }
            bufferedReader.close();

            String data = stringBuffer.toString();

            // 응답 객체에 데이터 설정
            response.setErrorCode(Constants.API_RESPONSE_SUCCESS);
            response.setData(data);
        }
        catch (Exception e){
            // 도로명 검색 API 장애시 log 와 응답 코드 설정
            log.error(e.toString());
            response.setErrorCode(Constants.API_RESPONSE_ERROR);
        }
        return response;
    }
}
