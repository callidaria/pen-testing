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

public class Category extends JFrame {
	public Category() {
		//GUI
		setTitle("Kategorie");
		setSize(300,200);
		setLocationRelativeTo(null);
		
		//Menubar
		JMenuBar menu = new JMenuBar();
		JMenu datei = new JMenu("Menü");
		JMenuItem bestand = new JMenuItem("Bestand");
		
			bestand.addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Front bestand = new Front();
				bestand.setVisible(true);
			
			}	
		});	
		
		//Button
		JButton button = new JButton ("New Category");
		button.addActionListener(new ActionListener() { 
		
			@Override
			public void actionPerformed(ActionEvent e) {
				NewCategory neu = new NewCategory();
				neu.setVisible(true);
			}
		});	
		
		
		
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
			
			
		//Layout
		menu.add(datei);
		datei.add(bestand);
		setJMenuBar (menu);
		
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
		
		pane.add(table.getTableHeader());
		pane.add(table);
		pane.add(button);
		
		
	}
	
	
}
