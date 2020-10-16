/**
 * @author Radoslaw Milek
 * @since 2020-05
 */

package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class GuiTools {
	private static JLabel loadingAnimation = null;
	
	protected void startLoadingAnimation(JFrame frame, JPanel contentPanel) {
		if(loadingAnimation==null) {
		contentPanel.setVisible(false);
		double screenWidth = contentPanel.getParent().getWidth(), screenHeight = contentPanel.getParent().getHeight();
		ImageGen loadingImage = new ImageGen(11, 1, this.getClass().getResource("/loadingImg.png"), (int) Math.ceil(screenWidth*0.13020), (int) Math.ceil(screenWidth*0.13020));
		
		loadingAnimation = new JLabel();
		loadingAnimation.setHorizontalAlignment(JLabel.CENTER);
		loadingAnimation.setVerticalAlignment(JLabel.CENTER);
		loadingAnimation.setBounds((int) Math.ceil(screenWidth*0.43541), (int) Math.ceil(screenHeight*0.38425), (int) Math.ceil(screenWidth*0.13020), (int) Math.ceil(screenWidth*0.13020));
		loadingAnimation.setIcon(new ImageIcon(loadingImage.getFrameArray().get(0)));
		contentPanel.getParent().add(loadingAnimation);
		frame.repaint();
		AnimationPlayer ap = new AnimationPlayer(loadingImage, loadingAnimation, 50);
		ap.start();
		ap.startRunning();
		}
		
	}
	protected boolean wasLoadingInterrupted() {
		if(loadingAnimation==null) {return true;}
		return false;
	}
	
	protected void stopLoadingAnimation(JFrame frame, JPanel contentPanel) {
		if(loadingAnimation != null) {
			contentPanel.getParent().remove(loadingAnimation);
			contentPanel.setVisible(true);
			loadingAnimation=null;
		}
	}
	
	
	protected void errorMessage(JFrame frame, JPanel contentPanel, String message) {
		double screenWidth = contentPanel.getParent().getWidth(), screenHeight = contentPanel.getParent().getHeight();
		JPanel errorMessage = new JPanel();			
		errorMessage.setBackground(Color.LIGHT_GRAY);
		errorMessage.setOpaque(true);
		errorMessage.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(30 * (screenWidth / 1920))));
		errorMessage.setBounds((int) Math.ceil(screenWidth*0.30729), (int) Math.ceil(screenHeight*0.10833), (int) Math.ceil(screenWidth*0.33489), (int) Math.ceil(screenHeight*0.36388));
		Container panel = (Container) contentPanel.getParent().getComponents()[0];
		errorMessage.setLayout(null);
		panel.add(errorMessage);
		
		
		JLabel text = new JLabel(message);
		text.setForeground(Color.BLACK);
		text.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(30 * (screenWidth / 1920))));
		text.setBounds((int) Math.ceil(screenWidth*0.01562), (int) Math.ceil(screenHeight*0.14351), (int) Math.ceil(screenWidth*0.30364), (int) Math.ceil(screenHeight*0.09259));
		errorMessage.add(text);
		
		JButton button = new JButton("OK");
		button.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(16 * (screenWidth / 1920))));
		button.setHorizontalAlignment(JButton.CENTER);
		button.setBounds((int) Math.ceil(screenWidth * 0.13906), (int) Math.ceil(screenHeight * 0.30648), (int) Math.ceil(screenWidth * 0.05520), (int) Math.ceil(screenHeight * 0.03148));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				panel.removeAll();
	            panel.setVisible(false);
	            contentPanel.setVisible(true);
	            frame.repaint();
			}
		});
		errorMessage.add(button);
		
		contentPanel.setVisible(false);
		panel.setVisible(true);
		panel.add(errorMessage);
		frame.repaint();
	}
	
}
