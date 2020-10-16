package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AdminDB {
    
	private static String GETPASS_Q = "SELECT AccountPassword FROM AdminAccount WHERE AdminId = 1";
	private static String UPDATEPASS_Q = "update AdminAccount set AccountPassword = ? WHERE AdminId = 1";
	private PreparedStatement getPassPS;
	private PreparedStatement updatePassPS;

    public AdminDB() throws DataAccessException {
		init();
	}
	private void init() throws DataAccessException {
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			getPassPS = connection.prepareStatement(GETPASS_Q);
			updatePassPS = connection.prepareStatement(UPDATEPASS_Q);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
    }
    
    public String getPassword() throws DataAccessException {
		String password = "";
		try {
			ResultSet rs = this.getPassPS.executeQuery();
			if (rs.next()) {
			password = rs.getString("AccountPassword");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return password;

	}

	public boolean changePassword(String pass) throws DataAccessException {
		try {
			this.updatePassPS.setString(1, pass);
			this.updatePassPS.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}