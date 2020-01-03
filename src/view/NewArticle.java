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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewArticle extends JFrame {
	
	public NewArticle() {
		
		//GUI
		setTitle("New Article");
		setSize(400,100);
		setLocationRelativeTo(null);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Textarea
		JTextArea ta1 = new JTextArea("          ");
		JTextArea ta2 = new JTextArea("          ");
		JTextArea ta3 = new JTextArea("          ");
		JTextArea ta4 = new JTextArea("          ");
		
		//Label
		JLabel l1 = new JLabel("Regal");
		JLabel l2 = new JLabel("Platz");
		JLabel l3 = new JLabel("Produktname");
		JLabel l4 = new JLabel("Anzahl");
		
		//Button
		JButton button = new JButton ("Save");
		//public void actionPerformed(ActionEvent e) {}
		
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
		pane.add(l1);
		pane.add(ta1);
		pane.add(l2);
		pane.add(ta2);
		pane.add(l3);
		pane.add(ta3);
		pane.add(l4);
		pane.add(ta4);
		pane.add(button);
	
	}
	
}