package gui;



import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import controller.BikeCtr;
import db.DataAccessException;
import model.Bike;

public class Statistics {
	
	
	public Statistics() {
		
	}
	
	
//	public static void main(String[] args) throws DataAccessException {
//			BikeCtr bikeCtr = new BikeCtr();
//			List<Bike> bikes = bikeCtr.getFinishedBikes();
//			List<Bike> bikeSold = new ArrayList<>();
//			for(Bike b: bikeCtr.getFinishedBikes()) {
//				if(b.getSoldDate() != null) {
//					bikeSold.add(b);
//				}
//			}
//			//sort by bike final price
//			Comparator<Bike> bikeFinalPriceComparator = (b1, b2)->(
//					b1.getFinalPrice() -b2.getFinalPrice() > 0 ? 1 : 
//						(b1.getFinalPrice()-b2.getFinalPrice() == 0 ? 0 : -1) );
//			bikeSold.sort(bikeFinalPriceComparator.reversed()); //high to low
//			bikeSold.sort(bikeFinalPriceComparator); //low to high
//			
//			
//			//sort by bike sale price
//			Comparator<Bike> bikeSalePriceComparator = (b1, b2)->(
//					b1.getSalePrice() -b2.getSalePrice() > 0 ? 1 : 
//						(b1.getSalePrice()-b2.getSalePrice() == 0 ? 0 : -1) );
//			bikes.sort(bikeSalePriceComparator.reversed()); //high to low
//			bikes.sort(bikeSalePriceComparator); //low to high
//
//			//sort by bike name
//			
//			//sort by register date
//			bikeSold.sort((Bike b1, Bike b2)->b1.getRegisterDate().compareTo(b2.getRegisterDate()));
//			
//			//sort by soldDate
//			bikeSold.sort((Bike b1, Bike b2)->b1.getSoldDate().compareTo(b2.getSoldDate()));
//
//			//avg price of female/ male/ uni
//			int male = 0;
//			int female = 0;
//			int uni = 0;
//			for(Bike b: bikes) {
//				if(b.getGender()=="M") {
//					male++;
//				} else {
//					if(b.getGender()=="F") {
//						female++;
//					} else {
//						uni++;
//					}
//				}
//			}
				
			
			
		
//	} 
	
	

	
	
}
