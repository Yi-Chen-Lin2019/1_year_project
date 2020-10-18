package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controller.CategoryCtr;
import controller.RepairItemCtr;
import db.DataAccessException;
import model.Category;
import model.RepairItem;

public class ManageRepairItemUI {
	
	double screenWidth, screenHeight;
	JFrame frame;

	RepairItemCtr repairItemCtr;	
	//labels and fields
	JLabel process;
    JLabel repairItemNameLabel;
    JLabel categoryLabel;
    JTextField repairItemNameField;
    JComboBox categoryField;
	private JTable table;
	//partId, partName, usedPrice, newPrice, categoryId
	
	private ArrayList<RepairItem> repairItemList;
	
    JButton createButton = new JButton("CREATE");
    JButton modifyButton = new JButton("MODIFY");
    JButton deleteButton = new JButton("DELETE");
    JButton saveButton = new JButton("SAVE");

	
	public ManageRepairItemUI() throws DataAccessException {
		frame = new JFrame("Manage repairs");
		frame.setSize(1280, 720);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		repairItemCtr = new RepairItemCtr();
		
		
		
		
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
		createButton = new JButton("Create New Repair Item");
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
				repairItemNameField.setText((String) table.getValueAt(table.getSelectedRow(), 1));
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
				RepairItem repairItem;
				try {
					Object o = table.getValueAt(table.getSelectedRow(), 0);
					int id = Integer.parseInt(o.toString());
					repairItem = repairItemCtr.getRepairItemById(id);
					repairItemCtr.deleteRepairItem(repairItem);
					initializeRepairItemList();
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
				RepairItem repairItem;
				
				try { 
					Category category = (Category)categoryField.getSelectedItem();
					
					
					if (process.getText() == "Creating") {
						repairItem = new RepairItem(category, repairItemNameField.getText(), "To be added");
						repairItem = repairItemCtr.newRepairItem(repairItem);
						process.setText("Created");
					}
					else {
						repairItem = new RepairItem(category, (String)table.getValueAt(table.getSelectedRow(), 1), "To be added");
						Object o = table.getValueAt(table.getSelectedRow(), 0);
						int id = Integer.parseInt(o.toString());
						repairItem.setRepairItemId(id);
						repairItem = repairItemCtr.updateRepairItem(repairItem);
						process.setText("Modified");
					}
					resetFields();
					initializeRepairItemList();
					saveButton.setEnabled(false);
				} catch (DataAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					process.setText("FAILED");
				}
			}
		});

		frame.getContentPane().add(saveButton);

		initializeRepairItemList();	
		frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
		frame.setVisible(true);
	}
	
	private void resetFields() {
		repairItemNameField.setText("");
		categoryField.setSelectedIndex(0);
	}
	

	//initialize class variables
	protected void initializeFields() throws DataAccessException {
		process = new JLabel();
	    repairItemNameLabel  = new JLabel("NAME");
	    categoryLabel = new JLabel("CATEGORY");
	    repairItemNameField = new JTextField();
	    categoryField = new JComboBox(new CategoryCtr().getAllCategories().toArray());

		process.setForeground(Color.BLUE);
	    process.setBounds(700, 100, 200, 230);
	    process.setSize(200, 30);
	    process.setFont(new Font("Serif", Font.PLAIN, 16));
	    repairItemNameLabel.setBounds(700, 220, 200, 30);
    	categoryLabel.setBounds(700, 290, 200, 30);
        repairItemNameField.setBounds(900, 220, 150, 30);
        categoryField.setBounds(900, 290, 150, 30);
        frame.getContentPane().add(process);
        frame.getContentPane().add(repairItemNameLabel);
        frame.getContentPane().add(repairItemNameField);
        frame.getContentPane().add(categoryLabel);
        frame.getContentPane().add(categoryField);

    	categoryField.setBounds(900, 430, 150, 30);
    	categoryLabel.setBounds(700, 430, 200, 30);
	}
	


	public void initializeRepairItemList() throws DataAccessException {
		repairItemList = (ArrayList<RepairItem>) repairItemCtr.getRepairItems();
		String[] columnNames = new String[]{"RepairItemId", "RepairItemName", "CategoryName"};
		DefaultTableModel model = new DefaultTableModel();   
        model.setColumnIdentifiers(columnNames);
        for (RepairItem repairItem : repairItemList) {
			model.addRow(new Object[]{repairItem.getRepairItemId(), repairItem.getName(), repairItem.getCategory().getCategoryName() });
		}
        table.setModel(model);
	}
	
}
