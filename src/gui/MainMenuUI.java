/*Code by Milek Radoslaw 2020.05*/

package gui;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenuUI extends GuiTools{
	
	double screenWidth, screenHeight;
	JFrame frame;
	JPanel contentPanel;
	ImageGen exitBtnImage;
	
	public MainMenuUI(JFrame frame, JPanel contentPanel, double screenWidth, double screenHeight) {
		this.frame = frame;
		this.contentPanel = contentPanel;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		//Initialazing frame in new thread so the loadingAnimation would play during loading
		Thread t1 = new Thread(new Runnable()
				{
				public void run() {
					
					contentPanel.removeAll();
					initializeImages();
					initialize();
					
					frame.repaint();
				}
				});
		t1.start();
	}
	
	
	private void initializeImages() {
	}
	
	private void initialize() {
		contentPanel.removeAll();

		Random r = new Random();
			int random = r.nextInt(9);
			JLabel label = new JLabel();
			switch(random) {
			case 0: label.setText("Welcome Back"); break;
			case 1: label.setText("Let's get to Work!"); break;
			case 2: label.setText("The Chains Will Fall"); break;
			case 3: label.setText("Keep Moving Forward"); break;
			case 4: label.setText("A Bike is Like a Box of Chocolates"); break;
			case 5: label.setText("Carpe diem"); break;
			case 6: label.setText("I'll be Back"); break;
			case 7: label.setText("I am your Father"); break;
			case 8: label.setText("Just Keep Swimming"); break;
			}
			label.setForeground(Color.BLACK);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setVerticalAlignment(JLabel.TOP);
			label.setFont(new Font("Times New Roman", Font.PLAIN, (int) Math.round(70 * (screenWidth / 1920))));
			label.setBounds(0, (int) Math.ceil(screenHeight*0.15), (int) Math.ceil(screenWidth*0.94739), (int) Math.ceil(screenHeight*0.5));
			contentPanel.add(label);
		
	}
}
