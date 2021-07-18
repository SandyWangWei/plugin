package com.vien.testplugin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        findViewById<View>(R.id.mBtnLoadPlugin).setOnClickListener { loadPlugin() }


        findViewById<View>(R.id.mBtnStartProxy).setOnClickListener { startProxy() }
    }


    /**
     * 加载插件
     */
    private fun loadPlugin() {
        PluginManager.getInstance().loadPlugin(this)
        Toast.makeText(this, "加载完成", Toast.LENGTH_LONG).show()
    }

    /**
     * 跳转插件
     */
    private fun startProxy() {
        val intent = Intent(this, ProxyPluginActivity::class.java) //这里就是一个占坑的activity
        //这里是拿到我们加载的插件的第一个activity的全类名
        intent.putExtra("ClassName", PluginManager.getInstance().packageInfo.activities.get(0).name)
        startActivity(intent)
    }
}