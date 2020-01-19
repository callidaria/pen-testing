package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.VirtualStorage;

public class SplashView extends JFrame {
	private static final long serialVersionUID = 1L;
	private VirtualStorage vs;
	private InventoryView inventoryView;

	public SplashView() {
		setContentPane(new JPanel());
		setTitle("Loading");
		setSize(480,360);
	}
	public void LoadingViewHide() {
		setVisible(false);
		inventoryView.setVisible(true);
	}

	public void setVirtualStorage(VirtualStorage mainVirtualStorage) {
		this.vs=mainVirtualStorage;
		
	}

	public void setInventoryView(InventoryView inventoryView) {
		this.inventoryView=inventoryView;
		LoadingViewHide();
	}
}
