package com.example.administrator.boshide2.Tools;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.boshide2.Conts;
import com.example.administrator.boshide2.Https.URLS;
import com.example.administrator.boshide2.Main.MyApplication;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * Created by lidong on 2016/1/28.
 */

public class FileUploadManager {
    private static URLS url=new URLS();
    private static final String ENDPOINT = MyApplication.shared.getString("ip", "");
    private static String TAG = FileUploadManager.class.getSimpleName();



    public interface FileUploadService {

        @POST("public/uploadFile")
        Call<ResponseBody> uploadFileWithRequestBody(@Body MultipartBody body);

    }

    private static final Retrofit sRetrofit = new Retrofit .Builder()
            .baseUrl(ENDPOINT)
            .build();

    private static final FileUploadService apiManager = sRetrofit.create(FileUploadService.class);



    /**
     * @param paths
     */
    public static void uploadMany(ArrayList<String> paths, String  picName, final Context  context){

        Log.e("pic", "访问路径："+ ENDPOINT);
        
        List<File> files = new ArrayList<>();
        Map<String,RequestBody> photos = new HashMap<>();
        if (paths.size()>0) {
            for (int i=0;i<paths.size();i++) {
                files.add(new File(paths.get(i)));
                File file = new File(paths.get(i));
                // photos.put("file", RequestBody.create(MediaType.parse("image/png"),file));
            }
        }
//        MultipartBody.Builder builder = new MultipartBody.Builder();
        MultipartBody body = filesToMultipartBody(files,picName);
//        for (File f:files) {
//            builder.addFormDataPart("file",f.getName(),RequestBody.create(MediaType.parse("image/png"), f));
//        }
//        MultipartBody formBody = builder.build();

        Log.e("sc", "开始上传" );
        Call<ResponseBody> stringCall = apiManager.uploadFileWithRequestBody(body);
        stringCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    Log.e("sc", "请求上传成功了吗？"+response.body().string() );
                    JSONObject  jsonObject=new JSONObject(response.body().toString());
                    Toast.makeText(context,jsonObject.getString("data"),Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onResponse() called with: " + "call = [" + call + "], response = [" + response.body() + "]");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("sc", "上传失败" );
                Log.d(TAG, "onFailure() called with: " + "call = [" + call + "], t = [" + t + "]");
            }
        });
    }


    public static MultipartBody filesToMultipartBody(List<File> files,String  picName) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
//        for (File file : files) {
//            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
//            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
//            builder.addFormDataPart("file", file.getName(), requestBody);
//        }

        //修改图片的名字为车牌号_pic1,2,3,4;
        for(int  i=0;i<files.size();i++){
//            builder.addFormDataPart("file", Conts.cp+"_pic"+i,RequestBody.create(MediaType.parse("image/png"), files.get(i)));
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), files.get(i));
            builder.addFormDataPart("file",picName+".jpg", requestBody);
        }



        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }


}

