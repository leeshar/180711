package com.icia.aboard2.dao;

import java.util.List;
import java.util.Map;

import com.icia.aboard2.dto.TravelDto.CreateTravel;
import com.icia.aboard2.dto.TravelDto.UpdateTravel;
import com.icia.aboard2.entity.Travel;

public interface TravelRepository {
	//C
	public void createTravel(CreateTravel travel);
	//R
	public List<Travel> listTravel(String id);
	//U
	public void updateTravel(UpdateTravel travel);
	//D
	public void deleteTravel(String travelBno);
	//detail
	public Map<String, Object> detailTravel(String travelBno);
}
