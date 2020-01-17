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

import controller.VirtualStorage;
import model.Category;

import java.awt.BorderLayout;
import java.awt.Container;
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

public class CategoryView extends JFrame {
	
	private VirtualStorage vs;
	public CategoryView() {
		//GUI
		setTitle("Kategorie Ansicht");
		setSize(400,150);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		
		
		
	}
	public void showCategoryView(int UID){
		setContentPane(new JPanel());
		//Label
		Category thisCategory = new Category(1,"Keine Kategorie");
		JLabel lid = new JLabel("Akuteller Kategoriename:");
		JLabel loldname = new JLabel(thisCategory.getName());
		JLabel lnewname = new JLabel("Neuer Kategoriename:");
		
		//Textarea

		JTextArea taname = new JTextArea(1,10);
		taname.setText(thisCategory.getName());
		
		//Button
		JButton button = new JButton ("Save");
		button.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = taname.getText();
		        System.out.println("contents = " + name);
			}
		});
		
		//Layout
		
		Container pane = getContentPane();
		pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (true) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }
        c.gridx = 0;
        c.gridy = 1;
		pane.add(lid,c);
		
		c.gridx = 1;
        c.gridy = 1;
		pane.add(loldname,c);
		
		c.gridx = 0;
        c.gridy = 2;
		pane.add(lnewname,c);
		
		c.gridx = 1;
        c.gridy = 2;
		pane.add(taname,c);
		
		c.gridx = 1;
        c.gridy = 4;
		pane.add(button,c);
	}
	public void setVirtualStorage(VirtualStorage vs) {
		this.vs = vs;
	}

}
