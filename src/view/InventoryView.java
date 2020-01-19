package view;

import javax.swing.AbstractAction;
import javax.swing.Action;
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
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.xml.sax.SAXException;

import controller.*;
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

/**
 * Die Hauptansicht der Datenbank.
 * Hier werden alle Inhalte des Bestandes angezeigt
 * Von hier aus kommt man zu den Klassen, die die Daten manipulieren können.
 * Es gibt eine Tabelle des gesamten Bestandes. Doppelklick auf einen Artikel in der Tabelle öffnet eine einzelansicht des Artikels
 * Klick auf den Button neuen eintrag erstellen öffnet ein Fenster, in welchem ein neuer Eintrag erstellt werden kann.
 * Durch die menubar gelangt man zur kategorieansicht
 * Durch klicken auf aktualisieren wird die Datenbank aktualisiert und änderungen werden angezeigt
 */


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

	/**
	 * Konstruktor der InventoryView
	 * Hier wird das gesamte Layout festgelegt und die Funktionen aller features.
	 */
	public InventoryView()
	{
		
		//GUI Aufruf des sichtbaren Fensters
		setContentPane(new JPanel());
		setTitle("Bestandsübersicht");
		setSize(1280,720);
		setLocationRelativeTo(null);

		
		//Buttons alle Buttons werden hier deklariert
		JButton bNewIE = new JButton ("Neuen Inventareintrag erstellen");
		bNewIE.addActionListener(new ActionListener() { 
			//Aufruf des Fensters für einen neuen Artikel durch buttonklick
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
				newArticleView.setFrameVisible(true);
			}
		});
		
		JButton bSearch = new JButton ("Los!");
		JButton bRefresh = new JButton ("Aktualisieren");
		
		
		//Label Alle Labels werden hier deklariert
		JLabel lSearch = new JLabel("Suchen:");
		
		//Textarea alle Textareas werden hier deklariert
		JTextField taSearch = new JTextField(40);
		taSearch.setPreferredSize(new Dimension(40,30));
	
		Action startSearch = new AbstractAction()
		{
		    @Override
		    public void actionPerformed(ActionEvent e)
		    {
		        System.out.println("Search ("+selectedSearch+"):"+taSearch.getText());
		        //vs.search(mode,string);
		        refresh();
		    }
		};
		
		taSearch.addActionListener(startSearch);
		bSearch.addActionListener(startSearch);
		
		
        //Table hier wird die Tabelle deklariert und mit den Einträgen der Datenbank gefüllt und die Funktionen festgelegt
        data = vs.getInventoryEntryObjectArray();
        
        tableModel = new DefaultTableModel(data, columnNames) {
		private static final long serialVersionUID = 1L;
			@Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
            public Class<?> getColumnClass( int column ) {
                switch( column ){
                    case 0: return String.class;
                    case 1: return String.class;
                    case 2: return Integer.class;
                    case 3: return Integer.class;
                    case 4: return Integer.class;
                    case 5: return String.class;
                    default: return Object.class;
                }
            }
            
        };
        
        //Diverse Einstellungen der Tabelle
		table = new JTable(tableModel);
		table.setAutoCreateRowSorter(true);
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setRowHeight(26);
		table.getRowSorter().toggleSortOrder(0);
		
		//Aufruf der ArtikelView, also der einzelansicht nach doppelklick auf einen Artikel der Tabelle
		table.addMouseListener(new MouseAdapter(){
			 public void mousePressed(MouseEvent mouseEvent) {
			        JTable table =(JTable) mouseEvent.getSource();
			        Point point = mouseEvent.getPoint();
			        int row = table.rowAtPoint(point);
			        int column = 0;
			        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1 && table.getValueAt(row, column)!=null) {
			        	SwingUtilities.invokeLater(new Runnable() {
			                @Override
			                public void run() {
			                	
					        	int UID = Integer.parseInt((String) table.getValueAt(row, column));
					        	System.out.println("Select UID:"+UID);
					        	articleView.setVisible(true);
								articleView.ShowArticleView(UID);
			                }
			            });
			        	
			        }
			    }	
		});
		
		
		
		
		//Menubar hier wird das Menu mit allen funktionen festgelegt
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menü");
		
		JMenuItem menuCategory = new JMenuItem("Kategorien");
		JMenuItem menuSave = new JMenuItem("Speichern");
		JMenuItem menuValidate = new JMenuItem("DB Validieren");
		menuCategory.addActionListener(new ActionListener() { 
			//Hiermit gelangt man zur Kategorie
			@Override
			public void actionPerformed(ActionEvent e) {
				categoriesView.setVisible(true);
			}
			
		});
		menuSave.addActionListener(new ActionListener() {
			//Hiermit werden alle Einträge der Datenbank gespeichert
			@Override
			public void actionPerformed(ActionEvent e) {
				vs.manualSave();
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
					valide = vs.validateDatabase();
				} catch (SAXException e1) {
					JOptionPane.showMessageDialog(getContentPane(),"Datenbank nicht valide und eventuell korrumpiert. Bitte wenden Sie sich an einen Admin. Fehlermeldung:\n"+e1.getMessage(),"DB Validierungsstatus", JOptionPane.INFORMATION_MESSAGE);
				}
				JOptionPane.showMessageDialog(getContentPane(),"Datenbank Validierungsstatus: "+valide,"DB Validierungsstatus", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		
		bRefresh.addActionListener(new ActionListener() {
			//Hiermit wird die tabellenansicht aktualisiert
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		
		//Hier wird die Reihenfolge der menuelemente festgelegt
		menu.add(menuCategory);
		menu.addSeparator();
		menu.add(menuSave);
		menu.addSeparator();
		menu.add(menuValidate);
		
		menuBar.add(menu);
		setJMenuBar (menuBar);
		
		
		//Layout Hier werden alle Elemente auf das sichtbare Fenster hinzugefügt
		Container container = getContentPane();
		
		container.setLayout(new BorderLayout());
		container.setPreferredSize(new Dimension(1280,720));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(lSearch);
		topPanel.add(searchSelector);
		topPanel.add(bNewIE);
		
		topPanel.add(taSearch);
		topPanel.add(bSearch);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(100, 100));
		rightPanel.add(bRefresh);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(100, 100));
		
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(bNewIE);
		
		container.add(topPanel,BorderLayout.PAGE_START);
		container.add(leftPanel,BorderLayout.LINE_START);
		container.add(scrollPane,BorderLayout.CENTER);
		container.add(rightPanel,BorderLayout.LINE_END);
		container.add(bottomPanel,BorderLayout.PAGE_END);	
	}
	
	/**
	 * Der refresh Button wird hier aufgerufen, sodass die Tabelle mit allen Einträgen aktualisiert werden kann
	 * ohne das programm neu aufzurufen
	 */
	
	public void refresh() {
		//vs.loadVirtualStorage();
	 	tableModel.setDataVector(vs.getInventoryEntryObjectArray(),columnNames);
		tableModel.fireTableDataChanged();
		table.getRowSorter().toggleSortOrder(0);
		return;
	}
	
	/**
	 * setter für newarticleview
	 */
	public void setNewArticleView(NewArticleView articleView) {
		newArticleView = articleView;
	}
	
	/**
	 * setter für den virtualstorage
	 */
	public void setVirtualStorage(VirtualStorage vs) {
		this.vs = vs;
	}
	
	/**
	 * setter für articleview
	 */
	public void setArticleView(ArticleView articleView) {
		this.articleView = articleView;
	}
	
	
	/**
	 * setter für categoriesview
	 */
	public void setCategoriesView(CategoriesView categoriesView) {
		this.categoriesView = categoriesView;
	}
	
	
}
