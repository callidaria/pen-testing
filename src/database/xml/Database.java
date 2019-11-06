package database.xml;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import database.DatabaseInterface;
import logic.interfaces.StoredProductInterface;

public class Database implements DatabaseInterface {

	@Override
	public Connection connect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<StoredProductInterface> retrieveStoredProducts(String query) {
		File xmlFile = new File("xml/positions.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("position");
		Element firstNode = (Element) nList.item(0);
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		System.out.println("First Node:");
		System.out.println("\tROWID:"+firstNode.getAttribute("ROWID"));
		System.out.println("\tproduct_id:"+firstNode.getElementsByTagName("product_id").item(0).getTextContent());
		System.out.println("\tposition:"+firstNode.getElementsByTagName("shelf_section").item(0).getTextContent()+firstNode.getElementsByTagName("shelf_place").item(0).getTextContent());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ArrayList<StoredProductInterface> fetchStoredProducts(Object[] options) {
		if(options==null) {
			this.retrieveStoredProducts(null);
		}
		else {
			throw new RuntimeException();
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<StoredProductInterface> searchStoredProducts(String productName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addStoredProduct(StoredProductInterface storedProduct) {
		// TODO Auto-generated method stub
		return false;
	}

}
