package controller;

import java.util.ArrayList;
import java.util.List;

import db.BikeDB;
import db.DataAccessException;
import model.Bike;
import model.UsedPart;

public class BikeCtr {
	 private BikeDB bikeDb;
	 private RepairCtr repairCtr = new RepairCtr();
	 private UsedPartCtr usedPartCtr = new UsedPartCtr();
	 
	 public BikeCtr() throws DataAccessException {
		 bikeDb = new BikeDB();
	 }
	 //insert the bike to DB -> create a repairList for it -> attach it to the bike
	 //check if exist -> throw duplicatedexception
	 public Bike registerBike(Bike bike) throws DataAccessException {
		 //check gear type then create repairlist
		 bike.createRepairList(repairCtr.insertRepairList(bike.getIsExternalGear()));
		 bike = bikeDb.insertBike(bike);
		 return bike;
	 }	
	 
	 public Bike findBikeByID(int id) throws DataAccessException {
		 return bikeDb.findById(id);
	 }
	 
	 public List<Bike> findBikeBySerialNumber(String serialNumber) throws DataAccessException {
		 return bikeDb.findBySerialNumber(serialNumber);
	 }
	 
	 public List<Bike> getFinishedBikes() throws DataAccessException{
		 //List<Bike> bikes = bikeDb.getFinishedBikes();
		 List<Bike> bikes = bikeDb.findAll();
		 List<Bike> finished = new ArrayList<Bike>();
		 for (Bike b: bikes) {
			 if(b.getRepairList().getIsFinished()) {
				 finished.add(b);
			 }
		 }
		 return finished;
		 
	 }
	 
	 public List<Bike> getUnfinishedBikes() throws DataAccessException{
		 List<Bike> bikes = bikeDb.findAll();
		 List<Bike> unfinished = new ArrayList<Bike>();
		 for (Bike b: bikes) {
			 if(!b.getRepairList().getIsFinished()) {
				 unfinished.add(b);
			 }
		 }
		 return unfinished;
	 } 
	 
	 public Bike saveBike(Bike bike, ArrayList<UsedPart> addedUsedParts, ArrayList<UsedPart> removedUsedParts) throws DataAccessException {
		 if(!usedPartCtr.updateUsedParts(addedUsedParts, removedUsedParts, bike.getId())) {return null;}
		 repairCtr.updateRepairList(bike.getRepairList());
		 Bike updatedBike = bikeDb.updateBike(bike);
		 return updatedBike;
	 }
	 
	 public Bike updateBike(Bike bike) throws DataAccessException {
		 repairCtr.updateRepairList(bike.getRepairList());
		 Bike updatedBike = bikeDb.updateBike(bike);
		 return updatedBike;
	 }
	 
	 public boolean deleteBike(Bike bike) throws DataAccessException {
		 boolean success = false;
		 try {
			success =  bikeDb.deleteBike(bike);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return success;
	 }
	 
	 public Bike checkIfBikeWasEntered(String serialNo) throws DuplicationException, DataAccessException{
		 Bike bike;
		 bike = bikeDb.findBySerialNumber(serialNo).get(0);
		 if (bike != null) {
			throw new DuplicationException("Bike already entered", new Exception());				 
		}			
		 return bike;
	 }
	 
	 public List<Bike> findAllBikes() throws DataAccessException{
			return bikeDb.findAll();
	 }
	 
	 public double getTotalPartPrice(Bike bike) {
		 double totalPrice = 0;
		 for(UsedPart i : bike.getUsedParts()) {
			 if(i.getIsNew()) {totalPrice =+ i.getPart().getNewPrice();}
			 if(!i.getIsNew()) {totalPrice =+ i.getPart().getUsedPrice();}
		 }
		 return totalPrice;
	 }
}
	 

