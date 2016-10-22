/*
 *
 * Copyright 2016 Dandeljane Maraat
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 *  is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *  express or implied. See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.djmaraat.apps.policelog.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Vehicle implements Parcelable{

    private int mData;

    public int id, checked_by;
    public String owner_name, driver_name, driver_license_num, make, color, engine_number,
            chassis_number, contact_number, address, checkpoint_loc, date_logged, official_receipt, cert_of_reg ;

    public Vehicle (String[] vehicleDetails) {
        if (vehicleDetails != null) {
            id = Integer.valueOf(vehicleDetails[0]);
            owner_name = vehicleDetails[1];
            driver_name = vehicleDetails[2];
            driver_license_num = vehicleDetails[3];
            make = vehicleDetails[4];
            color = vehicleDetails[5];
            official_receipt = vehicleDetails[6];
            cert_of_reg = vehicleDetails[7];
            engine_number = vehicleDetails[8];
            chassis_number = vehicleDetails[9];
            contact_number = vehicleDetails[10];
            address = vehicleDetails[11];
            checkpoint_loc = vehicleDetails[12];
            date_logged = vehicleDetails[13];
            checked_by = Integer.valueOf(vehicleDetails[14]);
        }

    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(owner_name);
        out.writeString(driver_name);
        out.writeString(driver_license_num);
        out.writeString(make);
        out.writeString(color);
        out.writeString(official_receipt);
        out.writeString(cert_of_reg);
        out.writeString(engine_number);
        out.writeString(chassis_number);
        out.writeString(contact_number);
        out.writeString(address);
        out.writeString(checkpoint_loc);
        out.writeString(date_logged);
        out.writeInt(checked_by);

        out.writeInt(mData);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Vehicle> CREATOR = new Parcelable.Creator<Vehicle>() {
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };

    private Vehicle(Parcel in) {
        id = in.readInt();
        owner_name = in.readString();
        driver_name = in.readString();
        driver_license_num = in.readString();
        make = in.readString();
        color = in.readString();
        official_receipt = in.readString();
        cert_of_reg = in.readString();
        engine_number = in.readString();
        chassis_number = in.readString();
        contact_number = in.readString();
        address = in.readString();
        checkpoint_loc = in.readString();
        date_logged = in.readString();
        checked_by = in.readInt();

        mData = in.readInt();
    }
}
