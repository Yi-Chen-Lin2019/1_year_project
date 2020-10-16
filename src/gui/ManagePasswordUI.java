package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import controller.PartCtr;
import db.AdminDB;
import db.DataAccessException;

public class ManagePasswordUI {
	
	double screenWidth, screenHeight;
	JFrame frame;

	PartCtr partCtr;	
	//labels and fields
	JLabel process;
    JLabel currentPassLabel;
    JPasswordField currentPassField;
    JLabel newPassLabel;
    JPasswordField newPassField;
    JLabel confirmNewPassLabel;
	JPasswordField confirmNewPassField;
	String currentPassword;
	String newPassword;
	String confirmPassword;
    JButton saveButton = new JButton("Change password");

	
	public ManagePasswordUI() throws DataAccessException {
		AdminDB adm = new AdminDB();
		frame = new JFrame("Manage password");
		frame.setSize(1280, 720);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		
		
		initializeFields();
		
		saveButton = new JButton("Change password");
		saveButton.setBounds(200, 360, 130, 30);
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				char[] currentPass = currentPassField.getPassword();
				currentPassword = new String(currentPass);
				char[] newPass = newPassField.getPassword();
				newPassword = new String(newPass);
				char[] confirmPass = confirmNewPassField.getPassword();
				confirmPassword = new String(confirmPass);

				ManagePassword mp = new ManagePassword();
				String pass = null;
				String input = currentPassword;

				try {
				input = mp.generateHash(input);
				//newPassword = mp.generateHash(newPassword);
				//confirmPassword = mp.generateHash(confirmPassword);
				} catch (NoSuchAlgorithmException en) {
					en.printStackTrace();
				}

				try {
				pass = adm.getPassword();

				} catch (DataAccessException d) {
					d.printStackTrace();
				}

				try {
				if(input.equals(pass.toLowerCase())) {
					try {
					newPassword = mp.generateHash(newPassword);
					confirmPassword = mp.generateHash(confirmPassword);
					} catch (NoSuchAlgorithmException ns) {
						ns.printStackTrace();
					}
					if(newPassword.equals(pass)) {
					System.out.println("Current password and new password can not be the same");
					} else if(newPassword.equals(confirmPassword)) {
						/*try {
						newPassword = mp.generateHash(newPassword);
						} catch (NoSuchAlgorithmException no) {
							no.printStackTrace();
						}*/
						adm.changePassword(newPassword);
					} else if(!confirmPassword.equals(newPassword)) {
						System.out.println("confirmed password not same as new password");
					}
				} else {
					System.out.println("Wrong password");
				}
			} catch (DataAccessException dt) {
				dt.printStackTrace();
			}
				
				
			}
		});
		
		frame.getContentPane().add(saveButton);
		

			
		frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
		frame.setVisible(true);
	}
	
	

	//initialize labels and fields
	protected void initializeFields() throws DataAccessException {
	    currentPassLabel = new JLabel("CURRENT PASSWORD");
	    newPassLabel = new JLabel("NEW PASSWORD");
	    confirmNewPassLabel = new JLabel("REPEAT NEW PASSWORD");
	    currentPassField = new JPasswordField();
	    newPassField = new JPasswordField();
	    confirmNewPassField = new JPasswordField();
	    
	    currentPassLabel.setBounds(200, 120, 200, 30);
	    currentPassField.setBounds(200, 160, 200, 30);
	    newPassLabel.setBounds(200, 200, 200, 30);
	    newPassField.setBounds(200, 240, 200, 30);
	    confirmNewPassLabel.setBounds(200, 280, 200, 30);
	    confirmNewPassField.setBounds(200, 320, 200, 30);

        frame.getContentPane().add(currentPassLabel);
        frame.getContentPane().add(currentPassField);
        frame.getContentPane().add(newPassLabel);
        frame.getContentPane().add(newPassField);
        frame.getContentPane().add(confirmNewPassLabel);
        frame.getContentPane().add(confirmNewPassField);
	}

	private void resetFields() {
		currentPassField.setText("");
		newPassField.setText("");
		confirmNewPassField.setText("");
	}
	
}
