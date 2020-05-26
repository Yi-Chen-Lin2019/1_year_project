package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Bike;
import model.Repair;
import model.RepairList;

public class RepairListDB implements RepairListDBIF{
	Connection connection;
	private static String FIND_ALL_Q = "Select RepairListId, Note, isFinished from RepairList";
	private static String FIND_BY_ID_Q = FIND_ALL_Q + " WHERE RepairListId = ?";
	private static String INSERT_Q = "INSERT INTO RepairList (isFinished) values (0)";
	private static String UPDATE_Q = "Update RepairList SET Note = ?, isFinished = ? WHERE RepairListId = ?";
	private PreparedStatement findAll;
	private PreparedStatement findById;
	private PreparedStatement insertPS;
	private PreparedStatement updatePS;

	
	public RepairListDB() {
		try {
			init();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void init() throws DataAccessException {
		try {
			connection = DBConnection.getInstance().getConnection();
			findAll = connection.prepareStatement(FIND_ALL_Q);
			findById = connection.prepareStatement(FIND_BY_ID_Q);
			
			
			insertPS = connection.prepareStatement(INSERT_Q, Statement.RETURN_GENERATED_KEYS);

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}
	
	@Override
	public List<RepairList> findAll() throws DataAccessException {
		List<RepairList> res = new ArrayList<>(0);

		try {
			ResultSet rs = this.findAll.executeQuery();
			res = buildObjects(rs);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}

	@Override
	public RepairList findById(int id) throws DataAccessException {
		RepairList res = null;
		try {
			findById.setInt(1, id);
			ResultSet rs = findById.executeQuery();
			if (rs.next()) {
				res = buildObject(rs);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return res;
	}
	

	@Override
	public RepairList updateRepairList(RepairList repairList) throws DataAccessException {
		try {
			updatePS = connection.prepareStatement(UPDATE_Q);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Note = ?, isFinished = ? WHERE RepairList = ?
		try {
			updatePS.setString(1, repairList.getNote());
			updatePS.setBoolean(2, repairList.getIsFinished());
			updatePS.setInt(3, repairList.getId());
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_PS_VARS_INSERT, e);
		}
		try {
			updatePS.executeUpdate();
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_INSERT, e);
		}
		return repairList;
	}

	@Override
	public RepairList insertRepairList() throws DataAccessException {		
		//RepairList (isFinished) values (0)
				RepairList repairList = new RepairList();
				try {
					//insert the repairList
					repairList.setIsFinished(false);
					repairList.setNote(null);
					insertPS.executeUpdate();
					
					ResultSet keys = insertPS.getGeneratedKeys();
					if (keys.next()) {
						repairList.setId(keys.getInt(1));
				    }
				} catch (SQLException e) {
					// e.printStackTrace();
					e.printStackTrace();
					throw new DataAccessException(DBMessages.COULD_NOT_INSERT, e);
					
				}
				return repairList;
	}

	@Override
	public boolean deleteRepairList(RepairList repairList) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	private RepairList buildObject(ResultSet rs) throws DataAccessException {
		//RepairListId, Note, isFinished
		RepairList res = new RepairList();
		try {
			int repairListId = rs.getInt("RepairListId");
			res.setId(repairListId);
			res.setNote(rs.getString("Note"));
			res.setIsFinished(rs.getBoolean("isFinished"));
			
			for (Repair repair : new RepairDB().findAllByRepairListId(repairListId)) {
				res.addRepair(repair);
			} 

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}
	
	private List<RepairList> buildObjects(ResultSet rs) throws DataAccessException{
		List<RepairList> res = new ArrayList<>();
		try {
			while (rs.next()) {
				RepairList currRepList = buildObject(rs);
				res.add(currRepList);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}

}
