/**
 * @author Radoslaw Milek, Yi-Chen Lin
 * @since 2020-05
 * 
 */

package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.BikeCtr;
import controller.StatisticsCtr;
import db.DataAccessException;
import model.Bike;

public class BikeListUI extends GuiTools{
	
	BikeCtr bikeCtr;
	
	double screenWidth, screenHeight;
	JFrame frame;
	JPanel contentPanel;
	int listType;
	String middleButtonText;
	
	int selectedIndex = -1;
	
	ImageGen loadingImage; JLabel loadingAnimation;
	
	JTextArea noteTextField;
	
	JTextField searchField;
	JPanel bikeListPanel, bikeInformationPanel;
	JScrollPane bikeList, bikeInformationScroll;
	
	ArrayList<JPanel> savedListComponents = new ArrayList<>();
	
	//bikeArrayList gets all bikes from db, searchBikeArrayList changes depending on search result
	ArrayList<Bike> bikeArrayList, searchBikeArrayList;
	
	public BikeListUI(JFrame frame, JPanel contentPanel, double screenWidth, double screenHeight, int listType) {
		this.frame = frame;
		this.contentPanel = contentPanel;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		//Decides what list of bikes will be shown
		//1 - workingOn     2 - 
		this.listType = listType;
		

		
		
		startLoadingAnimation(frame, contentPanel);
		//Initialazing frame in new thread so the loadingAnimation would play during loading
		Thread t1 = new Thread(new Runnable()
				{
				public void run() {
					try {bikeCtr = new BikeCtr();}
					catch(DataAccessException e) {e.printStackTrace();}
					bikeArrayList = new ArrayList<>();
					
					contentPanel.removeAll();
					initializeImages();
					initialize();
					stopLoadingAnimation(frame, contentPanel);
					
					frame.repaint();
				}
				});
		t1.start();
		
	}
	
	private void initializeImages() {
	}
	
	private void initialize() {
		bikeListPanel = new JPanel();
        
		bikeList = new JScrollPane (bikeListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bikeList.setBounds(0, (int) Math.ceil(screenHeight * 0.0787), (int) Math.ceil(screenWidth * 0.31614), (int) Math.ceil(screenHeight * 0.71203));
        
        switch(listType) {
        case 1: 
        	middleButtonText = "Sell";
        	constructBikeListPanel();
        	break;
        	
        case 2: 
        	middleButtonText = "Sold";
        	constructBikeListPanel();
        	break;
        	
        case 3: 
        	middleButtonText = "";
        	constructBikeListPanel();
        	break;
        
        
        default: System.out.println("Unexistant listType");
        }
        
		
		searchField = new JTextField("Search Bike");
		searchField.setBounds(0, (int) Math.ceil(screenHeight * 0.04629), (int) Math.ceil(screenWidth * 0.29218), (int) Math.ceil(screenHeight * 0.02314));
		searchField.setForeground(Color.BLACK);
		searchField.setFont(new Font("Tahoma", Font.PLAIN, (int) Math.round(18 * (screenWidth / 1920))));
		searchField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {if(searchField.getText().equals("Search Bike")) {searchField.setText(""); 
			}
			}
		});
		
