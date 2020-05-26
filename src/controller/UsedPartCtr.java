package controller;

import java.util.List;

import db.DataAccessException;
import db.UsedPartDB;
import model.UsedPart;

public class UsedPartCtr {
	
	private UsedPartDB usedPartDB = new UsedPartDB();
	
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
		
	public boolean deleteUsedPart(UsedPart usedPart) {
		//TODO:
		return false;
	}
}
