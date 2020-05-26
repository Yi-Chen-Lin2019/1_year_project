package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.BikeCtr;
import db.DataAccessException;
import model.Bike;

class TestDeleteBike {
	
	BikeCtr bikeCtr;

	@Test
	void testDeleteBike() {
		try {
			//Arrange
			bikeCtr = new BikeCtr();
			
			//Act
			Bike bike = bikeCtr.findBikeByID(9);
			bikeCtr.deleteBike(bike);
			
			//Assert
			//assertTrue(success);
			assertNull(bikeCtr.findBikeByID(9));
			
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
