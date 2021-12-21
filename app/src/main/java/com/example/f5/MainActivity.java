package com.example.f5;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library_bottom.BottomItem;
import com.example.library_bottom.BottomView;


public class MainActivity extends AppCompatActivity {

    private FrameLayout mainLayout;
    private BottomView mainBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mainLayout = (FrameLayout) findViewById(R.id.main_layout);
        mainBottom = (BottomView) findViewById(R.id.main_bottom);
    }

    private void initData() {
        mainBottom.addItem(new BottomItem("首页",null))
                .addItem(new BottomItem("分类",null))
                .addItem(new BottomItem("消息",null))
                .addItem(new BottomItem("小屋",null))
                .addItem(new BottomItem("我的",null)).build();
    }
}