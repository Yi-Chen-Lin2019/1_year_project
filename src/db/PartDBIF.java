package db;

import java.util.List;

import model.Part;

public interface PartDBIF {
	List<Part> findAll() throws DataAccessException;
	Part findById(int id) throws DataAccessException;
	List<Part> findAllByName(String name) throws DataAccessException; 
	Part updatePart(Part part) throws DataAccessException;
	Part insertPart(Part part) throws DataAccessException;
	boolean deletePart(Part part) throws DataAccessException;
}
