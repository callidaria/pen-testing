package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import controller.VirtualStorage;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewCategoryView extends JFrame {
	private static final long serialVersionUID = 1L;
	private VirtualStorage vs;
	private CategoriesView categoriesView;
	public NewCategoryView(VirtualStorage vs) {
		this.vs=vs;
		//GUI
		setTitle("Neue Kategorie erstellen");
		setSize(400,150);
		setLocationRelativeTo(null);
		setResizable(false);
				
		//Label
		JLabel lName = new JLabel("Kategoriename");
			
		//Text area
		JTextField tfName = new JTextField(15);
				
		//Button
		JButton bSave = new JButton ("Save");
		bSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = tfName.getText();
				vs.loadCategoryStorage();
				int err_code = vs.addCategory(name); //breakable
				if (err_code==0) {
					JOptionPane.showMessageDialog(getContentPane(),"Kategorie wurde hinzugefügt","Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
					categoriesView.refresh();
					setVisible(false);
					dispose();
				}
				else if (err_code==-1) JOptionPane.showMessageDialog(getContentPane(),"Kategoriename bereits vergeben","Fehler", JOptionPane.INFORMATION_MESSAGE);
				else JOptionPane.showMessageDialog(getContentPane(),"Unbekannter Fehler. Bitte wenden Sie sich an den Entwickler!","Fehler", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		//Layout
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
		pane.add(lName);
		pane.add(tfName);
		pane.add(bSave);
	
	}
	public void setCategoriesView(CategoriesView categoriesView) {
		this.categoriesView = categoriesView;
	}
	
}