package model;

public class Category {

	private int categoryId;
	private String categoryName;
	private Color colour;
	
	public Category() {
		
	}
	
	public Category(String categoryName, String colour) {
		this.categoryName = categoryName;
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
	
	public Color getColour() {
		return colour;
	}
	
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public void setColour(Color colour) {
		this.colour = colour;
	}
}
