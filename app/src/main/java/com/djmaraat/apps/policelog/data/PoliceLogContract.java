/*
 *
 * Copyright 2016 Dandeljane Maraat
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.djmaraat.apps.policelog.data;

import android.provider.BaseColumns;

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
        public static final String COL_LICENSE_NUM = "driver_licence_num";
        public static final String COL_MAKE = "make";
        public static final String COL_COLOR = "color";
        public static final String COL_OFFICIAL_RECEIPT = "official_receipt"; // unique 6, num
        public static final String COL_CERT_OF_REG = "cert_of_reg"; // unique 7, num
        public static final String COL_ENGINE_NUM = "engine_number";
        public static final String COL_CHASSIS_NUM = "chassis_number";
        public static final String COL_CONTACT_NUM = "contact_number";
        public static final String COL_ADDRESS = "address";
        public static final String COL_CHECKPOINT_LOC = "checkpoint_loc";
        public static final String COL_DATE_LOGGED = "date_logged";
        public static final String COL_CHECKED_BY = "checked_by"; // user id

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, UNIQUE ( %s ))",
                TABLE_NAME, VehicleTable._ID, COL_OWNER_NAME, COL_DRIVER_NAME, COL_LICENSE_NUM,
                COL_MAKE, COL_COLOR, COL_OFFICIAL_RECEIPT, COL_CERT_OF_REG,
                COL_ENGINE_NUM, COL_CHASSIS_NUM, COL_CONTACT_NUM,
                COL_ADDRESS, COL_CHECKPOINT_LOC, COL_DATE_LOGGED, COL_CHECKED_BY, COL_OFFICIAL_RECEIPT);

        /**
         * SQL statement to delete the table
         */
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        /**
         * Array of all the columns. Makes for cleaner code
         */
        public static final String[] KEY_ARRAY = {VehicleTable._ID,
                COL_OWNER_NAME, COL_DRIVER_NAME, COL_LICENSE_NUM,
                COL_MAKE, COL_COLOR, COL_OFFICIAL_RECEIPT, COL_CERT_OF_REG,
                COL_ENGINE_NUM, COL_CHASSIS_NUM, COL_CONTACT_NUM,
                COL_ADDRESS, COL_CHECKPOINT_LOC, COL_DATE_LOGGED, COL_CHECKED_BY
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
        public static final String COL_RANK = "rank";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY," +
                        "%s TEXT NOT NULL, %s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, %s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, %s TEXT NOT NULL)",
                TABLE_NAME, UserTable._ID, COL_USERNAME, COL_PASSWORD,
                COL_FIRST_NAME, COL_LAST_NAME, COL_PRESINCT, COL_RANK);

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String[] KEY_ARRAY = {
                COL_USERNAME, COL_PASSWORD,
                COL_FIRST_NAME, COL_LAST_NAME, COL_PRESINCT, COL_RANK
        };
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