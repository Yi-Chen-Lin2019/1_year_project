package model;

public class UsedPart {

	private int id;
	private boolean isNew;
	private Part part;
	
	
	public UsedPart() {
		
	}
	
	
	public UsedPart(boolean isNew, Part part) {
		this.isNew = isNew;
		this.part = part;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean getIsNew() {
		return isNew;
	}
	
	public Part getPart() {
		return part;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	public void setPart(Part part) {
		this.part = part;
	}
}



