package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import controller.BikeCtr;
import db.BikeDB;
import db.DataAccessException;
import model.Bike;

class TestFindBike {
	
	BikeDB bikeDB;
	BikeCtr bikeCtr;
	
	@Test
	void testFindByBikeId() {
		try {
			//Arrange
			bikeCtr = new BikeCtr();
			//Act
			//Assert
			assertEquals("Trek", bikeCtr.findBikeByID(1).getBikeName());
			bikeCtr.findBikeByID(1);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void testFindByBikeSerailNumber() {
		try {
			//Arrange
			bikeDB = new BikeDB();
			bikeCtr = new BikeCtr();
			//Act
			//List<Bike> list = bikeCtr.findBikeBySerialNumber("AK568954U");
			List<Bike> list = bikeDB.findBySerialNumber("AK568954X");
			for(Bike b : list) {
				assertEquals("AK568954U", b.getSerialNumber());
			}			
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
