package view;

import java.awt.Dimension;

import javax.swing.JFrame;

import controller.VirtualStorage;

public class Main {

	public Main() { 
		InventoryView inventoryView = new InventoryView();
		inventoryView.setMinimumSize(new Dimension(600, 200));
		NewArticleView newArticleView = new NewArticleView();
		
		inventoryView.setNewArticleView(newArticleView);
		newArticleView.setInventoryView(inventoryView);
		
		inventoryView.setVisible(true);
	}
	
}
