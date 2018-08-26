package com.icia.openApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenApi {
	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			String urlstr ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList?ServiceKey=인증키&contentTypeId=15&mapX=126.9783896&mapY=37.5663939&radius=2000&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=12&pageNo=1";
			URL url = new URL(urlstr);
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
			String result ="";
			String line;
			while((line = br.readLine()) != null) {
				result = result + line + "\n";
			}
			System.out.println(result);
		}
			catch(Exception e) {
				
				System.out.println(e.getMessage());
			}
			
}
		
	}

