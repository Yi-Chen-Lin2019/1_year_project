/*Code by Milek Radoslaw 2020.05*/

package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.BikeCtr;
import db.DataAccessException;
import model.Bike;

public class RegisterBikeUI {
	
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
		contentPanel.removeAll();
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		checkBoxImage = new ImageGen(4, 1, "/checkBox.png", (int) Math.ceil(screenWidth * 0.01302),(int) Math.ceil(screenWidth * 0.01302));
		
		try {bikeCtr = new BikeCtr();}
		catch(DataAccessException e) {}
		
		
		initialize();
		frame.repaint();
	}
	
	private void initialize() {
		JLabel titleText = new JLabel("Register a Bike");
		titleText.setForeground(Color.BLACK);
		titleText.setHorizontalAlignment(JLabel.LEFT);
		titleText.setVerticalAlignment(JLabel.BOTTOM);
		titleText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(30 * (screenWidth / 1920))));
		titleText.setBounds((int) Math.ceil(screenWidth * 0.42083), (int) Math.ceil(screenHeight * 0.02870), (int) Math.ceil(screenWidth * 0.10625), (int) Math.ceil(screenHeight * 0.03425));
		contentPanel.add(titleText);
		
		JLabel serialNumberText = new JLabel("Serial Number:");
		serialNumberText.setForeground(Color.BLACK);
		serialNumberText.setHorizontalAlignment(JLabel.LEFT);
		serialNumberText.setVerticalAlignment(JLabel.BOTTOM);
		serialNumberText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20 * (screenWidth / 1920))));
		serialNumberText.setBounds((int) Math.ceil(screenWidth * 0.36041), (int) Math.ceil(screenHeight * 0.09074), (int) Math.ceil(screenWidth * 0.06510), (int) Math.ceil(screenHeight * 0.02314));
		contentPanel.add(serialNumberText);
		
		JTextField serialNumberInput = new JTextField();
		serialNumberInput.setForeground(Color.BLACK);
		serialNumberInput.setHorizontalAlignment(JLabel.LEFT);
		serialNumberInput.setFont(new Font("Tahoma", Font.PLAIN, (int) Math.round(18 * (screenWidth / 1920))));
		serialNumberInput.setBounds((int) Math.ceil(screenWidth * 0.45416), (int) Math.ceil(screenHeight * 0.09074), (int) Math.ceil(screenWidth * 0.15625), (int) Math.ceil(screenHeight * 0.02314));
		contentPanel.add(serialNumberInput);
		
		JLabel bikeNameText = new JLabel("Bike Name:");
		bikeNameText.setForeground(Color.BLACK);
		bikeNameText.setHorizontalAlignment(JLabel.LEFT);
		bikeNameText.setVerticalAlignment(JLabel.BOTTOM);
		bikeNameText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20 * (screenWidth / 1920))));
		bikeNameText.setBounds((int) Math.ceil(screenWidth * 0.37760), (int) Math.ceil(screenHeight * 0.12414), (int) Math.ceil(screenWidth * 0.0485),(int) Math.ceil(screenHeight * 0.02314));
		contentPanel.add(bikeNameText);

		JTextField bikeNameInput = new JTextField();
		bikeNameInput.setForeground(Color.BLACK);
		bikeNameInput.setHorizontalAlignment(SwingConstants.LEFT);
		bikeNameInput.setFont(new Font("Tahoma", Font.PLAIN, (int) Math.round(18 * (screenWidth / 1920))));
		bikeNameInput.setBounds((int) Math.ceil(screenWidth * 0.45416), (int) Math.ceil(screenHeight * 0.12314), (int) Math.ceil(screenWidth * 0.15625), (int) Math.ceil(screenHeight * 0.02314));
		contentPanel.add(bikeNameInput);

		JLabel genderText = new JLabel("Gender:");
		genderText.setForeground(Color.BLACK);
		genderText.setHorizontalAlignment(JLabel.LEFT);
		genderText.setVerticalAlignment(JLabel.BOTTOM);
		genderText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20 * (screenWidth / 1920))));
		genderText.setBounds((int) Math.ceil(screenWidth * 0.36510), (int) Math.ceil(screenHeight * 0.15805),(int) Math.ceil(screenWidth * 0.03541), (int) Math.ceil(screenHeight * 0.02314));
		contentPanel.add(genderText);

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
		contentPanel.add(genderCheckBox1);

		JLabel genderText1 = new JLabel("Male");
		genderText1.setForeground(Color.BLACK);
		genderText1.setHorizontalAlignment(JLabel.LEFT);
		genderText1.setVerticalAlignment(JLabel.BOTTOM);
		genderText1.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20 * (screenWidth / 1920))));
		genderText1.setBounds((int) Math.ceil(screenWidth * 0.42760), (int) Math.ceil(screenHeight * 0.15805),(int) Math.ceil(screenWidth * 0.02447), (int) Math.ceil(screenHeight * 0.02314));
		contentPanel.add(genderText1);

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
		contentPanel.add(genderCheckBox2);

		JLabel genderText2 = new JLabel("Female");
		genderText2.setForeground(Color.BLACK);
		genderText2.setHorizontalAlignment(JLabel.LEFT);
		genderText2.setVerticalAlignment(JLabel.BOTTOM);
		genderText2.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20 * (screenWidth / 1920))));
		genderText2.setBounds((int) Math.ceil(screenWidth * 0.48333), (int) Math.ceil(screenHeight * 0.15805),(int) Math.ceil(screenWidth * 0.03333), (int) Math.ceil(screenHeight * 0.02314));
		contentPanel.add(genderText2);

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
		contentPanel.add(genderCheckBox3);
		
		JLabel genderText3 = new JLabel("Unisex");
		genderText3.setForeground(Color.BLACK);
		genderText3.setHorizontalAlignment(JLabel.LEFT);
		genderText3.setVerticalAlignment(JLabel.BOTTOM);
		genderText3.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20 * (screenWidth / 1920))));
		genderText3.setBounds((int) Math.ceil(screenWidth * 0.55416), (int) Math.ceil(screenHeight * 0.15805),(int) Math.ceil(screenWidth * 0.03333), (int) Math.ceil(screenHeight * 0.02314));
		contentPanel.add(genderText3);
		
		
		JLabel externalGearCheckBox = new JLabel();
		JLabel internalGearCheckBox = new JLabel();
		
		JLabel externalGearText = new JLabel("External Gear");
		externalGearText.setForeground(Color.BLACK);
		externalGearText.setHorizontalAlignment(JLabel.LEFT);
		externalGearText.setVerticalAlignment(JLabel.BOTTOM);
		externalGearText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20 * (screenWidth / 1920))));
		externalGearText.setBounds((int) Math.ceil(screenWidth * 0.42708), (int) Math.ceil(screenHeight * 0.19006),(int) Math.ceil(screenWidth * 0.06406), (int) Math.ceil(screenHeight * 0.02314));
		contentPanel.add(externalGearText);

		externalGearCheckBox.setBounds((int) Math.ceil(screenWidth * 0.41093), (int) Math.ceil(screenHeight * 0.18796), (int) Math.ceil(screenWidth * 0.01302),
				(int) Math.ceil(screenWidth * 0.01302));
		externalGearCheckBox.setHorizontalAlignment(JLabel.CENTER);
		externalGearCheckBox.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(0)));
		externalGearCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				checkBoxGenders[0]++;
				if (checkBoxGenders[0] == 2) {
					checkBoxGenders[0] = 0;
				}
				externalGearCheckBox.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxGenders[0])));
				checkBoxGenders[1] = 0;
				internalGearCheckBox.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxGenders[1])));
			}
		});
		contentPanel.add(externalGearCheckBox);
		
		JLabel internalGearText = new JLabel("Internal Gear");
		internalGearText.setForeground(Color.BLACK);
		internalGearText.setHorizontalAlignment(JLabel.LEFT);
		internalGearText.setVerticalAlignment(JLabel.BOTTOM);
		internalGearText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20 * (screenWidth / 1920))));
		internalGearText.setBounds((int) Math.ceil(screenWidth * 0.52708), (int) Math.ceil(screenHeight * 0.19006),(int) Math.ceil(screenWidth * 0.06406), (int) Math.ceil(screenHeight * 0.02314));
		contentPanel.add(internalGearText);

		internalGearCheckBox.setBounds((int) Math.ceil(screenWidth * 0.51093), (int) Math.ceil(screenHeight * 0.18796), (int) Math.ceil(screenWidth * 0.01302), (int) Math.ceil(screenWidth * 0.01302));
		internalGearCheckBox.setHorizontalAlignment(JLabel.CENTER);
		internalGearCheckBox.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(0)));
		internalGearCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				checkBoxGenders[1]++;
				if (checkBoxGenders[1] == 2) {
					checkBoxGenders[1] = 0;
				}
				internalGearCheckBox.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxGenders[1])));
				checkBoxGenders[0] = 0;
				externalGearCheckBox.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxGenders[0])));
			}
		});
		contentPanel.add(internalGearCheckBox);
		

		JButton registerBtn = new JButton("REGISTER");
		registerBtn.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(16 * (screenWidth / 1920))));
		registerBtn.setHorizontalAlignment(JButton.CENTER);
		registerBtn.setBounds((int) Math.ceil(screenWidth * 0.43854), (int) Math.ceil(screenHeight * 0.23888), (int) Math.ceil(screenWidth * 0.07031), (int) Math.ceil(screenHeight * 0.02314));
		registerBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String gender="";
				for(int i = 0; i<3; i++) {
					if(checkBoxGenders[i]==1) {if(i==0) {gender="M";} if(i==1) {gender ="F";} if(i==2) {gender = "U";}}
				}
					boolean isExternalGear=false;
					if(checkBoxGears[0]==1) {isExternalGear=true;}
				Bike bike = new Bike(serialNumberInput.getText(), gender, bikeNameInput.getText(), isExternalGear);
				try{bikeCtr.registerBike(bike); 
				System.out.println("Bike Registered");
				UpdateBikeUI updateBikeUI = new UpdateBikeUI(frame, contentPanel, screenWidth, screenHeight, bike);}
				catch(DataAccessException a) {a.printStackTrace();
					System.out.println("The Bike wasn't registered");};
				
			}
		});
		contentPanel.add(registerBtn);
	}
	
}