package db;

import java.util.List;

import model.UsedPart;

public interface UsedPartDBIF {
	List<UsedPart> findAll() throws DataAccessException;
	UsedPart findById(int id) throws DataAccessException;
	//do we need update? maybe delete/create is enough since it's one field.
	//UsedPart updateUsedPart(UsedPart usedPart) throws DataAccessException;
	UsedPart insertUsedPart(UsedPart usedPart, int BikeId) throws DataAccessException;
	boolean deleteUsedPart(UsedPart usedPart) throws DataAccessException;
	List<UsedPart> findAllByBikeId(int bikeId) throws DataAccessException;

}
