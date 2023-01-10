package com.example.closestdevicesbluetoothdetector.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.closestdevicesbluetoothdetector.R;

class ViewHolder extends RecyclerView.ViewHolder {
    TextView bluetoothName;
    TextView bluetoothDistance;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        bluetoothName = itemView.findViewById(R.id.bluetoothItem_TXT_name);
        bluetoothDistance = itemView.findViewById(R.id.bluetoothItem_TXT_distance);


    }
}
