package com.example.lfy.basicframes.ui.activity;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.databinding.ActivityPlayerBinding;
import com.example.lfy.basicframes.utill.CustomMediaController;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;


/**
 * 视频播放
 * */
public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding;
    private CustomMediaController mCustomMediaController;
    private String url1 = "http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv";
    private String url2 = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";
    private String url3 = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov";
    private String url4 = "http://42.96.249.166/live/24035.m3u8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = PlayerActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        //检查vitamio框架是否可用

        binding=DataBindingUtil.setContentView(this,R.layout.activity_player);
        mCustomMediaController = new CustomMediaController(this,binding.buffer,this);
        mCustomMediaController.setVideoName("蓝莲花");
        mCustomMediaController.show(5000); //5s隐藏

        //初始化加载库文件
        if (Vitamio.isInitialized(getApplicationContext())) {
            binding.buffer.setVideoURI(Uri.parse(url1));
            binding.buffer.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
            binding.buffer.setMediaController(mCustomMediaController);
            binding.buffer.setBufferSize(10240); //设置视频缓冲大小
            binding.buffer.requestFocus();
            binding.buffer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });

            binding.buffer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    binding.loadRate.setText(percent + "%");
                }
            });
            binding.buffer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    switch (what) {
                        //开始缓冲
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                            if (binding.buffer.isPlaying()) {
                                binding.buffer.pause();
                                binding.probar.setVisibility(View.VISIBLE);
                                binding.downloadRate.setText("");
                                binding.loadRate.setText("");
                                binding.downloadRate.setVisibility(View.VISIBLE);
                                binding.loadRate.setVisibility(View.VISIBLE);
                            }
                            break;
                        //缓冲结束
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                            binding.buffer.start();
                            binding.probar.setVisibility(View.GONE);
                            binding.downloadRate.setVisibility(View.GONE);
                            binding.loadRate.setVisibility(View.GONE);
                            break;
                        //正在缓冲
                        case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                            binding.downloadRate.setText("" + extra + "kb/s" + "  ");
                            break;
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //屏幕切换时，设置全屏
        if (binding.buffer != null){
            binding.buffer.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        if (binding.buffer!=null)
        binding.buffer.stopPlayback();
        super.onDestroy();
    }
}
