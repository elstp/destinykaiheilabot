package com.destiny.kaiheila.destinybot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author qilong
 */
@Configuration
public class RobotConfig {

    private static String Token;
    private static String arma3Server;
    private static String encryptKey;
    private static String verifToken;
    private static String iv;

    public static String getIv() {
        return iv;
    }

    @Value("${robot.iv}")
    public  void setIv(String iv) {
        RobotConfig.iv = iv;
    }

    public static String getVerifToken() {
        return verifToken;
    }

    @Value("${robot.veriftoken}")
    public  void setVerifToken(String verifToken) {
        RobotConfig.verifToken = verifToken;
    }

    public static String getEncryptKey() {
        return encryptKey;
    }

    @Value("${robot.encryptKey}")
    public  void setEncryptKey(String encryptKey) {
        RobotConfig.encryptKey = encryptKey;
    }

    public static String getToken() {
        return Token;
    }

    @Value("${robot.token}")
    public void setToken(String token) {
        Token = token;
    }

    public static String getArma3Server() {
        return arma3Server;
    }

    @Value("${robot.armaserver}")
    public  void setArma3Server(String arma3Server) {
        RobotConfig.arma3Server = arma3Server;
    }
}
