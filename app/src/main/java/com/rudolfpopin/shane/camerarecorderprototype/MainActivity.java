package com.rudolfpopin.shane.camerarecorderprototype;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    DispatchTouchEventListener dispatchTouchEventListener;
    OnBackPressedListener onBackPressedListener;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (dispatchTouchEventListener != null) {
            boolean result = dispatchTouchEventListener.dispatchTouchEvent(ev);
            if (result == true) return super.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }


    public void setOnDispatchTouchEventListener(DispatchTouchEventListener dispatchTouchEventListener){
        this.dispatchTouchEventListener = dispatchTouchEventListener;
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener){
        this.onBackPressedListener = onBackPressedListener;
    }

    public interface DispatchTouchEventListener{
        boolean dispatchTouchEvent(MotionEvent ev);
    }

    public interface OnBackPressedListener{
        void onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener!=null){
            onBackPressedListener.onBackPressed();
        }else{
            super.onBackPressed();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
