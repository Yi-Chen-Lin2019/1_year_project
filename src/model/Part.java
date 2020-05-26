package model;

public class Part {

	private int id;
	private String name;
	private double usedPrice;
	private double newPrice;
	private boolean isAvailable;
	private Category category;
	
	
	public Part() {
		
	}
	
	public Part(String name, double usedPrice, double newPrice, Category category) {
		this.name = name;
		this.usedPrice = usedPrice;
		this.newPrice = newPrice;
		this.category = category;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public double getUsedPrice() {
		return usedPrice;
	}
	
	public double getNewPrice() {
		return newPrice;
	}
	
	public boolean getIsAvailable() {
		return isAvailable;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUsedPrice(double usedPrice) {
		this.usedPrice = usedPrice;
	}
	
	public void setNewPrice(double newPrice) {
		this.newPrice = newPrice;
	}
	
	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
