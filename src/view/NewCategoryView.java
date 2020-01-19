package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import controller.VirtualStorage;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewCategoryView extends JFrame {
	private static final long serialVersionUID = 1L;
	private VirtualStorage vs;
	public NewCategoryView() {
		//GUI
		setTitle("Neue Kategorie erstellen");
		setSize(400,150);
		setLocationRelativeTo(null);
		setResizable(false);
				
		//Label
		JLabel lname = new JLabel("Kategoriename");
			
		//Text area
		JTextField taname = new JTextField(15);
				
		//Button
		JButton button = new JButton ("Save");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = taname.getText();
		        System.out.println("contents = "+ name);
			}
		});
		
		//Layout
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
		pane.add(lname);
		pane.add(taname);
		pane.add(button);
	
	}
	public void setVirtualStorage(VirtualStorage vs) {
		this.vs = vs;
	}
}