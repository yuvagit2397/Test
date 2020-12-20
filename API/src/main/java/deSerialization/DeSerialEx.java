package deSerialization;

import java.util.List;


import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

public class DeSerialEx {
	public static void main(String[] args) {
		RestAssured.baseURI = "https://reqres.in";
		Sample as = RestAssured.given().header("Content-Type", "application/json").queryParam("page", "2").expect()
				.defaultParser(Parser.JSON).when().get("/api/users").as(Sample.class);

		System.out.println(as.getPage());
		System.out.println(as.getPer_page());

		System.out.println(as.getPer_total());

		System.out.println(as.getTotal());

		System.out.println(as.getSupport().getText());
		System.out.println(as.getSupport().getUrl());
		
		
		List<Data> data = as.getData();
		for (Data data2 : data) {
			System.out.println(data2.getId());
			System.out.println(data2.getEmail());
			System.out.println(data2.getFirst_name());
			System.out.println(data2.getLast_name());
			System.out.println(data2.getAvatar());
		}

	}
}
