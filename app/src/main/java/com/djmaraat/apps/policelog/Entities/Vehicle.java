package com.djmaraat.apps.policelog.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by djanemaraat on 8/4/16.
 */
public class Vehicle implements Parcelable{

    private int mData;

    public int id;
    public String owner_name, driver_name, make, color, official_receipt, cert_of_reg, engine_number,
            chassis_number, contact_number, address, checkpoint_loc, date_logged;

    public Vehicle (String[] vehicleDetails) {
        id = Integer.valueOf(vehicleDetails[0]);
        owner_name = vehicleDetails[1];
        driver_name = vehicleDetails[2];
        make = vehicleDetails[3];
        color = vehicleDetails[4];
        official_receipt = vehicleDetails[5];
        cert_of_reg = vehicleDetails[6];
        engine_number = vehicleDetails[7];
        chassis_number = vehicleDetails[8];
        contact_number = vehicleDetails[9];
        address = vehicleDetails[10];
        checkpoint_loc = vehicleDetails[11];
        date_logged = vehicleDetails[12];
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(owner_name);
        out.writeString(driver_name);
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

        mData = in.readInt();
    }
}
