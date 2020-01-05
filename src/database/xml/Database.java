package database.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.XMLConstants;
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
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
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

import model.Category;
import model.InventoryEntry;
import model.Product;

public class Database{
	
	//**Der relative Pfad von root zu den Datenbankdateien
	static final String DBPATH="data/xml/";
	
	//Dateiname von Inventareinträgen	
	static final String DBPATH_IE="inventoryEntries.xml";
	
	//Dateiname der Definitionsdatei von Inventareinträgen
	static final String DBPATH_IE_XSD="inventoryEntries.xsd";
	
	//Dateiname von Kategorien	
		static final String DBPATH_CAT="categories.xml";
		
	//Dateiname der Definitionsdatei von Kategorien
	static final String DBPATH_CAT_XSD="categories.xsd";
	
	/** 	 
	 * @return alle Inventareinträge aus der Datenbank als ArrayList. Die ArrayList ist unsortiert.
	 */
	public static ArrayList<InventoryEntry> retrieveInventoryEntries(){
		ArrayList<InventoryEntry> inventoryEntries = new ArrayList<InventoryEntry>();
		try {
			Document doc = Database.buildDocument(DBPATH_IE);
			NodeList nList = doc.getElementsByTagName("entry");
			//System.out.println("Root element:" + doc.getDocumentElement().getNodeName());
			//System.out.println("Stored inventoryEntries: "+nList.getLength());
			for(int i = 0; i < nList.getLength();i++) {
				Element node = (Element) nList.item(i);

				int shelfSection = Database.getAttributes(node,"section");
				int shelfPlace = Database.getAttributes(node,"place");

				String productName = Database.getElementTextContent(node,"name");
				int productWeight = Database.getElementContent(node,"weight");
				int productPrize = Database.getElementContent(node,"prize");
				int productCount = Database.getElementContent(node,"count");
				int categoryid = Database.getElementContent(node,"category_id");
				
				Product product = new Product(productName,productCount,productWeight,productPrize,categoryid);
				InventoryEntry position = new InventoryEntry(shelfSection,shelfPlace,product);
				inventoryEntries.add(position);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error @retrieveInventoryEntries:"+e.getMessage());
			return null;
		}	
		System.out.println("Retrieved Inventory Entries");
		return inventoryEntries;
	}
	
	/**
	 * Bin noch nicht so ganz zufrieden, aber funktioniert gut. Allerdings O(n^2);
	 * 
	 * @param categories Die List an Kategorie. Damit die Referenzen korrekt sind und keine Kategorie doppelt während der Laufzeit existiert.
	 * @return alle Inventareintrag mit Kategorie.
	 */
	public static ArrayList<InventoryEntry> retrieveInventoryEntriesWithCategory(ArrayList<Category> categories){
		ArrayList<InventoryEntry> inventoryEntries = Database.retrieveInventoryEntries();
		Category defaultCategory = new Category(0, "Keine Kategorie");
		try {
			for(int i=0;i < inventoryEntries.size();i++) {
				int categoryID = inventoryEntries.get(i).product.getCategoryID();
				inventoryEntries.get(i).product.setCategory(defaultCategory);
				for(int n =0;n < categories.size();n++) {
					if(categoryID==categories.get(n).getUID()) {
						inventoryEntries.get(i).product.setCategory(categories.get(n));
						break;
					}
				}
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error @retrieveInventoryEntries:"+e.getMessage());
			return null;
		}	
		
		return inventoryEntries;
	}
	
	/** 	 
	 * @return alle Kategorien aus der Datenbank als ArrayList. Die ArrayList ist unsortiert.
	 */
	public static ArrayList<Category> retrieveCategories() {
		ArrayList<Category> categories = new ArrayList<Category>();
		try {
			Document doc = Database.buildDocument(DBPATH_CAT);
			NodeList nList = doc.getElementsByTagName("category");
			//System.out.println("Root element:" + doc.getDocumentElement().getNodeName());
			//System.out.println("Stored categories: "+nList.getLength());
			for(int i = 0; i < nList.getLength();i++) {
				Element node = (Element) nList.item(i);

				int uid = Database.getAttributes(node,"uid");

				String name = Database.getElementTextContent(node,"name");
				Category category = new Category(uid,name);
				categories.add(category);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}	
		System.out.println("Retrieved Categories");
		return categories;
	}
	
	
	/** 
	 * @param newIE erwartet einen Inventareintrag, der validiert sein sollte, sonst wird eine Exception geworfen.
	 * @throws some errors
	 */
	public static void addInventoryEntry(InventoryEntry newIE) throws Exception{
		System.out.println("WARNING METHODE: addInventoryEntry IS WORK IN PROGRESS!\nYour database might become corrupted!");
		String errorPreamble="Fehler beim Hinzufügen eines Inventareintrages.\n";
		if(!newIE.validate()) {
			throw new Exception(errorPreamble+DatabaseErrors.inventoryEntryFailedValidation);
		}
		
		if(Database.uidExists(newIE.getUID())){
			throw new Exception(errorPreamble+DatabaseErrors.uidTaken(newIE.getUID()));
		}
		
		
		if (Database.nameExists(newIE.product.getName()) != -1) {
			throw new Exception(errorPreamble+DatabaseErrors.nameTaken(newIE.product.getName()));
		}
		
		try {
			Document doc = Database.buildDocument(DBPATH_IE);

			// Get the root element
			Node entries = doc.getFirstChild();
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
			
			
			newEntry.appendChild(newName);
			newEntry.appendChild(newCount);
			newEntry.appendChild(newWeight);
			newEntry.appendChild(newPrize);
			newEntry.appendChild(newCategoryId);
			
			entries.appendChild(newEntry);

			// write the content into xml file
			Database.transform(doc, DBPATH_IE);
			
		   } catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		   } catch (SAXException sae) {
			   System.out.println(sae.getMessage());
		   }
	}
	
	
	/** Leitet die Funktion als Database.replaceInventoryEntry(UID, newIE, false) weiter.
	 * @see (replaceInventoryEntry();)
	 * 
	 */
	public static InventoryEntry replaceInventoryEntry(int UID,InventoryEntry newIE) throws Exception{
		return Database.replaceInventoryEntry(UID, newIE, false);
	}
	   
	
	/** Ersetzt den Inventareintrag an UID durch newIE.
	 * 
	 * @param force bestimmt ob, wenn ein Eintrag an UID vorhanden ist, überschrieben werden soll.
	 * 
	 * Pre:
	 * newIE muss validieren.
	 * an UID muss ein Eintrag existieren.
	 * 
	 * Post:
	 * 
	 * Der Eintrag an UID ist ersetzt durch newIE.
	 * 
	 * @throws unterschiedliche Exception mit lesbaren Nachrichten, die dem Nutzer direkt angezeigt werden können.
	 */
	public static InventoryEntry replaceInventoryEntry(int UID,InventoryEntry newIE,Boolean force) throws Exception{
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
	           
			   if ("entry".equals(node.getNodeName())) {
				   int entryUID=Integer.parseInt(attr.getNamedItem("place").getTextContent()+attr.getNamedItem("section").getTextContent());
				   
				   if (entryUID == UID) {
					   NodeList nodeChilds = node.getChildNodes();
					   for (int n = 0; n < nodeChilds.getLength(); n++) {
						   Node subNode = nodeChilds.item(n);
						   if ("name".equals(subNode.getNodeName())) {
							   subNode.setTextContent(newIE.product.getName());
							   //break;
						   }
					   }					   
				   }
			   }
			}
	
			// write the content into xml file			
			Database.transform(doc, DBPATH_IE);	
		   } catch (IOException ioe) {
			   System.out.println(ioe.getMessage());
		   } catch (SAXException sae) {
			   System.out.println(sae.getMessage());
		   }
		System.out.println("InventoryEntry ("+UID+"): replaced with\n"+newIE);
	
		return newIE;
		
	}

