package Test;



import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import controller.CategoryCtr;
import controller.RepairItemCtr;
import db.DataAccessException;
import model.Category;
import model.RepairItem;

class TestInsertRepairItem {
	
	
	private RepairItemCtr repairItemCtr;

	
	
	public TestInsertRepairItem() {
		repairItemCtr = new RepairItemCtr();
	}
	
	
	@Test
	void test() throws DataAccessException {
		//Arrange
		Category repairItemCategory = new Category("testCategory", "testColor");
		boolean insertSuccess = false;
		repairItemCategory = new CategoryCtr().newCategory(repairItemCategory);
		RepairItem repairItem = new RepairItem(repairItemCategory, "testItem");
		repairItem = repairItemCtr.newRepairItem(repairItem);
		if(repairItem.getRepairItemId() > 0) {
			insertSuccess = true;
		}
		//Assert
		Assertions.assertTrue(insertSuccess);
		
		
	}

}
