/**
 * @author Radoslaw Milek
 * @since 2020-05
 */

package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import db.AdminDB;
import db.DataAccessException;

public class SettingsUI extends GuiTools {
	
	double screenWidth, screenHeight;
	JFrame frame;
	JPanel contentPanel;
	
	boolean correct;
	

	public SettingsUI(JFrame frame, JPanel contentPanel, double screenWidth, double screenHeight) {
		this.frame = frame;
		this.contentPanel = contentPanel;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		startLoadingAnimation(frame, contentPanel);
		//Initialazing frame in new thread so the loadingAnimation would play during loading
		Thread t1 = new Thread(new Runnable()
				{
				public void run() {
					
					contentPanel.removeAll();
					stopLoadingAnimation(frame, contentPanel);
					
					//When password is correct, it will progress further
					try{typePassword();}
					catch(DataAccessException e) {}
					
					contentPanel.removeAll();
					initialize();
					frame.repaint();
				}
				});
		t1.start();
	}
	private void passwordCorrect() {
		correct = true;
	}
	
	public synchronized void typePassword() throws DataAccessException {
	        
	        /* mp = new ManagePassword();
	        String pass = null;
	        String input = null;*/
	        
	        JPanel errorMessage = new JPanel();			
			errorMessage.setBackground(Color.LIGHT_GRAY);
			errorMessage.setOpaque(true);
			errorMessage.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(30 * (screenWidth / 1920))));
			errorMessage.setBounds((int) Math.ceil(screenWidth*0.30729), (int) Math.ceil(screenHeight*0.10833), (int) Math.ceil(screenWidth*0.33489), (int) Math.ceil(screenHeight*0.36388));
			errorMessage.setLayout(null);
			contentPanel.add(errorMessage);
			
			JLabel label = new JLabel("Type in a Password:");
			label.setForeground(Color.BLACK);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(40 * (screenWidth / 1920))));
			label.setBounds((int) Math.ceil(screenWidth*0.04687), (int) Math.ceil(screenHeight*0.02685), (int) Math.ceil(screenWidth*0.24270), (int) Math.ceil(screenHeight*0.09259));
			errorMessage.add(label);
			
			JPasswordField text = new JPasswordField();
			text.setForeground(Color.BLACK);
			text.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(60 * (screenWidth / 1920))));
			text.setBounds((int) Math.ceil(screenWidth*0.01562), (int) Math.ceil(screenHeight*0.14351), (int) Math.ceil(screenWidth*0.30364), (int) Math.ceil(screenHeight*0.09259));
			errorMessage.add(text);
			
			JButton button = new JButton("LOG IN");
			button.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(16 * (screenWidth / 1920))));
			button.setHorizontalAlignment(JButton.CENTER);
			button.setBounds((int) Math.ceil(screenWidth * 0.13906), (int) Math.ceil(screenHeight * 0.30648), (int) Math.ceil(screenWidth * 0.05520), (int) Math.ceil(screenHeight * 0.03148));
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					Thread t1 = new Thread(new Runnable()
					{
					public void run() {
						ManagePassword mp = new ManagePassword();
						String pass = null;
						char[] inputPassword = text.getPassword();
				        String inputPass = new String(inputPassword);
						String input = inputPass;
						
						try {
				            input = mp.generateHash(input); 
				        } catch (NoSuchAlgorithmException a) {
				            a.printStackTrace();
				        }
				
				
				        try {
				            AdminDB adm = new AdminDB();
				            //adm.changePassword(input); use this method when password in database not hashes (means error in manage password)
				            pass = adm.getPassword();  
				        } catch (DataAccessException a) {
				            a.printStackTrace();
				        }
				
						if(input.equals(pass.toLowerCase())) {
							passwordCorrect();
				            }
				            else {
				            	errorMessage(frame, contentPanel, "Incorrect Password");
				            }
						
				        /*try {
				        //ManagePassword mp = new ManagePassword();
				        mp.compare(mp.generateHash("Password"), pass);
				        
				        System.out.println(mp.compare(mp.generateHash("Password"), pass));
				                e.printStackTrace();
				        }*/
	            	}
					});
			t1.start();
			        
				}
			});
			
			errorMessage.add(button);
			
			frame.repaint();
			
			while(!correct){
				try{Thread.sleep(20);}
				catch(Exception a) {}
			}
	    }

	
	private void initialize(){
		JLabel manage1 = new JLabel("Manage Category");
		manage1.setForeground(Color.WHITE);
		manage1.setBackground(new Color(64, 64, 64, 255));
		manage1.setOpaque(true);
		manage1.setHorizontalAlignment(JLabel.CENTER);
		manage1.setVerticalAlignment(JLabel.CENTER);
		manage1.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(22 * (screenWidth / 1920))));
		manage1.setBounds((int) Math.ceil(screenWidth * 0.20104), (int) Math.ceil(screenHeight * 0.04629),
				(int) Math.ceil(screenWidth * 0.10416), (int) Math.ceil(screenHeight * 0.05555));
		manage1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				manage1.setBackground(new Color(107, 107, 107, 255));
			}
		});
		manage1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				manage1.setBackground(new Color(64, 64, 64, 255));
			}
		});
		manage1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try{ManageCategoryUI manage = new ManageCategoryUI();}
				catch(DataAccessException a) {}
			}
		});
		
		JLabel manage2 = new JLabel("Manage Repairs");
		manage2.setForeground(Color.WHITE);
		manage2.setBackground(new Color(64, 64, 64, 255));
		manage2.setOpaque(true);
		manage2.setHorizontalAlignment(JLabel.CENTER);
		manage2.setVerticalAlignment(JLabel.CENTER);
		manage2.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(22 * (screenWidth / 1920))));
		manage2.setBounds((int) Math.ceil(screenWidth * 0.34947), (int) Math.ceil(screenHeight * 0.04629),
				(int) Math.ceil(screenWidth * 0.10416), (int) Math.ceil(screenHeight * 0.05555));
		manage2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				manage2.setBackground(new Color(107, 107, 107, 255));
			}
		});
		manage2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				manage2.setBackground(new Color(64, 64, 64, 255));
			}
		});
		manage2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try{ManageRepairItemUI manage = new ManageRepairItemUI();}
				catch(DataAccessException a) {}
			}
		});
		
		JLabel manage3 = new JLabel("Manage parts");
		manage3.setForeground(Color.WHITE);
		manage3.setBackground(new Color(64, 64, 64, 255));
		manage3.setOpaque(true);
		manage3.setHorizontalAlignment(JLabel.CENTER);
		manage3.setVerticalAlignment(JLabel.CENTER);
		manage3.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(22 * (screenWidth / 1920))));
		manage3.setBounds((int) Math.ceil(screenWidth * 0.49531), (int) Math.ceil(screenHeight * 0.04629),
				(int) Math.ceil(screenWidth * 0.10416), (int) Math.ceil(screenHeight * 0.05555));
		manage3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				manage3.setBackground(new Color(107, 107, 107, 255));
			}
		});
		manage3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				manage3.setBackground(new Color(64, 64, 64, 255));
			}
		});
		manage3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try{ManagePartUI manage = new ManagePartUI();}
				catch(DataAccessException a) {}
			}
		});
		
		JLabel manage4 = new JLabel("Manage password");
		manage4.setForeground(Color.WHITE);
		manage4.setBackground(new Color(64, 64, 64, 255));
		manage4.setOpaque(true);
		manage4.setHorizontalAlignment(JLabel.CENTER);
		manage4.setVerticalAlignment(JLabel.CENTER);
		manage4.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(22 * (screenWidth / 1920))));
		manage4.setBounds((int) Math.ceil(screenWidth * 0.64114), (int) Math.ceil(screenHeight * 0.04629),
				(int) Math.ceil(screenWidth * 0.10416), (int) Math.ceil(screenHeight * 0.05555));
		manage4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				manage4.setBackground(new Color(107, 107, 107, 255));
			}
		});
		manage4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				manage4.setBackground(new Color(64, 64, 64, 255));
			}
		});
		manage4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try{ManagePasswordUI manage = new ManagePasswordUI();}
				catch(DataAccessException a) {}
			}
		});
		

			contentPanel.add(manage1);
			contentPanel.add(manage2);
			contentPanel.add(manage3);
			contentPanel.add(manage4);

	}
	
}
