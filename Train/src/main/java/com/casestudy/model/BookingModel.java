package com.casestudy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="ticket")
public class BookingModel {

	@Id
	private int pnrId;
	private String userId;
	private String name;
	private String phnnumber;
	private String email;
	private String trainNo;
	private String trainName;
	private String from;
	private String to;
	private String date;
	private int totalseats;
	
	
	public BookingModel() {
		super();
	}
	
	public BookingModel(String userId, String name, String phnnumber, String email, String trainNo, String trainName,
			String from, String to, String date, int totalseats) {
		super();
		this.pnrId=1000 + (int)(Math.random() * ((10000 - 1000) + 1));
		this.userId = userId;
		this.name = name;
		this.phnnumber = phnnumber;
		this.email = email;
		this.trainNo = trainNo;
		this.trainName = trainName;
		this.from = from;
		this.to = to;
		this.date = date;
		this.totalseats = totalseats;
	}
	
	public int getPnrId() {
		return pnrId;
	}

	public void setPnrId(int pnrId) {
		this.pnrId = pnrId;
	}

	public int getTotalseats() {
		return totalseats;
	}

	public void setTotalseats(int totalseats) {
		this.totalseats = totalseats;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhnnumber() {
		return phnnumber;
	}
	public void setPhnnumber(String phnnumber) {
		this.phnnumber = phnnumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTrainNo() {
		return trainNo;
	}
	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}
	public String getTrainName() {
		return trainName;
	}
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "BookingModel [userId=" + userId + ", name=" + name + ", phnnumber=" + phnnumber + ", email=" + email
				+ ", trainNo=" + trainNo + ", trainName=" + trainName + ", from=" + from + ", to=" + to + ", date="
				+ date + ", totalseats=" + totalseats + "]";
	}

	
}
