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
		setSize(500,100);
		setLocationRelativeTo(null);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Textarea
		JTextArea tregal = new JTextArea(1,5);
		JTextArea tplatz = new JTextArea(1,5);
		JTextArea tprodukt = new JTextArea(1,5);
		JTextArea tanzahl = new JTextArea(1,5);
		
		//Label
		JLabel lregal = new JLabel("Regal");
		JLabel lplatz = new JLabel("Platz");
		JLabel lprodukt = new JLabel("Produktname");
		JLabel lanzahl = new JLabel("Anzahl");
		
		//Button
		JButton button = new JButton ("Save");
		button.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				String eins = tregal.getText();
                System.out.println("contents = " + eins);
			}
		});
		
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
		pane.add(lregal);
		pane.add(tregal);
		pane.add(lplatz);
		pane.add(tplatz);
		pane.add(lprodukt);
		pane.add(tprodukt);
		pane.add(lanzahl);
		pane.add(tanzahl);
		pane.add(button);
	
	}
	
}