package edu.sjsu.android.project3yanchen;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends Activity {

    private MyView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MyView(this);
        setContentView(view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        view.startSimulation();

    }

    @Override
    protected void onStop() {
        super.onStop();
        view.stopSimulation();
    }
}