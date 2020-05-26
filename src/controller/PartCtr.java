package controller;

import java.util.List;

import db.DataAccessException;
import db.PartDB;
import model.Part;

public class PartCtr {
	
	private PartDB partDB = new PartDB();
	
	public Part insertPart(Part part) throws DataAccessException {
		return partDB.insertPart(part);
	}
	
	public Part updatePart(Part part) throws DataAccessException {
		return partDB.updatePart(part);
	}
	
	public Part findPartById(int id) throws DataAccessException {
		return partDB.findById(id);
	}
	
	public List<Part> findPartByName(String name) throws DataAccessException {
		return partDB.findAllByName(name);
	}
	
	public List<Part> findAllParts() throws DataAccessException{
		return partDB.findAll();
	}
		
}
