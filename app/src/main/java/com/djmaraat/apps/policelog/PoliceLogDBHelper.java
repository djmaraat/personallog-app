package com.djmaraat.apps.policelog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    boolean storeVehicleInfo(String[] vehicleInfoArg) {
        // get the available database
        SQLiteDatabase database = getWritableDatabase();
        // initialize the content values object that will hold the object to be inserted
        // in the database
        ContentValues values = new ContentValues();

        // retrieve the vehicle info from the string array
        int count = 1;
        for (String vehicleInf: vehicleInfoArg) {
            if (count == 14)
                values.put(PoliceLogContract.VehicleTable.KEY_ARRAY[count], Integer.valueOf(vehicleInf));
            else values.put(PoliceLogContract.VehicleTable.KEY_ARRAY[count], vehicleInf);
//            Log.d("LOGTEST", "count: " + PoliceLogContract.VehicleTable.KEY_ARRAY[count] + " vehicle info: " + vehicleInf);
            count++;
        }

        // insert to the vehicle table, return false if there is a duplicate item
        try {
            // insertWithOnConflict throws a constraint exception if constraint is violated
            database.insertWithOnConflict(PoliceLogContract.VehicleTable.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_FAIL);
        } catch (SQLiteConstraintException sqEx) {
            database.close();
            return false;
        }
        database.close();
        // close the database connection
        // MUST add this to avoid leakage
        return true;
    }

    /* Create a new user information */
    boolean storeUserInfo(String[] userInfoArg) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        // retrieve the user info from the string array
        int count = 1;
        for (String userInf: userInfoArg) {
            values.put(PoliceLogContract.UserTable.KEY_ARRAY[count], userInf);
            count++;
        }

        // insert to the vehicle table, return false if there is a duplicate item
        try {
            // insertWithOnConflict throws a constraint exception if constraint is violated
            database.insertWithOnConflict(PoliceLogContract.UserTable.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_FAIL);
        } catch (SQLiteConstraintException sqEx) {
            database.close();
            return false;
        }
        database.close();
        // close the database connection
        // MUST add this to avoid leakage
        return true;
    }

    ArrayList<String[]> retrieveAllVehicleItems() {
        ArrayList<String[]> allVehiclesList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();

        String sql = String.format("SELECT * FROM %s", PoliceLogContract.VehicleTable.TABLE_NAME);

        Cursor cursor = database.rawQuery(sql, null);
        database.rawQuery(sql, null);
        String[] info = new String[PoliceLogContract.VehicleTable.KEY_ARRAY.length];

        while (cursor.moveToNext()) {
            for (int i = 0; i < info.length; i++) {
                if (i == 0 || i == 14) { // id, or, cr, user id (checked by)
                    info[i] = Integer.toString(cursor.getInt(cursor.getColumnIndex(PoliceLogContract.VehicleTable.KEY_ARRAY[i])));
                }
                else {
                    info[i] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.KEY_ARRAY[i]));
                }
            }
            allVehiclesList.add(info);
        }

        cursor.close();
        database.close();

        return allVehiclesList;
    }
    /* Retrieves a search with String parameters
     * @params colName, keyword */
    String[] retrieveVehicleInfo(String colName, String keyword) {
        String[] info = new String[PoliceLogContract.VehicleTable.KEY_ARRAY.length];
        SQLiteDatabase database = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = PoliceLogContract.VehicleTable.KEY_ARRAY;

        // Filter results WHERE "title" = 'My Title'
        String selection = colName + " = ?";
        String[] selectionArgs = { keyword };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                PoliceLogContract.VehicleTable.COL_DATE_LOGGED + " DESC";

        Cursor cursor = database.query(
                PoliceLogContract.VehicleTable.TABLE_NAME,// The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        if (cursor.moveToFirst()) {
            for (int i = 0; i < info.length; i++) {
                if (i == 0 || i == 14) { // id, user id (checked by)
                    info[i] = Integer.toString(cursor.getInt(cursor.getColumnIndex(PoliceLogContract.VehicleTable.KEY_ARRAY[i])));
                }
                else {
                    info[i] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.KEY_ARRAY[i]));
                }
//                Log.d("LOGTEST", "retrieval - count: " + PoliceLogContract.VehicleTable.KEY_ARRAY[i] + " vehicle info: " + info[i]);
            }
        }
        else info = null;
        cursor.close();
        database.close();
        // can return null if no contact was found.
        return info;
    }

    ArrayList<String[]> searchVehiclesByKeyword(String colName, String keyword) {
        ArrayList<String[]> allVehiclesList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = PoliceLogContract.VehicleTable.KEY_ARRAY;

        // Filter results WHERE "title" = 'My Title'
        String selection = colName + " = ?";
        String[] selectionArgs = { keyword };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                PoliceLogContract.VehicleTable.COL_DATE_LOGGED + " DESC";

        String[] info = new String[PoliceLogContract.VehicleTable.KEY_ARRAY.length];
        Cursor cursor = database.query(
                PoliceLogContract.VehicleTable.TABLE_NAME,// The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        if (cursor.moveToFirst()) {
            for (int i = 0; i < info.length; i++) {
                if (i == 0 || i == 14) { // id, or, cr, user id (checked by)
                    info[i] = Integer.toString(cursor.getInt(cursor.getColumnIndex(PoliceLogContract.VehicleTable.KEY_ARRAY[i])));
                }
                else {
                    info[i] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.VehicleTable.KEY_ARRAY[i]));
                }
            }
        }
        cursor.close();
        database.close();

        return allVehiclesList;
    }

    String[] retrieveUserInfo(String colName, String keyword) {
        String[] info = new String[PoliceLogContract.UserTable.KEY_ARRAY.length];
        SQLiteDatabase database = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = PoliceLogContract.UserTable.KEY_ARRAY;

        // Filter results WHERE "title" = 'My Title'
        String selection = colName + " = ?";
        String[] selectionArgs = { keyword };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                PoliceLogContract.UserTable.COL_LAST_NAME + " DESC";

        Cursor cursor = database.query(
                PoliceLogContract.UserTable.TABLE_NAME,// The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        if (cursor.moveToFirst()) {
            for (int i = 0; i < info.length; i++) {
                info[i] = cursor.getString(cursor.getColumnIndex(PoliceLogContract.UserTable.KEY_ARRAY[i]));
            }
        }
        else info = null;
        cursor.close();
        database.close();
        // can return null if no contact was found.
        return info;
    }

    boolean deleteRecord(int recordID){
        SQLiteDatabase database = getWritableDatabase();
        // Define 'where' part of query.
        String selection = PoliceLogContract.VehicleTable._ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { Integer.toString(recordID) };
        // Issue SQL statement.
        database.delete(PoliceLogContract.VehicleTable.TABLE_NAME, selection, selectionArgs);
        database.close();
        return true;
    }

    boolean updateRecord(String[] columns, String[] values, int recordID) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int valueCounter = 0;
        for (String column: columns) {
            contentValues.put(column, values[valueCounter]);
            valueCounter++;
        }
        database.update(PoliceLogContract.VehicleTable.TABLE_NAME, contentValues,
                PoliceLogContract.VehicleTable._ID + "=?", new String[] {Integer.toString(recordID)});
        database.close();
        return true;
    }
}

/*
    Name of Owner
    Name of Driver
    License Number
    Make (if honda, kwasaki)
    Color
    OR (Official Receipt) - unique
    CR - (Certificate of Registration) unique
    ENGINE NUMBER
    CHASSIS NUMBER
    CONTACT NUMBER
    ADDRESS BRGY
    Check point location
    Date Logged
    Checked by
 */