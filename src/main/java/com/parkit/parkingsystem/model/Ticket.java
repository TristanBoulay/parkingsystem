package com.parkit.parkingsystem.model;

import java.util.Date;

public class Ticket {
	private int id;
	private ParkingSpot parkingSpot;
	private String vehicleRegNumber;
	private double price;
	private Date inTime;
	private Date outTime;

//	Ajout d'un attribut de type boolean a Ticket
	private boolean alreadyVisited;

	public boolean isAlreadyVisited() {
		return this.alreadyVisited;
	}

	public void setAlreadyVisited(boolean alreadyVisited) {
		this.alreadyVisited = alreadyVisited;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ParkingSpot getParkingSpot() {
		return this.parkingSpot;
	}

	public void setParkingSpot(ParkingSpot parkingSpot) {
		this.parkingSpot = parkingSpot;
	}

	public String getVehicleRegNumber() {
		return this.vehicleRegNumber;
	}

	public void setVehicleRegNumber(String vehicleRegNumber) {
		this.vehicleRegNumber = vehicleRegNumber;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getInTime() {
		return this.inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public Date getOutTime() {
		return this.outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	@Override
	public String toString() {
		return "Ticket {" + "price=" + this.getPrice() + ", outTime =" + this.getOutTime() + '}';
	}
}
