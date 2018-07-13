/**
 * Swing application for Student Directory.
 * @author Aakash Bhatia
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
/**
 * Defining Directory Driver.
 */
public class DirectoryDriver implements ActionListener, KeyListener {
    /**
     * Defining dimension of text fields.
     */
    private Dimension dim = new Dimension(150, 40);
    /**
     * Static variable for directory.
     */
    private static Directory d = new Directory();
    /**
     * Instance variable for area.
     */
    private JTextArea area;
    /**
     * Instance variable for addButton.
     */
    private JButton addButton;
    /**
     * Instance variable for deletedButton.
     */
    private JButton deleteButton;
    /**
     * Instance variable for search by AndrewID Button.
     */
    private JButton byAndrewID;
    /**
     * Instance variable for search by FirstName Button.
     */
    private JButton byFirstName;
    /**
     * Instance variable for search by LastName Button.
     */
    private JButton byLastName;
    /**
     * Instance variable for search textfield.
     */
    private JTextField search;
    /**
     * Instance variable for creating an empty list.
     */
    private List<Student> empty1 = new LinkedList<Student>();
    /**
     * Constructor where JFrame and other components are instantiated.
     */
    public DirectoryDriver() {
        // Step1 : Create a window
        JFrame window = new JFrame("Student Directory");
        window.setSize(900, 700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setLayout(new FlowLayout());
        //Step 2: Create a Container
        JPanel pane1 = new JPanel(); // Step 2A: Container 1
        JPanel pane2 = new JPanel(); // Step 2B: Container 2
        JPanel pane3 = new JPanel(); // Step 2C: Container 3
        JPanel pane4 = new JPanel(); // Step 2D: Container 4
        JPanel pane5 = new JPanel(); // Step 2E: Container 5
        //Step 3: Adding components to the Container
        //Step 3A: Adding components to Container 1
        pane1.setLayout(new BorderLayout());
        pane1.setBorder(new EtchedBorder());
        pane1.setPreferredSize(new Dimension(900, 100));
        JLabel title = new JLabel("Student Directory", SwingConstants.CENTER);
        pane1.add(title, BorderLayout.CENTER);
        //Step 3B: Adding components to Container 2
        pane2.setLayout(new BoxLayout(pane2, BoxLayout.LINE_AXIS));
        pane2.setBorder(new EtchedBorder());     // Give the panel a border
        pane2.setPreferredSize(new Dimension(900, 100));
        addTitle(pane2, "Add a new student");
        JTextField fn = new JTextField(1);
        JTextField ln = new JTextField(1);
        JTextField id = new JTextField(1);
        JTextField pn = new JTextField(1);
        addComponent(pane2, new JLabel("First Name"), fn);
        addComponent(pane2, new JLabel("Last Name"), ln);
        addComponent(pane2, new JLabel("Andrew ID"), id);
        addComponent(pane2, new JLabel("Phone Number"), pn);
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (id.getText().isEmpty()) {
                    area.append("AndrewID field cannot be left blank\n");
                } else if (fn.getText().isEmpty()) {
                    area.append("First Name field cannot be left blank\n");
                } else if (ln.getText().isEmpty()) {
                    area.append("Last Name field cannot be left blank\n");
                } else if (d.searchByAndrewId(id.getText()) != null) {
                    area.append(String.format("Data already contains an entry for the Andrew ID %s\n", id.getText()));
                } else {
                Student s1 = new Student(id.getText());
                s1.setFirstName(fn.getText());
                s1.setLastName(ln.getText());
                //System.out.println(! pn.getText().isEmpty());
                if (!pn.getText().isEmpty()) {
                    s1.setPhoneNumber(pn.getText());
                } else {
                    s1.setPhoneNumber("");
                }
                d.addStudent(s1);
                area.append(String.format("A new entry with Andrew ID %s has been added into the directory.\n", id.getText()));
                fn.setText("");
                ln.setText("");
                id.setText("");
                pn.setText("");
                }
            }
        });
        pane2.add(addButton);
        //Step 3C: Adding Components to Container 3
        pane3.setLayout(new BoxLayout(pane3, BoxLayout.LINE_AXIS));
        pane3.setBorder(new EtchedBorder());     // Give the panel a border
        pane3.setPreferredSize(new Dimension(900, 100));
        addTitle(pane3, "Delete student");
        JTextField idd = new JTextField(1);
        addComponent(pane3, new JLabel("Andrew ID"), idd);
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idd.getText().isEmpty()) {
                    area.append("Delete field cannot be left blank\n");
                } else if (d.searchByAndrewId(idd.getText()) == null) {
                    area.append(String.format("No matches found for Andrew ID %s\n", idd.getText()));
                } else {
                   StringBuilder result = new StringBuilder();
                   result.append("An Entry with the following details has been deleted\n");
                   result.append(String.valueOf(d.searchByAndrewId(idd.getText())));
                   result.append("\n");
                   area.append(result.toString());
                   d.deleteStudent(idd.getText());
                   idd.setText("");
                }
            }
        });
        pane3.add(deleteButton);
        //Step 3D: Adding components to Container 4
        pane4.setLayout(new BoxLayout(pane4, BoxLayout.LINE_AXIS));
        pane4.setBorder(new EtchedBorder());     // Give the panel a border
        pane4.setPreferredSize(new Dimension(900, 100));
        search = new JTextField(1);
        addComponent(pane4, new JLabel("Search Key"), search);
        byAndrewID = new JButton("By Andrew ID");
        byAndrewID.addActionListener(this);
        byFirstName = new JButton("By First Name");
        byLastName = new JButton("By Last Name");
        pane4.add(byAndrewID);
        pane4.add(Box.createHorizontalStrut(10));
        pane4.add(byFirstName);
        byFirstName.addActionListener(this);
        pane4.add(Box.createHorizontalStrut(10));
        pane4.add(byLastName);
        byLastName.addActionListener(this);
        search.addKeyListener(this);
        //Step 3E: Adding Components to container 5.
        pane5.setLayout(new BoxLayout(pane5, BoxLayout.LINE_AXIS));
        pane5.setBorder(new EtchedBorder());     // Give the panel a border
        pane5.setPreferredSize(new Dimension(900, 300));
        area = new JTextArea(10, 40);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        pane5.add(area);
        // Step 4: Displaying the contents of the pane
        window.getContentPane().add(pane1);
        window.getContentPane().add(pane2);
        window.getContentPane().add(pane3);
        window.getContentPane().add(pane4);
        window.getContentPane().add(pane5);
        window.setVisible(true);
        search.requestFocusInWindow();
    }
    /**
     * Method to be adding title for each pane.
     * @param pane tells the pane to add the title on.
     * @param title title string
     */
    public void addTitle(JPanel pane, String title) {
        TitledBorder border = new TitledBorder(title);
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
        pane.setBorder(border);
        return;
    }
    /**
     * Method to add two components together.
     * @param pane pane in which the components are to added.
     * @param item component 1.
     * @param itemfield component 2.
     */
    public void addComponent(JPanel pane, JComponent item, JComponent itemfield) {
        pane.add(item);
        pane.add(Box.createHorizontalStrut(10));
        itemfield.setMaximumSize(dim);
        pane.add(itemfield);
        pane.add(Box.createHorizontalStrut(10));
        return;
    }
    /**
     * Method to be invoked when search button is clicked.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        searchButton(ae);
    }
    /**
     * Method to be invoked when enter button is pressed.
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getSource() == search) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                if (search.getText().isEmpty()) {
                    area.append("Search field cannot be left blank\n");
                }
                if ((String.valueOf(d.searchByAndrewId(search.getText()))) == "null") {
                    area.append(String.format("No matches found for %s\n", search.getText()));
                } else {
                    StringBuilder result = new StringBuilder();
                    result.append(String.valueOf(d.searchByAndrewId(search.getText())));
                    result.append("\n");
                    area.append(result.toString());
                    search.setText("");
                }
            }
        }
    }
    /**
     * Method that defines the process when you click any search button.
     * @param event event object.
     */
    public void searchButton(EventObject event) {
        if (search.getText().isEmpty()) {
            area.append("Search field cannot be left blank\n");
        } else if (event.getSource() == byAndrewID) {
            if ((String.valueOf(d.searchByAndrewId(search.getText()))) == "null") {
                area.append(String.format("No matches found for AndrewID %s\n", search.getText()));
            } else {
                StringBuilder result = new StringBuilder();
                result.append(String.valueOf(d.searchByAndrewId(search.getText())));
                result.append("\n");
                area.append(result.toString());
                search.setText("");
            }
        } else if (event.getSource() == byFirstName) {
            if (((d.searchByFirstName(search.getText()))).equals(empty1)) {
                area.append(String.format("No matches found for First Name %s\n", search.getText()));
            } else {
                for (int i = 0; i < d.searchByFirstName(search.getText()).size(); i = i + 1) {
                    StringBuilder result = new StringBuilder();
                    result.append(String.valueOf(d.searchByFirstName(search.getText()).get(i)));
                    result.append("\n");
                    area.append(result.toString());
                }
                search.setText("");
            }
        } else if (event.getSource() == byLastName) {
            if (((d.searchByLastName(search.getText()))).equals(empty1)) {
                area.append(String.format("No matches found for Last Name %s\n", search.getText()));
            } else {
                for (int i = 0; i < d.searchByLastName(search.getText()).size(); i = i + 1) {
                    StringBuilder result = new StringBuilder();
                    result.append(String.valueOf(d.searchByLastName(search.getText()).get(i)));
                    result.append("\n");
                    area.append(result.toString());
                }
                search.setText("");
            }
        }
    }
    /**
     * Method to be invoked when enter key is pressed.
     */
    @Override
    public void keyReleased(KeyEvent arg0) {
    }
    /**
     * Method to be invoked when enter key is pressed.
     */
    @Override
    public void keyTyped(KeyEvent arg0) {
    }
    /**
     * Method to be return an array of words in a line.
     * @param line string to be tokenized
     * @return String[] values.
     */
    public static String[] readCSVLine(String line) {
        int commaCount = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ',') {
                commaCount = commaCount + 1;
            }
        }
        String[] values = new String[commaCount + 1];
        int beginIndex = 0;
        for (int i = 0; i < commaCount; i++) {
            int endIndex = line.indexOf(',', beginIndex);
            if (line.charAt(beginIndex) == '"' && line.charAt(endIndex - 1) == '"') {
                values[i] = line.substring(beginIndex + 1, endIndex - 1);
            } else {
                values[i] = line.substring(beginIndex, endIndex);
            }
            beginIndex = endIndex + 1;
        }
        if (line.charAt(beginIndex) == '"' && line.charAt(line.length() - 1) == '"') {
            values[commaCount] = line.substring(beginIndex + 1, line.length() - 1);
        } else {
            values[commaCount] = line.substring(beginIndex, line.length());
        }
        return values;
    }
    /**
     * Main method that instantiates GUI Object.
     * @param args command line arguments
     * @throws IOException for input-output errors.
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: java CSVReaderTest <filename>");
            System.exit(0);
        }
        if (args.length == 1) {
            String csvFile = args[0];
            String line = "";
            int lineNum = 0;
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                String firstLine = br.readLine();
                while ((line = br.readLine()) != null) {
                    lineNum = lineNum + 1;
                    String[] values = readCSVLine(line);
                    Student stud = new Student(values[2]);
                    stud.setFirstName(values[0]);
                    stud.setLastName(values[1]);
                    if (!values[3].isEmpty()) {
                        stud.setPhoneNumber(values[3]);
                    } else {
                        stud.setPhoneNumber("");
                    }
                    d.addStudent(stud);
                    System.out.print("Line " + lineNum + "  " + values.length + " components:");
                    for (int i = 0; i < values.length; i++) {
                        System.out.print(" \"" + values[i] + "\"");
                    }
                    System.out.println();
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         new DirectoryDriver();
     }
}
