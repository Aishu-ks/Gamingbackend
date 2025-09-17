package com.asphalt.gamingsystem;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "members")
public class Members {
	
	@Id
	private String id;
	
	private String name;
	
	private String phone;
	


    private float balance;
    
 
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getphone() {
		return phone;
	}

	public void setphone(String phone) {
		this.phone = phone;
	}

	
	public float getbalance() {
		return balance;
	}

	public void setbalance(float balance) {
		this.balance = balance;
	}


	}
    
    
