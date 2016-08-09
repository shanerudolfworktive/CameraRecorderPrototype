package com.rudolfpopin.shane.camerarecorderprototype.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.rudolfpopin.shane.camerarecorderprototype.MainActivity;

/**
 * Created by shane on 8/8/16.
 */
public class BaseController extends Fragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((MainActivity) getActivity()).setOnBackPressedListener(new MainActivity.OnBackPressedListener() {
            @Override
            public void onBackPressed() {
                getActivity().finish();
                System.exit(0);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity) getActivity()).setOnBackPressedListener(null);
    }
}