	/** Ändert einen Attribut eines Inventareintrages.
	 * 
	 * @param UID bestimmt den Inventareintrag, der bearbeitet wird.
	 * @param attribute bestimmt welchen Attribute (z.B. Gewicht, Menge oder Name)
	 * @param newValue neuer Wert eines Attributes von UID.
	 * 
	 * Prüft Restriktionen der Datenbank.
	 * 
	 * @throws Exception - wenn der Attribute nicht in der Databankdefinition existiert {@link DatabaseSchema}.
	 * @throws Exception - wenn der Attribute den newValue nicht annehmen darf.
	 * @throws Exception - wenn die neue UID bereits belegt ist.
	 * @throws Exception - wenn der neue Name bereits benutzt wird.
	 */
	public static void editAttributeOfInventoryEntry(int UID, String attribute, String newValue) throws Exception{
		boolean inElements = Arrays.stream(DatabaseSchema.elements).anyMatch(attribute::equals);
		boolean inAttributes = Arrays.stream(DatabaseSchema.attributes).anyMatch(attribute::equals);
		if(!inElements&&!inAttributes) {
			throw new Exception("@editAttributeOfInventoryEntry Attribute doesn't exist.");
		}
		boolean verifiedAttribute = DatabaseSchema.verifyAttribute(attribute, newValue);
		if(!verifiedAttribute){
			throw new Exception("Attribute ("+attribute+") darf den Wert ("+newValue+") nicht haben.");
		}
		if(attribute=="UID") {
			if(Database.uidExists(Integer.parseInt(newValue))) {
				throw new Exception("Der Platz ("+newValue+") ist belegt.");
			}
		}
		if(attribute=="name") {
			if(Database.nameExists(newValue)!=-1) {
				throw new Exception("Der Name ("+newValue+") wird bereits benutzt.");
			}
		}
		Document doc = Database.buildDocument(DBPATH_IE);
		Element node;
		int[] sectionPlace = InventoryEntry.uidToSectionPlace(UID);
        int section=sectionPlace[0];
        int place=sectionPlace[1];
		try {
			node = (Element) Database.xpathNode(doc,"/entries/entry[@section="+section+" and @place="+place+"]");
			NodeList nodeChilds = node.getChildNodes();
			if(inElements) {
				for (int i = 0; i < nodeChilds.getLength(); i++) {
				   Node subNode = nodeChilds.item(i);
				   if (subNode.getNodeName()==attribute) {
					   System.out.println("SetAttribute ("+subNode.getNodeName()+"):"+subNode.getTextContent());
					   subNode.setTextContent(newValue);
					   break;
				   }
			   }
			}
			if(inAttributes) {
				node.setAttribute(attribute, newValue);
			}
			   
			
			Database.transform(doc, DBPATH_IE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
			

		return;
	}
	
	/** Ändert einen Attribut eines Inventareintrages.
	 * 
	 * @param UID bestimmt den Inventareintrag.
	 * @param attribute bestimmt welchen Attribute (z.B. Gewicht, Menge oder Name)
	 * @param newValue neuer Wert eines Attributes von UID.
	 * 
	 * Prüft Restriktionen der Datenbank.
	 * 
	 * @throws unterschiedliche Exception mit lesbaren Nachrichten, die dem Nutzer direkt angezeigt werden können.
	 */
	public static void editCategoryOfInventoryEntry(int inventoryUID, int categoryUID) throws Exception{
		return;
	}
	
	/** Löscht einen Inventareintrag mit der passenden UID, wenn die Menge bereits 0 ist.
	 * 
	 * @param UID bestimmt den Inventareintrag.
	 * @param force wenn wahr, ignoriert Menge des Inventareintrages.
	 * 
	 * @throws unterschiedliche Exception mit lesbaren Nachrichten, die dem Nutzer direkt angezeigt werden können.
	 */
	public static void deleteInventoryEntry(int UID, Boolean force) throws Exception{
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
				   throw new Exception("Löschen von Inventareintrag("+UID+") fehlgeschlagen. Die Quantität ("+subNode.getTextContent()+") sollte 0 sein ist, ansonsten benutzten Sie 'force delete'.");
			   }
		   }
		node.getParentNode().removeChild(node);
		Database.transform(doc, DBPATH_IE);
		System.out.println("InventoryEntry ("+UID+"): deleted");
		return;
	}
	
