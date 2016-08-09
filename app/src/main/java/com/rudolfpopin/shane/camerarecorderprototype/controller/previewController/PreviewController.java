package com.rudolfpopin.shane.camerarecorderprototype.controller.previewController;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rudolfpopin.shane.camerarecorderprototype.R;
import com.rudolfpopin.shane.camerarecorderprototype.controller.BaseController;

/**
 * Created by shane on 8/8/16.
 */
public class PreviewController extends BaseController implements TextureView.SurfaceTextureListener,View.OnClickListener,MediaPlayer.OnCompletionListener {

    private String path;
    private TextureView surfaceView;
    private MediaPlayer mediaPlayer;
    private ImageView imagePlay;

    public static PreviewController create(String path){
        PreviewController previewController = new PreviewController();
        previewController.path = path;
        return previewController;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ffmpeg_preview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        surfaceView = (TextureView) getView().findViewById(R.id.preview_video);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        RelativeLayout previewVideoParent = (RelativeLayout) getView().findViewById(R.id.preview_video_parent);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) previewVideoParent.getLayoutParams();
        layoutParams.width = displaymetrics.widthPixels;
        layoutParams.height = displaymetrics.widthPixels;
        previewVideoParent.setLayoutParams(layoutParams);

        surfaceView.setSurfaceTextureListener(this);
        surfaceView.setOnClickListener(this);

        imagePlay = (ImageView) getView().findViewById(R.id.previre_play);
        imagePlay.setOnClickListener(this);
    }

    @Override
    public void onStop() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            imagePlay.setVisibility(View.GONE);
        }
        super.onStop();
    }

    private void prepare(Surface surface) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 设置需要播放的视频
            mediaPlayer.setDataSource(path);
            // 把视频画面输出到Surface
            mediaPlayer.setSurface(surface);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.seekTo(0);
        } catch (Exception e) {
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture arg0, int arg1,
                                          int arg2) {
        prepare(new Surface(arg0));
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture arg0) {
        return false;
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture arg0, int arg1,
                                            int arg2) {

    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture arg0) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_cancel:
                stop();
                break;
            case R.id.previre_play:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                imagePlay.setVisibility(View.GONE);
                break;
            case R.id.preview_video:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imagePlay.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    private void stop() {
        mediaPlayer.stop();
//		Intent intent = new Intent(this, FFmpegRecorderActivity.class);
//		startActivity(intent);
//        getActivity().finish();
    }

//    @Override
//    public void onBackPressed() {
//        stop();
//    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        imagePlay.setVisibility(View.VISIBLE);
    }

}
