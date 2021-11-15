package com.example.faces.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.faces.objects.FacesResponse;
import com.example.faces.objects.ImageDescription;
import com.example.faces.objects.Face;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class FacesController {



	int numFaces=0;
	int numImage=0;

	@GetMapping("/faces")
	public FacesResponse faces(@RequestParam List<String> url) throws IOException, InterruptedException {
		 numFaces=0;
		 numImage=0;		
		
		List<Face> facesList = new ArrayList();
		List<ImageDescription> descriptionList = new ArrayList();
		FacesResponse facesResponse = new FacesResponse();
		
		
		url.forEach(e-> {
		numImage++;	
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://microsoft-computer-vision3.p.rapidapi.com/analyze?language=en&descriptionExclude=Celebrities&visualFeatures=ImageType,Faces,Description,Objects&details=Celebrities"))
				.header("content-type", "application/json")
				.header("x-rapidapi-host", "microsoft-computer-vision3.p.rapidapi.com")
				.header("x-rapidapi-key", "97055b95dcmsh712db9fd1caa827p15468fjsnf751a20cbfff")
				.method("POST", HttpRequest.BodyPublishers.ofString("{\r\"url\": \""+e+"\"\r}")).build();
		HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e1) {
			
			e1.printStackTrace();
		} catch (InterruptedException e1) {

			e1.printStackTrace();
		}
		
		
		    ObjectMapper mapper = new ObjectMapper();
		    JsonNode actualObj = null;
			try {
				actualObj = mapper.readTree(response.body());
			} catch (JsonMappingException e1) {

				e1.printStackTrace();
			} catch (JsonProcessingException e1) {

				e1.printStackTrace();
			}
			System.out.println(response.body());
			Face face = new Face();
		    JsonNode jsonNode1 = actualObj.get("faces");
		   
		    if (jsonNode1!=null) {
		    jsonNode1.forEach(el-> {
		    	numFaces++;
		    	face.setAge(el.get("age").toString());
		    	face.setGender(el.get("gender").toString());
		    	face.setBox(el.get("faceRectangle").toString());
		    });
		    
		    ImageDescription imageDescription = new ImageDescription();
		    if (actualObj.get("faces").size()>0) {
		    	jsonNode1 = actualObj.get("description");
		    	imageDescription.setImageNumber(numImage);
		    	imageDescription.setDescription(jsonNode1.toString());
		    }
		    
		    facesList.add(face);
		    descriptionList.add(imageDescription);
		    }		    
		    

		
		
		});
		
		facesResponse.setFaces(facesList);
		facesResponse.setTotalFaces(numFaces);
		facesResponse.setImageDescription(descriptionList);
		return facesResponse;
	}
	
}
