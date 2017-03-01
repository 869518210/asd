package myapplication.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import myapplication.myapplication.MainActivity;
import myapplication.myapplication.R;

/**
 * Created by Administrator on 2017/2/27.
 */

public class NewsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.news_fragment_layout,null);
//        判断view是否被partent加载过
//        如果加载过就要移除，避免重复加载。
        ViewGroup partent=(ViewGroup)view.getParent();
        if (partent!=null){
            partent.removeView(view);
        }
        return view;
    }
}
