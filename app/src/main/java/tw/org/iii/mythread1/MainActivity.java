package tw.org.iii.mythread1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mesg;
    private UIHandler handler;
    private  int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new UIHandler();

        mesg = findViewById(R.id.mesg);

    }//onCteate

    public void test1(View view) {

        new Thread(){ //所以只有看到 Thread才是執行緒  這個案下去 test3餐就可以同時進行使用
            @Override
            public void run() {
                for(int i=0; i<20; i++){
                    Log.i("brad","i=" + i);
                    //mesg.setText("i=" + i);
                    handler.sendEmptyMessage(i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }//test1

    public void test2(View view) {
        Log.i("brad","before");
        runOnUiThread(new Runnable() { //runnable不是執行緒  必須要是thread的物件實體才是執行緒
            @Override
            public void run() {
                //mesg.setText("ok");
                for(int i=0; i<10;i++){
                    mesg.setText("i =" + i);
                    Log.i("brad","i=" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Log.i("brad","after");


    }//test2

    public void test3(View view) {
        Log.i("brad","click");
    }//test3

    public void test4(View view) {
        new Thread(){
            @Override
            public void run() {
                for(i=0; i<10;i++){ //不再重複宣告  就是全域變數的i
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mesg.setText("i=" + i);  //可以使用i了
                        }
                    });
                }
            }
        }.start();
    }

    private class UIHandler extends Handler{ //空制器 韓斗魯
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //Log.i("brad","handler:ok");
            mesg.setText("i =" + msg.what);
        }
    }//韓斗魯


}//class
