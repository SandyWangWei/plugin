package com.vien.testplugin;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import com.vien.pluginframework.PluginActivityInterface;

import java.lang.reflect.Constructor;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by VienWang on 2021/7/17
 * Describe:
 */
public class ProxyPluginActivity extends AppCompatActivity {

    private PluginActivityInterface pluginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在这里拿到了真实跳转的activity 拿出来 再去启动真实的activity

        String className = getIntent().getStringExtra("ClassName");

        //通过反射在去启动一个真实的activity 拿到Class对象
        try {
            Class<?> plugClass = getClassLoader().loadClass(className);
            Constructor<?> pluginConstructor = plugClass.getConstructor(new Class[]{});
            //因为插件的activity实现了我们的标准
            pluginActivity = (PluginActivityInterface) pluginConstructor.newInstance(new Object[]{});
            pluginActivity.attach(this);//注入上下文
            pluginActivity.onCreate(new Bundle());//一定要调用onCreate
        } catch (Exception e) {
            if (e.getClass().getSimpleName() .equals("ClassCastException")){
                //我这里是直接拿到异常判断的 ，也可的 拿到上面的plugClass对象判断有没有实现我们的接口
                finish();
                Toast.makeText(this,"非法页面",Toast.LENGTH_LONG).show();
                return;
            }
            e.printStackTrace();
        }
    }

    //为什么要重写这个呢 因为这个是插件内部startactivity调用的 将真正要开启的activity的类名穿过来
    //然后取出来，启动我们的占坑的activity 在我们真正要启动的赛进去
    @Override
    public void startActivity(Intent intent) {
        String className1=intent.getStringExtra("ClassName");
        Intent intent1 = new Intent(this, ProxyPluginActivity.class);
        intent1.putExtra("ClassName", className1);
        super.startActivity(intent1);
    }

    //重写classLoader
    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getClassLoader();
    }

    //重写Resource
    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResource();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(pluginActivity != null) {
            pluginActivity.onStart();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pluginActivity != null) {
            pluginActivity.onDestroy();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(pluginActivity != null) {
            pluginActivity.onPause();
        }
    }
}