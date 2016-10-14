package com.djmaraat.apps.policelog.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.djmaraat.apps.policelog.Entities.Vehicle;
import com.djmaraat.apps.policelog.PoliceLogDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by djanemaraat on 14/10/2016.
 */
public class VehicleAdapter extends ArrayAdapter<Vehicle> {
    private List<Vehicle> vehicleList = new ArrayList<>();
    PoliceLogDBHelper databaseHelper;

    public VehicleAdapter(Activity context, List<Vehicle> objects, PoliceLogDBHelper databaseHelper) {
        super(context, 0, objects);
        this.databaseHelper = databaseHelper;
        vehicleList = objects;
    }

    @Override
    public int getCount() {
        return this.vehicleList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public Vehicle getItem(int position) {
        return this.vehicleList.get(position);
    }

    private class VehicleViewHolder {
        TextView labelOwnerName, labelDriverName, labelMake, labelColor;
        ImageView imageIcon;
        ImageButton imgBtnEdit, imgBtnDelete;
    }
}
