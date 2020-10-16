package model;

import java.awt.Color;

public class Category {

	private int categoryId;
	private String categoryName;
	private Color colour;
	private String colourString;
	
	public Category() {
		
	}
	
	public Category(String categoryName, String colour) {
		this.categoryName = categoryName;
		this.colourString = colour;
		switch(colour) {
	        case "black":
	            this.colour = Color.BLACK;
	        case "green":
	        	this.colour = Color.GREEN;
	        case "red":
	        	this.colour = Color.RED;
	        case "pink":
	        	this.colour = Color.PINK;
	        case "blue":
	        	this.colour = Color.BLUE;
	        case "yellow":
	        	this.colour = Color.YELLOW;   
    }

	}
	
	public int getCategoryId() {
		return categoryId;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public String getColour() {
		return colourString;
	}
	
	public Color getColourClass() {
		return colour;
	}
	
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public void setColour(String colour) {
		switch(colour) {
	        case "black":
	            this.colour = Color.BLACK;
	        case "green":
	        	this.colour = Color.GREEN;
	        case "red":
	        	this.colour = Color.RED;
	        case "pink":
	        	this.colour = Color.PINK;
	        case "blue":
	        	this.colour = Color.BLUE;
	        case "yellow":
	        	this.colour = Color.YELLOW;   
		}
		this.colourString = colour;
	}
	
    
    @Override
    public String toString() {
    	return getCategoryName();
    }
}
