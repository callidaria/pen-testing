package view;

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListDataListener;

import basic.Category;
import basic.InventoryEntry;
import basic.Product;
import controller.VirtualStorage;
import view.components.SelectionItem;

/**
 * In der Klasse ArticleView wird ein ausgewählter Artikel genauer betrachtet.
 * Alles über diesen Artikel kann hier verändert werden und er kann gelöscht werden. Dies geschieht über Textareas die einfach editiert werden.
 * Oder durch hinzuaddieren bei der Anzahl.
 * Am Ende müssen die Änderungen gespeichert werden.
 */
public class ArticleView extends JFrame {
	
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;
	private VirtualStorage vs;
	private InventoryView inventoryView;
	public JComboBox<SelectionItem> selectorCategory;
	/**
	 * Konstruktor der ArticleView
	 * 
	 * @param vs, der MainVirtualStorage
	 */
	public ArticleView(VirtualStorage vs) {
		this.vs=vs;
		setSize(480,360);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Artikeldetailansicht");
	}
	
	/**
	 * Wird aufgerufen beim Doppelklick auf Artikel in der Tabelle, mit UID als parameter. Über diese Methode wird das gesamte Fenster angezeigt.
	 * @param UID, UID des Eintrages
	 */
	public void ShowArticleView(int UID) {
		setContentPane(new JPanel());
		InventoryEntry thisArticle = vs.getEntryByUID(UID);
		
		//GUI
		setTitle(thisArticle.product.getName());
		
		//TextArea
		JTextField tfPlace = new JTextField(thisArticle.getStringifiedUID(),20);
		JTextField tfCount = new JTextField(Integer.toString(thisArticle.product.getCount()),5);
		JTextField tfAddRemove = new JTextField("0",5);
		
		SelectionItem[] searchSelectables = categoriesToItem();
		
		DefaultComboBoxModel<SelectionItem> selectionModel = new DefaultComboBoxModel<SelectionItem>(searchSelectables);
		selectorCategory = new JComboBox<SelectionItem>(selectionModel);
		
		selectorCategory.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SelectionItem item = (SelectionItem) selectorCategory.getSelectedItem();
				System.out.println(item.getId()+":"+item.getName());
			}
		});
		int selectedIndex=0;
		
		for(int n=0;searchSelectables.length>n;n++) {
			if(thisArticle.product.category.getName()==searchSelectables[n].getName()) {
				System.out.println(thisArticle.product.category.getName()+"|"+searchSelectables[n].getName());
				break;
			}
			selectedIndex++;
		}
		System.out.println(selectedIndex);
		selectorCategory.setSelectedIndex(selectedIndex);
		JTextField tfPrize = new JTextField(Integer.toString(thisArticle.product.getPrize()),5);
		JTextField tfWeight = new JTextField(Integer.toString(thisArticle.product.getWeight()),5);
		JTextField tfName = new JTextField(thisArticle.product.getName(),5);
		
		//Labels zur beschriftung der Textfields
		JLabel lWeight = new JLabel("Gewicht in 1/10 gramm:");
		JLabel lPrize = new JLabel("Preis in cent:");
		JLabel lCategory = new JLabel("Kategorie:");
		JLabel lPlace = new JLabel("Platznummer:");
		JLabel lCount = new JLabel("Anzahl:");
		JLabel lAdd = new JLabel("+/˗:");
		JLabel lName = new JLabel("Name:");
		
		//buttons zum löschen des Artikels und Speichern der Änderungen, und addieren/subtrahieren der Anzahl
		JButton bAdd = new JButton ("＋");
		JButton bSubtract = new JButton ("˗");
		JButton bDelete = new JButton ("Artikel Löschen");
		JButton bSave = new JButton ("Änderungen Speichern");
		// Button zum Löschen eines Artikels, geschieht nach klicken des Buttons über eine Methode im VirtualStorage
		bDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirme = JOptionPane.showConfirmDialog(getContentPane(), "Bestätigen","Artikel löschen",JOptionPane.YES_NO_OPTION);
				if (confirme==0) {
					try {
						vs.deleteProduct(UID);
						JOptionPane.showMessageDialog(getContentPane(),"Artikel erfolgreich gelöscht","Artikel gelöscht", JOptionPane.INFORMATION_MESSAGE);
						setVisible(false);
						inventoryView.refresh();
						dispose();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(getContentPane(),"Unbekannter Fehler","Artikel gelöscht", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		bAdd.addActionListener(new ActionListener() {
			// Nach klicken des Buttons wird die Zahl die eingegeben wurde drauf addiert auf die Anzahl.	
			@Override
			public void actionPerformed(ActionEvent e) {
				//Textfield to String
				String eins = tfAddRemove.getText();
				String zwei = tfCount.getText();
				
				//String to Integer
				int add = Integer.parseInt(eins);
				int anzahl = Integer.parseInt(zwei);
				
				//Berechnung des Ergebnisses für das Textfield
				int sum = add+anzahl;
				
				//tfAddRemove wieder auf 0, sodass weitere adds ausgeführt werden können
				tfAddRemove.setText("0");
				
				//tfCount auf aktuellen lagerbestand
				tfCount.setText(Integer.toString(sum));
				//Aufruf der Changeamout methode aus dem VirtualStorage
				try {
					vs.changeAmountBy(UID, add);
					inventoryView.refresh();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		bSubtract.addActionListener(new ActionListener() {
			// Nach Buttonklick wird die zahl weg subtrahiert von der Anzahl
			@Override
			public void actionPerformed(ActionEvent e) {
				//Textfield to String
				String eins = tfAddRemove.getText();
				String zwei = tfCount.getText();
				
				//String to Integer
				int add = Integer.parseInt(eins);
				int anzahl = Integer.parseInt(zwei);
				
				//Berechnung des Ergebnisses für das Textfield
				int sum = anzahl-add;
				
				tfAddRemove.setText("0");
				tfCount.setText(Integer.toString(sum));
				//aufruf der changeamount methode aus dem VirtualStorage
				
				try {
					vs.changeAmountBy(UID, -add);
					inventoryView.refresh();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});
		bSave.addActionListener(new ActionListener() {
			// Durch den Buttonklick werden die Änderungen am artikel gespeichert, dies geschieht über eine Methode des VirtualStorage
			@Override
			public void actionPerformed(ActionEvent e) {
				//Jedes Textfield wird ausgelesen und wenn nötig in Integer umgewandelt
				String suid = tfPlace.getText();
				int uid = Integer.parseInt(suid);
				
				String splatz = tfPlace.getText();
				int platz = Integer.parseInt(splatz);
				
				String sanzahl = tfCount.getText();
				int anzahl = Integer.parseInt(sanzahl);
				
				SelectionItem skategorie = (SelectionItem) selectorCategory.getSelectedItem();
				int kategorie_id = skategorie.getId();
				
				String spreis = tfPrize.getText();
				int preis = Integer.parseInt(spreis);
				String sgewicht = tfWeight.getText();
				int gewicht = Integer.parseInt(sgewicht);
				String name =tfName.getText();
				
				InventoryEntry editedIE = new InventoryEntry(uid, new Product(name, anzahl, gewicht, preis, kategorie_id));
				//Methode zum ändern des Artikels wird aufgerufen
				int err_code=vs.replaceInventoryEntry(thisArticle.getUID(),uid,name,anzahl,gewicht,preis,kategorie_id);
				//Fehlerabfragen
				if (err_code==-1) JOptionPane.showMessageDialog(getContentPane(),"Keine negativen Zahlen als Anzahl, Gewicht oder Preis zulässig","Fehler", JOptionPane.INFORMATION_MESSAGE);
				else if (err_code==-2) JOptionPane.showMessageDialog(getContentPane(),"ID bereits vergeben oder unzulässig","Fehler", JOptionPane.INFORMATION_MESSAGE);
				else if (err_code==-3) JOptionPane.showMessageDialog(getContentPane(),"Name bereits vergeben oder zu lang","Fehler", JOptionPane.INFORMATION_MESSAGE);
				else if (err_code==-4) JOptionPane.showMessageDialog(getContentPane(),"Operation nicht möglich da sonst das Regal überlastet wird","Fehler", JOptionPane.INFORMATION_MESSAGE);
				else {
					inventoryView.refresh();
					JOptionPane.showMessageDialog(getContentPane(),"Eintrag wurde erfolgreich geändert","Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					dispose();
				}
			}
		});
		
		//Layout wird hier festgelegt. Reihenfolge alle Elemente
		
		Container pane = getContentPane();
		
		if (RIGHT_TO_LEFT) {
	          pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	    }
		
		pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }
        if (shouldWeightX) {
            c.weightx = 0.5;
        }
       
        c.gridx = 0;
        c.gridy = 1;
        pane.add(lName, c);
        
        c.gridx = 0;
        c.gridy = 2;
        pane.add(tfName, c);
              
        c.gridx = 1;
        c.gridy = 1;
        pane.add(lPlace, c);
        
        c.gridx = 1;
        c.gridy = 2;
        pane.add(tfPlace, c);
        
        c.gridx = 0;
        c.gridy = 3;
        pane.add(lCount, c);
        
        c.ipady = 40;
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 4;
        pane.add(tfCount, c);
        c.ipady = 0;
        
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 3;
        pane.add(lAdd, c);        
        
        c.gridheight = 2;
        c.gridx = 1;
        c.gridy = 4;
        c.ipady = 40;
        pane.add(tfAddRemove, c);
        c.gridheight = 1;
        c.ipady = 0;
        
        c.gridx = 2;
        c.gridy = 4;
        pane.add(bAdd, c);
        
        c.gridx = 2;
        c.gridy = 5;
        pane.add(bSubtract, c);
        
        c.gridx = 0;
        c.gridy = 6;
        pane.add(lCategory, c);
        
        c.gridx = 1;
        c.gridy = 6;
        pane.add(lPrize, c);
        
        c.gridx = 2;
        c.gridy = 6;
        pane.add(lWeight, c);
        
        c.gridx = 0;
        c.gridy = 7;
        pane.add(selectorCategory, c);
        
        c.gridx = 1;
        c.gridy = 7;
        pane.add(tfPrize, c);
        
        c.gridx = 2;
        c.gridy = 7;
        pane.add(tfWeight, c);
        
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        //c.insets = new Insets(10,0,0,0);  //top padding
        c.gridx = 2;        
        c.gridy = 8;       
        pane.add(bDelete, c);
		
        c.gridwidth = 2;   //2 columns wide
        c.gridx = 0;
        c.gridy = 8;
        pane.add(bSave, c);
		selectorCategory.repaint();
        setContentPane(pane);
	}

	/**Hilfsmethode, um Kategorie zu SelectionItems zu machen
	 * @return Kategorien als SelectionItem für die BoxOptions
	 */
	private SelectionItem[] categoriesToItem() {
		List<Category> categories = vs.getAllCategories();
		List<SelectionItem> categoryNames = new ArrayList<SelectionItem>();
		for (int i=0; i<categories.size();i++) {
			categoryNames.add(new SelectionItem(categories.get(i).getUID(), categories.get(i).getName()));
		}
		SelectionItem[] searchSelectables = categoryNames.toArray(new SelectionItem[categories.size()]);
		return searchSelectables;
	}
	public void setInventoryView(InventoryView inventoryView) {
		this.inventoryView = inventoryView;
	}
}
