package com.destiny.kaiheila.destinybot.enumType;


/**
 * 群消息类型
 * @author qilong
 */
public enum SendMsgType {

    /**
     * 发送服务器文本信息
     */
    SendMsg("{\"type\":10,\"channel_id\":\"%s\",\"content\":\"%s\"}"),
    SendServerMessage("[{\\\"type\\\":\\\"card\\\",\\\"theme\\\":\\\"primary\\\",\\\"color\\\":\\\"#aaaaaa\\\",\\\"size\\\":\\\"lg\\\",\\\"modules\\\":[{\\\"type\\\":\\\"header\\\",\\\"text\\\":{\\\"type\\\":\\\"plain-text\\\",\\\"content\\\":\\\"%s\\\"}},{\\\"type\\\":\\\"divider\\\"},{\\\"type\\\":\\\"section\\\",\\\"mode\\\":\\\"left\\\",\\\"text\\\":{\\\"type\\\":\\\"plain-text\\\",\\\"content\\\":\\\"%s\\\",\\\"emoji\\\":true}},{\\\"type\\\":\\\"section\\\",\\\"mode\\\":\\\"right\\\",\\\"accessory\\\":{\\\"type\\\":\\\"button\\\",\\\"theme\\\":\\\"primary\\\",\\\"value\\\":\\\"%s\\\",\\\"click\\\":\\\"link\\\",\\\"text\\\":\\\"%s\\\"}}]}]");




    private final String value;

    public String getValue() {
        return value;
    }

    SendMsgType(String value) {
        this.value = value;
    }
}

