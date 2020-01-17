package view;

import java.awt.Dimension;

import javax.swing.JFrame;

import controller.VirtualStorage;

public class Main {

	public Main() {
		SplashFrame loadingView =new SplashFrame();
		InventoryView inventoryView = new InventoryView();
			inventoryView.setMinimumSize(new Dimension(600, 200));
		NewArticleView newArticleView = new NewArticleView();
		ArticleView articleView = new ArticleView();
		CategoriesView categoriesView = new CategoriesView();
		CategoryView categoryView = new CategoryView();
		
		loadingView.setVisible(true);
		
		VirtualStorage MainVirtualStorage=new VirtualStorage();
		
		loadingView.setVirtualStorage(MainVirtualStorage);
		inventoryView.setVirtualStorage(MainVirtualStorage);
		articleView.setVirtualStorage(MainVirtualStorage);
		categoriesView.setVirtualStorage(MainVirtualStorage);
		newArticleView.setVirtualStorage(MainVirtualStorage);
		
		loadingView.setInventoryView(inventoryView);
		inventoryView.setNewArticleView(newArticleView);
		inventoryView.setArticleView(articleView);
		inventoryView.setCategoriesView(categoriesView);
		articleView.setInventoryView(inventoryView);
		newArticleView.setInventoryView(inventoryView);
		categoriesView.setCategoryView(categoryView);
		
		

		
		
	}
	
}
