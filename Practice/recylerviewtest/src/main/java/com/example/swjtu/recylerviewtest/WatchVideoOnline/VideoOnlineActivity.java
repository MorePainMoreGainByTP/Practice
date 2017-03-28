package com.example.swjtu.recylerviewtest.WatchVideoOnline;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.swjtu.recylerviewtest.R;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import java.util.Random;

/**
 * Created by tangpeng on 2017/3/27.
 */

public class VideoOnlineActivity extends AppCompatActivity implements UniversalVideoView.VideoViewCallback {
    private static final String TAG = "VideoOnlineActivity";
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    private static final String VIDEO_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

    private String[] videoURL = {"http://all.51voa.com:88/201702/supreme-court-justices-process.mp4", "http://all.51voa.com:88/v/fisc-warrant.mp4",
            "http://all.51voa.com:88/201611/louisiana-purchase.mp4", "http://all.51voa.com:88/201701/could-have-would-have.mp4",
            "http://all.51voa.com:88/201702/linking-verbs.mp4", "http://all.51voa.com:88/201701/time-clause-beforeafter.mp4"};
    private String strVideoInfo = "“IT行业职场英语”，是面向计算机科学与技术、软件工程等相关专业学生开设的一门通识与公共基础类课程。" +
            "本课程通过对IT行业历史、文化的梳理，帮助学习者对行业文化有充分认识和理解，通过IT行业企业文化以及职场生存技能的训练，" +
            "帮助学习者规划自己的职业发展，进而在国际化IT公司的商务活动中更加自信自如的进行沟通。";
    private UniversalMediaController mediaController;
    private UniversalVideoView mVideoView;
    private FrameLayout mVideoLayout;
    private LinearLayout introduceLayout;
    private TextView videoInfo;

    private int mSeekPosition;
    private int cachedHeight;
    private boolean isFullscreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_online);
        initViews();
        setVideoAreaSize();
    }

    private void initViews() {
        mVideoLayout = (FrameLayout) findViewById(R.id.video_layout);
        introduceLayout = (LinearLayout) findViewById(R.id.introduce_layout);
        mediaController = (UniversalMediaController) findViewById(R.id.media_controller);
        mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
        mVideoView.setMediaController(mediaController);
        mVideoView.setVideoViewCallback(this);
        videoInfo = (TextView) findViewById(R.id.videoInfo);
        videoInfo.setText(strVideoInfo);
    }

    /**
     * 置视频区域大小
     */
    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                Random random = new Random();
                mVideoView.setVideoPath(videoURL[random.nextInt(videoURL.length)]);
                mVideoView.requestFocus();
                mVideoView.start();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause ");
        if (mVideoView != null && mVideoView.isPlaying()) {
            mSeekPosition = mVideoView.getCurrentPosition();
            Log.d(TAG, "onPause mSeekPosition=" + mSeekPosition);
            mVideoView.pause();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState Position=" + mVideoView.getCurrentPosition());
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
        Log.d(TAG, "onRestoreInstanceState Position=" + mSeekPosition);
    }

    @Override
    public void onScaleChange(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        if (isFullscreen) {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoLayout.setLayoutParams(layoutParams);
            introduceLayout.setVisibility(View.GONE);

        } else {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = this.cachedHeight;
            mVideoLayout.setLayoutParams(layoutParams);
            introduceLayout.setVisibility(View.VISIBLE);
        }
        switchTitleBar(!isFullscreen);
    }

    private void switchTitleBar(boolean show) {
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            if (show) {
                supportActionBar.show();
            } else {
                supportActionBar.hide();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (this.isFullscreen) {
            mVideoView.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPause(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.closePlayer();
            mVideoView = null;
        }
    }

    public void back(View v) {
        finish();
    }
}
