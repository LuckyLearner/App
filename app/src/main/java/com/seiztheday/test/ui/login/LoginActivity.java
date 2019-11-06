package com.seiztheday.test.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.seiztheday.test.MainActivity;
import com.seiztheday.test.R;
import com.seiztheday.test.ui.login.LoginViewModel;
import com.seiztheday.test.ui.login.LoginViewModelFactory;

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

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button registerButton=findViewById(R.id.register);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);



        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                    setResult(Activity.RESULT_OK);
                    goMainActivity();
                    finish();

                }

                //Complete and destroy login activity once successful

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
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

            //登录按钮监听事件，里面有连接网络的子线程
        loginButton.setOnClickListener(new View.OnClickListener() {
            //status传递状态，message传递返回信息
            String status ="";
            String message ="";
            @Override

            public void onClick(View v) {

                loadingProgressBar.setVisibility(View.VISIBLE);//显示加载进程

                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        String status = "";
                        String messagee = "";
                        BufferedReader bufferedReader = null;

                        URL url = null;// 根据自己的服务器地址填写
                        try {
                            url = new URL("http://139.9.166.228:1509/index/testapi/ApiLogin");
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
                            String id= "username="+usernameEditText.getText().toString()+"&password="+passwordEditText.getText().toString();
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
                                    status =transdata.get("status").toString();
                                    message=transdata.get("message").toString();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                        loginViewModel.login(status, message);


                    }


                });
                thread.start();


            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegisterActivity();
            }
        });
    }



    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

//MainActivity
    private void goMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }
    //前往RegisterActivity
    private void goRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivity(intent);
        this.finish();
    }




}
