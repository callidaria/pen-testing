package database.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import database.DatabaseInterface;
import model.InventoryEntry;
import model.Product;

public class Database implements DatabaseInterface {

	static final String DBPATH="data/xml/";
	public static ArrayList<InventoryEntry> retrieveInventoryEntries() {
		ArrayList<InventoryEntry> inventoryEntries = new ArrayList<InventoryEntry>();
		File inventoryEntriesFile = new File(DBPATH+"inventoryEntries.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		//int count;
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inventoryEntriesFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("entry");
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			System.out.println("Stored inventoryEntries: "+nList.getLength());
			for(int i = 0; i < nList.getLength();i++) {
				Element node = (Element) nList.item(i);
				//System.out.println("Node ("+i+"):");
				int productID = Integer.parseInt(node.getAttributes().getNamedItem("id").getTextContent());
				int shelfSection = Integer.parseInt(node.getAttributes().getNamedItem("section").getTextContent());
				int shelfPlace = Integer.parseInt(node.getAttributes().getNamedItem("place").getTextContent());
				// count = Integer.parseInt(node.getElementsByTagName("count").item(0).getTextContent());
				//System.out.println("\tproduct_id:"+productID);
				//System.out.println("\tposition:"+shelfSection+shelfPlace);
				//System.out.println("\tcount:"+count);
				String productName = node.getElementsByTagName("name").item(0).getTextContent();
				//int productWeight = Integer.parseInt(subNode.getElementsByTagName("weight").item(0).getTextContent());
				//int productPrize = Integer.parseInt(subNode.getElementsByTagName("prize").item(0).getTextContent());
				int productCount = Integer.parseInt(node.getElementsByTagName("count").item(0).getTextContent());
				//int productCategoryID = Integer.parseInt(subNode.getElementsByTagName("category_id").item(0).getTextContent());
				
				Product product = new Product(productID,productName,productCount);
				InventoryEntry position = new InventoryEntry(shelfSection,shelfPlace,product);
				inventoryEntries.add(position);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		
		return inventoryEntries;
	}
	
	/**
	 * @deprecated use retrieveInventoryEntries instead.  
	 */
	@Deprecated
	public static ArrayList<Product> retrieveProducts() {
		ArrayList<Product> inventoryEntries = new ArrayList<Product>();
		File xmlFile = new File(DBPATH+"inventoryEntries.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("entry");
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			System.out.println("Stored inventoryEntries: "+nList.getLength());
			for(int i = 0; i < nList.getLength();i++) {
				Element node = (Element) nList.item(i);
				//System.out.println("Node ("+i+"):");
				int id = Integer.parseInt(node.getAttributes().getNamedItem("id").getTextContent());
				String name = node.getElementsByTagName("name").item(0).getTextContent();
				int count = Integer.parseInt(node.getElementsByTagName("count").item(0).getTextContent());
				int weight = Integer.parseInt(node.getElementsByTagName("weight").item(0).getTextContent());
				int prize = Integer.parseInt(node.getElementsByTagName("prize").item(0).getTextContent());
				// count = Integer.parseInt(node.getElementsByTagName("count").item(0).getTextContent());
				/*
				System.out.println("\tid:"+id);
				System.out.println("\tname:"+name);
				System.out.println("\tcount:"+count);
				*/
				Product position = new Product(name,count,weight,prize);
				inventoryEntries.add(position);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		
		return inventoryEntries;
	}
	
	public static void addInventoryEntry(InventoryEntry newIE) throws TransformerException{
		System.out.println("WARNING METHODE: addInventoryEntry IS WORK IN PROGRESS!\nYour database is now corrupted!");
		try {
			String filepath = DBPATH+"inventoryEntries.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			// Get the root element
			Node entries = doc.getFirstChild();

			// Get the staff element , it may not working if tag has spaces, or
			// whatever weird characters in front...it's better to use
			// getElementsByTagName() to get it directly.
			// Node staff = company.getFirstChild();

			// Get the staff element by tag name directly
			Element newEntry = doc.createElement("entry");
			newEntry.setAttribute("id","-1");
			newEntry.setAttribute("place",Integer.toString(newIE.getShelfPlace()));
			newEntry.setAttribute("section",Integer.toString(newIE.getShelfSection()));
			
			
			Element newName = doc.createElement("name");
			Element newCount = doc.createElement("count");
			Element newWeight = doc.createElement("weight");
			Element newPrize = doc.createElement("prize");
			Element newCategoryId = doc.createElement("category_id");
			
			newName.appendChild(doc.createTextNode(newIE.product.getName()));
			
			newCount.appendChild(doc.createTextNode(Integer.toString(newIE.product.getCount())));
		
			newWeight.appendChild(doc.createTextNode(Integer.toString(newIE.product.getWeight())));
			
			newPrize.appendChild(doc.createTextNode(Integer.toString(newIE.product.getPrize())));
			
			newCategoryId.appendChild(doc.createTextNode(Integer.toString(newIE.product.getCategoryID())));
						
			
			
			newEntry.appendChild(newName);
			newEntry.appendChild(newCount);
			newEntry.appendChild(newWeight);
			newEntry.appendChild(newPrize);
			newEntry.appendChild(newCategoryId);
			
			entries.appendChild(newEntry);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);

			System.out.println("Done");

		   } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		   } catch (TransformerException tfe) {
			tfe.printStackTrace();
		   } catch (IOException ioe) {
			ioe.printStackTrace();
		   } catch (SAXException sae) {
			sae.printStackTrace();
		   }
	}
	
	public static InventoryEntry editInventoryEntry(int UID,InventoryEntry newIE) throws TransformerException{
		System.out.println("WARNING METHODE: editInventoryEntry IS WORK IN PROGRESS!\nYour database is now corrupted!");
		
		try {
			newIE.validate();
			Database.nameExists(newIE.product.getName());
			Database.uidExists(newIE.getUID());
		} catch(Exception ex){
			System.out.println(ex);
		}
		
		try {
			String filepath = DBPATH+"inventoryEntries.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			Node entries = doc.getFirstChild();
			NodeList entryList = entries.getChildNodes();
			

			for (int i = 0; i < entryList.getLength(); i++) {
				
               Node node = entryList.item(i);
               //Element elNode = (Element) node;
               NamedNodeMap attr = node.getAttributes();
               

			   // get the salary element, and update the value
			   if ("entry".equals(node.getNodeName())) {
				   int entryUID=Integer.parseInt(attr.getNamedItem("place").getTextContent()+attr.getNamedItem("section").getTextContent());
				   
				   if (entryUID == UID) {
					   NodeList nodeChilds = node.getChildNodes();
					   for (int n = 0; i < nodeChilds.getLength(); n++) {
						   Node subNode = nodeChilds.item(n);
						   if ("name".equals(subNode.getNodeName())) {
							   System.out.println("Name"+subNode.getTextContent());
							   subNode.setTextContent(newIE.product.getName());
							   break;
						   }
					   }
					   System.out.print("Found: "+entryUID+" (Name altered)");
					   
				   }
			   }
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);

			System.out.println("Done");

		   } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		   } /*catch (TransformerException tfe) {
			tfe.printStackTrace();
		   } */catch (IOException ioe) {
			ioe.printStackTrace();
		   } catch (SAXException sae) {
			sae.printStackTrace();
		   }
		return newIE;
	}
	
	private static boolean nameExists(String name) {
		return true;
	}
	
	private static boolean uidExists(int uid) {
		return true;
	}
	
	/*
	 * example for how to use transform factory
	 * 
	public static void saveToXML() {
		try {
			String filepath = DBPATH+"file.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			// Get the root element
			Node company = doc.getFirstChild();

			// Get the staff element , it may not working if tag has spaces, or
			// whatever weird characters in front...it's better to use
			// getElementsByTagName() to get it directly.
			// Node staff = company.getFirstChild();

			// Get the staff element by tag name directly
			Node staff = doc.getElementsByTagName("staff").item(0);
			Element newEntry = doc.createElement("entry");

			// update staff attribute
			NamedNodeMap attr = staff.getAttributes();
			Node nodeAttr = attr.getNamedItem("id");
			nodeAttr.setTextContent("2");

			// append a new node to staff

			// loop the staff child node
			NodeList list = staff.getChildNodes();

			for (int i = 0; i < list.getLength(); i++) {
				
	                   Node node = list.item(i);

			   // get the salary element, and update the value
			   if ("salary".equals(node.getNodeName())) {
				node.setTextContent("2000000");
			   }

	                   //remove firstname
			   if ("firstname".equals(node.getNodeName())) {
				staff.removeChild(node);
			   }

			}
			Element newElement = doc.createElement("NewEl");
			newElement.appendChild(doc.createTextNode("New"));
			newEntry.appendChild(newElement);
			company.appendChild(newEntry);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);

			System.out.println("Done");

		   } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		   } catch (TransformerException tfe) {
			tfe.printStackTrace();
		   } catch (IOException ioe) {
			ioe.printStackTrace();
		   } catch (SAXException sae) {
			sae.printStackTrace();
		   }
	}*/
	
	public static Boolean editInventoryEntry() {
		
		
		return false;
	}


}
