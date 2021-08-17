package com.atguigu.msmservice.util;

import com.atguigu.commonutils.response.R;
import com.atguigu.commonutils.util.RandomUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @Author hgk
 * @Date 2021/8/17 9:23
 * @description 发送短信验证码工具类
 */
public class AuthCodeUtil {

    /**
     * 模拟验证码发送
     * @return
     */
    public static R sendAuthCode(String code) {
        int length = code.length();
        if (length == 4) {
            return R.ok().message("短信验证码发送成功");
        } else {
            return R.error().message("短信验证码发送失败");
        }
    }

    /**
     * 发送短信
     * @param host  请求的地址
     * @param path  请求的后缀
     * @param appCode   购入的api的appCode
     * @param phoneNum  发送验证码的目的号码
     * @param sign      签名编号
     * @param skin      模板编号
     * @return
     */
    public static R sendCodeByShortMessage(
            String host,
            String path,
            String appCode,
            String phoneNum,
            String sign,
            String skin,
            String param
    ){
        // 生成验证码
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < 4; i++){
//            int random = (int)(Math.random()*10);
//            builder.append(random);
//        }
//        String param = builder.toString();  // 【4】请求参数，详见文档描述
        String urlSend = host + path + "?param=" + param + "&phone=" + phoneNum + "&sign=" + sign + "&skin=" + skin;  // 【5】拼接请求链接
        try {
            URL url = new URL(urlSend);
            HttpURLConnection httpURLCon = (HttpURLConnection) url.openConnection();
            httpURLCon.setRequestProperty("Authorization", "APPCODE " + appCode);// 格式Authorization:APPCODE (中间是英文空格)
            int httpCode = httpURLCon.getResponseCode();
            if (httpCode == 200) {
                String json = read(httpURLCon.getInputStream());
                return R.ok().message(param);
            } else {
                Map<String, List<String>> map = httpURLCon.getHeaderFields();
                String error = map.get("X-Ca-Error-Message").get(0);
                if (httpCode == 400 && error.equals("Invalid AppCode `not exists`")) {
                    return R.error().message("AppCode错误 ");
                } else if (httpCode == 400 && error.equals("Invalid Url")) {
                    return R.error().message("请求的 Method、Path 或者环境错误");
                } else if (httpCode == 400 && error.equals("Invalid Param Location")) {
                    return R.error().message("参数错误");
                } else if (httpCode == 403 && error.equals("Unauthorized")) {
                    return R.error().message("服务未被授权（或URL和Path不正确）");
                } else if (httpCode == 403 && error.equals("Quota Exhausted")) {
                    return R.error().message("套餐包次数用完 ");
                } else {
                    return R.error().message("参数名错误 或 其他错误" + error);
                }
            }

        } catch (MalformedURLException e) {
            return R.error().message("URL格式错误");
        } catch (UnknownHostException e) {
            return R.error().message("URL地址错误");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().message("套餐包次数用完 ");
        }
    }

    /*
     * 读取返回结果
     */
    private static String read(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            line = new String(line.getBytes(), StandardCharsets.UTF_8);
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

}
