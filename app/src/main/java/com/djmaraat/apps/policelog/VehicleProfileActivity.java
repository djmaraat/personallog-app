package com.djmaraat.apps.policelog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.djmaraat.apps.policelog.Entities.Vehicle;

public class VehicleProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_profile);

        Vehicle newVehicle = getIntent().getExtras().getParcelable("vehicle");

        EditText textOwnerName = (EditText) findViewById(R.id.editTextNameOwner);
        textOwnerName.setEnabled(false);
        textOwnerName.setText(newVehicle.owner_name);

        EditText textDriverName = (EditText) findViewById(R.id.editTextNameDriver);
        textDriverName.setEnabled(false);
        textDriverName.setText(newVehicle.driver_name);

        EditText textMake = (EditText) findViewById(R.id.editTextMake);
        textMake.setEnabled(false);
        textMake.setText(newVehicle.make);

        EditText textColor = (EditText) findViewById(R.id.editTextColor);
        textColor.setEnabled(false);
        textColor.setText(newVehicle.color);

        EditText textOR = (EditText) findViewById(R.id.editTextOR);
        textOR.setEnabled(false);
        textOR.setText(newVehicle.official_receipt);

        EditText textCR = (EditText) findViewById(R.id.editTextCR);
        textCR.setEnabled(false);
        textCR.setText(newVehicle.cert_of_reg);

        EditText textEngineNum = (EditText) findViewById(R.id.editTextEngineNum);
        textEngineNum.setEnabled(false);
        textEngineNum.setText(newVehicle.engine_number);

        EditText textChassisNum = (EditText) findViewById(R.id.editTextChassisNum);
        textChassisNum.setEnabled(false);
        textChassisNum.setText(newVehicle.chassis_number);

        EditText textContactNum = (EditText) findViewById(R.id.editTextContactNum);
        textContactNum.setEnabled(false);
        textContactNum.setText(newVehicle.contact_number);

        EditText textAddress = (EditText) findViewById(R.id.editTextAddress);
        textAddress.setEnabled(false);
        textAddress.setText(newVehicle.address);

        EditText textCheckPointLoc = (EditText) findViewById(R.id.editTextCheckpointLoc);
        textCheckPointLoc.setEnabled(false);
        textCheckPointLoc.setText(newVehicle.checkpoint_loc);

        EditText textDateLogged = (EditText) findViewById(R.id.editTextDateLogged);
        textDateLogged.setEnabled(false);
        textDateLogged.setText(newVehicle.date_logged);
    }

    public void donePressed(View view) {
        finish();
    }
}
