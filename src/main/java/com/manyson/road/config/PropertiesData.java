package com.manyson.road.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * properties 파일의 정보를 사용하기 위한 클래스
 *
 * @author  김정현
 * @version 1.0
 * @see     Properties
 */
@Slf4j
public class PropertiesData {

    /**
     * properties 파일 정보 객체
     * @see Properties
     */
    private Properties  properties  = null  ;

    /**
     * properties 파일의 loading 여부
     */
    private boolean     loaded      = false ;

    /**
     * 생성자 - 객체의 기본값 설정
     */
    public PropertiesData(){
        this.properties = new Properties() ;
    }

    /**
     * 파일 경로 정보로 properties 파일을 loading
     * @param filePath properties 파일의 경로 정보
     */
    public void loadProperties(String filePath){

        try {
            // src/main/resources 하위에 있는 파일을 사용
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);

            if (inputStream != null) {
                // properties 객체에 load
                this.properties.load(inputStream);
                // 정상 로딩
                this.loaded = true;
            }
        }
        catch (IOException ioException){
            log.error(ioException.getMessage());
            this.loaded = false;
        }
    }

    /**
     *  properties 파일의 로딩 여부 (true, false)
     * @return boolean
     */
    public boolean isLoaded(){
        return this.loaded;
    }

    /**
     * properties 파일의 정보에서 원하는 key에 대한 value 조회
     * @param key properties 의 값을 얻기 위한 key
     * @return properties 에서 조회된 value 정보
     */
    public String getProperty(String key){
        return this.properties.getProperty(key, null);
    }
}
