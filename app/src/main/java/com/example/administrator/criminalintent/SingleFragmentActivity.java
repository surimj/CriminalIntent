package com.example.administrator.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dragyu on 2016/11/23.
 * 每新建一个Activity都需要一段同样的代码，为了避免重复，将重复代码用抽象类封装起来
 * 这段代码的功能是：从activity_fragment.xml布局里实例化activity视图，
 * 然后在容器中查找FragmentManager里的fragment，找不到就新建一个放入容器。
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        //获取FragmentManager管理fragment事务
        //Activity销毁时，Manager会保存原有的Fragment队列里的Fragment
        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            //创建一个新的Fragment事务，加入一个添加操作，然后提交该事务
            //ID作用：确定Fragment的位置，作为FragmentManager队列里该Fragment的唯一标识符
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }

    }
}
