package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
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

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.Category;
import model.CodedException;
import model.InventoryEntry;
import model.Product;

/**
 * Hauptklasse im database Packet. Beinhaltet Methoden um von einer XML Datei zulesen und zuschreiben.
 * Ermöglicht das Lesen und Schreiben von der Inventareintrags XML Datei
 * Ermöglicht das Lesen und Schreiben von der Kategorie XML Datei 
 * 
 * @author Freddy
 *
 */
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
	
	//Legt fest ob Eingaben validiert werden sollen.
	static final Boolean DB_VALIDATE=false;
	
	static final Document IE_DOC;
	static final Document CAT_DOC;

	static {
	     Document tmp = null;
	     Document tmp2 = null;
	     try {
	       tmp = Database.buildDocument(DBPATH_IE);
	       tmp2 = Database.buildDocument(DBPATH_CAT);
	       System.out.println(tmp);
	     } catch (Exception uhe) {
	    	 System.err.println("Fatal error. Trouble finding the file:"+DBPATH_IE+"\n We will rebuild the file if it doesn' exist. Please restart.");
	     }
	     IE_DOC = tmp;
	     CAT_DOC=tmp2;
	}
	
	
	//Testing with only one tranformerFactory instance
	static TransformerFactory transformerFactory = TransformerFactory.newInstance();

	private static DOMSource CAT_DOM_SOURCE= new DOMSource(Database.CAT_DOC);
	private static DOMSource IE_DOM_SOURCE= new DOMSource(Database.IE_DOC);
	
	
	/**Abfragen aller Inventareinträge aus der XML Datei unter DBPATH_IE
	 *  
	 * @return alle Inventareinträge aus der Datenbank als ArrayList. Die ArrayList ist unsortiert.
	 * 
	 * Bitte benutzte retrieveInventoryEntriesWithCategory(), ansonsten wird den Inventareinträgen keine Kategorie zugewiesen.
	 */
	public static ArrayList<InventoryEntry> retrieveInventoryEntries(){
		ArrayList<InventoryEntry> inventoryEntries = new ArrayList<InventoryEntry>();
		
			//Lesen der Datei
			Document doc = Database.IE_DOC;
			NodeList nList = doc.getElementsByTagName("entry");
			
			//Füge jeden Eintrag in der XML Datei zu ArrayList als Inventareintrag hinzu.
			for(int i = 0; i < nList.getLength();i++) {
				Element node = (Element) nList.item(i);

				int shelfSection = Database.getAttributes(node,"area");
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
		
		System.out.println("Retrieved Inventory Entries");
		return inventoryEntries;
	}
	
	/**
	 * Abfragen aller Inventareinträge aus der XML Datei unter DBPATH_IE. Und zuweisen der Kategorien zu jedem Inventareintrag.
	 * 
	 * @param categories Die List an Kategorie. Damit die Referenzen korrekt sind und keine Kategorie doppelt während der Laufzeit existiert.
	 * @return alle Inventareintrag mit Kategorie.
	 */
	public static ArrayList<InventoryEntry> retrieveInventoryEntriesWithCategory(ArrayList<Category> categories){
		ArrayList<InventoryEntry> inventoryEntries = Database.retrieveInventoryEntries();
		Category defaultCategory = new Category(0, "Keine Kategorie");
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
		
		return inventoryEntries;
	}
	
	/** 
	 * @param newIE erwartet einen Inventareintrag, der validiert sein sollte, sonst wird eine Exception geworfen.
	 * @throws Exception errors
	 * @throws CodedException errors
	 */
	public static void addInventoryEntry(InventoryEntry newIE){
		
		
		/*
		String errorPreamble="Fehler beim Hinzufügen eines Inventareintrages.\n";
		if(DB_VALIDATE) {
			if(!newIE.validate()) {
				throw new Exception(errorPreamble+DatabaseErrors.inventoryEntryFailedValidation);
			}
			if(Database.uidExists(newIE.getUID())){
				throw new Exception(errorPreamble+DatabaseErrors.uidTaken(newIE.getUID()));
			}
			if (Database.nameExists(newIE.product.getName()) != -1) {
				throw new Exception(errorPreamble+DatabaseErrors.nameTaken(newIE.product.getName()));
			}
		}
		*/
		
		
			Document doc = Database.IE_DOC;

			// Get the root element
			Node entries = doc.getFirstChild();
			Element newEntry = doc.createElement("entry");
			
			newEntry.setAttribute("area",Integer.toString(newIE.getShelfSection()));
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
			
			newCategoryId.appendChild(doc.createTextNode(Integer.toString(newIE.product.category.getUID())));
			
			
			newEntry.appendChild(newName);
			newEntry.appendChild(newCount);
			newEntry.appendChild(newWeight);
			newEntry.appendChild(newPrize);
			newEntry.appendChild(newCategoryId);
			
			entries.appendChild(newEntry);

			// write the content into xml file
			Database.transformInventoryEntries();
			
		   
	}
	
	public static void addTestInventoryEntries(int maxAreas, int maxPlaces,Boolean validate){
		String errorPreamble="Fehler beim Hinzufügen eines Inventareintrages.\n";
		
		Document doc = Database.IE_DOC;

		// Get the root element
		Node entries = doc.getFirstChild();
		for (int i=0;i<=maxAreas && i<=999;i++) {
			for (int n=0;n<=maxPlaces && n<=999;n++) {
				String area = Integer.toString(i);
				String place = Integer.toString(n);
				while (area.length()<3) {
					area="0"+area;
				}
				while (place.length()<3) {
					place="0"+place;
				}
				Product newProduct = new Product("Produkt "+area+place,1,1,100,0);
				InventoryEntry newIE = new InventoryEntry(i,n,newProduct);
				/*
				if(validate) {
					if(!newIE.validate()) {
						throw new CodedException(errorPreamble+DatabaseErrors.inventoryEntryFailedValidation,1);
					}
					
					if(Database.uidExists(newIE.getUID())){
						throw new Exception(errorPreamble+DatabaseErrors.uidTaken(newIE.getUID()));
					}
					
					if (Database.nameExists(newIE.product.getName()) != -1) {
						throw new Exception(errorPreamble+DatabaseErrors.nameTaken(newIE.product.getName()));
					}
				}
				*/
				
				
				Element newEntry = doc.createElement("entry");
				
				newEntry.setAttribute("area",Integer.toString(newIE.getShelfSection()));
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
				
				newCategoryId.appendChild(doc.createTextNode(Integer.toString(newIE.product.category.getUID())));
				
				
				newEntry.appendChild(newName);
				newEntry.appendChild(newCount);
				newEntry.appendChild(newWeight);
				newEntry.appendChild(newPrize);
				newEntry.appendChild(newCategoryId);
				
				entries.appendChild(newEntry);
				
				if(i % 100 ==0 || n % 100==0) {
					System.out.println("Created InventoryEntry:"+area+place);
				}
				area=null;
				place=null;
				}
			}
			// write the content into xml file
			Database.transformInventoryEntries();
	}
	
	
	
	/** Ersetzt den Inventareintrag an UID durch newIE.
	 * 
	 * Eine der wenigen Funktionen die kein XPath benutzt, sondern alle Elemente im Dokument durchgeht.
	 * 
	 * @param UID Bestimmt den Inventareintrag, der ersetzt werden soll.
	 * @param newIE der Inventareintrag der an der ausgewählten Stelle eingefügt wird.
	 * @param force bestimmt ob, wenn ein anderer Eintrag an UID vorhanden ist, überschrieben werden soll.
	 * 
	 * Pre:
	 * newIE muss validieren.
	 * an UID muss ein Eintrag existieren.
	 * 
	 * Post:
	 * 
	 * Der Eintrag an UID ist ersetzt durch newIE.
	 * 
	 * @throws Exception unterschiedliche Exception mit lesbaren Nachrichten, die dem Nutzer direkt angezeigt werden können.
	 * @return Gibt den newIE zurück. (warum?)
	 */
	public static InventoryEntry replaceInventoryEntry(int UID,InventoryEntry newIE){
		
		Element node;
		int[] sectionPlace = InventoryEntry.uidToSectionPlace(UID);
        int section=sectionPlace[0];
        int place=sectionPlace[1];
		try {

			node = (Element) Database.xpathNode(Database.IE_DOC,"/entries/entry[@area="+section+" and @place="+place+"]");
			node.setAttribute("area", Integer.toString(newIE.getShelfSection()));
			node.setAttribute("place", Integer.toString(newIE.getShelfPlace()));
			
			NodeList nodeChilds = node.getChildNodes();
			
				for (int i = 0; i < nodeChilds.getLength(); i++) {
				   Node subNode = nodeChilds.item(i);
				   if (subNode.getNodeName()=="name") {
					   
					   subNode.setTextContent(newIE.product.getName());
				   }
			   }
			
			
			Database.transformInventoryEntries();
			System.out.println("SetAttribute ("+UID+"):"+newIE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
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
			throw new Exception("@editAttributeOfInventoryEntry Attribute doesn't exist."+DatabaseErrors.internalWarning);
		}
		boolean verifiedAttribute = DatabaseSchema.verifyAttribute(attribute, newValue);
		if(!verifiedAttribute){
			throw new Exception("Attribute ("+attribute+") darf den Wert ("+newValue+") nicht haben.");
		}
		
		/*
		if(DB_VALIDATE) {
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
			if(!uidExists(UID)) {
				throw new Exception("Die UID ("+UID+") exsitiert nicht."+DatabaseErrors.internalWarning);
			}
		}
		*/
		
		
		Element node;
		int[] sectionPlace = InventoryEntry.uidToSectionPlace(UID);
        int section=sectionPlace[0];
        int place=sectionPlace[1];
		try {

			node = (Element) Database.xpathNode(Database.IE_DOC,"/entries/entry[@area="+section+" and @place="+place+"]");
			NodeList nodeChilds = node.getChildNodes();
			if(inElements) {
				for (int i = 0; i < nodeChilds.getLength(); i++) {
				   Node subNode = nodeChilds.item(i);
				   if (subNode.getNodeName()==attribute) {
					   
					   subNode.setTextContent(newValue);
					   break;
				   }
			   }
			}
			if(inAttributes) {
				node.setAttribute(attribute, newValue);
			}
			
			
			System.out.println("SetAttribute ("+attribute+") @ ("+UID+"):"+newValue);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} 
		return;
	}
	
	public static void deleteInventoryEntry(int UID) throws Exception{
		Database.deleteInventoryEntry(UID,true);
		return;
	}
	/** Löscht einen Inventareintrag mit der passenden UID, wenn die Menge bereits 0 ist.
	 * @param UID bestimmt den Inventareintrag.
	 * @param force wenn wahr, ignoriert Menge des Inventareintrages.
	 * @throws Exception unterschiedliche Exception mit lesbaren Nachrichten, die dem Nutzer direkt angezeigt werden können.
	 */
	public static void deleteInventoryEntry(int UID, Boolean force) throws Exception{
		Document doc = Database.IE_DOC;
		Element node;
		int[] sectionPlace = InventoryEntry.uidToSectionPlace(UID);
	    int section=sectionPlace[0];
	    int place=sectionPlace[1];
		try {
			node = (Element) Database.xpathNode(doc,"/entries/entry[@area="+section+" and @place="+place+"]");
			if(node==null) {
				return;
			}
			NodeList nodeChilds = node.getChildNodes();
			for (int i = 0; i < nodeChilds.getLength(); i++) {
				   Node subNode = nodeChilds.item(i);
				   if (subNode.getNodeName()=="count" && Integer.parseInt(subNode.getTextContent())!=0 && force==false) {
					   throw new Exception("Löschen von Inventareintrag("+UID+") fehlgeschlagen. Die Quantität ("+subNode.getTextContent()+") sollte 0 sein ist, ansonsten benutzten Sie 'force delete'.");
				   }
			   }
		node.getParentNode().removeChild(node);
		Database.transformInventoryEntries();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Internal Error.");
		}
		System.out.println("InventoryEntry ("+UID+"): deleted");
		return;
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
		catch(SAXException | IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
		System.out.println("Retrieved Categories");
		return categories;
	}
	public static int addCategory(Category category) throws Exception {
		System.out.println("WARNING METHODE: addCategory IS WORK IN PROGRESS!\nYour database might become corrupted!");
		String errorPreamble="Fehler beim Hinzufügen eines Inventareintrages.\n";
		int freeUID=-1;
		if(!category.validate()) {
			throw new Exception(errorPreamble+DatabaseErrors.inventoryEntryFailedValidation);
		}
		
		
		try {
			Document doc = Database.buildDocument(DBPATH_CAT);
			freeUID = Database.freeCategoryUID();
			// Get the root element
			Node entries = doc.getFirstChild();
			Element newCategory = doc.createElement("category");
			
			newCategory.setAttribute("uid",Integer.toString(freeUID));
			
			
			Element newName = doc.createElement("name");
			
			newName.appendChild(doc.createTextNode(category.getName()));			
	
			newCategory.appendChild(newName);
			
			entries.appendChild(newCategory);

			Database.transformCategories();
			
		   } catch (IOException|SAXException e) {
			System.out.println(e.getMessage());
		   }
		return freeUID;
	}
	
	/** Gibt die nächste frei UID für Kategorien zurück. Dabei wird die UID des letzten Eintrags mit eins addiert. 
	 * 
	 * @return nächste freie UID
	 */
	private static int freeCategoryUID() {
		// TODO Auto-generated method stub
		Category lastCategory=Database.retrieveCategories().get(Database.retrieveCategories().size() -1);
		return lastCategory.getUID()+1;
	}


	public static void renameCategory(int UID, String newName) {
		Element node;
		try {
			node = (Element) Database.xpathNode(Database.CAT_DOC,"/categories/category[@uid="+UID+"]");
			NodeList nodeChilds = node.getChildNodes();
				for (int i = 0; i < nodeChilds.getLength(); i++) {
				   Node subNode = nodeChilds.item(i);
				   if (subNode.getNodeName()=="name") {
					   subNode.setTextContent(newName);
					   break;
				   }
			   }
			
			
			System.out.println("SetName ("+UID+"):"+newName);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} 
		return;
	}
	
	public static void deleteCategory(int UID) throws Exception {
		Document doc = Database.CAT_DOC;
		Element node;
		try {
			node = (Element) Database.xpathNode(doc,"/categories/category[@uid="+UID+"]");
			if(node==null) {
				return;
			}
			node.getParentNode().removeChild(node);
			Database.transformInventoryEntries();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Internal Error.");
		}
		System.out.println("Category ("+UID+"): deleted");
		return;
	}

	/* Diese Methode ermittelt den freien Platz in einem "Bereich".
	 * Es dauert aber bei einer vollen Datenbank bis zu einer Minute. Daher wird eine in-ram Lösung entwickelt.
	 * 
	 * @param shelf Nummer des Regals
	 * 
	 * @return die Menge ein freiem Platz in dem Regal mit der Nummer shelf.
	 * @deprecated bitte benutze eine Methode aus dem Controller / VirtualStorage.
	 * */
	@Deprecated
	public static int freeSpace(int shelf) {
		System.out.println("WARNING work in progress @freeSpace!");
		
		int shelfCapicity=100*1000;
		int usedSpace = 0;
		try {
			Document doc = Database.IE_DOC;
			NodeList nodes;
			nodes = Database.xpathNodes(doc,"/entries/entry[@area="+shelf+"]");
			System.out.println("Nodes in Shelf: "+nodes.getLength());
			   for (int i = 0; i < nodes.getLength(); i++) {
				   Element node = (Element) nodes.item(i);
					int count = Integer.parseInt(node.getElementsByTagName("count").item(0).getTextContent());
					int weight = Integer.parseInt(node.getElementsByTagName("weight").item(0).getTextContent());
					
					usedSpace = usedSpace + (count*weight);
			   }
		}catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception in freeSpace ("+e.getMessage()+")");
			return -1;
		}
		return shelfCapicity-usedSpace;
	}
	
	/** Prüft die Datenbank auf das Einhalten der Datenbankrestriktionen mit hilfe einer XSD.
	 * @return wahr, wenn die Inventareintragsdatei gegen die XSD validiert.
	 * @throws SAXException 
	 */
	public static boolean validate() throws SAXException {
		return Database.validateAgainstXSD(DBPATH_IE,DBPATH_IE_XSD);
	}
	

	/** Prüft ob ein Name in der Datenbank exisitiert. Wichtig zum Prüfen der Datenbankrestriktionen.
	 * @param name, der zu prüfende Name.
	 * 
	 * @return -1, wenn nicht exisitert, ansonsten UID wo der Name existiert.
	 * @deprecated bitte benutze eine Methode aus dem Controller / VirtualStorage.
	 * */
	@Deprecated
	public static int nameExists(String name) {
		try {
	        Document doc = Database.buildDocument("inventoryEntries.xml");
	        Element node;
			node = (Element) Database.xpathNode(doc,"/entries/entry[name/text()='"+name+"']");
			if (node!=null) {
				int shelfSection = Integer.parseInt(node.getAttributes().getNamedItem("area").getTextContent());
				int shelfPlace = Integer.parseInt(node.getAttributes().getNamedItem("place").getTextContent());
				return InventoryEntry.sectionPlaceToUID(shelfSection, shelfPlace);
			};
		}  catch (SAXException|IOException|XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	
	/** Prüft ob eine UID in der Datenbank exisitiert. Wichtig zum Prüfen der Datenbankrestriktionen.
	 * @param uid, die zu prüfende UID.
	 * 
	 * @return true, wenn exisitert, ansonsten false.
	 * @deprecated bitte benutze eine Methode aus dem Controller / VirtualStorage.
	 * */
	@Deprecated
	public static boolean uidExists(int uid) {
		try {
	        Document xmlDocument = Database.buildDocument("inventoryEntries.xml");
	        Element subNode;
	        int[] sectionPlace = InventoryEntry.uidToSectionPlace(uid);
	        int section=sectionPlace[0];
	        int place=sectionPlace[1];
			subNode = (Element) Database.xpathNode(xmlDocument, "/entries/entry[@area="+section+" and @place="+place+"]");
			if (subNode!=null) {
				return true;
			};
		} catch (XPathExpressionException|SAXException|IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static String escapeString(String unescapedString) {
		return unescapedString;
	}
	
	/** Hilfsmethode. Gibt das fertige Document zurück.
	 * 
	 * @param file Pfad zur Datei
	 * @return Document
	 * @throws SAXException
	 * @throws IOException
	 */
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
	
	/** Hilfsmethode. Gibt einen einzellen Node zurück. Dazu wird der eine XPath Anfrage auf ein XML Document angewandt.
	 * 
	 * @param doc, erwartet das zu parsende Document
	 * @param xpath_query, die Anfrage die auf das doc angewandt werden soll.
	 * @return gibt einen einzellnen Node der mit XPath gefunden wurde zurück.
	 * @throws XPathExpressionException wenn die eingebene xpath_query nicht valide ist.
	 */
	private static Node xpathNode(Document doc,String xpath_query) throws XPathExpressionException{
		Node node = null;
		XPath xPath = XPathFactory.newInstance().newXPath();
		node = (Node) xPath.compile(Database.escapeString(xpath_query)).evaluate(doc, XPathConstants.NODE);
		
		return node;
		
	}
	
	/** Hilfsmethode. Gibt einen eine NodeList zurück. Dazu wird der eine XPath Anfrage auf ein XML Document angewandt.
	 * 
	 * @param doc, erwartet das zu parsende Document
	 * @param xpath_query, die Anfrage die auf das doc angewandt werden soll.
	 * @return gibt einen NodeList, mit Nodes die via XPath gefunden wurde, zurück.
	 * @throws XPathExpressionException wenn die eingebene xpath_query nicht valide ist.
	 */
	private static NodeList xpathNodes(Document doc,String xpath_query) throws XPathExpressionException{
		NodeList node = null;

		XPath xPath = XPathFactory.newInstance().newXPath();
		node = (NodeList) xPath.compile(Database.escapeString(xpath_query)).evaluate(doc, XPathConstants.NODESET);

		
		return node;
		
	}
	
	/**Hilfsmethode. Speichert (transformiert) das veränderte doc an der Stelle targetPath.
	 */
	public static boolean transformInventoryEntries() {
		
		
		Date start;
		Date end;
		
		
			
		TransformerFactory transformerFactory = Database.transformerFactory;
		Transformer transformer;
		
		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
			transformer.setOutputProperty(OutputKeys.INDENT, "no");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(Database.IE_DOM_SOURCE, new StreamResult(new File(DBPATH+DBPATH_IE)));
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	/**Hilfsmethode. Speichert (transformiert) das veränderte doc an der Stelle targetPath.
	 * 
	 * 
	 * @param doc erwartet ein Dokument, welche sinnvollerweiße verändert wurde
	 * @param targetPath der Speicherort
	 */
	public static boolean transformCategories() {
		
		
		Date start;
		Date end;
		
		
			
		TransformerFactory transformerFactory = Database.transformerFactory;
		Transformer transformer;
		
		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
			transformer.setOutputProperty(OutputKeys.INDENT, "no");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(Database.CAT_DOM_SOURCE, new StreamResult(new File(DBPATH+DBPATH_CAT)));
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	/** Vergleich eine XML Datei mit einer XSD (XML Schema Definition) Datei.
	 * 
	 * @param xmlPath Pfad der XML Datei.
	 * @param xsdPath Pfad der XSD Datei.
	 * @return wahr, wenn die XML mit der XSD validiert. Ansonsten false.
	 * @throws SAXException 
	 */
	private static boolean validateAgainstXSD(String xmlPath, String xsdPath) throws SAXException
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
	        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	        Schema schema = factory.newSchema(new StreamSource(xsd));
	        Validator validator = schema.newValidator();
	        System.out.println("Validating ("+xmlPath+") against ("+xsdPath+").\nThis may take a very long time with a big XML File to validate.");
	        validator.validate(new StreamSource(xml));
	        return true;
	    }
	    catch (IOException e) {
			e.printStackTrace();
	    	return false;
		}
	}
	
	/**Hilfmethode.
	 * 
	 * @param element, ein Element
	 * @param string, das Tag welches wir erhalten wollen.
	 * @return der Wert des Tags von element als String.
	 */
	private static String getElementTextContent(Element element, String string) {
		return element.getElementsByTagName(string).item(0).getTextContent();
	}

	/**Hilfmethode.
	 * 
	 * @param element, ein Element
	 * @param string, das Tag welches wir erhalten wollen.
	 * @return der Wert des Tags von element als Integer.
	 */
	private static int getElementContent(Element element, String string) {
		return Integer.parseInt(element.getElementsByTagName(string).item(0).getTextContent());
	}

	/**Hilfmethode.
	 * 
	 * @param element, ein Element
	 * @param string, das Attribut welches wir erhalten wollen.
	 * @return der Wert des Attributes von element als Integer.
	 */
	private static int getAttributes(Element element, String string) {
		return Integer.parseInt(element.getAttributes().getNamedItem(string).getTextContent());
	}
	
	
	
}
