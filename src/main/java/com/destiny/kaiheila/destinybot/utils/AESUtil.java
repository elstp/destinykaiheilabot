package com.destiny.kaiheila.destinybot.utils;



import com.destiny.kaiheila.destinybot.config.RobotConfig;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public enum AESUtil {
    ;


    public static String encrypt(String src,String key,String iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(iv.getBytes("UTF-8")));
            return  Base64.getEncoder().encodeToString(cipher.doFinal(src.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String src,String key,String iv,boolean autoDecode)throws Exception {
        String decrypted = "";
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,  new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(iv.getBytes()));
            if (autoDecode){
                decrypted = new String(cipher.doFinal(Base64.getDecoder().decode(src)));
            }else{
                decrypted = new String(cipher.doFinal(src.getBytes()));
            }
        return decrypted;
    }

}