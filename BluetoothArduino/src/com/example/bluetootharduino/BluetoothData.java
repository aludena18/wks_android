package com.example.bluetootharduino;

public class BluetoothData {
	
	private String btName;
	private String btMac;
	
	public BluetoothData(String name, String mac){
		this.btName = name;
		this.btMac = mac;
	}
	
	public String getBtName() {
		return btName;
	}
	public void setBtName(String btName) {
		this.btName = btName;
	}
	public String getBtMac() {
		return btMac;
	}
	public void setBtMac(String btMac) {
		this.btMac = btMac;
	}
	
	

}
