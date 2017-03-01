package myapplication.myapplication.Utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/2/28.
 */

public class       BaseViewPage extends ViewPager {

    private boolean scrollable = true;

    public BaseViewPage(Context context) {
        super(context);
    }

    public BaseViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /*
    * 设置viewpage是否可以滚动
    * */

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    /**
     * onInterceptTouchEvent()是用于处理事件（重点onInterceptTouchEvent这个事件是从父控件开始往子控件传的，
     * 直到有拦截或者到没有这个事件的view，然后就往回从子到父控件，这次是onTouch的）（类似于预处理，当然也可以不处理）
     * 并改变事件的传递方向，也就是决定是否允许Touch事件继续向下（子控件）传递，一但返回True（代表事件在当前的viewGroup中会被处理），
     * 则向下传递之路被截断（所有子控件将没有机会参与Touch事件），同时把事件传递给当前的控件的onTouchEvent()处理；
     * 返回false，则把事件交给子控件的onInterceptTouchEvent()
     * */

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (scrollable) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }
}
