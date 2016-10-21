package com.djmaraat.apps.policelog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.djmaraat.apps.policelog.entities.Vehicle;

public class VehicleProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_profile);

        Vehicle newVehicle = getIntent().getExtras().getParcelable("vehicle");

        Log.d("LOGTEST", "vehicle OR: " + newVehicle.official_receipt +  "vehicle ID: " + newVehicle.id);

        TextView textOwnerName = (TextView) findViewById(R.id.textNameOwner);
        textOwnerName.setText(newVehicle.owner_name);

        TextView textDriverName = (TextView) findViewById(R.id.textNameDriver);
        textDriverName.setText(newVehicle.driver_name);

        TextView textDriverLicense = (TextView) findViewById(R.id.textDriverLicense);
        textDriverLicense.setText(newVehicle.driver_name);

        TextView textMake = (TextView) findViewById(R.id.textMake);
        textMake.setText(newVehicle.make);

        TextView textColor = (TextView) findViewById(R.id.textColor);
        textColor.setText(newVehicle.color);
    }

    public void donePressed(View view) {
        finish();
    }
}
