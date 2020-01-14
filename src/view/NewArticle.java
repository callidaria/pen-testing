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
		JTextArea tsektion = new JTextArea(1,5);
		JTextArea tplatz = new JTextArea(1,5);
		JTextArea tprodukt = new JTextArea(1,5);
		JTextArea tanzahl = new JTextArea(1,5);
		JTextArea tgewicht = new JTextArea(1,5);
		JTextArea tpreis = new JTextArea(1,5);
		JTextArea tid = new JTextArea(1,5);
		
		//Label
		JLabel lsektion = new JLabel("Sektion");
		JLabel lplatz = new JLabel("Platz");
		JLabel lprodukt = new JLabel("Produktname");
		JLabel lanzahl = new JLabel("Anzahl");
		JLabel lgewicht = new JLabel("Gewicht");
		JLabel lpreis = new JLabel("Preis");
		JLabel lid = new JLabel("ID");
		
		//Button
		JButton button = new JButton ("Save");
		button.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				String ssektion = tsektion.getText();
				String splatz = tplatz.getText();
				String produkt = tprodukt.getText();
				String sanzahl = tanzahl.getText();
				String sgewicht = tgewicht.getText();
				String spreis = tpreis.getText();
				String sid = tid.getText();
				
				int anzahl = Integer.parseInt(sanzahl);
				int gewicht = Integer.parseInt(sgewicht);
				int preis = Integer.parseInt(spreis);
				int id = Integer.parseInt(sid);
				int sektion = Integer.parseInt(ssektion);
				int platz = Integer.parseInt(splatz);
				
                //VirtualStorage.addProduct(produkt, anzahl, gewicht, preis, id, sektion, platz);
			}
		});
		

		
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
		pane.add(lsektion);
		pane.add(tsektion);
		pane.add(lplatz);
		pane.add(tplatz);
		pane.add(lid);
		pane.add(tid);
		pane.add(lprodukt);
		pane.add(tprodukt);
		pane.add(lanzahl);
		pane.add(tanzahl);
		pane.add(lgewicht);
		pane.add(tgewicht);
		pane.add(lpreis);
		pane.add(tpreis);
		
		pane.add(button);
	
	}
	
}