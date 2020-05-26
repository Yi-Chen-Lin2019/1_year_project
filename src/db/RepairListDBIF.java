package db;

import java.util.List;

import model.Bike;
import model.RepairList;


public interface RepairListDBIF {
	List<RepairList> findAll() throws DataAccessException;
	RepairList findById(int id) throws DataAccessException;
	RepairList updateRepairList(RepairList repairList) throws DataAccessException;
	RepairList insertRepairList() throws DataAccessException;
	boolean deleteRepairList(RepairList repairList) throws DataAccessException;
}
