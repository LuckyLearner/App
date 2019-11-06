package com.seiztheday.test.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.seiztheday.test.ui.login.RegisterViewModel;
import com.seiztheday.test.ui.login.RegisterViewFactory;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.seiztheday.test.R;

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

public class RegisterActivity extends AppCompatActivity {
    RegisterViewModel registerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerViewModel = ViewModelProviders.of(this, new RegisterViewFactory()).get(RegisterViewModel.class);
        final Button register = findViewById(R.id.register_register);
        Button back = findViewById(R.id.register_back);
        final EditText username = findViewById(R.id.register_username);
        final EditText password = findViewById(R.id.register_password);
        final EditText phone = findViewById(R.id.register_phone);
        final EditText email = findViewById(R.id.register_email);
        final EditText real_name = findViewById(R.id.register_real_name);
        final EditText student_id = findViewById(R.id.register_student_id);

        registerViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(@Nullable RegisterFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }
                register.setEnabled(registerFormState.isDataValid());
                if (registerFormState.getUsernameError() != null) {
                    username.setError(getString(registerFormState.getUsernameError()));
                }
                if (registerFormState.getPasswordError() != null) {
                    password.setError(getString(registerFormState.getPasswordError()));
                }
                if (registerFormState.getReal_nameError() != null) {
                    real_name.setError(getString(registerFormState.getReal_nameError()));
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {
                    String status;

                    @Override
                    public void run() {
                        String status = "";
                        String messagee = "";
                        BufferedReader bufferedReader = null;

                        URL url = null;// 根据自己的服务器地址填写
                        try {
                            url = new URL("http://139.9.166.228:1509/index/Api/ApiRegister");
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
                            String data= "username="+username.getText().toString()+"&password="+password.getText().toString()+"&phone="+phone.getText().toString()
                                    +"&email="+email.getText().toString()+"&real_name="+real_name.getText().toString()+"&student_id"+student_id.getText().toString();
                            os.write(data.getBytes());
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
                                    status=transdata.get("status").toString();
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



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLoginActivity();
            }
        });




        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.loginDataChanged(username.getText().toString(),
                        password.getText().toString(), real_name.getText().toString());
            }

        };
        username.addTextChangedListener(afterTextChangedListener);
        password.addTextChangedListener(afterTextChangedListener);
        real_name.addTextChangedListener(afterTextChangedListener);

    }





    private void goLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        this.startActivity(intent);
        this.finish();
    }




}
