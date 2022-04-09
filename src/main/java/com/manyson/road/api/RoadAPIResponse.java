package com.manyson.road.api;

import com.manyson.road.constant.Constants;
import lombok.Getter;
import lombok.Setter;

/**
 * 도로명 주소 API의 응답 데이터를 위한 클래스
 *
 * @author  김정현
 * @version 1.0
 */
@Getter
@Setter
public class RoadAPIResponse {

    /**
     * 도로명 API 응답 코드
     * @see Constants
     */
    private String errorCode    ;

    /**
     * 도로명 API 응답 데이터
     * <pre>
     *  정상 시 : API 호출 응답 데이터
     *  에러 시 : exception 메시지
     * </pre>
     */
    private String data         ;

    /**
     * 생성자 - 객체의 기본값 설정
     */
    public RoadAPIResponse(){
        this.errorCode = Constants.API_RESPONSE_ERROR;
        this.data = null ;
    }
}
