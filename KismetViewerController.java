import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.IllegalStateException;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.lang.NullPointerException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;


// Primary class that controls everything that occurs for the main window. Controls the GUI elements
// for KismetViewer.fxml, a file created using SceneBuilder. GUI elements are connected through
// SceneBuilder.
public class KismetViewerController {
    @FXML private MenuBar menuBar;
    @FXML private Menu fileMenu;
    @FXML private MenuItem openMenuItem;
    @FXML private MenuItem exportMenuItem;
    @FXML private CheckMenuItem deleteConfirmCheckMenuItem;
    @FXML private Button statisticsButton;
    @FXML private Button clientsButton;
    @FXML private Button detailsButton;
    @FXML private ChoiceBox<String> categoryChoiceBox;
    @FXML private TextField searchTextField;
    @FXML private Button deleteButton;
    @FXML private TableView<Network> infoTableView;
    @FXML private TableColumn<Network, Integer> signalStrengthTableColumn;
    @FXML private TableColumn<Network, Integer> lastSignalDbmTableColumn;
    @FXML private TableColumn<Network, Integer> minSignalDbmTableColumn;
    @FXML private TableColumn<Network, Integer> maxSignalDbmTableColumn;
    @FXML private TableColumn<Network, String> BSSIDTableColumn;
    @FXML private TableColumn<Network, String> encryptionTableColumn;
    @FXML private TableColumn<Network, LocalDateTime> lastSeenTableColumn;
    @FXML private TableColumn<Network, Integer> numClientsTableColumn;
    
    private DateTimeFormatter stdFormatter = DateTimeFormatter.ofPattern("MMM d HH:mm:ss yyyy");
    private DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    private ObservableList<String> choiceList = FXCollections.observableArrayList();
    private List<Network> networkList = new ArrayList<Network>();
    private boolean fileUploaded = false;
    private static int counter = 0;
    
