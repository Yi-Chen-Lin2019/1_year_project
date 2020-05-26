/*Code by Milek Radoslaw 2020.05*/

package gui;

import model.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.BikeCtr;
import db.DataAccessException;

public class RegisterBikeUI {
	
	double screenWidth, screenHeight;
	JFrame frame;
	JPanel contentPanel;
	ImageGen checkBoxImage;
	
	int[] checkBox1Option = {0, 0, 0};
	ArrayList<Integer> checkBoxRepairItems;
	BikeCtr bikeCtr;
	

	
	public RegisterBikeUI(JFrame frame, JPanel contentPanel, double screenWidth, double screenHeight) throws DataAccessException {
		this.frame = frame;
		this.contentPanel = contentPanel;
		contentPanel.removeAll();
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		bikeCtr = new BikeCtr();
		
		initializeImages();
		initialize();
		frame.repaint();
	}
	
	
	private void initializeImages() {
		checkBoxImage = new ImageGen(4, 1, "res/checkBox.png", (int) Math.ceil(screenWidth*0.01302), (int) Math.ceil(screenWidth*0.01302));
	}
	
	private void initialize() {
		//bike properties
		JLabel bikeNameText = new JLabel("Bike Name:");
		bikeNameText.setForeground(Color.BLACK);
		bikeNameText.setHorizontalAlignment(JLabel.LEFT);
		bikeNameText.setVerticalAlignment(JLabel.BOTTOM);
		bikeNameText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20*(screenWidth/1920))));
		bikeNameText.setBounds(0, (int) Math.ceil(screenHeight*0.00185), (int) Math.ceil(screenWidth*0.0485), (int) Math.ceil(screenHeight*0.02314));
		contentPanel.add(bikeNameText);
		
		JTextField bikeNameInput = new JTextField();
		bikeNameInput.setForeground(Color.BLACK);
		bikeNameInput.setHorizontalAlignment(SwingConstants.LEFT);
		bikeNameInput.setFont(new Font("Tahoma", Font.PLAIN, (int) Math.round(18*(screenWidth/1920))));
		bikeNameInput.setBounds((int) Math.ceil(screenWidth*0.05312), 0, (int) Math.ceil(screenWidth*0.15625), (int) Math.ceil(screenHeight*0.02314));
		contentPanel.add(bikeNameInput);
		
		
		JLabel serialNumberText = new JLabel("Serial Number:");
		serialNumberText.setForeground(Color.BLACK);
		serialNumberText.setHorizontalAlignment(JLabel.LEFT);
		serialNumberText.setVerticalAlignment(JLabel.BOTTOM);
		serialNumberText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20*(screenWidth/1920))));
		serialNumberText.setBounds((int) Math.ceil(screenWidth*0.26145), (int) Math.ceil(screenHeight*0.00185), (int) Math.ceil(screenWidth*0.06510), (int) Math.ceil(screenHeight*0.02314));
		contentPanel.add(serialNumberText);
		
		JTextField serialNumberInput = new JTextField();
		serialNumberInput.setForeground(Color.BLACK);
		serialNumberInput.setHorizontalAlignment(JLabel.LEFT);
		serialNumberInput.setFont(new Font("Tahoma", Font.PLAIN, (int) Math.round(18*(screenWidth/1920))));
		serialNumberInput.setBounds((int) Math.ceil(screenWidth*0.33177), 0, (int) Math.ceil(screenWidth*0.15625), (int) Math.ceil(screenHeight*0.02314));
		contentPanel.add(serialNumberInput);
		
		
		JLabel genderText = new JLabel("Gender:");
		genderText.setForeground(Color.BLACK);
		genderText.setHorizontalAlignment(JLabel.LEFT);
		genderText.setVerticalAlignment(JLabel.BOTTOM);
		genderText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20*(screenWidth/1920))));
		genderText.setBounds((int) Math.ceil(screenWidth*0.54010), (int) Math.ceil(screenHeight*0.00185), (int) Math.ceil(screenWidth*0.03541), (int) Math.ceil(screenHeight*0.02314));
		contentPanel.add(genderText);
		
		JLabel genderCheckBox1 = new JLabel();
		JLabel genderCheckBox2 = new JLabel();
		JLabel genderCheckBox3 = new JLabel();
		genderCheckBox1.setBounds((int) Math.ceil(screenWidth*0.58593), 0, (int) Math.ceil(screenWidth*0.01302), (int) Math.ceil(screenWidth*0.01302));
		genderCheckBox1.setHorizontalAlignment(JLabel.CENTER);
		genderCheckBox1.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(0)));
		genderCheckBox1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				checkBox1Option[0]++;
				if(checkBox1Option[0]==2) {checkBox1Option[0]=0;}
				genderCheckBox1.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBox1Option[0])));
				checkBox1Option[1] = 0;
				checkBox1Option[2] = 0;
				genderCheckBox2.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBox1Option[1])));
				genderCheckBox3.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBox1Option[2])));
			}
		});
		contentPanel.add(genderCheckBox1);
		
		JLabel genderText1 = new JLabel("Male");
		genderText1.setForeground(Color.BLACK);
		genderText1.setHorizontalAlignment(JLabel.LEFT);
		genderText1.setVerticalAlignment(JLabel.BOTTOM);
		genderText1.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20*(screenWidth/1920))));
		genderText1.setBounds((int) Math.ceil(screenWidth*0.60208), (int) Math.ceil(screenHeight*0.00185), (int) Math.ceil(screenWidth*0.02447), (int) Math.ceil(screenHeight*0.02314));
		contentPanel.add(genderText1);
		
		genderCheckBox2.setBounds((int) Math.ceil(screenWidth*0.64218), 0, (int) Math.ceil(screenWidth*0.01302), (int) Math.ceil(screenWidth*0.01302));
		genderCheckBox2.setHorizontalAlignment(JLabel.CENTER);
		genderCheckBox2.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(0)));
		genderCheckBox2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				checkBox1Option[1]++;
				if(checkBox1Option[1]==2) {checkBox1Option[1] = 0;}
				genderCheckBox2.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBox1Option[1])));
				checkBox1Option[0] = 0;
				checkBox1Option[2] = 0;
				genderCheckBox1.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBox1Option[0])));
				genderCheckBox3.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBox1Option[2])));
			}
		});
		contentPanel.add(genderCheckBox2);
		
		JLabel genderText2 = new JLabel("Female");
		genderText2.setForeground(Color.BLACK);
		genderText2.setHorizontalAlignment(JLabel.LEFT);
		genderText2.setVerticalAlignment(JLabel.BOTTOM);
		genderText2.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20*(screenWidth/1920))));
		genderText2.setBounds((int) Math.ceil(screenWidth*0.65781), (int) Math.ceil(screenHeight*0.00185), (int) Math.ceil(screenWidth*0.03333), (int) Math.ceil(screenHeight*0.02314));
		contentPanel.add(genderText2);
		
		genderCheckBox3.setBounds((int) Math.ceil(screenWidth*0.70781), 0, (int) Math.ceil(screenWidth*0.01302), (int) Math.ceil(screenWidth*0.01302));
		genderCheckBox3.setHorizontalAlignment(JLabel.CENTER);
		genderCheckBox3.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(0)));
		genderCheckBox3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				checkBox1Option[2]++;
				if(checkBox1Option[2]==2) {checkBox1Option[2] = 0;}
				genderCheckBox3.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBox1Option[2])));
				checkBox1Option[0] = 0;
				checkBox1Option[1] = 0;
				genderCheckBox1.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBox1Option[0])));
				genderCheckBox2.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBox1Option[1])));
			}
		});
		contentPanel.add(genderCheckBox3);
		
		JLabel genderText3 = new JLabel("Unisex");
		genderText3.setForeground(new Color(64, 64, 64, 255));
		genderText3.setHorizontalAlignment(JLabel.LEFT);
		genderText3.setVerticalAlignment(JLabel.BOTTOM);
		genderText3.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20*(screenWidth/1920))));
		genderText3.setBounds((int) Math.ceil(screenWidth*0.72395), (int) Math.ceil(screenHeight*0.00185), (int) Math.ceil(screenWidth*0.03333), (int) Math.ceil(screenHeight*0.02314));
		contentPanel.add(genderText3);
		
		//Save and Cancel Button
				JLabel acceptBtn = new JLabel("Save");	
				acceptBtn.setForeground(Color.WHITE);
				acceptBtn.setBackground(new Color(64, 64, 64, 255));
				acceptBtn.setOpaque(true);
				acceptBtn.setHorizontalAlignment(JLabel.CENTER);
				acceptBtn.setVerticalAlignment(JLabel.CENTER);
				acceptBtn.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(30*(screenWidth/1920))));
				acceptBtn.setBounds((int) Math.ceil(screenWidth*0.08125), (int) Math.ceil(screenHeight*0.68796), (int) Math.ceil(screenWidth*0.10416), (int) Math.ceil(screenHeight*0.05555));
				acceptBtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						acceptBtn.setBackground(new Color(107,107,107,255));
					}
				});
				acceptBtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseExited(MouseEvent e) {
						acceptBtn.setBackground(new Color(64,64,64,255));
					}
				
				
				});
				
				//TODO: gearType checkbox, getGender, navigate
				acceptBtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						String serialNumber = serialNumberInput.getText();
						String gender = getGender();
						//not added
						boolean isExternalGear = false;
						Bike bike = new Bike(serialNumber, gender, bikeNameInput.getText(), LocalDateTime.now(), isExternalGear);
						
						try {
							bikeCtr.registerBike(bike);
						} catch (DataAccessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						//navigate somewhere
						//
					}

					private String getGender() {
						String gender = "";
						//logic to get the gender
						
						return gender;
					}
				});
				
				contentPanel.add(acceptBtn);
				
				JLabel cancelBtn = new JLabel("Cancel");	
				cancelBtn.setForeground(Color.WHITE);
				cancelBtn.setBackground(new Color(64, 64, 64, 255));
				cancelBtn.setOpaque(true);
				cancelBtn.setHorizontalAlignment(JLabel.CENTER);
				cancelBtn.setVerticalAlignment(JLabel.CENTER);
				cancelBtn.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(30*(screenWidth/1920))));
				cancelBtn.setBounds((int) Math.ceil(screenWidth*0.20104), (int) Math.ceil(screenHeight*0.68796), (int) Math.ceil(screenWidth*0.10416), (int) Math.ceil(screenHeight*0.05555));
				cancelBtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						cancelBtn.setBackground(new Color(107,107,107,255));
					}
				});
				cancelBtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseExited(MouseEvent e) {
						cancelBtn.setBackground(new Color(64,64,64,255));
					}
				});
				acceptBtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						//navigate somewhere
						//
					}

				});
				contentPanel.add(cancelBtn);
	}
	
}
