package com.icia.aboard2.dto;

import java.util.Date;

import lombok.Data;

public class TravelDto {
	@Data
	public static class CreateTravel{
		private String title;
		private String content;
		private String id;
	}
	@Data
	public static class UpdateTravel{
		private String travelBno;
		private String title;
		private String content;
		private Date travelStartDate;
		private Date travelEndDate;
	}
	@Data
	public static class ListTravel{
		private String travelBno;
		private String title;
		private String content;
		private Date travelStartDate;
		private Date travelEndDate;
	}
}