	/*
	 * @param shelf Nummer des Regals
	 * 
	 * @return die Menge ein freiem Platz in dem Regal mit der Nummer shelf.*/
	public static int freeSpace(int shelf) {
		System.out.println("WARNING work in progress @freeSpace!");
		
		int shelfCapicity=100*1000;
		int usedSpace = 0;
		try {
			Document doc = Database.buildDocument(DBPATH_IE);
			NodeList nodes;
			nodes = Database.xpathNodes(doc,"/entries/entry[@section="+shelf+"]");
			System.out.println("Nodes in Shelf: "+nodes.getLength());
			   for (int i = 0; i < nodes.getLength(); i++) {
				   Element node = (Element) nodes.item(i);
					int count = Integer.parseInt(node.getElementsByTagName("count").item(0).getTextContent());
					int weight = Integer.parseInt(node.getElementsByTagName("weight").item(0).getTextContent());
					
					usedSpace = usedSpace + (count*weight);
			   }
		}catch (Exception e) {
			System.out.println("Exception in freeSpace ("+e.getMessage()+")");
			return -1;
		}
		return shelfCapicity-usedSpace;
	}
	
	/** Prüft die Datenbank auf das Einhalten der Datenbankrestriktionen mit hilfe einer XSD.
	 * */
	public static boolean validate() {
		return Database.validateAgainstXSD(DBPATH_IE,DBPATH_IE_XSD);
	}
	

