package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.VirtualStorage;

public class SplashView extends JFrame {
	private static final long serialVersionUID = 1L;
	private InventoryView inventoryView;

	/**
	 * Ein Fenster was nur am Anfang beim Laden angezeigt wird.
	 */
	public SplashView() {
		setContentPane(new JPanel());
		setTitle("Loading");
		setSize(480,360);
	}
	
	/**
	 * Frame entfernt sich selber und öffnent inventoryView
	 */
	public void LoadingViewHide() {
		setVisible(false);
		dispose();
		inventoryView.setVisible(true);
	}

	/**Verbindet mit inventoryView und öffnent dann LoadingViewHide()
	 * 
	 * @param inventoryView link zur Inventory View
	 */
	public void setInventoryView(InventoryView inventoryView) {
		this.inventoryView=inventoryView;
		LoadingViewHide();
	}
}
