package Test;


import org.junit.*;
import org.junit.jupiter.api.Assertions;

import controller.BikeCtr;
import controller.DuplicationException;
import db.DataAccessException;
import model.Bike;

public class TestDuplicatedSerialNumber {
	
	private BikeCtr bikeCtr;
	
	
	@Test
	public void testDupMessageThrownWhileInsert() {
		
		//Arrange
		try {
			bikeCtr = new BikeCtr();
			//Act
			//Assert
			Assertions.assertThrows(DuplicationException.class,
					() -> bikeCtr.registerBike(new Bike("AK568954U", "F", "LIN000", true)));
			Assertions.assertThrows(DuplicationException.class,
			() -> bikeCtr.checkIfBikeWasEntered(new Bike("AK568954U", "F", "LIN000", true).getSerialNumber()));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	

}
