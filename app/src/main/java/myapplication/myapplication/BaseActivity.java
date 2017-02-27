package myapplication.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2017/2/27.
 */

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initLayout()!=0){
            setContentView(initLayout());
            initView();
            initViewData();
            initevent();
            initData();
        }
    }

    //    获取布局文件的ID
    protected  abstract int initLayout();
//    findbyId
    protected abstract void initView();
//    获取intent的data
    protected abstract void initData();
//    刷新view的data
    protected abstract void initViewData();
//    点击事件处理
    protected abstract void initevent();
}
//

