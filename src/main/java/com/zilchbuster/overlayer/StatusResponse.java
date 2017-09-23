package com.zilchbuster.overlayer;

import com.zilchbuster.overlayer.StatusEnum;

public class StatusResponse {
	private StatusEnum status;
	private String message;
	private String token;
	
	public StatusResponse(StatusEnum status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public StatusResponse(StatusEnum status) {
		this.status = status;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public StatusEnum getStatus(){
		return this.status;
	}
}
