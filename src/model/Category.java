package model;

public class Category {

	private int categoryId;
	private String categoryName;
	private String colour;
	
	public Category() {
		
	}
	
	public Category(String categoryName, String colour) {
		this.categoryName = categoryName;
		this.colour = colour;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public String getColour() {
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
	
    public boolean equals(Category category) {

         if (this.getCategoryId() == category.getCategoryId()) {
        	 return true;
        }

        return false;

    }
    
    @Override
    public String toString() {
    	return getCategoryName();
    }
}
