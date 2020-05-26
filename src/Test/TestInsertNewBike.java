package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import controller.BikeCtr;
import db.BikeDB;
import db.DataAccessException;
import model.Bike;

class TestInsertNewBike {
	
	
	private BikeCtr bikeCtr;

	
	
	public TestInsertNewBike() {
		try {
			bikeCtr = new BikeCtr();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	void test() throws DataAccessException {
		//Arrange
		//(String serialNumber, String gender, String bikeName, Date registerDate, boolean isExternalGear)
		Bike b = new Bike("AK568954U", "F", "LIN000", true);
		boolean insertSuccess;
				
		//Act
		try {
			bikeCtr.registerBike(b);
			insertSuccess = true;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			insertSuccess = false;
			e.printStackTrace();
		}
		//Assert
		Assertions.assertTrue(insertSuccess);
		//Assertions.assertEquals(b.getBikeName(), bikeCtr.findBikeByID(b.getId()));
		
		
	}

}
