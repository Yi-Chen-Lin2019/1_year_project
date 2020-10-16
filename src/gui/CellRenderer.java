package gui;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import model.Part;
import model.UsedPart;

public class CellRenderer {
	private static CellRenderer cellRenderer;
	
	private CellRenderer() {}
	
	public static CellRenderer getInstance()
    {
        if (cellRenderer==null)
        {
        	cellRenderer = new CellRenderer();
        }
        return cellRenderer;
    }
	

	public class PartCellRenderer implements ListCellRenderer<Part> {

		private DefaultListCellRenderer dlcr;
		
		@Override
		public Component getListCellRendererComponent(JList<? extends Part> list, Part value, int index, boolean isSelected, boolean cellHasFocus) {
			dlcr = new DefaultListCellRenderer();
			String textToShow = value.getName();
			return dlcr.getListCellRendererComponent(list, textToShow, index, isSelected, cellHasFocus);
		}

	}
	
	public class UsedPartCellRenderer implements ListCellRenderer<UsedPart> {

		private DefaultListCellRenderer dlcr;
		
		@Override
		public Component getListCellRendererComponent(JList<? extends UsedPart> list, UsedPart value, int index, boolean isSelected, boolean cellHasFocus) {
			dlcr = new DefaultListCellRenderer();
			dlcr.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {}
				});
			String textToShow = "";
			if(value.getIsNew()) {textToShow = value.getPart().getName();}
			else {textToShow = value.getPart().getName();}
			return dlcr.getListCellRendererComponent(list, textToShow, index, isSelected, cellHasFocus);
		}

	}
	
	
}
