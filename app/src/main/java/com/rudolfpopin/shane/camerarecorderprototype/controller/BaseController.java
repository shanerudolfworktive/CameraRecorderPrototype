package com.rudolfpopin.shane.camerarecorderprototype.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by shane on 8/8/16.
 */
public class BaseController extends Fragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
