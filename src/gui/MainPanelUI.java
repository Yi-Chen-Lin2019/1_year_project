/*Code by Milek Radoslaw 2020.05*/

package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.DataAccessException;

public class MainPanelUI {
	
	JFrame frame;
	JPanel mainPanel;
	JPanel contentPanel;
	
	double screenWidth, screenHeight;
	
	ImageGen chainImage;
	ImageGen logoImage, logoImageBig;
	ImageGen btnEnteredImage, btnClickedImage;
	ImageGen exitBtnImage;
	
	
	public MainPanelUI() {
//		screenWidth =Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//		screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		screenWidth = 3840;
		screenHeight = 2160;

		if((screenHeight/screenWidth)>0.5625) {screenHeight=Math.floor(screenWidth*0.5625);}
		else if(screenHeight/screenWidth<0.5625) {screenWidth=Math.floor(screenHeight*1.7778);}
		
		initializeImages();
		initializeFrame();
		initialize();
		
	}

	
	private void initializeImages() {
		exitBtnImage = new ImageGen(2, 4, "/exitBtn.png", (int) Math.ceil(screenWidth*0.02864), (int) Math.ceil(screenHeight*0.02777));
		chainImage = new ImageGen(1,1, "/chain.png", (int) Math.ceil(screenWidth*1),(int) Math.ceil(screenHeight*0.16388));
		logoImage = new ImageGen(2, 1, "/logo.png", (int) Math.ceil(screenHeight*0.14537), (int) Math.ceil(screenHeight*0.14537));
		logoImageBig = new ImageGen(2, 1, "/logo.png", (int) Math.ceil(screenHeight*0.16388), (int) Math.ceil(screenHeight*0.16388));
		btnEnteredImage = new ImageGen(8, 1, "/btnEntered.png", (int) Math.ceil(screenWidth*0.13020), (int) Math.ceil(screenHeight*0.08611));
		btnClickedImage = new ImageGen(8, 1, "/btnClicked.png", (int) Math.ceil(screenWidth*0.13020), (int) Math.ceil(screenHeight*0.08611));
	}
	
	private void initializeFrame(){
		frame = new JFrame("Cykel20");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLocationRelativeTo(null);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds((int) Math.floor(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2-screenWidth/2), 0, (int)screenWidth, (int)screenHeight);
        frame.getContentPane().add(mainPanel);
        
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBounds((int) Math.ceil(screenWidth*0.02604), (int) Math.ceil(screenHeight*0.16388), (int) Math.ceil(screenWidth*0.94791), (int) Math.ceil(screenHeight*0.79166));
        mainPanel.add(contentPanel);
        
        frame.setVisible(true);
	}
	
