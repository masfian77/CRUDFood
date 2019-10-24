package com.imastudio.crudfood.model_login;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin {

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("data")
	private DataLogin dataLogin;

	@SerializedName("sukses")
	private boolean sukses;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataLogin(DataLogin dataLogin){
		this.dataLogin = dataLogin;
	}

	public DataLogin getDataLogin(){
		return dataLogin;
	}

	public void setSukses(boolean sukses){
		this.sukses = sukses;
	}

	public boolean isSukses(){
		return sukses;
	}
}