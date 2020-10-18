package model;

public class RepairItem {

	private int repairItemId;
	private String name;
	private Category category;
	private String danish;
	
	
	
	public RepairItem() {
		
	}
	
	
	public RepairItem(Category category, String name, String danish) {
		this.category = category;
		this.name = name;
		this.danish = danish;
	}
	
	public int getRepairItemId() {
		return repairItemId;
	}
	
	public String getName() {
		return name;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setRepairItemId(int repairItemId) {
		this.repairItemId = repairItemId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public void setDanish(String danish) {this.danish = danish;}
	public String getDanish() {return danish;}
}
