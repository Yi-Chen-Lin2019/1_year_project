package gui;



import java.time.LocalDateTime;
import java.util.*;

import controller.BikeCtr;
import db.DataAccessException;
import model.*;

public class Statistics {
	
	
	public Statistics() {
		
	}
	
	
	public static void main(String[] args) throws DataAccessException {
			BikeCtr bikeCtr = new BikeCtr();
			List<Bike> bikes = bikeCtr.getFinishedBikes();
			List<Bike> bikeSold = new ArrayList<>();
			for(Bike b: bikeCtr.getFinishedBikes()) {
				if(b.getSoldDate() != null) {
					bikeSold.add(b);
				}
			}
			//sort by bike final price
			Comparator<Bike> bikeFinalPriceComparator = (b1, b2)->(
					b1.getFinalPrice() -b2.getFinalPrice() > 0 ? 1 : 
						(b1.getFinalPrice()-b2.getFinalPrice() == 0 ? 0 : -1) );
			bikeSold.sort(bikeFinalPriceComparator.reversed()); //high to low
			bikeSold.sort(bikeFinalPriceComparator); //low to high
			
			
			//sort by bike sale price
			Comparator<Bike> bikeSalePriceComparator = (b1, b2)->(
					b1.getFinalPrice() -b2.getFinalPrice() > 0 ? 1 : 
						(b1.getFinalPrice()-b2.getFinalPrice() == 0 ? 0 : -1) );
			bikes.sort(bikeSalePriceComparator.reversed()); //high to low
			bikes.sort(bikeSalePriceComparator); //low to high

			//sort by bike name
			
			//sort by date
			bikeSold.sort((Bike b1, Bike b2)->b1.getRegisterDate().compareTo(b2.getRegisterDate()));
			bikeSold.forEach((bike)->System.out.println(bike.getBikeName()+" "+bike.getSoldDate()));
			
			
		
	} 
	//sort by specific date range
	public List<Bike> sortSoldBikesDate(LocalDateTime from, LocalDateTime to) throws DataAccessException{
		BikeCtr bikeCtr = new BikeCtr();
		List<Bike> result = new ArrayList<>();
		List<Bike> bikes = bikeCtr.findAllBikes();
		List<Bike> bikeSold = new ArrayList<>();
		
		for(Bike b: bikes) {
			if(b.getSoldDate() != null) {
				bikeSold.add(b);
			}
		}
		for(Bike bs: bikeSold) {
			if(bs.getSoldDate().compareTo(from) >= 0 && bs.getSoldDate().compareTo(to) <= 0 ) {
				result.add(bs);}
		}		
		
		return result;
	
}
	
	public List<Bike> sortAllBikesDate(LocalDateTime from, LocalDateTime to) throws DataAccessException{
		BikeCtr bikeCtr = new BikeCtr();
		List<Bike> bikes = bikeCtr.findAllBikes();
		List<Bike> result = new ArrayList<>();
		for(Bike bs: bikes) {
			if(bs.getRegisterDate().compareTo(from) >= 0 && bs.getRegisterDate().compareTo(to) <= 0 ) {
				result.add(bs);}
		}		
		
		return result;
	
}
}
