package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controller.CategoryCtr;
import controller.PartCtr;
import db.DataAccessException;
import model.Category;
import model.Part;

public class ManagePartUI {
	
	double screenWidth, screenHeight;
	JFrame frame;

	PartCtr partCtr;	
	//labels and fields
	private JTextField search;
	JLabel process;
    JLabel usedPriceLabel;
    JLabel newPriceLabel;
    JLabel nameLabel;
    JLabel categoryLabel;
    JTextField nameField;
    JFormattedTextField usedPriceField;
    JFormattedTextField newPriceField;
    JComboBox categoryField;
	private JTable table;
	//partId, partName, usedPrice, newPrice, categoryId
	
	private ArrayList<Part> partList;
	
    JButton createButton = new JButton("CREATE");
    JButton modifyButton = new JButton("MODIFY");
    JButton deleteButton = new JButton("DELETE");
    JButton saveButton = new JButton("SAVE");

	
	public ManagePartUI() throws DataAccessException {
		frame = new JFrame("Manage parts");
		frame.setSize(1280, 720);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		partCtr = new PartCtr();
		
		
		/**Search, scroll */
		
		search = new JTextField();
		search.setText("Search");
		search.setColumns(1);
		search.setBackground(new Color(255, 255, 255));
		search.setForeground(Color.BLACK);
		search.setHorizontalAlignment(SwingConstants.LEFT);
		search.setFont(new Font("Tahoma", Font.PLAIN, 14));
		search.setBounds(64, 60, 200, 30);
		search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(search.getText().equals("Search")) 
				{
					search.setText(""); 
				}
			}
			});
		
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(search);
		
		JButton searchBtn = new JButton("Search");
		searchBtn.setBounds(272, 60, 70, 30);
		frame.getContentPane().add(searchBtn);
		searchBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {try {
				resetFields();
				initializePartList();
			} catch (DataAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
		});
		
		
		table = new JTable();
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	resetFields();
	            modifyButton.setEnabled(true);
	            deleteButton.setEnabled(true);
	        }
	    });
		table.setBounds(64, 100, 400, 500);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(64, 100, 400, 500);
		scrollPane.setVisible(true);
		frame.getContentPane().add(scrollPane);
		
		initializeFields();
		
		//buttons
		createButton = new JButton("Create New Part");
		createButton.setBounds(500, 160, 130, 30);
		createButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				process.setText("Creating");
				resetFields();
				saveButton.setEnabled(true);
			}
		});
		
		frame.getContentPane().add(createButton);
		
		
		modifyButton = new JButton("Modify selected part");
		modifyButton.setBounds(500, 260, 130, 30);
		modifyButton.setEnabled(false);
		modifyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				process.setText("Modifying");
				resetFields();
				nameField.setText((String) table.getValueAt(table.getSelectedRow(), 1));
				usedPriceField.setText(((Double) table.getValueAt(table.getSelectedRow(), 2)).toString());
				newPriceField.setText(((Double) table.getValueAt(table.getSelectedRow(), 3)).toString());
				categoryField.setSelectedIndex(0);
				saveButton.setEnabled(true);
			}
		});
		
		frame.getContentPane().add(modifyButton);
		
		deleteButton = new JButton("Delete selected part");
		deleteButton.setBounds(500, 360, 130, 30);
		deleteButton.setEnabled(false);
		deleteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				process.setText("DELETING...");
				resetFields();
				Part part;
				try {
					Object o = table.getValueAt(table.getSelectedRow(), 0);
					int id = Integer.parseInt(o.toString());
					part = partCtr.findPartById(id);
					partCtr.deletePart(part);
					initializePartList();
					process.setText("DELETED");
				} catch (DataAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					process.setText("FAILED TO DELETE");
				}
			}
		});

		frame.getContentPane().add(deleteButton);
		
		saveButton = new JButton("SAVE");
		saveButton.setBounds(800, 500, 130, 30);
		saveButton.setEnabled(false);
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Part part;
				
				try { 
					Category category = (Category)categoryField.getSelectedItem();
					Double usedPrice = Double.parseDouble(usedPriceField.getText());
					Double newPrice = Double.parseDouble(newPriceField.getText());
					part = new Part(nameField.getText(), usedPrice, newPrice, category);
					if (process.getText() == "Creating") {
						part = partCtr.insertPart(part);
						process.setText("Created");
					}
					else if (process.getText() == "Modifying") {
						Object o = table.getValueAt(table.getSelectedRow(), 0);
						int id = Integer.parseInt(o.toString());
						part.setId(id);
						part = partCtr.updatePart(part);
						process.setText("Modified");
					}
					resetFields();
					initializePartList();
					saveButton.setEnabled(false);
				} catch (DataAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					process.setText("FAILED");
				}
			}
		});

		frame.getContentPane().add(saveButton);

		initializePartList();	
		frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
		frame.setVisible(true);
	}
	
	private void resetFields() {
		nameField.setText("");
		usedPriceField.setText("");
		usedPriceField.setText("");
		newPriceField.setText("");
		categoryField.setSelectedIndex(0);
	}
	

	//initialize class variables
	protected void initializeFields() throws DataAccessException {
		process = new JLabel();
	    usedPriceLabel = new JLabel("USED PRICE");
	    newPriceLabel = new JLabel("NEW PRICE");
	    nameLabel  = new JLabel("PART NAME");
	    categoryLabel = new JLabel("PART'S CATEGORY");
	    nameField = new JTextField();
	    usedPriceField = new JFormattedTextField();
	    newPriceField = new JFormattedTextField();
	    categoryField = new JComboBox(new CategoryCtr().getAllCategories().toArray());

		process.setForeground(Color.BLUE);
	    process.setBounds(700, 100, 200, 230);
	    process.setSize(200, 30);
	    process.setFont(new Font("Serif", Font.PLAIN, 16));
	    nameLabel.setBounds(700, 220, 200, 30);
    	usedPriceLabel.setBounds(700, 290, 200, 30);
    	newPriceLabel.setBounds(700, 360, 200, 30);
    	categoryLabel.setBounds(700, 430, 200, 30);
        nameField.setBounds(900, 220, 150, 30);
        usedPriceField.setBounds(900, 290, 150, 30);
    	newPriceField.setBounds(900, 360, 150, 30);
        categoryField.setBounds(900, 430, 150, 30);
        frame.getContentPane().add(process);
        frame.getContentPane().add(nameLabel);
        frame.getContentPane().add(nameField);
        frame.getContentPane().add(usedPriceLabel);
        frame.getContentPane().add(usedPriceField);
        frame.getContentPane().add(newPriceLabel);
        frame.getContentPane().add(newPriceField);
        frame.getContentPane().add(categoryLabel);
        frame.getContentPane().add(categoryField);

    	categoryField.setBounds(900, 430, 150, 30);
    	categoryLabel.setBounds(700, 430, 200, 30);
	}
	


	public void initializePartList() throws DataAccessException {
		if (search.getText().equals("") || search.getText().equals("Search")) {
			partList = (ArrayList<Part>) partCtr.findAllParts();
		}
		else {
			partList = (ArrayList<Part>) partCtr.findPartByName(search.getText());
		}
		String[] columnNames = new String[]{"Part Id", "Part Name", "Used Price", "New Price", "Category"};
		DefaultTableModel model = new DefaultTableModel();   
        model.setColumnIdentifiers(columnNames);
        for (Part part : partList) {
			model.addRow(new Object[]{part.getId(), part.getName(), part.getUsedPrice(), part.getNewPrice(), part.getCategory().getCategoryName() });
		}
        table.setModel(model);
	}
	
}
