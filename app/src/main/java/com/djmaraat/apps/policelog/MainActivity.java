package com.djmaraat.apps.policelog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.djmaraat.apps.policelog.Entities.Vehicle;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    PoliceLogDBHelper dbHelper;

    //UI items
    EditText textOwnerName, textDriverName, textDriverLicense, textMake, textColor, textOR, textCR, textEngineNum,
            textChassisNum, textContactNum, textAddress, textCheckPointLoc, textDateLogged;
    Button btnAddToRecord;

    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new PoliceLogDBHelper(this);

        // call init UI function
        initUIAndData();
    }
    /*
    Initializes UI items
     */
    void initUIAndData() {
        textOwnerName = (EditText) findViewById(R.id.editTextNameOwner);
        textDriverName = (EditText) findViewById(R.id.editTextNameDriver);
        textDriverLicense = (EditText) findViewById(R.id.editTextDriverLicenseNum);
        textMake = (EditText) findViewById(R.id.editTextMake);
        textColor = (EditText) findViewById(R.id.editTextColor);
        textOR = (EditText) findViewById(R.id.editTextOR);
        textCR = (EditText) findViewById(R.id.editTextCR);
        textEngineNum = (EditText) findViewById(R.id.editTextEngineNum);
        textChassisNum = (EditText) findViewById(R.id.editTextChassisNum);
        textContactNum = (EditText) findViewById(R.id.editTextContactNum);
        textAddress = (EditText) findViewById(R.id.editTextAddress);
        textCheckPointLoc = (EditText) findViewById(R.id.editTextCheckpointLoc);
        textDateLogged = (EditText) findViewById(R.id.editTextDateLogged);
        textDateLogged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });
        textDateLogged.setText(Calendar.getInstance().get(Calendar.MONTH) +
                "/" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) +
                "/" + Calendar.getInstance().get(Calendar.YEAR)); // set to current date

        btnAddToRecord = (Button) findViewById(R.id.buttonAddToRec);
    }

    /*
    Method that creates a new record from the vehicle form
     */
    public void addToRecord(View view) {
        String[] recordArg = new String[PoliceLogContract.VehicleTable.KEY_ARRAY.length - 1];

        // get form input values from the fields
        recordArg[0] = textOwnerName.getText().toString();
        recordArg[1] = textDriverName.getText().toString();
        recordArg[2] = textDriverLicense.getText().toString();
        recordArg[3] = textMake.getText().toString();
        recordArg[4] = textColor.getText().toString();
        recordArg[5] = textOR.getText().toString();
        recordArg[6] = textCR.getText().toString();
        recordArg[7] = textEngineNum.getText().toString();
        recordArg[8] = textChassisNum.getText().toString();
        recordArg[9] = textContactNum.getText().toString();
        recordArg[10] = textAddress.getText().toString();
        recordArg[11] = textCheckPointLoc.getText().toString();
        recordArg[12] = textDateLogged.getText().toString();
        recordArg[13] = "1"; // TODO get user id

        if (checkCompletedForm(recordArg)) {
            if (dbHelper.storeVehicleInfo(recordArg)) {
                resetFields();
                Intent vehicleProf = new Intent(this, VehicleProfileActivity.class);
                String[] vehicleInfoArg = dbHelper.retrieveVehicleInfo(PoliceLogContract.VehicleTable.COL_OFFICIAL_RECEIPT, recordArg[5]);
                try {
                    vehicleProf.putExtra("vehicle", new Vehicle(vehicleInfoArg));
                    startActivity(vehicleProf);
                } catch (Exception nulEx) {
                    Log.d("LOGTEST", "null error: " + nulEx);
                }
//                vehicleProf.putExtra("vehicle", new Vehicle());
//                startActivity(vehicleProf);
            }
            else {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Duplicate Vehicle Record");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }
        else {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("All fields are REQUIRED.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    /*
    All fields must be filled
     */
    boolean checkCompletedForm(String[] infoArg) {
        // iterate the info array to check for empty fields
        for (String info: infoArg) {
            //TODO set error message here based on info position
            if (info.isEmpty()) return false;
        }
        return true;
    }

    void resetFields() {
        textOwnerName.setText("");
        textDriverName.setText("");
        textMake.setText("");
        textColor.setText("");
        textOR.setText("");
        textCR.setText("");
        textEngineNum.setText("");
        textChassisNum.setText("");
        textContactNum.setText("");
        textAddress.setText("");
        textCheckPointLoc.setText("");
        textDateLogged.setText(Calendar.getInstance().get(Calendar.MONTH) +
                "/" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) +
                "/" + Calendar.getInstance().get(Calendar.YEAR)); // set to current date
    }

    void showDatePicker(View view) {
        // Get Current Date
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        textDateLogged = (EditText) view;

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textDateLogged.setText((monthOfYear + 1) + "/" + (dayOfMonth) + "/" + (year) );
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
