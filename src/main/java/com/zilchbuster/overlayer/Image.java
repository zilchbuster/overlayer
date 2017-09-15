package com.zilchbuster.overlayer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import lombok.Data;

@Data
@Entity
public class Image {
	private @Id @GeneratedValue Long id;
	private  @Column(length=1024) String imagePath1;
	private  @Column(length=1024) String imagePath2;
	private  @Column(length=1024) String token;

	private Image() {}

	public Image(String imagePath1, String imagePath2, String token) {
		this.imagePath1 = imagePath1;
		this.imagePath1 = imagePath2;
		this.token = token;
	}
	
	public Long getId() {
		return id;
	}
}
