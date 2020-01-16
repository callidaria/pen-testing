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
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;



public class NewArticleView extends JFrame {
	
	private InventoryView inventoryView;

	public NewArticleView() {
		
		//GUI
		setTitle("Neuer Artikel");
		setSize(500,100);
		setLocationRelativeTo(null);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Textarea
		JTextArea tkategorie = new JTextArea(1,5);
		JTextArea tprodukt = new JTextArea(1,5);
		JTextArea tanzahl = new JTextArea(1,5);
		JTextArea tgewicht = new JTextArea(1,5);
		JTextArea tpreis = new JTextArea(1,5);
		JTextArea tid = new JTextArea(1,5);
		
		//Label
		JLabel lkategorie = new JLabel("KategorieID");
		JLabel lprodukt = new JLabel("Produktname");
		JLabel lanzahl = new JLabel("Anzahl");
		JLabel lgewicht = new JLabel("Gewicht");
		JLabel lpreis = new JLabel("Preis");
		JLabel lid = new JLabel("ID");
		
		//Button
		JButton button = new JButton ("Hinzufügen");
		button.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				String skategorie = tkategorie.getText();
				String produkt = tprodukt.getText();
				String sanzahl = tanzahl.getText();
				String sgewicht = tgewicht.getText();
				String spreis = tpreis.getText();
				String sid = tid.getText();
				
				int anzahl = Integer.parseInt(sanzahl);
				int gewicht = Integer.parseInt(sgewicht);
				int preis = Integer.parseInt(spreis);
				int id = Integer.parseInt(sid);
				int kategorie = Integer.parseInt(skategorie);
				
				VirtualStorage vs = new VirtualStorage();
                try {
					vs.addProduct(id, produkt, anzahl, gewicht, preis, kategorie);
					inventoryView.refresh();
					setVisible(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		

		
		Container pane = getContentPane();
		
		
		
		pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
       
        c.gridx = 0;
        c.gridy = 0;
		pane.add(lkategorie,c);
		
		c.gridx = 1;
	    c.gridy = 0;
		pane.add(tkategorie,c);
		
		c.gridx = 2;
	    c.gridy = 0;
		pane.add(lid,c);
		
		c.gridx = 3;
	    c.gridy = 0;
		pane.add(tid,c);
		
		c.gridx = 4;
	    c.gridy = 0;
		pane.add(lprodukt,c);
		
		c.gridx = 5;
	    c.gridy = 0;
		pane.add(tprodukt,c);
		
		c.gridx = 0;
	    c.gridy = 1;
		pane.add(lanzahl,c);
		
		c.gridx = 1;
	    c.gridy = 1;
		pane.add(tanzahl,c);
		
		c.gridx = 2;
	    c.gridy = 1;
		pane.add(lgewicht,c);
		
		c.gridx = 3;
	    c.gridy = 1;
		pane.add(tgewicht,c);
		
		c.gridx = 4;
	    c.gridy = 1;
		pane.add(lpreis,c);
		
		c.gridx = 5;
	    c.gridy = 1;
		pane.add(tpreis,c);
		
		c.ipadx=40;
		c.gridwidth = 2;
		c.gridx = 2;
	    c.gridy = 2;
		pane.add(button,c);
	
	}

	public void setInventoryView(InventoryView inventoryView) {
		this.inventoryView = inventoryView;
	}

	public void setFrameVisible(boolean b) {
		setVisible(b);
	}
	
}