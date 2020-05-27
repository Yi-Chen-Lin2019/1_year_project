/*Code by Milek Radoslaw 2020.05*/

package gui;

import model.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.BikeCtr;
import db.DataAccessException;

public class UpdateBikeUI {
	
	double screenWidth, screenHeight;
	JFrame frame;
	JPanel contentPanel;
	ImageGen checkBoxImage;
	
	HashSet<Category> categories = new HashSet<>();	
	int[] checkBox1Option = {0, 0, 0};
	ArrayList<Integer> checkBoxRepairItems;
	
	JList<Part> availablePartsJList, usedPartsJList;
	JScrollPane availablePartsListScroll, usedPartsListScroll;
	ArrayList<Part> availablePartsList, usedPartsList;
	BikeCtr bikeCtr = new BikeCtr();

	
	public UpdateBikeUI(JFrame frame, JPanel contentPanel, double screenWidth, double screenHeight, Bike bike) throws DataAccessException {
		this.frame = frame;
		this.contentPanel = contentPanel;
		contentPanel.removeAll();
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.bike = bike;
		
		
		for (Repair repair : bike.getRepairList().getAllRepairs()) {
			categories.add(repair.getRepairItem().getCategory());
		}
		
		initializeImages();
		initialize();
		frame.repaint();
	}
	
	
	private void initializeImages() {
		checkBoxImage = new ImageGen(4, 1, "/checkBox.png", (int) Math.ceil(screenWidth*0.01302), (int) Math.ceil(screenWidth*0.01302));
	}
	
