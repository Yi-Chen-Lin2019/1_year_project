package Test;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.*;
import db.*;
import model.*;

class TestUpdateRepairProcess {
	
	private RepairCtr repairCtr;
	
	public TestUpdateRepairProcess() {
		repairCtr = new RepairCtr();
	}
	@Test
	void testUpateRepairs() {
		
		try {
			//Arrange
			BikeCtr bikeCtr = new BikeCtr();
			Bike bike = bikeCtr.findBikeByID(9);
			
			//Act
			RepairList rList = bike.getRepairList();
			int id = -1;
			for (Repair repair : rList.getAllRepairs()) {
				repair.setStatus("Repaired");
				id = repair.getId();
			}
			repairCtr.updateRepairList(rList);
			
			//Assert
			assertEquals("Repaired", repairCtr.findRepairById(id).getStatus());
			
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
		}


		
		
	}

}
