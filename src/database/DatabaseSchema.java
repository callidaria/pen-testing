package database;

/**
 * Nebenklasse im database Packet. Hält wichtig statische Werte, wie zum Beispiel den Pfad zu den XML Dateien.
 * 
 * @author Freddy
 *
 */
public class DatabaseSchema {
	
	public static String[] elements = new String[] {"name","count","weight","prize","category_id"};
	public static String[] attributes = new String[] {"place","area"};
	
	public static String inventoryEntriesXSD = "<?xml version=\"1.0\"?>\r\n" + 
			"<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\r\n" + 
			"    <xs:element name=\"entries\">\r\n" + 
			"        <xs:complexType>\r\n" + 
			"            <xs:sequence minOccurs=\"0\" maxOccurs=\"unbounded\">\r\n" + 
			"                <xs:element name=\"entry\">\r\n" + 
			"                    <xs:complexType>\r\n" + 
			"                        <xs:sequence>\r\n" + 
			"                            <xs:element name=\"name\" type=\"xs:string\"/>\r\n" + 
			"                            <xs:element name=\"count\" type=\"xs:nonNegativeInteger\"/>\r\n" + 
			"                            <xs:element name=\"weight\" type=\"xs:nonNegativeInteger\"/>\r\n" + 
			"							<xs:element name=\"prize\" type=\"xs:nonNegativeInteger\"/>\r\n" + 
			"							<xs:element name=\"category_id\" type=\"xs:nonNegativeInteger\"/>\r\n" + 
			"                        </xs:sequence>\r\n" + 
			"						<xs:attribute name=\"area\" type=\"shelfInt\" />\r\n" + 
			"						<xs:attribute name=\"place\" type=\"shelfInt\" />\r\n" + 
			"                    </xs:complexType>\r\n" + 
			"                </xs:element>\r\n" + 
			"            </xs:sequence>\r\n" + 
			"        </xs:complexType>\r\n" + 
			"        <xs:unique name=\"nameMustBeUnique\">\r\n" + 
			"			<xs:selector xpath=\"entry/name\"/>\r\n" + 
			"            <xs:field xpath=\".\" />\r\n" + 
			"		</xs:unique>\r\n" + 
			"        <xs:unique name=\"areaPlaceMustBeUnique\">\r\n" + 
			"			<xs:selector xpath=\"entry\" />\r\n" + 
			"			<xs:field xpath=\"@area\" />\r\n" + 
			"            <xs:field xpath=\"@place\" />\r\n" + 
			"        </xs:unique>\r\n" + 
			"    </xs:element>\r\n" + 
			"    <xs:simpleType name=\"shelfInt\">\r\n" + 
			"        <xs:restriction base=\"xs:nonNegativeInteger\">\r\n" + 
			"            <xs:minInclusive value=\"0\"/>\r\n" + 
			"            <xs:maxInclusive value=\"999\"/>\r\n" + 
			"        </xs:restriction>\r\n" + 
			"    </xs:simpleType>\r\n" + 
			"</xs:schema> ";
	
	public static String categoriesXSD = "<?xml version=\"1.0\"?>\r\n" + 
			"<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\r\n" + 
			"    <xs:element name=\"categories\">\r\n" + 
			"        <xs:complexType>\r\n" + 
			"            <xs:sequence minOccurs=\"0\" maxOccurs=\"unbounded\">\r\n" + 
			"                <xs:element name=\"category\">\r\n" + 
			"                    <xs:complexType>\r\n" + 
			"                        <xs:sequence>\r\n" + 
			"                            <xs:element name=\"name\" type=\"xs:string\"/>\r\n" + 
			"                        </xs:sequence>\r\n" + 
			"						<xs:attribute name=\"uid\" type=\"xs:nonNegativeInteger\" />\r\n" + 
			"                    </xs:complexType>\r\n" + 
			"                </xs:element>\r\n" + 
			"            </xs:sequence>\r\n" + 
			"        </xs:complexType>\r\n" + 
			"        <xs:unique name=\"nameMustBeUnique\">\r\n" + 
			"			<xs:selector xpath=\"category/name\"/>\r\n" + 
			"            <xs:field xpath=\".\" />\r\n" + 
			"		</xs:unique>\r\n" + 
			"        <xs:unique name=\"uidMustBeUnique\">\r\n" + 
			"			<xs:selector xpath=\"category\" />\r\n" + 
			"			<xs:field xpath=\"@uid\" />\r\n" + 
			"        </xs:unique>\r\n" + 
			"    </xs:element>\r\n" + 
			"</xs:schema> "; 
}
