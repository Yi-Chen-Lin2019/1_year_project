package model;

public class Category {

	private int categoryId;
	private String categoryName;
	private String colour;
	
	public Category() {
		
	}
	
	public Category(String categoryName, String colour) {
		this.categoryName = categoryName;
		this.colour = Color.getColour(colour);
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
	
	public void setColour(String colour) {
		this.colour = colour;
	}
}
