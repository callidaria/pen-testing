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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.VirtualStorage;
import model.Category;
import model.InventoryEntry;
import model.Product;
import view.components.SelectionItem;

public class ArticleView extends JFrame {
	
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;
	private VirtualStorage vs;
	private InventoryView inventoryView;
	
	public ArticleView() {
		
		setSize(480,360);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Artikeldetailansicht");
	}
	
	public void ShowArticleView(int UID) {
		setContentPane(new JPanel());
		InventoryEntry thisArticle = vs.getEntryByUID(UID);
		
		//GUI
		setTitle(thisArticle.product.getName());
		
		
		
		
		//TextArea
		JTextField taplatz = new JTextField(thisArticle.getStringifiedUID(),20);
		JTextField taanzahl = new JTextField(Integer.toString(thisArticle.product.getCount()),5);
		JTextField taadd = new JTextField("0",5);
		
		SelectionItem[] searchSelectables = categoriesToItem();

		JComboBox<SelectionItem> takategorie = new JComboBox<SelectionItem>(searchSelectables);
		takategorie.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SelectionItem item = (SelectionItem) takategorie.getSelectedItem();
				System.out.println(item.getId()+":"+item.getName());
			}
		});
		JTextField tapreis = new JTextField(Integer.toString(thisArticle.product.getPrize()),5);
		JTextField tagewicht = new JTextField(Integer.toString(thisArticle.product.getWeight()),5);
		JTextField taproduct = new JTextField(thisArticle.product.getName(),5);
		
		
		
		//Label
		JLabel lgewicht = new JLabel("Gewicht in 1/10 gramm:");
		JLabel lpreis = new JLabel("Preis:");
		JLabel lkategorie = new JLabel("Kategorie:");
		JLabel lplatz = new JLabel("Platznummer:");
		JLabel lanzahl = new JLabel("Anzahl:");
		JLabel ladd = new JLabel("+/˗:");
		JLabel product = new JLabel("Name:");
		
		//button
		JButton badd = new JButton ("＋");
		JButton bsubtract = new JButton ("˗");
		JButton bdelete = new JButton ("Artikel Löschen");
		JButton bsave = new JButton ("Änderungen Speichern");
		
		bdelete.addActionListener(new ActionListener() { 
			
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
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(getContentPane(),"Hier könnte ihre Nachricht stehen:"+e1.getMessage(),"Artikel gelöscht", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
				
			}
			
			
		});
		
		badd.addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String eins = taadd.getText();
				String zwei = taanzahl.getText();
				int add = Integer.parseInt(eins);
				int anzahl = Integer.parseInt(zwei);
				int sum = add+anzahl;
				
				taadd.setText("0");
				taanzahl.setText(Integer.toString(sum));
				try {
					vs.changeAmountBy(UID, add);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		bsubtract.addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String eins = taadd.getText();
				String zwei = taanzahl.getText();
				int add = Integer.parseInt(eins);
				int anzahl = Integer.parseInt(zwei);
				int sum = anzahl-add;
				
				taadd.setText("0");
				taanzahl.setText(Integer.toString(sum));
				try {
					vs.changeAmountBy(UID, -add);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		bsave.addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String suid = taplatz.getText();
				int uid = Integer.parseInt(suid);
				
				String splatz = taplatz.getText();
				int platz = Integer.parseInt(splatz);
				
				String sanzahl = taanzahl.getText();
				int anzahl = Integer.parseInt(sanzahl);
				
				SelectionItem skategorie = (SelectionItem) takategorie.getSelectedItem();
				int kategorie_id = skategorie.getId();
				
				String spreis = tapreis.getText();
				int preis = Integer.parseInt(spreis);
				String sgewicht = tagewicht.getText();
				int gewicht = Integer.parseInt(sgewicht);
				String name =taproduct.getText();
				
				InventoryEntry editedIE = new InventoryEntry(uid, new Product(name, anzahl, gewicht, preis, kategorie_id));
				
				if (anzahl<0) JOptionPane.showMessageDialog(getContentPane(),"Bitte eine positive Zahl eingeben als Anzahl eingeben","", JOptionPane.INFORMATION_MESSAGE);
			
			
			
			
			
				if (preis<0) JOptionPane.showMessageDialog(getContentPane(),"Bitte eine positive Zahl eingeben als Preis eingeben","", JOptionPane.INFORMATION_MESSAGE);
			
			
			
			
				if (gewicht < 0) JOptionPane.showMessageDialog(getContentPane(),"Bitte eine positive Zahl eingeben als Gewicht eingeben","", JOptionPane.INFORMATION_MESSAGE); 
				try {
					vs.replaceInventoryEntry(thisArticle.getUID(),editedIE);
					int close = JOptionPane.showConfirmDialog(getContentPane(), "Änderung gespeichert.\n Schließen?","Änderung gespeichert",JOptionPane.YES_NO_OPTION);
					if (close==0) {
						inventoryView.refresh();
						setVisible(false);
						dispose();
					}
				}catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(getContentPane(),"Fehler: "+e1.getMessage(),"Fehler", JOptionPane.INFORMATION_MESSAGE);
				}
				
				
			}
			
				
			
		});
		
		//Layout
		//Container pane = getContentPane();
		//pane.setLayout(new GridLayout(2,4));
		
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
        pane.add(product, c);
        
        c.gridx = 0;
        c.gridy = 2;
        pane.add(taproduct, c);
              
   
        c.gridx = 1;
        c.gridy = 1;
        pane.add(lplatz, c);
        
        c.gridx = 1;
        c.gridy = 2;
        pane.add(taplatz, c);
        
        
       
        c.gridx = 0;
        c.gridy = 3;
        pane.add(lanzahl, c);
        
        c.ipady = 40;
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 4;
        pane.add(taanzahl, c);
        c.ipady = 0;
        
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 3;
        pane.add(ladd, c);        
        
        
        c.gridheight = 2;
        c.gridx = 1;
        c.gridy = 4;
        c.ipady = 40;
        pane.add(taadd, c);
        c.gridheight = 1;
        c.ipady = 0;
        
        c.gridx = 2;
        c.gridy = 4;
        pane.add(badd, c);
        
        c.gridx = 2;
        c.gridy = 5;
        pane.add(bsubtract, c);
        
     
        c.gridx = 0;
        c.gridy = 6;
        pane.add(lkategorie, c);
        
        c.gridx = 1;
        c.gridy = 6;
        pane.add(lpreis, c);
        
        c.gridx = 2;
        c.gridy = 6;
        pane.add(lgewicht, c);
        
        c.gridx = 0;
        c.gridy = 7;
        pane.add(takategorie, c);
        
        c.gridx = 1;
        c.gridy = 7;
        pane.add(tapreis, c);
        
        c.gridx = 2;
        c.gridy = 7;
        pane.add(tagewicht, c);
        
       
        
      
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        //c.insets = new Insets(10,0,0,0);  //top padding
        c.gridx = 2;        
        c.gridy = 8;       
        pane.add(bdelete, c);
		
        c.gridwidth = 2;   //2 columns wide
        c.gridx = 0;
        c.gridy = 8;
        pane.add(bsave, c);
        setContentPane(pane);
	}

	private SelectionItem[] categoriesToItem() {
		List<Category> categories = vs.getAllCategories();
		List<SelectionItem> categoryNames = new ArrayList<SelectionItem>();
		for (int i=0; i<categories.size();i++) {
			categoryNames.add(
					new SelectionItem(categories.get(i).getUID(), categories.get(i).getName())
					);
		}
		SelectionItem[] searchSelectables = categoryNames.toArray(new SelectionItem[categories.size()]);
		return searchSelectables;
	}
	public void setVirtualStorage(VirtualStorage vs) {
		this.vs = vs;
	}
	public void setInventoryView(InventoryView inventoryView) {
		this.inventoryView = inventoryView;
	}
}
