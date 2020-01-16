package view;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CategoriesView extends JFrame {
	public CategoriesView() {
		//GUI
		setTitle("Kategorie");
		setSize(500,600);
		setLocationRelativeTo(null);
		
		//Menubar
		JMenuBar menu = new JMenuBar();
		JMenu datei = new JMenu("Menü");
		JMenuItem bestand = new JMenuItem("Bestand");
		
			bestand.addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				InventoryView bestand = new InventoryView();
				bestand.setVisible(true);
			
			}	
		});	
		
		//Button
		JButton button = new JButton ("New Category");
		button.addActionListener(new ActionListener() { 
		
			@Override
			public void actionPerformed(ActionEvent e) {
				NewCategoryView neu = new NewCategoryView();
				neu.setVisible(true);
			}
		});
		
		JButton such = new JButton ("Los!");
		
		//Textarea
		JTextArea ta = new JTextArea(1,5);
		
		//Label
		JLabel suchen = new JLabel("Suchen:");
		
		//Table
		String[] columnNames = {"ID",
                "Category Name",
                "Description"};
			
		Object[][] data = {
			    {new Integer(1), "Pens", "Regular Pens"},
			    {new Integer(2), "Colored Pens", "Red, Blue, but not Black"},
			    {new Integer(3), "Big Pens", "All big pens"}
		};	
			
		JTable table = new JTable(data, columnNames);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);	
		
		table.addMouseListener(new MouseAdapter(){
			 public void mousePressed(MouseEvent mouseEvent) {
			        JTable table =(JTable) mouseEvent.getSource();
			        Point point = mouseEvent.getPoint();
			        int row = table.rowAtPoint(point);
			        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
			        	CategoryView category = new CategoryView();
						category.setVisible(true);
			        }
			    }	
		});
			
		//Layout
		menu.add(datei);
		datei.add(bestand);
		setJMenuBar (menu);
		
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
		
		pane.add(button);
		pane.add(suchen);
		pane.add(ta);
		pane.add(such);
		pane.add(scrollPane);
		
		
	}
	
	
}
