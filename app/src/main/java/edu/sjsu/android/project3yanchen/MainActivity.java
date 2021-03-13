package edu.sjsu.android.project3yanchen;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // TODO: add a MyView attribute

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: construct the MyView object
        // TODO: and set content view to MyView instead of the layout file.
    }

    @Override
    protected void onStart() {
        super.onStart();
        // TODO: use the startSimulation method in MyView to register the listener
    }

    @Override
    protected void onStop() {
        super.onStop();
        // TODO:  use the startSimulation method in MyView to unregister the listener
    }
}