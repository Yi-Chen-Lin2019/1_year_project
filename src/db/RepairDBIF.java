package db;

import java.util.List;

import model.Repair;


public interface RepairDBIF {
	List<Repair> findAll() throws DataAccessException;
	Repair findById(int id) throws DataAccessException;
	Repair updateRepair(Repair repair) throws DataAccessException;
	Repair insertRepair(Repair repair, int repairListId) throws DataAccessException;
	boolean deleteRepair(Repair repair) throws DataAccessException;
	List<Repair> findAllByRepairListId(int id) throws DataAccessException;
}
