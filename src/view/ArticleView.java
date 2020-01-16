package view;

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.VirtualStorage;
import model.InventoryEntry;

public class ArticleView extends JFrame {
	
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;
	
	public ArticleView(int UID) {
		VirtualStorage vs = new VirtualStorage();
		InventoryEntry thisArticle = vs.getEntryByUID(UID);
		
		
		
		//GUI
		setTitle(thisArticle.product.getName());
		setSize(350,280);
		setLocationRelativeTo(null);
		
		
		
		//TextArea
		JTextArea tasektion = new JTextArea(Integer.toString(thisArticle.getShelfSection()),1,5);
		JTextArea taplatz = new JTextArea(Integer.toString(thisArticle.getShelfPlace()),1,5);
		JTextArea taanzahl = new JTextArea(Integer.toString(thisArticle.product.getCount()),1,5);
		JTextArea taadd = new JTextArea("0",1,5);
		JTextArea takategorie = new JTextArea(Integer.toString(thisArticle.product.getCategoryID()),1,5);
		JTextArea tapreis = new JTextArea(Integer.toString(thisArticle.product.getPrize()),1,5);
		JTextArea tagewicht = new JTextArea(Integer.toString(thisArticle.product.getWeight()),1,5);
		JTextArea taproduct = new JTextArea(thisArticle.product.getName(),1,5);
		
		
		
		//Label
		JLabel lgewicht = new JLabel("Gewicht:");
		JLabel lpreis = new JLabel("Preis:");
		JLabel lkategorie = new JLabel("Kategorie:");
		JLabel lsektion = new JLabel("Sektion:");
		JLabel lplatz = new JLabel("Platznummer:     ");
		JLabel lanzahl = new JLabel("Anzahl:  ");
		JLabel ladd = new JLabel("Add/Delete:");
		JLabel product = new JLabel("Name:");
		
		//button
		JButton badd = new JButton ("Add");
		JButton bsubtract = new JButton ("Subtract");
		JButton bdelete = new JButton ("Artikel Löschen");
		JButton bsave = new JButton ("Änderungen Speichern");
		
		bdelete.addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					vs.deleteProduct(UID);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
				String ssektion = tasektion.getText();
				int sektion = Integer.parseInt(ssektion);
				
				String splatz = taplatz.getText();
				int platz = Integer.parseInt(splatz);
				
				String sanzahl = taanzahl.getText();
				int anzahl = Integer.parseInt(sanzahl);
				try {vs.setAmount(UID, anzahl);} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();}
				
				
				String kategorie = takategorie.getText();
				
				String spreis = tapreis.getText();
				int preis = Integer.parseInt(spreis);
				try {vs.setPrize(UID, preis);} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();}
				
				String sgewicht = tagewicht.getText();
				int gewicht = Integer.parseInt(sgewicht);
				try {vs.setWeight(UID, gewicht);} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();}
				
				String name =taproduct.getText();
				try {vs.setName(UID, name);} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();}
				
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
        
        c.gridx = 2;
        c.gridy = 1;
        pane.add(lsektion, c);
        
        c.gridx = 2;
        c.gridy = 2;
        pane.add(tasektion, c);
        
       
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
		
	}
}
