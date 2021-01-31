package com.destiny.kaiheila.destinybot.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author admin
 */
@Configuration
public class SocketIOConfig {

    private static String host;

    @Value("${web-socketio.host}")
    public  void setHost(String host) {
        SocketIOConfig.host = host;
    }
    public static String getHost() {
        return host;
    }

}
