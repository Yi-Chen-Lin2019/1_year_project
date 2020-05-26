/*Code by Milek Radoslaw 2020.05*/

package gui;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class AnimationPlayer extends Thread{
	
	private volatile boolean running = false;
	private boolean interrupt = false;
	private boolean enableStart = true;
	private ImageGen image;
	private JLabel label;
	private int animationSpeed;
	private int mode;
	private int currentFrame;
	
	public AnimationPlayer(ImageGen image, JLabel label, int animationSpeed) {
		this.image = image;
		this.label = label;
		this.animationSpeed = animationSpeed;
		
		mode = 0;
		currentFrame = 0;
	}
	
	public void startRunning() {interrupt=true; boolean started = false; while(!started) {
		try {Thread.sleep(2);}
		catch (InterruptedException e) {}
		if(enableStart) {started = true; running=true; enableStart=false;}}
	}
	
	public void stopRunning() {running = false; if(mode==0) {interrupt=true;}
	}
	
	public void setMode(int mode) {this.mode = mode;}
	
	public void setCurrentFrame(int frame) {currentFrame = frame;}
	
	public void run() {
		while(true) {
			//resource save delay
			try {Thread.sleep(20);}
			catch (InterruptedException e) {}
			
			if(mode==0 && interrupt) {interrupt = false; enableStart = true;}
			while(running) {
				
				//mode 0 - loop      mode 1 - play forwards and stop     mode 2 - play backwards and stop
				switch(mode) {
				
					case 0:
						for(BufferedImage i : image.getFrameArray()) {
							if(interrupt) {interrupt = false; enableStart = true; break;}
					        	label.setIcon(new ImageIcon(i));
					        	try {Thread.sleep(animationSpeed-20);}
					        	catch (InterruptedException e) {}
				    	}
						break;
				
					case 1:
						interrupt = false;
						while(currentFrame < image.getFrameArray().size()-1) {
							if(interrupt) {interrupt = false; enableStart = true; break;}
							currentFrame++;
							BufferedImage i = image.getFrameArray().get(currentFrame);
							label.setIcon(new ImageIcon(i));
							try {Thread.sleep(animationSpeed);} 
							catch (InterruptedException e) {}
				    	}
						running = false;
						enableStart = true;
						break;
						
					case 2:
						interrupt = false;
						while(currentFrame > 0) {
							if(interrupt) {interrupt = false; enableStart = true; break;}
								currentFrame--;
								BufferedImage i = image.getFrameArray().get(currentFrame);
					        	label.setIcon(new ImageIcon(i));
					        	try {Thread.sleep(animationSpeed);} 
					        	catch (InterruptedException e) {}
				    	}
						running = false;
						enableStart = true;
						break;
				}
				
				
				
				
				
			}
		}
	}
	
}
