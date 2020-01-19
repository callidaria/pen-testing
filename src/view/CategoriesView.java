package view;

import javax.swing.GroupLayout;
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
import javax.swing.table.DefaultTableModel;

import controller.VirtualStorage;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CategoriesView extends JFrame {
	private VirtualStorage vs;
	private CategoryView categoryView;
	private DefaultTableModel tableModel;
	public CategoriesView() {
		setTitle("Kategorie");
		setSize(500,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
	}
	public void showCategoriesView(){
		//GUI
		
		//Button
		JButton button = new JButton ("Neue Kategorie erstellen");
		button.addActionListener(new ActionListener() { 
		
			@Override
			public void actionPerformed(ActionEvent e) {
				NewCategoryView neu = new NewCategoryView();
				neu.setVisible(true);
			}
		});
		
		JButton bsuch = new JButton ("Los!");
		
		//Textarea
		JTextArea tasuchen = new JTextArea(1,15);
		
		//Label
		JLabel lsuchen = new JLabel("Suchen:");
		
		//Table
		String[] columnNames = {"ID",
                "Kategorie"};
			
		Object[][] data = vs.getCategoryObjectArray();
		tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
		JTable table = new JTable(tableModel);

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
			        	Integer UID = (Integer) table.getModel().getValueAt(row, column);
			        	System.out.println(UID);
			        	categoryView.showCategoryView(0);
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
	
}
