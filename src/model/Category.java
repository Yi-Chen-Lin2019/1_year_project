package model;

public class Category {

	private int categoryId;
	private String categoryName;
	private Color colour;
	
	public Category() {
		
	}
	
	public Category(String categoryName, Color colour) {
		this.categoryName = categoryName;
		this.colour = colour;
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
