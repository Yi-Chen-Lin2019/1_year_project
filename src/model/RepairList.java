package model;
import java.util.*;

public class RepairList {

	private int id;
	private String note;
	private ArrayList<Repair> repairs;
	private boolean isFinished;
	
	
	
	public RepairList() {
		repairs = new ArrayList<Repair>();
	}
	
	
	public RepairList(ArrayList<Repair> repairs) {
		this.repairs = repairs;
	}
	
	public int getId() {
		return id;
	}
	
	public String getNote() {
		return note;
	}
	
	public boolean getIsFinished() {
		return isFinished;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public void setIsFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	
	public void addRepair(Repair repair) {
		repairs.add(repair);
	}
	
	public ArrayList<Repair> getAllRepairs() {
		return repairs;
	}
}
