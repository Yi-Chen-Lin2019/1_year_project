package controller;

import java.util.List;

import db.DataAccessException;
import db.RepairItemDB;
import model.RepairItem;

public class RepairItemCtr {
	
	private RepairItemDB repItemDB = new RepairItemDB();
	
	public RepairItem newRepairItem(RepairItem repItem) throws DataAccessException {
		return repItemDB.insertRepairItem(repItem);
	}
	
	public RepairItem updateRepairItem(RepairItem repItem) throws DataAccessException {
		return repItemDB.updateRepairItem(repItem);
	}
	
	public boolean deleteRepairItem(RepairItem repItem) throws DataAccessException {
		return repItemDB.deleteRepairItem(repItem);
	}
	
	public RepairItem getRepairItemById(int id) throws DataAccessException {
		return repItemDB.findById(id);
	}
	
	public List<RepairItem> getRepairItems() throws DataAccessException{
		return repItemDB.findAll();
	}

	public List<RepairItem> getRepairItemsByGearType(boolean isExternal) throws DataAccessException {
		return repItemDB.findAllByGearType(isExternal);
	}
}
