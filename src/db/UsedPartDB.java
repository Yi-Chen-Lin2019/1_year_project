package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Bike;
import model.Part;
import model.UsedPart;

public class UsedPartDB implements UsedPartDBIF {
	private static String FIND_ALL_Q = "Select UsedPartId, isNew, BikeId, PartId from UsedPart ";
	private static String FIND_BY_ID_Q = FIND_ALL_Q + " where UsedPartId = ?";
	private static String FIND_BY_BIKE_ID_Q = FIND_ALL_Q + " where BikeId = ?";
	private static String INSERT_Q = "INSERT INTO UsedPart (isNew, BikeId, PartId) values (?, ?, ?)";
	private static String DELETE_Q = "DELETE FROM UsedPart where UsedPartId = ?";

//we don't need update, one field that can change only. so delete and reinsert is more sensible
	
	private PreparedStatement findAll;
	private PreparedStatement findById;
	private PreparedStatement findByBikeId;
	private PreparedStatement insertPS;
	private PreparedStatement deletePS;


	
	public UsedPartDB() {
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
			findByBikeId = connection.prepareStatement(FIND_BY_BIKE_ID_Q);
			insertPS = connection.prepareStatement(INSERT_Q, Statement.RETURN_GENERATED_KEYS);
			deletePS = connection.prepareStatement(DELETE_Q);

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}
	
	@Override
	public List<UsedPart> findAll() throws DataAccessException {
		List<UsedPart> res = new ArrayList<>(0);

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
	public UsedPart findById(int id) throws DataAccessException {
		UsedPart res = null;
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
	public UsedPart insertUsedPart(UsedPart usedPart, int BikeId) throws DataAccessException {
//INSERT INTO UsedPart (isNew, BikeId, PartId) values (?, ?, ?)
		try {
					insertPS.setBoolean(1, usedPart.getIsNew());
					insertPS.setInt(2, BikeId);
					insertPS.setInt(3, usedPart.getPart().getId());
					
				} catch (SQLException e) {
					// e.printStackTrace();
					throw new DataAccessException(DBMessages.COULD_NOT_BIND_PS_VARS_INSERT, e);
				}
				try {
					insertPS.executeUpdate();
					ResultSet keys = insertPS.getGeneratedKeys();
					if (keys.next()) {
						usedPart.setId(keys.getInt(1));
				    }
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DataAccessException(DBMessages.COULD_NOT_INSERT, e);
				}
				return usedPart;
			}
	
	private UsedPart buildObject(ResultSet rs) throws DataAccessException {
		UsedPart res = new UsedPart();
		try {
			res.setId(rs.getInt("UsedPartId"));
			res.setIsNew(rs.getBoolean("isNew"));
			res.setPart(new PartDB().findById(rs.getInt("PartId")));

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}
	
	private List<UsedPart> buildObjects(ResultSet rs) throws DataAccessException{
		List<UsedPart> res = new ArrayList<>();
		try {
			while (rs.next()) {
				UsedPart currPart = buildObject(rs);
				res.add(currPart);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}
	
	public List<UsedPart> findAllByBikeId(int bikeId) throws DataAccessException {
		List<UsedPart> res = new ArrayList<>(0);
		try {
			findByBikeId.setInt(1, bikeId);
			ResultSet rs = findByBikeId.executeQuery();
			res = buildObjects(rs);
		} catch (SQLException e) {
			 e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return res;
	}
	
	@Override
	public boolean deleteUsedPart(UsedPart usedPart) throws DataAccessException {
		try {
			deletePS.setInt(1, usedPart.getId());
			int i = deletePS.executeUpdate();
			if (i > 0) {
				return true;
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return false;
	}

	
	
}
