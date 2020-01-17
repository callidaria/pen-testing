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
import javax.swing.JTextField;

import controller.VirtualStorage;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;



public class NewArticleView extends JFrame {
	
	private InventoryView inventoryView;
	private VirtualStorage vs;

	public NewArticleView() {
		
		//GUI
		setTitle("Neuer Artikel");
		setSize(500,100);
		setLocationRelativeTo(null);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Textarea
		JTextField tKategorie = new JTextField(20);
		JTextField tName = new JTextField(20);
		JTextField tAnzahl = new JTextField(20);
		JTextField tGewicht = new JTextField(20);
		JTextField tPreis = new JTextField(20);
		JTextField tUid = new JTextField(20);
		
		//Label
		JLabel lKategorie = new JLabel("KategorieID:");
		JLabel lName = new JLabel("Produktname:");
		JLabel lAnzahl = new JLabel("Anzahl:");
		JLabel lGewicht = new JLabel("Gewicht:");
		JLabel lPreis = new JLabel("Preis:");
		JLabel lUid = new JLabel("ID:");
		
		//Button
		JButton bAdd = new JButton ("Hinzufügen");
		bAdd.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				String skategorie = tKategorie.getText();
				String produkt = tName.getText();
				String sanzahl = tAnzahl.getText();
				String sgewicht = tGewicht.getText();
				String spreis = tPreis.getText();
				String sid = tUid.getText();
				
				int anzahl = Integer.parseInt(sanzahl);
				int gewicht = Integer.parseInt(sgewicht);
				int preis = Integer.parseInt(spreis);
				int id = Integer.parseInt(sid);
				int kategorie = Integer.parseInt(skategorie);
                try {
					int adding=vs.addProduct(id, produkt, anzahl, gewicht, preis, kategorie);
					if(adding==0) {
						JOptionPane.showMessageDialog(getContentPane(),"Erfolgreich","Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
						inventoryView.refresh();
						setVisible(false);
					}else {
						JOptionPane.showMessageDialog(getContentPane(),"Fehler","Fehler", JOptionPane.INFORMATION_MESSAGE);
						
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		

		
		Container pane = getContentPane();
		
		
		
		pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,5,0,5);
        c.gridx = 0;
        c.gridy = 0;
		pane.add(lKategorie,c);
		
		c.gridx = 1;
	    c.gridy = 0;
		pane.add(tKategorie,c);
		
		c.gridx = 0;
	    c.gridy = 1;
		pane.add(lUid,c);
		
		c.gridx = 1;
	    c.gridy = 1;
		pane.add(tUid,c);
		
		c.gridx = 0;
	    c.gridy = 2;
		pane.add(lName,c);
		
		c.gridx = 1;
	    c.gridy = 2;
		pane.add(tName,c);
		
		c.gridx = 0;
	    c.gridy = 3;
		pane.add(lAnzahl,c);
		
		c.gridx = 1;
	    c.gridy = 3;
		pane.add(tAnzahl,c);
		
		c.gridx = 0;
	    c.gridy = 4;
		pane.add(lGewicht,c);
		
		c.gridx = 1;
	    c.gridy = 4;
		pane.add(tGewicht,c);
		
		c.gridx = 0;
	    c.gridy = 5;
		pane.add(lPreis,c);
		
		c.gridx = 1;
	    c.gridy = 5;
		pane.add(tPreis,c);
		
		c.ipadx=40;
		c.ipady=5;
		c.gridwidth = 2;
		c.gridx = 1;
	    c.gridy = 6;
		pane.add(bAdd,c);
		
		pack();
	}

	public void setInventoryView(InventoryView inventoryView) {
		this.inventoryView = inventoryView;
	}

	public void setFrameVisible(boolean b) {
		setVisible(b);
	}
	public void setVirtualStorage(VirtualStorage vs) {
		this.vs = vs;
	}
	
}