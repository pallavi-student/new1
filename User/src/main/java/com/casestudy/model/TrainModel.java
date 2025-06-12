package com.casestudy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="trainsdata")
public class TrainModel {
	
	@Id
	public String trainNo;
	public String trainName;
	public String trainFrom;
	public String trainTo;
	public int fare;
	public int seats;
	public String time;
	
	
	public TrainModel() {
		super();
	}

	
	public TrainModel(String trainNo, String trainName, String trainFrom, String trainTo, int fare, int seats,
			String time) {
		super();
		this.trainNo = trainNo;
		this.trainName = trainName;
		this.trainFrom = trainFrom;
		this.trainTo = trainTo;
		this.fare = fare;
		this.seats = seats;
		this.time = time;
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

	public int getFare() {
		return fare;
	}

	public void setFare(int fare) {
		this.fare = fare;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "TrainModel [trainNo=" + trainNo + ", trainName=" + trainName + ", trainFrom="
				+ trainFrom + ", trainTo=" + trainTo + ", fare=" + fare + ", seats=" + seats + ", time=" + time + "]";
	}
	
	
}

