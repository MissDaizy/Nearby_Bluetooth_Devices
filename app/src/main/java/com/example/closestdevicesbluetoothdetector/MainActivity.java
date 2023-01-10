package com.example.closestdevicesbluetoothdetector;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.closestdevicesbluetoothdetector.databinding.ActivityMainBinding;
import com.example.closestdevicesbluetoothdetector.data.model.Bluetooth;
import com.example.closestdevicesbluetoothdetector.view.ListAdapter;
import com.example.closestdevicesbluetoothdetector.viewmodel.MainViewModel;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {
    private  BroadcastReceiver mReceiver;
    private ActivityMainBinding activityMainBinding;
    private RecyclerView recyclerView;
    private ListAdapter myListAdapter;
    private BluetoothAdapter bluetoothAdapter;
    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        setAdapter();
        setViewModel();
        setListeners();

        checkBluetoothPermission();

        findDevices();
        displayListOfFoundDevices();
    }

    private void findDevices() {
        IntentFilter bluetoothFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, bluetoothFilter);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        bluetoothAdapter.startDiscovery();
    }

    Observer<ArrayList<Bluetooth>> bluetoothListUpdateObserver = new Observer<ArrayList<Bluetooth>>() {

        @Override
        public void onChanged(ArrayList<Bluetooth> bluetoothArrayList) {
            myListAdapter.updateBluetoothList(bluetoothArrayList);
        }
    };


    private void setViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getBluetoothMutableList().observe(this, bluetoothListUpdateObserver);
    }

    private void setAdapter() {
        myListAdapter = new ListAdapter();
        activityMainBinding.activityMainRVBluetoothDevices.setAdapter(myListAdapter);
    }

    private void checkBluetoothPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                return;
            }
        }
        connect();
    }

    private void connect() {
        BluetoothManager bluetoothManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            bluetoothManager = getSystemService(BluetoothManager.class);
        }
        bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        } else if (!bluetoothAdapter.isEnabled()) {
            openBluetoothActivityForResult();
        }
    }


    private void setListeners() {
        activityMainBinding.activityMainBTNStartScan.setOnClickListener(v -> displayListOfFoundDevices());
    }


    private void setViews() {
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        recyclerView = activityMainBinding.activityMainRVBluetoothDevices;
    }

    public void openBluetoothActivityForResult() {
        Intent enableBtIntent = new Intent(android.bluetooth.BluetoothAdapter.ACTION_REQUEST_ENABLE);
        someActivityResultLauncher.launch(enableBtIntent);
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        IntentFilter bluetoothFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                        getApplicationContext().registerReceiver(mReceiver, bluetoothFilter);
                        final android.bluetooth.BluetoothAdapter mBluetoothAdapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter();
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        mBluetoothAdapter.startDiscovery();
                        //  doSomeOperations();
                    }
                }
            });

    private void displayListOfFoundDevices() {

         mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {

                //if you have found the devices
                if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {

                    // Now that you have found the device. Get the Bluetooth Device
                    // object and its info from the Intent.

                    BluetoothDevice deviceInfo = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    String deviceName = deviceInfo.getName();
                    ArrayList<Bluetooth> list = new ArrayList<>();
                    list = viewModel.getBluetoothMutableList().getValue();
                    list.add(new Bluetooth().setName(bluetoothAdapter.getName()).setDistance(bluetoothAdapter.getAddress()));
                    // TODO : continue from here
//                list.add(new Bluetooth(bluetoothAdapter.getName(),bluetoothAdapter.getAddress().toString());
//               (toString()));

                    bluetoothListUpdateObserver.onChanged(list);


                    //Make sure you update your arraylist/recyclerlist adapter from here. As it
                    //invokes for every found device once.

                }
            }
        };
    }


}