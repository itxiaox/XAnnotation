package com.itxiaox.annotation;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.itxiaox.anotation.ContentView;
import com.itxiaox.anotation.InjectView;
import com.itxiaox.anotation.OnClick;
import com.itxiaox.anotation.OnLongClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @InjectView(R.id.tv_info)
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView.setText("测试");
    }

    @OnClick({R.id.tv_info,R.id.btn_submit})
    public void showInfo(View view){
        Toast.makeText(MainActivity.this, "自定义注解OnClick", Toast.LENGTH_SHORT).show();
    }

    @OnLongClick({R.id.tv_info})
    public void showLongInfo(View view){
        Toast.makeText(MainActivity.this, "自定义注解OnLongClick", Toast.LENGTH_SHORT).show();
    }

}
