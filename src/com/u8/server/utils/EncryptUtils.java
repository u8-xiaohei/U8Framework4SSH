package com.u8.server.utils;

import com.u8.server.log.Log;

import java.nio.charset.Charset;
import java.security.MessageDigest;


public class EncryptUtils {

    public static String md5(String txt){

        return encrypt(txt, "MD5");
    }

    public static String sha(String txt){

        return encrypt(txt, "SHA");
    }

    public static  String sha1(String txt){

        return encrypt(txt, "SHA1");
    }

    private static String encrypt(String txt, String algorithName){

        if(txt == null || txt.trim().length() == 0){
            return null;
        }

        if(algorithName == null || algorithName.trim().length() == 0){
            algorithName = "MD5";
        }

        String result = null;

        try{
            MessageDigest m = MessageDigest.getInstance(algorithName);
            m.reset();
            m.update(txt.getBytes(Charset.forName("UTF-8")));
            byte[] bts = m.digest();

            return hex(bts);
        }catch (Exception e){
            Log.e("encrypt error.", e);
        }


        return null;
    }

    private static String hex(byte[] bts){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<bts.length; i++){
            sb.append(Integer.toHexString((bts[i] & 0xFF) | 0x100).substring(1,3));
        }

        return sb.toString();
    }

}
