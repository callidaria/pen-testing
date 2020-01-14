package view;

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

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Front extends JFrame {

	public Front()
	{	
		//GUI
		setTitle("Bestandsübersicht");
		setSize(500,600);
		setLocationRelativeTo(null);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		//Button
		JButton button = new JButton ("New Article");
		button.addActionListener(new ActionListener() { 
		
			@Override
			public void actionPerformed(ActionEvent e) {
				NewArticle neu = new NewArticle();
				neu.setVisible(true);
			}
		});
		
		JButton such = new JButton ("Los!");
		
		//Label
		JLabel suchen = new JLabel("Suchen:");
		
		//Textarea
		JTextArea ta = new JTextArea(1,5);
		
		//Table
		String[] columnNames = {"Bereich",
                "Regal",
                "Platz",
                "Produktname",
                "Anzahl"};
		
		
		Object[][] data = {
			    {new Integer(5), new Integer(5), new Integer(5), "Roter Stift", new Integer(10000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Blauer Stift", new Integer(500)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)},
			    {new Integer(5), new Integer(5), new Integer(5), "Grüner Stift", new Integer(3000)}
		};
		
		JTable table = new JTable(data, columnNames);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		table.addMouseListener(new MouseAdapter(){
			 public void mousePressed(MouseEvent mouseEvent) {
			        JTable table =(JTable) mouseEvent.getSource();
			        Point point = mouseEvent.getPoint();
			        int row = table.rowAtPoint(point);
			        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
			        	ArticleView article = new ArticleView();
						article.setVisible(true);
			        }
			    }	
		});
		
		
		
		
		//Menubar
		JMenuBar menu = new JMenuBar();
		JMenu datei = new JMenu("Menü");
		JMenu submenu = new JMenu("Submenü");
		JMenuItem category = new JMenuItem("Kategorien");
		JMenuItem zwei = new JMenuItem("Punkt 2");
		category.addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Category cat = new Category();
				cat.setVisible(true);
			}
			
		});
		
		zwei.addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(getContentPane(),"Hier könnte ihre Nachricht stehen","�berschrift", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		
		
		submenu.add(zwei);
		datei.add(category);
		datei.addSeparator();
		datei.add(submenu);
		menu.add(datei);
		setJMenuBar (menu);
		
		
		//Layout
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
	
		pane.add(button);
		pane.add(suchen);
		pane.add(ta);
		pane.add(such);
		pane.add(scrollPane);
		
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