		JButton searchButton = new JButton();
		searchButton.setBounds((int) Math.ceil(screenWidth * 0.29739), (int) Math.ceil(screenHeight * 0.04629), (int) Math.ceil(screenWidth * 0.01875), (int) Math.ceil(screenHeight * 0.02314));
		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				searchBike();
			}
		});
		
		bikeInformationPanel = new JPanel();
		bikeInformationPanel.setLayout(new BoxLayout(bikeInformationPanel, BoxLayout.PAGE_AXIS));
		bikeInformationPanel.setBackground(Color.WHITE);
		
        bikeInformationScroll = new JScrollPane(bikeInformationPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        bikeInformationScroll.setBounds((int) Math.ceil(screenWidth * 0.36822), (int) Math.ceil(screenHeight * 0.04629), (int) Math.ceil(screenWidth * 0.31614), (int) Math.ceil(screenHeight * 0.46296));
        bikeInformationScroll.setPreferredSize(new Dimension((int) Math.ceil(screenWidth * 0.31614), (int) Math.ceil(screenHeight * 0.46296)));
        constructInformationPanel();
		
		
		JButton updateBikeBtn = new JButton("Repair");
		updateBikeBtn.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(17 * (screenWidth / 1920))));
		updateBikeBtn.setHorizontalAlignment(JButton.CENTER);
		updateBikeBtn.setBounds((int) Math.ceil(screenWidth * 0.38229), (int) Math.ceil(screenHeight * 0.52314),(int) Math.ceil(screenWidth * 0.07031), (int) Math.ceil(screenHeight * 0.02777));
		updateBikeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
					if(selectedIndex!=-1) {
						startLoadingAnimation(frame,contentPanel);
						Thread t1 = new Thread(new Runnable()
							{
								public void run() {
									try {
										Bike selectedBike = bikeCtr.findBikeByID(searchBikeArrayList.get(selectedIndex-1).getId());
										Object[] options = {"English", "Danish"};
										int x = JOptionPane.showOptionDialog(null,"Choose a language for checklist: ",
								                "Click a button",
								                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
										if(!wasLoadingInterrupted()) {UpdateBikeUI updateBikeUI = new UpdateBikeUI(frame, contentPanel, screenWidth, screenHeight, selectedBike, x);}
									}
									catch (DataAccessException e1) {
										e1.printStackTrace();
									}
								}
						});
						t1.start();
					}
				}
			});
		
		JButton middleBtn = new JButton(middleButtonText);
		middleBtn.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(17 * (screenWidth / 1920))));
		middleBtn.setHorizontalAlignment(JButton.CENTER);
		middleBtn.setBounds((int) Math.ceil(screenWidth * 0.48385), (int) Math.ceil(screenHeight * 0.52314),(int) Math.ceil(screenWidth * 0.07031), (int) Math.ceil(screenHeight * 0.02777));
		middleBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				switch(listType) {
				case 1: 
					if(selectedIndex!=-1) {
						startLoadingAnimation(frame,contentPanel);
						Thread t1 = new Thread(new Runnable()
							{
								public void run() {
									try{markForSale(bikeCtr.findBikeByID(searchBikeArrayList.get(selectedIndex-1).getId()));}
									catch(DataAccessException a) {a.printStackTrace();}
								}
						});
						t1.start();
					}
					
				break;
				}
			}
		});
		
		JButton deleteBikeBtn = new JButton("Delete");
		deleteBikeBtn.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(17 * (screenWidth / 1920))));
		deleteBikeBtn.setHorizontalAlignment(JButton.CENTER);
		deleteBikeBtn.setBounds((int) Math.ceil(screenWidth * 0.58541), (int) Math.ceil(screenHeight * 0.52314),(int) Math.ceil(screenWidth * 0.07031), (int) Math.ceil(screenHeight * 0.02777));
		deleteBikeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(selectedIndex!=-1) {
					Thread t1 = new Thread(new Runnable()
						{
							public void run() {
								try {
									Bike selectedBike = bikeCtr.findBikeByID(searchBikeArrayList.get(selectedIndex-1).getId());
									bikeCtr.deleteBike(selectedBike);
									
									constructBikeListPanel();
								}
								catch (DataAccessException e1) {
									e1.printStackTrace();
								}
							}
					});
					t1.start();
				}
			}
		});
		
		/** Note Panel */
		JPanel notePanel = new JPanel();
		notePanel.setLayout(null);
		notePanel.setBackground(Color.WHITE);
		notePanel.setBounds((int) Math.ceil(screenWidth * 0.74739), (int) Math.ceil(screenHeight * 0.04629),
				(int) Math.ceil(screenWidth * 0.2), (int) Math.ceil(screenHeight * 0.74351));

		// Creates a stroke around a note
		JLabel noteStroke = new JLabel();
		noteStroke.setBounds(0, 0, (int) Math.ceil(screenWidth * 0.2), (int) Math.ceil(screenHeight * 0.74351));
		Dimension arcs = new Dimension(15, 15);
		BufferedImage noteStrokeImage = new BufferedImage((int) Math.ceil(screenWidth * 0.2),
				(int) Math.ceil(screenHeight * 0.74351), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = noteStrokeImage.createGraphics();
		int width = noteStroke.getWidth();
		int height = noteStroke.getHeight();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		float dash1[] = { (0.02314f * (float) screenHeight) };
		final BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1,
				0.0f);
		g.setStroke(dashed);

		// Draws the rounded panel with borders.
		g.setColor(Color.BLACK);
		g.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);// paint

		noteStroke.setHorizontalAlignment(JLabel.CENTER);
		noteStroke.setVerticalAlignment(JLabel.CENTER);
		noteStroke.setIcon(new ImageIcon(noteStrokeImage));
		notePanel.add(noteStroke);

		noteTextField = new JTextArea();
		noteTextField.setEditable(false);
		noteTextField.setForeground(Color.BLACK);
		noteTextField.setLineWrap(true);
		noteTextField.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(30 * (screenWidth / 1920))));
		noteTextField.setBounds(2, 2, (int) Math.ceil(screenWidth * 0.2) - 4,
				(int) Math.ceil(screenHeight * 0.74351) - 4);
		notePanel.add(noteTextField);
		
		//Statistics panel
		JPanel statisticPanel = new JPanel();
		statisticPanel.setLayout(null);
		statisticPanel.setBackground(Color.WHITE);
		statisticPanel.setBounds((int) Math.ceil(screenWidth * 0.74739), (int) Math.ceil(screenHeight * 0.04629),
				(int) Math.ceil(screenWidth * 0.2), (int) Math.ceil(screenHeight * 0.74351));
		showStatistic(statisticPanel);
		
		
		//If loading wasn't interrupted, whole scene is added to contentPanel
		if(!wasLoadingInterrupted()) {
			contentPanel.add(bikeList);
			contentPanel.add(searchField);
			contentPanel.add(searchButton);
			contentPanel.add(bikeInformationScroll);
			contentPanel.add(updateBikeBtn);
			contentPanel.add(middleBtn);
			contentPanel.add(deleteBikeBtn);
			contentPanel.add(notePanel);
			contentPanel.add(statisticPanel);
		}

	}
	
	private void constructBikeListPanel() {
		int tableDivideNumber = 0;
		//iterator, counts number of forEach loop
		int it = 0;
		boolean isFirst = true;
		bikeListPanel = new JPanel();
		bikeListPanel.setLayout(new BoxLayout(bikeListPanel, BoxLayout.PAGE_AXIS));
		bikeListPanel.setBackground(Color.WHITE);
			switch(listType) {
			case 1:
				workingOn(it, isFirst, tableDivideNumber);				
			break;

			case 2:
				selling(it, isFirst, tableDivideNumber);
			break;
					
			case 3:
				bikeHistory(it, isFirst, tableDivideNumber);
			break;
			
			default:
				
			}
			
		}	
	
	private void bikeHistory(int it, boolean isFirst, int tableDivideNumber) {
		try {
    		StatisticsCtr sCtr = new StatisticsCtr();
    		searchBikeArrayList = bikeArrayList = (ArrayList<Bike>) sCtr.findAllBikes();}
    	catch(DataAccessException e) {e.printStackTrace();}
		//For each loop creates listComponents from bike list
		for(int x = -1; x < searchBikeArrayList.size(); x++) {
		//assigns number to every listComponent
		final int itFin = it;
		final JPanel listComponent = new JPanel();
		bikeListPanel.setPreferredSize(new Dimension(0, searchBikeArrayList.size()*(int) Math.ceil(screenHeight * 0.028)));
		
		listComponent.setBackground(new Color(64, 64, 64, 255));
		listComponent.setOpaque(true);
		listComponent.setLayout(new BoxLayout(listComponent, BoxLayout.LINE_AXIS));
		listComponent.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) Math.ceil(screenHeight * 0.028)));
		
			if(it==0) {
				listComponent.setBackground(new Color(125, 184, 182, 255));
				JLabel tableLabel1 = new JLabel("   Name");
				JLabel tableLabel2 = new JLabel("   Serial Number");
				JLabel tableLabel3 = new JLabel("   Registered Date");
				listComponent.add(tableLabel1);
				listComponent.add(tableLabel2);
				listComponent.add(tableLabel3);
				
				//Sets divide number based on number of labels added to listComponent
				for(Component c : listComponent.getComponents()) {tableDivideNumber++;}
				
				//Setting default values to every Label in ListComponent
				for(Component c : listComponent.getComponents()) {
					JLabel label = (JLabel) c;
					label.setMaximumSize(new Dimension((int) Math.ceil(screenWidth * 0.31614/tableDivideNumber), Integer.MAX_VALUE));
					label.setForeground(Color.WHITE);
					label.setHorizontalAlignment(JLabel.LEFT);
					label.setVerticalAlignment(JLabel.CENTER);
					label.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(15 * (screenWidth / 1920))));
				}
			}
			
			else {
				listComponent.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						if(selectedIndex == itFin) {listComponent.setBackground(new Color(124, 153, 38, 255));}
						else {listComponent.setBackground(new Color(107, 107, 107, 255));}
					}
				});
				listComponent.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseExited(MouseEvent e) {
						if(selectedIndex == itFin) {listComponent.setBackground(new Color(124, 153, 38, 255));}
						else {listComponent.setBackground(new Color(64, 64, 64, 255));}
					}
				});
				listComponent.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						selectedIndex = itFin;
						updateListSelectedColor();
						Thread t1 = new Thread(new Runnable()
						{
							public void run() {
								constructInformationPanel();
							}
						});
						t1.start();
						
						listComponent.setBackground(new Color(124, 153, 38, 255));
					}
				});
				
				JLabel bikeNameLabel = new JLabel("     "+searchBikeArrayList.get(x).getBikeName());
				JLabel bikeSerialLabel = new JLabel("     "+ searchBikeArrayList.get(x).getSerialNumber());
				JLabel bikeDateLabel = new JLabel("     "+ searchBikeArrayList.get(x).getRegisterDate().getDayOfMonth() +"."+ searchBikeArrayList.get(x).getRegisterDate().getMonthValue() +"."+ searchBikeArrayList.get(x).getRegisterDate().getYear());
				listComponent.add(bikeNameLabel);
				listComponent.add(bikeSerialLabel);
				listComponent.add(bikeDateLabel);
				savedListComponents.add(listComponent);
				
				
				for(Component c : listComponent.getComponents()) {
					JLabel label = (JLabel) c;
					label.setMaximumSize(new Dimension((int) Math.ceil(screenWidth * 0.31614/tableDivideNumber), Integer.MAX_VALUE));
					label.setForeground(Color.WHITE);
					label.setHorizontalAlignment(JLabel.LEFT);
					label.setVerticalAlignment(JLabel.CENTER);
					label.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(15 * (screenWidth / 1920))));
				}		
			}
			
			bikeListPanel.add(listComponent);
			bikeListPanel.add(Box.createRigidArea(new Dimension(0, (int) Math.ceil(screenHeight * 0.00462))));
			it++;
		}
		bikeList.setViewportView(bikeListPanel);
		
	}

	private void selling(int it, boolean isFirst, int tableDivideNumber) {
		try {
    		StatisticsCtr sCtr = new StatisticsCtr();
    		searchBikeArrayList = bikeArrayList = (ArrayList<Bike>) sCtr.getForSaleBikes();}
    	catch(DataAccessException e) {e.printStackTrace();}
		//For each loop creates listComponents from bike list
		for(int x = -1; x<searchBikeArrayList.size(); x++) {
		//assigns number to every listComponent
		final int itFin = it;
		final JPanel listComponent = new JPanel();
		bikeListPanel.setPreferredSize(new Dimension(0, searchBikeArrayList.size()*(int) Math.ceil(screenHeight * 0.028)));
		
		listComponent.setBackground(new Color(64, 64, 64, 255));
		listComponent.setOpaque(true);
		listComponent.setLayout(new BoxLayout(listComponent, BoxLayout.LINE_AXIS));
		listComponent.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) Math.ceil(screenHeight * 0.028)));
		
			if(it==0) {
				listComponent.setBackground(new Color(125, 184, 182, 255));
				JLabel tableLabel1 = new JLabel("   Name");
				JLabel tableLabel2 = new JLabel("   Serial Number");
				JLabel tableLabel3 = new JLabel("   Registered Date");
				listComponent.add(tableLabel1);
				listComponent.add(tableLabel2);
				listComponent.add(tableLabel3);
				
				//Sets divide number based on number of labels added to listComponent
				for(Component c : listComponent.getComponents()) {tableDivideNumber++;}
				
				//Setting default values to every Label in ListComponent
				for(Component c : listComponent.getComponents()) {
					JLabel label = (JLabel) c;
					label.setMaximumSize(new Dimension((int) Math.ceil(screenWidth * 0.31614/tableDivideNumber), Integer.MAX_VALUE));
					label.setForeground(Color.WHITE);
					label.setHorizontalAlignment(JLabel.LEFT);
					label.setVerticalAlignment(JLabel.CENTER);
					label.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(15 * (screenWidth / 1920))));
				}
			}
			
			else {
				listComponent.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						if(selectedIndex == itFin) {listComponent.setBackground(new Color(124, 153, 38, 255));}
						else {listComponent.setBackground(new Color(107, 107, 107, 255));}
					}
				});
				listComponent.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseExited(MouseEvent e) {
						if(selectedIndex == itFin) {listComponent.setBackground(new Color(124, 153, 38, 255));}
						else {listComponent.setBackground(new Color(64, 64, 64, 255));}
					}
				});
				listComponent.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						selectedIndex = itFin;
						updateListSelectedColor();
						Thread t1 = new Thread(new Runnable()
						{
							public void run() {
								constructInformationPanel();
							}
						});
						t1.start();
						
						listComponent.setBackground(new Color(124, 153, 38, 255));
					}
				});
				
				JLabel bikeNameLabel = new JLabel("     "+searchBikeArrayList.get(x).getBikeName());
				JLabel bikeSerialLabel = new JLabel("     "+ searchBikeArrayList.get(x).getSerialNumber());
				JLabel bikeDateLabel = new JLabel("     "+ searchBikeArrayList.get(x).getRegisterDate().getDayOfMonth() +"."+ searchBikeArrayList.get(x).getRegisterDate().getMonthValue() +"."+ searchBikeArrayList.get(x).getRegisterDate().getYear());
				listComponent.add(bikeNameLabel);
				listComponent.add(bikeSerialLabel);
				listComponent.add(bikeDateLabel);
				savedListComponents.add(listComponent);
				
				
				for(Component c : listComponent.getComponents()) {
					JLabel label = (JLabel) c;
					label.setMaximumSize(new Dimension((int) Math.ceil(screenWidth * 0.31614/tableDivideNumber), Integer.MAX_VALUE));
					label.setForeground(Color.WHITE);
					label.setHorizontalAlignment(JLabel.LEFT);
					label.setVerticalAlignment(JLabel.CENTER);
					label.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(15 * (screenWidth / 1920))));
				}		
			}
			
			bikeListPanel.add(listComponent);
			bikeListPanel.add(Box.createRigidArea(new Dimension(0, (int) Math.ceil(screenHeight * 0.00462))));
			it++;
		}
		bikeList.setViewportView(bikeListPanel);
		
	}

	//When clicked on list component it sets every component color to default
		private void updateListSelectedColor() {
			for(JPanel i : savedListComponents) {
				i.setBackground(new Color(64, 64, 64, 255));
			}
		}
		
		private void searchBike() {
			String searchTxt = searchField.getText();
			searchBikeArrayList = new ArrayList<>();
			for(Bike bike : bikeArrayList)
			{
				try {
				if(bike.getSerialNumber().toLowerCase().startsWith(searchTxt.toLowerCase())){searchBikeArrayList.add(bike);}
				else if(searchTxt.equals("")) {searchBikeArrayList.add(bike);}
				}
				catch(Exception ex){}
			}
			constructBikeListPanel();
		}
		
	private void constructInformationPanel() {
		bikeInformationPanel.removeAll();
		if(selectedIndex!=-1) {
			Bike selectedBike = searchBikeArrayList.get(selectedIndex-1);

			//iterator, counts number of forEach loop
			
			//For each loop creates listComponents from bike list
			for(int i = 0; i < 4; i++) {
				
			final JPanel listComponent = new JPanel();
			listComponent.setLayout(new BoxLayout(listComponent, BoxLayout.LINE_AXIS));
			listComponent.setBackground(Color.WHITE);
			listComponent.setOpaque(true);
					
			bikeInformationPanel.setMaximumSize(new Dimension((int) Math.ceil(screenWidth * 0.31614), 4*(int) Math.ceil(screenHeight * 0.04)));
				
				JLabel row1 = new JLabel(); 
				row1.setHorizontalAlignment(JLabel.LEFT);
				JLabel row2 = new JLabel();
				row2.setHorizontalAlignment(JLabel.LEFT);
				
				
				if(i==0) {
					row1.setText(" Serial Number: " + selectedBike.getSerialNumber());
					row2.setText(" Name: " + selectedBike.getBikeName());
				}
				else if(i==1) {
					row1.setText(" Register Date: " + selectedBike.getRegisterDate().getDayOfMonth() +"."+ selectedBike.getRegisterDate().getMonthValue() +"."+ selectedBike.getRegisterDate().getYear());
					if(selectedBike.getSoldDate()!=null) {
					row2.setText("Sell Date: " + selectedBike.getSoldDate().getDayOfMonth() +"."+ selectedBike.getSoldDate().getMonthValue() +"."+ selectedBike.getSoldDate().getYear());
					}
					else {row2.setText("Sell Date:            ");}
				}
				else if(i==2) {
					String s ="";
					switch(selectedBike.getGender()) {
					case "M": s = "Male";
					case "F": s = "Female";
					case "U": s = "Unisex";
					}
					row1.setText(" Gender: " + s);
					if(selectedBike.getIsExternalGear()) {s = "External ";}
					else {s = "Internal ";}
					row2.setText("Gear Type: " + s);
				}
				else if(i==3) {
					row1.setText(" Price: " + selectedBike.getFinalPrice());
					row2.setText(" Sell Price: " + selectedBike.getSalePrice() + " ");
				}
					listComponent.add(row1);
					listComponent.add(row2);
				
				for(Component c : listComponent.getComponents()) {
					JLabel label = (JLabel) c;
					label.setMaximumSize(new Dimension((int) Math.ceil(screenWidth * 0.31614/2), Integer.MAX_VALUE));
					label.setForeground(Color.BLACK);								
					label.setVerticalAlignment(JLabel.CENTER);
					label.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(20 * (screenWidth / 1920))));
				}

				noteTextField.setText("Loading... Please Wait.");
				Thread t1 = new Thread(new Runnable()
				{
					public void run() {
						try {
							Bike fullBike = bikeCtr.findBikeByID(searchBikeArrayList.get(selectedIndex-1).getId());
							noteTextField.setText(fullBike.getRepairList().getNote());
						} catch (DataAccessException e) {}
						
						
					}
				});
				t1.start();
				bikeInformationPanel.add(listComponent);
			}
			bikeInformationScroll.setViewportView(bikeInformationPanel);
		}
	}
	
	private void markForSale(Bike bike) {
		stopLoadingAnimation(frame, contentPanel);
		Container panel = (Container) contentPanel.getParent().getComponents()[0];
		contentPanel.setVisible(false);
		panel.setVisible(true);
		
		 JPanel markForSaleWindow = new JPanel();			
		 	markForSaleWindow.setBackground(Color.LIGHT_GRAY);
		 	markForSaleWindow.setOpaque(true);
		 	markForSaleWindow.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(30 * (screenWidth / 1920))));
		 	markForSaleWindow.setBounds((int) Math.ceil(screenWidth*0.30729), (int) Math.ceil(screenHeight*0.10833), (int) Math.ceil(screenWidth*0.33489), (int) Math.ceil(screenHeight*0.36388));
		 	markForSaleWindow.setLayout(null);
			panel.add(markForSaleWindow);
			
			JLabel label = new JLabel("Type in a Price:");
			label.setForeground(Color.BLACK);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(40 * (screenWidth / 1920))));
			label.setBounds((int) Math.ceil(screenWidth*0.04687), (int) Math.ceil(screenHeight*0.02685), (int) Math.ceil(screenWidth*0.24270), (int) Math.ceil(screenHeight*0.09259));
			markForSaleWindow.add(label);
			
			double totalPartPrice = bikeCtr.getTotalPartPrice(bike);
			JLabel label2 = new JLabel("Total part price: " + totalPartPrice);
			label2.setForeground(Color.BLACK);
			label2.setHorizontalAlignment(JLabel.CENTER);
			label2.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(40 * (screenWidth / 1920))));
			label2.setBounds((int) Math.ceil(screenWidth*0.04687), (int) Math.ceil(screenHeight*0.10648), (int) Math.ceil(screenWidth*0.24270), (int) Math.ceil(screenHeight*0.04629));
			markForSaleWindow.add(label2);
			
			JTextField text = new JTextField();
			text.setForeground(Color.BLACK);
			text.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(40 * (screenWidth / 1920))));
			text.setBounds((int) Math.ceil(screenWidth*0.05989), (int) Math.ceil(screenHeight*0.19259), (int) Math.ceil(screenWidth*0.21458), (int) Math.ceil(screenHeight*0.07314));
			markForSaleWindow.add(text);
			
			JButton button = new JButton("OK");
			button.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(16 * (screenWidth / 1920))));
			button.setHorizontalAlignment(JButton.CENTER);
			button.setBounds((int) Math.ceil(screenWidth * 0.09583), (int) Math.ceil(screenHeight * 0.30648), (int) Math.ceil(screenWidth * 0.05520), (int) Math.ceil(screenHeight * 0.03148));
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					try {
						double finalPrice = Double.valueOf(text.getText());
						bike.setTotalPartPrice(totalPartPrice);
						bike.getRepairList().setIsFinished(true);
						bike.setFinalPrice(finalPrice);
						
						bikeCtr.updateBike(bike);
						
						selectedIndex = -1;
						constructBikeListPanel();
						panel.removeAll();
			            panel.setVisible(false);
			            contentPanel.setVisible(true);
			            frame.repaint();
					}
					catch(NumberFormatException a) {label.setText("Price must be a number:");}
					catch(DataAccessException b) {b.printStackTrace();}
				}
			});
			markForSaleWindow.add(button);
			
			
			JButton button2 = new JButton("Cancel");
			button2.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(16 * (screenWidth / 1920))));
			button2.setHorizontalAlignment(JButton.CENTER);
			button2.setBounds((int) Math.ceil(screenWidth * 0.18281), (int) Math.ceil(screenHeight * 0.30648), (int) Math.ceil(screenWidth * 0.05520), (int) Math.ceil(screenHeight * 0.03148));
			button2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					panel.removeAll();
		            panel.setVisible(false);
		            contentPanel.setVisible(true);
		            frame.repaint();
				}
			});
			markForSaleWindow.add(button2);
			
			frame.repaint();
	}
	
	private void showStatistic(JPanel panel) {
		StatisticsCtr sctr;
		try {
			sctr = new StatisticsCtr();
			int male = 0;
			int female = 0;
			int unix = 0;
			for(Bike b: sctr.findAllBikes()) {
				switch(b.getGender()) {
				case "M":
				male ++;
				break;
				
				case "F":
				female ++;
				break;
				
				case "U":
				unix ++;
				break;
				}
			}
			JLabel femaleLabel = new JLabel("Female: ");
			JLabel maleLabel = new JLabel("Male: ");
			JLabel unixLebel = new JLabel("Unisex: ");
			panel.add(femaleLabel);
			
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	private void workingOn(int it, boolean isFirst, int tableDivideNumber) {
		try {
    		//lin
    		StatisticsCtr sCtr = new StatisticsCtr();
    		searchBikeArrayList = bikeArrayList = (ArrayList<Bike>) sCtr.getUnfinishedBikes();}
    	catch(DataAccessException e) {e.printStackTrace();}
		//For each loop creates listComponents from bike list
		for(int x = -1; x<searchBikeArrayList.size(); x++) {
			//assigns number to every listComponent
			final int itFin = it;
			final JPanel listComponent = new JPanel();
			bikeListPanel.setPreferredSize(new Dimension(0, searchBikeArrayList.size()*(int) Math.ceil(screenHeight * 0.028)));
			
			listComponent.setBackground(new Color(64, 64, 64, 255));
			listComponent.setOpaque(true);
			listComponent.setLayout(new BoxLayout(listComponent, BoxLayout.LINE_AXIS));
			listComponent.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) Math.ceil(screenHeight * 0.028)));	
				if(it == 0) {					
					listComponent.setBackground(new Color(125, 184, 182, 255));
					JLabel tableLabel1 = new JLabel("   Name");
					JLabel tableLabel2 = new JLabel("   Serial Number");
					JLabel tableLabel3 = new JLabel("   Registered Date");
					listComponent.add(tableLabel1);
					listComponent.add(tableLabel2);
					listComponent.add(tableLabel3);
					
					//Sets divide number based on number of labels added to listComponent
					for(Component c : listComponent.getComponents()) {tableDivideNumber++;}
					
					//Setting default values to every Label in ListComponent
					for(Component c : listComponent.getComponents()) {
						JLabel label = (JLabel) c;
						label.setMaximumSize(new Dimension((int) Math.ceil(screenWidth * 0.31614/tableDivideNumber), Integer.MAX_VALUE));
						label.setForeground(Color.WHITE);
						label.setHorizontalAlignment(JLabel.LEFT);
						label.setVerticalAlignment(JLabel.CENTER);
						label.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(15 * (screenWidth / 1920))));
					}										
				} else {	
					listComponent.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {
							if(selectedIndex == itFin) {listComponent.setBackground(new Color(124, 153, 38, 255));}
							else {listComponent.setBackground(new Color(107, 107, 107, 255));}
						}
					});
					listComponent.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseExited(MouseEvent e) {
							if(selectedIndex == itFin) {listComponent.setBackground(new Color(124, 153, 38, 255));}
							else {listComponent.setBackground(new Color(64, 64, 64, 255));}
						}
					});
					listComponent.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							selectedIndex = itFin;
							updateListSelectedColor();
							Thread t1 = new Thread(new Runnable()
							{
								public void run() {
									constructInformationPanel();
								}
							});
							t1.start();
							
							listComponent.setBackground(new Color(124, 153, 38, 255));
						}
					});
					
					JLabel bikeNameLabel = new JLabel("     "+searchBikeArrayList.get(x).getBikeName());
					JLabel bikeSerialLabel = new JLabel("     "+ searchBikeArrayList.get(x).getSerialNumber());
					JLabel bikeDateLabel = new JLabel("     "+ searchBikeArrayList.get(x).getRegisterDate().getDayOfMonth() +"."+ searchBikeArrayList.get(x).getRegisterDate().getMonthValue() +"."+ searchBikeArrayList.get(x).getRegisterDate().getYear());
					listComponent.add(bikeNameLabel);
					listComponent.add(bikeSerialLabel);
					listComponent.add(bikeDateLabel);
					savedListComponents.add(listComponent);
					
					
					for(Component c : listComponent.getComponents()) {
						JLabel label = (JLabel) c;
						label.setMaximumSize(new Dimension((int) Math.ceil(screenWidth * 0.31614/tableDivideNumber), Integer.MAX_VALUE));
						label.setForeground(Color.WHITE);
						label.setHorizontalAlignment(JLabel.LEFT);
						label.setVerticalAlignment(JLabel.CENTER);
						label.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(15 * (screenWidth / 1920))));
					}
					
				}
				it++;
				bikeListPanel.add(listComponent);
				bikeListPanel.add(Box.createRigidArea(new Dimension(0, (int) Math.ceil(screenHeight * 0.00462))));
			}
		bikeList.setViewportView(bikeListPanel);
	}
}
