package com.djmaraat.apps.policelog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // call generate UI function
        generateUIAndData();
    }

    void generateUIAndData() {


    }
}
