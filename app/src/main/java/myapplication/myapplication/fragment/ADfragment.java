package myapplication.myapplication.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import myapplication.myapplication.ADInfo;
import myapplication.myapplication.R;
import myapplication.myapplication.Utils.BaseViewPage;

/**
 * Created by Administrator on 2017/2/28.
 */

public class ADfragment extends Fragment implements ViewPager.OnPageChangeListener{

    private List<ImageView> mImageViewList = new ArrayList<ImageView>();
    private LinearLayout indectorLayout, viewpagelayout;
    private List<ADInfo> mInfos;
    private ImageView[] indetor;
    private BaseViewPage mBaseViewPage;
    private int time = 500;//默认轮播时间
    private int currentPosition = 0;//默认当前位置
    private boolean isScrolling = false;//滚动框是否滚动
    private boolean isCycle = false;//是否循环
    private boolean isWheel = false;//是否轮播
    private long releasetime = 0;//手指松开，页面不滚动，防止手指松开后短时间切换
    private int WHEEL = 100;//转动
    private int WHEEL_WAITE = 101;//等待
    private ImageCyclelister mImageCyclelister;
    private ViewpagerAdapter mAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHEEL && mImageViewList.size() != 0) {
                if (!isScrolling) {
                    int max = mImageViewList.size() + 1;
                    int position = (currentPosition + 1) % mImageViewList.size();
                    mBaseViewPage.setCurrentItem(position, true);
                    if (position == max) {
                        mBaseViewPage.setCurrentItem(1, true);
                    }
                    releasetime = System.currentTimeMillis();
                    mHandler.removeCallbacks(mRunnable);
                    mHandler.postDelayed(mRunnable, time);
                }
            }
            if (msg.what == WHEEL_WAITE && mImageViewList.size() != 0) {
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable, time);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ad_fragment_layout, null);
        container = (ViewGroup) view.getParent();
        if (container != null) {
            container.removeView(view);
        }
        mBaseViewPage = (BaseViewPage) view.findViewById(R.id.viewpage);
        indectorLayout = (LinearLayout) view.findViewById(R.id.viewspager_indictor);
        viewpagelayout = (LinearLayout) view.findViewById(R.id.ll_viewpage);
        return view;
    }

    public void setData(List<ImageView> imageViews, List<ADInfo> infos, ImageCyclelister imageCyclelister) {
        setData(imageViews, infos, imageCyclelister, 0);
    }

    /**
     * 初始化viewpager
     */
    private void setData(List<ImageView> views, List<ADInfo> infos, ImageCyclelister cyclelister, int showposition) {
        mImageCyclelister = cyclelister;
        mInfos = infos;
        this.mImageViewList.clear();
        if (views.size() == 0) {
            viewpagelayout.setVisibility(View.GONE);
            return;
        }
        for (ImageView imageView : views) {
            this.mImageViewList.add(imageView);
        }
        int IVsize = views.size();
//        设置指示器，只开辟空间不setdata
        indetor = new ImageView[IVsize];
        if (isCycle) {
            indetor = new ImageView[IVsize - 2];
        }
        indectorLayout.removeAllViews();
        for (int i = 0; i < indetor.length; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.viewpager_layout_indector, null);
            indetor[i] = (ImageView) view.findViewById(R.id.iv_viewpage_indector);
            indectorLayout.addView(view);
        }
        mAdapter=new ViewpagerAdapter();
//        默认指向第一项
        setindictor(0);
//        滑动左右当前界面时不用重新oncreat
        mBaseViewPage.setOffscreenPageLimit(3);
        mBaseViewPage.setOnPageChangeListener(this);
        mBaseViewPage.setAdapter(mAdapter);
        if (showposition<0||showposition>views.size()){
            showposition=0;
            if (isCycle){
                showposition=showposition+1;
            }
            mBaseViewPage.setCurrentItem(showposition);
        }
    }

    /**
     * 设置指示器
     * */
    private void setindictor( int selectindictor){
        for (int i=0;i<indetor.length;i++){
            indetor[i].setBackgroundResource(R.drawable.icon_point);
        }
        if (selectindictor<indetor.length){
            indetor[selectindictor].setBackgroundResource(R.drawable.icon_point_pre);
        }
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isWheel() {

        return isWheel;
    }

    public void setWheel(boolean wheel) {
        isWheel = wheel;
    }

    public boolean isCycle() {

        return isCycle;
    }

    public void setCycle(boolean cycle) {
        isCycle = cycle;
    }

    /**
     * 释放指示器高度，可能由于之前指示器被限制了高度，此处释放
     */
    public void releaseHeight() {
        getView().getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
        refreshData();
    }

    /**
     * 刷新数据，当外部视图更新后，通知刷新数据
     */
    public void refreshData() {
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    public BaseViewPage getBaseViewPage() {
        return mBaseViewPage;
    }

    public void setBaseViewPage(BaseViewPage baseViewPage) {
        mBaseViewPage = baseViewPage;
    }

    /**

     * 设置指示器居中，默认居右
     * */
    private void setindictorcenter(){
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        indectorLayout.setLayoutParams(params);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 页面设配器，返回view
     * */
    public class ViewpagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            container.removeView((View)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
           final ImageView imageView=mImageViewList.get(position);
            if (mImageCyclelister!=null){
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mImageCyclelister.OnImageClick(mInfos.get(position-1),currentPosition,imageView);
                    }
                });
            }
            container.addView(imageView);
            return imageView;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    /**
     * 子线程用于判断收到滑动后是否等待
     */
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (getActivity() != null && !getActivity().isFinishing() && isWheel) {
                long now = System.currentTimeMillis();
//            判断滑动事件是否刚结束
                if (now - releasetime > time - 500) {
                    mHandler.sendEmptyMessage(WHEEL);
                } else {
                    mHandler.sendEmptyMessage(WHEEL_WAITE);
                }
            }
        }
    };

    /**
     * 轮播监听事件
     */
    public static interface ImageCyclelister {
        /**
         * 图片点击事件
         */
        public void OnImageClick(ADInfo info, int position, View imageView);
    }

}
