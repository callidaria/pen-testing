package view;
 
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import database.xml.Database;
import model.InventoryEntry;
 
public class TextAreaLogProgram extends JFrame {
    /**
     * The text area which is used for displaying logging information.
     */
    private JTextArea textArea;
     
    private JButton buttonStart = new JButton("Start");
    private JButton buttonClear = new JButton("Clear");
     
    private PrintStream standardOut;
     
    public TextAreaLogProgram() {
        super("Demo printing to JTextArea");
         
        textArea = new JTextArea(50, 10);
        textArea.setEditable(false);
        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
         
        // keeps reference of standard output stream
        standardOut = System.out;
         
        // re-assigns standard output stream and error output stream
        System.setOut(printStream);
        System.setErr(printStream);
 
        // creates the GUI
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;
         
        add(buttonStart, constraints);
         
        constraints.gridx = 1;
        add(buttonClear, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
         
        add(new JScrollPane(textArea), constraints);
         
        // adds event handler for button Start
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	try {
                    textArea.getDocument().remove(0,
                            textArea.getDocument().getLength());
                    standardOut.println("Text area cleared");
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
                printLog();
            }
        });
         
        // adds event handler for button Clear
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // clears the text area
                try {
                    textArea.getDocument().remove(0,
                            textArea.getDocument().getLength());
                    standardOut.println("Text area cleared");
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });
         
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 320);
        setLocationRelativeTo(null);    // centers on screen
    }
     
    /**
     * Prints log statements for testing in a thread
     */
    private void printLog() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
            	ArrayList<InventoryEntry> inventoryEntry = Database.retrieveInventoryEntries();
    			System.out.println("Objects in Array: "+inventoryEntry.size());
    			for (int i=0;i<inventoryEntry.size();i++) {
    				System.out.println("InventoryEntry ("+i+"):");
    				System.out.println("\tproduct_id:"+inventoryEntry.get(i).getProductID());
    				System.out.println("\t\tproduct_name:"+inventoryEntry.get(i).product.getName());
    				System.out.println("\t\tproduct_count:"+inventoryEntry.get(i).product.getCount());
    				System.out.println("\tposition:"+inventoryEntry.get(i).getID());
    				System.out.println("\tshelf_section:"+inventoryEntry.get(i).getShelfSection());
    				System.out.println("\tshelf_place:"+inventoryEntry.get(i).getShelfPlace());
    			}
            }
        });
        thread.start();
    }
     
    /**
     * Runs the program
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TextAreaLogProgram().setVisible(true);
            }
        });
    }
}