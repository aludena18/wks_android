package com.example.bluetootharduino;

import java.util.ArrayList;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.adapter.DeviceAdapter;

public class MainActivity extends Activity implements OnItemClickListener, OnClickListener{

	ArrayAdapter<String> mArrayAdapter;

	TextView tvMensaje;
	ListView lvLista;
	Button btLed1;
	
	BluetoothUtils bluetooth;
	
	private static final int REQUEST_ENABLE_BT = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bluetooth = new BluetoothUtils();

		tvMensaje = (TextView)findViewById(R.id.txvMensaje);
		lvLista = (ListView)findViewById(R.id.ltvLista);
		btLed1 = (Button)findViewById(R.id.btnLed1);
		
		lvLista.setOnItemClickListener(this);
		btLed1.setOnClickListener(this);
		
		
		//mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		ArrayList<BluetoothDevice> lista = bluetooth.getArrayList();
		Log.d("MainActivity -- line54", "" + lista.size());
		DeviceAdapter deviceAdapter = new DeviceAdapter(this, 0, lista);
		//lvLista.setAdapter(mArrayAdapter);
		lvLista.setAdapter(deviceAdapter);

		
		//CONFIRMANDO ADAPTADOR HABILITADO
		if (!bluetooth.isEnabled()) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (bluetooth.connect(position)){
			Toast.makeText(this, "Dispositivo Conectado", Toast.LENGTH_LONG).show();
		}
		else{
			Toast.makeText(this, "No conectado", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(!bluetooth.isConnected()){
			Toast.makeText(this, "Debe conectarse primero", Toast.LENGTH_LONG).show();
			return;
		}
		
		switch (v.getId()) {
		case R.id.btnLed1:
			bluetooth.send(49);
			break;

		default:
			break;
		}
	}
}
