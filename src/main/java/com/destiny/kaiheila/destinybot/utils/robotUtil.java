package com.destiny.kaiheila.destinybot.utils;

import com.alibaba.fastjson.JSONObject;
import com.destiny.kaiheila.destinybot.SteamServerQuery.SteamServerInfo;
import com.destiny.kaiheila.destinybot.SteamServerQuery.SteamServerPlayer;
import com.destiny.kaiheila.destinybot.SteamServerQuery.SteamServerQuery;
import com.destiny.kaiheila.destinybot.config.RobotConfig;
import com.destiny.kaiheila.destinybot.config.SocketIOConfig;
import com.destiny.kaiheila.destinybot.enumType.SendMsgType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.util.DigestUtils;

import java.net.UnknownHostException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author qilong
 */
@Slf4j
public class robotUtil {

    private static String staticServerList= null;

    /**
     * 加密数据
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptData(String data)  {
        String encrypt = AESUtil.encrypt(data, RobotConfig.getEncryptKey(), RobotConfig.getIv());
        return encrypt;
    }

    /**
     * 解密数据
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryptData(String data) throws Exception {
        String decodeStr = new String(Base64.getDecoder().decode(data),"utf-8");
        String iv = decodeStr.substring(0,16);
        char[] chars = new char[32];
        for (int i = 0; i<  RobotConfig.getEncryptKey().length(); i++) {
            chars[i]= RobotConfig.getEncryptKey().charAt(i);
        }
        String key = new String(chars);
        String decode = AESUtil.decrypt(decodeStr.substring(16),key , iv,true);
      return decode;
    }


    /**
     * 处理消息
     * @param msg
     */
    public static void processMsg(JSONObject msg) {
        String content = msg.getJSONObject("d").getString("content");
        String targetId = msg.getJSONObject("d").getString("target_id");
        JSONObject extra = msg.getJSONObject("d").getJSONObject("extra");
        JSONObject author =extra.getJSONObject("author");
        log.info("频道:{} 昵称:{} 说:{}", extra.getString("channel_name"),author.getString("nickname"),content);
       try{
           String data = QuerySteamInfo(content);
           if (null != data){
               sendMsgPost(data,targetId);
           }
       }catch (Exception e){
           e.printStackTrace();
           sendMsgPost("查询异常:"+e.getMessage(),targetId);
       }

    }

    public static String sendMsgPost(String data,String targetId){
        Map<String,String> header = new HashMap<>(16);
        header.put("Authorization","Bot "+RobotConfig.getToken());
        String reponseContent =  HttpClientUtils.sendPostRequest(SocketIOConfig.getHost()+"channel/message",String.format(SendMsgType.SendMsg.getValue(),targetId,data),"json",header);
        return reponseContent;
    }

    private static String QuerySteamInfo(String serverName) throws Exception {
        if (null == staticServerList){
            staticServerList = RobotConfig.getArma3Server();
        }
        SteamServerQuery ServerQuery = null;
        for (String s:staticServerList.split(",")) {
            String[] strings = s.split("-");
            if (strings[0].equals(serverName)){
                try {
                    ServerQuery = new SteamServerQuery(strings[1] + ":" + strings[2]);

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                SteamServerInfo ServerInfo = ServerQuery.getInfo();
                SteamServerPlayer player = ServerQuery.getPlayer();
                String msg = SendMsgType.SendServerMessage.getValue();
                String title =ServerInfo.getName()+"  ("+ServerInfo.getPlayers()+"/"+ServerInfo.getMaxPlayers()+")";

                String playerData = "无在线玩家";
                if (null != player.playerList()){
                    if (player.playerList().size()>=1){
                        playerData = "";
                        for (int i = 0; i < player.playerList().size(); i++) {
                            Map<String,Object> tempMap = player.playerList().get(i);
                            String online=Long.parseLong(tempMap.get("Score").toString())>50000?"-1":tempMap.get("Score").toString();
                            String regEx="[\\s~·`!！@#￥$%^……&*（()）\\-——\\-_=+【\\[\\]】｛{}｝\\|、\\\\；;：:‘'“”\"，,《<。.》>、/？?]";
                            Pattern p = Pattern.compile(regEx);
                            Matcher m = p.matcher(tempMap.get("playerName").toString());
                            String playerName= m.replaceAll("");
                            playerData += "ID:"+(i+1)+"\\\\t名称:"+playerName+"\\\\t得分:"+ online+"\\\\t在线:"+tempMap.get("Duration").toString()+"\\\\r\\\\n";
                        }
                    }
                }
                msg = String.format(msg,title,playerData,"steam://connect/"+strings[1] + ":" + strings[2],"加入服务器");
                return msg;
            }
        }
        return null;
    }


}
