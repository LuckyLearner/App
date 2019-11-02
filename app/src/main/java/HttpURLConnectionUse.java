import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.seiztheday.test.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/*使用方法: Object.HttpURLConnectionUse(button,textView,URL)
*/
public class HttpURLConnectionUse {
    HttpURLConnectionUse(Button button, final TextView textView, final URL url1){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()== R.id.button1){
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

                            URL url=url1;
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
                            textView.setText(response.toString());
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

    }





}
