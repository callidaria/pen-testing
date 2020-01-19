package view;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import basic.Category;
import controller.VirtualStorage;
import view.components.SelectionItem;

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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * wird aufgerufen, wenn in der InventoryView auf den Button neuen Eintrag erstellen geklickt wird.
 * Nachdem alle Felder ausgefüllt sind und auf den speicherbutton geklickt wurde, wird der neue Eintrag der datenbank hinzugefügt
 */

public class NewArticleView extends JFrame {
	
	private InventoryView inventoryView;
	private VirtualStorage vs;

	/**
	 * Konstruktor der NewArticleView, nimmt einen neuen VirtualStorage eintrag als Parameter um diesen nach Beendigung hinzuzufügen
	 */
	public NewArticleView(VirtualStorage vs) {
		this.vs=vs;
		
		//GUI
		setTitle("Neuer Artikel");
		setSize(500,100);
		setResizable(false);
		setLocationRelativeTo(null);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Textfields - Hier werden alle Parameter des zu erstellenden Artikels eingetragen
		//JTextField tKategorie = new JTextField(20);
		JTextField tName = new JTextField(20);
		JTextField tAnzahl = new JTextField(20);
		JTextField tGewicht = new JTextField(20);
		JTextField tPreis = new JTextField(20);
		JTextField tUid = new JTextField(20);
		
		//Label beschriftung für die Textfields
		JLabel lKategorie = new JLabel("KategorieID:");
		JLabel lName = new JLabel("Produktname:");
		JLabel lAnzahl = new JLabel("Anzahl:");
		JLabel lGewicht = new JLabel("Gewicht:");
		JLabel lPreis = new JLabel("Preis:");
		JLabel lUid = new JLabel("ID:");
		
		JComboBox<SelectionItem> takategorie = new JComboBox<SelectionItem>(categoriesToItem());
		takategorie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SelectionItem item = (SelectionItem) takategorie.getSelectedItem();
			}
		});
		
		//Button hier wird der neue Eintrag erstellt und die Daten an die Datenbank weitergeleitet
		JButton bAdd = new JButton ("Hinzufügen");
		bAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Auslesen aller Textareas als Strings
				String produkt = tName.getText();
				String sanzahl = tAnzahl.getText();
				String sgewicht = tGewicht.getText();
				String spreis = tPreis.getText();
				String sid = tUid.getText();
				
				//Konvertieren in Integer
				int anzahl = Integer.parseInt(sanzahl);
				int gewicht = Integer.parseInt(sgewicht);
				int preis = Integer.parseInt(spreis);
				int id = Integer.parseInt(sid);
                
				//Ausführen der hinzufügen Methode aus dem VirtualStorage
				try {
					int adding=vs.addProduct(id, produkt, anzahl, gewicht, preis, ((SelectionItem)takategorie.getSelectedItem()).getId());
					if (adding==0) {
						JOptionPane.showMessageDialog(getContentPane(),"Eintrag wurde hinzugefügt","Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
						inventoryView.refresh();
						setVisible(false);
					} else if (adding==-1) {
						JOptionPane.showMessageDialog(getContentPane(),"Produktname bereits vergeben oder zu lang","Fehler", JOptionPane.INFORMATION_MESSAGE);
					} else if (adding==-2) {
						JOptionPane.showMessageDialog(getContentPane(),"ID bereits vergeben oder unzulässig","Fehler", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(getContentPane(),"keine negativen Werte zulässig","Fehler", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		

		//Layout Hier wird die Reihenfolge aller Labels, Textareas und Buttons festgelegt
		
		Container pane = getContentPane();
		pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,5,0,5);
        
        //gridx für horizontale koordinate
        //gridy für vertikale koordinate
        c.gridx = 0;
        c.gridy = 0;
		pane.add(lKategorie,c);
		
		c.gridx = 1;
	    c.gridy = 0;
		pane.add(takategorie,c);
		
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

	/**
	 * setter für inventoryView
	 */
	public void setInventoryView(InventoryView inventoryView) {
		this.inventoryView = inventoryView;
	}
	public void setFrameVisible(boolean b) {
		setVisible(b);
	}
	private SelectionItem[] categoriesToItem() {
		List<Category> categories = vs.getAllCategories();
		List<SelectionItem> categoryNames = new ArrayList<SelectionItem>();
		for (int i=0; i<categories.size();i++) {
			categoryNames.add(new SelectionItem(categories.get(i).getUID(), categories.get(i).getName()));
		}
		SelectionItem[] searchSelectables = categoryNames.toArray(new SelectionItem[categories.size()]);
		return searchSelectables;
	}
}