package com.example.administrator.boshide2.Tools;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017-5-21.
 */

public class OcrUtil {
//车牌识别==========================================================================
    /**
     * @fieldName: serialVersionUID
     * @fieldType: long
     * @Description: TODO
     */
    private static final long serialVersionUID = 1L;

    /**
     * @Title: getOcrData
     * @Description: 获取OCR识别信息
     * @param filePath
     * @param ocrUrl
     * @return
     * @return: String
     */
    public static String getOcrData(String filePath,String ocrUrl){
        String datas="";
        try {
            File file = new File(filePath);
            InputStream is=new FileInputStream(file);
            datas = sendDataPic(is,ocrUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }

    /**
     * @Title: sendDataPic
     * @Description: 调用接口获取信息
     * @param is
     * @param ocrUrl
     * @return
     * @return: String
     */
    private static String sendDataPic(InputStream is, String ocrUrl) {
        URL url = null;
        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream in = null;
        String resultJson ="";
        try {
            url = new URL(ocrUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","multipart/form-data; boundary=Bounday---");
            connection.setRequestProperty("Cache-Control","no-cache");
            outputStream=connection.getOutputStream();
            in = is;
            byte[] myByte = new byte[1024];
            int count = -1;
            while((count = in.read(myByte, 0, myByte.length)) != -1){
                outputStream.write(myByte,0, count);
                outputStream.flush();
            }
            is.close();
            outputStream.close();
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int read = 0;
            while ((read = inputStream.read(b)) != -1) {
                byteOut.write(b, 0, read);
            }
            resultJson = new String(byteOut.toByteArray(), "UTF-8");
            in.close();
            byteOut.close();
        } catch (Exception e) {
            resultJson="";
            e.printStackTrace();
        }finally{
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    resultJson="";
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    resultJson="";
                }
            }
            if(connection != null){
                connection.disconnect();
            }
        }
        return resultJson;
    }
//车牌识别==========================================================================



//基础保养==========================================================================

}
