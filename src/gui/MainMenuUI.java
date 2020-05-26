/*Code by Milek Radoslaw 2020.05*/

package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenuUI {
	
	double screenWidth, screenHeight;
	JFrame frame;
	JPanel contentPanel;
	ImageGen exitBtnImage;
	
	public MainMenuUI(JFrame frame, JPanel contentPanel, double screenWidth, double screenHeight) {
		this.frame = frame;
		this.contentPanel = contentPanel;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		initializeImages();
		initialize();
	}
	
	
	private void initializeImages() {
		exitBtnImage = new ImageGen(2, 4, "/exitBtn.png", (int) Math.ceil(screenWidth*0.02864), (int) Math.ceil(screenHeight*0.02777));
	}
	
	private void initialize() {
		contentPanel.removeAll();
		
		JLabel exitBtn = new JLabel();
		exitBtn.setBounds(0, 0, (int) Math.ceil(screenWidth*0.02864), (int) Math.ceil(screenHeight*0.02777));
		exitBtn.setIcon(new ImageIcon(exitBtnImage.getFrameArray().get(0)));
		AnimationPlayer exit1 = new AnimationPlayer(exitBtnImage, exitBtn, 15);
		exit1.start();
		exitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exit1.setMode(1);
				exit1.startRunning();
			}
		});
		exitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
//				exit1.setMode(2);
//				exit1.startRunning();
			}
		});
		contentPanel.add(exitBtn);
		
		frame.repaint();
	}
}
