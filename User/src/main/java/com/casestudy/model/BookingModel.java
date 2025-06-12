package com.casestudy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="ticket")
public class BookingModel {
	
	@Id
	private String pnrId;
	private String userId;
	private String name;
	private String phnnumber;
	private String email;
	private String trainNo;
	private String trainName;
	private String trainFrom;
	private String trainTo;
	private String date;
	private String time;
	private int totalseats;
	private int fare;
	
	public BookingModel() {
		super();
	}
	
	public BookingModel(String pnrId, String userId, String name, String phnnumber, String email, String trainNo,
			String trainName, String trainFrom, String trainTo, String date, String time, int totalseats, int fare) {
		super();
		this.pnrId = pnrId;
		this.userId = userId;
		this.name = name;
		this.phnnumber = phnnumber;
		this.email = email;
		this.trainNo = trainNo;
		this.trainName = trainName;
		this.trainFrom = trainFrom;
		this.trainTo = trainTo;
		this.date = date;
		this.time = time;
		this.totalseats = totalseats;
		this.fare = fare;
	}
	
	
	public String getPnrId() {
		return pnrId;
	}
	
	public void setPnrId(String pnrId) {
		this.pnrId = pnrId;
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
	public String getTrainFrom() {
		return trainFrom;
	}
	
	public void setTrainFrom(String trainFrom) {
		this.trainFrom = trainFrom;
	}
	
	public String getTrainTo() {
		return trainTo;
	}
	
	public void setTrainTo(String trainTo) {
		this.trainTo = trainTo;
	}
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public int getTotalseats() {
		return totalseats;
	}
	
	public void setTotalseats(int totalseats) {
		this.totalseats = totalseats;
	}
	
	public int getFare() {
		return fare;
	}
	
	public void setFare(int fare) {
		this.fare = fare;
	}
	
	@Override
	public String toString() {
		return "BookingModel [pnrId=" + pnrId + ", userId=" + userId + ", name=" + name + ", phnnumber=" + phnnumber
				+ ", email=" + email + ", trainNo=" + trainNo + ", trainName=" + trainName + ", trainFrom=" + trainFrom
				+ ", trainTo=" + trainTo + ", date=" + date + ", time=" + time + ", totalseats=" + totalseats
				+ ", fare=" + fare + "]";
	}
	
	
}
