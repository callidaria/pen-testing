package view;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class ArticleView extends JFrame {
	public ArticleView() {
		//GUI
		setTitle("ArticleView");
		setSize(180,400);
		setLocationRelativeTo(null);
		
		//TextArea
		JTextArea taproduct = new JTextArea("	");
		JTextArea taregal = new JTextArea("Regalnummer	");
		JTextArea taplatz = new JTextArea("Platznummer	");
		JTextArea taanzahl = new JTextArea("Stückzahl	");
		
		//Label
		JLabel lproduct = new JLabel("Produkt:");
		JLabel lregal = new JLabel("Regal:    ");
		JLabel lplatz = new JLabel("Platz:     ");
		JLabel lanzahl = new JLabel("Anzahl:  ");
		
		
		
		
		//Layout
		//Container pane = getContentPane();
		//pane.setLayout(new GridLayout(1,2));
		
		Container column1 = getContentPane();
		column1.setLayout(new FlowLayout());
		//pane.add(column1);
		
		Container column2 = getContentPane();
		column2.setLayout(new FlowLayout());
		//pane.add(column2);
		
		
		column1.add(lproduct);
		column2.add(taproduct);
		column1.add(lregal);
		column2.add(taregal);
		column1.add(lplatz);
		column2.add(taplatz);
		column1.add(lanzahl);
		column2.add(taanzahl);
		
	}
}
