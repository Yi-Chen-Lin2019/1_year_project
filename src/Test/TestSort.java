package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import db.DataAccessException;
import gui.Statistics;
import model.Bike;

class TestSort {
	


	@Test
	void testFinishedBikesWithTimeRange() {
		Statistics s = new Statistics();

		try {
			LocalDateTime dt1 
            = LocalDateTime.parse("2020-05-01T00:00:00"); 
			LocalDateTime dt2
            = LocalDateTime.parse("2020-05-31T00:00:00");
			List<Bike> bikes = s.sortSoldBikesDate(dt1, dt2);
			bikes.forEach((bike)->System.out.println(bike.getBikeName()+" Sold date: "+bike.getSoldDate()));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void testAllBikesWithTimeRange() {
		Statistics s = new Statistics();
		try {
			LocalDateTime dt1 
            = LocalDateTime.parse("2020-05-24T12:45:30"); 
			List<Bike> bikes = s.sortAllBikesDate(dt1, java.time.LocalDateTime.now());
			bikes.forEach((bike)->System.out.println(bike.getId()+" "+bike.getBikeName()+" "+bike.getRegisterDate()));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
