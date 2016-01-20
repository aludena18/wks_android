package com.alg.adapter;

import java.util.ArrayList;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alg.androidbtcontrol.R;

public class DeviceBtAdapter extends ArrayAdapter<BluetoothDevice>{
	LayoutInflater layoutInf;
	ArrayList<BluetoothDevice> btList;

	public DeviceBtAdapter(Context context, int resource,
			ArrayList<BluetoothDevice> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub
		layoutInf = LayoutInflater.from(context);
		btList = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View viewItem = layoutInf.inflate(R.layout.activity_items_bt, null);
		
		TextView name = (TextView)viewItem.findViewById(R.id.txvName);
		TextView address = (TextView)viewItem.findViewById(R.id.txvAddress);
		
		name.setText(""+btList.get(position).getName());
		address.setText(""+btList.get(position).getAddress());
		
		return viewItem;
	}
	
	
	

}
