package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Bike;
import model.UsedPart;

public class BikeInfoDB implements BikeDBIF{
	
	private static String FIND_ALL_Q = "SELECT BikeId, SerialNumber, BikeName, RegisterDate, SoldDate, Gender, FinalPrice, SalePrice FROM BikeInfo";
	private static String FIND_BY_ID_Q = FIND_ALL_Q + " WHERE Bike.BikeId = ?";
	private static String FIND_BY_SERIALN0_Q = FIND_ALL_Q + " WHERE Bike.SerialNumber = ?";
	private PreparedStatement findAll;
	private PreparedStatement findById;
	private PreparedStatement findBySerialNo;


	public BikeInfoDB() throws DataAccessException {
		init();
	}
	private void init() throws DataAccessException {
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			findAll = connection.prepareStatement(FIND_ALL_Q);
			findById = connection.prepareStatement(FIND_BY_ID_Q);
			findBySerialNo = connection.prepareStatement(FIND_BY_SERIALN0_Q);
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
			
			
			//res.setTotalPartPrice(rs.getDouble("TotalPartPrice"));
			res.setFinalPrice(rs.getDouble("FinalPrice"));
			res.setSalePrice(rs.getDouble("SalePrice"));
		
			/*	
			if(rs.getInt("isExternalGear") == 0) {
				res.setIsExternalGear(true);
			} else {
				res.setIsExternalGear(false);
			}
			
			
			RepairListDB r = new RepairListDB();
			res.createRepairList(r.findById(rs.getInt("RepairListId")));
			*/
					

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

	@Override
	public Bike updateBike(Bike bike) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bike insertBike(Bike bike) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteBike(Bike bike) throws DataAccessException {
		// TODO Auto-generated method stub
		return false;
	}
	
}
