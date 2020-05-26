package Test;



import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import controller.BikeCtr;
import controller.CategoryCtr;
import controller.PartCtr;
import controller.UsedPartCtr;
import db.BikeDB;
import db.DataAccessException;
import model.Bike;
import model.Category;
import model.Part;
import model.UsedPart;

class TestInsertUsedPart {
	
	
	private BikeCtr bikeCtr;
	private PartCtr partCtr;

	private UsedPartCtr usedPartCtr;
	private CategoryCtr categoryCtr;

	
	
	public TestInsertUsedPart() {
		try {
			bikeCtr = new BikeCtr();
			partCtr = new PartCtr();
			usedPartCtr = new UsedPartCtr();
			categoryCtr = new CategoryCtr();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	void test() throws DataAccessException {
		//Arrange
		//(String serialNumber, String gender, String bikeName, Date registerDate, boolean isExternalGear)
		Bike b = new Bike("TestInsertUsedPart", "F", "LIN000", true);
		boolean insertSuccess = false;
		Category c = new Category("TestCategory", "TestColor");
		//Act
		try {
			c = categoryCtr.newCategory(c);
			Part p = new Part("TestPart", 15.2, 4, c);
			p = partCtr.insertPart(p);
			b = bikeCtr.registerBike(b);
			UsedPart uP = new UsedPart(true, p);
			uP = usedPartCtr.insertUsedPart(uP, b.getId());
			b.addUsedPart(uP);
			
			
			
			if (b.getUsedParts().get(0).getId() > 0) {
				insertSuccess = true;
			}
			
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			insertSuccess = false;
			e.printStackTrace();
		}
		//Assert
		Assertions.assertTrue(insertSuccess);
		
		
	}

}
