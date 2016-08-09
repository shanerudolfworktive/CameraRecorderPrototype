package com.rudolfpopin.shane.camerarecorderprototype.view.squareRecorder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.rudolfpopin.shane.camerarecorderprototype.R;
import com.rudolfpopin.shane.camerarecorderprototype.view.BaseFragment;

/**
 * Created by shane on 8/8/16.
 */
public class SquareRecorderFragment extends BaseFragment{

    TextureView mTextureView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_square_recorder, container, false);
        loadInitialViews(rootView);
        return rootView;
    }

    private void loadInitialViews(View rootView){
        mTextureView = (TextureView) rootView.findViewById(R.id.textureViewSquareRecorder);
    }

    private void restoreViewState(View rootView){

    }

}
