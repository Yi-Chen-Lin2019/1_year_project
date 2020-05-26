package controller;

import java.util.List;

import db.DBConnection;
import db.DataAccessException;
import db.RepairDB;
import db.RepairListDB;
import model.Bike;
import model.RepairList;
import model.Repair;
import model.RepairItem;

public class RepairCtr {
		private RepairItemCtr repairItemCtr = new RepairItemCtr();
		private RepairListDB repairListDB = new RepairListDB();
		private RepairDB repairDB = new RepairDB();
		
		//repairList stuff
		
			//create repair list and populate it with repairs according to the gear type
			public RepairList insertRepairList(boolean isExternalGear) throws DataAccessException {
				RepairList repairList = repairListDB.insertRepairList();
				List<RepairItem> repairItems = repairItemCtr.getRepairItemsByGearType(isExternalGear);
				for (RepairItem repairItem : repairItems) {
					//default constructor with repairItemId and repairListId
					Repair repair = new Repair(repairItem);
					//insert the repair to the DB and add it to the repairList
					repair = repairDB.insertRepair(repair, repairList.getId());
					repairList.addRepair(repair);
				}
				return repairList;
			}
			
			public RepairList findRepairListById(int id) throws DataAccessException {
				return repairListDB.findById(id);
			}

			public RepairList updateRepairList(RepairList repairList) throws DataAccessException {
				DBConnection.getInstance().startTransaction();
				for (Repair repair : repairList.getAllRepairs()) {
					updateRepair(repair);
				}
				repairList = repairListDB.updateRepairList(repairList);
				DBConnection.getInstance().commitTransaction();
				return repairList;
			}
			
			public boolean deleteRepairList(RepairList repairList) throws DataAccessException {
				return repairListDB.deleteRepairList(repairList);
			}
			
			
		//repair stuff
			public Repair findRepairById(int id) throws DataAccessException {
				return repairDB.findById(id);
			}
			
			public List<Repair> getRepairs() throws DataAccessException{
				return repairDB.findAll();
			}
			
			private Repair updateRepair(Repair repair) throws DataAccessException {
				return repairDB.updateRepair(repair);
			}
			
			
			
			
			
			
			
			
}
