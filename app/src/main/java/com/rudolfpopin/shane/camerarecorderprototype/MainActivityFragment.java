package com.rudolfpopin.shane.camerarecorderprototype;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rudolfpopin.shane.camerarecorderprototype.controller.squareController.SquareRecorderController;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutHolder, new SquareRecorderController(), null).commit();
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
