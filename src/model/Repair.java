package model;


public class Repair {

	private int id;
	private StatusEnum status;
	private RepairItem repairItem;
	
	
	public Repair() {
		
	}
	
	public Repair(RepairItem repairItem) {
		this.repairItem = repairItem;
		this.status = StatusEnum.NOT_CHECKED;
	}
	
	public int getId() {
		return id;
	}
	
	public String getStatus() {
		String statusString = "";
		switch (status) {
		case NEED_REPAIR:
			statusString = "Needs Repairs";
			break;
		case FINE:
			statusString = "Fine";
			break;
		case REPAIRED:
			statusString = "Repaired";
			break;
		default: 
			statusString = "Not Checked";
		break;
		}
	return statusString;
	}
	
	public RepairItem getRepairItem() {
		return repairItem;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setStatus(String statusString) {
		switch (statusString) {
		case "Needs Repairs":
			status = StatusEnum.NEED_REPAIR;
			break;
		case "Fine":
			status = StatusEnum.FINE;
			break;
		case "Repaired":
			status = StatusEnum.REPAIRED;
			break;
			default: 
			status = StatusEnum.NOT_CHECKED;
		break;
		}
	}
	
	public void setRepairItem(RepairItem repairItem) {
		this.repairItem = repairItem;
	}
}



