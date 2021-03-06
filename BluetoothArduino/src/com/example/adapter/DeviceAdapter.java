package com.example.adapter;

import java.util.ArrayList;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bluetootharduino.R;

public class DeviceAdapter extends ArrayAdapter<BluetoothDevice>{

	private Context context;
	private ArrayList<BluetoothDevice> btList;

	public DeviceAdapter(Context ctx, int resource, ArrayList<BluetoothDevice> list) {
		super(ctx, resource, list);
		// TODO Auto-generated constructor stub
		context = ctx;
		btList = list;
		Log.d("DeviceAdapter -- ", ""+btList.size());
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vItem = LayoutInflater.from(context).inflate(R.layout.items, null);
		TextView name = (TextView)vItem.findViewById(R.id.txvName);
		TextView mac = (TextView)vItem.findViewById(R.id.txvMac);
		
		name.setText(""+btList.get(position).getName());
		mac.setText(""+btList.get(position).getAddress());
		
		return vItem;
	}

	
	

}
