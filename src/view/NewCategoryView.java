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
import javax.swing.JTextField;

import controller.VirtualStorage;

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

/**
 * Erstellt eine neue kategorie und fügt sie der Datenbank hinzu
 * wird über den Button neue Kategorie erstellen aufgerufen
 */

public class NewCategoryView extends JFrame {
	private VirtualStorage vs;
	
	/**
	 * Konstruktor der NewCategoryView, wird über den Button aufgerufen
	 */
	public NewCategoryView() {
		//GUI
		setTitle("Neue Kategorie erstellen");
		setSize(400,150);
		setLocationRelativeTo(null);
				
				
				
		//Label
		JLabel lname = new JLabel("Kategoriename");
			
		//Textarea
		JTextField taname = new JTextField(15);
		
		
		//Button wird hier erstellt und an die Datenbank weitergeleitet
		JButton button = new JButton ("Save");
		button.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = taname.getText();
		        System.out.println("contents = "+ name);
			}
		});
		
		//Layout
				
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
		pane.add(lname);
		pane.add(taname);
		pane.add(button);
	
	}
	
	/**
	 * setter für VirtualStorage
	 */
	public void setVirtualStorage(VirtualStorage vs) {
		this.vs = vs;
	}
}
