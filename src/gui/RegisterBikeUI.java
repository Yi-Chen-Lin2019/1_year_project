/**
 * @author Radoslaw Milek
 * @since 2020-05
 */

package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.BikeCtr;
import db.DataAccessException;
import model.Bike;

public class RegisterBikeUI extends GuiTools{
	
	double screenWidth, screenHeight;
	JFrame frame;
	JPanel contentPanel;

	int[] checkBoxGenders = { 0, 0, 0 };
	int[] checkBoxGears = {0, 0};
	
	ImageGen checkBoxImage;
	
	BikeCtr bikeCtr;
	
	public RegisterBikeUI(JFrame frame, JPanel contentPanel, double screenWidth, double screenHeight) {
		this.frame = frame;
		this.contentPanel = contentPanel;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		startLoadingAnimation(frame, contentPanel);
		//Initialazing frame in new thread so the loadingAnimation would play during loading
		Thread t1 = new Thread(new Runnable()
				{
				public void run() {
					try {bikeCtr = new BikeCtr();}
					catch(DataAccessException e) {}
					
					initializeImages();
					contentPanel.removeAll();
					initialize();
					stopLoadingAnimation(frame, contentPanel);
					
					frame.repaint();
				}
				});
		t1.start();
	
	}
	
	private void initializeImages() {
		checkBoxImage = new ImageGen(4, 1, this.getClass().getResource("/checkBox.png"), (int) Math.ceil(screenWidth * 0.01302),(int) Math.ceil(screenWidth * 0.01302));
	}
	
