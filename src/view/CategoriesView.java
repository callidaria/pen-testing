package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
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

public class CategoriesView extends JFrame {
	private static final long serialVersionUID = 1L;
	private VirtualStorage vs;
	private CategoryView categoryView;
	private DefaultTableModel tableModel;
	private String[] columnNames;
	private JTable table;
	public CategoriesView() {
		setTitle("Kategorien");
		setSize(500,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
	}
	public void showCategoriesView() {
		//Button
		JButton button = new JButton ("Neue Kategorie erstellen");
		CategoriesView tp = this;
		button.addActionListener(new ActionListener() { 
		
			@Override
			public void actionPerformed(ActionEvent e) {
				vs.loadCategoryStorage();
				NewCategoryView neu = new NewCategoryView(vs,tp);
				neu.setVisible(true);
			}
		});
		JButton bsuch = new JButton ("Los!");
		
		//Textarea
		JTextArea tasuchen = new JTextArea(1,15);
		
		//Label
		JLabel lsuchen = new JLabel("Suchen:");
		
		//Table
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
		//Layout
		Container container = getContentPane();
		
		container.setLayout(new BorderLayout());
		container.setPreferredSize(new Dimension(1280,720));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(lsuchen);
		topPanel.add(tasuchen);
		topPanel.add(bsuch);
		
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
	public void setVirtualStorage(VirtualStorage vs) {
		this.vs = vs;
		showCategoriesView();
	}
	public void setCategoryView(CategoryView categoryView) {
		this.categoryView=categoryView;
	}
	public void refresh() {
	 	tableModel.setDataVector(vs.getCategoryObjectArray(),columnNames);
		tableModel.fireTableDataChanged();
		//table.removeColumn(table.getColumnModel().getColumn(0));
	}
}