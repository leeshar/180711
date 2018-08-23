package com.icia.aboard2.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icia.aboard2.dao.TravelRepository;
import com.icia.aboard2.dto.TravelDto.CreateTravel;
import com.icia.aboard2.dto.TravelDto.UpdateTravel;
import com.icia.aboard2.entity.Travel;

@Service
public class TravelService {
	@Autowired
	private TravelRepository travelDao;
	
	public void createTravel(CreateTravel travel) {
		travelDao.createTravel(travel);
	}
	public List<Travel> listTravel(String id) {
		return travelDao.listTravel(id);
	}
	public void updateTravel(UpdateTravel travel) {
		travelDao.updateTravel(travel);
	}
	public void deleteTravel(String travelBno) {
		travelDao.deleteTravel(travelBno);
	}
	public Map<String, Object> detailTravel(String travelBno){
		return travelDao.detailTravel(travelBno);
	}
}