	/** Prüft ob ein Name in der Datenbank exisitiert. Wichtig zum Prüfen der Datenbankrestriktionen.
	 * 
	 * @return -1, wenn nicht exisitert, ansonsten UID wo der Name existiert.
	 * */
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
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	
	/** Prüft ob eine UID in der Datenbank exisitiert. Wichtig zum Prüfen der Datenbankrestriktionen.
	 * 
	 * @return true, wenn exisitert, ansonsten false.
	 * */
	public static boolean uidExists(int uid) {
		try {
	        Document xmlDocument = Database.buildDocument("inventoryEntries.xml");
	        Element subNode;
	        int[] sectionPlace = InventoryEntry.uidToSectionPlace(uid);
	        int section=sectionPlace[0];
	        int place=sectionPlace[1];
			subNode = (Element) Database.xpathNode(xmlDocument, "/entries/entry[@section="+section+" and @place="+place+"]");
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
	
	public static String escapeString(String unescapedString) {
		return unescapedString;
	}
	
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
	
	private static Node xpathNode(Document doc,String xpath_query) throws XPathExpressionException{
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
	
	private static NodeList xpathNodes(Document doc,String xpath_query) throws XPathExpressionException{
		NodeList node = null;
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			node = (NodeList) xPath.compile(Database.escapeString(xpath_query)).evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return node;
		
	}
	
	private static void transform(Document doc, String targetPath) {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(DBPATH+targetPath));
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static boolean validateAgainstXSD(String xmlPath, String xsdPath)
	{
		FileInputStream xml;
		FileInputStream xsd;
		try {
		xml = new FileInputStream(DBPATH+xmlPath);
		xsd = new FileInputStream(DBPATH+xsdPath);			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	    try
	    {
	        SchemaFactory factory = 
	            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	        Schema schema = factory.newSchema(new StreamSource(xsd));
	        Validator validator = schema.newValidator();
	        validator.validate(new StreamSource(xml));
	        return true;
	    }
	    catch(Exception ex)
	    {
	        return false;
	    }
	}
	
	private static String getElementTextContent(Element element, String string) {
		return element.getElementsByTagName(string).item(0).getTextContent();
	}

	private static int getElementContent(Element element, String string) {
		return Integer.parseInt(element.getElementsByTagName(string).item(0).getTextContent());
	}

	private static int getAttributes(Element element, String string) {
		return Integer.parseInt(element.getAttributes().getNamedItem(string).getTextContent());
	}
	
	
	/** Graveyard for deprecated methodes  */
	
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
				/**
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


}
