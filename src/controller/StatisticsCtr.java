package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import db.*;
import model.*;

public class StatisticsCtr {
	private BikeDBIF bikeDb;
	private PartDBIF partDb;
	
	public StatisticsCtr() throws DataAccessException {
		bikeDb = new BikeInfoDB();
		partDb = new PartInfoDB();
	}
	
	public List<Bike> findAllBikes() throws DataAccessException{
		return bikeDb.findAll();
	}
	
	public List<Part> findAllParts() throws DataAccessException{
		return partDb.findAll();
	}
	
	public Bike findBikeById(int id) throws DataAccessException{
		return bikeDb.findById(id);
	}
	
		
	public List<Bike> sortAllBikesDate(LocalDateTime from, LocalDateTime to) throws DataAccessException{
		List<Bike> bikes = findAllBikes();
		List<Bike> result = new ArrayList<>();
		for(Bike bs: bikes) {
			if(bs.getRegisterDate().compareTo(from) >= 0 && bs.getRegisterDate().compareTo(to) <= 0 ) {
					result.add(bs);}
		}					
		return result;
	}
	
}
