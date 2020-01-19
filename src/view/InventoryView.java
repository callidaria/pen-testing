package view;

import javax.swing.AbstractAction;
import javax.swing.Action;
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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.xml.sax.SAXException;
import controller.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

	private static final long serialVersionUID = 1L;
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
			"Kategories"};
	private int selectedSearch;
	private CategoriesView categoriesView;
	
	/**
	 * Konstruktor der InventoryView
	 * Hier wird das gesamte Layout festgelegt und die Funktionen aller features.
	 * 
	 * @param vs, der MainVirtualStorage
	 */
	public InventoryView(VirtualStorage vs) {
		this.vs=vs;
		//GUI Aufruf des sichtbaren Fensters
		setContentPane(new JPanel());
		setTitle("Bestandsübersicht");
		setSize(1280,720);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		

		
		//Buttons alle Buttons werden hier deklariert
		JButton bAddEntry = new JButton ("Neuen Inventareintrag erstellen");
		bAddEntry.addActionListener(new ActionListener() {
			//Aufruf des Fensters für einen neuen Artikel durch buttonklick

			@Override
			public void actionPerformed(ActionEvent e) {
				vs.loadVirtualStorage();
				vs.loadCategoryStorage();
				newArticleView.setVisible(true);
			}
		});
		
		JButton bSearch = new JButton ("Los!");

		//Label Alle Labels werden hier deklariert
		JLabel lSearch = new JLabel("Suchen:");
		
		String searchSelectables[] = {"Alles","ID","Name","Anzahl","Gewicht","Preis","Kategorie"};
		JComboBox<String> searchSelector = new JComboBox<String>(searchSelectables);
		searchSelector.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedSearch=searchSelector.getSelectedIndex();
			}
		});
		
		//Textarea
		JTextField tfSearch = new JTextField(40);
		tfSearch.setPreferredSize(new Dimension(40,30));
		Action startSearch = new AbstractAction()
		{
		    /**
			 * 
			 */
			private static final long serialVersionUID = 2869195429379554645L;

			@Override
		    public void actionPerformed(ActionEvent e)
		    {
		    	vs.loadVirtualStorage();
		    	if (tfSearch.getText()!="") {
		    		if (searchSelector.getSelectedIndex()==0) vs.searchEntries(tfSearch.getText());
		    		else if (searchSelector.getSelectedIndex()==1) vs.searchEntriesByID(tfSearch.getText());
		    		else if (searchSelector.getSelectedIndex()==2) vs.searchEntriesByName(tfSearch.getText());
		    		else if (searchSelector.getSelectedIndex()==3) vs.searchEntriesByAmount(tfSearch.getText());
		    		else if (searchSelector.getSelectedIndex()==4) vs.searchEntriesByWeight(tfSearch.getText());
		    		else if (searchSelector.getSelectedIndex()==5) vs.searchEntriesByPrize(tfSearch.getText());
		    		else vs.searchEntriesByCategory(tfSearch.getText());
		    	}
		        refresh();
		        
		    }
		};
		tfSearch.addActionListener(startSearch);
		bSearch.addActionListener(startSearch);
		

        //Table hier wird die Tabelle deklariert und mit den Einträgen der Datenbank gefüllt und die Funktionen festgelegt
        data = vs.getInventoryEntryObjectArray();

        tableModel = new DefaultTableModel(data, columnNames) {
		private static final long serialVersionUID = 1L;
			@Override
            public boolean isCellEditable(int row, int column) { return false; }
            public Class<?> getColumnClass( int column ) {
                switch( column ) {
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
			//feuert, wenn mit der Maus auf die tabelle geklickt wurde
			public void mousePressed(MouseEvent mouseEvent) {
			        JTable table =(JTable) mouseEvent.getSource();
			        Point point = mouseEvent.getPoint();
			        int row = table.rowAtPoint(point);
			        int column = 0;
			        //Stellt fest, ob es ein doppelklick war und in die zu sehende Tabelle geklickt wurde
			        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1 && table.getValueAt(row, column)!=null) {
			        	SwingUtilities.invokeLater(new Runnable() {
			                @Override
			                public void run() {
			                	vs.loadVirtualStorage();
			                	vs.loadCategoryStorage();
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
		JMenuItem menuRefresh = new JMenuItem("Aktualisieren");
		JMenuItem menuSave = new JMenuItem("Manuel Speichern");
		JMenuItem menuValidate = new JMenuItem("DB Validieren");
		menuCategory.addActionListener(new ActionListener() { 
			//Hiermit gelangt man zur Kategorie
			@Override
			public void actionPerformed(ActionEvent e) {
				categoriesView.setVisible(true);
			}
		});

		menuRefresh.addActionListener(new ActionListener() {
			//nach klicken wird die Tabelle aktualisiert
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		
		menuSave.addActionListener(new ActionListener() {
			//nach Buttonklick werden alle änderungen gesichert
			@Override
			public void actionPerformed(ActionEvent e) {
				vs.manualSave();
			}
		});
		
		menuValidate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int yna = JOptionPane.showConfirmDialog(getContentPane(),"Das Validieren ist für den Administrator gedacht. Achtung! Das Validieren der Datenbank kann bei einem großen Datensatz sehr lange dauern. Wir reden von ca. einer Stunde bei einer vollen Datenbank! Fortfahren?","Achtung!", JOptionPane.YES_NO_OPTION);
				if (yna==0) {
					boolean valide=false;
					try {
						valide = vs.validateDatabase();
					} catch (SAXException e1) {
						JOptionPane.showMessageDialog(getContentPane(),"Datenbank nicht valide und eventuell korrumpiert. Bitte wenden Sie sich an einen Admin. Fehlermeldung:\n"+e1.getMessage(),"DB Validierungsstatus", JOptionPane.INFORMATION_MESSAGE);
					}
					JOptionPane.showMessageDialog(getContentPane(),"Datenbank Validierungsstatus: "+valide,"DB Validierungsstatus", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		//Legt die Reihenfolge der Item in der Menubar fest
		menu.add(menuCategory);
		menu.addSeparator();
		menu.add(menuRefresh);
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
		topPanel.add(bAddEntry);
		
		topPanel.add(tfSearch);
		topPanel.add(bSearch);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(100, 100));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(100, 100));
		
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(bAddEntry);
		
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
	 	tableModel.setDataVector(vs.getInventoryEntryObjectArray(),columnNames);
		tableModel.fireTableDataChanged();
		table.getRowSorter().toggleSortOrder(0);
		return;
	}

	/**Verlinkt diesen Frame zur newArticleView
	 * 
	 * @param newArticleView der zu verlinkende Frame
	 */
	public void setNewArticleView(NewArticleView newArticleView) {
		this.newArticleView = newArticleView;
	}
	/**Verlinkt diesen Frame zur articleView
	 * 
	 * @param articleView der zu verlinkende Frame
	 */
	public void setArticleView(ArticleView articleView) {
		this.articleView = articleView;
	}
	
	/**Verlinkt diesen Frame zur categoriesView
	 * 
	 * @param categoriesView der zu verlinkende Frame
	 */
	public void setCategoriesView(CategoriesView categoriesView) {
		this.categoriesView = categoriesView;
	}
}
