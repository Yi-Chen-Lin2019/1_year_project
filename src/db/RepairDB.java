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

import model.Repair;
import model.StatusEnum;

public class RepairDB implements RepairDBIF {

	private static String FIND_ALL_Q = "Select RepairId, RepairStatus, RepairItemId, RepairListId from Repair ";
	private static String FIND_BY_ID_Q = FIND_ALL_Q + " where RepairId = ?";
	private static String FIND_BY_REPAIR_LIST_ID_Q = FIND_ALL_Q + " where RepairListId = ?";
	private static String INSERT_Q = "INSERT INTO Repair (RepairStatus, RepairItemId, RepairListId) values (?, ?, ?)";
	private static String UPDATE_Q = "Update Repair SET RepairStatus = ? WHERE RepairId = ?";

	private PreparedStatement findAll;
	private PreparedStatement findAllByRepairListId;
	private PreparedStatement findById;
	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	
	public RepairDB() {
		try {
			init();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void init() throws DataAccessException {
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			findAll = connection.prepareStatement(FIND_ALL_Q);
			findById = connection.prepareStatement(FIND_BY_ID_Q);
			findAllByRepairListId = connection.prepareStatement(FIND_BY_REPAIR_LIST_ID_Q);
			insertPS = connection.prepareStatement(INSERT_Q, Statement.RETURN_GENERATED_KEYS);
			updatePS = connection.prepareStatement(UPDATE_Q);

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}
	
	@Override
	public List<Repair> findAll() throws DataAccessException {
		List<Repair> res = new ArrayList<>(0);

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
	public Repair findById(int id) throws DataAccessException {
		Repair res = null;
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
	
	public List<Repair> findAllByRepairListId(int id) throws DataAccessException {
		List<Repair> res = new ArrayList<>(0);

		try {
			findAllByRepairListId.setInt(1,  id);
			ResultSet rs = this.findAllByRepairListId.executeQuery();
			res = buildObjects(rs);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}
	

	@Override
	public Repair updateRepair(Repair repair) throws DataAccessException {
		//RepairStatusId = ? WHERE RepairId = ?
		try {
			
			updatePS.setString(1, repair.getStatus());
			updatePS.setInt(2, repair.getId());
			
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
		return repair;
	}

	@Override
	public Repair insertRepair(Repair repair, int repairListId) throws DataAccessException {
		//(RepairStatusId - 1, RepairItemId, RepairListId)
				try {
					insertPS.setString(1, repair.getStatus());
					insertPS.setInt(2, repair.getRepairItem().getRepairItemId());
					insertPS.setInt(3, repairListId);
				} catch (SQLException e) {
					// e.printStackTrace();
					throw new DataAccessException(DBMessages.COULD_NOT_BIND_PS_VARS_INSERT, e);
				}
				try {
					insertPS.executeUpdate();
					ResultSet keys = insertPS.getGeneratedKeys();
					if (keys.next()) {
						repair.setId(keys.getInt(1));
				    }
				} catch (SQLException e) {
					// e.printStackTrace();
					throw new DataAccessException(DBMessages.COULD_NOT_INSERT, e);
				}
				repair.setStatus("default");
				return repair;
	}

	@Override
	public boolean deleteRepair(Repair repair) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	private Repair buildObject(ResultSet rs) throws DataAccessException {
		Repair res = new Repair();
		try {
			res.setId(rs.getInt("RepairId"));
			res.setStatus(rs.getString("RepairStatus"));
			res.setRepairItem(new RepairItemDB().findById(rs.getInt("RepairItemId")));
			
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}
	
	private List<Repair> buildObjects(ResultSet rs) throws DataAccessException{
		List<Repair> res = new ArrayList<>();
		try {
			while (rs.next()) {
				Repair currRepair = buildObject(rs);
				res.add(currRepair);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}

	
}
