package com.destiny.kaiheila.destinybot.utils;

import java.io.*;
import org.apache.commons.codec.binary.Base64;

public class BaseUtils {
    private static final String CharsetName = "UTF-8";
    public static String GetImageStr(String imgFilePath) throws UnsupportedEncodingException {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;

        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
       String base64Str = new String(Base64.encodeBase64(data),CharsetName);
       // BASE64Encoder encoder = new BASE64Encoder();
        //encoder.encode(data)
        return base64Str;// 返回Base64编码过的字节数组字符串
    }
}
