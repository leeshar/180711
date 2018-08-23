package com.icia.aboard2.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.aboard2.dto.TravelDto.CreateTravel;
import com.icia.aboard2.dto.TravelDto.UpdateTravel;
import com.icia.aboard2.service.TravelService;

@RestController
public class TravelController {
	@Autowired
	private TravelService service;
	@Autowired
	private ObjectMapper mapper;
	
	
	@RequestMapping("/travel/create")
	public void createTravel(String createTravel) throws ParseException {
		JSONObject obj = jsonParser(createTravel);
		CreateTravel travel = new CreateTravel();
		travel.setContent(obj.get("content").toString());
		travel.setId(obj.get("id").toString());
		travel.setTitle(obj.get("title").toString());
		service.createTravel(travel);
	}
	@RequestMapping(value="/travel/listTravel/{id}", produces = "application/json; charset=UTF-8" )
	public String listTravel(@PathVariable String id) throws ParseException, JsonProcessingException {
		return mapper.writeValueAsString(service.listTravel(id));
	}
	@RequestMapping("/travel/update")
	public void updateTravel(String updateTravel) throws ParseException, java.text.ParseException {
		JSONObject obj = jsonParser(updateTravel);
		UpdateTravel travel = new UpdateTravel();
		travel.setContent(obj.get("content").toString());
		travel.setTitle(obj.get("title").toString());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(obj.get("travelStartDate").toString());
		Date endDate = df.parse(obj.get("travelEndDate").toString());
		travel.setTravelBno(obj.get("travelBno").toString());
		travel.setTravelStartDate(startDate);
		travel.setTravelEndDate(endDate);
		System.out.println(startDate);
		service.updateTravel(travel);
	}
	@RequestMapping(value="/travel/detail/{travelBno}", produces = "application/json; charset=UTF-8" )
	public String detailTravel(@PathVariable String travelBno) throws JsonProcessingException {
		return mapper.writeValueAsString(service.detailTravel(travelBno));
	}
	@RequestMapping("/travel/delte")
	public void deleteTravel(String travelBno) {
		service.deleteTravel(travelBno);
	}
	private JSONObject jsonParser(String unrecommend) throws ParseException {
		JSONParser jsonparser = new JSONParser();
		JSONObject obj = (JSONObject) jsonparser.parse(unrecommend);
		return obj;
	}
}
