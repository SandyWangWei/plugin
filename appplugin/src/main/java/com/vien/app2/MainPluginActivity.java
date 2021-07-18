package com.vien.app2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vien.pluginframework.BasePluginActivity;

public class MainPluginActivity extends BasePluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //这里是启动第二个activity
        findViewById(R.id.mBtnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(that, SecondPluginActivity.class);
                //这里其实调用父类的 最终会调用宿主里面的startActivity方法，下面会对其进行重写
                startActivity(intent);
            }
        });
    }
}