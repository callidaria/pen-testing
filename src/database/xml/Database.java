package database.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
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
	
	static final String DBPATH_IE="inventoryEntries.xml";
	
	private static Document buildDocument(String file) throws SAXException, IOException {
		File inventoryEntriesFile = new File(DBPATH+file);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(inventoryEntriesFile);
			doc.getDocumentElement().normalize();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	
	private static Node xpathNode(Document doc,String xpath_query) {
		Node node = null;
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			node = (Node) xPath.compile(Database.escapeString(xpath_query)).evaluate(doc, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return node;
		
	}
	
	private static void transform(Document doc, String target) {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(DBPATH+target));
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
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
				//int productID = Integer.parseInt(node.getAttributes().getNamedItem("id").getTextContent());
				int shelfSection = Integer.parseInt(node.getAttributes().getNamedItem("section").getTextContent());
				int shelfPlace = Integer.parseInt(node.getAttributes().getNamedItem("place").getTextContent());
				// count = Integer.parseInt(node.getElementsByTagName("count").item(0).getTextContent());
				//System.out.println("\tproduct_id:"+productID);
				//System.out.println("\tposition:"+shelfSection+shelfPlace);
				//System.out.println("\tcount:"+count);
				String productName = node.getElementsByTagName("name").item(0).getTextContent();
				int productWeight = Integer.parseInt(node.getElementsByTagName("weight").item(0).getTextContent());
				int productPrize = Integer.parseInt(node.getElementsByTagName("prize").item(0).getTextContent());
				int productCount = Integer.parseInt(node.getElementsByTagName("count").item(0).getTextContent());
				//int productCategoryID = Integer.parseInt(subNode.getElementsByTagName("category_id").item(0).getTextContent());
				
				//Product product = new Product(productID,productName,productCount);
				Product product = new Product(productName,productCount,productWeight,productPrize);
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
				//int id = Integer.parseInt(node.getAttributes().getNamedItem("id").getTextContent());
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
	
	public static void addInventoryEntry(InventoryEntry newIE) throws Exception{
		System.out.println("WARNING METHODE: addInventoryEntry IS WORK IN PROGRESS!\nYour database might become corrupted!");
		if(!newIE.validate()) {
			throw new Exception("New InventoryEntry failed validation.");
		}
		
		if(Database.uidExists(newIE.getUID())){
			throw new Exception("New InventoryEntry UID taken.");
		}
		
		
		if (Database.nameExists(newIE.product.getName()) != -1) {
			System.out.print("NAME TAKEN");
			throw new Exception("@editInventoryEntry newName is taken.");
		}
		
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
			
			newEntry.setAttribute("section",Integer.toString(newIE.getShelfSection()));
			newEntry.setAttribute("place",Integer.toString(newIE.getShelfPlace()));
			
			
			Element newName = doc.createElement("name");
			Element newCount = doc.createElement("count");
			Element newWeight = doc.createElement("weight");
			Element newPrize = doc.createElement("prize");
			Element newCategoryId = doc.createElement("category_id");
			
			newName.appendChild(doc.createTextNode(newIE.product.getName()));
			
			newCount.appendChild(doc.createTextNode(Integer.toString(newIE.product.getCount())));
		
			newWeight.appendChild(doc.createTextNode(Integer.toString(newIE.product.getWeight())));
			
			newPrize.appendChild(doc.createTextNode(Integer.toString(newIE.product.getPrize())));
			
			//newCategoryId.appendChild(doc.createTextNode(Integer.toString(newIE.product.getCategoryID())));
						
			
			
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
	
	public static InventoryEntry replaceInventoryEntry(int UID,InventoryEntry newIE) throws Exception{
		return Database.replaceInventoryEntry(UID, newIE, false);
	}
	   /**
	    * Edit a InventoryEntry
	    * @param UID edit Entry with this UID
	    * @see #setArea(String)
	    * @see #setExchange(String)
	    * @see #setExtension(String)
	    * @throws Exception in case of invalid value
	    */
	public static InventoryEntry replaceInventoryEntry(int UID,InventoryEntry newIE,Boolean force) throws Exception{
		System.out.println("WARNING METHODE: editInventoryEntry IS WORK IN PROGRESS!\nYour database might become corrupted!");
		
		if(!newIE.validate()) {
			throw new Exception("@editInventoryEntry newInventoryEntry failed validation.");
		}
		
		if(!Database.uidExists(UID)) {
			throw new Exception("@editInventoryEntry UID doesn't exisit.");
		}
		if (newIE.getUID()!=UID) {
			if(Database.uidExists(newIE.getUID())) {
				if(force==true) {
					throw new Exception("@editInventoryEntry newUID is taken.");
				}
				else {
					System.out.println("@editInventoryEntry forced overwrite of "+newIE.getUID());
				}
			}
		}
		if (Database.nameExists(newIE.product.getName())!=UID && Database.nameExists(newIE.product.getName())!=-1) {
			throw new Exception("@editInventoryEntry newName is taken.");
		}
		try {

			Document doc = Database.buildDocument(DBPATH_IE);
	
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
					   for (int n = 0; n < nodeChilds.getLength(); n++) {
						   Node subNode = nodeChilds.item(n);
						   if ("name".equals(subNode.getNodeName())) {
							   System.out.println("Replace Name: "+subNode.getTextContent());
							   subNode.setTextContent(newIE.product.getName());
							   //break;
						   }
					   }
					   System.out.print("Found: "+entryUID+" (Name altered)");
					   
				   }
			   }
			}
	
			// write the content into xml file
			
			Database.transform(doc, DBPATH_IE);
	
			System.out.println("Done");
	
		   } catch (IOException ioe) {
			ioe.printStackTrace();
		   } catch (SAXException sae) {
			sae.printStackTrace();
		   }
		return newIE;
		
	}

	public static void editAttributeOfInventoryEntry(int UID, String attribute, String newValue) throws Exception{
		System.out.println("WARNING work in progress!\n@editAttributeOfInventoryEntry doesn't yet check for any constraints");
		Document doc = Database.buildDocument(DBPATH_IE);
		try {
			Boolean attributeExists = false;
			Element node;
			int[] sectionPlace = InventoryEntry.uidToSectionPlace(UID);
	        int section=sectionPlace[0];
	        int place=sectionPlace[1];
			node = (Element) Database.xpathNode(doc,"/entries/entry[@section="+section+" and @place="+place+"]");
			NodeList nodeChilds = node.getChildNodes();
			   for (int i = 0; i < nodeChilds.getLength(); i++) {
				   Node subNode = nodeChilds.item(i);
				   if (subNode.getNodeName()==attribute) {
					   System.out.println("SetAttribute ("+subNode.getNodeName()+"):"+subNode.getTextContent());
					   subNode.setTextContent(newValue);
					   attributeExists=true;
				   }
			   }
			if(!attributeExists) {
				throw new Exception("@editAttributeOfInventoryEntry Attribute doesn't exist.");
			}
			Database.transform(doc, DBPATH_IE);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return;
	}
	
	public static void deleteInventoryEntry(int UID, Boolean force) throws Exception{
		System.out.println("WARNING work in progress!");
		Document doc = Database.buildDocument(DBPATH_IE);

			Element node;
			int[] sectionPlace = InventoryEntry.uidToSectionPlace(UID);
	        int section=sectionPlace[0];
	        int place=sectionPlace[1];
			node = (Element) Database.xpathNode(doc,"/entries/entry[@section="+section+" and @place="+place+"]");
			NodeList nodeChilds = node.getChildNodes();
			   for (int i = 0; i < nodeChilds.getLength(); i++) {
				   Node subNode = nodeChilds.item(i);
				   if (subNode.getNodeName()=="count" && Integer.parseInt(subNode.getTextContent())!=0 && force==false) {
					   throw new Exception("@deleteInventoryEntry count is ("+subNode.getTextContent()+") should be zero, use force delete or set count to zero.");
				   }
			   }
			node.getParentNode().removeChild(node);
			Database.transform(doc, DBPATH_IE);
		return;
	}
	
	public static int nameExists(String name) {
		try {
	        Document doc = Database.buildDocument("inventoryEntries.xml");
	        Element node;
			node = (Element) Database.xpathNode(doc,"/entries/entry[name/text()='"+name+"']");
			if (node!=null) {
				int shelfSection = Integer.parseInt(node.getAttributes().getNamedItem("section").getTextContent());
				int shelfPlace = Integer.parseInt(node.getAttributes().getNamedItem("place").getTextContent());
				return InventoryEntry.sectionPlaceToUID(shelfSection, shelfPlace);
			};
		}  catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public static boolean uidExists(int uid) {
		try {
	        Document xmlDocument = Database.buildDocument("inventoryEntries.xml");
	        XPath xPath = XPathFactory.newInstance().newXPath();
	        Element subNode;
	        int[] sectionPlace = InventoryEntry.uidToSectionPlace(uid);
	        int section=sectionPlace[0];
	        int place=sectionPlace[1];
			subNode = (Element) xPath.compile("/entries/entry[@section="+section+" and @place="+place+"]").evaluate(xmlDocument, XPathConstants.NODE);
			if (subNode!=null) {
				return true;
			};
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
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
	
	public static String escapeString(String unescapedString) {
		return unescapedString;
	}
	
	


}
