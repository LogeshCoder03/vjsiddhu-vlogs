package com.teachhub.backend.Utils;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyUtil {
    public static String generateToken(){
        byte[] bytes=new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }
//    public String generateVerificationToken(){
//        St
//    }
}
