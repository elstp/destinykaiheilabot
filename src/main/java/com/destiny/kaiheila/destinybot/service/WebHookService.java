package com.destiny.kaiheila.destinybot.service;

import com.alibaba.fastjson.JSONObject;


/**
 * @author Administrator
 */
public interface WebHookService {
    /**
     * 实现接口
     * @param jsonObject
     * @return
     */
     JSONObject webHook( JSONObject jsonObject);

}
