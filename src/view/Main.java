package view;

import java.awt.Dimension;

import controller.VirtualStorage;

/**Das ist die Main View.
 * Sie verbindet die Frames miteinander, die eine Beziehung haben. Und initalisiert alle Frames mit dem MainVirtualStorage.
 * 
 * @author Freddy
 *
 */
public class Main {

	public Main() {
		//Initialisiert die Hauptinstanze von VirtualStorage.
		VirtualStorage MainVirtualStorage=new VirtualStorage();
		
		//Erstellte alle Frames und verbindet sie mit MainVirtualStorage (außer SplashView)
		
		//Ladebild zu Programmstart
		SplashView loadingView =new SplashView();
		
		//Inventaransicht
		InventoryView inventoryView = new InventoryView(MainVirtualStorage);
			inventoryView.setMinimumSize(new Dimension(600, 200));	
		//Neuer Inventareintrag
		NewArticleView newArticleView = new NewArticleView(MainVirtualStorage);
		//Detailiert Artikelansicht
		ArticleView articleView = new ArticleView(MainVirtualStorage);	
		//Kategorienansicht
		CategoriesView categoriesView = new CategoriesView(MainVirtualStorage);	
		//Kategorie Detailansicht
		CategoryView categoryView = new CategoryView(MainVirtualStorage);
		//Neue Kategorie
		NewCategoryView newCategoryView = new NewCategoryView(MainVirtualStorage);
		
		loadingView.setVisible(true);
		
		/*
		loadingView.setVirtualStorage(MainVirtualStorage);
		inventoryView.setVirtualStorage(MainVirtualStorage);
		articleView.setVirtualStorage(MainVirtualStorage);
		categoriesView.setVirtualStorage(MainVirtualStorage);
		categoryView.setVirtualStorage(MainVirtualStorage);
		newArticleView.setVirtualStorage(MainVirtualStorage);*/
		
		
		//Verbindet alle Frames undereinander, die eine Beziehung haben.
		loadingView.setInventoryView(inventoryView);
		inventoryView.setNewArticleView(newArticleView);
		inventoryView.setArticleView(articleView);
		inventoryView.setCategoriesView(categoriesView);
		articleView.setInventoryView(inventoryView);
		newArticleView.setInventoryView(inventoryView);
		categoriesView.setCategoryView(categoryView);
		categoryView.setCategoriesView(categoriesView);
		categoriesView.setNewCategoriesView(newCategoryView);
		newCategoryView.setCategoriesView(categoriesView);
	}
	
}