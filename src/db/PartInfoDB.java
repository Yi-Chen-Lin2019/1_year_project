package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import db.DBMessages;
import db.DataAccessException;
import db.PartDBIF;
import model.Category;
import model.Part;

public class PartInfoDB implements PartDBIF {
	
	private static String FIND_ALL_Q = "Select PartId, PartName, UsedPrice, NewPrice, CategoryName, Color, isAvailable FROM PartInfo";
	private static String FIND_BY_ID_Q = FIND_ALL_Q + " where PartId = ?";
	private static String FIND_BY_NAME_Q = FIND_ALL_Q + " where PartName like '%?%'";

	
	private PreparedStatement findAll;
	private PreparedStatement findById;
	private PreparedStatement findByName;

	public PartInfoDB() {
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
			findByName = connection.prepareStatement(FIND_BY_NAME_Q);

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}

	@Override
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

	@Override
	public Part updatePart(Part part) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Part insertPart(Part part) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deletePart(Part part) throws DataAccessException {

		// TODO Auto-generated method stub
		return false;
	}
	private Part buildObject(ResultSet rs) throws DataAccessException {
		Part res = new Part();
		try {
			res.setId(rs.getInt("PartId"));
			res.setName(rs.getString("PartName"));
			res.setUsedPrice(rs.getDouble("UsedPrice"));
			res.setNewPrice(rs.getDouble("NewPrice"));
			res.setCategory(new Category(rs.getString("CategoryName"), rs.getString("Color")));

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

}
