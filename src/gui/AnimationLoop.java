package gui;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AnimationLoop extends Thread{
	
	private volatile boolean running = false;
	private ImageGen image;
	private JLabel label;
	private int animationSpeed;
	
	public AnimationLoop(ImageGen image, JLabel label, int animationSpeed) {
		this.image = image;
		this.label = label;
		this.animationSpeed = animationSpeed;
	}
	
	public void startRunning() {running=true;}
	public void stopRunning() {running=false;}
	
	public void run() {
		while(true) {
			while(running) {
				for(BufferedImage i : image.getAnimation()) {
					if(running) {
			        	label.setIcon(new ImageIcon(i));
			        	try {Thread.sleep(animationSpeed);} 
			        	catch (InterruptedException e) {}
					}
		    	}
			}
		}
	}
	
}
