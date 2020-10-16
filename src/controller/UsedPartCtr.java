package controller;

import java.util.ArrayList;
import java.util.List;

import db.DataAccessException;
import db.UsedPartDB;
import model.UsedPart;

public class UsedPartCtr {
	
	private UsedPartDB usedPartDB = new UsedPartDB();
	
	public boolean updateUsedParts(ArrayList<UsedPart>addedParts, ArrayList<UsedPart>removedParts, int bikeId) {
		for(UsedPart i : addedParts) {
			try {
				insertUsedPart(i, bikeId);
			} catch (DataAccessException e) {
				return false;
			}
		}
		
		for(UsedPart i : removedParts) {
			try {
				deleteUsedPart(i);
			} catch (DataAccessException e) {
				return false;
			}
		}
		return true;
	}
	
	public UsedPart insertUsedPart(UsedPart usedPart, int bikeId) throws DataAccessException {
		return usedPartDB.insertUsedPart(usedPart, bikeId);
	}
	
	public UsedPart findUsedPartById(int id) throws DataAccessException {
		return usedPartDB.findById(id);
	}
	
	public List<UsedPart> findAllUsedParts() throws DataAccessException{
		return usedPartDB.findAll();
	}
	
	public List<UsedPart> findAllByBikeId(int id) throws DataAccessException{
		return usedPartDB.findAllByBikeId(id);
	}
		
	public boolean deleteUsedPart(UsedPart usedPart) throws DataAccessException{
		return usedPartDB.deleteUsedPart(usedPart);
	}
}
