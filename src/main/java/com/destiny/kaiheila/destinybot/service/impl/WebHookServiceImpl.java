package com.destiny.kaiheila.destinybot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.destiny.kaiheila.destinybot.config.RobotConfig;
import com.destiny.kaiheila.destinybot.service.WebHookService;
import com.destiny.kaiheila.destinybot.utils.robotUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author qilong
 */
@Service
@Slf4j
public class WebHookServiceImpl implements WebHookService {
public static String SN = "";
    @Override
    public JSONObject webHook(JSONObject jsonObject) {
        if (null==jsonObject){
            return null;
        }

        String encrypt = jsonObject.getString("encrypt");
        JSONObject results = new JSONObject();
        try {
            String decryptData = robotUtil.decryptData(encrypt);
            JSONObject decryptDataJson = JSON.parseObject(decryptData);
            //验证是否来自官方请求
            if (!RobotConfig.getVerifToken().equals( decryptDataJson.getJSONObject("d").getString("verify_token"))){
                return null;
            }
            log.debug("事件信息:{}",decryptDataJson);
            switch (decryptDataJson.getInteger("s")){
                case 0:
                    //验证请求
                  JSONObject wv =  webhookValidation(decryptDataJson);
                   if ( null != wv){
                       return wv;
                   }
                    //处理频道聊天事件消息
                    webhookGroupMsg(decryptDataJson);
                    break;
                default:
                    log.info("输出:{}",decryptDataJson.toJSONString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return results;
    }

    /**
     * 验证加密请求
     * @param object
     * @return
     */
    static JSONObject webhookValidation (JSONObject object){
        if ("WEBHOOK_CHALLENGE".equals(object.getJSONObject("d").getString("channel_type"))){
            String challenge = object.getJSONObject("d").getString("challenge");
            log.info("收到加密的验证请求:{}",challenge);
            JSONObject results = new JSONObject();
            results.put("challenge",challenge);
            return results;
        }else{
            return null;
        }
    }

    /**
     * 处理聊天消息
     * @param object
     */
    static void webhookGroupMsg(JSONObject object){
        if ("GROUP".equals(object.getJSONObject("d").getString("channel_type"))){
            if (!SN.equals(object.getString("sn"))){
                SN = object.getString("sn");
                robotUtil.processMsg(object);
            }

        }
    }

}
