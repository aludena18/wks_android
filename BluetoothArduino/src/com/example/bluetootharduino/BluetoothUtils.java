package com.example.bluetootharduino;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class BluetoothUtils {

	BluetoothAdapter mBluetoothAdapter;
	BluetoothSocket socket;

	ArrayList<BluetoothDevice> bluetoothList;

	private static final String UUID_CODE = "00001101-0000-1000-8000-00805F9B34FB";

	public BluetoothUtils(){
		bluetoothList = new ArrayList<BluetoothDevice>();

		//HABILITAMOS EL ADAPTADOR --------------------------------
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			Log.d("BluetoothUtils -- ","Status: not supported");
			return;
		}


		//CREANDO LISTA DE DISPOSITIVOS CONFIABLES ----------------------------------
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				//mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
				bluetoothList.add(device);
				Log.d("BluetoothUtils -- pairedDevices", device.getName());
			}
		}

		/*
		//AÑADIENDO DISPOSITIVOS NUEVOS --------------------------------------------------
		final BroadcastReceiver mReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				// When discovery finds a device
				if (BluetoothDevice.ACTION_FOUND.equals(action)) {
					// Get the BluetoothDevice object from the Intent
					BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					// Add the name and address to an array adapter to show in a ListView
					//mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
					bluetoothList.add(device);
					Log.d("MainActivity -- Broadcast", device.getName());
				}
			}
		};
		
		// Register the BroadcastReceiver
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
		//-------------------------------------------------------------------------------------*/
		//mBluetoothAdapter.startDiscovery();



	}

	
	public boolean isEnabled(){
		if (mBluetoothAdapter==null) return true;
		return false;
	}


	
	public boolean connect(int position){
		if (position<0 || position>=bluetoothList.size())
			return false;

		try{
			BluetoothDevice btDev = bluetoothList.get(position);
			socket = btDev.createInsecureRfcommSocketToServiceRecord(UUID.fromString(UUID_CODE));
			socket.connect();
			Log.d("MainActivity --", "socket conectado");
			return true;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	public boolean isConnected(){
		if(socket!=null) return socket.isConnected();
		return false;
	}
	
	
	public void send(int data){
		try {
			socket.getOutputStream().write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<BluetoothDevice> getArrayList(){
		return bluetoothList;
	}
	

}