	private void initialize() {
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
		
		
		/**RepairList panel*/
		JPanel repairListPanel = new JPanel();
		repairListPanel.setLayout(null);
		repairListPanel.setBackground(Color.WHITE);
		repairListPanel.setBounds(0, (int) Math.ceil(screenHeight*0.04629), (int) Math.ceil(screenWidth*0.40989), (int) Math.ceil(screenHeight*0.74351));
        contentPanel.add(repairListPanel);
        
        JLabel repairChecklistText = new JLabel("Repair Checklist:");
        repairChecklistText.setForeground(Color.BLACK);
        repairChecklistText.setHorizontalAlignment(JLabel.LEFT);
        repairChecklistText.setVerticalAlignment(JLabel.BOTTOM);
        repairChecklistText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20*(screenWidth/1920))));
        repairChecklistText.setBounds(0, 0, (int) Math.ceil(screenWidth*0.07708), (int) Math.ceil(screenHeight*0.02314));
		repairListPanel.add(repairChecklistText);
		
		
		JPanel categoryRepairItems = new JPanel();
		categoryRepairItems.setLayout(null);
		categoryRepairItems.setBounds(0, (int) Math.ceil(screenHeight*0.06481), (int) Math.ceil(screenWidth*0.40989), (int) Math.ceil(screenHeight*0.67870));
		repairListPanel.add(categoryRepairItems);
		
		//Creating same amount of JLabel as categories
		ArrayList<JLabel> categoryLabel = new ArrayList<>();
		for(Category i : categories) {
			categoryLabel.add(new JLabel(i.getCategoryName()));
		}
		//Creating same amount of JPanels as categories
		ArrayList<JPanel> categoryPanel = new ArrayList<>();
		for(Category i : categories) {
			JPanel temp = new JPanel();
			temp.setLayout(null);
			temp.setBackground(new Color(245, 245, 245, 255));
			temp.setBounds(0, 0, (int) Math.ceil(screenWidth*0.40989), (int) Math.ceil(screenHeight*0.67870));
			categoryPanel.add(temp);
		}
		
		//Automatic generation of Category buttons
		int it = 0;
		int tempWidth = 0;
		for(JLabel i : categoryLabel) {
			//Calculating text size width
			BufferedImage tempImage = new BufferedImage((int)Math.round(screenWidth/16), (int)Math.round(screenHeight/16), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = tempImage.createGraphics();
			g.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(10*(screenWidth/1920))));
			Rectangle2D r = g.getFontMetrics().getStringBounds(i.getText(), g);
			
			i.setForeground(Color.WHITE);
			i.setBackground(new Color(64, 64, 64, 255));
			i.setOpaque(true);
	        i.setHorizontalAlignment(JLabel.CENTER);
	        i.setVerticalAlignment(JLabel.CENTER);
	        i.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(20*(screenWidth/1920))));
	        i.setBounds((int) Math.ceil(screenWidth*(30+tempWidth+it*10)/1920), (int) Math.ceil(screenHeight*0.03240), (int) Math.ceil(screenWidth*((r.getWidth()+10)/1920)), (int) Math.ceil(screenHeight*0.02314));
	        i.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					i.setBackground(new Color(107,107,107,255));
				}
			});
	        i.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseExited(MouseEvent e) {
					i.setBackground(new Color(64,64,64,255));
				}
			});
	        final int finalIt = it;
	        i.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					categoryRepairItems.removeAll();
					categoryRepairItems.add(categoryPanel.get(finalIt));
					frame.repaint();
				}
			});
	        repairListPanel.add(i);
	        categoryRepairItems.add(categoryPanel.get(it));
	        
			it++;
			tempWidth = tempWidth + (int) Math.round(r.getWidth()+10);
		}
		
		int it2 = 0;
		int it4 = 0;
		//Automatic generation of RepairItems from Category
		for(JPanel i : categoryPanel) {
			int it3 = 0;
			int tempHeight = 0;
			ArrayList<JLabel> repairItemLabels = new ArrayList<>();
			for(RepairItem ri : categories.get(it2).getRepairItems()) {
				int checkBoxChoice = 0;
				checkBoxRepairItems.add(checkBoxChoice);
				JLabel temp2 = new JLabel();
				final int it4Fin = it4;
				JLabel temp = new JLabel("   "+ri.getName());
				temp.setForeground(Color.BLACK);
				temp.setBackground(new Color(200,200,200,255));
				temp.setOpaque(true);
				temp.setHorizontalAlignment(JLabel.LEFT);
				temp.setVerticalAlignment(JLabel.BOTTOM);
				temp.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(20*(screenWidth/1920))));
				temp.setBounds(0, (int) Math.ceil(screenHeight*(10+tempHeight+it3*10)/1080), (int) Math.ceil(screenWidth*0.40989), (int) Math.ceil(screenHeight*0.02314));
				temp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						i.remove(temp);
						i.remove(temp);
						temp.setBackground(new Color(107,107,107,255));
						i.add(temp2);
						i.add(temp);
						frame.repaint();
					}
				});
				temp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseExited(MouseEvent e) {
						i.remove(temp);
						i.remove(temp);
						temp.setBackground(new Color(200,200,200,255));
						i.add(temp2);
						i.add(temp);
						frame.repaint();
					}
				});
				temp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						checkBoxRepairItems.set(it4Fin, (checkBoxRepairItems.get(it4Fin)+1));
						if(checkBoxRepairItems.get(it4Fin)==4) {checkBoxRepairItems.set(it4Fin, 0);}
						temp2.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(checkBoxRepairItems.get(it4Fin))));
					}
				});
				temp2.setHorizontalAlignment(JLabel.CENTER);
				temp2.setVerticalAlignment(JLabel.CENTER);
				temp2.setBounds((int) Math.ceil(screenWidth*0.34479), (int) Math.ceil(screenHeight*(10+tempHeight+it3*10)/1080), (int) Math.ceil(screenHeight*0.02314), (int) Math.ceil(screenHeight*0.02314));
				temp2.setIcon(new ImageIcon(checkBoxImage.getFrameArray().get(0)));
				
				i.add(temp2);
				i.add(temp);
				
				it3++;
				it4++;
				tempHeight = tempHeight + 25+10;
			}
			it2++;
		}
		
		
		/**Note Panel*/
		JPanel notePanel = new JPanel();
		notePanel.setLayout(null);
		notePanel.setBackground(Color.WHITE);
		notePanel.setBounds((int) Math.ceil(screenWidth*0.42760), (int) Math.ceil(screenHeight*0.04629), (int) Math.ceil(screenWidth*0.2), (int) Math.ceil(screenHeight*0.74351));
        contentPanel.add(notePanel);
        
        //Creates a stroke around a note
        JLabel noteStroke = new JLabel();
        noteStroke.setBounds(0, 0, (int) Math.ceil(screenWidth*0.2), (int) Math.ceil(screenHeight*0.74351));
        Dimension arcs = new Dimension(15, 15);
        BufferedImage noteStrokeImage = new BufferedImage((int) Math.ceil(screenWidth*0.2), (int) Math.ceil(screenHeight*0.74351), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = noteStrokeImage.createGraphics();
		int width = noteStroke.getWidth();
        int height = noteStroke.getHeight();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        float dash1[] = {(0.02314f*(float) screenHeight)};
        final BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
        g.setStroke(dashed);

     // Draws the rounded panel with borders.
     g.setColor(Color.BLACK);
     g.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);// paint

        noteStroke.setHorizontalAlignment(JLabel.CENTER);
        noteStroke.setVerticalAlignment(JLabel.CENTER);
        noteStroke.setIcon(new ImageIcon(noteStrokeImage));
        notePanel.add(noteStroke);
        
        JTextArea noteTextField = new JTextArea();
        noteTextField.setForeground(Color.BLACK);
        noteTextField.setLineWrap(true);
        noteTextField.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(18*(screenWidth/1920))));
        noteTextField.setBounds(2, 2, (int) Math.ceil(screenWidth*0.2)-4, (int) Math.ceil(screenHeight*0.74351)-4);
		notePanel.add(noteTextField);
		
		/** Right Panel*/
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(null);
		rightPanel.setBackground(Color.WHITE);
		rightPanel.setBounds((int) Math.ceil(screenWidth*0.64218), (int) Math.ceil(screenHeight*0.04629), (int) Math.ceil(screenWidth*0.30520), (int) Math.ceil(screenHeight*0.74351));
        contentPanel.add(rightPanel);
		
        //Parts menu
        JLabel availablePartsText = new JLabel("Available Parts");
        availablePartsText.setForeground(Color.BLACK);
        availablePartsText.setHorizontalAlignment(JLabel.CENTER);
        availablePartsText.setVerticalAlignment(JLabel.BOTTOM);
        availablePartsText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20*(screenWidth/1920))));
        availablePartsText.setBounds((int) Math.ceil(screenWidth*0.11770), (int) Math.ceil(screenHeight*0.008), (int) Math.ceil(screenWidth*0.07031), (int) Math.ceil(screenHeight*0.02314));
		rightPanel.add(availablePartsText);
        
		availablePartsListScroll = new JScrollPane();
		availablePartsListScroll.setBounds((int) Math.ceil(screenWidth*0.01562), (int) Math.ceil(screenHeight*0.03240), (int) Math.ceil(screenWidth*0.27395), (int) Math.ceil(screenHeight*0.23148));
		rightPanel.add(availablePartsListScroll);
		availablePartsJList = new JList<Part>();
		availablePartsListScroll.setViewportView(availablePartsJList);
		
		JButton addNewPartBtn = new JButton("Add New Part");
		addNewPartBtn.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(18*(screenWidth/1920))));
		addNewPartBtn.setHorizontalAlignment(JButton.CENTER);
		addNewPartBtn.setBounds((int) Math.ceil(screenWidth*0.06458), (int) Math.ceil(screenHeight*0.27314), (int) Math.ceil(screenWidth*0.07031), (int) Math.ceil(screenHeight*0.02314));
