package view;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.VirtualStorage;

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
 * Hier wird die Tabelle der Kategorien angezeigt, von hier aus werden die Änderungen verschiedenster Kategorien aufgerufen.
 * Gleiches Prinzip wie beim Inventory View.
 * Es gibt wieder eine Menubar, über welche der Bestand aufgerufen werden kann.
 */

public class CategoriesView extends JFrame {
	private static final long serialVersionUID = 1L;
	private VirtualStorage vs;
	private CategoryView categoryView;
	private DefaultTableModel tableModel;
	private String[] columnNames;
	private JTable table;
	private NewCategoryView newCategoryView;
	/**Die Methode zum anzeigen der CategoryView, wird aufgerufen durch die Menubar in der Bestandsübersicht.
	 * 
	 * @param vs, der MainVirtualStorage
	 */
	public CategoriesView(VirtualStorage vs) {
		this.vs=vs;
		setTitle("Kategorien");
		setSize(500,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);

		//Button
		JButton bAddCategory = new JButton ("Neue Kategorie erstellen");
		
		bAddCategory.addActionListener(new ActionListener() { 
			//Ruft ein neues Fenster auf, in welchem man eine neue Kategorie erstellen kann
			@Override
			public void actionPerformed(ActionEvent e) {
				vs.loadCategoryStorage();
				newCategoryView.setVisible(true);
			}
		});
		JButton bSearch = new JButton ("Los!");
		
		//Textarea
		JTextField tfSearch = new JTextField(15);
		
		//Label
		JLabel lSearch = new JLabel("Suchen:");
		
		Action startSearch = new AbstractAction()
		{
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public void actionPerformed(ActionEvent e)
		    {
		    	if (tfSearch.getText()!="") {
		    		 vs.searchCategoriesByName(tfSearch.getText());
		    	}
		    	else vs.loadVirtualStorage();
		        refresh();
		    }
		};
		bSearch.addActionListener(startSearch);
		tfSearch.addActionListener(startSearch);
		
		//Table wird hier mit Elementen aus der Datenbank gefüllt
		columnNames = new String[]{"ID","Kategorie"};
			
		Object[][] data = vs.getCategoryObjectArray();
		tableModel = new DefaultTableModel(data, columnNames) {
			private static final long serialVersionUID = 1L;
			@Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
		table = new JTable(tableModel);

		table.removeColumn(table.getColumnModel().getColumn(0));
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);	
		
		//Durch doppelklick öffnet sich ein einzelnes Fenster einer Kategorie, in welcher diese verändert werden kann
		table.addMouseListener(new MouseAdapter(){
			 public void mousePressed(MouseEvent mouseEvent) {
			        JTable table =(JTable) mouseEvent.getSource();
			        Point point = mouseEvent.getPoint();
			        int row = table.rowAtPoint(point);
			        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
			        	int column = 0;
			        	vs.loadCategoryStorage();
			        	Integer UID = (Integer) table.getModel().getValueAt(row, column);
			        	System.out.println(UID);
			        	categoryView.showCategoryView(UID);
						categoryView.setVisible(true);
			        }
			 }
		});
			
		//Layout wird hier festgelegt, Reihenfolge wie die Elemente angezeigt werden
		Container container = getContentPane();
		
		container.setLayout(new BorderLayout());
		container.setPreferredSize(new Dimension(1280,720));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(lSearch);
		topPanel.add(tfSearch);
		topPanel.add(bSearch);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(100, 100));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(100, 100));
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(bAddCategory);
		
		container.add(topPanel,BorderLayout.PAGE_START);
		container.add(leftPanel,BorderLayout.LINE_START);
		container.add(scrollPane,BorderLayout.CENTER);
		container.add(rightPanel,BorderLayout.LINE_END);
		container.add(bottomPanel,BorderLayout.PAGE_END);
	}
	

	public void setCategoryView(CategoryView categoryView) {
		this.categoryView=categoryView;
	}
	public void refresh() {
	 	tableModel.setDataVector(vs.getCategoryObjectArray(),columnNames);
		tableModel.fireTableDataChanged();
		table.removeColumn(table.getColumnModel().getColumn(0));
		//table.removeColumn(table.getColumnModel().getColumn(0));
	}
	public void setNewCategoriesView(NewCategoryView newCategoryView) {
		this.newCategoryView = newCategoryView;
		
	}
}