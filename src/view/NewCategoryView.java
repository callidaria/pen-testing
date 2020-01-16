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

public class NewCategoryView extends JFrame {
	private VirtualStorage vs;
	public NewCategoryView() {
		//GUI
		setTitle("New Category");
		setSize(400,150);
		setLocationRelativeTo(null);
				
				
				
		//Label
		JLabel lid = new JLabel("ID");
		JLabel lname = new JLabel("Categoryname");
		JLabel ldesc = new JLabel("Description");
			
		//Textarea
		JTextArea taid = new JTextArea(1,5);
		JTextArea taname = new JTextArea(1,5);
		JTextArea tadesc = new JTextArea(1,5);
				
		//Button
		JButton button = new JButton ("Save");
		button.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = taid.getText();
				String name = taname.getText();
				String desc = tadesc.getText();
		        System.out.println("contents = " + id + name + desc);
			}
		});
		
		//Layout
				
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
		pane.add(lid);
		pane.add(taid);
		pane.add(lname);
		pane.add(taname);
		pane.add(ldesc);
		pane.add(tadesc);
		pane.add(button);
	
	}
	public void setVirtualStorage(VirtualStorage vs) {
		this.vs = vs;
	}
}
