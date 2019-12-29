package com.pp.util;

import java.io.InputStream;

public class Resources {
    public static InputStream getResourceAsStream(String path){
        return Resources.class.getResourceAsStream(path);
    }
}
