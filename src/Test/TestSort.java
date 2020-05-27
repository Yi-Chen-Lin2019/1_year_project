package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import controller.StatisticsCtr;
import db.DataAccessException;
import gui.Statistics;
import model.Bike;
import model.Part;

class TestSort {
	


	//@Test
	void testFinishedBikesWithTimeRange() {
		
		try {
			StatisticsCtr s = new StatisticsCtr();
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
		
		try {
			StatisticsCtr s = new StatisticsCtr();
			LocalDateTime dt1 
            = LocalDateTime.parse("2020-05-01T12:45:30"); 
			List<Bike> bikes = s.sortAllBikesDate(dt1, java.time.LocalDateTime.now());
			bikes.forEach((bike)->System.out.println(bike.getId()+" "+bike.getBikeName()+" "+bike.getRegisterDate()));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void testAllPart() {
		try {
			StatisticsCtr s = new StatisticsCtr();
			List<Part> parts = s.findAllParts();
			parts.forEach((part)->System.out.println(part.getId()+" "+part.getName()+" new price: "+part.getNewPrice()+" used price: "+ part.getUsedPrice()));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
