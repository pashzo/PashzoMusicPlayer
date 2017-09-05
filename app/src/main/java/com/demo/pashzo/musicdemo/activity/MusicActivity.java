package com.demo.pashzo.musicdemo.activity;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.demo.pashzo.musicdemo.utils.LogCat;
import com.demo.pashzo.musicdemo.R;
import com.demo.pashzo.musicdemo.adapter.MyBaseAdapter;
import com.demo.pashzo.musicdemo.music.MusicControl;
import com.demo.pashzo.musicdemo.music.MusicPlayer;
import com.demo.pashzo.musicdemo.music.MusicService;
import com.demo.pashzo.musicdemo.utils.FormatTime;
import com.demo.pashzo.musicdemo.utils.MediaList;

import java.io.File;
import java.util.ArrayList;

public class MusicActivity extends Activity implements View.OnClickListener, View.OnLongClickListener, AdapterView.OnItemClickListener, SeekBar.OnSeekBarChangeListener {
    private MusicControl musicControl = null;
    private Intent intentService;// 音乐后台服务
    private int musicIndex; // 起始音乐播放序列号
    private boolean isService;//服务起始flag
    private final int REQUEST_READWRITE_STORAGE = 1;
    private ListView lv;
    private ImageView imgbtn;
    private ImageView left;
    private ImageView right;
    private SeekBar pro;
    private TextView totalTime;
    private TextView currentTime;
    private ArrayList<String> list;
    private MyBaseAdapter adapter;
    private NotificationManager manager;
    private boolean flag;
    private UpDateSeekBar updateSeek;//更新进度条线程

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //初始音乐列表
        //填充路径*
        File file = new File(Environment.getExternalStorageDirectory() + "");
        if (!file.canRead()) {
            checkPermission();
        } else {
            fillList();
        }
        //播放的位置
        musicIndex = 0;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //看需求
//        if (musicControl != null) {
//            musicControl.play();
//        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        //看需求
//        if (musicControl != null) {
//            musicControl.play();
//        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //页面销毁停止
        if (isService) {
            if (musicControl != null) {
                musicControl.releaseMedia();
            }
            unbindService(conn);
            isService = false;
        }
        seekHandler.removeCallbacks(updateSeek);//结束进度条线程
    }

    private void initView() {
        //通知栏实例
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 更新进度条线程实例
        updateSeek = new UpDateSeekBar();
        //音乐列表实例
        list = new ArrayList<>();
        //view实例
        lv = (ListView) findViewById(R.id.listView1);
        imgbtn = (ImageView) findViewById(R.id.imageButton1);
        left = (ImageView) findViewById(R.id.imgleft);
        right = (ImageView) findViewById(R.id.imgright);
        pro = (SeekBar) findViewById(R.id.progressBar1);
        totalTime = (TextView) findViewById(R.id.textView1);
        currentTime = (TextView) findViewById(R.id.textView2);
        //view监听
        lv.setOnItemClickListener(this);
        imgbtn.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        pro.setOnClickListener(this);
        left.setOnLongClickListener(this);
        right.setOnLongClickListener(this);
        pro.setOnSeekBarChangeListener(this);
    }

    void checkPermission() {

        int permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READWRITE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_READWRITE_STORAGE) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                finishCreationStep();
            }
        }
    }

    private void finishCreationStep() {
        fillList();
    }

    private void fillList() {
        list.clear();
        list = MediaList.getMusicList(this);
        for (String str : list) {
            LogCat.e(" 音乐路径  ==========" + str);
        }
        setListView();
    }

    private void setListView() {
        adapter = new MyBaseAdapter(this, list);
        lv.setAdapter(adapter);
        //开启播放服务
        LogCat.e("列表个数为 === " + list.size());
        if (list.size() > 0) {
            intentService = new Intent(this, MusicService.class);
            bindService(intentService, conn, BIND_AUTO_CREATE);
            isService = true;
        }

    }


    /*
        音乐服务
     */
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicControl = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicControl = (MusicControl) service;
            musicControl.getStopCallBack(new MusicPlayer.StopCallBack() {
                @Override
                public void stopMediaplyer() {
                    MusicActivity.this.finish();
                }

                @Override
                public void completionMusicPlayer() {
                    LogCat.e("completionMusicPlayer");
                    String musicName = musicControl.getMusicName();
                }
            });
            musicControl.setMusicList(list);
            musicControl.start(musicIndex);


            // 播放音乐名称
            String musicName = musicControl.getMusicName();

            // 播放动画

            // seekbar开启线程
            musicControl.play();
            new Thread(updateSeek).start();
        }
    };


    class UpDateSeekBar implements Runnable {

        @Override
        public void run() {
            seekHandler.sendMessage(Message.obtain());
            if (musicControl == null) {
                flag = false;
            } else if (musicControl.isPlay()) {
                flag = true;
            }
            if (flag) {
                seekHandler.postDelayed(updateSeek, 1000);
            }
        }
    }

    Handler seekHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            LogCat.e("seekHandler -----------");
            if (isService && musicControl.isPlay()) {

                long currentPosion = musicControl.getCurrentPosion();//当前进度数据 时分
                int currentPosition = (int) (currentPosion * 100 / musicControl.getMusicDuration());//当前进度 百分

                pro.setProgress(currentPosition);
                //时间更新
                currentTime.setText(FormatTime.formatTime(currentPosion / 1000));
                totalTime.setText(FormatTime.formatTime(musicControl.getMusicDuration() / 1000));
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton1:
                musicControl.play();
                if (musicControl.isPlay()){
                    imgbtn.setImageResource(R.mipmap.a2h);
                }else{
                    imgbtn.setImageResource(R.mipmap.a2f);
                }
                break;
            case R.id.imgleft:
                musicControl.back();
                break;
            case R.id.imgright:
                musicControl.forward();
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {

            case R.id.imgleft:
                break;
            case R.id.imgright:
                break;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        musicControl.start(position);
        totalTime.setText(FormatTime.formatTime(musicControl.getMusicDuration() / 1000));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        musicControl.setCurrentMusicProgress(progress);
    }
}
