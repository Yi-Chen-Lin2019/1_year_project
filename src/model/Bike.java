package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Bike {

	private int id;
	private String serialNumber;
	private String bikeName;
	private LocalDateTime registerDate;
	private LocalDateTime soldDate;
	private String gender;
	private boolean isExternalGear;
	private double totalPartPrice;
	private double finalPrice;
	private double salePrice;
	private RepairList repairList;
	private ArrayList<UsedPart> usedParts = new ArrayList<UsedPart>();
	
	
	public Bike() {
		
	}
	
	
	public Bike(String serialNumber, String gender, String bikeName, boolean isExternalGear) {
		this.serialNumber = serialNumber;
		this.gender = gender;
		this.bikeName = bikeName;
		this.registerDate = LocalDateTime.now();
		this.isExternalGear = isExternalGear;
	}
	
	public int getId() {
		return id;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	
	public String getBikeName() {
		return bikeName;
	}
	
	public LocalDateTime getRegisterDate() {
		return registerDate;
	}
	
	public LocalDateTime getSoldDate() {
		return soldDate;
	}
	
	public String getGender() {
		return gender;
	}
	
	public boolean getIsExternalGear() {
		return isExternalGear;
	}
	
	public double getTotalPartPrice() {
		return totalPartPrice;
	}
	
	public double getFinalPrice() {
		return finalPrice;
	}
	
	public double getSalePrice() {
		return salePrice;
	}
	
	public RepairList getRepairList() {
		return repairList;
	}
	
	public ArrayList<UsedPart> getUsedParts() {
		return usedParts;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public void setBikeName(String bikeName) {
		this.bikeName = bikeName;
	}
	
	public void setRegisterDate(LocalDateTime registerDate) {
		this.registerDate = registerDate;
	}
	
	public void setSoldDate(LocalDateTime soldDate) {
		this.soldDate = soldDate;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public void setIsExternalGear(boolean isExternalGear) {
		this.isExternalGear = isExternalGear;
	}
	
	public void setTotalPartPrice(double totalPartPrice) {
		this.totalPartPrice = totalPartPrice;
	}
	
	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}
	
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	
	public void createRepairList(RepairList repairList) {
		//RepairList repairList = new RepairList(); I think create name might be confusing here xd
		this.repairList = repairList;
	}
	
	public void addUsedPart(UsedPart usedPart) {
		usedParts.add(usedPart);
	}
}




