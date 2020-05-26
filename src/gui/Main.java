package gui;

import javax.swing.UIManager;

public class Main {

	public static void main(String[] agrs) {
		//Set system window look
        try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch(Exception e) {}
		
			MainPanelUI mainPanelUI = new MainPanelUI();
	}


	
	
}
