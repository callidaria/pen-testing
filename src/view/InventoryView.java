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
		JButton bNewIE = new JButton ("Neuen Inventareintrag erstellen");
		bNewIE.addActionListener(new ActionListener() { 
		
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
				newArticleView.setFrameVisible(true);
			}
		});
		
		JButton bSearch = new JButton ("Los!");
		JButton bRefresh = new JButton ("Aktualisieren");
		//Label
		JLabel lSearch = new JLabel("Suchen:");
		
		//Textarea
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
		table = new JTable(tableModel);
		table.setAutoCreateRowSorter(true);
		
		table.getTableHeader().setReorderingAllowed(false);
		//table.removeColumn(table.getColumnModel().getColumn(5));
		JScrollPane scrollPane = new JScrollPane(table);
		//scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		table.setFillsViewportHeight(true);
		table.setRowHeight(26);
		table.getRowSorter().toggleSortOrder(0);
		
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
		
		
		
		
		//Menubar
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menü");
		//JMenu submenu = new JMenu("Submenü");
		JMenuItem menuCategory = new JMenuItem("Kategorien");
		JMenuItem menuSave = new JMenuItem("Speichern");
		JMenuItem menuValidate = new JMenuItem("DB Validieren");
		menuCategory.addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				categoriesView.setVisible(true);
			}
			
		});
		menuSave.addActionListener(new ActionListener() {
			
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
			
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		
		//submenu.add(zwei);
		menu.add(menuCategory);
		menu.addSeparator();
		menu.add(menuSave);
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
	
	public void refresh() {
		//vs.loadVirtualStorage();
	 	tableModel.setDataVector(vs.getInventoryEntryObjectArray(),columnNames);
		tableModel.fireTableDataChanged();
		table.getRowSorter().toggleSortOrder(0);
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
