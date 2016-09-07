package com.sip.shortnews;

import android.app.ActionBar;
import android.graphics.Typeface;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.sip.shortnews.Utilies.Config;
import com.sip.shortnews.config.ServerConfig;
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

/**
 * Created by ssd on 8/13/16.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{
    private FragmentManager mFragmentManager;
    private boolean mIsBackgroundOnTop;
    private ImageView mNavigateMenu;
    private TextView mTitlel;
    private Stack<String> mStackTitle;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private RelativeLayout mLoadProgress;
    ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;
    DilatingDotsProgressBar mDilatingDotsProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStackTitle = new Stack<>();
        setContentView(R.layout.main_activity);
        generateMenu();
        mNavigateMenu = (ImageView)findViewById(R.id.navigate);
        mSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.sliding_tabs);
        mViewPager = (ViewPager)findViewById(R.id.view_page);
        mTitlel = (TextView)findViewById(R.id.header_title);
        mFragmentManager = getSupportFragmentManager();
        ImageView menu = (ImageView)findViewById(R.id.navigate);
        TextView textView = (TextView)findViewById(R.id.day_left);
        mLoadProgress = (RelativeLayout)findViewById(R.id.load_progress);
        mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progress);
        mDilatingDotsProgressBar.show();
        mLoadProgress.setVisibility(View.VISIBLE);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/brush.ttf");
        textView.setTypeface(typeface);
        menu.setOnClickListener(this);
        getFirebaseConfig();
    }

    private void loadFragment(){
        FragmentAdapter fragmentAdapter = new FragmentAdapter(mFragmentManager);
        mViewPager.setAdapter(fragmentAdapter);
        mSlidingTabLayout.setOnclickMenu(this);
        mSlidingTabLayout.setViewPager(mViewPager);
        mDilatingDotsProgressBar.hide();
        mLoadProgress.setVisibility(View.GONE);
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
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);


        if(showBackMenu){
            mNavigateMenu.setImageResource(R.drawable.back_menu);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }else{
            mNavigateMenu.setImageResource(R.drawable.menu);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
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
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        switch (v.getId()){
            case R.id.menu:
                if(mFragmentManager.getBackStackEntryCount()<2)
                    drawerLayout.openDrawer(GravityCompat.START);

                else
                    popBackStack();
                break;

        }
    }
    public void generateMenu(){



        ListView listView = (ListView)findViewById(R.id.list_view_menu);
        ArrayList<MenuItemCustomize> menuItemArrayList = new ArrayList<>();
        int[] resourceIcon = new int[20];
        resourceIcon[0] = R.drawable.icon_airplane;
        resourceIcon[1] = R.drawable.icon_bicycle;
        resourceIcon[2] = R.drawable.icon_directions;
        resourceIcon[3] = R.drawable.icon_eiffel_tower;
        resourceIcon[4] = R.drawable.icon_glasses;
        resourceIcon[5] = R.drawable.icon_helm;
        resourceIcon[6] = R.drawable.icon_palm_tree;
        resourceIcon[7] = R.drawable.icon_home;
        for(int i=0;i<8;i++){
            MenuItemCustomize menuItemCustomize = new MenuItemCustomize();
            if(i==3){
                menuItemCustomize.setHasNewLesson(true);
            }
            if(i == 7){
                menuItemCustomize.setmMenuTitle("Home");
            }
            menuItemCustomize.setmIconResource(resourceIcon[i]);
            menuItemArrayList.add(menuItemCustomize);
        }
        MenuAdapter menuAdapter = new MenuAdapter(this,R.layout.item_menu,menuItemArrayList);
        listView.setAdapter(menuAdapter);
    }
    private void getFirebaseConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mFirebaseRemoteConfig.setDefaults(R.xml.default_firebase_config);

        ServerConfig.setDefault_domain(mFirebaseRemoteConfig.getString("default_domain_in_local"));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFirebaseRemoteConfig.fetch(0)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Fetch Succeeded",
                                            Toast.LENGTH_SHORT).show();

                                    // Once the config is successfully fetched it must be activated before newly fetched
                                    // values are returned.
                                    mFirebaseRemoteConfig.activateFetched();
                                    ServerConfig.setDefault_domain(mFirebaseRemoteConfig.getString("default_domain"));
                                    loadFragment();
                                }else {
                                    Toast.makeText(getApplicationContext(), "Fetch fail",
                                            Toast.LENGTH_SHORT).show();
                                    loadFragment();
                                }
                            }
                        });
            }
        },500);


    }

}
