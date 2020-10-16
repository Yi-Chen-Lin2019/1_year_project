package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.RepairItem;

public class RepairItemDB implements RepairItemDBIF {
	Connection connection;
	private static String FIND_ALL = "Select RepairItemId, RepairItemName, CategoryId from RepairItem";
	private static String FIND_ALL_Q = "Select RepairItemId, RepairItemName, CategoryId from RepairItem where IsDisabled = 0";
	private static String FIND_BY_ID_Q = FIND_ALL + " where RepairItemId = ? and IsDisabled = 0";
	private static String FIND_BY_GEARTYPE = FIND_ALL + " where CategoryId != ? and IsDisabled = 0";
	private static String INSERT_Q = "INSERT INTO RepairItem (RepairItemName, CategoryId) values (?, ?)";
	private static String UPDATE_Q = "Update RepairItem SET RepairItemName = ?, CategoryId = ? WHERE RepairItemId = ?";
	private static String DELETE_Q = "Update RepairItem SET IsDisabled = 1 where RepairItemId = ?";
	private PreparedStatement findAll;
	private PreparedStatement findById;
	private PreparedStatement findByGearTypePS;
	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;

	
	public RepairItemDB() {
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
			findByGearTypePS = connection.prepareStatement(FIND_BY_GEARTYPE);
			insertPS = connection.prepareStatement(INSERT_Q, Statement.RETURN_GENERATED_KEYS);
			updatePS = connection.prepareStatement(UPDATE_Q);
			deletePS = connection.prepareStatement(DELETE_Q);

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}
	
	@Override
	public List<RepairItem> findAll() throws DataAccessException {
		List<RepairItem> res = new ArrayList<>(0);

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
	public RepairItem findById(int id) throws DataAccessException {
		RepairItem res = null;
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
	public RepairItem updateRepairItem(RepairItem repairItem) throws DataAccessException {
		//RepairItemName = ?, CategoryId = ? WHERE RepairItemId = ?
		try {
			updatePS.setString(1, repairItem.getName());
			updatePS.setInt(2, repairItem.getCategory().getCategoryId());
			updatePS.setInt(3, repairItem.getRepairItemId());
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
		return repairItem;
	}

	@Override
	public RepairItem insertRepairItem(RepairItem repairItem) throws DataAccessException {
				//(RepairItemName, CategoryId) values (?, ?)
				try {
					insertPS.setString(1, repairItem.getName());
					insertPS.setInt(2, repairItem.getCategory().getCategoryId());
				} catch (SQLException e) {
					// e.printStackTrace();
					throw new DataAccessException(DBMessages.COULD_NOT_BIND_PS_VARS_INSERT, e);
				}
				
				try {
					insertPS.executeUpdate();
					ResultSet keys = insertPS.getGeneratedKeys();
					if (keys.next()) {
						repairItem.setRepairItemId(keys.getInt(1));
				    }
				} catch (SQLException e) {
					// e.printStackTrace();
					throw new DataAccessException(DBMessages.COULD_NOT_INSERT, e);
				}
				return repairItem;
	}

	@Override
	public boolean deleteRepairItem(RepairItem repairItem) throws DataAccessException {
		boolean success = false;
		try {
			deletePS.setInt(1, repairItem.getRepairItemId());
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_PS_VARS_INSERT, e);
		}
		try {
			int temp = deletePS.executeUpdate();
			if (temp > 0) {
				success = true;
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_INSERT, e);
		}
		return success;
	}

	private RepairItem buildObject(ResultSet rs) throws DataAccessException {
		RepairItem res = new RepairItem();
		try {
			CategoryDB categoryDB = new CategoryDB();
			res.setCategory(categoryDB.findById(rs.getInt("CategoryId")));
			res.setName(rs.getString("RepairItemName"));
			res.setRepairItemId(rs.getInt("RepairItemId"));

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}
	
	private List<RepairItem> buildObjects(ResultSet rs) throws DataAccessException{
		List<RepairItem> res = new ArrayList<>();
		try {
			while (rs.next()) {
				RepairItem currRepItem = buildObject(rs);
				res.add(currRepItem);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}

	
	public List<RepairItem> findAllByGearType(boolean isExternal) throws DataAccessException {
		List<RepairItem> res = new ArrayList<>(0);

		try {
			int categoryId;
			if (isExternal) {
				categoryId = 2;
			}
			else {
				categoryId = 3;
			}
				
			findByGearTypePS.setInt(1, categoryId);
			ResultSet rs = this.findByGearTypePS.executeQuery();
			res = buildObjects(rs);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}

}
