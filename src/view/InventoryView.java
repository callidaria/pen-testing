package view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import controller.*;
import model.InventoryEntry;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class InventoryView extends JFrame {

	private NewArticleView newArticleView;
	private Object[][] data;
	VirtualStorage vs = new VirtualStorage();

	public InventoryView()
	{
		setContentPane(new JPanel());
		setTitle("Bestandsübersicht");
		setSize(1280,720);
		setLocationRelativeTo(null);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		//Button
		JButton button = new JButton ("Neuen Inventareintrag erstellen");
		button.addActionListener(new ActionListener() { 
		
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
				newArticleView.setFrameVisible(true);
			}
		});
		
		JButton such = new JButton ("Los!");
		
		//Label
		JLabel suchen = new JLabel("Suchen:");
		
		//Textarea
		JTextArea ta = new JTextArea(1,40);
		
		//Table
		String[] columnNames = {"ID",
				"Produktname",
                "Preis",
                "Gewicht",
                "Anzahl",
                "Kategorie"};
		
		
        
        data = vs.getObjectArray();
        
		JTable table = new JTable(data, columnNames) {
			/** Verhindert das Zellen bearbeitbar sind.
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {                
	            return false;               
			};
		};
		
		JScrollPane scrollPane = new JScrollPane(table);
		//scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		table.setFillsViewportHeight(true);
		table.setRowHeight(26);
		
		table.addMouseListener(new MouseAdapter(){
			 public void mousePressed(MouseEvent mouseEvent) {
			        JTable table =(JTable) mouseEvent.getSource();
			        Point point = mouseEvent.getPoint();
			        int row = table.rowAtPoint(point);
			        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
			        	int column = 0;
			        	int UID = Integer.parseInt((String) table.getValueAt(row, column));
			        	System.out.println("Select UID:"+UID);
			        	ArticleView article = new ArticleView(UID);
						article.setVisible(true);
			        }
			    }	
		});
		
		
		
		
		//Menubar
		JMenuBar menu = new JMenuBar();
		JMenu datei = new JMenu("Menü");
		//JMenu submenu = new JMenu("Submenü");
		JMenuItem category = new JMenuItem("Kategorien");
		JMenuItem zwei = new JMenuItem("Punkt 2");
		category.addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CategoriesView cat = new CategoriesView();
				cat.setVisible(true);
			}
			
		});
		
		zwei.addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(getContentPane(),"Hier könnte ihre Nachricht stehen","�berschrift", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		
		
		//submenu.add(zwei);
		datei.add(category);
		//datei.addSeparator();
		//datei.add(submenu);
		menu.add(datei);
		setJMenuBar (menu);
		
		
		//Layout
		Container container = getContentPane();
		
		container.setLayout(new BorderLayout());
		container.setPreferredSize(new Dimension(1280,720));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(button);
		topPanel.add(suchen);
		topPanel.add(ta);
		topPanel.add(such);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(100, 100));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(100, 100));
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(button);
		
		container.add(topPanel,BorderLayout.PAGE_START);
		container.add(leftPanel,BorderLayout.LINE_START);
		container.add(scrollPane,BorderLayout.CENTER);
		container.add(rightPanel,BorderLayout.LINE_END);
		container.add(bottomPanel,BorderLayout.PAGE_END);	
	}
	
	public void refresh() {
		vs.loadVirtualStorage();
		System.out.println("Heureka, jetzt wird die Tabelle refreshed oder auch nicht D:");
		return;
	}
	
	public void setNewArticleView(NewArticleView articleView) {
		newArticleView = articleView;
		System.out.println("Connected Frames");
	}
	
	
}
