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


public class Front extends JFrame {

	public Front()
	{	
		//GUI
		setTitle("Bestandsübersicht");
		setSize(400,220);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		//Button
		JButton button = new JButton ("Ende");
		button.addActionListener(new ActionListener() { 
		
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		//Label
		JLabel suchen = new JLabel("Suchen:");
		
		//Textarea
		JTextArea ta = new JTextArea("Bitte eingeben!");
		
		//Table
		String[] columnNames = {"Bereich",
                "Regal",
                "Platz",
                "Produktname",
                "Anzahl"};
		
		Object[][] data = {
				{"Bereich",  "Regal",
					 "Platz", "Produktname", "Anzahl"},
			    {new Integer(5), new Integer(5),
			    	new Integer(5), "Roter Stift", new Integer(10000)},
			    {new Integer(5), new Integer(5),
				    	new Integer(5), "Blauer Stift", new Integer(500)},
			    {new Integer(5), new Integer(5),
					    	new Integer(5), "Grüner Stift", new Integer(3000)}
		};
		
		JTable table = new JTable(data, columnNames);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		
		//Menubar
		JMenuBar menu = new JMenuBar();
		JMenu datei = new JMenu("Datei");
		JMenu submenu = new JMenu("Submenü");
		JMenuItem eins = new JMenuItem("Kategorie");
		JMenuItem zwei = new JMenuItem("Punkt 2");
		eins.addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Front m = new Front();
				m.setVisible(true);
			}
			
		});
		
		zwei.addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(getContentPane(),"Hier könnte ihre Nachricht stehen","Überschrift", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		
		
		submenu.add(zwei);
		datei.add(eins);
		datei.addSeparator();
		datei.add(submenu);
		menu.add(datei);
		setJMenuBar (menu);
		
		
		//Layout
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
	
		pane.add(suchen);
		pane.add(ta);
		pane.add(table.getTableHeader());
		pane.add(table);
		pane.add(button);
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Front m = new Front();
				m.setVisible(true);
			}
		});
	}	
	
}
