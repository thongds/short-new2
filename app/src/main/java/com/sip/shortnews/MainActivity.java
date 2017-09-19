package com.sip.shortnews;

import android.app.ActionBar;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.sip.shortnews.Utilies.Config;
import com.sip.shortnews.config.ServerConfig;
import com.sip.shortnews.model.NewsHomeSection;
import com.sip.shortnews.model.SocialMediaSection;
import com.sip.shortnews.model.SupportResponse;
import com.sip.shortnews.service.home_api.HomeMediaService;
import com.sip.shortnews.view_customize.SlidingTabLayout;
import com.sip.shortnews.adapter.FragmentAdapter;
import com.sip.shortnews.adapter.MenuAdapter;
import com.sip.shortnews.fragment.NewsFragment;
import com.sip.shortnews.fragment.PFragment;
import com.sip.shortnews.fragment.SocialFragment;
import com.sip.shortnews.model.MenuItemCustomize;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import java.util.ArrayList;
import java.util.Stack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.Query;
import tr.xip.errorview.ErrorView;

/**
 * Created by ssd on 8/13/16.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{
    private FragmentManager mFragmentManager;
    private boolean mIsBackgroundOnTop;
    private ImageView mNavigateMenu;
    private TextView mTitlel;
    private Stack<String> mStackTitle;
    private RelativeLayout mLoadProgress;
    private RelativeLayout mRlUpdate;
    private TextView mUpdateMessage;
    private TextView mUpdateLink;
    ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;
    DilatingDotsProgressBar mDilatingDotsProgressBar;
    private RelativeLayout mRlProgress;
    ErrorView mErrorView;
    private MainActivity mMainActivity;
    private String mLinkUpdate;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStackTitle = new Stack<>();
        setContentView(R.layout.main_activity);
        generateMenu();
        //mNavigateMenu = (ImageView)findViewById(R.id.navigate);
        mSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.sliding_tabs);
        mViewPager = (ViewPager)findViewById(R.id.view_page);
        mTitlel = (TextView)findViewById(R.id.header_title);
        mRlUpdate = (RelativeLayout)findViewById(R.id.rl_update);
        mUpdateMessage = (TextView)findViewById(R.id.update_message);
        mUpdateLink = (TextView)findViewById(R.id.update_link);
        mRlProgress = (RelativeLayout) findViewById(R.id.rl_progress);
        mFragmentManager = getSupportFragmentManager();
        //ImageView menu = (ImageView)findViewById(R.id.navigate);
        //TextView textView = (TextView)findViewById(R.id.day_left);
        mLoadProgress = (RelativeLayout)findViewById(R.id.load_progress);
        mErrorView = (ErrorView)findViewById(R.id.error_view);
        mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progress);
        mDilatingDotsProgressBar.show();
        mMainActivity = this;
        mLoadProgress.setVisibility(View.VISIBLE);
        //Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/brush.ttf");
        //textView.setTypeface(typeface);
        //menu.setOnClickListener(this);
        //mSlidingTabLayout.setOnclickMenu(this);
        mUpdateLink.setOnClickListener(this);
        loadFragment();
        mErrorView.setOnRetryListener(new ErrorView.RetryListener() {
            @Override
            public void onRetry() {
                mMainActivity.loadFragment();
            }
        });
        //getFirebaseConfig();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainActivity = this;
    }

    private void loadFragment(){
        mErrorView.setVisibility(View.GONE);
        mRlProgress.setVisibility(View.VISIBLE);
        String versionName = BuildConfig.VERSION_NAME;
        HomeMediaService.service().checkVersion(versionName,"1").enqueue(new Callback<SupportResponse>() {
            @Override
            public void onResponse(Call<SupportResponse> call, Response<SupportResponse> response) {
                if (response.isSuccessful()){
                    SupportResponse supportResponse = response.body();
                    if(supportResponse.isIs_support()){
                        FragmentAdapter fragmentAdapter = new FragmentAdapter(mFragmentManager);
                        mViewPager.setAdapter(fragmentAdapter);
                        //mSlidingTabLayout.setViewPager(mViewPager);
                        mRlProgress.setVisibility(View.GONE);
                        //mDilatingDotsProgressBar.hide();
                        mErrorView.setVisibility(View.GONE);
                        mLoadProgress.setVisibility(View.GONE);
                        AppApplication application = (AppApplication) getApplication();
                        mTracker = application.getDefaultTracker();
                        mTracker.setScreenName("Android MainActivity");
                        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
                    }else{
                        mRlProgress.setVisibility(View.GONE);
                        //mDilatingDotsProgressBar.hide();
                        mRlUpdate.setVisibility(View.VISIBLE);
                        mLinkUpdate = supportResponse.getLink_update();
                        mUpdateMessage.setText(supportResponse.getMessage_update());
                    }
                }
            }

            @Override
            public void onFailure(Call<SupportResponse> call, Throwable t) {
                mRlProgress.setVisibility(View.GONE);
                //mDilatingDotsProgressBar.hide();
                mErrorView.setVisibility(View.VISIBLE);
            }
        });

    }

    public void replaceForground (PFragment fragment){
        if(mIsBackgroundOnTop){
            popBackStack();
        }
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contain,fragment,fragment.getClass().getSimpleName()).
                    addToBackStack(fragment.getClass().getSimpleName()).commit();
        mIsBackgroundOnTop = false;
    }
    public void replaceBackground (Fragment fragment){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.holder_content,fragment,fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName()).
                commit();
        mIsBackgroundOnTop = true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        popBackStack();

    }
    public void addTitle(String title){
        mStackTitle.push(title);
        updateTitle();
    }
    public void  updateTitle(){
        if(!mStackTitle.isEmpty()){
             mTitlel.setText(mStackTitle.peek());
        }
    }
    public void changeNavigateMenu(boolean showBackMenu){
//        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
//
//        mNavigateMenu.setImageResource(R.drawable.back_menu);
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//        if(showBackMenu){
//            mNavigateMenu.setImageResource(R.drawable.back_menu);
//            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//        }else{
//            mNavigateMenu.setImageResource(R.drawable.menu);
//            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//        }
    }

    private void popBackStack(){
        if(!mStackTitle.isEmpty()){
            mStackTitle.pop();
            updateTitle();
        }
        if(mIsBackgroundOnTop || mFragmentManager.getBackStackEntryCount()>1){
            mFragmentManager.popBackStack();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mFragmentManager.getBackStackEntryCount()>1){
                    changeNavigateMenu(true);

                }
                else {
                    changeNavigateMenu(false);
                }
            }
        },100);

    }
    @Override
    public void onClick(View v) {
//        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        switch (v.getId()){
            case R.id.update_link:
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                break;

        }
    }
    public void generateMenu(){
    }

}
