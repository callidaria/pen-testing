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
import models.Position;

public class Database implements DatabaseInterface {

	public ArrayList<Position> retrieveStoredProducts(String query) {
		ArrayList<Position> positions = new ArrayList<Position>();
		File xmlFile = new File("xml/positions.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		int shelfSection;
		int shelfPlace;
		//int count;
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("position");
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			System.out.println("Stored positions: "+nList.getLength());
			for(int i = 0; i < nList.getLength();i++) {
				Element node = (Element) nList.item(i);
				//System.out.println("Node ("+i+"):");
				int productID = Integer.parseInt(node.getElementsByTagName("product_id").item(0).getTextContent());
				shelfSection = Integer.parseInt(node.getElementsByTagName("shelf_section").item(0).getTextContent());
				shelfPlace = Integer.parseInt(node.getElementsByTagName("shelf_place").item(0).getTextContent());
				// count = Integer.parseInt(node.getElementsByTagName("count").item(0).getTextContent());
				//System.out.println("\tproduct_id:"+productID);
				//System.out.println("\tposition:"+shelfSection+shelfPlace);
				//System.out.println("\tcount:"+count);
				
				Position position = new Position(shelfSection,shelfPlace,productID);
				positions.add(position);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		
		return positions;
	}
	
	
	public ArrayList<Position> fetchStoredProducts(Object[] options) {
		ArrayList<Position> list = new ArrayList<Position>();
		if(options==null) {
			list = this.retrieveStoredProducts(null);
		}
		else {
			throw new RuntimeException();
		}
		// TODO Auto-generated method stub
		return list;
	}


}
