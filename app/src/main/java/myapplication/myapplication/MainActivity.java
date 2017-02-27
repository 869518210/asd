package myapplication.myapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import myapplication.myapplication.fragment.MjFragment;
import myapplication.myapplication.fragment.NewsFragment;
import myapplication.myapplication.fragment.RecruitFragment;
import myapplication.myapplication.fragment.SearchFragment;
import myapplication.myapplication.fragment.ServiceFragment;

public class MainActivity extends android.support.v4.app.FragmentActivity {

    private int mButtons[] = {R.drawable.ic_tab_paper_selector, R.drawable.ic_tab_search_selected, R.drawable.ic_tab_service_selected, R.drawable.ic_tab_recruit_selected, R.drawable.ic_tab_mj_selected};
    private String text[] = {"麦报", "麦搜", "麦服", "麦聘", "麦金"};
    private Class FragmentActivity[] = {NewsFragment.class, SearchFragment.class, ServiceFragment.class, RecruitFragment.class, MjFragment.class};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        实例化tabhost
        FragmentTabHost fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.frag_content);
        for (int i = 0; i < text.length; i++) {
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(text[i]).setIndicator(getview(i));
//            建立tarbar与fragment的联系
            fragmentTabHost.addTab(tabSpec, FragmentActivity[i], null);
        }
    }

    //获取tarbar的view
    private View getview(int i) {
        View view = View.inflate(MainActivity.this,R.layout.tabcontent, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tarbar);
        TextView textView = (TextView) view.findViewById(R.id.tv_tarbar);
        imageView.setImageResource(mButtons[i]);
        textView.setText(text[i]);
        return view;
    }

}
