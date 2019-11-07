package database.xml;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import database.DatabaseInterface;
import models.InventoryEntry;
import models.Product;

public class Database implements DatabaseInterface {

	final String DBPATH="xml/";
	public ArrayList<InventoryEntry> retrieveInventoryEntry() {
		ArrayList<InventoryEntry> inventoryEntries = new ArrayList<InventoryEntry>();
		File xmlFile = new File(this.DBPATH+"inventoryEntries.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		//int count;
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("position");
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			System.out.println("Stored inventoryEntries: "+nList.getLength());
			for(int i = 0; i < nList.getLength();i++) {
				Element node = (Element) nList.item(i);
				//System.out.println("Node ("+i+"):");
				int productID = Integer.parseInt(node.getElementsByTagName("product_id").item(0).getTextContent());
				int shelfSection = Integer.parseInt(node.getAttributes().getNamedItem("section").getTextContent());
				int shelfPlace = Integer.parseInt(node.getAttributes().getNamedItem("place").getTextContent());
				// count = Integer.parseInt(node.getElementsByTagName("count").item(0).getTextContent());
				//System.out.println("\tproduct_id:"+productID);
				//System.out.println("\tposition:"+shelfSection+shelfPlace);
				//System.out.println("\tcount:"+count);
				
				InventoryEntry position = new InventoryEntry(shelfSection,shelfPlace,productID);
				inventoryEntries.add(position);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		
		return inventoryEntries;
	}
	
	
	public ArrayList<Product> retrieveProducts() {
		ArrayList<Product> inventoryEntries = new ArrayList<Product>();
		File xmlFile = new File(this.DBPATH+"products.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("product");
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			System.out.println("Stored inventoryEntries: "+nList.getLength());
			for(int i = 0; i < nList.getLength();i++) {
				Element node = (Element) nList.item(i);
				//System.out.println("Node ("+i+"):");
				int id = Integer.parseInt(node.getAttributes().getNamedItem("id").getTextContent());
				String name = node.getElementsByTagName("name").item(0).getTextContent();
				int count = Integer.parseInt(node.getElementsByTagName("count").item(0).getTextContent());
				// count = Integer.parseInt(node.getElementsByTagName("count").item(0).getTextContent());
				/*
				System.out.println("\tid:"+id);
				System.out.println("\tname:"+name);
				System.out.println("\tcount:"+count);
				*/
				Product position = new Product(id,name,count);
				inventoryEntries.add(position);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		
		return inventoryEntries;
	}


}