	private void initialize() {
		JLabel titleText = new JLabel("Register a Bike");
		titleText.setForeground(Color.BLACK);
		titleText.setHorizontalAlignment(JLabel.LEFT);
		titleText.setVerticalAlignment(JLabel.BOTTOM);
		titleText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(30 * (screenWidth / 1920))));
		titleText.setBounds((int) Math.ceil(screenWidth * 0.42083), (int) Math.ceil(screenHeight * 0.02870), (int) Math.ceil(screenWidth * 0.10625), (int) Math.ceil(screenHeight * 0.03425));
		
		JLabel serialNumberText = new JLabel("Serial Number:");
		serialNumberText.setForeground(Color.BLACK);
		serialNumberText.setHorizontalAlignment(JLabel.LEFT);
		serialNumberText.setVerticalAlignment(JLabel.BOTTOM);
		serialNumberText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(19 * (screenWidth / 1920))));
		serialNumberText.setBounds((int) Math.ceil(screenWidth * 0.36041), (int) Math.ceil(screenHeight * 0.09074), (int) Math.ceil(screenWidth * 0.06510), (int) Math.ceil(screenHeight * 0.02314));
		
		JTextField serialNumberInput = new JTextField();
		serialNumberInput.setForeground(Color.BLACK);
		serialNumberInput.setHorizontalAlignment(JLabel.LEFT);
		serialNumberInput.setFont(new Font("Tahoma", Font.PLAIN, (int) Math.round(18 * (screenWidth / 1920))));
		serialNumberInput.setBounds((int) Math.ceil(screenWidth * 0.45416), (int) Math.ceil(screenHeight * 0.09074), (int) Math.ceil(screenWidth * 0.15625), (int) Math.ceil(screenHeight * 0.02314));
		
		JLabel bikeNameText = new JLabel("Bike Name:");
		bikeNameText.setForeground(Color.BLACK);
		bikeNameText.setHorizontalAlignment(JLabel.LEFT);
		bikeNameText.setVerticalAlignment(JLabel.BOTTOM);
		bikeNameText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(19 * (screenWidth / 1920))));
		bikeNameText.setBounds((int) Math.ceil(screenWidth * 0.37760), (int) Math.ceil(screenHeight * 0.12414), (int) Math.ceil(screenWidth * 0.0495),(int) Math.ceil(screenHeight * 0.02314));

		JTextField bikeNameInput = new JTextField();
		bikeNameInput.setForeground(Color.BLACK);
		bikeNameInput.setHorizontalAlignment(SwingConstants.LEFT);
		bikeNameInput.setFont(new Font("Tahoma", Font.PLAIN, (int) Math.round(18 * (screenWidth / 1920))));
		bikeNameInput.setBounds((int) Math.ceil(screenWidth * 0.45416), (int) Math.ceil(screenHeight * 0.12314), (int) Math.ceil(screenWidth * 0.15625), (int) Math.ceil(screenHeight * 0.02314));

		JLabel genderText = new JLabel("Gender:");
		genderText.setForeground(Color.BLACK);
		genderText.setHorizontalAlignment(JLabel.LEFT);
		genderText.setVerticalAlignment(JLabel.BOTTOM);
		genderText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(19 * (screenWidth / 1920))));
		genderText.setBounds((int) Math.ceil(screenWidth * 0.36510), (int) Math.ceil(screenHeight * 0.15805),(int) Math.ceil(screenWidth * 0.03541), (int) Math.ceil(screenHeight * 0.02314));

		JLabel genderCheckBox1 = new JLabel();
		JLabel genderCheckBox2 = new JLabel();
		JLabel genderCheckBox3 = new JLabel();
		genderCheckBox1.setBounds((int) Math.ceil(screenWidth * 0.41093), (int) Math.ceil(screenHeight * 0.15555), (int) Math.ceil(screenWidth * 0.01302),
				(int) Math.ceil(screenWidth * 0.01302));
		genderCheckBox1.setHorizontalAlignment(JLabel.CENTER);
		genderCheckBox1.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(0)));
		genderCheckBox1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				checkBoxGenders[0]++;
				if (checkBoxGenders[0] == 2) {
					checkBoxGenders[0] = 0;
				}
				genderCheckBox1.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxGenders[0])));
				checkBoxGenders[1] = 0;
				checkBoxGenders[2] = 0;
				genderCheckBox2.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxGenders[1])));
				genderCheckBox3.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxGenders[2])));
			}
		});

		JLabel genderText1 = new JLabel("Male");
		genderText1.setForeground(Color.BLACK);
		genderText1.setHorizontalAlignment(JLabel.LEFT);
		genderText1.setVerticalAlignment(JLabel.BOTTOM);
		genderText1.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20 * (screenWidth / 1920))));
		genderText1.setBounds((int) Math.ceil(screenWidth * 0.42760), (int) Math.ceil(screenHeight * 0.15805),(int) Math.ceil(screenWidth * 0.02447), (int) Math.ceil(screenHeight * 0.02314));

		genderCheckBox2.setBounds((int) Math.ceil(screenWidth * 0.46718), (int) Math.ceil(screenHeight * 0.15555), (int) Math.ceil(screenWidth * 0.01302),(int) Math.ceil(screenWidth * 0.01302));
		genderCheckBox2.setHorizontalAlignment(JLabel.CENTER);
		genderCheckBox2.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(0)));
		genderCheckBox2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				checkBoxGenders[1]++;
				if (checkBoxGenders[1] == 2) {
					checkBoxGenders[1] = 0;
				}
				genderCheckBox2.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxGenders[1])));
				checkBoxGenders[0] = 0;
				checkBoxGenders[2] = 0;
				genderCheckBox1.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxGenders[0])));
				genderCheckBox3.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxGenders[2])));
			}
		});

		JLabel genderText2 = new JLabel("Female");
		genderText2.setForeground(Color.BLACK);
		genderText2.setHorizontalAlignment(JLabel.LEFT);
		genderText2.setVerticalAlignment(JLabel.BOTTOM);
		genderText2.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20 * (screenWidth / 1920))));
		genderText2.setBounds((int) Math.ceil(screenWidth * 0.48333), (int) Math.ceil(screenHeight * 0.15805),(int) Math.ceil(screenWidth * 0.03333), (int) Math.ceil(screenHeight * 0.02314));

		genderCheckBox3.setBounds((int) Math.ceil(screenWidth * 0.53281), (int) Math.ceil(screenHeight * 0.15555), (int) Math.ceil(screenWidth * 0.01302),
				(int) Math.ceil(screenWidth * 0.01302));
		genderCheckBox3.setHorizontalAlignment(JLabel.CENTER);
		genderCheckBox3.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(0)));
		genderCheckBox3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				checkBoxGenders[2]++;
				if (checkBoxGenders[2] == 2) {
					checkBoxGenders[2] = 0;
				}
				genderCheckBox3.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxGenders[2])));
				checkBoxGenders[0] = 0;
				checkBoxGenders[1] = 0;
				genderCheckBox1.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxGenders[0])));
				genderCheckBox2.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxGenders[1])));
			}
		});
		
		JLabel genderText3 = new JLabel("Unisex");
		genderText3.setForeground(Color.BLACK);
		genderText3.setHorizontalAlignment(JLabel.LEFT);
		genderText3.setVerticalAlignment(JLabel.BOTTOM);
		genderText3.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20 * (screenWidth / 1920))));
		genderText3.setBounds((int) Math.ceil(screenWidth * 0.55416), (int) Math.ceil(screenHeight * 0.15805),(int) Math.ceil(screenWidth * 0.03333), (int) Math.ceil(screenHeight * 0.02314));
		
		JLabel gearText = new JLabel("Gear Type:");
		gearText.setForeground(Color.BLACK);
		gearText.setHorizontalAlignment(JLabel.LEFT);
		gearText.setVerticalAlignment(JLabel.BOTTOM);
		gearText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(19 * (screenWidth / 1920))));
		gearText.setBounds((int) Math.ceil(screenWidth * 0.36510), (int) Math.ceil(screenHeight * 0.18796),(int) Math.ceil(screenWidth * 0.06510), (int) Math.ceil(screenHeight * 0.02314));
		
		JLabel externalGearCheckBox = new JLabel();
		JLabel internalGearCheckBox = new JLabel();
		
		JLabel externalGearText = new JLabel("External Gear");
		externalGearText.setForeground(Color.BLACK);
		externalGearText.setHorizontalAlignment(JLabel.LEFT);
		externalGearText.setVerticalAlignment(JLabel.BOTTOM);
		externalGearText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20 * (screenWidth / 1920))));
		externalGearText.setBounds((int) Math.ceil(screenWidth * 0.42708), (int) Math.ceil(screenHeight * 0.19006),(int) Math.ceil(screenWidth * 0.06406), (int) Math.ceil(screenHeight * 0.02314));

		externalGearCheckBox.setBounds((int) Math.ceil(screenWidth * 0.41093), (int) Math.ceil(screenHeight * 0.18796), (int) Math.ceil(screenWidth * 0.01302),
				(int) Math.ceil(screenWidth * 0.01302));
		externalGearCheckBox.setHorizontalAlignment(JLabel.CENTER);
		externalGearCheckBox.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(0)));
		externalGearCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				checkBoxGears[0]++;
				if (checkBoxGears[0] == 2) {
					checkBoxGears[0] = 0;
				}
				externalGearCheckBox.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxGears[0])));
				checkBoxGears[1] = 0;
				internalGearCheckBox.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxGears[1])));
			}
		});
		
		JLabel internalGearText = new JLabel("Internal Gear");
		internalGearText.setForeground(Color.BLACK);
		internalGearText.setHorizontalAlignment(JLabel.LEFT);
		internalGearText.setVerticalAlignment(JLabel.BOTTOM);
		internalGearText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20 * (screenWidth / 1920))));
		internalGearText.setBounds((int) Math.ceil(screenWidth * 0.52708), (int) Math.ceil(screenHeight * 0.19006),(int) Math.ceil(screenWidth * 0.06406), (int) Math.ceil(screenHeight * 0.02314));

		internalGearCheckBox.setBounds((int) Math.ceil(screenWidth * 0.51093), (int) Math.ceil(screenHeight * 0.18796), (int) Math.ceil(screenWidth * 0.01302), (int) Math.ceil(screenWidth * 0.01302));
		internalGearCheckBox.setHorizontalAlignment(JLabel.CENTER);
		internalGearCheckBox.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(0)));
		internalGearCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				checkBoxGears[1]++;
				if (checkBoxGears[1] == 2) {
					checkBoxGears[1] = 0;
				}
				internalGearCheckBox.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxGears[1])));
				checkBoxGears[0] = 0;
				externalGearCheckBox.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxGears[0])));
			}
		});
		
		

		JButton registerBtn = new JButton("REGISTER");
		registerBtn.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(16 * (screenWidth / 1920))));
		registerBtn.setHorizontalAlignment(JButton.CENTER);
		registerBtn.setBounds((int) Math.ceil(screenWidth * 0.43854), (int) Math.ceil(screenHeight * 0.23888), (int) Math.ceil(screenWidth * 0.07031), (int) Math.ceil(screenHeight * 0.02314));
		registerBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				boolean filled = true;
				
				if(bikeNameInput.getText().equals("")) {filled = false;}
				else if(serialNumberInput.getText().equals("")) {filled = false;}
				else if(checkBoxGears[0]==0 && checkBoxGears[1]==0) {filled = false;}
				else if(checkBoxGenders[0]==0 && checkBoxGenders[1]==0 && checkBoxGenders[2]==0) {filled = false;}
			
				
				if(filled==true) {
					startLoadingAnimation(frame,contentPanel);
					Thread t1 = new Thread(new Runnable()
						{
							public void run() {
								String gender="";
								for(int i = 0; i<3; i++) {
									if(checkBoxGenders[i]==1) {if(i==0) {gender="M";} if(i==1) {gender ="F";} if(i==2) {gender = "U";}}
								}
									boolean isExternalGear=false;
									if(checkBoxGears[0]==1) {isExternalGear=true;}
								Bike bike = new Bike(serialNumberInput.getText(), gender, bikeNameInput.getText(), isExternalGear);
								try{bikeCtr.registerBike(bike); 
								UpdateBikeUI updateBikeUI = new UpdateBikeUI(frame, contentPanel, screenWidth, screenHeight, bike);}
								catch(DataAccessException a) {a.printStackTrace();}
							}
					});					
					t1.start();
				}
				else {
				errorMessage(frame, contentPanel, "ERROR: ALL FIELDS MUST BE FILLED!");
				}
				
			}
		});
		
		
		if(!wasLoadingInterrupted()) {
			contentPanel.add(titleText);
			contentPanel.add(serialNumberText);
			contentPanel.add(serialNumberInput);
			contentPanel.add(bikeNameText);
			contentPanel.add(bikeNameInput);
			contentPanel.add(genderText);
			contentPanel.add(genderCheckBox1);
			contentPanel.add(genderText1);
			contentPanel.add(genderCheckBox2);
			contentPanel.add(genderText2);
			contentPanel.add(genderCheckBox3);
			contentPanel.add(genderText3);
			contentPanel.add(externalGearText);
			contentPanel.add(externalGearCheckBox);
			contentPanel.add(internalGearText);
			contentPanel.add(internalGearCheckBox);
			contentPanel.add(registerBtn);
			contentPanel.add(gearText);
		}
	}

}
