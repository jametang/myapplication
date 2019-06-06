package com.example.admin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity{

    TextView mTextView;
    ImageView mImageView;
    private View mDecorView;
    private Random random;


    private boolean mIsBind = false;
    private boolean mIsConnected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
        mDecorView = getWindow().getDecorView();
        setDimMode(this);
//        setSupportActionBar(toolbar);
        initView();
        initData();
    }

    /**
     * 5.0及以上版本设置沉浸式模式（状态栏沉浸式）
     * @param activity
     */
    public static void setDimMode(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int uiOpt = activity.getWindow().getDecorView().getSystemUiVisibility();
            activity.getWindow().getDecorView().setSystemUiVisibility(uiOpt | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
    AnimationDrawable mAnimationDrawable;
    Button button;
    Button button1;

    private void initView() {
        ImageView mAnimationBgIv = findViewById(R.id.webp_animation);
        mAnimationDrawable = (AnimationDrawable) mAnimationBgIv.getDrawable();
        mAnimationDrawable.start();
        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
        Log.e("TANGLI","start");
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.main_activity_content_ly,new MyFragment());
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();


//            Toolbar toolbar = findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
    }

    private void initData() {
        final Animation animationZoomIn = AnimationUtils.loadAnimation(this, R.anim.home_hot_word_zoom_in);
        final Animation animationZoomOut = AnimationUtils.loadAnimation(this, R.anim.home_hot_word_zoom_out);
        animationZoomIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                button.startAnimation(animationZoomOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationZoomOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                button.startAnimation(animationZoomIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        button.startAnimation(animationZoomIn);
    }

    class Data{

        public Data(String name){
            this.name = name;
        }
        String name;

        @Override
        public String toString() {
            return name;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        hideStatusBar();
    }

    private void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }



    //    @Override
//    protected void onStart() {
//        super.onStart();
//        Intent service = new Intent(this,RemindService.class);
//        mIsBind  = bindService(service,connection, Context.BIND_AUTO_CREATE);
//    }
//
//    @Override
//    public void refreshUI(final int num) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if(mTextView != null){
//                    mTextView.setText(num + "");
//                }
//                if(mImageView != null){
//                    mImageView.setBackgroundColor(getCumstomColor());
//                }
//            }
//        });
//    }
//
//    private ServiceConnection connection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            mService = ((RemindService.MyBinder)iBinder).getService();
//            mService.setMyInterface(MainActivity.this);
//            MainActivity.this.mIsConnected = true;
//            if(mService != null){
//                mService.startTask();
//            }
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            MainActivity.this.mIsConnected = false;
//        }
//    };
//
//    public int getCumstomColor() {
//        return Color.rgb(random.nextInt(), random.nextInt(), random.nextInt());
//    }
}
