package database.xml;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.Node;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

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
		File inventoryEntriesFile = new File(this.DBPATH+"inventoryEntries.xml");
		File productsFile = new File(this.DBPATH+"products.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		//int count;
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inventoryEntriesFile);
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
				
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = builderFactory.newDocumentBuilder();
				Document xmlDocument = builder.parse(productsFile);
				XPath xPath = XPathFactory.newInstance().newXPath();
				Element subNode = (Element) xPath.compile("/products/product[@id="+productID+"]").evaluate(xmlDocument, XPathConstants.NODE);
				String productName = subNode.getElementsByTagName("name").item(0).getTextContent();
				//int productWeight = Integer.parseInt(subNode.getElementsByTagName("weight").item(0).getTextContent());
				//int productPrize = Integer.parseInt(subNode.getElementsByTagName("prize").item(0).getTextContent());
				int productCount = Integer.parseInt(subNode.getElementsByTagName("count").item(0).getTextContent());
				//int productCategoryID = Integer.parseInt(subNode.getElementsByTagName("category_id").item(0).getTextContent());
				
				Product product = new Product(productID,productName,productCount);
				InventoryEntry position = new InventoryEntry(shelfSection,shelfPlace,productID,product);
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
