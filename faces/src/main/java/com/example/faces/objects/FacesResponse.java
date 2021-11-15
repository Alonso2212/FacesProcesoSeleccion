package com.example.faces.objects;

import java.util.List;

public class FacesResponse {
	
	private List<Face> faces;
	private int totalFaces;
	private List<ImageDescription> imageDescription;
	
	public List<Face> getFaces() {
		return faces;
	}
	public void setFaces(List<Face> faces) {
		this.faces = faces;
	}
	public int getTotalFaces() {
		return totalFaces;
	}
	public void setTotalFaces(int totalFaces) {
		this.totalFaces = totalFaces;
	}
	public List<ImageDescription> getImageDescription() {
		return imageDescription;
	}
	public void setImageDescription(List<ImageDescription> imageDescription) {
		this.imageDescription = imageDescription;
	} 

}
