package view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.xml.sax.SAXException;

import controller.*;
import database.Database;
import model.InventoryEntry;
import model.Product;

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
	private ArticleView articleView;
	JTable table;
	DefaultTableModel tableModel;
	String[] columnNames = {"ID",
			"Produktname",
            "Anzahl",
            "Gewicht",
            "Preis",
            //"Kategorie ID",
            "Kategories"};
	private CategoriesView categoriesView;
	
	private int selectedSearch=0;

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
		JButton refresh = new JButton ("Aktualisieren");
		//Label
		JLabel suchen = new JLabel("Suchen:");
		
		//Textarea
		JTextArea ta = new JTextArea(1,40);
	
		

		
		
        
        data = vs.getInventoryEntryObjectArray();
        
       tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
		table = new JTable(tableModel);
		//table.removeColumn(table.getColumnModel().getColumn(5));
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
			        	SwingUtilities.invokeLater(new Runnable() {
			                @Override
			                public void run() {
			                	int column = 0;
					        	int UID = Integer.parseInt((String) table.getValueAt(row, column));
					        	System.out.println("Select UID:"+UID);
					        	articleView.setVisible(true);
								articleView.ShowArticleView(UID);
			                }
			            });
			        	
			        }
			    }	
		});
		
		
		
		
		//Menubar
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menü");
		//JMenu submenu = new JMenu("Submenü");
		JMenuItem menuCategory = new JMenuItem("Kategorien");
		JMenuItem menuValidate = new JMenuItem("DB Validieren");
		menuCategory.addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				categoriesView.setVisible(true);
			}
			
		});
		String searchSelectables[] = {"Alles","ID","Name","Anzahl","Gewicht","Preis","Kategorie"};
		JComboBox<String> searchSelector = new JComboBox<String>(searchSelectables);
		searchSelector.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedSearch=searchSelector.getSelectedIndex();
			}
		});
		
		menuValidate.addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(getContentPane(),"Das Validieren ist für den Administrator gemacht. Achtung! Das Validieren der Datenbank kann bei einem großen Datensatz sehr lange dauern. Wir reden von ca einer Stunde bei einer vollen Datenbank!","Datenbankvalidierung gestartet", JOptionPane.INFORMATION_MESSAGE);
				boolean valide=false;
				try {
					valide = Database.validate();
				} catch (SAXException e1) {
					JOptionPane.showMessageDialog(getContentPane(),"Datenbank nicht valide und eventuell korrumpiert. Bitte wenden Sie sich an einen Admin. Fehlermeldung:\n"+e1.getMessage(),"DB Validierungsstatus", JOptionPane.INFORMATION_MESSAGE);
				}
				JOptionPane.showMessageDialog(getContentPane(),"Datenbank Validierungsstatus: "+valide,"DB Validierungsstatus", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		
		refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		
		//submenu.add(zwei);
		menu.add(menuCategory);
		menu.addSeparator();
		menu.add(menuValidate);
		menuBar.add(menu);
		setJMenuBar (menuBar);
		
		
		//Layout
		Container container = getContentPane();
		
		container.setLayout(new BorderLayout());
		container.setPreferredSize(new Dimension(1280,720));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(searchSelector);
		topPanel.add(button);
		topPanel.add(suchen);
		topPanel.add(ta);
		topPanel.add(such);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(100, 100));
		rightPanel.add(refresh);
		
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
	 	tableModel.setDataVector(vs.getInventoryEntryObjectArray(),columnNames);
		tableModel.fireTableDataChanged();
		return;
	}
	
	public void setNewArticleView(NewArticleView articleView) {
		newArticleView = articleView;
	}
	
	public void setVirtualStorage(VirtualStorage vs) {
		this.vs = vs;
	}

	public void setArticleView(ArticleView articleView) {
		this.articleView = articleView;
	}

	public void setCategoriesView(CategoriesView categoriesView) {
		this.categoriesView = categoriesView;
	}
	
	
}
