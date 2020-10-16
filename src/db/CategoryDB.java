package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Bike;
import model.Category;
import model.RepairItem;
import model.RepairList;

public class CategoryDB implements CategoryDBIF {
	private Connection connection;
	private static String FIND_ALL_Q = "Select CategoryId, CategoryName, Color from Category where isDisabled = 0";
	private static String FIND_BY_ID_Q = "Select CategoryId, CategoryName, Color from Category where CategoryId = ? and IsDisabled = 0";
	private static String INSERT_Q = "INSERT INTO Category (CategoryName, Color) values (?, ?)";
	private static String UPDATE_Q = "Update Category SET CategoryName = ?, Color = ? WHERE CategoryId = ?";
	private static String DELETE_Q = "Update Category SET IsDisabled = 1 WHERE CategoryId = ?";

	private PreparedStatement findAll;
	private PreparedStatement findById;
	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	
	public CategoryDB() {
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
			updatePS = connection.prepareStatement(UPDATE_Q);
			deletePS = connection.prepareStatement(DELETE_Q);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}
	
	@Override
	public List<Category> findAll() throws DataAccessException {
		List<Category> res = new ArrayList<>(0);

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
	public Category findById(int id) throws DataAccessException {
		Category res = null;
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
	public Category updateCategory(Category category) throws DataAccessException {
		//Update Category SET CategoryName = ?, Color = ? WHERE CategoryId = ?
		try {
			updatePS.setString(1, category.getCategoryName());
			updatePS.setString(2, category.getColour());
			updatePS.setInt(3, category.getCategoryId());
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
		return category;
	}

	@Override
	public boolean deleteCategory(Category category) throws DataAccessException {
				boolean success = false;

				try {
					deletePS.setInt(1, category.getCategoryId());
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

	@Override
	public Category insertCategory(Category category) throws DataAccessException {
		//INSERT INTO Category (CategoryName, Color) values (?, ?)
		try {
			insertPS.setString(1, category.getCategoryName());
			insertPS.setString(2, category.getColour());
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_PS_VARS_INSERT, e);
		}
		
		try {
			insertPS.executeUpdate();
			ResultSet keys = insertPS.getGeneratedKeys();
			if (keys.next()) {
				category.setCategoryId(keys.getInt(1));
		    }

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_INSERT, e);
		}
		return category;
	}
	
	private Category buildObject(ResultSet rs) throws DataAccessException {
		//RepairListId, Note, isFinished
		Category res = new Category();
		try {
			res.setCategoryId(rs.getInt("CategoryId"));
			res.setCategoryName(rs.getString("CategoryName"));
			res.setColour(rs.getString("Color"));

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}
	
	private List<Category> buildObjects(ResultSet rs) throws DataAccessException{
		List<Category> res = new ArrayList<>();
		try {
			while (rs.next()) {
				Category currCat = buildObject(rs);
				res.add(currCat);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}

}
