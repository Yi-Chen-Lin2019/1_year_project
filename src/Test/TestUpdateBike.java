package Test;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.BikeCtr;
import controller.UsedPartCtr;
import db.BikeDB;
import db.DataAccessException;
import model.Bike;
import model.Part;
import model.UsedPart;

class TestUpdateBike {

	BikeCtr bikeCtr;

	@Test
	void testUpdateBikeSalePrice() {
		try {
			//Arrange
			bikeCtr = new BikeCtr();
			
			//Act
			Bike bike = bikeCtr.findBikeByID(1);
			bike.setSalePrice(700);
			bikeCtr.updateBike(bike);
			
			//Assert
			assertEquals(bike.getSalePrice(), bikeCtr.findBikeByID(1).getSalePrice());
			
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	void testUpdateUsedPart() {
		try {
			//Arrange
			bikeCtr = new BikeCtr();
			
			
			//Act
			Bike bike = bikeCtr.findBikeByID(5);
			UsedPart usedPart = new UsedPart(true, new Part());
			usedPart = new UsedPartCtr().insertUsedPart(usedPart, bike.getId());
			bike.addUsedPart(usedPart);
			bikeCtr.updateBike(bike);
			
			//Assert
			assertTrue(bikeCtr.findBikeByID(6).getUsedParts().get(0).getIsNew());
			
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
