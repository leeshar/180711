package com.myPro.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class TravelController {
	@Autowired
	private ObjectMapper mapper;
	// 숙박 정보
	@RequestMapping(value= "/travel/areaSearch/{areaCode}/{sigunguCode}/{page}", produces = "application/json; charset=UTF-8" )
	public String staySearch(@PathVariable String areaCode,@PathVariable String sigunguCode,@PathVariable String page) throws ParseException, JsonProcessingException {
		String result ="";
		String urlstr;
		BufferedReader br = null;
		try {
			urlstr ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchStay?ServiceKey=gnU6hW6oBRyWh4Gc%2FbebNXyArGz5gwRBjXu8wq7O%2BWPpKZslklvmAXNfJhsVmtq%2B40XXQIgeXzpX9NGWErXj3Q%3D%3D&contentTypeId=12&areaCode="+areaCode+"&sigunguCode="+sigunguCode+"&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=12&pageNo="+page;
			URL url = new URL(urlstr);
			System.out.println(urlstr);
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
			String line;
			while((line = br.readLine()) != null) {
				result = result + line + "\n";
			}
			System.out.println(result);
			return mapper.writeValueAsString(result);
		}
			catch(Exception e) {
				
				System.out.println(e.getMessage());
			}
		System.out.println(result);
		return mapper.writeValueAsString(result);
		
}	
	// 숙박 정보 시군구 코드가 없는 경우
		@RequestMapping(value= "/travel/areaSearch/{areaCode}/{page}", produces = "application/json; charset=UTF-8" )
		public String staySearch(@PathVariable String areaCode,@PathVariable String page) throws ParseException, JsonProcessingException {
			String result ="";
			String urlstr;
			BufferedReader br = null;
			try {
				urlstr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchStay?ServiceKey=gnU6hW6oBRyWh4Gc%2FbebNXyArGz5gwRBjXu8wq7O%2BWPpKZslklvmAXNfJhsVmtq%2B40XXQIgeXzpX9NGWErXj3Q%3D%3D&contentTypeId=12&areaCode="+areaCode+"&sigunguCode=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=12&pageNo="+page;
				URL url = new URL(urlstr);
				System.out.println(urlstr);
				HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
				urlconnection.setRequestMethod("GET");
				br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
				String line;
				while((line = br.readLine()) != null) {
					result = result + line + "\n";
				}
				System.out.println(result);
				return mapper.writeValueAsString(result);
			}
				catch(Exception e) {
					
					System.out.println(e.getMessage());
				}
			System.out.println(result);
			return mapper.writeValueAsString(result);
			
	}	
	//관광지 정보
	@RequestMapping(value="/travel/tourSearch/{areaCode}/{sigunguCode}/{page}", produces = "application/json; charset=UTF-8" )
	public String tourSearch(@PathVariable String areaCode,@PathVariable String sigunguCode, @PathVariable String page) throws ParseException, JsonProcessingException {
		String result ="";
		BufferedReader br = null;
		try {
			String urlstr ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey=gnU6hW6oBRyWh4Gc%2FbebNXyArGz5gwRBjXu8wq7O%2BWPpKZslklvmAXNfJhsVmtq%2B40XXQIgeXzpX9NGWErXj3Q%3D%3D&contentTypeId=12&areaCode="+areaCode+"&sigunguCode="+sigunguCode+"&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=12&pageNo="+page;
			URL url = new URL(urlstr);
			System.out.println(urlstr);
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
			String line;
			while((line = br.readLine()) != null) {
				result = result + line + "\n";
			}
			System.out.println(result);
			return mapper.writeValueAsString(result);
		}
			catch(Exception e) {
				
				System.out.println(e.getMessage());
			}
		System.out.println(result);
		return mapper.writeValueAsString(result);
		
}	
	@RequestMapping(value="/travel/tourSearch/{areaCode}/{page}", produces = "application/json; charset=UTF-8" )
	public String tourSearch(@PathVariable String areaCode, @PathVariable String page) throws ParseException, JsonProcessingException {
		String result ="";
		BufferedReader br = null;
		try {
			String urlstr ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey=gnU6hW6oBRyWh4Gc%2FbebNXyArGz5gwRBjXu8wq7O%2BWPpKZslklvmAXNfJhsVmtq%2B40XXQIgeXzpX9NGWErXj3Q%3D%3D&contentTypeId=12&areaCode="+areaCode+"&sigunguCode=&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=12&pageNo="+page;
			URL url = new URL(urlstr);
			System.out.println(urlstr);
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
			String line;
			while((line = br.readLine()) != null) {
				result = result + line + "\n";
			}
			System.out.println(result);
			return mapper.writeValueAsString(result);
		}
			catch(Exception e) {
				
				System.out.println(e.getMessage());
			}
		System.out.println(result);
		return mapper.writeValueAsString(result);
		
}	
	// 숙박 내용 상세 보기
	@RequestMapping(value="/travel/stayDetail/{contentId}", produces = "application/json; charset=UTF-8")
	public String stayDetail(@PathVariable String contentId) throws JsonProcessingException {
		String result ="";
		BufferedReader br = null;
		String contentTypeId = "32";
		try {
			String urlstr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey=gnU6hW6oBRyWh4Gc%2FbebNXyArGz5gwRBjXu8wq7O%2BWPpKZslklvmAXNfJhsVmtq%2B40XXQIgeXzpX9NGWErXj3Q%3D%3D&contentId="+contentId+"&contentTypeId="+contentTypeId+"&defaultYN=Y&firstImageYN=Y&addrinfoYN=Y&overviewYN=Y&MobileOS=ETC&MobileApp=AppTest";
			URL url = new URL(urlstr);
			System.out.println(urlstr);
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
			String line;
			while((line = br.readLine()) != null) {
				result = result + line + "\n";
			}
			System.out.println(result);
			return mapper.writeValueAsString(result);
		}
			catch(Exception e) {
				
				System.out.println(e.getMessage());
			}
		System.out.println(result);
		return mapper.writeValueAsString(result);
		
		
	}
	// 숙박 상세 내용 intro
	@RequestMapping(value="/travel/introInfo/{contentId}", produces = "application/json; charset=UTF-8")
	public String introInfo(@PathVariable String contentId) throws JsonProcessingException {
		String result ="";
		BufferedReader br = null;
		try {
			String urlstr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?ServiceKey=gnU6hW6oBRyWh4Gc%2FbebNXyArGz5gwRBjXu8wq7O%2BWPpKZslklvmAXNfJhsVmtq%2B40XXQIgeXzpX9NGWErXj3Q%3D%3D&contentId="+contentId+"&contentTypeId=32&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y";
			URL url = new URL(urlstr);
			System.out.println(urlstr);
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
			String line;
			while((line = br.readLine()) != null) {
				result = result + line + "\n";
			}
			System.out.println(result);
			return mapper.writeValueAsString(result);
		}
			catch(Exception e) {
				
				System.out.println(e.getMessage());
			}
		System.out.println(result);
		return mapper.writeValueAsString(result);
		
		
	}

	
}
