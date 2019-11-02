package com.seiztheday.test.ui.home;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.seiztheday.test.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Calendar;

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

        Calendar now=Calendar.getInstance();
        Calendar gaoKao=Calendar.getInstance();
        TextView textView1=root.findViewById(R.id.textView1);
        gaoKao.set(2018,5,8);
/*这里计时*/
        long value=now.getTimeInMillis()-gaoKao.getTimeInMillis();
        long day=0;
        long oneDay=1000*24*3600;
        day=value/oneDay;
        textView1.setText("距离1509班高考结束已过去"+day+"天！");
/*计时结束*/

 /*开始使用HttpURLConnection*/
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
                            URL url =new URL("http://139.9.166.228:1509/index/testapi/api");
                            connection =(HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setConnectTimeout(8000);
                            connection.setReadTimeout(8000);
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


/* 结束，可以看作一个类*/



        return root;
    }




    }


