package com.djmaraat.apps.policelog;

import android.provider.BaseColumns;

import com.djmaraat.apps.policelog.Entities.Vehicle;

/**
 * Created by djanemaraat on 12/10/2016.
 */
public final class PoliceLogContract {

    /* An array list of all the SQL create table statements */
    public static final String[] SQL_CREATE_TABLE_ARRAY = {
            VehicleTable.CREATE_TABLE, UserTable.CREATE_TABLE
    };

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private PoliceLogContract() {}

    /* Inner class that defines the vehicle table contents */
    public static abstract class VehicleTable implements BaseColumns {
        public static final String TABLE_NAME = "vehicle";

        // VEHICLE INFO
        public static final String COL_OWNER_NAME = "owner_name";
        public static final String COL_DRIVER_NAME = "driver_name";
        public static final String COL_MAKE = "make";
        public static final String COL_COLOR = "color";
        public static final String COL_OFFICIAL_RECEIPT = "official_receipt";
        public static final String COL_CERT_OF_REG = "cert_of_reg";
        public static final String COL_ENGINE_NUM = "engine_number";
        public static final String COL_CHASSIS_NUM = "chassis_number";
        public static final String COL_CONTACT_NUM = "contact_number";
        public static final String COL_ADDRESS = "address";
        public static final String COL_CHECKPOINT_LOC = "checkpoint_loc";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, %s TEXT NOT NULL)",
                TABLE_NAME, VehicleTable._ID, COL_OWNER_NAME, COL_DRIVER_NAME,
                COL_MAKE, COL_COLOR, COL_OFFICIAL_RECEIPT, COL_CERT_OF_REG,
                COL_ENGINE_NUM, COL_CHASSIS_NUM, COL_CONTACT_NUM,
                COL_ADDRESS, COL_CHECKPOINT_LOC);

        /**
         * SQL statement to delete the table
         */
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        /**
         * Array of all the columns. Makes for cleaner code
         */
        public static final String[] KEY_ARRAY = {
                COL_OWNER_NAME, COL_DRIVER_NAME,
                COL_MAKE, COL_COLOR, COL_OFFICIAL_RECEIPT, COL_CERT_OF_REG,
                COL_ENGINE_NUM, COL_CHASSIS_NUM, COL_CONTACT_NUM,
                COL_ADDRESS, COL_CHECKPOINT_LOC
        };
    }

    /* Inner class that defines the table contents */
    public static abstract class UserTable implements BaseColumns {
        public static final String TABLE_NAME = "user";
        //USER INFO
        public static final String COL_USERNAME = "username";
        public static final String COL_PASSWORD = "password";
        public static final String COL_FIRST_NAME = "first_name";
        public static final String COL_LAST_NAME = "last_name";
        public static final String COL_PRESINCT = "presinct";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY," +
                        "%s TEXT NOT NULL, %s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, %s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL)",
                TABLE_NAME, UserTable._ID, COL_USERNAME, COL_PASSWORD,
                COL_FIRST_NAME, COL_LAST_NAME, COL_PRESINCT);

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String[] KEY_ARRAY = {
                COL_USERNAME, COL_PASSWORD,
                COL_FIRST_NAME, COL_LAST_NAME, COL_PRESINCT
        };
    }
}