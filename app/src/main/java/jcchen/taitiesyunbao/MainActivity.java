package jcchen.taitiesyunbao;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Vector;

/**
 * Created by JCChen on 2017/6/24.
 */

public class MainActivity extends AppCompatActivity {

    class BottomTabInfo {
        int ImageID;
        int StringID;
        Class fragment;

        BottomTabInfo(int ImageID, int StringID, Class fragment) {
            this.ImageID = ImageID;
            this.StringID = StringID;
            this.fragment = fragment;
        }
    }

    private Context context = this;
    private BottomTabInfo bottomTabInfos[] = {
            new BottomTabInfo(R.drawable.main_tab_selector, R.string.bottom_tab_1, MainFragment.class),
            new BottomTabInfo(R.drawable.map_tab_selector, R.string.bottom_tab_2, StoreFragment.class),
            new BottomTabInfo(R.drawable.like_tab_selector, R.string.bottom_tab_3, LikeFragment.class),
            new BottomTabInfo(R.drawable.sug_tab_selector, R.string.bottom_tab_4, SugFragment.class) };
    public int Toolbar_Image[] = {R.drawable.main_title, R.drawable.menu_title,
                                   R.drawable.like_title, R.drawable.sug_title};
    private Vector<Fragment> fragmentVector;

    private ImageView back_imageView, drawer_imageView, toolbar_icon_imageView, signIn_imageView;
    private RelativeLayout right_drawer;
    private DrawerLayout right_drawerLayout;
    private ListView drawer_listView;
    private TextView userName_textView, toolbar_text_textView;
    private RoundedImageView userPic_roundedImageView;
    private MainViewPager fragment_pager;
    private FragmentTabHost bottom_tab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        initUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initUI() {
        // Toolbar
        back_imageView = (ImageView) findViewById(R.id.back_imageView);
        drawer_imageView = (ImageView) findViewById(R.id.drawer_imageView);
        toolbar_icon_imageView = (ImageView) findViewById(R.id.toolbar_icon_imageView);
        toolbar_text_textView = (TextView) findViewById(R.id.toolbar_text_textView);
        setTitleImage(ContextCompat.getDrawable(context, Toolbar_Image[0]));
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setTitle("");
        setSupportActionBar(toolbar);

        // Drawer
        right_drawerLayout = (DrawerLayout) findViewById(R.id.right_drawerLayout);
        right_drawer = (RelativeLayout) findViewById(R.id.right_drawer);
        signIn_imageView = (ImageView) findViewById(R.id.signIn_imageView);
        drawer_listView = (ListView) findViewById(R.id.drawer_listView);
        userName_textView = (TextView) findViewById(R.id.userName_textView);
        userPic_roundedImageView = (RoundedImageView) findViewById(R.id.userPic_RoundedImageView);

        drawer_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(right_drawerLayout.isDrawerOpen(right_drawer))
                    right_drawerLayout.closeDrawer(right_drawer);
                else
                    right_drawerLayout.openDrawer(right_drawer);
            }
        });
        signIn_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });

        // Bottom tab & Content
        fragment_pager = (MainViewPager) findViewById(R.id.fragment_pager);
        bottom_tab = (FragmentTabHost) findViewById(R.id.bottom_tab);

        fragment_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottom_tab.setCurrentTab(position);
                setTitleImage(ContextCompat.getDrawable(context, Toolbar_Image[position]));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fragmentVector = new Vector<>();
        fragmentVector.add(new MainFragment());
        fragmentVector.add(new StoreFragment());
        fragmentVector.add(new LikeFragment());
        fragmentVector.add(new SugFragment());
        fragment_pager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), fragmentVector));

        bottom_tab.setup(this, getSupportFragmentManager(), R.id.fragment_pager);
        bottom_tab.getTabWidget().setDividerDrawable(null);
        bottom_tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int index = bottom_tab.getCurrentTab();
                fragment_pager.setCurrentItem(index);
            }
        });
        for(BottomTabInfo tabInfo : bottomTabInfos) {
            bottom_tab.addTab(bottom_tab.newTabSpec(getResources().getString(tabInfo.StringID))
                                    .setIndicator(getImageView(tabInfo)), tabInfo.fragment, null);
        }
    }

    public void setTitleString(String title) {
        toolbar_icon_imageView.setAlpha(1.0f);
        toolbar_icon_imageView.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                toolbar_icon_imageView.setClickable(false);
            }
        }).start();
        toolbar_text_textView.setAlpha(0);
        toolbar_text_textView.setText(title);
        toolbar_text_textView.animate().alpha(1).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                toolbar_text_textView.setAlpha(1);
            }
        }).start();
    }

    public void setTitleImage(Drawable drawable) {
        toolbar_icon_imageView.setImageDrawable(drawable);
        toolbar_icon_imageView.setAlpha(0.0f);
        toolbar_icon_imageView.animate().alpha(1.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                toolbar_icon_imageView.setClickable(true);
            }
        }).start();


        toolbar_text_textView.setAlpha(1);
        toolbar_text_textView.animate().alpha(0).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                toolbar_text_textView.setAlpha(0);
                toolbar_text_textView.setText("");
            }
        }).start();
    }

    /**
     *
     * @param tabInfo
     * @return
     */
    private View getImageView(BottomTabInfo tabInfo) {
        View view = getLayoutInflater().inflate(R.layout.tab_content, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_imageView);
        TextView  textView  = (TextView) view.findViewById(R.id.tab_textView);
        imageView.setImageDrawable(getResources().getDrawable(tabInfo.ImageID));
        textView.setText(tabInfo.StringID);
        return view;
    }

    private void SignIn() {

    }

    private void LogOut() {

    }
}
