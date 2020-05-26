package db;

import java.util.List;

import model.RepairItem;

public interface RepairItemDBIF {

	List<RepairItem> findAll() throws DataAccessException;

	RepairItem findById(int id) throws DataAccessException;

	RepairItem updateRepairItem(RepairItem repairItem) throws DataAccessException;

	boolean deleteRepairItem(RepairItem repairItem) throws DataAccessException;

	RepairItem insertRepairItem(RepairItem repairItem) throws DataAccessException;

}
