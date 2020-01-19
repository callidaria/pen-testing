package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import basic.Category;
import controller.VirtualStorage;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Wird angezeigt beim Doppelklick auf die Kategorientabellen
 * hier wird der Kategoriename einer bestehenden Kategorie verändert.
 */

public class CategoryView extends JFrame {
	
	private VirtualStorage vs;
	private CategoriesView categoriesView;
	public CategoryView(VirtualStorage vs) {
		this.vs=vs;
		//GUI
		setTitle("Kategorie Ansicht");
		setSize(400,150);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
	}
	public void showCategoryView(int UID) {
		setContentPane(new JPanel());
		Category thisCategory = vs.getCategoryByUID(UID);
		//Label
		JLabel lSelected = new JLabel("Ausgewählte Kategorie:");
		JLabel lOldName = new JLabel(thisCategory.getName());
		JLabel lNewName = new JLabel("Neuer Kategoriename:");

		JTextField tfName = new JTextField(10);
		tfName.setText(thisCategory.getName());
		//Button
		JButton bSave = new JButton ("Speichern");
		bSave.addActionListener(new ActionListener() { 
			//Nach Buttonklick werden die Änderungen gespeichert.
			@Override
			public void actionPerformed(ActionEvent e) {
				vs.loadCategoryStorage();
				vs.loadVirtualStorage();
				String newName = tfName.getText();
				int success=vs.renameCategory(UID, newName);
				if(success==0) {
					JOptionPane.showMessageDialog(getContentPane(),"Kategorie umbennant","Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					dispose();
					categoriesView.refresh();
				}else if(success==-1){
					JOptionPane.showMessageDialog(getContentPane(),"Dieser Name existiert bereits.","Fehler", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(getContentPane(),"Unbekannter Fehler","Fehler", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		JButton bDelete = new JButton ("Löschen");
		bDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(getContentPane(), "Kategorie Löschen?","Bestätigen",JOptionPane.YES_NO_OPTION)==0) {
					//try {
						vs.removeCategory(UID);
						JOptionPane.showMessageDialog(getContentPane(),"Kategorie gelöscht","Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
						setVisible(false);
						dispose();
						categoriesView.refresh();
					/*} catch (Exception e1) {
						JOptionPane.showMessageDialog(getContentPane(),"Unbekannter Fehler beim Löschen. Wenden sie sich bitte an den Entwickler!","Fehler", JOptionPane.INFORMATION_MESSAGE);
						e1.printStackTrace();
						System.out.println(e1.getMessage());
					}*/
				}
			}
		});
		
		//Layout Hier wird die Reihenfolge der vorher deklarierten Elemente festgelegt, und ob sie überhaupt dargestellt sind.
		
		Container pane = getContentPane();
		pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (true) {
            c.fill = GridBagConstraints.HORIZONTAL;
        }
        c.gridx = 0;
        c.gridy = 1;
		pane.add(lSelected,c);
		
		c.gridx = 1;
        c.gridy = 1;
		pane.add(lOldName,c);
		
		c.gridx = 0;
        c.gridy = 2;
		pane.add(lNewName,c);
		
		c.gridx = 1;
        c.gridy = 2;
		pane.add(tfName,c);
		
		c.gridx = 0;
        c.gridy = 4;
		pane.add(bDelete,c);
		
		c.gridx = 1;
        c.gridy = 4;
		pane.add(bSave,c);
	}
	public void setCategoriesView(CategoriesView categoriesView) {
		this.categoriesView = categoriesView;
	}
}
