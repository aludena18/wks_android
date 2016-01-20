package com.alg.androidbtcontrol;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class BluetoothClass implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9007369468139151607L;
	BluetoothAdapter btAdapter;
	BluetoothSocket btSocket;

	ArrayList<BluetoothDevice> btList;

	private static final String UUID_CODE = "00001101-0000-1000-8000-00805F9B34FB";


	public BluetoothClass(){
		btList = new ArrayList<BluetoothDevice>();

		//HABILITAMOS EL ADAPTADOR --------------------------------
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		if (btAdapter == null) {
			Log.d("BluetoothUtils -- ","Status: not supported");
			return;
		}



		//CREANDO LISTA DE DISPOSITIVOS CONFIABLES ----------------------------------
		Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				//mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
				btList.add(device);
				Log.d("BluetoothUtils -- pairedDevices", device.getName());
			}
		}
	}
	
	
	
	
	
	public boolean isEnabled(){
		if(btAdapter!=null) return true;
		return false;
	}
	
	
	
	
	
	public void connect(int position){
		if (position<0 || position>=btList.size())
			return;

		try{
			BluetoothDevice btDev = btList.get(position);
			btSocket = btDev.createInsecureRfcommSocketToServiceRecord(UUID.fromString(UUID_CODE));
			btSocket.connect();
			Log.d("BluetoothClass --", "socket conectado");
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public boolean isConnected(){
		if(!btSocket.isConnected()) return false;
		return true;
	}
	
	
	
	
	public void send(int data){
		try {
			btSocket.getOutputStream().write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public ArrayList<BluetoothDevice> getArrayList(){
		return btList;
	}

}
