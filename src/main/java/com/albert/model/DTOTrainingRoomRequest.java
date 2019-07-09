package com.albert.model;


public class DTOTrainingRoomRequest {
	private Long trainingRoomId;
	private String startDate;
	private String endDate;
	private String roomDesc;
	
	
	public Long getTrainingRoomId() {
		return trainingRoomId;
	}
	public void setTrainingRoomId(Long trainingRoomId) {
		this.trainingRoomId = trainingRoomId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getRoomDesc() {
		return roomDesc;
	}
	public void setRoomDesc(String roomDesc) {
		this.roomDesc = roomDesc;
	}

}
