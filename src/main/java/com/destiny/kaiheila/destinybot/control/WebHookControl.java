package com.destiny.kaiheila.destinybot.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.destiny.kaiheila.destinybot.service.WebHookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author qilong
 */

@RestController
public class WebHookControl {

    @Resource
    private WebHookService webHookService;

    @PostMapping(value = "/webhook",produces = "application/json;charset=UTF-8")
    public JSONObject webHook(@RequestBody JSONObject jsonObject){
        return webHookService.webHook(jsonObject);
    }

}
