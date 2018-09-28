package com.example.administrator.boshide2.Tools;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
/**
 * Created by Administrator on 2017-5-24.
 */

public class OcrbaoyangUtil {


    /**
     * @Title: getData 调用API接口获取数据
     * @param jsonByteData
     * @throws Exception
     * @createDate 2016年2月22日;上午9:31:01
     */
    public static  String sendData(String url, byte[] jsonByteData) throws Exception{
        //接口访问地址
        String SERVER_URL = url;
        /**开启连接*/
        URL uploadServlet = new URL(SERVER_URL);
        URLConnection uc = uploadServlet.openConnection();
        /**如果为 true，则只要有条件就允许协议使用缓存。*/
        uc.setUseCaches(false);
        /**URL 连接可用于输入和/或输出。将 doOutput 标志设置为 true，指示应用程序要将数据写入 URL 连接。*/
        uc.setDoOutput(true);
        /**URL 连接可用于输入和/或输出。将 doInput 标志设置为 true，指示应用程序要从 URL 连接读取数据。*/
        uc.setDoInput(true);
        /**开启流，写入json数据*/
		/* 设置Content-Type头部指示指定URL已编码数据的窗体MIME类型*/
        int l = jsonByteData.length;
        uc.setRequestProperty("Content-Length", "" + l);
		/* 设置字符编码*/
//		uc.setRequestProperty("Accept-Charset", "GBK");

        /**提取连接的适当的类型*/
        HttpURLConnection hc = (HttpURLConnection) uc;
        /** 把HTTP请求方法设置为POST（默认的是GET）*/
        hc.setRequestMethod("POST");
        /**将数据流发给客户端，通过连接得到输出流*/
        BufferedOutputStream output = new BufferedOutputStream(uc.getOutputStream());
        /**把数据写进输出流里,发送数据*/
        output.write(jsonByteData, 0, l);
        output.flush();
        output.close();
        try {
            /**根据连接，开启输入流*/
            InputStreamReader insreader = new InputStreamReader(uc.getInputStream(), "UTF-8");
            BufferedReader bin = new BufferedReader(insreader);
            StringBuffer sb = new StringBuffer();
            String line;
            /**按行读取返回的文件流*/
            while ((line = bin.readLine()) != null) {
                sb.append(line);
            }
            bin.close();
            insreader.close();
            /**（7）返回的json*/
            String reJson = sb.toString();
            System.out.println("--------------接收的数据start------------");
            System.out.println(reJson);
            System.out.println("--------------接收的数据end--------------");
            return reJson;
        } catch (Exception e) {
            System.out.println("数据已经发送，但没有返回结果！");
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * @Title: getRequireParams 通过请求类型代码设置请求头信息
     * @param username 接口调用账号
     * @param password 接口调用密码
     * @param requestCode 接口编码
     * @param map 接口请求参数
     * @createDate 2016年2月22日;上午9:49:40
     * @author
     */
    public static byte[] getRequireParams(String username,String password,String requestCode,Map map){
        //判断请求参数是否为空
        if(requestCode==null || "".equals(requestCode)|| map.isEmpty()){
            return null;
        }

        JSONObject head=new JSONObject();
        /** 创建body节点 */
        JSONObject body=new JSONObject();
        head.put("channelType", "00");//渠道 :00 测试 ,01 正式
        head.put("operatorCode",username);//账号
        head.put("operatorPwd", password);//密码
        head.put("dtype", "json");//返回格式，json或xml，默认json
        head.put("data", body);
        //设置请求编码

        head.put("requestCode", requestCode);
        //循环添加请求参数
        for (Object key : map.keySet()) {
            body.put((String) key, map.get(key));
        }
        return head.toString().getBytes();
    }


    /**
     * @Title: getDate
     * @Description:调用接口获取数据的方法
     * @param apiUrl 接口地址
     * @param username 接口调用账号
     * @param password 接口调用密码
     * @param requestCode 接口编码
     * @param map 接口请求参数
     * @return
     */
    public static String getDate(String apiUrl,String username,String password,String requestCode,Map map){
        String data="";
        if(apiUrl.equals("")){
            return null;
        }
        //设置请求参数
        byte[] requireParams=getRequireParams(username,password,requestCode, map);
        //判断请求参数是否为空
        if(requireParams!=null){
            try {
                data=sendData(apiUrl, requireParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }


}
