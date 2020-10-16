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
import db.DataAccessException;
import model.Category;

public class ManageCategoryUI {
	
	double screenWidth, screenHeight;
	JFrame frame;

	CategoryCtr categoryCtr;	
	//labels and fields
	JLabel process;
    JLabel nameLabel;
    JLabel colorLabel;
    JTextField nameField;
    JComboBox colorField;
	private JTable table;
	//partId, partName, usedPrice, newPrice, categoryId
	
	private ArrayList<Category> categoryList;
	
    JButton createButton = new JButton("CREATE");
    JButton modifyButton = new JButton("MODIFY");
    JButton deleteButton = new JButton("DELETE");
    JButton saveButton = new JButton("SAVE");

	
	public ManageCategoryUI() throws DataAccessException {
		frame = new JFrame("Manage categories");
		frame.setSize(1280, 720);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		categoryCtr = new CategoryCtr();
		
		
		
		
		
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
		createButton = new JButton("Create New Category");
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
		
		
		modifyButton = new JButton("Modify selected category");
		modifyButton.setBounds(500, 260, 130, 30);
		modifyButton.setEnabled(false);
		modifyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				process.setText("Modifying");
				resetFields();
				nameField.setText((String) table.getValueAt(table.getSelectedRow(), 1));
				colorField.setSelectedIndex(0);
				saveButton.setEnabled(true);
			}
		});
		
		frame.getContentPane().add(modifyButton);
		
		deleteButton = new JButton("Delete selected category");
		deleteButton.setBounds(500, 360, 130, 30);
		deleteButton.setEnabled(false);
		deleteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				process.setText("DELETING...");
				resetFields();
				Category category;
				try {
					Object o = table.getValueAt(table.getSelectedRow(), 0);
					int id = Integer.parseInt(o.toString());
					category = categoryCtr.getCategoryById(id);
					categoryCtr.deleteCategory(category);
					initializeCategoryList();
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
				Category category;
				
				try { 
					String catName = nameField.getText();
					category = new Category(catName, (String) colorField.getSelectedItem());
					if (process.getText() == "Creating") {
						category = categoryCtr.newCategory(category);
						process.setText("Created");
					}
					else if (process.getText() == "Modifying") {
						Object o = table.getValueAt(table.getSelectedRow(), 0);
						int id = Integer.parseInt(o.toString());
						category.setCategoryId(id);
						category = categoryCtr.updateCategory(category);
						process.setText("Modified");
					}
					else {
						process.setText("");
					}
					resetFields();
					initializeCategoryList();
					saveButton.setEnabled(false);
				} catch (DataAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					process.setText("FAILED");
				}
			}
		});

		frame.getContentPane().add(saveButton);

		initializeCategoryList();	
		frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
		frame.setVisible(true);
	}
	
	private void resetFields() {
		nameField.setText("");
		colorField.setSelectedIndex(0);
	}
	

	//initialize class variables
	protected void initializeFields() throws DataAccessException {
		process = new JLabel();
	    nameLabel  = new JLabel("CATEGORY NAME");
	    colorLabel = new JLabel("COLOR");
	    nameField = new JTextField();
	    colorField = new JComboBox(new String[] {"black", "green", "red", "pink", "blue", "yellow"});
  
		process.setForeground(Color.BLUE);
	    process.setBounds(700, 100, 200, 230);
	    process.setSize(200, 30);
	    process.setFont(new Font("Serif", Font.PLAIN, 16));
	    nameLabel.setBounds(700, 220, 200, 30);
    	colorLabel.setBounds(700, 430, 200, 30);
        nameField.setBounds(900, 220, 150, 30);
        colorField.setBounds(900, 430, 150, 30);
        frame.getContentPane().add(process);
        frame.getContentPane().add(nameLabel);
        frame.getContentPane().add(nameField);
        frame.getContentPane().add(colorLabel);
        frame.getContentPane().add(colorField);

	}
	


	public void initializeCategoryList() throws DataAccessException {
		categoryList = (ArrayList<Category>) categoryCtr.getAllCategories();
		String[] columnNames = new String[]{"Category Id", "Category Name", "Color"};
		DefaultTableModel model = new DefaultTableModel();   
        model.setColumnIdentifiers(columnNames);
        for (Category category: categoryList) {
			model.addRow(new Object[]{category.getCategoryId(), category.getCategoryName(), category.getColour()});
		}
        table.setModel(model);
	}
	
}
