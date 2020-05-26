package Test;


import static org.junit.Assert.*;

import org.junit.*;
import java.time.LocalDate;

import db.*;


/**
 * Inspired by the book: Flexible, Reliable Software Henrik B�rbak Christensen:
 * Flexible, Reliable Software. Taylor and Francis Group, LLC 2010
 */

public class TestDatabaseAccess {
	
	DBConnection con = null;
	

	@Before
	public void setUp() throws DataAccessException {
		con = DBConnection.getInstance();
	}
	
	
	@Test
	public void wasConnected() throws DataAccessException {
		assertNotNull("Connected - connection cannot be null", con);
		
		DBConnection.disconnect();
		boolean wasNull = DBConnection.instanceIsNull();
		assertTrue("Disconnected - instance set to null", wasNull);
		
		con = DBConnection.getInstance();
		assertNotNull("Connected - connection cannot be null", con);	
		
		
	}

	
	
	/**  */
	@After
	public void cleanUp() {
		DBConnection.disconnect();
	}	
	
	@AfterClass
	public static void cleanUpWhenFinish() {
		 		
		// Arrange
		
		
		// Act
		try {
		} catch(Exception ex) { 
			System.out.println("Error: " + ex.getMessage());
		} finally {
			DBConnection.disconnect();
		}
	
		// Assert
	}	

}
