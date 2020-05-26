package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Part;
import model.Repair;

public class PartDB implements PartDBIF {
	private static String FIND_ALL_Q = "Select PartId, PartName, UsedPrice, NewPrice, CategoryId, isAvailable from Part ";
	private static String FIND_BY_ID_Q = FIND_ALL_Q + " where PartId = ?";
	private static String FIND_BY_NAME_Q = FIND_ALL_Q + " where PartName like '%?%'";
	private static String INSERT_Q = "INSERT INTO Part (PartName, UsedPrice, NewPrice, CategoryId, isAvailable) values (?, ?, ?, ?, 1)";
	private static String UPDATE_Q = "Update Part SET PartName = ?, UsedPrice = ?, NewPrice = ?, CategoryId = ?, isAvailable = ?  WHERE PartId = ?";
	private static String DELETE_Q = "Update Part SET isAvailable = 0 where PartId = ?";

	
	private PreparedStatement findAll;
	private PreparedStatement findById;
	private PreparedStatement findByName;
	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	
	public PartDB() {
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
			findByName = connection.prepareStatement(FIND_BY_ID_Q);
			insertPS = connection.prepareStatement(INSERT_Q, Statement.RETURN_GENERATED_KEYS);
			updatePS = connection.prepareStatement(UPDATE_Q);
			deletePS = connection.prepareStatement(DELETE_Q);

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}
	
	public List<Part> findAll() throws DataAccessException {
		List<Part> res = new ArrayList<>(0);

		try {
			ResultSet rs = this.findAll.executeQuery();
			res = buildObjects(rs);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}
	
	public Part findById(int id) throws DataAccessException {
		Part res = null;
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
	
	public List<Part> findAllByName(String name) throws DataAccessException {
		List<Part> res = new ArrayList<>(0);

		try {
			findByName.setString(1, name);
			ResultSet rs = this.findByName.executeQuery();
			res = buildObjects(rs);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}
	
	public Part updatePart(Part part) throws DataAccessException {
		//PartName = ?, UsedPrice = ?, NewPrice = ?, CategoryId = ?, isAvailable = ?  WHERE RepairId = ?
				try {
					
					updatePS.setString(1, part.getName());
					updatePS.setDouble(2, part.getUsedPrice());
					updatePS.setDouble(3, part.getNewPrice());
					updatePS.setInt(4,  part.getCategory().getCategoryId());
					updatePS.setBoolean(5,  part.getIsAvailable());
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
				return part;
			}
	
	public Part insertPart(Part part) throws DataAccessException {
		//new part is available by default
		//(PartName, UsedPrice, NewPrice, CategoryId, isAvailable) values (?, ?, ?, ?, 1)";
		try {
			insertPS.setString(1, part.getName());
			insertPS.setDouble(2, part.getUsedPrice());
			insertPS.setDouble(3, part.getNewPrice());
			insertPS.setInt(4, part.getCategory().getCategoryId());
			
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_PS_VARS_INSERT, e);
		}
		try {
			insertPS.executeUpdate();
			ResultSet keys = insertPS.getGeneratedKeys();
			if (keys.next()) {
				part.setId(keys.getInt(1));
		    }
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_INSERT, e);
		}
		return part;
	}
	
	private Part buildObject(ResultSet rs) throws DataAccessException {
		Part res = new Part();
		try {
			res.setId(rs.getInt("PartId"));
			res.setName(rs.getString("PartName"));
			res.setUsedPrice(rs.getDouble("UsedPrice"));
			res.setNewPrice(rs.getDouble("NewPrice"));
			res.setCategory(new CategoryDB().findById(rs.getInt("CategoryId")));
//			res.setCategory(new CategoryDB().buildObject(rs));
			res.setIsAvailable(rs.getBoolean("isAvailable"));

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}
	
	private List<Part> buildObjects(ResultSet rs) throws DataAccessException{
		List<Part> res = new ArrayList<>();
		try {
			while (rs.next()) {
				Part currPart = buildObject(rs);
				res.add(currPart);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}

	@Override
	public boolean deletePart(Part part) throws DataAccessException {
		try {
			
			deletePS.setInt(1, part.getId());
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_PS_VARS_INSERT, e);
		}
		try {
			int i = deletePS.executeUpdate();
			if (i > 0) {
				return true;
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_INSERT, e);
		}
		return false;
	}

}
