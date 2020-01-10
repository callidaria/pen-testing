package view;

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ArticleView extends JFrame {
	
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;
	
	public ArticleView() {
		//GUI
		setTitle("ArticleView");
		setSize(350,280);
		setLocationRelativeTo(null);
		
		//TextArea
		JTextArea tasektion = new JTextArea("100");
		JTextArea taregal = new JTextArea("101");
		JTextArea taplatz = new JTextArea("102");
		JTextArea taanzahl = new JTextArea("10");
		JTextArea taadd = new JTextArea("");
		JTextArea takategorie = new JTextArea("Buntstifte");
		JTextArea tapreis = new JTextArea("0,99€");
		JTextArea tagewicht = new JTextArea("10 Gramm");
		
		//Label
		JLabel lgewicht = new JLabel("Gewicht:");
		JLabel lpreis = new JLabel("Preis:");
		JLabel lkategorie = new JLabel("Kategorie:");
		JLabel lsektion = new JLabel("Sektion:");
		JLabel lregal = new JLabel("Regal:    ");
		JLabel lplatz = new JLabel("Platz:     ");
		JLabel lanzahl = new JLabel("Anzahl:  ");
		JLabel ladd = new JLabel("Add/Delete:");
		
		JLabel product = new JLabel("Grüner Stift");
		
		//button
		JButton badd = new JButton ("Add");
		JButton bsubtract = new JButton ("Subtract");
		JButton bdelete = new JButton ("Delete Article");
		JButton bsave = new JButton ("Save Changes");
		
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
        c.ipady = 40; 
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(product, c);
        c.ipady = 0;
        c.gridwidth = 1;
        
        
        c.gridx = 0;
        c.gridy = 1;
        pane.add(lsektion, c);
 
        c.gridx = 1;
        c.gridy = 1;
        pane.add(lregal, c);

   
        c.gridx = 2;
        c.gridy = 1;
        pane.add(lplatz, c);
        
        c.gridx = 0;
        c.gridy = 2;
        pane.add(tasektion, c);
        
        c.gridx = 1;
        c.gridy = 2;
        pane.add(taregal, c);
        
        c.gridx = 2;
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
		
	}
}
