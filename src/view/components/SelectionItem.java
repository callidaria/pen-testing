package view.components;

/**Eine Classe die nur für die JComboBox genutz wird
 * 
 * Dadurch kann im Selector der Name angezeigt werden und bei Auswahl die ID genutz werden.
 */
public class SelectionItem {
	/**
	 * ID des SelectionItem
	 */
	private int id;
	
	/**
	 * Name des SelectionItem
	 */
    private String name;

    /** Konstruktor mit ID und Name
     * 
     * @param id setzt ID
     * @param name setzt Name
     */
    public SelectionItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**Gibt ID zurück
     * 
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**Setzt ID
     * 
     * @param id, gewünschte ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**Gibt Name zurück
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**Setzt Name
     * 
     * @param name, gewünschter Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Diese Funktion wird bei der Anzeige in der JComboBox genutzt und definiert was in dem Selector steht.
     */
    public String toString(){
        return this.name;
    }
}
