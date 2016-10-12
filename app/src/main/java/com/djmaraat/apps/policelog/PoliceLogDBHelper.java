package com.djmaraat.apps.policelog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Dandeljane Maraat on 12/10/2016.
 */
public class PoliceLogDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "policelogs.db";

    public PoliceLogDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // incremented database version from 2 to 3
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // execute the sql for vehicle and user tables
        db.execSQL(PoliceLogContract.SQL_CREATE_TABLE_ARRAY[0]); // create vehicle table
        db.execSQL(PoliceLogContract.SQL_CREATE_TABLE_ARRAY[1]); // create user table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(PoliceLogContract.SQL_CREATE_TABLE_ARRAY[0]);
        db.execSQL(PoliceLogContract.SQL_CREATE_TABLE_ARRAY[1]);
        onCreate(db);
    }


    /* Create a new vehicle information */
    void storeVehicleInfo(String[] vehicleInfoArg) {
        // get the available database
        SQLiteDatabase database = getWritableDatabase();
        // initialize the content values object that will hold the object to be inserted
        // in the database
        ContentValues values = new ContentValues();

        // retrieve the vehicle info from the string array
        values.put(PoliceLogContract.VehicleTable.COL_OWNER_NAME, vehicleInfoArg[0]);
        values.put(PoliceLogContract.VehicleTable.COL_DRIVER_NAME, vehicleInfoArg[1]);
        values.put(PoliceLogContract.VehicleTable.COL_MAKE, vehicleInfoArg[2]);
        values.put(PoliceLogContract.VehicleTable.COL_COLOR, vehicleInfoArg[3]);
        values.put(PoliceLogContract.VehicleTable.COL_OFFICIAL_RECEIPT, vehicleInfoArg[4]);
        values.put(PoliceLogContract.VehicleTable.COL_CERT_OF_REG, vehicleInfoArg[5]);
        values.put(PoliceLogContract.VehicleTable.COL_ENGINE_NUM, vehicleInfoArg[6]);
        values.put(PoliceLogContract.VehicleTable.COL_CHASSIS_NUM, vehicleInfoArg[7]);
        values.put(PoliceLogContract.VehicleTable.COL_CONTACT_NUM, vehicleInfoArg[8]);
        values.put(PoliceLogContract.VehicleTable.COL_ADDRESS, vehicleInfoArg[9]);
        values.put(PoliceLogContract.VehicleTable.COL_CHECKPOINT_LOC, vehicleInfoArg[10]);

        // insert to the vehicle table
        database.insert(PoliceLogContract.VehicleTable.TABLE_NAME, null, values);
        // close the database connection MUST add this to avoid leakage
        database.close();
    }

    /* Create a new user information */
    void storeUserInfo(String[] userInfoArg) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PoliceLogContract.UserTable.COL_USERNAME, userInfoArg[0]);
        values.put(PoliceLogContract.UserTable.COL_PASSWORD, userInfoArg[1]);

        database.insert(PoliceLogContract.UserTable.TABLE_NAME, null, values);
        database.close();
    }

    ArrayList<String[]> retrieveAllVehicleItems() {
        ArrayList<String[]> allVehiclesList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();

        String sql = String.format("SELECT * FROM %s", PoliceLogContract.VehicleTable.TABLE_NAME);

        Cursor cursor = database.rawQuery(sql, null);
        database.rawQuery(sql, null);
        String[] info = new String[12];

        while (cursor.moveToNext()) {
            info[0] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable._ID));  // vehicle ID
            info[1] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_OWNER_NAME));  // owner name
            info[2] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_DRIVER_NAME));  // driver name
            info[3] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_MAKE));  // make
            info[4] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_COLOR));  // color
            info[5] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_OFFICIAL_RECEIPT));  // OR
            info[6] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_CERT_OF_REG));  // CR
            info[7] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_ENGINE_NUM));  // Engine Num
            info[8] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_CHASSIS_NUM));  // CHASSIS NUM
            info[9] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_CONTACT_NUM));  // CONTACT NUM
            info[10] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_ADDRESS));  // ADDRESS
            info[11] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_CHECKPOINT_LOC));  // CHECKPOINT LOC
            allVehiclesList.add(info);
        }

        cursor.close();
        database.close();

        return allVehiclesList;
    }

    String[] retrieveVehicleInfo(String certReg) {
        String[] info = new String[12];
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                PoliceLogContract.VehicleTable._ID,
                PoliceLogContract.VehicleTable.COL_OWNER_NAME,
                PoliceLogContract.VehicleTable.COL_DRIVER_NAME,
                PoliceLogContract.VehicleTable.COL_MAKE,
                PoliceLogContract.VehicleTable.COL_COLOR,
                PoliceLogContract.VehicleTable.COL_CHECKPOINT_LOC
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = PoliceLogContract.VehicleTable.COL_CERT_OF_REG + " = ?";
        String[] selectionArgs = { certReg };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                PoliceLogContract.VehicleTable.COL_OWNER_NAME + " DESC";

        Cursor cursor = db.query(
                PoliceLogContract.VehicleTable.TABLE_NAME,// The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        if (cursor.moveToFirst()) {
            info[0] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable._ID));  // vehicle ID
            info[1] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_OWNER_NAME));  // owner name
            info[2] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_DRIVER_NAME));  // driver name
            info[3] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_MAKE));  // make
            info[4] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_COLOR));  // color
            info[5] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_OFFICIAL_RECEIPT));  // OR
            info[6] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_CERT_OF_REG));  // CR
            info[7] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_ENGINE_NUM));  // Engine Num
            info[8] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_CHASSIS_NUM));  // CHASSIS NUM
            info[9] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_CONTACT_NUM));  // CONTACT NUM
            info[10] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_ADDRESS));  // ADDRESS
            info[11] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.COL_CHECKPOINT_LOC));  // CHECKPOINT LOC
        }
        else info = null;
        cursor.close();
        // can return null if no contact was found.
        return info;
    }

    /**
     * // Getting single contact
     Contact getContact(String name) {
     SQLiteDatabase db = this.getReadableDatabase();

     Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
     KEY_NAME, KEY_PH_NUM }, KEY_NAME + "=?",
     new String[] { name }, null, null, null, null);
     //...

     Contact contact = null;
     if (cursor.moveToFirst()) {
     contact = new Contact(Integer.parseInt(cursor.getString(0)),
     cursor.getString(1), cursor.getString(2));
     }
     cursor.close();
     // can return null if no contact was found.
     return contact;

     **/
}

/*
    Name of Owner
    Name of Driver
    Make (if honda, kwasaki)
    Color
    OR (Official Receipt) - unique
    CR - (Certificate of Registration) unique
    ENGINE NUMBER
    CHASSIS NUMBER
    CONTACT NUMBER
    ADDRESS BRGY
    Check point location
 */