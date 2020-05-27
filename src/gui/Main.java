package gui;

import javax.swing.UIManager;

import db.DataAccessException;

public class Main {

	public static void main(String[] agrs) throws DataAccessException {
		//Set system window look
        try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch(Exception e) {}
		
			MainPanelUI mainPanelUI = new MainPanelUI();
       // ManagePartUI mpUI = new ManagePartUI();
	}


	
	
}
