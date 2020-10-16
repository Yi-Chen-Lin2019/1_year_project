package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Bike;
import model.UsedPart;

public class BikeDB implements BikeDBIF{
	
	private static String FIND_ALL_Q = "Select BikeId, SerialNumber, BikeName, RegisterDate, SoldDate, Gender, FinalPrice, SalePrice, TotalPartPrice, RepairListId, isExternalGear FROM Bike";
	private static String FIND_BY_ID_Q = FIND_ALL_Q + " WHERE Bike.BikeId = ?";
	private static String FIND_BY_SERIALN0_Q = FIND_ALL_Q + " WHERE Bike.SerialNumber = ?";
	private static String UPDATE_Q = "Update Bike SET BikeName = ?, SoldDate = ?, Gender = ?, isExternalGear = ?, FinalPrice = ?, SalePrice = ? , TotalPartPrice = ?  WHERE BikeId = ?";
	private static String INSERT_Q = "INSERT INTO Bike (SerialNumber, BikeName, RegisterDate, SoldDate, Gender, TotalPartPrice, FinalPrice, SalePrice, RepairListId, IsExternalGear) values (?,?,?,?,?,?,?,?,?,?)";
	private static String DELETE_Q = "  delete from Repair where Repair.RepairListId = (Select RepairListId from Bike where BikeId = ?);" + 
			"" + 
			"  delete from Bike where BikeId = ?  " + 
			"" + 
			" delete from RepairList where RepairListId = ?";
	private PreparedStatement findAll;
	private PreparedStatement findById;
	private PreparedStatement findBySerialNo;
	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;

	public BikeDB() throws DataAccessException {
		init();
	}
	private void init() throws DataAccessException {
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			findAll = connection.prepareStatement(FIND_ALL_Q);
			findById = connection.prepareStatement(FIND_BY_ID_Q);
			findBySerialNo = connection.prepareStatement(FIND_BY_SERIALN0_Q);
			insertPS = connection.prepareStatement(INSERT_Q, Statement.RETURN_GENERATED_KEYS);
			updatePS = connection.prepareStatement(UPDATE_Q);
			deletePS = connection.prepareStatement(DELETE_Q);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}
	
	@Override
	public List<Bike> findAll() throws DataAccessException {
		List<Bike> res = new ArrayList<>(0);

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
	public Bike findById(int id) throws DataAccessException {
		Bike res = null;
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
	public Bike updateBike(Bike bike) throws DataAccessException {
		//BikeName = ?, SoldDate = ?, Gender = ?, isExternalGear = ?, FinalPrice = ?, 
		//SalePrice = ? , TotalPartPrice = ?  WHERE BikeId = ?
		try {
			updatePS.setString(1, bike.getBikeName());
			
			if(bike.getSoldDate()==null) {
				updatePS.setNull(2, Types.DATE);
			} else {
				updatePS.setTimestamp(2, java.sql.Timestamp.valueOf(bike.getSoldDate()));
			}		
			updatePS.setString(3, bike.getGender());
			
			if (bike.getIsExternalGear()) {
				updatePS.setInt(4, 0);
			} else {
				updatePS.setInt(4, 1);
			}			
			
			updatePS.setDouble(5, bike.getFinalPrice());
			updatePS.setDouble(6, bike.getSalePrice());
			updatePS.setDouble(7, bike.getTotalPartPrice());
			updatePS.setInt(8, bike.getId());
			
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			updatePS.executeUpdate();
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_UPDATE, e);
		}
		return bike;
	}

	@Override
	public Bike insertBike(Bike bike) throws DataAccessException {
	
		//INSERT INTO Bike (SerialNumber, BikeName, RegisterDate, SoldDate, Gender, 
		//TotalPartPrice, FinalPrice, SalePrice, RepairListId, IsExternalGear)
		try {
			
			insertPS.setString(1, bike.getSerialNumber());
			insertPS.setString(2, bike.getBikeName());
					
			insertPS.setTimestamp(3, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
			insertPS.setNull(4, Types.DATE);
			
			insertPS.setString(5, bike.getGender());

			insertPS.setFloat(6, 0); //TotalPartPrice
			insertPS.setFloat(7, 0); //FinalPrice
			insertPS.setFloat(8, 0); //SalePrice
			
			insertPS.setInt(9, bike.getRepairList().getId());

			if (bike.getIsExternalGear()) {
				insertPS.setInt(10, 0);
			} else {
				insertPS.setInt(10, 1);
			}

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_PS_VARS_INSERT, e);
		}
		try {
			insertPS.executeUpdate();
			ResultSet keys = insertPS.getGeneratedKeys();
			if (keys.next()) {
				bike.setId(keys.getInt(1));
		    }
			
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_INSERT, e);
		}
		return bike;
		
	}

	@Override
	public boolean deleteBike(Bike bike) throws DataAccessException {
		boolean success = false;
		try {
			deletePS.setInt(1, bike.getId());
			deletePS.setInt(2, bike.getId());
			deletePS.setInt(3, bike.getRepairList().getId());
			deletePS.executeUpdate();
			success = true;
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return success;
	}
	
	private Bike buildObject(ResultSet rs) throws DataAccessException {
		Bike res = new Bike();
		try {		
			//BikeId, SerialNumber, BikeName, RegisterDate, SoldDate, 
			//Gender, FinalPrice, SalePrice, TotalPartPrice, RepairListId, isExternalGear
			res.setId(rs.getInt("BikeId"));
			res.setSerialNumber(rs.getString("SerialNumber"));
			res.setBikeName(rs.getString("BikeName"));
			

			res.setRegisterDate(rs.getObject(4, LocalDateTime.class));
			
			
			if (rs.getDate("SoldDate") != null) {
				res.setSoldDate(rs.getObject(5, LocalDateTime.class));
			}
			else {
				res.setSoldDate(null);
			}
			
			
			res.setGender(rs.getString("Gender"));
			
			UsedPartDB u = new UsedPartDB();
			List<UsedPart> usedParts = u.findAllByBikeId(rs.getInt("BikeId"));
			if(usedParts.size()!=0) {
				for (UsedPart usedPart : usedParts) {
					res.addUsedPart(usedPart);
				}
			}
			
			
			res.setTotalPartPrice(rs.getDouble("TotalPartPrice"));
			res.setFinalPrice(rs.getDouble("FinalPrice"));
			res.setSalePrice(rs.getDouble("SalePrice"));
		
				
			if(rs.getInt("isExternalGear") == 0) {
				res.setIsExternalGear(true);
			} else {
				res.setIsExternalGear(false);
			}
			
			
			RepairListDB r = new RepairListDB();
			res.createRepairList(r.findById(rs.getInt("RepairListId")));
			
					

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}
	
	private List<Bike> buildObjects(ResultSet rs) throws DataAccessException{
		List<Bike> res = new ArrayList<>();
		try {
			while (rs.next()) {
				Bike currBike = buildObject(rs);
				res.add(currBike);
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return res;
	}
	
	@Override
	public List<Bike> findBySerialNumber(String number) throws DataAccessException {
		List<Bike> res = new ArrayList<>(0);
		try {		
			findBySerialNo.setString(1, number);
			ResultSet rs = findBySerialNo.executeQuery();
			res = buildObjects(rs);
			
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return res;
		
	}
	

}