    @FXML
    public void initialize() {
        initializeCategoryChoiceBox();
        deleteConfirmCheckMenuItem.setSelected(true);
        
        searchTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            initializeTable(newVal);
        }); // searchTextField listener
        
        
        // To allow the user to press the 'backspace' button to delete entries on table on main screen
        infoTableView.setOnKeyReleased((KeyEvent e) -> {
            KeyCode key = e.getCode();
            
            if (key == KeyCode.BACK_SPACE && networkList.size() != 0) {
                if (deleteConfirmCheckMenuItem.isSelected()) {
                    boolean answer = ConfirmBox.display("Delete", "Are you sure you want to delete?");
                    
                    if (answer) {
                        networkList.remove(infoTableView.getSelectionModel().getSelectedItem());
                        infoTableView.getItems().removeAll(infoTableView.getSelectionModel().getSelectedItem());
                    } // if
                }
                else {
                    networkList.remove(infoTableView.getSelectionModel().getSelectedItem());
                    infoTableView.getItems().removeAll(infoTableView.getSelectionModel().getSelectedItem());
                } // else
            } // if
        }); // infoTableView setOnKeyReleased
    } // initialize
    
    
    // Opens a new window by calling a method from 'ClientsWindow.java'. Shows details about clients
    // based on selected network in the table. If nothing is selected, displays an error by opening
    // a new window by calling a method from 'MessageBox.java'.
    @FXML
    void clientsButtonPressed(ActionEvent event) {
        try {
            if (fileUploaded)
                ClientsWindow.display(infoTableView.getSelectionModel().getSelectedItem().getClientList());
            else
                MessageBox.display("Error", "No file has been uploaded");
        }
        catch (NullPointerException e) {
            MessageBox.display("Error", "No item selected");
        } // catch
    } // clientsButtonPressed
    
    
    // If a file has been uploaded and a network in the table is selected, this will delete it from
    // the table (and the list that the table is displaying) so if the data is exported the deleted
    // item will not appear. Displays errors by opening a new window by calling a method from
    // 'MessageBox.java'
    @FXML
    void deleteButtonPressed(ActionEvent event) {
        try {
            if (fileUploaded) {
                if (deleteConfirmCheckMenuItem.isSelected()) {
                    boolean answer = ConfirmBox.display("Delete", "Are you sure you want to delete?");
                    
                    if (answer) {
                        networkList.remove(infoTableView.getSelectionModel().getSelectedItem());
                        infoTableView.getItems().removeAll(infoTableView.getSelectionModel().getSelectedItem());
                    } // if
                }
                else {
                    networkList.remove(infoTableView.getSelectionModel().getSelectedItem());
                    infoTableView.getItems().removeAll(infoTableView.getSelectionModel().getSelectedItem());
                } // else
            }
            else
                MessageBox.display("Error", "No file has been uploaded");
        }
        catch (NullPointerException e) {
            MessageBox.display("Error", "No item selected");
        } // catch
    } // deleteButtonPressed
    
    
    
    // If a file has been uploaded and a network in the table is selected, this will open a new
    // window by calling a method from 'DetailsWindow.java'. This displays all the information
    // about the selected network that was gathered from the read-in Kismet output files.
    @FXML
    void detailsButtonPressed(ActionEvent event) {
        try {
            if (fileUploaded)
                DetailsWindow.display(infoTableView.getSelectionModel().getSelectedItem());
            else
                MessageBox.display("Error", "No file has been uploaded");
        }
        catch (NullPointerException e) {
            MessageBox.display("Error", "No item selected");
        } // catch
    } // detailsButtonPressed
    
    
    
    // Opens a new window by calling a method from 'ExportWindow.java' which takes in user input
    // and returns a String array of size 2. Calls appropriate export functions based on input.
    // result[0] = filetype, result[1] = filename
    @FXML
    void exportMenuItemPressed(ActionEvent event) {
        String [] result = ExportWindow.display();
        
        if (!result[0].equals("CANCEL_SENTVAL")) {
            if (result[0].equals("csv"))
                writeToCSVFile(result[1]);
            else if (result[0].equals("txt"))
                writeToTXTFile(result[1]);
        } // if
    } // exportMenuItemPressed
                
    
    
    // Opens a new window by calling a method from 'OpenWindow.java' which takes in a file path and
    // file name. Based on this, opens the appropriate files to read in the Kismet output. If the
    // input does not have an extension, it's assumed that the files to be read in are Kismet output
    // files that have the same name and are in the same directory (also that they have their
    // original, different extensions). The .nettxt and .netxml file are opened and read from to
    // gather the pertinent information (main information parsing is done in the .nettxt file, the
    // .netxml file is only used to gather signal strength information). If the input has a .txt
    // extension, it's assumed to be a file that was previously exported from KismetViewer and
    // is the sole file that is parsed (has similar format to the .nettxt file.
    @FXML
    void openMenuItemPressed(ActionEvent event) {
        String [] answer = OpenWindow.display();
        String txtExtension = ".nettxt", xmlExtension = ".netxml", filename = "";
        boolean hasExtension = false;
        
        if (!answer[0].equals("CANCEL_SENTVAL")) {
            fileUploaded = true;
            
            if (answer[1].contains(".txt")) {
                filename = answer[1]; // will read in a .txt file, output from KismetViewer
                hasExtension = true;
            }
            else
                filename = answer[1] + txtExtension; // will read in a .nettxt and .netxml file
            
            try (Scanner input = new Scanner(Paths.get(filename))) {
                String temp = "";
                String [] tokens;
                boolean hasWPAVersion = false, started = false;
                int index = 0;
                
                while (input.hasNextLine()) {
                    // If line doesn't start with "Network", it's irrelevant information
                    // All relevant information follows a line that starts with "Network" and is
                    // captured in the following if statements
                    if (!temp.startsWith("Network"))
                        temp = trimSpaces(input.nextLine());
                    
                    
                    if (temp.startsWith("Network")) {
                        Network tempNetwork = new Network();
                        
                        // get manufacturer [string]
                        tokens = tokenize(input.nextLine());
                        temp = "";
                        for (int i = 2; i < tokens.length; i++)
                            temp += tokens[i] + " ";
                        tempNetwork.setManuf(temp);
                        
                        // get firstSeen [localdatetime]
                        tokens = tokenize(input.nextLine());
                        temp = "";
                        for (int i = 2; i < tokens.length; i++)
                            temp += tokens[i] + " ";
                        tempNetwork.setFirstSeen(temp);
                        
                        // get lastSeen [localdatetime]
                        tokens = tokenize(input.nextLine());
                        temp = "";
                        for (int i = 2; i < tokens.length; i++)
                            temp += tokens[i] + " ";
                        tempNetwork.setLastSeen(temp);
                        
                        // get type [string]
                        tokens = tokenize(input.nextLine());
                        temp = "";
                        for (int i = 2; i < tokens.length; i++)
                            temp += tokens[i] + " ";
                        tempNetwork.setType(temp);
                        
                        // get BSSID [string]
                        tokens = tokenize(input.nextLine());
                        tempNetwork.setBSSID(tokens[2]);
                        
                        // check if there's an SSID or if next line is 'channel'
                        // can have [0,infinity] SSIDs
                        temp = trimSpaces(input.nextLine());
                        if (temp.startsWith("SSID")) {
                            
                            // can have multiple SSIDs so have to check [SSID object]
                            while (temp.startsWith("SSID")) {
                                SSID tempSSID = new SSID();
                                
                                // get type [string]
                                tokens = tokenize(input.nextLine());
                                temp = "";
                                for (int i = 2; i < tokens.length; i++)
                                    temp += tokens[i] + " ";
                                tempSSID.setType(temp);
                                
                                // get SSID [string]
                                tokens = tokenize(input.nextLine());
                                temp = "";
                                for (int i = 2; i < tokens.length; i++)
                                    temp += tokens[i] + " ";
                                tempSSID.setSSIDName(temp);
                                
                                // get info [string] - can have [0,1]
                                tokens = tokenize(input.nextLine());
                                if (tokens[0].contains("Info")) {
                                    tempSSID.setInfo(tokens[2]);
                                    tokens = tokenize(input.nextLine());
                                } // if
                                
                                // get firstSeen [localdatetime]
                                temp = "";
                                for (int i = 2; i < tokens.length; i++)
                                    temp += tokens[i] + " ";
                                tempSSID.setFirstSeen(temp);
                                
                                // get lastSeen [localdatetime]
                                tokens = tokenize(input.nextLine());
                                temp = "";
                                for (int i = 2; i < tokens.length; i++)
                                    temp += tokens[i] + " ";
                                tempSSID.setLastSeen(temp);
                                
                                // get maxRate [double]
                                tokens = tokenize(input.nextLine());
                                tempSSID.setMaxRate(Double.parseDouble(tokens[3]));
                                
                                // get beacon [int] - can have [0,1]
                                tokens = tokenize(input.nextLine());
                                if (tokens[0].contains("Beacon")) {
                                    tempSSID.setBeacon(Integer.parseInt(tokens[2]));
                                    tokens = tokenize(input.nextLine());
                                } // if
                                
                                // get packets [int]
                                tempSSID.setPackets(Integer.parseInt(tokens[2]));
                                
                                // get WPS [string]
                                tokens = tokenize(input.nextLine());
                                temp = "";
                                for (int i = 2; i < tokens.length; i++)
                                    temp += tokens[i] + " ";
                                tempSSID.setWPS(temp);
                                
                                // get WPSManuf [string] - can have [0,1]
                                temp = trimSpaces(input.nextLine());
                                if (temp.startsWith("WPS Manuf")) {
                                    tokens = temp.split(" ");
                                    temp = "";
                                    for (int i = 3; i < tokens.length; i++)
                                        temp += tokens[i] + " ";
                                    tempSSID.setWPSManuf(temp);
                                    temp = trimSpaces(input.nextLine());
                                } // if
                                
                                // get devName [string] - can have [0,1]
                                if (temp.startsWith("Dev")) {
                                    tokens = temp.split(" ");
                                    temp = "";
                                    for (int i = 3; i < tokens.length; i++)
                                        temp += tokens[i] + " ";
                                    tempSSID.setWPSManuf(temp);
                                    temp = trimSpaces(input.nextLine());
                                } // if
                                
                                // get modelName [string] - can have [0,1]
                                if (temp.startsWith("Model Name")) {
                                    tokens = temp.split(" ");
                                    temp = "";
                                    for (int i = 3; i < tokens.length; i++)
                                        temp += tokens[i] + " ";
                                    tempSSID.setWPSManuf(temp);
                                    temp = trimSpaces(input.nextLine());
                                } // if
                                
                                // get modelNum [string] - can have [0,1]
                                if (temp.startsWith("Model Num")) {
                                    tokens = temp.split(" ");
                                    temp = "";
                                    for (int i = 3; i < tokens.length; i++)
                                        temp += tokens[i] + " ";
                                    tempSSID.setWPSManuf(temp);
                                    temp = trimSpaces(input.nextLine());
                                } // if
                                
                                // get encryption [string] - can have [1,infinity]
                                while (temp.startsWith("Encryption")) {
                                    tokens = temp.split(" ");
                                    tempSSID.addEncryption(tokens[2]);
                                    
                                    temp = trimSpaces(input.nextLine());
                                } // while
                                
                                // get WPA Version [string] - can have [0,1]
                                if (temp.startsWith("WPA Version")) {
                                    hasWPAVersion = true;
                                    tokens = temp.split(" ");
                                    tempSSID.setWPAVersion(tokens[2]);
                                } // if
                                
                                // If it doesn't have a WPA Version, then temp will already be
                                // holding either 'channel' or the next SSID so don't need to get
                                // next line. If it does, need to get the next line to process
                                if (hasWPAVersion)
                                    temp = trimSpaces(input.nextLine());
                                
                                tempNetwork.addSSIDNetwork(tempSSID); // add
                            } // while
                        } // if SSID
                        
                        
                        // get channel
                        tokens = temp.split(" ");
                        tempNetwork.setChannel(Integer.parseInt(tokens[2]));
                        
                        // get frequency [string] - can have [1,infinity]
                        temp = trimSpaces(input.nextLine());
                        while (temp.startsWith("Frequency")) {
                            tokens = temp.split(":");
                            tempNetwork.addFrequency(tokens[1].trim());
                            temp = trimSpaces(input.nextLine());
                        } // while
                        
                        // get maxSeen [int]
                        tokens = temp.split(" ");
                        tempNetwork.setMaxSeen(Integer.parseInt(tokens[3]));
                        
                        // get carrier [string] - can have [0,infinity]
                        tokens = tokenize(input.nextLine());
                        if (tokens[0].contains("Carrier")) {
                            while (tokens[0].contains("Carrier")) {
                                temp = "";
                                for (int i = 2; i < tokens.length; i++)
                                    temp += tokens[i] + " ";
                                tempNetwork.addCarrier(temp);
                                tokens = tokenize(input.nextLine());
                            } // while
                        } // if
                        
                        // get encoding [string] - can have [0,infinity]
                        if (tokens[0].contains("Encoding")) {
                            while (tokens[0].contains("Encoding")) {
                                tempNetwork.addEncoding(tokens[2]);
                                tokens = tokenize(input.nextLine());
                            } // while
                        } // if
                        
                        // get LLC [int]
                        tempNetwork.setLLC(Integer.parseInt(tokens[2]));
                        
                        // get data [int]
                        tokens = tokenize(input.nextLine());
                        tempNetwork.setData(Integer.parseInt(tokens[2]));
                        
                        // get crypt [int]
                        tokens = tokenize(input.nextLine());
                        tempNetwork.setCrypt(Integer.parseInt(tokens[2]));
                        
                        // get fragments [int]
                        tokens = tokenize(input.nextLine());
                        tempNetwork.setFragments(Integer.parseInt(tokens[2]));
                        
                        // get retries [int]
                        tokens = tokenize(input.nextLine());
                        tempNetwork.setRetries(Integer.parseInt(tokens[2]));
                        
                        // get total [int]
                        tokens = tokenize(input.nextLine());
                        tempNetwork.setTotal(Integer.parseInt(tokens[2]));
                        
                        // get datasize [int]
                        tokens = tokenize(input.nextLine());
                        tempNetwork.setDatasize(Integer.parseInt(tokens[2]));
                        
                        // get lastBSSTS [localdatetime]
                        tokens = tokenize(input.nextLine());
                        temp = "";
                        for (int i = 3; i < tokens.length; i++)
                            temp += tokens[i] + " ";
                        tempNetwork.setLastBSSTS(temp);
                        
                        // get seenBy [string]
                        tokens = tokenize(input.nextLine());
                        temp = "";
                        for (int i = 3; i < tokens.length; i++)
                            temp += tokens[i] + " ";
                        temp += trimSpaces(input.nextLine());
                        tempNetwork.setSeenBy(temp);
                        
                        // if re-uploaded .txt file, get last/min/max signal dbm [int]
                        if (hasExtension) {
                            tokens = tokenize(input.nextLine());
                            tempNetwork.setLastSignalDbm(Integer.parseInt(tokens[2]));
                            
                            tokens = tokenize(input.nextLine());
                            tempNetwork.setMinSignalDbm(Integer.parseInt(tokens[2]));
                            
                            tokens = tokenize(input.nextLine());
                            tempNetwork.setMaxSignalDbm(Integer.parseInt(tokens[2]));
                        } // if hasExtension
                        
                        // get alerts [] [string] - can have [0,infinity]
                        temp = trimSpaces(input.nextLine());
                        if (temp.startsWith("Alert")) {
                            while (temp.startsWith("Alert")) {
                                index = temp.indexOf(":");
                                tempNetwork.addAlert(temp.substring(index+2, temp.length()));
                                temp = trimSpaces(input.nextLine());
                            } // while
                        }
                        
                        // get Clients [], check if there's a Client - can have [0,infinity]
                        if (temp.startsWith("Client")) {

                            // can have multiple Clients so have to check [Client object]
                            while (temp.startsWith("Client")) {
                                Client tempClient = new Client();
                                
                                // get manufacturer [string]
                                tokens = tokenize(input.nextLine());
                                temp = "";
                                for (int i = 2; i < tokens.length; i++)
                                    temp += tokens[i] + " ";
                                tempClient.setManuf(temp);
                                
                                // get firstSeen [localdatetime]
                                tokens = tokenize(input.nextLine());
                                temp = "";
                                for (int i = 2; i < tokens.length; i++)
                                    temp += tokens[i] + " ";
                                tempClient.setFirstSeen(temp);
                                
                                // get lastSeen [localdatetime]
                                tokens = tokenize(input.nextLine());
                                temp = "";
                                for (int i = 2; i < tokens.length; i++)
                                    temp += tokens[i] + " ";
                                tempClient.setLastSeen(temp);
                                
                                // get type [string]
                                tokens = tokenize(input.nextLine());
                                temp = "";
                                for (int i = 2; i < tokens.length; i++)
                                    temp += tokens[i] + " ";
                                tempClient.setType(temp);
                                
                                // get MAC [string]
                                tokens = tokenize(input.nextLine());
                                tempClient.setMAC(tokens[2]);
                                
                                // check if there's an SSID or if next line is 'channel'
                                // can have [0, infinity] SSIDs associated with each Client
                                temp = trimSpaces(input.nextLine());
                                if (temp.startsWith("SSID")) {
                                    
                                    // can have multiple SSIDs so have to check [SSID object]
                                    while (temp.startsWith("SSID")) {
                                        SSID tempSSID = new SSID();
                                        
                                        // get type [string]
                                        tokens = tokenize(input.nextLine());
                                        temp = "";
                                        for (int i = 2; i < tokens.length; i++)
                                            temp += tokens[i] + " ";
                                        tempSSID.setType(temp);
                                        
                                        // get SSID [string]
                                        tokens = tokenize(input.nextLine());
                                        temp = "";
                                        for (int i = 2; i < tokens.length; i++)
                                            temp += tokens[i] + " ";
                                        tempSSID.setSSIDName(temp);
                                        
                                        // get firstSeen [localdatetime]
                                        tokens = tokenize(input.nextLine());
                                        temp = "";
                                        for (int i = 2; i < tokens.length; i++)
                                            temp += tokens[i] + " ";
                                        tempSSID.setFirstSeen(temp);
                                        
                                        // get lastSeen [localdatetime]
                                        tokens = tokenize(input.nextLine());
                                        temp = "";
                                        for (int i = 2; i < tokens.length; i++)
                                            temp += tokens[i] + " ";
                                        tempSSID.setLastSeen(temp);
                                        
                                        // get maxRate [double]
                                        tokens = tokenize(input.nextLine());
                                        tempSSID.setMaxRate(Double.parseDouble(tokens[3]));
                                        
                                        // get packets [int]
                                        tokens = tokenize(input.nextLine());
                                        tempSSID.setPackets(Integer.parseInt(tokens[2]));
                                        
                                        // get encryption [] - can have [1,infinity]
                                        temp = trimSpaces(input.nextLine());
                                        while (temp.startsWith("Encryption")) {
                                            tokens = temp.split(" ");
                                            tempSSID.addEncryption(tokens[2]);
                                            temp = trimSpaces(input.nextLine());
                                        } // while
                                        
                                        tempClient.addSSIDClient(tempSSID); // add
                                    } // while
                                } // if SSID
                                
                                
                                // get channel [string]
                                tokens = temp.split(" ");
                                tempClient.setChannel(Integer.parseInt(tokens[2]));
                                
                                // get frequency [string] - can have [1,infinity]
                                temp = trimSpaces(input.nextLine());
                                while (temp.startsWith("Frequency")) {
                                    tokens = temp.split(":");
                                    tempClient.addFrequency(tokens[1].trim());
                                    temp = trimSpaces(input.nextLine());
                                } // while
                                
                                // get maxSeen [int]
                                tokens = temp.split(" ");
                                tempClient.setMaxSeen(Integer.parseInt(tokens[3]));
                                
                                // get carrier [string] - can have [0,infinity]
                                tokens = tokenize(input.nextLine());
                                if (tokens[0].contains("Carrier")) {
                                    while (tokens[0].contains("Carrier")) {
                                        temp = "";
                                        for (int i = 2; i < tokens.length; i++)
                                            temp += tokens[i] + " ";
                                        tempClient.addCarrier(temp);
                                        tokens = tokenize(input.nextLine());
                                    }
                                } // if
                                
                                // get encoding [string] - can have [0,infinity]
                                if (tokens[0].contains("Encoding")) {
                                    while (tokens[0].contains("Encoding")) {
                                        tempClient.addEncoding(tokens[2]);
                                        tokens = tokenize(input.nextLine());
                                    } // while
                                } // if
                                    
                                
                                // get LLC [int]
                                tempClient.setLLC(Integer.parseInt(tokens[2]));
                                
                                // get data [int]
                                tokens = tokenize(input.nextLine());
                                tempClient.setData(Integer.parseInt(tokens[2]));
                                
                                // get crypt [int]
                                tokens = tokenize(input.nextLine());
                                tempClient.setCrypt(Integer.parseInt(tokens[2]));
                                
                                // get fragments [int]
                                tokens = tokenize(input.nextLine());
                                tempClient.setFragments(Integer.parseInt(tokens[2]));
                                
                                // get retries [int]
                                tokens = tokenize(input.nextLine());
                                tempClient.setRetries(Integer.parseInt(tokens[2]));
                                
                                // get total [int]
                                tokens = tokenize(input.nextLine());
                                tempClient.setTotal(Integer.parseInt(tokens[2]));
                                
                                // get datasize [int]
                                tokens = tokenize(input.nextLine());
                                tempClient.setDatasize(Integer.parseInt(tokens[2]));
                                
                                // get seenBy [string]
                                tokens = tokenize(input.nextLine());
                                temp = "";
                                for (int i = 3; i < tokens.length; i++)
                                    temp += tokens[i] + " ";
                                temp += trimSpaces(input.nextLine());
                                tempClient.setSeenBy(temp);

                                if (input.hasNextLine())
                                    temp = trimSpaces(input.nextLine());
                                
                                tempNetwork.addClient(tempClient); // add
                            } // while Client
                        } // if Client
                        
                        // should be network here or done
                        networkList.add(tempNetwork);
                    } // if network
                    
                } // while Network
                
            } 
            catch (IOException | NoSuchElementException | IllegalStateException e) {
                e.printStackTrace();
                MessageBox.display("Error", "Error opening " + txtExtension + " file");
            } // catch
            
            
            // If there's no extension, need to also read the .netxml file to get the Signal Strength
            // information. Min/Max/Last Signal Strength information is the only thing parsed from
            // the .netxml file
            if (!hasExtension) {
                try (Scanner input = new Scanner(Paths.get(answer[1]+xmlExtension))) {
                    boolean enteredNetwork = false;
                    String temp = "", last_signal_dbm = "", min_signal_dbm = "", max_signal_dbm = "";
                    
                    while (input.hasNextLine()) {
                        temp = input.nextLine().trim();
                        
                        if (temp.contains("wireless-network number"))
                            enteredNetwork = true;
                        
                        if (enteredNetwork && temp.contains("last_signal_dbm")) {
                            last_signal_dbm = temp.substring(temp.indexOf(">")+1, temp.lastIndexOf("<"));
                            networkList.get(counter).setLastSignalDbm(Integer.parseInt(last_signal_dbm));
                            System.out.println(networkList.get(counter).getBSSID());
                            System.out.println(last_signal_dbm);
                            System.out.println(String.valueOf(counter));
                            System.out.println(networkList.get(counter).getLastSignalDbm());
                        } // if
                        
                        if (enteredNetwork && temp.contains("min_signal_dbm")) {
                            min_signal_dbm = temp.substring(temp.indexOf(">")+1, temp.lastIndexOf("<"));
                            networkList.get(counter).setMinSignalDbm(Integer.parseInt(min_signal_dbm));
                        } // if
                        
                        if (enteredNetwork && temp.contains("max_signal_dbm")) {
                            max_signal_dbm = temp.substring(temp.indexOf(">")+1, temp.lastIndexOf("<"));
                            networkList.get(counter).setMaxSignalDbm(Integer.parseInt(max_signal_dbm));
                            enteredNetwork = false;
                            counter++;
                        } // if
                    } // while
                }
                catch (IOException | NoSuchElementException | IllegalStateException e) {
                    // e.printStackTrace();
                    MessageBox.display("Error", "Error opening " + xmlExtension + " file");
                } // catch
            } // if
            
            initializeTable(""); // initialize the table with a empty search string so all info appears
        } // if
        
    } // openMenuItemPressed
    
    
    // Replaces all multiple spaces with one space and then tokenizes the string by splitting spaces
    private String [] tokenize(String tok) {
        return tok.trim().replaceAll("\\s{2,}"," ").split(" ");
    } // tokenize
    
    // Replaces all multiple spaces with one space
    private String trimSpaces(String str) {
        return str.trim().replaceAll("\\s{2,}"," ");
    } // trimSpaces
    
    
    // Initializes table on main screen based on the 'search' parameter string passed in. If blank,
    // shows all networks, otherwise shows networks based on the search parameter for the selected
    // options in the categoryChoiceBox (i.e. if BSSID is selected in the categoryChoiceBox, then
    // the search parameter is only matched with network BSSIDs to decide which ones populate the
    // table). Search parameter is not case-sensitive.
    private void initializeTable(String search) {
        infoTableView.getItems().clear(); // clear existing data
        search = search.toLowerCase(); // not case-sensitive search
        
        lastSignalDbmTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastSignalDbm"));
        minSignalDbmTableColumn.setCellValueFactory(new PropertyValueFactory<>("minSignalDbm"));
        maxSignalDbmTableColumn.setCellValueFactory(new PropertyValueFactory<>("maxSignalDbm"));
        BSSIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("BSSID"));
        encryptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("encryption"));
        lastSeenTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastSeen"));
        numClientsTableColumn.setCellValueFactory(new PropertyValueFactory<>("numClients"));
        
        if (search.equals("") || search == null)
            infoTableView.getItems().addAll(networkList);
        else {
            switch (categoryChoiceBox.getValue()) {
                case "Signal Strength":
                    for (int i = 0; i < networkList.size(); i++)
                        if (String.valueOf(networkList.get(i).getLastSignalDbm()).toLowerCase().contains(search) ||
                            String.valueOf(networkList.get(i).getMinSignalDbm()).toLowerCase().contains(search) ||
                            String.valueOf(networkList.get(i).getMaxSignalDbm()).toLowerCase().contains(search) )
                            infoTableView.getItems().add(networkList.get(i));
                    break;
                case "BSSID":
                    for (int i = 0; i < networkList.size(); i++)
                        if (networkList.get(i).getBSSID().toLowerCase().contains(search))
                            infoTableView.getItems().add(networkList.get(i));
                    break;
                case "Encryption":
                    for (int i = 0; i < networkList.size(); i++)
                        if (networkList.get(i).getEncryption().toLowerCase().contains(search))
                            infoTableView.getItems().add(networkList.get(i));
                    break;
                case "Last Seen":
                    for (int i = 0; i < networkList.size(); i++)
                        if (networkList.get(i).getLastSeen().toLowerCase().contains(search))
                            infoTableView.getItems().add(networkList.get(i));
                    break;
                case "Clients":
                    for (int i = 0; i < networkList.size(); i++)
                        if (String.valueOf(networkList.get(i).getNumClients()).toLowerCase().contains(search))
                            infoTableView.getItems().add(networkList.get(i));
                    break;
                default: infoTableView.getItems().addAll(networkList);
                    break;
            } // switch
        } // else
        
    } // initializeTable
    
    
    // Initializes the categoryChoiceBox with the columns from the infoTableView (done manually).
    // These will be the categories that users can use as categories to limit their searches.
    private void initializeCategoryChoiceBox() {
        choiceList.removeAll(choiceList);
        choiceList.addAll("Signal Strength", "BSSID", "Encryption", "Last Seen", "Clients");
        categoryChoiceBox.getItems().addAll(choiceList);
        categoryChoiceBox.setValue(choiceList.get(0)); // sets first value as default
    } // initializeCategoryChoiceBox
    
    
    // Opens a window by calling a method from 'StatisticsWindow.java' which at the moment just
    // shows a pie graph of all the different types of encryptions encountered for the networks.
    // Can be expanded to show other/more details statistics.
    @FXML
    void statisticsButtonPressed(ActionEvent event) {
        if (fileUploaded)
            StatisticsWindow.display(networkList);
        else
            MessageBox.display("Error", "No file has been uploaded");
    } // statisticsButtonPressed

    
    // Method called by exportMenuItemPressed if the user selects '.csv'. Produces a .csv file that
    // contains all networks (each row is a network). Because each network can have a different
    // number of clients and SSIDs (and each client can have a different number of SSIDs), the .csv
    // file is not very organized. Can be organized in future. SSIDs are enclosed by {} brackets
    // and Clients are enclosed by [] brackets
    private void writeToCSVFile(String filename) {
        try (Formatter output = new Formatter(filename + ".csv")) {
            output.format("%s %s %s %s%n", "Manuf, First, Last, Type, BSSID, {SSIDs}, Channel,",
                          "Frequency(s), Carrier(s), Encoding(s), LLC, Data, Crypt, Fragments,",
                          "Retries, Total, Datasize, Last BSSTS, Seen By, Alert(s),",
                          "LastSignalDBM, MinSignalDBM, MaxSignalDBM, [Clients]");
            
            
            for (int i = 0; i < networkList.size(); i++) {
                output.format("%s%n", networkList.get(i).toString_CSV());
            } // for
            
        }
        catch (SecurityException | FileNotFoundException | FormatterClosedException e) {
            e.printStackTrace();
            System.exit(1); // terminate the program
        } // catch
    } // writeToCSVFile
    
    
    // Method called by exportMenuItemPressed if the user selects '.txt'. Produces a .txt file
    // that can be re-opened by KismetViewer to re-view/manage the data. This file mirrors the
    // format of regular Kismet .nettxt output files with the exception that it also contains
    // min/max/last signal strength after 'Seen By'.
    private void writeToTXTFile(String filename) {
        try (Formatter output = new Formatter(filename + ".txt")) {
            for (int i = 0; i < networkList.size(); i++)
                output.format("%s%n", networkList.get(i).toString_TXT());
        }
        catch (SecurityException | FileNotFoundException | FormatterClosedException e) {
            e.printStackTrace();
            System.exit(1); // terminate the program
        } // catch
    } // writeToCSVFile
    
} // KismetViewerController




