package com.knoldus.radarservice.base;

import java.io.InputStream;


public interface CommonTestUtils {
    default InputStream readFileFromResource(String path){
        return this.getClass().getClassLoader().getResourceAsStream(path);
    }
}
