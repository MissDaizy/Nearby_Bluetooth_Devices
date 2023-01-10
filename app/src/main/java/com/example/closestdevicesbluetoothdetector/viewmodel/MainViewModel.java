package com.example.closestdevicesbluetoothdetector.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.closestdevicesbluetoothdetector.data.model.Bluetooth;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    MutableLiveData<ArrayList<Bluetooth>> bluetoothLiveData;
    ArrayList<Bluetooth> bluetoothArrayList;

    public MainViewModel() {
        bluetoothLiveData = new MutableLiveData<>();

        // call your Rest API in init method
        init();
    }

    public MutableLiveData<ArrayList<Bluetooth>> getBluetoothMutableList(){
        return bluetoothLiveData;
    }

    public void init(){
        populateList();
        bluetoothLiveData.setValue(bluetoothArrayList);
    }

    public void populateList(){

        Bluetooth bluetooth = new Bluetooth();
        bluetooth.setName("Darknight");
        bluetooth.setDistance("Best rating movie");

        bluetoothArrayList = new ArrayList<>();
        bluetoothArrayList.add(bluetooth);
        bluetoothArrayList.add(bluetooth);
        bluetoothArrayList.add(bluetooth);
        bluetoothArrayList.add(bluetooth);
        bluetoothArrayList.add(bluetooth);
        bluetoothArrayList.add(bluetooth);
    }
}