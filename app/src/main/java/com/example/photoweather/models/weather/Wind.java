package com.example.photoweather.models.weather;

import com.google.gson.annotations.SerializedName;

public class Wind {

	@SerializedName("deg")
	private int deg;

	@SerializedName("speed")
	private double speed;

	public int getDeg(){
		return deg;
	}

	public double getSpeed(){
		return speed;
	}

	@Override
 	public String toString(){
		return 
			"Wind{" + 
			"deg = '" + deg + '\'' + 
			",speed = '" + speed + '\'' + 
			"}";
		}
}