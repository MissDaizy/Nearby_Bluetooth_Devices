package com.example.closestdevicesbluetoothdetector.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.closestdevicesbluetoothdetector.R;
import com.example.closestdevicesbluetoothdetector.data.model.Bluetooth;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<Bluetooth> bluetoothArrayList;

    public ListAdapter() {
        this.bluetoothArrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooth_item,parent,false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Bluetooth bluetooth = bluetoothArrayList.get(position);
        ViewHolder viewHolder= (ViewHolder) holder;

        viewHolder.bluetoothName.setText(bluetooth.getName());
        viewHolder.bluetoothDistance.setText(bluetooth.getDistance());
    }

    @Override
    public int getItemCount() {
        return bluetoothArrayList.size();
    }
    public void updateBluetoothList(final ArrayList<Bluetooth> bluetoothList) {
        this.bluetoothArrayList.clear();
        this.bluetoothArrayList = bluetoothList;
        notifyDataSetChanged();
    }
}
