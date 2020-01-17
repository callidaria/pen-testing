package view;

import java.awt.Dimension;

import javax.swing.JFrame;

import controller.VirtualStorage;

/**Das ist die Main View. Sie verbindet die Frames miteinander, die eine Beziehung haben. Und verbindet alle Frames mit dem MainVirtualStorage.
 * 
 * @author Freddy
 *
 */
public class Main {

	public Main() {
		SplashView loadingView =new SplashView();
		InventoryView inventoryView = new InventoryView();
			inventoryView.setMinimumSize(new Dimension(600, 200));
		NewArticleView newArticleView = new NewArticleView();
		ArticleView articleView = new ArticleView();
		CategoriesView categoriesView = new CategoriesView();
		CategoryView categoryView = new CategoryView();
		NewCategoryView newCategoryView = new NewCategoryView();
		
		loadingView.setVisible(true);
		
		VirtualStorage MainVirtualStorage=new VirtualStorage();
		
		loadingView.setVirtualStorage(MainVirtualStorage);
		inventoryView.setVirtualStorage(MainVirtualStorage);
		articleView.setVirtualStorage(MainVirtualStorage);
		categoriesView.setVirtualStorage(MainVirtualStorage);
		newArticleView.setVirtualStorage(MainVirtualStorage);
		newCategoryView.setVirtualStorage(MainVirtualStorage);
		
		loadingView.setInventoryView(inventoryView);
		inventoryView.setNewArticleView(newArticleView);
		inventoryView.setArticleView(articleView);
		inventoryView.setCategoriesView(categoriesView);
		articleView.setInventoryView(inventoryView);
		newArticleView.setInventoryView(inventoryView);
		categoriesView.setCategoryView(categoryView);
		
		

		
		
	}
	
}
