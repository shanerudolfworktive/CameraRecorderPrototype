package com.rudolfpopin.shane.camerarecorderprototype.controller.squareController;

import android.os.AsyncTask;
import android.util.Log;

import com.googlecode.javacv.FrameRecorder;
import com.googlecode.javacv.cpp.opencv_core;
import com.rudolfpopin.shane.camerarecorderprototype.libs.squareRecorderLib.NewFFmpegFrameRecorder;
import com.rudolfpopin.shane.camerarecorderprototype.libs.squareRecorderLib.SavedFrames;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

public class RecorderThread extends Thread {

    private static final AtomicBoolean M_IS_STOP = new AtomicBoolean(false);
    private static final AtomicBoolean M_IS_FINISH = new AtomicBoolean(false);

    private opencv_core.IplImage mYuvIplImage;
    private NewFFmpegFrameRecorder mVideoRecorder;
    private ByteBuffer mByteBuffer;
    private SquareRecorderController.AsyncStopRecording mAsyncTask;

    private byte[] mBytes;
    private final int mSize;
    private final long[] mTime;
    private int mIndex;
    private int mTotalFrame = 180;


    public RecorderThread(opencv_core.IplImage yuvIplImage, NewFFmpegFrameRecorder videoRecorder, int size, int frame){
        this.mYuvIplImage = yuvIplImage;
        this.mVideoRecorder = videoRecorder;
        this.mSize = size;
        mTotalFrame = frame;
        mTime = new long[mTotalFrame];
    }

    public void putByteData(SavedFrames lastSavedframe){
        if(mByteBuffer != null && mByteBuffer.hasRemaining()){
            mTime[mIndex] = lastSavedframe.getTimeStamp();
            mIndex++;
            mByteBuffer.put(lastSavedframe.getFrameBytesData());
        }
    }

    @Override
    public void run() {
        try {
            if(mByteBuffer == null){
                mByteBuffer = ByteBuffer.allocateDirect(mSize * mTotalFrame);
                mBytes = new byte[mSize];
            }
            int timeIndex = 0;
            int pos = 0;
            int byteIndex;
            while (!M_IS_FINISH.get()) {
                if (mByteBuffer.position() > pos) {
                    for(byteIndex = 0;byteIndex < mSize;byteIndex++){
                        mBytes[byteIndex] = mByteBuffer.get(pos + byteIndex);
                    }

                    pos += mSize;
                    mVideoRecorder.setTimestamp(mTime[timeIndex]);
                    timeIndex++;
                    mYuvIplImage.getByteBuffer().put(mBytes);

                    try {
                        mVideoRecorder.record(mYuvIplImage);
                    } catch (FrameRecorder.Exception e) {
                        Log.i("recorder", "录制错误" + e.getMessage());
                        e.printStackTrace();
                    }

                    if(mAsyncTask != null){
                        int progress = (int)((pos*100l)/mByteBuffer.position());
                        mAsyncTask.publishProgressFromOther(progress);
                    }

                } else {
                    if(M_IS_STOP.get()){
                        break;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }finally {
            release();
        }
    }

    public void stopRecord(AsyncTask asyncTask){
        mAsyncTask = (SquareRecorderController.AsyncStopRecording)asyncTask;
        M_IS_STOP.set(true);
        try {
            this.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finish(){
        M_IS_FINISH.set(true);
        try {
            this.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void release(){
        mAsyncTask = null;
        mYuvIplImage = null;
        mVideoRecorder = null;
        if(mByteBuffer != null){
            mByteBuffer.clear();
        }
        mByteBuffer = null;
        mIndex = 0;
    }
}
