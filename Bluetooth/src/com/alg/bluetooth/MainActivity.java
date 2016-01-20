package com.alg.bluetooth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity implements OnItemClickListener{
	private static final int REQUEST_ENABLE_BT = 1;
	private static final String UUID_CODE = "00001101-0000-1000-8000-00805F9B34FB";
	
	ArrayAdapter<String> mArrayAdapter;
	ArrayList<BluetoothDevice> devicesBt;

	TextView tvMensaje;
	ListView lvLista;
	Button btLed1;
	Button btLed2;
	
	BluetoothAdapter mBluetoothAdapter;
	BluetoothSocket socket;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tvMensaje = (TextView)findViewById(R.id.txvMensaje);
		lvLista = (ListView)findViewById(R.id.ltvLista);
		btLed1 = (Button)findViewById(R.id.btnLed1);
		btLed2 = (Button)findViewById(R.id.btnLed2);
		
		lvLista.setOnItemClickListener(this);
		
		btLed1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					socket.getOutputStream().write(49);
					Log.d("MainActivity -- ", "Boton LED 1 presionado");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		btLed2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					socket.getOutputStream().write(50);
					Log.d("MainActivity -- ", "Boton LED 2 presionado");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		devicesBt = new ArrayList<BluetoothDevice>();
		mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		lvLista.setAdapter(mArrayAdapter);
		

		//HABILITANDO EL ADAPTADOR--------------------------------------------------
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			// Device does not support Bluetooth
			tvMensaje.setText("Status: not supported");
		}
		//--------------------------------------------------------------------------
		
		//CONFIRMANDO ADAPTADOR HABILITADO -----------------------------------------
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
		//---------------------------------------------------------------------------
		
		//CREANDO LISTA DE DISPOSITIVOS CONFIABLES ----------------------------------
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
			// Loop through paired devices
			for (BluetoothDevice device : pairedDevices) {
				// Add the name and address to an array adapter to show in a ListView
				mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
				devicesBt.add(device);
			}
			
		}
		//--------------------------------------------------------------------------------
		
		//AÑADIENDO DISPOSITIVOS NUEVOS --------------------------------------------------
		final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		    public void onReceive(Context context, Intent intent) {
		        String action = intent.getAction();
		        // When discovery finds a device
		        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
		            // Get the BluetoothDevice object from the Intent
		            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		            // Add the name and address to an array adapter to show in a ListView
		            mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
		            devicesBt.add(device);
		            Log.d("MainActivity -- Broadcast", device.getName());
		        }
		    }
		};
		// Register the BroadcastReceiver
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
		//-------------------------------------------------------------------------------------
		
		mBluetoothAdapter.startDiscovery();
		
		

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		try{
			BluetoothDevice btDev = devicesBt.get(position);
			socket = btDev.createInsecureRfcommSocketToServiceRecord(UUID.fromString(UUID_CODE));
			socket.connect();
			Log.d("MainActivity --", "socket conectado");
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	
}
