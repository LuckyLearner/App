package com.seiztheday.test.ui.Me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.seiztheday.test.MainActivity;
import com.seiztheday.test.R;
import com.seiztheday.test.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MeFragment extends Fragment
{

    private MeViewModel MeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        MeViewModel =
                ViewModelProviders.of(this).get(MeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_me, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        MeViewModel.getText().observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)
            {
                textView.setText(s);
            }
        });
        Button goLogin=root.findViewById(R.id.logout);
        goLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { new Thread(new Runnable(){
                @Override
                public void run(){
                    HttpURLConnection connection = null;
                    BufferedReader reader =null;

                    URL url = null;
                    try {
                        url = new URL("http://139.9.166.228:1509/index/Api/Apilogout");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    try {
                        connection =(HttpURLConnection) url.openConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        connection.setRequestMethod("GET");
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    }
                    connection.setConnectTimeout(2000);
                        connection.setReadTimeout(2000);
                    try {
                        InputStream in =connection.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }).start();




                //实现MainActivity跳转LoginActivity
              Intent intent=new Intent(getActivity(),LoginActivity.class);
              startActivity(intent);

            }
        });

        return root;
    }

}