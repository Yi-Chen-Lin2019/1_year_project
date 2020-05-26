package db;

import java.util.List;


import model.*;


public interface BikeDBIF {

	
	List<Bike> findAll() throws DataAccessException;
	Bike findById(int id) throws DataAccessException;
	List<Bike> findBySerialNumber(String number) throws DataAccessException;
	Bike updateBike(Bike bike) throws DataAccessException;
	Bike insertBike(Bike bike) throws DataAccessException;
	boolean deleteBike(Bike bike) throws DataAccessException;
	
}
