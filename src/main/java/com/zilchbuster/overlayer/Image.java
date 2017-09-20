package com.zilchbuster.overlayer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.UUID;

import javax.persistence.Column;
import lombok.Data;

@Data
@Entity
public class Image {
	private @Id @GeneratedValue Long id;
	private  @Column(length=1024) String token;

	public Image() {
		this.token = UUID.randomUUID().toString();
	}
	
	public Image(String uuid) {
		this.token = uuid;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getToken(){
		return this.token;
	}
}