//		addNewPartBtn.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {if(availablePartsJList.getSelectedIndex()>=0) {usedPartsList.add((availablePartsJList.getModel().getElementAt(availablePartsJList.getSelectedIndex()))); 
//			updateUsedParts();}
//			}
//		});
		rightPanel.add(addNewPartBtn);
        
		JButton addUsedPartBtn = new JButton("Add Used Part");
		addUsedPartBtn.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(18*(screenWidth/1920))));
		addUsedPartBtn.setHorizontalAlignment(JButton.CENTER);
		addUsedPartBtn.setBounds((int) Math.ceil(screenWidth*0.17031), (int) Math.ceil(screenHeight*0.27314), (int) Math.ceil(screenWidth*0.07031), (int) Math.ceil(screenHeight*0.02314));
//		addUsedPartBtn.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {if(availablePartsJList.getSelectedIndex()>=0) {usedPartsList.add((availablePartsJList.getModel().getElementAt(availablePartsJList.getSelectedIndex()))); 
//			updateUsedParts();}
//			}
//		});
		rightPanel.add(addUsedPartBtn);
		
		JButton removeUsedPart = new JButton("Remove Part");
		removeUsedPart.setFont(new Font("Arial", Font.PLAIN, (int) Math.round(18*(screenWidth/1920))));
		removeUsedPart.setHorizontalAlignment(JButton.CENTER);
		removeUsedPart.setBounds((int) Math.ceil(screenWidth*0.11718), (int) Math.ceil(screenHeight*0.34259), (int) Math.ceil(screenWidth*0.07031), (int) Math.ceil(screenHeight*0.02314));
//		removeUsedPart.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {if(availablePartsJList.getSelectedIndex()>=0) {usedPartsList.add((availablePartsJList.getModel().getElementAt(availablePartsJList.getSelectedIndex()))); 
//			updateUsedParts();}
//			}
//		});
		rightPanel.add(removeUsedPart);
        
		usedPartsListScroll = new JScrollPane();
		usedPartsListScroll.setBounds((int) Math.ceil(screenWidth*0.01562), (int) Math.ceil(screenHeight*0.375), (int) Math.ceil(screenWidth*0.27395), (int) Math.ceil(screenHeight*0.23148));
		rightPanel.add(usedPartsListScroll);
		usedPartsJList = new JList<Part>();
		availablePartsListScroll.setViewportView(usedPartsJList);
        
		JLabel usedPartsText = new JLabel("Used Parts");
		usedPartsText.setForeground(Color.BLACK);
		usedPartsText.setHorizontalAlignment(JLabel.CENTER);
		usedPartsText.setVerticalAlignment(JLabel.BOTTOM);
		usedPartsText.setFont(new Font("Calibri", Font.PLAIN, (int) Math.round(20*(screenWidth/1920))));
		usedPartsText.setBounds((int) Math.ceil(screenWidth*0.11770), (int) Math.ceil(screenHeight*0.61574), (int) Math.ceil(screenWidth*0.07031), (int) Math.ceil(screenHeight*0.02314));
		rightPanel.add(usedPartsText);
        

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
		acceptBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				
//				Bike bike = new Bike(serialNumber, gender, bikeName, isExternalGear)
//				bikeCtr.updateBike(bike);		
				//navigate somewhere
				//
			}

			private String getGender() {
				String gender = "";
				//logic to get the gender
				
				return gender;
			}
		});
		
		rightPanel.add(acceptBtn);
		
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
		rightPanel.add(cancelBtn);
	}
	
}
