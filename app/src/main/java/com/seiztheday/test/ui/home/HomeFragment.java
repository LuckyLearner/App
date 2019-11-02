package com.seiztheday.test.ui.home;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.seiztheday.test.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;
import java.util.Calendar;
import java.util.Properties;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }

        });
        final TextView textView_data=root.findViewById(R.id.textView_data);
/*这里计时
        Calendar now=Calendar.getInstance();
        Calendar gaoKao=Calendar.getInstance();
        gaoKao.set(2018,5,8);

        long value=now.getTimeInMillis()-gaoKao.getTimeInMillis();
        long day=0;
        long oneDay=1000*24*3600;
        day=value/oneDay;
        textView1.setText("距离1509班高考结束已过去"+day+"天！");
计时结束*/


 /*开始使用HttpURLConnection
     final EditText idText=root.findViewById(R.id.idText);
        Button button1=root.findViewById(R.id.button1);
        final TextView responseText=root.findViewById(R.id.textView_data);//responseText是显示的文本框
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.button1){
                    //   Log.d("button", "onClick: ok");
                    sendRequestWithHttpURLConnection();
                }
            }
            private void  sendRequestWithHttpURLConnection(){
                //开启线程来发送网络请求
                new Thread(new Runnable(){
                    @Override
                    public void run(){
                        HttpURLConnection connection = null;
                        BufferedReader reader =null;
                        try{
                            URL url =new URL("http://139.9.166.228:1509/index/testapi/api2?id=1509");
                            connection =(HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setConnectTimeout(2000);
                            connection.setReadTimeout(2000);
                            InputStream in =connection.getInputStream();
                            //下面对获取到的输入流进行读取
                            reader = new BufferedReader(new InputStreamReader(in));
                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = reader.readLine())!=null){
                                response.append(line);
                            }

                            //    Log.d("button", "show: ok"+response.toString());
                            JSONObject transdata=new JSONObject(response.toString());
                            responseText.setText("id:"+transdata.get("id").toString()+"\n"
                                    +"request_time:"+transdata.get("request_time")+"\n"
                                    +"message:"+transdata.get("message")+"\n"
                                    +"day:"+transdata.get("days")
                            );



                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            if(reader!=null){
                                try{
                                    reader.close();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }
                            if (connection!=null){
                                connection.disconnect();
                            }
                        }
                    }
                }).start();
            }



        });


结束，可以看作一个类*/

 /*
 button1.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         new Thread(new Runnable() {
             @Override
             public void run() {
                 try {
                     JSONObject obj=new JSONObject();
                     URL postUrl =new URL("http://139.9.166.228:1509/index/testapi/api2?id="+idText.getText());// 创建url资源
                     Properties systemProperties =System.getProperties();
                     HttpURLConnection connection=(HttpURLConnection)postUrl.openConnection();// 建立http连接
                     connection.setDoOutput(true); // 设置允许输出
                     connection.setDoInput(true);
                     connection.setUseCaches(false); // 设置不用缓存
                     connection.setRequestMethod("POST");  // 设置传递方式
                     connection.setInstanceFollowRedirects(true);
                     connection.setRequestProperty("Connection","Kepp-Alive"); // 设置维持长连接
                     connection.setRequestProperty("Charset","UTF-8"); // 设置文件字符集:
                     byte[] data= (obj.toString()).getBytes(); //转换为字节数组
                     connection.setRequestProperty("Content_Length",String.valueOf(data.length));  // 设置文件长度
                     connection.setRequestProperty("contentType","application/json"); // 设置文件类型:




                     connection.connect();   // 开始连接请求
                     OutputStream out= (OutputStream) idText.getText();  // 写入请求的字符串
                             out.write((obj.toString()).getBytes());
                             out.flush();
                             out.close();
                             textView_data.setText(connection.getResponseCode());

                 } catch (MalformedURLException e) {
                     e.printStackTrace();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }

             }
         }).start();

     }
 });

        */
 Button button1=root.findViewById(R.id.button1);
 final EditText idText=root.findViewById(R.id.idText);
 button1.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         Thread thread = new Thread(new Runnable() {
         @Override
         public void run() {
             BufferedReader bufferedReader = null;

             URL url = null;// 根据自己的服务器地址填写
             try {
                 url = new URL("http://139.9.166.228:1509/index/testapi/api2");
             } catch (MalformedURLException e) {
                 e.printStackTrace();
             }
             HttpURLConnection conn = null;
             try {
                 conn = (HttpURLConnection) url.openConnection();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             conn.setConnectTimeout(5000);
             conn.setDoOutput(true);// 允许输出
             try {
                 conn.setRequestMethod("POST");
             } catch (ProtocolException e) {
                 e.printStackTrace();
             }
             conn.setRequestProperty("Connection", "Keep-Alive");
             conn.setRequestProperty("Charset", "GBK");
             OutputStream os = null;
             try {
                 os = conn.getOutputStream();
             } catch (IOException e) {
                 e.printStackTrace();
             }
          try {
              String id= "id="+String.valueOf(idText.getText());
                os.write(id.getBytes());
             } catch (IOException e) {
                 e.printStackTrace();
             }


             try {
                 if (conn.getResponseCode() == 200) {
                     System.out.println(conn.toString());
                     InputStream is = conn.getInputStream();
                     InputStreamReader isr = new InputStreamReader(is, "GBK");
                     bufferedReader = new BufferedReader(isr);
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
             String result = "";
             String line = "";
             if (bufferedReader != null) {

                 while (true) {
                     try {
                         if (!((line = bufferedReader.readLine()) != null)) break;
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                     result += line;
                     JSONObject transdata= null;
                     try {
                         transdata = new JSONObject(result);
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                     try {
                         textView_data.setText("状态:"+transdata.get("status")+"\n消息:"+transdata.get("message")

                         );
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }



                 }
             }

         }


     });
         thread.start();


     }
 });














        return root;
    }




    }


