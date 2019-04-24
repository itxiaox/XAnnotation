package com.itxiaox.annotation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itxiaox.anotation.InjectManager;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //帮助所有子类完成注入工作
        InjectManager.inject(this);
    }
}