	private void initialize() {
		JPanel mainNav = new JPanel();
		mainNav.setLayout(null);
		mainNav.setBackground(Color.WHITE);
		mainNav.setBounds(0, 0, (int) Math.ceil(screenWidth*1), (int) Math.ceil(screenHeight*0.16388));
		mainPanel.add(mainNav);
		
		
		JLabel exitBtn = new JLabel();
		exitBtn.setBounds((int) Math.ceil(screenWidth*0.97135), 0, (int) Math.ceil(screenWidth*0.02864), (int) Math.ceil(screenHeight*0.02777));
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
				exit1.setMode(2);
				exit1.startRunning();
			}
		});
		exitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				frame.dispose();
			}
		});
		mainNav.add(exitBtn);
		
		JLabel chain = new JLabel();
		chain.setBounds(0, 0, (int) Math.ceil(screenWidth*1),(int) Math.ceil(screenHeight*0.16388));
		chain.setHorizontalAlignment(JLabel.CENTER);
		chain.setIcon(new ImageIcon(chainImage.getFrameArray().get(0)));
		mainNav.add(chain);
		
		
		JLabel registerBikeBtn = new JLabel();
		registerBikeBtn.setBounds((int) Math.ceil(screenWidth*0.02604), (int) Math.ceil(screenHeight*0.04259), (int) Math.ceil(screenWidth*0.13020), (int) Math.ceil(screenHeight*0.08611));
		registerBikeBtn.setHorizontalAlignment(JLabel.CENTER);
		registerBikeBtn.setIcon(new ImageIcon(btnEnteredImage.getFrameArray().get(0)));
		AnimationPlayer b1 = new AnimationPlayer(btnEnteredImage, registerBikeBtn, 15);
		b1.start();
		AnimationPlayer bc1 = new AnimationPlayer(btnClickedImage, registerBikeBtn, 25);
		bc1.start();
		JLabel registerBikeBtnHitBox = new JLabel("REGISTER BIKE");
		registerBikeBtnHitBox.setForeground(new Color(64, 64, 64, 255));
		registerBikeBtnHitBox.setHorizontalAlignment(JLabel.CENTER);
		registerBikeBtnHitBox.setVerticalAlignment(JLabel.CENTER);
		registerBikeBtnHitBox.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(25*(screenWidth/1920))));
		registerBikeBtnHitBox.setBounds((int) Math.ceil(screenWidth*0.03385), (int) Math.ceil(screenHeight*0.06296), (int) Math.ceil(screenWidth*0.11406), (int) Math.ceil(screenHeight*0.04166));
		registerBikeBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				b1.setMode(1);
				b1.startRunning();
			}
		});
		registerBikeBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				b1.setMode(2);
				b1.startRunning();
			}
		});
		registerBikeBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				bc1.setMode(1);
				bc1.startRunning();
				bc1.setCurrentFrame(0);
				RegisterBikeUI registerBikeUI = new RegisterBikeUI(frame, contentPanel, screenWidth, screenHeight);

			}
		});
		mainNav.add(registerBikeBtnHitBox);
		mainNav.add(registerBikeBtn);
		
		JLabel workingOnBtn = new JLabel();
		workingOnBtn.setBounds((int) Math.ceil(screenWidth*0.16822), (int) Math.ceil(screenHeight*0.04259), (int) Math.ceil(screenWidth*0.13020), (int) Math.ceil(screenHeight*0.08611));
		workingOnBtn.setHorizontalAlignment(JLabel.CENTER);
		workingOnBtn.setIcon(new ImageIcon(btnEnteredImage.getFrameArray().get(0)));
		AnimationPlayer b2 = new AnimationPlayer(btnEnteredImage, workingOnBtn, 15);
		b2.start();
		AnimationPlayer bc2 = new AnimationPlayer(btnClickedImage, workingOnBtn, 25);
		bc2.start();
		JLabel workingOnBtnHitBox = new JLabel("WORKING ON");
		workingOnBtnHitBox.setForeground(new Color(64, 64, 64, 255));
		workingOnBtnHitBox.setHorizontalAlignment(JLabel.CENTER);
		workingOnBtnHitBox.setVerticalAlignment(JLabel.CENTER);
		workingOnBtnHitBox.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(25*(screenWidth/1920))));
		workingOnBtnHitBox.setBounds((int) Math.ceil(screenWidth*0.17604), (int) Math.ceil(screenHeight*0.06296), (int) Math.ceil(screenWidth*0.11406), (int) Math.ceil(screenHeight*0.04166));
		workingOnBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				b2.setMode(1);
				b2.startRunning();
			}
		});
		workingOnBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				b2.setMode(2);
				b2.startRunning();
			}
		});
		workingOnBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				bc2.setMode(1);
				bc2.startRunning();
				bc2.setCurrentFrame(0);
				try{UpdateBikeUI test = new UpdateBikeUI(frame, contentPanel, screenWidth, screenHeight);}
				catch(Exception a) {}
			}
		});
		mainNav.add(workingOnBtnHitBox);
		mainNav.add(workingOnBtn);
		
		
		JLabel bikesForSaleBtn = new JLabel();
		bikesForSaleBtn.setBounds((int) Math.ceil(screenWidth*0.31093), (int) Math.ceil(screenHeight*0.04259), (int) Math.ceil(screenWidth*0.13020), (int) Math.ceil(screenHeight*0.08611));
		bikesForSaleBtn.setHorizontalAlignment(JLabel.CENTER);
		bikesForSaleBtn.setIcon(new ImageIcon(btnEnteredImage.getFrameArray().get(0)));
		AnimationPlayer b3 = new AnimationPlayer(btnEnteredImage, bikesForSaleBtn, 15);
		b3.start();
		AnimationPlayer bc3 = new AnimationPlayer(btnClickedImage, bikesForSaleBtn, 25);
		bc3.start();
		JLabel bikesForSaleBtnHitBox = new JLabel("BIKES FOR SALE");
		bikesForSaleBtnHitBox.setForeground(new Color(64, 64, 64, 255));
		bikesForSaleBtnHitBox.setHorizontalAlignment(JLabel.CENTER);
		bikesForSaleBtnHitBox.setVerticalAlignment(JLabel.CENTER);
		bikesForSaleBtnHitBox.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(25*(screenWidth/1920))));
		bikesForSaleBtnHitBox.setBounds((int) Math.ceil(screenWidth*0.31875), (int) Math.ceil(screenHeight*0.06296), (int) Math.ceil(screenWidth*0.11406), (int) Math.ceil(screenHeight*0.04166));
		bikesForSaleBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				b3.setMode(1);
				b3.startRunning();
			}
		});
		bikesForSaleBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				b3.setMode(2);
				b3.startRunning();
			}
		});
		bikesForSaleBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				bc3.setMode(1);
				bc3.startRunning();
				bc3.setCurrentFrame(0);
			}
		});
		mainNav.add(bikesForSaleBtnHitBox);
		mainNav.add(bikesForSaleBtn);
		
		
		JLabel bikeHistoryBtn = new JLabel();
		bikeHistoryBtn.setBounds((int) Math.ceil(screenWidth*0.55833), (int) Math.ceil(screenHeight*0.04259), (int) Math.ceil(screenWidth*0.13020), (int) Math.ceil(screenHeight*0.08611));
		bikeHistoryBtn.setHorizontalAlignment(JLabel.CENTER);
		bikeHistoryBtn.setIcon(new ImageIcon(btnEnteredImage.getFrameArray().get(0)));
		AnimationPlayer b4 = new AnimationPlayer(btnEnteredImage, bikeHistoryBtn, 15);
		b4.start();
		AnimationPlayer bc4 = new AnimationPlayer(btnClickedImage, bikeHistoryBtn, 25);
		bc4.start();
		JLabel bikeHistoryBtnHitBox = new JLabel("BIKE HISTORY");
		bikeHistoryBtnHitBox.setForeground(new Color(64, 64, 64, 255));
		bikeHistoryBtnHitBox.setHorizontalAlignment(JLabel.CENTER);
		bikeHistoryBtnHitBox.setVerticalAlignment(JLabel.CENTER);
		bikeHistoryBtnHitBox.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(25*(screenWidth/1920))));
		bikeHistoryBtnHitBox.setBounds((int) Math.ceil(screenWidth*0.56614), (int) Math.ceil(screenHeight*0.06296), (int) Math.ceil(screenWidth*0.11406), (int) Math.ceil(screenHeight*0.04166));
		bikeHistoryBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				b4.setMode(1);
				b4.startRunning();
			}
		});
		bikeHistoryBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				b4.setMode(2);
				b4.startRunning();
			}
		});
		bikeHistoryBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				bc4.setMode(1);
				bc4.startRunning();
				bc4.setCurrentFrame(0);
			}
		});
		mainNav.add(bikeHistoryBtnHitBox);
		mainNav.add(bikeHistoryBtn);
		
		
		JLabel statisticsBtn = new JLabel();
		statisticsBtn.setBounds((int) Math.ceil(screenWidth*0.70052), (int) Math.ceil(screenHeight*0.04259), (int) Math.ceil(screenWidth*0.13020), (int) Math.ceil(screenHeight*0.08611));
		statisticsBtn.setHorizontalAlignment(JLabel.CENTER);
		statisticsBtn.setIcon(new ImageIcon(btnEnteredImage.getFrameArray().get(0)));
		AnimationPlayer b5 = new AnimationPlayer(btnEnteredImage, statisticsBtn, 15);
		b5.start();
		AnimationPlayer bc5 = new AnimationPlayer(btnClickedImage, statisticsBtn, 25);
		bc5.start();
		JLabel statisticsBtnHitBox = new JLabel("STATISTICS");
		statisticsBtnHitBox.setForeground(new Color(64, 64, 64, 255));
		statisticsBtnHitBox.setHorizontalAlignment(JLabel.CENTER);
		statisticsBtnHitBox.setVerticalAlignment(JLabel.CENTER);
		statisticsBtnHitBox.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(25*(screenWidth/1920))));
		statisticsBtnHitBox.setBounds((int) Math.ceil(screenWidth*0.70833), (int) Math.ceil(screenHeight*0.06296), (int) Math.ceil(screenWidth*0.11406), (int) Math.ceil(screenHeight*0.04166));
		statisticsBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				b5.setMode(1);
				b5.startRunning();
			}
		});
		statisticsBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				b5.setMode(2);
				b5.startRunning();
			}
		});
		statisticsBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				bc5.setMode(1);
				bc5.startRunning();
				bc5.setCurrentFrame(0);
			}
		});
		mainNav.add(statisticsBtnHitBox);
		mainNav.add(statisticsBtn);
		
		
		JLabel settingsBtn = new JLabel();
		settingsBtn.setBounds((int) Math.ceil(screenWidth*0.84322), (int) Math.ceil(screenHeight*0.04259), (int) Math.ceil(screenWidth*0.13020), (int) Math.ceil(screenHeight*0.08611));
		settingsBtn.setHorizontalAlignment(JLabel.CENTER);
		settingsBtn.setIcon(new ImageIcon(btnEnteredImage.getFrameArray().get(0)));
		AnimationPlayer b6 = new AnimationPlayer(btnEnteredImage, settingsBtn, 15);
		b6.start();
		AnimationPlayer bc6 = new AnimationPlayer(btnClickedImage, settingsBtn, 25);
		bc6.start();
		JLabel settingsBtnHitBox = new JLabel("SETTINGS");
		settingsBtnHitBox.setForeground(new Color(64, 64, 64, 255));
		settingsBtnHitBox.setHorizontalAlignment(JLabel.CENTER);
		settingsBtnHitBox.setVerticalAlignment(JLabel.CENTER);
		settingsBtnHitBox.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(25*(screenWidth/1920))));
		settingsBtnHitBox.setBounds((int) Math.ceil(screenWidth*0.85104), (int) Math.ceil(screenHeight*0.06296), (int) Math.ceil(screenWidth*0.11406), (int) Math.ceil(screenHeight*0.04166));
		settingsBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				b6.setMode(1);
				b6.startRunning();
			}
		});
		settingsBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				b6.setMode(2);
				b6.startRunning();
			}
		});
		settingsBtnHitBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				bc6.setMode(1);
				bc6.startRunning();
				bc6.setCurrentFrame(0);
			}
		});
		mainNav.add(settingsBtnHitBox);
		mainNav.add(settingsBtn);
		
		JLabel logo = new JLabel();
		logo.setBounds((int) Math.ceil(screenWidth*0.45364), 0, (int) Math.ceil(screenHeight*0.16388), (int) Math.ceil(screenHeight*0.16388));
		logo.setHorizontalAlignment(JLabel.CENTER);
		logo.setIcon(new ImageIcon(logoImage.getFrameArray().get(0)));
		AnimationPlayer l1 = new AnimationPlayer(logoImageBig, logo, 150);
		l1.start();
        logo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				l1.setMode(0);
				l1.startRunning();
			}
		});
        logo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				l1.stopRunning();
				logo.setIcon(new ImageIcon(logoImage.getFrameArray().get(0)));
			}
		});
        logo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				MainMenuUI mainMenuUI = new MainMenuUI(frame, contentPanel, screenWidth, screenHeight);
			}
		});
		mainNav.add(logo);
		
		System.out.println("Your screen resolution: " + screenWidth + " x " + screenHeight);
		
		frame.repaint();
	}
}
