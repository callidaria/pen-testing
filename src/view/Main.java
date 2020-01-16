package view;

import java.awt.Dimension;

import javax.swing.JFrame;

import controller.VirtualStorage;

public class Main {

	public Main() {
		VirtualStorage MainVirtualStorage=new VirtualStorage();
		
		InventoryView inventoryView = new InventoryView();
		inventoryView.setVirtualStorage(MainVirtualStorage);
		
		inventoryView.setMinimumSize(new Dimension(600, 200));
		NewArticleView newArticleView = new NewArticleView();
		ArticleView articleView = new ArticleView();
		
		articleView.setVirtualStorage(MainVirtualStorage);
		inventoryView.setNewArticleView(newArticleView);
		inventoryView.setArticleView(articleView);
		newArticleView.setInventoryView(inventoryView);
		
		inventoryView.setVisible(true);

		
		
	}
	
}
